package ingestion.http

import org.apache.hc.client5.http.classic.methods.HttpGet
import org.apache.hc.client5.http.impl.classic.HttpClients
import org.apache.hc.core5.http.io.entity.EntityUtils

class RealHttpClient extends HttpClient {
  override def get(url: String): String = {
    val client = HttpClients.createDefault()
    val get = new HttpGet(url)
    val response = client.execute(get)

    try {
      val entity = response.getEntity
      if (entity != null) {
        EntityUtils.toString(entity)
      } else {
        throw new RuntimeException("Empty response from API")
      }
    } finally {
      response.close()
    }
  }
}
