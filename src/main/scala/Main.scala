import org.apache.hc.client5.http.classic.methods.HttpGet
import org.apache.hc.client5.http.impl.classic.HttpClients
import org.apache.hc.core5.http.io.entity.EntityUtils
import org.apache.spark.sql.functions.lit
import org.apache.spark.sql.{DataFrame, SparkSession}

import java.time.LocalDate
object Main {
  def main(args: Array[String]): Unit = {

    val spark = createSparkSession()
    val apiUrl = "https://api.open-meteo.com/v1/forecast?latitude=-23.55&longitude=-46.63&hourly=temperature_2m"

    val jsonResponse = fetchApiData(apiUrl)

    val jsonRDD = spark.sparkContext.parallelize(Seq(jsonResponse))

    val today = LocalDate.now()
    val year = today.getYear
    val month = today.getMonthValue
    val day = today.getDayOfMonth

    val df =  spark.read.json(jsonRDD).withColumn("year", lit(year))
      .withColumn("month", lit(month))
      .withColumn("day", lit(day))
    println("Mostrando os campos principais do JSON:")
    df.printSchema()
    df.show(false)

    val outputPath = "s3://personal-project-fernando-oliveira-pereira/data/"

    df.write
      .format("delta")
      .mode("append")
      .partitionBy("year", "month", "day")
      .save(outputPath)

    println(s"Dados gravados com sucesso no Delta: $outputPath")

    spark.stop()
  }

  def createSparkSession(): SparkSession = {
//    SparkSession.builder()
//      .appName("OpenMeteo API to Delta")
//      .master("local[*]")
//      .config("spark.sql.extensions", "io.delta.sql.DeltaSparkSessionExtension")
//      .config("spark.sql.catalog.spark_catalog", "org.apache.spark.sql.delta.catalog.DeltaCatalog")
//      .getOrCreate()

    SparkSession.builder()
      .appName("OpenMeteo API to Delta")
      .config("spark.sql.extensions", "io.delta.sql.DeltaSparkSessionExtension")
      .config("spark.sql.catalog.spark_catalog", "org.apache.spark.sql.delta.catalog.DeltaCatalog")
      .getOrCreate()
  }
  def fetchApiData(url: String): String = {
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
