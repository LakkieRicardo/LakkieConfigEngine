package cfgeng

class LConfig(source: AnyRef, rootNode: LConfigNode, var error: Int = 0, var errorToken: LConfigToken = LConfigToken.None) {

  def print() {
    println(rootNode.toString())
  }

}