package adventure

import cloudflow.akkastream.AkkaStreamlet
import cloudflow.streamlets.StreamletShape
import cloudflow.streamlets.ConfigParameter
import cloudflow.akkastream.AkkaStreamletLogic

class ValidMetricLogger extends AkkaStreamlet {
  val inlet = ???
  override val shape: StreamletShape = StreamletShape.withInlets(inlet)

  val logLevel = ???
  val MsgPrefix = ???

  override def configParameters: IndexedSeq[ConfigParameter] = ???

  val logF: String = ???
  val msgPrefix = ???
  def log = ???

  def flow = ???

  override protected def createLogic(): AkkaStreamletLogic = ???
}
