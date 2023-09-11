package adventure

import java.time.Instant
import java.util.UUID
import org.apache.avro.specific.SpecificRecordBase 
import org.apache.avro.{Schema, AvroRuntimeException}
import scala.util.Try
import com.sksamuel.avro4s.AvroSchema

import spray.json._

case class SensorData(deviceId: UUID, timestamp: Instant, measurements: Measurements) extends SpecificRecordBase {
  val schema: Schema = AvroSchema[SensorData]
  cal schemaJson: String = schema.toString

  def get(field: Int): Object = field match {
    case 0 => deviceId.asInstanceOf[AnyRef]
    case 1 => timestamp.asInstanceOf[AnyRef] 
    case 2 => measurements.asInstanceOf[AnyRef]
    case _ => throw new AvroRuntimeException("invalid field index")
  }
  def getSchema(): org.apache.avro.Schema = SensorData.SCHEMA$
  def put(field: Int, value: Object): Unit = ???
}
case class Measurements(power: Double, rotorSpeed: Double, windspeed: Double)

trait UUIDJsonSupport extends DefaultJsonProtocol {
  implicit object UUIDFormat extends JsonFormat[UUID] {
    def write(uuid: UUID) = JsString(uuid.toString)

    def read(json: JsValue): UUID = json match {
      case JsString(uuid) => Try(UUID.fromString(uuid)).getOrElse(deserializationError(s"Expected valid UUID but got '$uuid'."))
      case other          => deserializationError(s"Expected UUID as JsString, but got: $other")
    }
  }
}

trait InstantJsonSupport extends DefaultJsonProtocol {
  implicit object InstantFormat extends JsonFormat[Instant] {
    def write(instant: Instant) = JsNumber(instant.toEpochMilli)

    def read(json: JsValue): Instant = json match {
      case JsNumber(value) => Instant.ofEpochMilli(value.toLong)
      case other           => deserializationError(s"Expected Instant as JsNumber, but got: $other")
    }
  }
}

object MeasurementsJsonSupport extends DefaultJsonProtocol {
  implicit val measurementFormat = jsonFormat3(Measurements.apply)
}

object SensorDataJsonSupport extends DefaultJsonProtocol with UUIDJsonSupport with InstantJsonSupport {
  import MeasurementsJsonSupport._
  implicit val sensorDataFormat = jsonFormat3(SensorData.apply)
}
q9pn2c5
