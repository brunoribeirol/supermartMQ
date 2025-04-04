import pika
import os
from dotenv import load_dotenv


def main():
    load_dotenv()
    amqp_url = os.getenv("CLOUDAMQP_URL")

    routing_keys = {
        "1": "beverages",
        "2": "fruits",
        "3": "cleaning_products",
        "4": "meat_fish"
    }

    sectors = {
        "1": "beverages",
        "2": "fruits",
        "3": "cleaning products",
        "4": "meat fish"
    }

    products = {
        "beverages": ["Coke", "Beer"],
        "fruits": ["Apple", "Banana", "Grape", "Strawberry"],
        "cleaning products": ["Hand Sanitizer", "Window Cleaner"],
        "meat fish": ["Sirloin", "Tuna"]
    }

    discounts = {
        "coke": 10,
        "beer": 5,
        "apple": 15,
        "banana": 7,
        "grape": 11,
        "strawberry": 6,
        "hand sanitizer": 25,
        "window cleaner": 15,
        "sirloin": 20,
        "tuna": 28
    }


    try:
        params = pika.URLParameters(amqp_url)
        connection = pika.BlockingConnection(params)
        channel = connection.channel()
        channel.exchange_declare(exchange="topic-exchange", exchange_type="topic")

        while True:
            print("\n----------Welcome to the FreshMarket----------")
            print("\n")

            sector = input("""Which sector do you wanna see the discounts?
            [1] - Beverages
            [2] - Fruits
            [3] - Cleaning Products
            [4] - Meat and Fish
            """)

            if sector not in sectors:
                print("Invalid option. Try again!")
                sector = input("""Which sector do you wanna see the discounts?
                [1] - Beverages
                [2] - Fruits
                [3] - Cleaning Products
                [4] - Meat and Fish
                """)

            sector_name = sectors.get(sector)
            product_list = products.get(sector_name)

            print(f"Products {product_list}")

            product = input("Which product do you wanna see the discount? ").strip().lower()

            if product not in [p.lower() for p in products[sector_name]]:
                print("Invalid option. Try again!")
                print(f"Products {product_list}")
                product = input("Which product do you wanna see the discount? ").strip().lower()

            print(f"{product.capitalize()} has {discounts[product]}% discount")


    except Exception as e:
        print(f"Error: {e}")


if __name__ == "__main__":
    main()
