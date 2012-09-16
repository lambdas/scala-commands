package org.zenmode.cmd.command

import org.scalatest.{ParallelTestExecution, FunSuite}
import org.scalatest.matchers.ShouldMatchers
import org.mockito.Mockito._
import org.zenmode.cmd.executor.Executor
import org.zenmode.cmd.result.SingleResult

class FailFastCommandSequenceSuite extends FunSuite with ShouldMatchers with ParallelTestExecution {

  private implicit val executor = mock(classOf[Executor])

  private val firstCmd = Command("ls")
  private val secondCmd = Command("cd")
  private val cmdSeq = FailFastCommandSequence(firstCmd, secondCmd)
  private val successResult = SingleResult(0, "First output", "First errors")
  private val failResult = SingleResult(-1, "Second output", "Second result")

  test("Execution in order") {
    val order = inOrder(executor)

    when(executor.execute("ls", Nil, ".", Map.empty)).thenReturn(successResult)
    when(executor.execute("cd", Nil, ".", Map.empty)).thenReturn(successResult)

    cmdSeq.execute

    order.verify(executor, times(1)).execute("ls", Nil, ".", Map.empty)
    order.verify(executor, times(1)).execute("cd", Nil, ".", Map.empty)
  }

  test("Separate results") {
    when(executor.execute("ls", Nil, ".", Map.empty)).thenReturn(successResult)
    when(executor.execute("cd", Nil, ".", Map.empty)).thenReturn(failResult)

    val seqResult = cmdSeq.execute

    seqResult.results should equal (Seq(successResult, failResult))
  }

  test("Should stop execution on first fail") {
    when(executor.execute("ls", Nil, ".", Map.empty)).thenReturn(failResult)
    when(executor.execute("cd", Nil, ".", Map.empty)).thenReturn(successResult)

    cmdSeq.execute

    verify(executor, never).execute("cd", Nil, ".", Map.empty)
  }
}
