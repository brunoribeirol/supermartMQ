import pika
import os
from dotenv import load_dotenv
from datetime import datetime

def main():
    load_dotenv()
    amqp_url = os.getenv("CLOUDAMQP_URL")

    params = pika.URLParameters(amqp_url)
    connection = pika.BlockingConnection(params)
    channel = connection.channel()
    channel.exchange_declare(exchange="topic-exchange", exchange_type="topic", durable=False)

    sectors = {
        "1": "meat_fish",
        "2": "fruits",
        "3": "cleaning_products"
    }

    print("🚀 FreshMarket Sales")

    while True:
        print("📦 Escolha o setor:")
        print("[1] Meat & Fish")
        print("[2] Fruits")
        print("[3] Cleaning Products")
        sector = input("> ").strip()

        if sector.lower() == "exit":
            break

        if sector not in sectors:
            print("❌ Invalid sector. Try again!")
            continue

        message = input("✍️ Type the sale: ").strip()
        if message.lower() == "exit":
            break

        data_hora = datetime.now().strftime("%d/%m/%Y - %H:%M")
        full_message = f"[{data_hora}] FreshMarket {sectors[sector]}. - {message}"
        routing_key = f"freshMarket.{sectors[sector]}"

        channel.basic_publish(
            exchange="topic-exchange",
            routing_key=routing_key,
            body=full_message.encode()
        )

        print(f"✅ Sent to {sectors[sector].replace('_', ' ').title()}: {message}\n")

    print("⛔ Producer finished.")
    connection.close()

if __name__ == "__main__":
    main()
