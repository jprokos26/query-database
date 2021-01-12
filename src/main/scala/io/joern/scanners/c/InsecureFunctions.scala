package io.joern.scanners.c

import io.joern.scanners._
import io.shiftleft.semanticcpg.language._
import io.shiftleft.console._

object InsecureFunctions extends QueryBundle {

  @q
  def getsUsed(): Query = Query(
    name = "call-to-gets",
    author = Crew.suchakra,
    title = "Insecure function gets() used",
    description =
      """
        | Avoid gets() function as it can lead to reads beyond buffer boundary and cause
        | buffer overflows. Some secure alternatives are fgets() and gets_s().
        |""".stripMargin,
    score = 8,
    traversal = { cpg =>
      cpg.call("gets")
    }
  )

  @q
  def argvUsedInPrintf(): Query = Query(
    name = "format-controlled-printf",
    author = Crew.suchakra,
    title = "Function printf(), sprintf() or vsprintf() used insecurely",
    description =
      """
        | Avoid user controlled format strings like "argv" in printf, sprintf and vsprintf 
        | functions as they can cause buffer overflows. Some secure alternatives are 
        | snprintf() and vsnprintf().
        |""".stripMargin,
    score = 4,
    traversal = { cpg =>
      cpg
        .call("printf")
        .whereNot(_.argument.order(1).isLiteral) ++
        cpg
          .call("(sprintf|vsprintf)")
          .whereNot(_.argument.order(2).isLiteral)
    }
  )

  @q
  def scanfUsed(): Query = Query(
    name = "call-to-scanf",
    author = Crew.suchakra,
    title = "Insecure function scanf() used",
    description =
      """
        | Avoid scanf() function as it can lead to reads beyond buffer boundary and cause
        | buffer overflows. A secure alternative is fgets().
        |""".stripMargin,
    score = 4,
    traversal = { cpg =>
      cpg.call("scanf")
    }
  )

  @q
  def strcatUsed(): Query = Query(
    name = "call-to-strcat",
    author = Crew.suchakra,
    title = "Insecure functions strcat() or strncat() used",
    description =
      """
        | Avoid strcat() or strncat() functions. These can be used insecurely
        | causing non null-termianted strings leading to memory corruption.
        | A secure alternative is strcat_s().
        |""".stripMargin,
    score = 4,
    traversal = { cpg =>
      cpg.call("(strcat|strncat)")
    }
  )

  @q
  def strcpyUsed(): Query = Query(
    name = "call-to-strcpy",
    author = Crew.suchakra,
    title = "Insecure functions strcpy() or strncpy() used",
    description =
      """
        | Avoid strcpy() or strncpy() function. strcpy() does not check buffer lengths. 
        | A possible mitigation could be strncpy() which could prevent buffer overflows
        | but does not null-termiante strings leading to memory corruption. A secure 
        | alternative (on BSD) is strlcpy().
        |""".stripMargin,
    score = 4,
    traversal = { cpg =>
      cpg.call("(strcpy|strncpy)")
    }
  )

  @q
  def strtokUsed(): Query = Query(
    name = "call-to-strtok",
    author = Crew.suchakra,
    title = "Insecure function strtok() used",
    description =
      """
        | Avoid strtok() function as it modifies the original string in place and appends
        | a null character after each token. This makes the original string unsafe.
        | Suggested alternative is strtok_r() with saveptr.
        |""".stripMargin,
    score = 4,
    traversal = { cpg =>
      cpg.call("strtok")
    }
  )

  @q
  def getwdUsed(): Query = Query(
    name = "call-to-getwd",
    author = Crew.claudiu,
    title = "Insecure function getwd() used",
    description =
      """
        | Avoid the getwd() function, it does not check buffer lengths.
        | Use getcwd() instead, as it checks the buffer size.
        |""".stripMargin,
    score = 4,
    traversal = { cpg =>
      cpg.call("getwd")
    }
  )

}
