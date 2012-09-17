package org.zenmode.commands.cmd.seq

import org.zenmode.commands.result.Result
import org.zenmode.commands.cmd.Executable

class FailFastCmdSeq(cmds: Executable*) extends CmdSeq(cmds: _*) {
  override protected def onCmdExecuted(result: Result) =
    result.succeeded
}

object FailFastCmdSeq {
  def apply(cmds: Executable*): FailFastCmdSeq =
    new FailFastCmdSeq(cmds: _*)
}
