package ingestion

import ingestion.http.HttpClient
import org.apache.spark.sql.{DataFrame, SparkSession}
import org.slf4j.LoggerFactory
import transformation.PartitionTransformer

import java.time.LocalDate

class OpenMeteoApiReader(httpClient: HttpClient) {
  private val logger = LoggerFactory.getLogger(getClass)

  def read(apiUrl: String, executionDate: String = LocalDate.now().toString)(
    implicit spark: SparkSession
  ): DataFrame = {
    val jsonResponse = httpClient.get(apiUrl)
    logger.info(s"API Response: ${jsonResponse.take(100)}...")

    val jsonRDD = spark.sparkContext.parallelize(Seq(jsonResponse))

    PartitionTransformer.addDatePartitions(
      spark.read
        .json(jsonRDD),
      executionDate
    )
  }
}
