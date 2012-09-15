package org.zenmode.cmd.result

case class SimpleResult(
  exitCode: Int,
  stdout: Iterable[Byte],
  stderr: Iterable[Byte]) extends ResultBase {

  import SimpleResult._

  def succeeded = exitCode == successExitCode
}

object SimpleResult {
  val successExitCode = 0

  def apply(
    exitCode: Int,
    stdout: String = "",
    stderr: String = ""
  ): SimpleResult = SimpleResult(exitCode, stdout.getBytes, stderr.getBytes)
}
