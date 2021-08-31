package cfgeng

import scala.collection.mutable.ArrayBuffer

class LConfigNode(var name: String, var children: ArrayBuffer[LConfigNode] = new ArrayBuffer[LConfigNode], var parentNode: LConfigNode = null, var numberValues: ArrayBuffer[LConfigValueNumber] = new ArrayBuffer[LConfigValueNumber], var stringValues: ArrayBuffer[LConfigValueString] = new ArrayBuffer[LConfigValueString]) {

  override def toString(): String = {
    val out = new StringBuilder()
    out.append("Node ")
    out.append(name)
    out.append(" with numbers [")
    var isFirst = true
    for (numberValue <- numberValues) {
      if (isFirst) {
        out.append("[")
        out.append(numberValue.name)
        out.append(" = ")
        out.append(numberValue.value)
        out.append("]")
      } else {
        out.append(",[")
        out.append(numberValue.name)
        out.append(" = ")
        out.append(numberValue.value)
        out.append("]")
      }
      isFirst = false
    }
    out.append("] with strings [")
    isFirst = true
    for (stringValue <- stringValues) {
      if (isFirst) {
        out.append("[")
        out.append(stringValue.name)
        out.append(" = ")
        out.append(stringValue.value)
        out.append("]")
      } else {
        out.append(",[")
        out.append(stringValue.name)
        out.append(" = ")
        out.append(stringValue.value)
        out.append("]")
      }
      isFirst = false
    }
    out.append("] with children [")
    isFirst = true
    for (childNode <- children) {
      if (isFirst) {
        out.append("[")
        out.append(childNode)
        out.append("]")
      } else {
        out.append(",[")
        out.append(childNode)
        out.append("]")
      }
      isFirst = false
    }
    out.append("]")
    return out.toString
  }

}

class LConfigValueString(var name: String, var value: String)

class LConfigValueNumber(var name: String, var value: Double)