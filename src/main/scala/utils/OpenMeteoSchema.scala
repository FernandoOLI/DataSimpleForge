package utils

import org.apache.spark.sql.types._

object OpenMeteoSchema {
  val openMeteoSchema: StructType = StructType(
    Seq(
      StructField("elevation", DoubleType, nullable = true),
      StructField("generationtime_ms", DoubleType, nullable = true),
      StructField(
        "hourly",
        StructType(
          Seq(
            StructField(
              "temperature_2m",
              ArrayType(DoubleType, containsNull = true),
              nullable = true
            ),
            StructField(
              "time",
              ArrayType(StringType, containsNull = true),
              nullable = true
            )
          )
        ),
        nullable = true
      ),
      StructField(
        "hourly_units",
        StructType(
          Seq(
            StructField("temperature_2m", StringType, nullable = true),
            StructField("time", StringType, nullable = true)
          )
        ),
        nullable = true
      ),
      StructField("latitude", DoubleType, nullable = true),
      StructField("longitude", DoubleType, nullable = true),
      StructField("timezone", StringType, nullable = true),
      StructField("timezone_abbreviation", StringType, nullable = true),
      StructField("utc_offset_seconds", LongType, nullable = true),
      StructField("year", IntegerType, nullable = false),
      StructField("month", IntegerType, nullable = false),
      StructField("day", IntegerType, nullable = false)
    )
  )
}
