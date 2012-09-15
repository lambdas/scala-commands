package org.zenmode.cmd.command

import org.zenmode.cmd.executor.{Executor, LocalExecutor}
import org.zenmode.cmd.result.Result

case class Command(
  appPath: String,
  args: Iterable[String] = Nil,
  workDir: String = LocalExecutor.currentDir,
  env: Map[String, String] = Map.empty) extends Executable {

  override def execute(implicit executor: Executor): Result =
    executor.execute(appPath, args, workDir, env)

  def withArgs(additionalArgs: Iterable[String]) =
    copy(args = args ++ additionalArgs)

  def withArg(additionalArg: String) =
    copy(args = args ++ Seq(additionalArg))

  def inDir(newWorkDir: String) =
    copy(workDir = newWorkDir)

  def withEnv(additionalEnv: Map[String, String]) =
    copy(env = env ++ additionalEnv)
}
