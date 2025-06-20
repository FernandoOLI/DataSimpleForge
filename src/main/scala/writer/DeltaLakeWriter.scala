package writer

import org.apache.spark.sql.DataFrame

object DeltaLakeWriter {
  def write(df: DataFrame, path: String, partitionCols: Seq[String]): Unit = {
    val writer = df.write
      .format("delta")
      .mode("overwrite")

    val finalWriter = if (partitionCols.nonEmpty) {
      writer.partitionBy(partitionCols: _*)
    } else {
      writer
    }

    finalWriter.save(path)
  }
}
