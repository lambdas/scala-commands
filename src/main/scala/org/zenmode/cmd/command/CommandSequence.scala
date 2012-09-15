package org.zenmode.cmd.command

import org.zenmode.cmd.executor.Executor
import org.zenmode.cmd.result.ResultSequence

class CommandSequence(cmds: Executable*) extends Executable {
  def execute(implicit executor: Executor) = {
    val results = cmds map (_.execute)
    ResultSequence(results)
  }
}

object CommandSequence {
  def apply(cmds: Executable*): CommandSequence = new CommandSequence(cmds: _*)
}
