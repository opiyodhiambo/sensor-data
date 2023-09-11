package adventure
import akka.stream.scaladsl._
import cloudflow.akkastream.scaladsl._
import cloudflow.streamlets._
import cloudflow.akkastream._
import cloudflow.streamlets.avro._

class SensorDataMetrics extends AkkaStreamlet {
  val in: CodecOutlet[SensorData] = AvroInlet[SensorData]("in")
  val out: CodecOutlet[Metric] = AvroInlet[Metric]("out").withPartitioner(RoundRobinPartitioner)
  override def shape(): StreamletShape = 

  def flow = ???
  override protected def createLogic(): AkkaStreamletLogic = {
    override def runnableGraph[_] = ???
  }
}
