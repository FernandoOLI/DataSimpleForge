package ingestion

import org.apache.hc.client5.http.classic.methods.HttpGet
import org.apache.hc.client5.http.impl.classic.HttpClients
import org.apache.hc.core5.http.io.entity.EntityUtils
import org.apache.spark.sql.{DataFrame, SparkSession}
import org.slf4j.LoggerFactory
import transformation.PartitionTransformer

import java.time.LocalDate

object OpenMeteoApiReader {
  private val logger = LoggerFactory.getLogger(getClass)

  def read(apiUrl: String, executionDate: String = LocalDate.now().toString)(
      implicit spark: SparkSession
  ): DataFrame = {
    val jsonResponse = fetchApiData(apiUrl)

    val jsonRDD = spark.sparkContext.parallelize(Seq(jsonResponse))

    PartitionTransformer.addDatePartitions(
      spark.read
        .json(jsonRDD),
      executionDate
    )
  }

  private def fetchApiData(url: String): String = {
    logger.info(s"Get data from URL: $url")
    val client = HttpClients.createDefault()
    val get = new HttpGet(url)
    val response = client.execute(get)

    try {
      val entity = response.getEntity
      if (entity != null) {
        EntityUtils.toString(entity)
      } else {
        throw new RuntimeException("Resposta vazia da API Open-Meteo")
      }
    } finally {
      response.close()
    }
  }
}
