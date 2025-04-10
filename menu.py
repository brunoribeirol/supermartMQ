#!/usr/bin/env python3
import subprocess

def start_audit():
    print("\nStarting Audit (Java Spring Boot)\n")
    subprocess.run([
        "./mvnw",
        "spring-boot:run",
        "-Dspring-boot.run.main-class=com.SupermartMQ.audit.AuditApplication"
    ])


def start_consumer():
    print("\nStarting Consumer (Java Spring Boot)\n")
    subprocess.run([
        "./mvnw",
        "spring-boot:run",
        "-Dspring-boot.run.main-class=com.SupermartMQ.consumer.ConsumerApplication"
    ])


def start_producer(script_name):
    print(f"\nStarting Producer: {script_name}\n")
    subprocess.run([
        "python3",
        f"src/main/python/producer/{script_name}"
    ])


def producer_menu():
    while True:
        print("\n=== Select a Producer ===")
        print("1. Market Hub")
        print("2. Fresh Market")
        print("3. Back to Main Menu")

        choice = input("Choose an option (1-3): ")

        if choice == "1":
            start_producer("market_hub.py")
        elif choice == "2":
            start_producer("fresh_market.py")
        elif choice == "3":
            break
        else:
            print("Invalid option. Please try again.")


def main_menu():
    while True:
        print("\n=== SupermartMQ ===")
        print("1. Start Audit (Java)")
        print("2. Start Consumer (Java)")
        print("3. Start Producer (Python)")
        print("4. Exit")

        choice = input("Choose an option (1-4): ")

        if choice == "1":
            start_audit()
        elif choice == "2":
            start_consumer()
        elif choice == "3":
            producer_menu()
        elif choice == "4":
            print("Exiting the system.")
            break
        else:
            print("Invalid option. Please try again.")


if __name__ == "__main__":
    main_menu()
