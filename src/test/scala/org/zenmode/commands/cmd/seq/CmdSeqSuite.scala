package org.zenmode.commands.cmd.seq

import org.scalatest.{ParallelTestExecution, FunSuite}
import org.scalatest.matchers.ShouldMatchers
import org.mockito.Mockito._
import org.zenmode.commands.executor.Executor
import org.zenmode.commands.result.SingleResult
import org.zenmode.commands.cmd.Cmd

class CmdSeqSuite extends FunSuite with ShouldMatchers with ParallelTestExecution {

  implicit val executor = mock(classOf[Executor])

  val firstCmd = Cmd("ls") withUnexecute Cmd("unls")
  val secondCmd = Cmd("cd") withUnexecute Cmd("uncd")
  val cmdSeq = CmdSeq(firstCmd, secondCmd)

  test("Execution in order") {
    val order = inOrder(executor)

    cmdSeq.execute

    order.verify(executor).execute("ls", Nil, ".", Map.empty)
    order.verify(executor).execute("cd", Nil, ".", Map.empty)
  }

  test("Separate results") {
    val firstResult = SingleResult(0, "First output", "First errors")
    val secondResult = SingleResult(-1, "Second output", "Second result")

    when(executor.execute("ls", Nil, ".", Map.empty)).thenReturn(firstResult)
    when(executor.execute("cd", Nil, ".", Map.empty)).thenReturn(secondResult)

    val seqResult = cmdSeq.execute

    seqResult.results should equal(Seq(firstResult, secondResult))
  }

  test("Should unexecute commands in reverse order") {
    val order = inOrder(executor)

    cmdSeq.unexecute

    order.verify(executor).execute("uncd", Nil, ".", Map.empty)
    order.verify(executor).execute("unls", Nil, ".", Map.empty)
  }
}
