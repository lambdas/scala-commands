package org.zenmode.commands.executor

import org.scalatest.{ParallelTestExecution, FunSuite}
import org.scalatest.matchers.ShouldMatchers

class LocalExecutorSuite extends FunSuite with ShouldMatchers with ParallelTestExecution {

  private val executor = new LocalExecutor

  test("Success exit code") {
    val result = executor.execute("ls")
    result.succeeded should be(true)
  }

  test("Error exit code") {
    val result = executor.execute("ls", Seq("nonExistingDir"))
    result.failed should be(true)
  }

  test("Stdout") {
    val result = executor.execute("expr", Seq("1", "+", "2"))
    result.stdoutAsString should be("3")
  }

  test("Stderr") {
    val result = executor.execute("ls", Seq("nonExistingDir"))
    result.stderrAsString should include("No such file or directory")
  }

  test("Work directory") {
    var workDir = "/usr/bin"
    val result = executor.execute("pwd", Nil, workDir)
    result.stdoutAsString should include(workDir)
  }

  test("Environment variables") {
    var varName = "TEST_VAR"
    var varValue = "Test value"
    val env = Map(varName -> varValue)
    val result = executor.execute("env", Nil, LocalExecutor.currentDir, env)
    result.stdoutAsString should include("%s=%s" format(varName, varValue))
  }
}
