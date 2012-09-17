package org.zenmode.commands.result

import ExitCodes._

trait ResultBase extends Result {

  def succeeded = exitCode == success

  def failed = !succeeded

  def stdoutAsString: String = stdout

  def stderrAsString: String = stderr

  private implicit def bytesToString(bytes: Iterable[Byte]): String =
    new String(bytes.toArray)
}
