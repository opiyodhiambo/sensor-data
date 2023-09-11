package adventure

import cloudflow.akkastream.AkkaStreamlet
import cloudflow.streamlets.StreamletShape
import cloudflow.streamlets.ConfigParameter
import cloudflow.akkastream.AkkaStreamletLogic
import cloudflow.streamlets.CodecInlet
import cloudflow.streamlets.RegExpConfigParameter
import cloudflow.streamlets.StringConfigParameter
import cloudflow.akkastream.scaladsl.FlowWithCommittableContext
import cloudflow.akkastream.scaladsl.RunnableGraphStreamletLogic
import akka.stream.scaladsl.RunnableGraph

class ValidMetricLogger extends AkkaStreamlet {
  // Defining the inlet
  val inlet: CodecInlet[Metric] = AvroInlet[Metric]("in")
  // Defining the shape of the streamlet
  override val shape: StreamletShape = StreamletShape.withInlets(inlet)
  // Defining the log configs for the streamlet
  val LogLevel = RegExpConfigParameter(
    "log-level",
    "provide the following log levels, debug, info, warning or error",
    "^debug|info|warning|error$",
    Some("debug")
  )
  val MsgPrefix = StringConfigParameter(
    "msg-prefix",
    "Provide a prefix for the log lines",
    Some("valid-logger")
  )

  override def configParameters: IndexedSeq[ConfigParameter] =
    Vector(logLevel, msgPrefix)

  val logF: String => Unit = LogLevel.value.toLowerCase match {
    case "debug"   => system.log.debug _
    case "info"    => system.log.info _
    case "warning" => system.log.warning _
    case "error"   => system.log.error
  }
  val msgPrefix = MsgPrefix.value
  def log(metric: Metric): Unit =
    logF(s"$msgPrefix $metric")

  def flow = {
    FlowWithCommittableContext[Metric]()
      .map { validMetric =>
        log(validMetric)
        validMetric
      }
  }

  override protected def createLogic(): AkkaStreamletLogic = new RunnableGraphStreamletLogic() {
    override def runnableGraph(): RunnableGraph[_] = sourceWithCommittableContext(inlet).via(flow).to(committableSink)
  }
}
