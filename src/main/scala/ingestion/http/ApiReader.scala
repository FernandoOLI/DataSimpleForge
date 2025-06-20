package ingestion.http

import org.apache.spark.sql.{DataFrame, SparkSession}

class ApiReader(httpClient: HttpClient) {

  def readApi(url: String)(implicit spark: SparkSession): DataFrame = {
    val jsonStr = httpClient.get(url)
    import spark.implicits._
    spark.read.json(Seq(jsonStr).toDS())
  }
}