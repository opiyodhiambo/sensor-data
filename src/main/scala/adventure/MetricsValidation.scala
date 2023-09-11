package adventure

import cloudflow.akkastream._
import cloudflow.streamlets._
import cloudflow.streamlets.avro.AvroInlet
import cloudflow.akkastream.scaladsl.FlowWithCommittableContext
import cloudflow.akkastream.scaladsl.RunnableGraphStreamletLogic
import akka.stream.scaladsl.RunnableGraph
import cloudflow.akkastream.util.scaladsl.Splitter

class MetricsValidation extends AkkaStreamlet {
  val in: CodecInlet[Metric] = AvroInlet[Metric]("in")
  val valid: CodecOutlet[InvalidMetric] = AvroOutlet[Metric].withPartitioner(RoundRobinPartitioner)
  val invalid: CodecOutlet[Metric] = AvroOutlet[InvalidMetric].withPartitioner(metric => metric.metric.deviceId.toString)

  override val shape: StreamletShape = StreamletShape(in).withOutlets(valid, invalid)

  def flow = {
    FlowWithCommittableContext[Metric]()
      .map{metric =>
        if (!SensorDataUtils.isValidMetric(metric)) Left(InvalidMetric(metric, "All measurements have to be positive"))
        else Right(metric)
      
    }
  }
  override protected def createLogic(): AkkaStreamletLogic = new RunnableGraphStreamletLogic() {
    override def runnableGraph(): RunnableGraph[_] = sourceWithCommittableContext(in).to(Splitter.sink(flow, valid, invalid))
  }
}
