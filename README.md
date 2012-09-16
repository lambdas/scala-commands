# Scala Commands

## Overview

**Scala Commands** provides simple DSL for executing shell commands locally or through SSH. It is lightweight, extensible and fully tested. Enjoy ;)

## Installation

Coming soon.

## Single command execution

Easiest part, just tell **Scala Commands** what your command is:

    import org.zenmode.cmd.executor.LocalExecutor
    import org.zenmode.cmd.command.Cmd

    # Local executor sends commands to local shell
    implicit val executor = new LocalExecutor

    # Define your awesome command
    val ls = Cmd("ls") withArg "-la" inDir "/usr/bin"

    # Execute it
    val res = ls.execute

    # Enjoy results
    println(res.exitCode)
    println(res.stdoutAsString)
    println(res.stderrAsString)

## Combining commands in sequences

Usually you will execute several commands in row. In such cases you may want to combine your commands in to single entity, for easy management.

**Scala Commands** contains different command sequences:

* CmdSeq - Just plain sequence. Executes your commands one by one no matter what.
* FailFastCmdSeq - As one above, but stops execution if one of commands returns error code. Most times this is what you want.
* CmdTransaction - The most complicated yet most powerful sequence, will roll changes back if one of the commands fails.

Using it is simple and fun:

    import org.zenmode.cmd.command.{Cmd, FailFastCmdSeq}

    # Local executor sends commands to local shell
    implicit val executor = new LocalExecutor

    # Define scommand equence
    val prepareHomeStructure =
      FailFastCmdSeq(
        Cmd("mkdir") withArg "/home/paul",
        Cmd("mkdir") withArg "Books"  inDir "/home/paul",
        Cmd("mkdir") withArg "Music"  inDir "/home/paul",
        Cmd("touch") withArg "Readme" inDir "/home/paul"
      )

    # All ready for execution
    prepareHomeStructure.execute

## Collecting results

**Scala Commands** carefully collects results of all your commands, so you could fully manage execution flow. After command or sequence executed, library provides you with a `Result` instance. Let's inspect it:

    # Executing some command
    val result = cmd.execute

    # Easy way to check success
    if (result.succeeded)
      println("Ok")

    # Or handle failure
    if (result.failed)
      println("Failed")

    # You can easily access original command exit code
    val exitCode = result.exitCode

    # Command output as string
    val out = result.stdoutAsString

    # And even as raw bytes!
    val bytes = result.stdout

    # Errors goes to stderr
    val errors = result.stderrAsString

After sequence executed, you got `ResultSeq` which contains results from each of commands, unbelievable!

    # Executing some sequence
    val results = seq.execute

    # Is our sequence succeeded?
    if (results.succeeded) {
      # Of course it does
    }

    # Inspecting each of results
    results.map { result =>
      val exitCode = result.exitCode
      val out      = result.stdoutAsString
      val errors   = result.stderrAsString
    }

## Remote executing

**Scala Commands** could execute your commands through SSH transparently, just change executor!

    import org.zenmode.cmd.executor.SSHExecutor
    import org.zenmode.cmd.command.Cmd

    # As easy as change one line
    implicit val executor = new SSHExecutor("my-pc.at-work.com", "paul", "password")

    # And
    val retrieveForgottenDoc = Cmd("cat") withArg "prices.xml" inDir "/home/paul/Documents"

    # Your commands executes on remote host automatically
    val res = retrieveForgottenDoc.execute

    # Pure magic!
    val docIveForgot = res.stdoutAsString

## And there is more

Stay tuned.
