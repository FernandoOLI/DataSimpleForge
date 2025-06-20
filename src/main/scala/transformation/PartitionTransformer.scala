package transformation

import org.apache.spark.sql.{DataFrame, functions => F}
import org.slf4j.LoggerFactory

object PartitionTransformer {
  private val logger = LoggerFactory.getLogger(getClass)
  def addDatePartitions(df: DataFrame, executionDate: String): DataFrame = {
    logger.info(s"Adding partition columns based on executionDate: $executionDate")
    val date = java.time.LocalDate.parse(executionDate)

    df.withColumn("year", F.lit(date.getYear))
      .withColumn("month", F.lit(date.getMonthValue))
      .withColumn("day", F.lit(date.getDayOfMonth))
  }
}