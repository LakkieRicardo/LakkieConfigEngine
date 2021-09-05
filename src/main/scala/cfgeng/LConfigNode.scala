package cfgeng

import scala.collection.mutable.ArrayBuffer

class LConfigNode(var name: String, var children: ArrayBuffer[LConfigNode] = new ArrayBuffer[LConfigNode], var parentNode: LConfigNode = null, var numberValues: ArrayBuffer[LConfigValueNumber] = new ArrayBuffer[LConfigValueNumber], var stringValues: ArrayBuffer[LConfigValueString] = new ArrayBuffer[LConfigValueString]) {

  def findImmediateString(name: String): LConfigValueString = {
    val it = stringValues.iterator
    while (it.hasNext) {
      val next = it.next()
      if (next.name == name)
        return next
    }
    return null
  }

  def findImmediateNumber(name: String): LConfigValueNumber = {
    val it = numberValues.iterator
    while (it.hasNext) {
      val next = it.next()
      if (next.name == name)
        return next
    }
    return null
  }

  def findImmediateChildNode(name: String): LConfigNode = {
    val it = children.iterator
    while (it.hasNext) {
      val next = it.next()
      if (next.name == name)
        return next
    }
    return null
  }

  /**
    * Finds a child node
    *
    * @param path Dot-separated path to the node.
    * @return Child node as specified by the path.
    */
  def findChildNode(path: String): LConfigNode = {
    if (path == null)
      return null
    
    val trimmedPath = path.trim()
    if (trimmedPath.isEmpty())
      return null
    
    if (!trimmedPath.contains("."))
      return findImmediateChildNode(trimmedPath)
    
    // Recursively find the next child
    val separatorIndex = trimmedPath.indexOf('.')
    val rootNodeName = trimmedPath.substring(0, separatorIndex)
    val childNode = findImmediateChildNode(rootNodeName)
    if (childNode == null)
      return null
    
    return childNode.findChildNode(trimmedPath.substring(separatorIndex + 1))
  }

  def findString(path: String): LConfigValueString = {
    if (path == null)
      return null
    
    val trimmedPath = path.trim()
    if (trimmedPath.isEmpty())
      return null
    
    if (!trimmedPath.contains("."))
      return findImmediateString(trimmedPath)
    
    // Split the path into node location and string location
    val separatorIndex = trimmedPath.lastIndexOf('.')
    val stringValName = trimmedPath.substring(separatorIndex + 1)
    val nodeLocName = trimmedPath.substring(0, separatorIndex)

    val node = findChildNode(nodeLocName)
    if (node == null)
      return null
    
    return node.findImmediateString(stringValName)
  }

  def findNumber(path: String): LConfigValueNumber = {
    if (path == null)
      return null
    
    val trimmedPath = path.trim()
    if (trimmedPath.isEmpty())
      return null
    
    if (!trimmedPath.contains("."))
      return findImmediateNumber(trimmedPath)
    
    // Split the path into node location and string location
    val separatorIndex = trimmedPath.lastIndexOf('.')
    val stringValName = trimmedPath.substring(separatorIndex + 1)
    val nodeLocName = trimmedPath.substring(0, separatorIndex)

    val node = findChildNode(nodeLocName)
    if (node == null)
      return null
    
    return node.findImmediateNumber(stringValName)
  }

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