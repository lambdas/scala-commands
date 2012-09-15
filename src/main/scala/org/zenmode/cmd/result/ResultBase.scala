package org.zenmode.cmd.result

trait ResultBase extends Result {

  def failed = !succeeded

  def stdoutAsString: String = stdout

  def stderrAsString: String = stderr

  private implicit def bytesToString(bytes: Iterable[Byte]): String =
    new String(bytes.toArray)
}
