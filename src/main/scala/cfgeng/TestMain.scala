package cfgeng

import scala.io.Source
import cfgeng.LConfigLoader

object TestMain {

  def main(args: Array[String]) {
    val input = Source.fromResource("ErrorConfig.cfg")
    val config = LConfigLoader.loadConfigFromStream(input)
    printf("Error %d (%s) at %d:%d to %d:%d\n", config.error, LConfigLoaderError.getCode(config.error), config.errorToken.startLine, config.errorToken.startChar, config.errorToken.endLine, config.errorToken.endChar)
    config.print()
  }

}
