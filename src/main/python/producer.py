import pika
import os
from dotenv import load_dotenv


load_dotenv()
amqp_url = os.getenv("CLOUDAMQP_URL")

# Connection parameters
params = pika.URLParameters(amqp_url)
connection = pika.BlockingConnection(params)
channel = connection.channel()
# channel.exchange_declare(exchange="topic-exchange", exchange_type="topic")

# Declare the topic exchange (should match the Spring Boot exchange name)
exchange_name = 'topic-exchange'
channel.exchange_declare(exchange=exchange_name, exchange_type='topic')

# Routing key (Spring listener is expecting this)
routing_key = 'marketHub'

# Message to send
message = 'Hello from Python Producer! 🐍'

# Publish the message
channel.basic_publish(
    exchange=exchange_name,
    routing_key=routing_key,
    body=message.encode('utf-8')
)

print(f"✅ Sent '{message}' with routing key '{routing_key}'")

# Close the connection
connection.close()
