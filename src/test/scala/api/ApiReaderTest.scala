package api

import ingestion.http.{ApiReader, HttpClient}
import org.apache.spark.sql.SparkSession
import org.scalatest.funsuite.AnyFunSuite
import org.mockito.Mockito._
import org.mockito.ArgumentMatchers._
import utils.Spark

class ApiReaderTest extends AnyFunSuite {

  implicit val spark: SparkSession = Spark.createSparkSession("local")

  test("ApiReader should parse JSON from mocked HTTP client") {

    val fakeJsonResponse =
      """
        |{
        |  "latitude": -23.5,
        |  "longitude": -46.6,
        |  "hourly": {
        |    "time": ["2025-06-21T00:00"],
        |    "temperature_2m": [15.2]
        |  }
        |}
        |""".stripMargin

    // ✅ Criar o mock
    val httpClientMock = mock(classOf[HttpClient])
    when(httpClientMock.get(any[String])).thenReturn(fakeJsonResponse)

    val apiReader = new ApiReader(httpClientMock)

    val df = apiReader.readApi("https://fake-url.com")

    assert(df.count() == 1)
    assert(df.columns.contains("latitude"))
    assert(df.columns.contains("hourly"))

    spark.stop()
  }
}
