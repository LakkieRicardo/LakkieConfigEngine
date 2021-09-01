package cfgeng

import scala.io.BufferedSource

class TokenIterator(var input: BufferedSource) extends Iterator[LConfigToken] {

  var prevChar = if (input.hasNext) input.next else '\u0000'
  var isFirstChar = true
  var appendPrevChar = false

  var currentLine = 1
  var currentChar = 1

  override def hasNext: Boolean = input.hasNext

  override def next(): LConfigToken = {
    var isConfigLine = false
    var currentToken = LConfigTokenType.TokenNone
    val currentTokenValue = new StringBuilder()
    var stringTokenEscaped = false

    var tokenStartLine = currentLine
    var tokenStartChar = currentChar
    
    while (hasNext) {
      var nextChar = ' '
      if (isFirstChar) {
        nextChar = prevChar
        isFirstChar = false
      } else {
        if (appendPrevChar) {
          nextChar = prevChar
          appendPrevChar = false
        } else {
          nextChar = input.next
          currentChar += 1
        }
      }

      if (nextChar == '\n') {
        currentLine += 1
        currentChar = 1
      }

      if (!isConfigLine && nextChar != '#') {
        if (currentToken == LConfigTokenType.TokenNone) {
          // Attempt to identify new token type
          currentToken = identifyTokenByFirstCharacter(nextChar)
          if (currentToken == LConfigTokenType.TokenAssignment || currentToken == LConfigTokenType.TokenBraceOpen || currentToken == LConfigTokenType.TokenBraceClose) {
            currentTokenValue.append(nextChar)
            tokenStartLine = currentLine
            tokenStartChar = currentChar
            return new LConfigToken(currentToken, currentTokenValue.toString, tokenStartLine, tokenStartChar, currentLine, currentChar)
          } else if (currentToken != LConfigTokenType.TokenNone) {
            currentTokenValue.append(nextChar)
            tokenStartLine = currentLine
            tokenStartChar = currentChar
          }
        } else {
          if (currentToken == LConfigTokenType.TokenNumber) {
            if (!nextChar.isDigit && nextChar != '.') {
              // If this is the end of the number sequence
              prevChar = nextChar
              appendPrevChar = true
              return new LConfigToken(currentToken, currentTokenValue.toString, tokenStartLine, tokenStartChar, currentLine, currentChar)
            } else {
              currentTokenValue.append(nextChar)
            }
          } else if (currentToken == LConfigTokenType.TokenString) {
            if (nextChar == '\\') {
              if (stringTokenEscaped) {
                stringTokenEscaped = false
                currentTokenValue.append(nextChar)
              } else {
                stringTokenEscaped = true
              }
            } else if (nextChar == '"') {
              currentTokenValue.append(nextChar)
              if (stringTokenEscaped) {
                stringTokenEscaped = false
              } else {
                prevChar = nextChar
                return new LConfigToken(currentToken, currentTokenValue.toString, tokenStartLine, tokenStartChar, currentLine, currentChar)
              }
            } else {
              currentTokenValue.append(nextChar)
            }
          } else if (currentToken == LConfigTokenType.TokenIdentifier) {
            if (nextChar.isLetterOrDigit || nextChar == '_' || nextChar == '-') {
              currentTokenValue.append(nextChar)
            } else {
              prevChar = nextChar
              appendPrevChar = true
              return new LConfigToken(currentToken, currentTokenValue.toString, tokenStartLine, tokenStartChar, currentLine, currentChar)
            }
          }
        }
      } else if (isConfigLine && nextChar == '\n') {
        isConfigLine = false
      }  else if (nextChar == '#') {
        isConfigLine = true
      }
    }

    return null
  }

  def identifyTokenByFirstCharacter(next: Char): LConfigTokenType.LConfigTokenType = {
    if (next.isDigit || next == '-' || next == '.')
    {
      return LConfigTokenType.TokenNumber
      // currentTokenValue.append(next)
    } else if (next == '"') {
      return LConfigTokenType.TokenString
    } else if (next.isLetter || next == '_' || next == '-') {
      return LConfigTokenType.TokenIdentifier
      //currentTokenValue.append(next)
    } else if (next == '{') {
      return LConfigTokenType.TokenBraceOpen //, "{", currentNode)
    } else if (next == '}') {
      return LConfigTokenType.TokenBraceClose //, "}", currentNode)
    } else if (next == '=') {
      return LConfigTokenType.TokenAssignment //, "=", currentNode)
    } else {
      return LConfigTokenType.TokenNone
    }
  }

}
