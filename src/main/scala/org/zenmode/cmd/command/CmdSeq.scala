package org.zenmode.cmd.command

import org.zenmode.cmd.executor.Executor
import org.zenmode.cmd.result.ResultSeq

class CmdSeq(cmds: Executable*) extends Executable {
  def execute(implicit executor: Executor) = {
    val results = cmds map (_.execute)
    ResultSeq(results)
  }

  def unexecute(implicit executor: Executor) =
    Some(ResultSeq(unexecuteInReverse))

  private def unexecuteInReverse(implicit executor: Executor) =
    cmds.reverse.flatMap(_.unexecute)
}

object CmdSeq {
  def apply(cmds: Executable*): CmdSeq = new CmdSeq(cmds: _*)
}
