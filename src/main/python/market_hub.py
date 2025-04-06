import pika
import os
import sys
import time
import random
from dotenv import load_dotenv
from datetime import datetime

load_dotenv()
amqp_url = os.getenv("CLOUDAMQP_URL")

supermarkets = {
    "1": "MarketHub",
    "2": "SuperCenter"
}

products = {
    "beverages": ["Coke", "Beer", "Juice"],
    "fruits": ["Apple", "Banana", "Orange"],
    "cleaning_products": ["Hand Sanitizer", "Window Cleaner", "Bleach"]
}

def escolher_setores(cod):
    if cod == "4":
        return list(products.keys())
    elif cod == "1":
        return ["beverages"]
    elif cod == "2":
        return ["fruits"]
    elif cod == "3":
        return ["cleaning_products"]
    else:
        return []

def main():
    if len(sys.argv) < 3:
        print("❌ Uso correto: python market_hub.py [market] [sector]")
        return

    mercado = supermarkets.get(sys.argv[1], "MarketHub")
    setores = escolher_setores(sys.argv[2])

    params = pika.URLParameters(amqp_url)
    connection = pika.BlockingConnection(params)
    channel = connection.channel()
    channel.exchange_declare(exchange="topic-exchange", exchange_type="topic", durable=False)

    try:
        print(f"\n🚀 Enviando promoções do {mercado} nos setores: {', '.join(setores)}")
        while True:
            setor = random.choice(setores)
            produto = random.choice(products[setor])
            desconto = random.randint(5, 35)
            horario = datetime.now().strftime("%d/%m/%Y - %H:%M")

            mensagem = f"[{horario}] {mercado}: {setor.replace('_', ' ').title()}: {produto} com {desconto}% de desconto!"
            routing_key = f"marketHub.{setor}"

            channel.basic_publish(
                exchange="topic-exchange",
                routing_key=routing_key,
                body=mensagem.encode()
            )

            print(f"✅ Enviada: {mensagem}")
            time.sleep(5)

    except KeyboardInterrupt:
        print("\n⛔ Produtor encerrado.")
        connection.close()

if __name__ == "__main__":
    main()
