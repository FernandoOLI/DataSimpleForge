
---

#  DataSimpleForge - Data Ingestion Framework for Delta Lake on Databricks

Personal data engineering project by **Fernando Oliveira Pereira**, focused on ingesting public API data into Delta Lake using Apache Spark (Scala), with CI/CD deployment to Databricks.

---

## ✅ Project Status

| Topic                                  | Status                        |
| -------------------------------------- | ----------------------------- |
| Project structure organized            | ❌                             |
| Date parameterization                  | ❌                             |
| Professional logging                   | ❌                             |
| Data Quality Checks                    | ❌                             |
| Unit tests                             | ❌                             |
| Integration tests                      | ❌                             |
| Data Observability (metrics)           | ❌                             |
| CI/CD setup with GitHub Actions        | ✅ (basic build + upload done) |
| Deployment via Databricks Asset Bundle | ✅                             |
| Technical documentation                | ❌                             |

---

## 📌 Purpose

Ingest data from public APIs (starting with Open-Meteo) and store it in **Delta Lake**, partitioned by **year**, **month**, and **day**, with automated deployment using GitHub Actions and Databricks Asset Bundles.

---

## 📂 Project Structure

```
DataSimpleForge/
├── src/main/scala/br/dataforge/
├── test/
├── .github/workflows/
├── data_forge_asset/         # Databricks Asset Bundle
├── docs/                     # Future technical documentation
├── README.md
└── build.sbt
```

---

## ✅ Improvement Checklist

### 1) Project Organization

* [ ] Restructure Scala packages (`ingestion`, `utils`, `quality`, etc.)
* [ ] Improve README with build and deployment instructions
* [ ] Add build/deploy helper scripts (Makefile or `.sh`)

---

### 2) Runtime Parameterization

* [ ] Accept execution date as a parameter (`--date`)
* [ ] Generate partition columns based on the execution date
* [ ] Validate input parameters (e.g., date format)

---

### 3) Professional Logging

* [ ] Implement Log4j, SLF4J, or Logback
* [ ] Add logs for:

    * Job start
    * API fetch success
    * Data transformation
    * Data write
    * Errors and exceptions

---

### 4) Data Quality Checks

* [ ] Validate API JSON structure and required fields
* [ ] Define a fixed **Spark StructType** schema
* [ ] Check for empty DataFrames
* [ ] Validate non-null required columns
* [ ] Ensure data type consistency

---

### 5) Unit Tests

* [ ] Add tests for JSON parsing
* [ ] Test transformation functions
* [ ] Test partition column generation logic

---

### 6) Integration Tests

* [ ] Test actual Delta write
* [ ] Test read-after-write integrity
* [ ] Test correct partition creation

---

### 7) Data Observability (Ingestion Metrics)

* [ ] Generate ingestion metrics JSON per run
* [ ] Save metrics to a dedicated S3 folder (e.g., `/data_quality_metrics/`)
* [ ] Example metrics file:

```json
{
  "execution_date": "2025-06-19",
  "rows_written": 100,
  "errors": 0,
  "duration_seconds": 30
}
```

---

### 8) CI/CD Pipeline (GitHub Actions)

* [x] JAR build
* [x] JAR upload to S3
* [x] Databricks Asset Bundle deployment
* [ ] Validate if the Bundle exists after deploy
* [ ] (Optional) Run a smoke test job on Databricks post-deployment

---

### 9) Technical Documentation

* [ ] Create `/docs/README_openmeteo.md` explaining the Open-Meteo API
* [ ] Document Delta table structure (schema + partitioning)
* [ ] Document AWS permission prerequisites
* [ ] Document how to run the job manually via CLI or Databricks UI

---

### 10) Future Enhancements

* [ ] Multi-source ingestion (multiple APIs)
* [ ] Workflow orchestration with Airflow
* [ ] Streaming ingestion with Spark Structured Streaming
* [ ] Catalog external Delta table in Databricks metastore
* [ ] Monitoring with tools like **Datadog**, **CloudWatch**, etc.

---

## 💡 Tech Stack

* Apache Spark (Scala)
* Delta Lake
* Databricks Asset Bundles
* GitHub Actions (CI/CD)
* AWS S3
* Open-Meteo API (initial data source)

---

If you want, I can send you the full `README.md` file ready for commit.
**Want me to generate the file for you now?**
