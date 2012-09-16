package org.zenmode.cmd.command

import org.zenmode.cmd.executor.Executor
import org.zenmode.cmd.result.{Result, ResultSeq}

class FailFastCmdSeq(cmds: Executable*) extends CmdSeq {

  override def execute(implicit executor: Executor) =
    ResultSeq(results = failFastExecute(cmds))

  protected def failFastExecute(cmds: Seq[Executable])(implicit executor: Executor): Seq[Result] =
    if (cmds.nonEmpty)
      failFastExecuteNonEmpty(cmds)
    else
      Nil

  private def failFastExecuteNonEmpty(cmds: Seq[Executable])(implicit executor: Executor): Seq[Result] =
    cmds.head.execute(executor) match {
      case res if res.failed    => Seq(res)
      case res if res.succeeded => Seq(res) ++ failFastExecute(cmds.tail)
    }
}

object FailFastCmdSeq {
  def apply(cmds: Executable*): FailFastCmdSeq =
    new FailFastCmdSeq(cmds: _*)
}
