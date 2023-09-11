package adventure

import cloudflow.akkastream._
import cloudflow.streamlets._
import cloudflow.streamlets.avro.AvroInlet
import cloudflow.akkastream.scaladsl.FlowWithCommittableContext

class MetricsValidation extends AkkaStreamlet {
  val in: CodecInlet[Metric] = AvroInlet[Metric]("in")
  val valid: CodecOutlet[InvalidMetric] = AvroInlet[Metric].withPartitioner(RoundRobinPartitioner)
  val invalid: CodecOutlet[Metric] = AvroInlet[InvalidMetric].withPartitioner(metric => metric.metric.deviceId.toString)

  override val shape: StreamletShape = StreamletShape(in).withOutlets(valid, invalid)

  def flow = {
    FlowWithCommittableContext[Metric]()
      .map{metric =>
        if (!SensorDataUtils.isInvalidMetric(metric)) Left(InvalidMetric(metric, "All measurements have to be positive"))
        else Right(metric)
      
    }
  }
  override protected def createLogic(): AkkaStreamletLogic = ???
}