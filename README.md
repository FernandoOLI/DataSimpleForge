# DataSimpleForge  
### End-to-end data pipeline using the Open-Meteo API with observability and Delta Lake

DataSimpleForge is an educational, end-to-end data pipeline designed to demonstrate how modern data workflows operate using **real data**, **Delta Lake**, **Python**, **Scala**, **Databricks**, and a strong focus on **observability and metrics**.

The project ingests weather data from the public **Open-Meteo API**, stores it in structured layers (raw → refined → business), computes observability metrics, and can run both **locally** and in **Databricks**.

---

## 🎯 Project Purpose

DataSimpleForge provides a practical, fully functional reference for:

- Understanding the complete lifecycle of a data pipeline  
- Learning how to structure a data project professionally  
- Practicing locally or deploying to Databricks  
- Building observability and metrics into pipelines  
- Using real-world APIs instead of mock data  
- Demonstrating best practices for reliable pipelines running 24/7  

---

## 🌦️ Data Source — Open-Meteo API

The pipeline consumes real meteorological data from the public:

**Open-Meteo Weather API**  
https://open-meteo.com/en/docs

Data includes temperature, humidity, wind speed, and more — ideal for demonstrating ingestion, transformations, and metric calculations.

---

### 🔍 Observability includes:
- Volume metrics per run  
- API latency monitoring  
- Success/failure counters  
- Data quality checks  
- Structured logging  

---

## ⚙️ Technologies Used

| Category | Technology |
|----------|------------|
| Languages | Python, Scala |
| Platform | Databricks (local execution supported) |
| Storage | Delta Lake |
| API | Open-Meteo Weather API |
| Cloud (optional) | AWS — S3, Glue, EC2 |
| Observability | Custom metrics + structured logs |

---

## 🚀 How to Run

### **1. Local Execution**
Perfect for learning and experimentation.

```bash
python src/ingestion/fetch_openmeteo_api.py
python src/ingestion/load_raw.py
python src/etl/refined_transform.py
python src/etl/business_transform.py
python src/metrics/compute_metrics.py
```

---

### **2. Databricks Execution**
The project is fully compatible with AWS Databricks.

Notebook order:

1. `01_ingestion_openmeteo`
2. `02_raw_to_refined`
3. `03_refined_to_business`
4. `04_metrics_observability`

Supports Delta Lake, DBFS, and continuous (24/7) workflows.

---

## 🧩 Repository Structure

```
DataSimpleForge/
│
├── data/                       # Example data and generated files
├── notebooks/                  # Databricks notebooks
├── src/
│   ├── ingestion/              # API ingestion + raw loading
│   ├── etl/                    # Refining + business modeling
│   ├── metrics/                # Observability metrics
│   └── utils/                  # Helper functions
│
├── docs/
├── requirements.txt
└── README.md
```

---

## 📚 Target Audience

- Aspiring data engineers  
- Students  
- Developers transitioning into data  
- Anyone wanting a practical, real-world pipeline  
- People learning Databricks + Delta Lake  

---

## 🧭 Motivation

Most examples online show toy ETLs.  
Few show **real data**, **observability**, **clean architecture**, and **production-ready structure**.

DataSimpleForge exists to fill that gap.

---

## 🤝 Contributing

PRs and suggestions are welcome.  
This project exists for learning and community growth.

---

## 📩 Author

**Fernando Oliveira Pereira**  
Data Engineer — Scala | Python | Spark | Databricks | AWS  
GitHub: https://github.com/FernandoOLI  
LinkedIn: https://www.linkedin.com/in/fernando-oliveira-b81032b4/
