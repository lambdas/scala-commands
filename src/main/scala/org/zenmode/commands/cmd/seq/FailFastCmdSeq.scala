package org.zenmode.commands.cmd.seq

import org.zenmode.commands.result.Result
import org.zenmode.commands.cmd.Executable
import org.zenmode.commands.executor.Executor

class FailFastCmdSeq(cmds: Executable*) extends CmdSeq(cmds: _*) {
  override protected def onCmdExecuted(result: Result)
                                      (implicit executor: Executor) =
    result.succeeded
}

object FailFastCmdSeq {
  def apply(cmds: Executable*): FailFastCmdSeq =
    new FailFastCmdSeq(cmds: _*)
}
