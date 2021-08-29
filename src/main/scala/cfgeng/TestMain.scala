package cfgeng

import scala.io.Source

object TestMain {

  def main(args: Array[String]): Unit = {
    val input = Source.fromResource("ExampleConfig.cfg")
    val config = LConfigLoader.loadConfigFromStream(input)
    config.print()
  }

}
