package cfgeng

class LConfig(source: AnyRef, rootNode: LConfigNode, var error: Int = 0) {

  def print() {
    rootNode.print()
  }

}