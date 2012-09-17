package org.zenmode.commands.result

case class SingleResult(
  exitCode: Int,
  stdout: Iterable[Byte],
  stderr: Iterable[Byte]) extends ResultBase

object SingleResult {
  def apply(
    exitCode: Int,
    stdout: String = "",
    stderr: String = ""
  ): SingleResult = SingleResult(exitCode, stdout.getBytes, stderr.getBytes)
}
