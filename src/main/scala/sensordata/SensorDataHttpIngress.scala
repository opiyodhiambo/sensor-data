package sensordata

import akka.http.scaladsl.marshallers.sprayjson.SprayJsonSupport._
import cloudflow.akkastream._
import cloudflow.akkastream.util.scaladsl._
import cloudflow.streamlets._
import cloudflow.streamlets.avro._
import SensorDataJsonSupport._

class SensorDataHttpIngress extends AkkaServerStreamlet {
  // An outlet for emitting data from the streamlet. Data of type SensorData
  val out: CodecOutlet[SensorData] =
    AvroOutlet[SensorData]("out").withPartitioner(RoundRobinPartitioner)
  // Defining the shape of the streamlet with just one outlet, "out"
  override def shape(): StreamletShape = StreamletShape.withOutlets(out)
  // Defining the logic of the streamlet, listening for incoming http requestst and routing them to thre specified outlet
  override def createLogic(): AkkaStreamletLogic =
    HttpServerLogic.default(this, out)

}
