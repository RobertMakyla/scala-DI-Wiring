package wiring


//source : https://www.codeproject.com/Articles/1057967/Scala-Dependency-Injection-IoC

/**
 * 
 * Prod
 * - pure scala, nice usage of trait, type-checked at compile-time
 * 
 * Cons
 * - lots or boilerplate (unnecessary code)
 */

object aca_CakePattern extends App {
  val app = new Top with RealProcessorComponent
  app.start()
}

// This trait is how you would express a dependency
// Any class that needs a Processor would mix in this trait
// along with using a self type to allow us to mixin either
// a mock / test double
trait ProcessorComponent {

  //abstract implementation, inheritors provide implementation
  val processor: Processor

  trait Processor {
    def process(): Unit
  }
}


// A Real Processor
trait RealProcessorComponent extends ProcessorComponent {
  val processor = new ActualProcessor()
  class ActualProcessor() extends Processor {
    def process(): Unit = {
      println("ActualProcessor")
    }
  }

}


// A Test Processor
trait TestProcessorComponent extends ProcessorComponent {
  val processor = new TestProcessor()
  class TestProcessor() extends Processor {
    def process(): Unit = {
      println("TestProcessor")
    }
  }

}


class Top {

  // NOTE : The self type that allows to
  // mixin and use a ProcessorComponent
  this: ProcessorComponent =>

  def start() {
    processor.process()
  }
}


