package org.zenmode.cmd.command

import org.scalatest.{ParallelTestExecution, FunSuite}
import org.scalatest.matchers.ShouldMatchers
import org.mockito.Mockito._
import org.zenmode.cmd.executor.Executor
import org.zenmode.cmd.result.SingleResult

class CommandTransactionSuite extends FunSuite with ShouldMatchers with ParallelTestExecution {

  private implicit val executor = mock(classOf[Executor])

  private val firstCmd = Command("ls")
  private val secondUnCmd = Command("rmdir") withArg "test"
  private val secondCmd = Command("mkdir") withArg "test" withUnexecute secondUnCmd
  private val transaction = CommandTransaction(secondCmd, firstCmd)
  private val successResult = SingleResult(0, "First output", "First errors")
  private val failResult = SingleResult(-1, "Second output", "Second result")

  test("Should unexecute commands till fault") {
    when(executor.execute("mkdir", Seq("test"), ".", Map.empty)).thenReturn(successResult)
    when(executor.execute("ls", Nil, ".", Map.empty)).thenReturn(failResult)

    transaction.execute

    verify(executor).execute("rmdir", Seq("test"), ".", Map.empty)
  }
}
