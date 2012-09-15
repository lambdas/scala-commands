package org.zenmode.cmd.executor

import org.zenmode.cmd.result.Result

trait Executor {
  def execute(
               appPath: String,
               args: Iterable[String],
               workDir: String,
               env: Iterable[(String, String)]
               ): Result
}
