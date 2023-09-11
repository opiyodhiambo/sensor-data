package adventure

import cloudflow.akkastream.AkkaStreamlet
import cloudflow.streamlets.StreamletShape
import cloudflow.streamlets.ConfigParameter
import cloudflow.akkastream.AkkaStreamletLogic
import cloudflow.streamlets.CodecInlet

class ValidMetricLogger extends AkkaStreamlet {
  // Defining the inlet  
  val inlet: CodecInlet[Metric] = AvroInlet[Metric]("in")
  // Defining the shalep of the streamlet
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
