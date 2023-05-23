from faker import Faker
import requests
import json
import re

# /* Requirement 7.3 */
fake = Faker()

host = input("Server Host: ")
port = input("Server Port: ")
num_users = int(input("Number of Users: "))
num_websites = int(input("Number of Websites: "))
num_logs = int(input("Number of logs per user: "))

users = []
for i in range(num_users):
	user = {
		"username": (f"{fake.first_name()}_{fake.last_name()}").lower(),
		"password": fake.password(),
		"email": (fake.email()).lower(),
	}
	users.append(user)

pattern_order = r'[0-9,-]'
websites = []
for i in range(num_websites):		
	website = {
		"displayName": re.sub(pattern_order, '', fake.company().replace(" ", "_")[:26]),
		"redirectUrl": fake.url(),
	}
	websites.append(website)
	
logs = []
for i in range(num_logs):
	log = {
		"data": fake.text()[:512],
		"logType": fake.random_int(min=0, max=3),
	}
	logs.append(log)
 
for user in users:
	response = requests.post(f"http://{host}:{port}/api/v1/users", json=user)
	if response.status_code != 200:
		print(f"Error: Server returned: {response.content}")
		print(f"Data of error: {user}\n")
	else:
		del user["username"]
		response = requests.post(f"http://{host}:{port}/api/v1/users/authorize", json=user)
		token = response.cookies.get("token")
		user["headers"] = {"Cookie": f"token={token}"}

admin = {"password": "adminadmin", "email": "admin@example.com"}
response = requests.post(f"http://{host}:{port}/api/v1/users/authorize", json=admin)
token = response.cookies.get("token")
admin["headers"] = {"Cookie": f"token={token}"}
for website in websites:
	response = requests.post(f"http://{host}:{port}/api/v1/websites", json=website, headers=admin["headers"])
	if response.status_code != 200:
		print(f"Error: Server returned: {response.content}")
		print(f"Data of error: {website}\n")

for user in users:
	for log in logs:
		response = requests.post(f"http://{host}:{port}/api/v1/logs", json=log, headers=user["headers"])
		if response.status_code != 200:
			print(f"For user: {user['email']}")
			print(f"Error: Server returned: {response.content}")
			print(f"Data of error: {log}\n")
