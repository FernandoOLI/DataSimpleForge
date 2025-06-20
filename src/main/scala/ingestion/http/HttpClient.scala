package ingestion.http

trait HttpClient {
  def get(url: String): String
}