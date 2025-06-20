package writer

import org.apache.spark.sql.DataFrame

object DeltaLakeWriter {
  def write(df: DataFrame, path: String, partitionCols: Seq[String]): Unit = {
    df.write
      .format("delta")
      .mode("overwrite")
      .partitionBy(partitionCols: _*)
      .save(path)
  }
}
