package org.zenmode.cmd.command

import org.zenmode.cmd.executor.{Executor, LocalExecutor}
import org.zenmode.cmd.result.Result

case class Cmd(
  appPath:      String,
  args:         Iterable[String]    = Nil,
  workDir:      String              = LocalExecutor.currentDir,
  env:          Map[String, String] = Map.empty,
  unexecuteCmd: Option[Cmd]     = None
) extends Executable {

  override def execute(implicit executor: Executor): Result =
    executor.execute(appPath, args, workDir, env)

  def unexecute(implicit executor: Executor): Option[Result] =
    unexecuteCmd.map(_.execute)

  def withArgs(additionalArgs: Iterable[String]) =
    copy(args = args ++ additionalArgs)

  def withArg(additionalArg: String) =
    copy(args = args ++ Seq(additionalArg))

  def inDir(newWorkDir: String) =
    copy(workDir = newWorkDir)

  def withEnv(additionalEnv: Map[String, String]) =
    copy(env = env ++ additionalEnv)

  def withUnexecute(newUnexecuteCmd: Cmd) =
    copy(unexecuteCmd = Some(newUnexecuteCmd))
}
