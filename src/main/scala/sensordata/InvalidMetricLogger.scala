package sensordata

import cloudflow.akkastream.AkkaStreamlet
import cloudflow.akkastream.AkkaStreamletLogic
import cloudflow.streamlets.CodecInlet
import cloudflow.streamlets.StreamletShape
import cloudflow.akkastream.scaladsl.FlowWithCommittableContext
import cloudflow.akkastream.scaladsl.RunnableGraphStreamletLogic
import akka.stream.scaladsl.RunnableGraph

class InvalidMetricLogger extends AkkaStreamlet {
  val inlet: CodecInlet[InvalidMetric] = CodecInlet[InvalidMetric]("inlet")
  override val shape: StreamletShape = StreamletShape.withInlets(inlet)
  val flow = FlowWithCommittableContext[InvalidMetric]()
    .map { invalidMetric =>
      system.log.warning("invalid metric detected", $invalidMetric)
      invalidMetric
    }

  override def createLogic(): AkkaStreamletLogic =
    new RunnableGraphStreamletLogic() {
      override def runnableGraph(): RunnableGraph[_] =
        sourceWithCommittableContext(inlet).via(flow).to(committableSink)
    }

}
