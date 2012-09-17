package org.zenmode.commands.cmd

import org.zenmode.commands.executor.Executor
import org.zenmode.commands.result.Result

trait Executable {
  def execute(implicit executor: Executor): Result
  def unexecute(implicit executor: Executor): Option[Result]
}
