package org.zenmode.cmd

import sys.process.Process
import java.io.File
import util.SimpleProcessLogger

class LocalExecutor {
  def execute(
    appPath: String,
    args:    Iterable[String] = Nil,
    workDir: String = LocalExecutor.currentDir,
    env:     Iterable[(String, String)] = Nil) = {

    val logger   = new SimpleProcessLogger
    val process = Process(
      appPath :: args.toList,
      new File(workDir),
      env.toSeq: _*)
    val exitCode = process ! logger

    SimpleResult(exitCode, logger.stdout, logger.stderr)
  }
}

object LocalExecutor {
  val currentDir = "."
}
