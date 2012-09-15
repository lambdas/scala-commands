package org.zenmode.cmd

case class SimpleResult(
  exitCode: Int,
  stdout:   Iterable[Byte],
  stderr:   Iterable[Byte]) {

  import SimpleResult._

  def succeeded = exitCode == successExitCode

  def failed = !succeeded

  def stdoutAsString: String = stdout

  def stderrAsString: String = stderr

  private implicit def bytesToString(bytes: Iterable[Byte]): String =
    new String(bytes.toArray)
}

object SimpleResult {
  val successExitCode = 0

  def apply(
    exitCode: Int,
    stdout:   String = "",
    stderr:   String = ""
  ): SimpleResult = SimpleResult(exitCode, stdout.getBytes, stderr.getBytes)
}
