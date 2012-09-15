package org.zenmode.cmd.command

import org.scalatest.{ParallelTestExecution, FunSuite}
import org.scalatest.matchers.ShouldMatchers
import org.mockito.Mockito._
import org.zenmode.cmd.executor.Executor
import org.zenmode.cmd.result.SimpleResult

class CommandSequenceSuite extends FunSuite with ShouldMatchers with ParallelTestExecution {

  implicit val executor = mock(classOf[Executor])

  val firstCmd = Command("ls")
  val secondCmd = Command("cd")
  val cmdSeq = CommandSequence(firstCmd, secondCmd)

  test("Execution in order") {
    val order = inOrder(executor)

    cmdSeq.execute

    order.verify(executor).execute("ls", Nil, ".", Map.empty)
    order.verify(executor).execute("cd", Nil, ".", Map.empty)
  }

  test("Separate exit codes") {
    val firstResult = SimpleResult(0, "First output", "First errors")
    val secondResult = SimpleResult(-1, "Second output", "Second result")

    when(executor.execute("ls", Nil, ".", Map.empty)).thenReturn(firstResult)
    when(executor.execute("cd", Nil, ".", Map.empty)).thenReturn(secondResult)

    val seqResult = cmdSeq.execute

    seqResult.results should equal (Seq(firstResult, secondResult))
  }
}
