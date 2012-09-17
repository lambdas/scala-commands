package org.zenmode.commands.cmd.seq

import org.zenmode.commands.executor.Executor
import org.zenmode.commands.result.{ResultSeq, Result}
import org.zenmode.commands.cmd.Executable
import scala.Some

class CmdSeq(cmds: Executable*) extends Executable {

  /** Template method */
  def execute(implicit executor: Executor) =
    onSeqExecuted(ResultSeq(results = executeWithHooks))

  def unexecute(implicit executor: Executor) =
    Some(ResultSeq(unexecuteInReverse))

  /** Override and return false to stop execution */
  protected def onCmdExecuted(result: Result): Boolean =
    true

  /** Override and modify results to your needs */
  protected def onSeqExecuted(results: ResultSeq)(implicit executor: Executor): ResultSeq =
    results

  private def executeWithHooks(implicit executor: Executor) =
    executeCmdsWithHooks(cmds)

  private def executeCmdsWithHooks(cmds: Seq[Executable])(implicit executor: Executor): Seq[Result] =
    if (cmds.nonEmpty)
      executeNonEmptyCmdsWithHooks(cmds)
    else
      Nil

  private def executeNonEmptyCmdsWithHooks(cmds: Seq[Executable])(implicit executor: Executor): Seq[Result] =
    cmds.head.execute(executor) match {
      case res if !onCmdExecuted(res) => Seq(res)
      case res => Seq(res) ++ executeCmdsWithHooks(cmds.tail)
    }

  private def unexecuteInReverse(implicit executor: Executor) =
    cmds.reverse.flatMap(_.unexecute)
}

object CmdSeq {
  def apply(cmds: Executable*): CmdSeq = new CmdSeq(cmds: _*)
}
