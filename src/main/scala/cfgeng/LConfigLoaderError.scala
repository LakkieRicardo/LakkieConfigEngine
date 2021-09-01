package cfgeng

import scala.io.Source

object LConfigLoaderError {
  
    var initialized = false
    val errorCodes = scala.collection.mutable.Map[Int, String]()

    def initCodes() {
        val errorCodeLines = Source.fromResource("ErrorCodes.txt").getLines()
        for (errorCodeLine <- errorCodeLines) {
            if (!errorCodeLine.isEmpty && !errorCodeLine.startsWith("#")) {
                val separatorPos = errorCodeLine.indexOf(" ")
                if (separatorPos != -1) {
                    val errorCodeNumber = errorCodeLine.substring(0, separatorPos)
                    val errorCodeString = errorCodeLine.substring(separatorPos + 1)
                    errorCodes.put(errorCodeNumber.toInt, errorCodeString)
                }
            }
        }
    }

    def getCode(code: Int): String = {
        if (errorCodes.isEmpty) initCodes()
        return errorCodes.getOrElse[String](code, "Invalid error code")
    }

}
