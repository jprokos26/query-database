package io.joern.scanners.c

import io.shiftleft.codepropertygraph.generated.nodes
import io.shiftleft.console.scan._

class MetricsTests extends QueryTestSuite {

  override def queryBundle = Metrics

  "find functions with too many parameters" in {
    queryBundle.tooManyParameters(4)(cpg).map(_.evidence) match {
      case List(List(method: nodes.Method)) =>
        method.name shouldBe "too_many_params"
      case _ => fail()
    }
  }

  "find functions with high cyclomatic complexity" in {
    queryBundle.tooHighComplexity(4)(cpg).map(_.evidence) match {
      case List(List(method: nodes.Method)) =>
        method.name shouldBe "high_cyclomatic_complexity"
      case _ => fail()
    }
  }

  "find functions that are long (in terms of line numbers)" in {
    queryBundle.tooLong(13)(cpg).map(_.evidence) match {
      case List(List(method: nodes.Method)) =>
        method.name shouldBe "func_with_many_lines"
      case _ => fail()
    }
  }

  "find functions with multiple returns" in {
    queryBundle.multipleReturns()(cpg).map(_.evidence) match {
      case List(List(method: nodes.Method)) =>
        method.name shouldBe "func_with_multiple_returns"
      case _ => fail()
    }
  }

  "find functions with high number of loops" in {
    queryBundle.tooManyLoops(3)(cpg).map(_.evidence) match {
      case List(List(method: nodes.Method)) =>
        method.name shouldBe "high_number_of_loops"
      case _ => fail()
    }
  }

  "find deeply nested functions" in {
    queryBundle.tooNested(2)(cpg).map(_.evidence) match {
      case List(List(method: nodes.Method)) =>
        method.name shouldBe "func_with_nesting_level_of_3"
      case _ => fail()
    }
  }

}
