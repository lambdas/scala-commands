package org.zenmode.cmd.command

import org.scalatest.{ParallelTestExecution, FunSuite}
import org.scalatest.matchers.ShouldMatchers
import org.mockito.Mockito._
import org.zenmode.cmd.executor.Executor

class CmdSuite extends FunSuite with ShouldMatchers with ParallelTestExecution {

  implicit val executor = mock(classOf[Executor])

  test("With arguments") {
    val cmd = Cmd("ls", Seq("-l")) withArgs Seq("-a", "/usr/bin")
    cmd.args should equal(Seq("-l", "-a", "/usr/bin"))
  }

  test("With argument") {
    val cmd = Cmd("ls", Seq("-l")) withArg "/usr/bin"
    cmd.args should equal(Seq("-l", "/usr/bin"))
  }

  test("In directory") {
    val cmd = Cmd("ls") inDir "/usr/bin"
    cmd.workDir should equal("/usr/bin")
  }

  test("With environment") {
    val cmd = Cmd("ls", env = Map("SOME_FLAG" -> "Some value")) withEnv
      Map("ANOTHER_FLAG" -> "Another value")
    cmd.env should equal(Map("SOME_FLAG" -> "Some value", "ANOTHER_FLAG" -> "Another value"))
  }

  test("With unexecute") {
    val unCmd = Cmd("rmdir") withArg "temp"
    val cmd = Cmd("mkdir") withArg "temp" withUnexecute unCmd

    cmd.unexecuteCmd should equal (Some(unCmd))
  }

  test("Executing") {
    val cmd = Cmd("pwd") inDir "/usr/bin" withArg "-P"

    cmd.execute

    verify(executor).execute("pwd", Seq("-P"), "/usr/bin", Map.empty)
  }

  test("Unexecuting") {
    val unCmd = Cmd("rmdir") withArg "temp"
    val cmd = Cmd("mkdir") withArg "temp" withUnexecute unCmd

    cmd.unexecute

    verify(executor).execute("rmdir", Seq("temp"), ".", Map.empty)
  }
}
