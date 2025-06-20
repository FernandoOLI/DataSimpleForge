# DataSimpleForge - Data Ingestion Framework for Delta Lake on Databricks

Personal data engineering project by **Fernando Oliveira Pereira**, focused on ingesting public API data into Delta Lake using Apache Spark (Scala), with CI/CD deployment to Databricks.

---

## ✅ Project Status (As of June 19, 2025)

| Topic                                  | Status                        |
| -------------------------------------- | ----------------------------- |
| Project structure organized            | ✅                             |
| Date parameterization                  | ✅                             |
| Professional logging                   | ✅                             |
| Data Quality Checks                    | ✅ (Basic Schema Validation)   |
| Unit tests                             | ❌                             |
| Integration tests                      | ❌                             |
| Data Observability (metrics)           | ❌                             |
| CI/CD setup with GitHub Actions        | ✅ (build + upload + deploy)   |
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
│     ├── reader/
│     ├── transformation/
│     ├── writer/
│     ├── quality/
│     ├── config/
│     └── utils/
├── test/
├── .github/workflows/
├── data_forge_asset/         # Databricks Asset Bundle
├── docs/                     # Future technical documentation
├── README.md
└── build.sbt
```
---

---

## ✅ Improvement Checklist

### 1) Project Organization

- [x] Restructure Scala packages (`reader`, `transformation`, `writer`, `quality`, etc.)
- [ ] Improve README with full build and deployment instructions
- [ ] Add helper scripts (Makefile or `.sh`) for local dev and build

### 2) Runtime Parameterization

- [x] Accept execution date as a parameter (`--date`)
- [x] Accept API URL and output path as parameters
- [x] Environment handling (Local vs Prod Spark Master)

### 3) Professional Logging

- [x] SLF4J + Log4j setup
- [x] Logs for:

  - Job start
  - API fetch success
  - Data transformation
  - Schema validation
  - Data write
  - Errors and exceptions

### 4) Data Quality Checks

- [x] Validate API JSON structure against fixed **Spark StructType**
- [x] Check for empty DataFrames
- [x] Validate non-null required columns
- [ ] Validate ranges for numeric fields (latitude, longitude, temperature)
- [ ] Persist bad records (optional)

### 5) Unit Tests

- [ ] Test JSON parsing
- [ ] Test transformation logic
- [ ] Test partition column generation
- [ ] Test schema validator

### 6) Integration Tests

- [ ] Test Delta write
- [ ] Test read-after-write consistency
- [ ] Test correct partition creation in S3

### 7) Data Observability (Ingestion Metrics)

- [ ] Generate metrics JSON per run (rows written, errors, execution time)
- [ ] Save metrics to S3 (`/data_quality_metrics/`)
- [ ] Log metrics summary at end of job

### 8) CI/CD Pipeline (GitHub Actions)

- [x] JAR build
- [x] JAR upload to S3
- [x] Databricks Asset Bundle deployment
- [ ] Add post-deploy validation step
- [ ] (Optional) Trigger a Databricks job as a smoke test post-deploy

### 9) Technical Documentation

- [ ] Create `/docs/README_openmeteo.md` about Open-Meteo API fields and params
- [ ] Document Delta table schema and partitioning strategy
- [ ] Document AWS IAM setup and required roles/policies
- [ ] Document how to run job manually (local + Databricks)

### 10) Future Enhancements

- [ ] Multi-API ingestion support
- [ ] Airflow orchestration
- [ ] Streaming ingestion with Spark Structured Streaming
- [ ] External catalog registration (Databricks metastore)
- [ ] Monitoring integration (Datadog, CloudWatch, etc)

---

## 💡 Tech Stack

- Apache Spark (Scala)
- Delta Lake
- Databricks Asset Bundles
- GitHub Actions (CI/CD)
- AWS S3
- Open-Meteo API (Initial Data Source)
