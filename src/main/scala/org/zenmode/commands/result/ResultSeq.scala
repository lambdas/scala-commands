package org.zenmode.commands.result

import ExitCodes._

case class ResultSeq(results: Iterable[Result]) extends ResultBase {

  override lazy val exitCode =
    if (allResultsSucceeded)
      success
    else
      fail

  override lazy val stdout = results.flatMap(_.stdout)

  override lazy val stderr = results.flatMap(_.stderr)

  def ++(other: ResultSeq) =
    ResultSeq(results ++ other.results)

  private lazy val allResultsSucceeded =
    results.map(_.exitCode).forall(success ==)
}

object ResultSeq {
  def apply(results: Result*): ResultSeq =
    ResultSeq(results)
}
