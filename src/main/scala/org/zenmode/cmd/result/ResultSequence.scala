package org.zenmode.cmd.result

import ExitCodes._

case class ResultSequence(results: Iterable[Result]) extends ResultBase {

  override lazy val exitCode =
    if (allResultsSucceeded)
      success
    else
      fail

  override lazy val stdout = results.flatMap(_.stdout)

  override lazy val stderr = results.flatMap(_.stderr)

  private lazy val allResultsSucceeded =
    results.map(_.exitCode).forall(success ==)

}
