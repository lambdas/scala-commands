package org.zenmode.cmd.result

trait Result {
  def exitCode: Int

  def stdout: Iterable[Byte]

  def stderr: Iterable[Byte]

  def succeeded: Boolean

  def failed: Boolean

  def stdoutAsString: String

  def stderrAsString: String
}
