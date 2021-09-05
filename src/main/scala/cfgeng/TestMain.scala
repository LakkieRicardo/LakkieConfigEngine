package cfgeng

import scala.io.Source
import cfgeng.LConfigLoader

object TestMain {

  def main(args: Array[String]) {
    val input = Source.fromResource("ExampleConfig.cfg")
    val config = LConfigLoader.loadConfigFromStream(input)
    println(config.findString("ns-a.ns-b.ns-c.ns-d.f").value)
  }

}
