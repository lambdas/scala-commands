package org.zenmode.cmd.command

import org.zenmode.cmd.executor.Executor
import org.zenmode.cmd.result.ResultSequence

class CommandSequence(cmds: Executable*) extends Executable {
  def execute(implicit executor: Executor) = {
    val results = cmds map (_.execute)
    ResultSequence(results)
  }

  def unexecute(implicit executor: Executor) =
    Some(ResultSequence(unexecuteInReverse))

  private def unexecuteInReverse(implicit executor: Executor) =
    cmds.reverse.flatMap(_.unexecute)
}

object CommandSequence {
  def apply(cmds: Executable*): CommandSequence = new CommandSequence(cmds: _*)
}
