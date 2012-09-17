package org.zenmode.commands.util

import sys.process.ProcessLogger

class SimpleProcessLogger extends ProcessLogger {
  var stdout = ""
  var stderr = ""

  override def buffer[T](f: => T): T = f

  override def out(s: => String) {
    stdout += s
  }

  override def err(s: => String) {
    stderr += s
  }
}
