package org.zenmode.cmd.command

import org.zenmode.cmd.executor.Executor
import org.zenmode.cmd.result.{Result, ResultSeq}

class CmdTransaction(cmds: Executable*) extends FailFastCmdSeq {

  override def execute(implicit executor: Executor) =
    ResultSeq(results = executeAndUnexecuteIfFailed(cmds))

  private def executeAndUnexecuteIfFailed(cmds: Seq[Executable])(implicit executor: Executor) = {
    val results = failFastExecute(cmds)
    results ++ unexecuteIfFailed(results)
  }

  private def unexecuteIfFailed(results: Seq[Result])(implicit executor: Executor) =
    if (ResultSeq(results).failed)
      unexecuteSucceededCommands(results)
    else
      Nil

  private def unexecuteSucceededCommands(results: Seq[Result])(implicit executor: Executor) = {
    val unexecuteSeq = CmdSeq(succeededCommands(results): _*)
    unexecuteSeq.unexecute.map(_.results).getOrElse(Nil)
  }

  private def succeededCommands(results: Seq[Result]) = {
    val executedCmdsCountExceptFailedOne = results.size - 1
    cmds.take(executedCmdsCountExceptFailedOne)
  }
}

object CmdTransaction {
  def apply(cmds: Executable*): CmdTransaction =
    new CmdTransaction(cmds: _*)
}
