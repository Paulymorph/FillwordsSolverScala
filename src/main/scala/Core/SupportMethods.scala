package Core

import java.io.File

object SupportMethods {
  def readFile(filePath: File): Iterable[String] = {
    val inputStream = io.Source.fromFile(filePath.getCanonicalPath)
    val lines = (for (line <- inputStream.getLines()) yield line).toArray
    inputStream.close
    lines
  }
}
