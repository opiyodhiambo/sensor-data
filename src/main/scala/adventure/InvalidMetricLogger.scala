package adventure

import cloudflow.akkastream.AkkaStreamlet
import cloudflow.akkastream.AkkaStreamletLogic

class InvalidMetricLogger extends AkkaStreamlet {
  val inlet = ???
  val shape = ???
  val flow = ???

  override protected def createLogic(): AkkaStreamletLogic = ???

}
