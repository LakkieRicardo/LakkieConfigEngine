package cfgeng

import scala.io.Source
import cfgeng.LConfigLoader

object TestMain {

  def main(args: Array[String]) {
    val input = Source.fromResource("ExampleConfig.cfg")
    val config = LConfigLoader.loadConfigFromStream(input)
    config.print()
  }

}
