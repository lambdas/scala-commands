package org.zenmode.cmd.command

import org.zenmode.cmd.executor.Executor
import org.zenmode.cmd.result.{Result, ResultSequence}

class CommandTransaction(cmds: Executable*) extends FailFastCommandSequence {

  override def execute(implicit executor: Executor) =
    ResultSequence(results = executeWithUnexecuteOnFault(cmds))

  private def executeWithUnexecuteOnFault(cmds: Seq[Executable])(implicit executor: Executor) = {
    val results = failFastExecute(cmds)
    results ++ unexecuteIfFailed(results)
  }

  private def unexecuteIfFailed(results: Seq[Result])(implicit executor: Executor) =
    if (ResultSequence(results).failed)
      unexecuteSucceededCommands(results)
    else
      Nil

  private def unexecuteSucceededCommands(results: Seq[Result])(implicit executor: Executor) = {
    val unexecuteSeq = CommandSequence(succeededCommands(results): _*)
    unexecuteSeq.unexecute.map(_.results).getOrElse(Nil)
  }

  private def succeededCommands(results: Seq[Result]) = {
    val executedCmdsCountExceptFailedOne = results.size - 1
    cmds.take(executedCmdsCountExceptFailedOne)
  }
}

object CommandTransaction {
  def apply(cmds: Executable*): CommandTransaction =
    new CommandTransaction(cmds: _*)
}
