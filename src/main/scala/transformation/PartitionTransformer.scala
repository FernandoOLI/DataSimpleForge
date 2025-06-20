package transformation

import org.apache.spark.sql.{DataFrame, functions => F}

object PartitionTransformer {
  def addDatePartitions(df: DataFrame, executionDate: String): DataFrame = {
    val date = java.time.LocalDate.parse(executionDate)

    df.withColumn("year", F.lit(date.getYear))
      .withColumn("month", F.lit(date.getMonthValue))
      .withColumn("day", F.lit(date.getDayOfMonth))
  }
}