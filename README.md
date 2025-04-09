# 🛒 SupermartMQ — CloudAMQP Sales Distribution System

**SupermartMQ** is a messaging-based sales tracking system that leverages **RabbitMQ** and the **AMQP protocol** to simulate the flow of sales data from two supermarkets—**MarketHub** and **FreshMarket**—to consumers and auditors.  
Producers are written in **Python** and publish messages to **CloudAMQP**, while consumers are built in **Java** using **Spring Boot**.

---

## 📦 Project Structure

```
supermartmq/
├── python/
│   ├── market_hub.py        # Producer for MarketHub sectors
│   └── fresh_market.py      # Producer for FreshMarket sectors
└── java/
    ├── audit/
    │   ├── AuditApplication.java
    │   ├── AuditReceiver.java
    │   └── RabbitMQConfig.java  # Audit-specific config
    └── consumer/
        ├── ConsumerCLI.java
        ├── ConsumerReceiver.java
        └── RabbitMQConfig.java  # Consumer-specific config
```

---

## 🧠 Key Concepts

- **Exchange Type**: `topic`
- **Exchange Name**: `topic-exchange`
- **Queues**:
  - `audit.queue`: Binds to all messages using wildcard `#`
  - Dynamic queues for consumers: Bind to specific routing keys like `marketHub.bakery`
- **Routing Keys**:
  - **MarketHub**:
    - `marketHub.beverages`
    - `marketHub.snacks`
    - `marketHub.bakery`
  - **FreshMarket**:
    - `freshMarket.meat_fish`
    - `freshMarket.fruits`
    - `freshMarket.cleaning_products`

---

## 🐍 Python Producers

### `market_hub.py`

Simulates sales data from MarketHub:

- User selects a sector and types a message.
- Publishes to `topic-exchange` using the chosen routing key.

### `fresh_market.py`

Simulates sales data from FreshMarket:

- Works similarly to `market_hub.py` but uses FreshMarket sectors.

> **Note**: Both scripts read the `CLOUDAMQP_URL` from a `.env` file.

#### 🧪 Example `.env` file

```env
CLOUDAMQP_URL=amqps://<username>:<password>@<host>/<vhost>
```

#### ▶️ Run the producers

```bash
python3 market_hub.py
# or
python3 fresh_market.py
```

---

## ☕ Java Consumers

### 🕵️ Audit Service

Receives **all messages** across all routing keys for audit purposes.

**Files**:
- `AuditApplication.java`
- `AuditReceiver.java`
- `RabbitMQConfig.java` (Audit version)

**How it works**:
- Listens on `audit.queue` bound with `#` to receive every message.

**Run it**:

```bash
./gradlew bootRun --args='--spring.profiles.active=audit'
```

---

### 🎧 Consumer CLI

Interactive consumer that lets users choose which **supermarket** and **sector** to subscribe to.

**Files**:
- `ConsumerCLI.java`
- `ConsumerReceiver.java`
- `RabbitMQConfig.java` (Consumer version)

**Behavior**:
- Creates a **temporary**, auto-deleting queue.
- Binds to a **specific routing key** based on user input.
- Starts consuming messages in real time.

**Run it**:

```bash
./gradlew bootRun --args='--spring.profiles.active=consumer'
```

---

## 🔗 CloudAMQP Setup

1. Create a free account at [CloudAMQP](https://www.cloudamqp.com).
2. Create a new RabbitMQ instance.
3. Copy the AMQP URL and paste it into your `.env` file.

---

## 🧰 Dependencies

### Python

Install required packages:

```bash
pip install pika python-dotenv
```

### Java

- Java 17+
- Spring Boot Dependencies:
  - `spring-boot-starter-amqp`
  - `spring-boot-starter`

---

## 📃 License

[Apache License 2.0](LICENSE) — Feel free to use and modify!


---
