package adventure

import cloudflow.akkastream._
import cloudflow.streamlets._

class MetricsValidation extends AkkaStreamlet {
  val in: CodecInlet[Metric] = ???
  val valid: CodecOutlet[InvalidMetric] = ???
  val invalid: CodecOutlet[Metric] = ???

  override val shape: StreamletShape = ???

  def flow = ???
  override protected def createLogic(): AkkaStreamletLogic = ???
}
