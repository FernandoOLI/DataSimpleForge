package utils

import org.apache.spark.sql.SparkSession

object Spark {
  def createSparkSession(env: String): SparkSession = {
    val builderSpark = SparkSession
      .builder()
      .appName("OpenMeteo API to Delta")
      .config("spark.sql.extensions", "io.delta.sql.DeltaSparkSessionExtension")
      .config(
        "spark.sql.catalog.spark_catalog",
        "org.apache.spark.sql.delta.catalog.DeltaCatalog"
      )
    if (env == "local") {
      builderSpark.master("local[*]")
    }
    builderSpark.getOrCreate()
  }
}
