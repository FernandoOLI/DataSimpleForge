package quality

import org.apache.spark.sql.DataFrame
import org.apache.spark.sql.types._
import org.slf4j.LoggerFactory
import utils.OpenMeteoSchema

object SchemaValidator {

  private val logger = LoggerFactory.getLogger(getClass)

  private val expectedSchema: StructType = OpenMeteoSchema.openMeteoSchema

  def validate(df: DataFrame): Boolean = {
    logger.info("Starting schema validation...")

    if (!validateSchema(df)) {
      logger.error("Schema mismatch detected.")
      return false
    }

    if (!validateDataQuality(df)) {
      logger.error("Data quality checks failed.")
      return false
    }

    logger.info("DataFrame passed all validations.")
    true
  }

  private def validateSchema(df: DataFrame): Boolean = {
    val dfFields = df.schema.fields.map(f => (f.name, f.dataType)).toSet
    val expectedFields =
      expectedSchema.fields.map(f => (f.name, f.dataType)).toSet

    val missingFields = expectedFields.diff(dfFields)
    val extraFields = dfFields.diff(expectedFields)

    if (missingFields.nonEmpty) {
      logger.error(s"Missing fields: ${missingFields.mkString(", ")}")
      return false
    }

    if (extraFields.nonEmpty) {
      logger.warn(s"Extra unexpected fields: ${extraFields.mkString(", ")}")
    }

    true
  }

  private def validateDataQuality(df: DataFrame): Boolean = {
    val nullCounts = df
      .select(
        df.columns.map(c =>
          org.apache.spark.sql.functions
            .count(org.apache.spark.sql.functions.when(df(c).isNull, 1))
            .alias(c)
        ): _*
      )
      .collect()(0)

    val criticalColumns = Seq("latitude", "longitude", "year", "month", "day")

    val hasNulls =
      criticalColumns.exists(col => nullCounts.getAs[Long](col) > 0)

    if (hasNulls) {
      logger.error(s"Found nulls in critical columns: $criticalColumns")
      return false
    }

    true
  }
}
