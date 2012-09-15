package org.zenmode.cmd.executor

import sys.process.Process
import java.io.File
import org.zenmode.cmd.util.SimpleProcessLogger
import org.zenmode.cmd.result.SingleResult

class LocalExecutor extends Executor {
  override def execute(
    appPath: String,
    args: Iterable[String] = Nil,
    workDir: String = LocalExecutor.currentDir,
    env: Iterable[(String, String)] = Nil
  ) = {
    val logger = new SimpleProcessLogger
    val process = Process(
      appPath :: args.toList,
      new File(workDir),
      env.toSeq: _*)
    val exitCode = process ! logger

    SingleResult(exitCode, logger.stdout, logger.stderr)
  }
}

object LocalExecutor {
  val currentDir = "."
}
