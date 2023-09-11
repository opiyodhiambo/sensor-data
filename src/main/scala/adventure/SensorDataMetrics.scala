package adventure
import akka.stream.scaladsl._
import cloudflow.akkastream.scaladsl._
import cloudflow.streamlets._
import cloudflow.akkastream._

class SensorDataMetrics extends AkkaStreamlet {
  val in: CodecOutlet[SensorData] = ???
  val out: CodecOutlet[Metric] = ???
  override def shape(): StreamletShape = ???

  def flow = ???
  override protected def createLogic(): AkkaStreamletLogic = {
    override def runnableGraph[_] = ???
  }
}
