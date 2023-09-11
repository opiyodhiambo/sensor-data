package adventure

import cloudflow.akkastream._
import cloudflow.streamlets._
import cloudflow.streamlets.avro.AvroInlet

class MetricsValidation extends AkkaStreamlet {
  val in: CodecInlet[Metric] = AvroInlet[Metric]("in")
  val valid: CodecOutlet[InvalidMetric] = AvroInlet[Metric].withPartitioner(RoundRobinPartitioner)
  val invalid: CodecOutlet[Metric] = AvroInlet[InvalidMetric].withPartitioner(metric => metric.metric.deviceId.toString)

  override val shape: StreamletShape = ???

  def flow = ???
  override protected def createLogic(): AkkaStreamletLogic = ???
}
