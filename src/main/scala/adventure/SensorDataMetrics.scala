package adventure
import akka.stream.scaladsl._
import cloudflow.akkastream.scaladsl._
import cloudflow.streamlets._
import cloudflow.akkastream._
import cloudflow.streamlets.avro._

class SensorDataMetrics extends AkkaStreamlet {
  val in: CodecOutlet[SensorData] = AvroInlet[SensorData]("in")
  val out: CodecOutlet[Metric] = AvroInlet[Metric]("out").withPartitioner(RoundRobinPartitioner)
  override def shape(): StreamletShape = StreamletShape(in, out)

  def flow = {
    FlowWithCommittableContext[SensorData]
      .mapConcat{ data =>
        List(
          Metric(data.deviceId, data.timestamp, "Power", data.measurements.power),
          Metric(data.deviceId, data.timestamp, "RotorSpeed", data.measurements.rotorSpeed),
          Metric(data.deviceId, data.timestamp, "WindSpeed", data.measurements.windspeed)
          )
      
    }
  }
  override protected def createLogic(): AkkaStreamletLogic = {
    override def runnableGraph[_] = ???
  }
}
