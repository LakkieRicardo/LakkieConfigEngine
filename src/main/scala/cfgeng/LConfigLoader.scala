package cfgeng

import java.io.InputStream
import scala.io.BufferedSource

object LConfigLoader {

  def createEmptyConfig(): LConfig = new LConfig(null, new LConfigNode("root"))

  def loadConfigFromStream(input: BufferedSource): LConfig = {
    val tokenIterator = new TokenIterator(input)
    var currentNode = new LConfigNode("root")
    val config = new LConfig(input, currentNode)

    var lastIdentifierToken = LConfigToken.None
    var hasAssignmentToken = false

    while (tokenIterator.hasNext) {
      val token = tokenIterator.next
      if (token != null) {
        if (token.tokenType == LConfigTokenType.TokenIdentifier) {
          lastIdentifierToken = token
        } else {
          if (token.tokenType == LConfigTokenType.TokenBraceOpen) {
            if (lastIdentifierToken != LConfigToken.None) {
              // Enter new node
              val node = new LConfigNode(lastIdentifierToken.tokenValue, parentNode = currentNode)
              currentNode.children.append(node)
              currentNode = node
            } else {
              config.error = 1
              return config
            }
          } else if (token.tokenType == LConfigTokenType.TokenBraceClose) {
            if (currentNode.parentNode != null) {
              // Leave current node
              currentNode = currentNode.parentNode
            } else {
              config.error = 2
              return config
            }
          } else if (token.tokenType == LConfigTokenType.TokenNumber) {
            if (hasAssignmentToken) {
              if (lastIdentifierToken != LConfigToken.None) {
                val numValueNode = new LConfigValueNumber(lastIdentifierToken.tokenValue, token.tokenValue.toDouble)
                currentNode.numberValues.append(numValueNode)
              } else {
                config.error = 4
                return config
              }
            } else {
              config.error = 3
              return config
            }
          } else if (token.tokenType == LConfigTokenType.TokenString) {
            if (hasAssignmentToken) {
              if (lastIdentifierToken != LConfigToken.None) {
                val numValueNode = new LConfigValueString(lastIdentifierToken.tokenValue, token.tokenValue.substring(1, token.tokenValue.length - 1)) // Strip off quotes
                currentNode.stringValues.append(numValueNode)
              } else {
                config.error = 4
                return config
              }
            } else {
              config.error = 3
              return config
            }
          }
        }
        hasAssignmentToken = token.tokenType == LConfigTokenType.TokenAssignment
      }
    }
    return config
  }



}