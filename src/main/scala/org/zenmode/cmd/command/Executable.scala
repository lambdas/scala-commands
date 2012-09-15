package org.zenmode.cmd.command

import org.zenmode.cmd.executor.Executor
import org.zenmode.cmd.result.Result

trait Executable {
  def execute(implicit executor: Executor): Result
}
