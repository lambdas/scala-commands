package org.zenmode.commands.util

import org.scalatest.{ParallelTestExecution, FunSuite}
import org.scalatest.matchers.ShouldMatchers

class SimpleProcessLoggerSuite extends FunSuite with ShouldMatchers with ParallelTestExecution {

  val logger = new SimpleProcessLogger

  test("Buffer should call argument and return result") {
    def someFun = 1
    logger.buffer(someFun) should equal (1)
  }

  test("Calls to out should accumulate in stdout") {
    logger.out("awe")
    logger.out("some")
    logger.stdout should equal ("awesome")
  }

  test("Calls to err should accumulate in stderr") {
    logger.err("awe")
    logger.err("some")
    logger.stderr should equal ("awesome")
  }
}
