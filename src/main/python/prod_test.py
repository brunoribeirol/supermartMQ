import pika
import os
from dotenv import load_dotenv


def main():
    load_dotenv()
    amqp_url = os.getenv("CLOUDAMQP_URL")
    EXCHANGE_NAME = "topic_exchange"
    EXCHANGE_TYPE = "topic"

    routing_keys = {
        "1": "beverages",
        "2": "fruits",
        "3": "cleaning_products",
        "4": "snacks",
        "5": "dairy_products",
        "6": "bakery",
        "7": "frozen_foods",
        "8": "meat_and_poultry",
        "9": "seafood",
        "10": "vegetables",
        "11": "personal_care",
        "12": "household_items",
        "13": "baby_products",
        "14": "pet_supplies",
        "15": "health_and_wellness"
    }

    sectors = {
        "1": "beverages",
        "2": "fruits",
        "3": "cleaning products",
        "4": "snacks",
        "5": "dairy products",
        "6": "bakery",
        "7": "frozen foods",
        "8": "meat and poultry",
        "9": "seafood",
        "10": "vegetables",
        "11": "personal care",
        "12": "household items",
        "13": "baby products",
        "14": "pet supplies",
        "15": "health and wellness"
    }

    try:
        params = pika.URLParameters(amqp_url)
        connection = pika.BlockingConnection(params)
        channel = connection.channel()
        channel.exchange_declare(exchange="topic-exchange", exchange_type="topic")

        while True:
            print("\n----------Welcome to the MarketHub----------")
            print("\n")

            sector = input("""Which sector do you wanna see the discounts?
            [1] - BEVERAGES  
            [2] - FRUITS  
            [3] - CLEANING_PRODUCTS  
            [4] - SNACKS  
            [5] - DAIRY_PRODUCTS  
            [6] - BAKERY  
            [7] - FROZEN_FOODS  
            [8] - MEAT_AND_POULTRY  
            [9] - SEAFOOD  
            [10] - VEGETABLES  
            [11] - PERSONAL_CARE  
            [12] - HOUSEHOLD_ITEMS  
            [13] - BABY_PRODUCTS  
            [14] - PET_SUPPLIES  
            [15] - HEALTH_AND_WELLNESS  

            """)

            if sector not in sectors:
                print("Invalid option. Try again!")
                sector = input("""Which sector do you wanna see the discounts?
                [1] - BEVERAGES  
                [2] - FRUITS  
                [3] - CLEANING_PRODUCTS  
                [4] - SNACKS  
                [5] - DAIRY_PRODUCTS  
                [6] - BAKERY  
                [7] - FROZEN_FOODS  
                [8] - MEAT_AND_POULTRY  
                [9] - SEAFOOD  
                [10] - VEGETABLES  
                [11] - PERSONAL_CARE  
                [12] - HOUSEHOLD_ITEMS  
                [13] - BABY_PRODUCTS  
                [14] - PET_SUPPLIES  
                [15] - HEALTH_AND_WELLNESS  
                """)

            sector_name = sectors.get(sector)
            product_list = products.get(sector_name)

            print(f"Products {product_list}")

            product = input("Which product do you wanna see the discount? ").strip().lower()

            if product not in [p.lower() for p in products[sector_name]]:
                print("Invalid option. Try again!")

            # print(f"{product.capitalize()} has {discounts[product]}% discount")







    except Exception as e:
        print(f"Error: {e}")


if __name__ == "__main__":
    main()
