package org.zenmode.commands.result

import org.scalatest.{ParallelTestExecution, FunSuite}
import org.scalatest.matchers.ShouldMatchers
import ExitCodes._

class SingleResultSuite extends FunSuite with ShouldMatchers with ParallelTestExecution {

  test("Result with exit code equal to EXIT_CODE_STATUS is succeeded") {
    val result = SingleResult(exitCode = success)
    result.succeeded should be(true)
    result.failed should be(false)
  }

  test("Result with exit code other than EXIT_CODE_STATUS is failed") {
    val someErrorCode = -1
    val result = SingleResult(exitCode = someErrorCode)
    result.succeeded should be(false)
    result.failed should be(true)
  }

  test("Stdout as string") {
    val sampleStdout = "Sample output"
    val result = SingleResult(0, sampleStdout)
    result.stdoutAsString should equal(sampleStdout)
  }

  test("Stderr as string") {
    val sampleStderr = "Sample error output"
    val result = SingleResult(0, "", sampleStderr)
    result.stderrAsString should equal(sampleStderr)
  }
}
