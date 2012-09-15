package org.zenmode.cmd.command

import org.scalatest.{ParallelTestExecution, FunSuite}
import org.scalatest.matchers.ShouldMatchers
import org.mockito.Mockito._
import org.zenmode.cmd.executor.Executor

class CommandSuite extends FunSuite with ShouldMatchers with ParallelTestExecution {

  test("With arguments") {
    val cmd = Command("ls", Seq("-l")) withArgs Seq("-a", "/usr/bin")
    cmd.args should equal(Seq("-l", "-a", "/usr/bin"))
  }

  test("With argument") {
    val cmd = Command("ls", Seq("-l")) withArg "/usr/bin"
    cmd.args should equal(Seq("-l", "/usr/bin"))
  }

  test("In directory") {
    val cmd = Command("ls") inDir "/usr/bin"
    cmd.workDir should equal("/usr/bin")
  }

  test("With environment") {
    val cmd = Command("ls", env = Map("SOME_FLAG" -> "Some value")) withEnv Map("ANOTHER_FLAG" -> "Another value")
    cmd.env should equal(Map("SOME_FLAG" -> "Some value", "ANOTHER_FLAG" -> "Another value"))
  }

  test("Executing") {
    implicit val executor = mock(classOf[Executor])
    val cmd = Command("pwd") inDir "/usr/bin" withArg "-P"

    cmd.execute

    verify(executor).execute("pwd", Seq("-P"), "/usr/bin", Map.empty)
  }
}
