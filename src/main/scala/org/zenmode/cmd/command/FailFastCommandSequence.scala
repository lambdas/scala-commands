package org.zenmode.cmd.command

import org.zenmode.cmd.executor.Executor
import org.zenmode.cmd.result.{Result, ResultSequence}

class FailFastCommandSequence(cmds: Executable*) extends CommandSequence {

  override def execute(implicit executor: Executor) =
    ResultSequence(results = failFastExecute(cmds))

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

object FailFastCommandSequence {
  def apply(cmds: Executable*): FailFastCommandSequence =
    new FailFastCommandSequence(cmds: _*)
}
