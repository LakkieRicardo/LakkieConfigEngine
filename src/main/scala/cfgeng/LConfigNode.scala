package cfgeng

import scala.collection.mutable.ArrayBuffer

class LConfigNode(var name: String, var children: ArrayBuffer[LConfigNode] = new ArrayBuffer[LConfigNode], var parentNode: LConfigNode = null, var numberValues: ArrayBuffer[LConfigValueNumber] = new ArrayBuffer[LConfigValueNumber], var stringValues: ArrayBuffer[LConfigValueString] = new ArrayBuffer[LConfigValueString]) {

  override def toString() {
    val out = new StringBuilder()
    out.
    ("Node %s with numbers [", name)
    var isFirst = true
    for (numberValue <- numberValues) {
      if (isFirst)
        printf("[%s = %d]", numberValue.name, numberValue.value)
      else
        printf(",[%s = %d]", numberValue.name, numberValue.value)
      isFirst = false
    }
    isFirst = true
    for (stringValue <- stringValues) {
      if (isFirst)
        printf("[%s = %s]", stringValue.name, stringValue.value)
      else
        printf(",[%s = %s]", stringValue.name, stringValue.value)
      isFirst = false
    }
    isFirst = true
    for (childNode <- children) {
      if (isFirst)
        printf("[%s]", childNode.)
      else
        printf(",[%s = %s]", numberValue.name, numberValue.value)
      isFirst = false
    }
  }

}

class LConfigValueString(var name: String, var value: String)

class LConfigValueNumber(var name: String, var value: Double)