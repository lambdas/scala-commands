package org.zenmode.cmd.result

case class ResultSequence(results: Iterable[Result]) extends ResultBase {
  def exitCode = 0

  def stdout = null

  def stderr = null

  def succeeded = false
}
