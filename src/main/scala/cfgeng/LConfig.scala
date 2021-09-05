package cfgeng

class LConfig(source: AnyRef, var rootNode: LConfigNode, var error: Int = 0, var errorToken: LConfigToken = LConfigToken.None) {

  def findNode(path: String): LConfigNode = rootNode.findChildNode(path)

  def findString(path: String): LConfigValueString = rootNode.findString(path)

  def findNumber(path: String): LConfigValueNumber = rootNode.findNumber(path)

  def print() {
    println(rootNode.toString())
  }

}