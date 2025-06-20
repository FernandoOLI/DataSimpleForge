package ingestion

import org.apache.hc.client5.http.classic.methods.HttpGet
import org.apache.hc.client5.http.impl.classic.HttpClients
import org.apache.hc.core5.http.io.entity.EntityUtils
import org.apache.spark.sql.{DataFrame, SparkSession}
import transformation.PartitionTransformer

import java.time.LocalDate

object OpenMeteoApiReader {
  def read(apiUrl: String)(implicit spark: SparkSession): DataFrame = {
    val jsonResponse = fetchApiData(apiUrl)

    val jsonRDD = spark.sparkContext.parallelize(Seq(jsonResponse))

    PartitionTransformer.addDatePartitions(
      spark.read
        .json(jsonRDD),
      LocalDate.now().toString
    )
  }

  private def fetchApiData(url: String): String = {
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
