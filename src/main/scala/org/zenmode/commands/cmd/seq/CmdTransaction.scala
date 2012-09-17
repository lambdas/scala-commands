package org.zenmode.commands.cmd.seq

import org.zenmode.commands.executor.Executor
import org.zenmode.commands.result.ResultSeq
import org.zenmode.commands.cmd.Executable

class CmdTransaction(cmds: Executable*) extends FailFastCmdSeq(cmds: _*) {

  override protected def onSeqExecuted(results: ResultSeq)(implicit executor: Executor) =
    results ++ unexecuteIfFailed(results)

  private def unexecuteIfFailed(results: ResultSeq)(implicit executor: Executor) =
    if (results.failed)
      unexecuteSucceededCommands(results)
    else
      ResultSeq(Nil)

  private def unexecuteSucceededCommands(results: ResultSeq)(implicit executor: Executor) = {
    val unexecuteSeq = CmdSeq(succeededCommands(results): _*)
    ResultSeq(unexecuteSeq.unexecute.map(_.results).getOrElse(Nil))
  }

  private def succeededCommands(results: ResultSeq) = {
    val executedCmdsCountExceptFailedOne = results.results.size - 1
    cmds.take(executedCmdsCountExceptFailedOne)
  }
}

object CmdTransaction {
  def apply(cmds: Executable*): CmdTransaction =
    new CmdTransaction(cmds: _*)
}
