package org.zenmode.commands.executor

import org.zenmode.commands.result.Result

trait Executor {
  def execute(
    appPath: String,
    args:    Iterable[String],
    workDir: String,
    env:     Map[String, String]
  ): Result
}
