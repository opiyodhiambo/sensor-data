package adventure

import cloudflow.akkastream.AkkaStreamlet
import cloudflow.streamlets.StreamletShape
import cloudflow.streamlets.ConfigParameter
import cloudflow.akkastream.AkkaStreamletLogic
import cloudflow.streamlets.CodecInlet
import cloudflow.streamlets.RegExpConfigParameter
import cloudflow.streamlets.StringConfigParameter

class ValidMetricLogger extends AkkaStreamlet {
  // Defining the inlet  
  val inlet: CodecInlet[Metric] = AvroInlet[Metric]("in")
  // Defining the shape of the streamlet
  override val shape: StreamletShape = StreamletShape.withInlets(inlet)
 // Defining the log configs for the streamlet
  val logLevel = RegExpConfigParameter(
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

  override def configParameters: IndexedSeq[ConfigParameter] = ???

  val logF: String = ???
  val msgPrefix = ???
  def log = ???

  def flow = ???

  override protected def createLogic(): AkkaStreamletLogic = ???
}
