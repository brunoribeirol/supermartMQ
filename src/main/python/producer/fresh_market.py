import pika
import os
from dotenv import load_dotenv
from datetime import datetime

load_dotenv()

def main():

    amqp_url = os.getenv("CLOUDAMQP_URL")

    params = pika.URLParameters(amqp_url)
    connection = pika.BlockingConnection(params)
    channel = connection.channel()
    channel.exchange_declare(exchange="topic-exchange", exchange_type="topic", durable=False)

    sectors = {
        1: "meat_fish",
        2: "fruits",
        3: "cleaning_products"
    }

    print("🚀 FreshMarket Sales")
    print("--------------------")

    while True:
        print("📦 Choose a sector:")
        print("[1] Meat & Fish")
        print("[2] Fruits")
        print("[3] Cleaning Products")
        print("[4] Exit")

        try:
            sector = int(input("> "))
        except ValueError:
            print("\n❌ Invalid input. Please enter a number from 1 to 4.")
            continue

        if sector == 4:
            print("👋 Exiting...")
            break

        if sector not in sectors:
            print("\n❌ Invalid sector. Try again!")
            continue

        print(f"You chose: {sectors[sector].upper()}")

        message = input("✍️ Type the sale: ").strip()

        date_time = datetime.now().strftime("%d/%m/%Y - %H:%M")
        full_message = f"[{date_time}] FreshMarket {sectors[sector].capitalize()} - {message.capitalize()}"
        routing_key = f"freshMarket.{sectors[sector]}"

        channel.basic_publish(
            exchange="topic-exchange",
            routing_key=routing_key,
            body=full_message.encode()
        )

        print(f"✅ Sent to {sectors[sector].replace('_', ' ').upper()}: {message}\n")

    print("⛔ Producer finished.")
    connection.close()

if __name__ == "__main__":
    main()
