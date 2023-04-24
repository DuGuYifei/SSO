from faker import Faker
import requests
import json
import re

fake = Faker()

host = input("Server Host: ")
port = input("Server Port: ")
num_users = int(input("Number of Users: "))
num_logs = int(input("Number of Logs: "))
num_websites = int(input("Number of Websites: "))

pattern1 = "^[a-zA-Z_-]+$"
regex1 = re.compile(pattern1)
pattern2 = "^[a-z0-9_.+-]+@[a-z0-9-]+\\.[a-z0-9-.]+$"
regex2 = re.compile(pattern2)

users = []
for i in range(num_users):

	user_name = fake.user_name()
	while not regex1.match(user_name):
		user_name = fake.user_name()

	email = fake.email()
	while not regex2.match(email):
		email = fake.email()

	user = {
		"username": user_name,
		"password": fake.password(),
		"email": email,
	}
	users.append(user)

logs = []
for i in range(num_logs):
	log = {
		"data": fake.text(),
		"logType": fake.random_int(min=0, max=3),
	}
	logs.append(log)

websites = []
for i in range(num_websites):

	display_name = fake.word()
	while not regex1.match(display_name):
		display_name = fake.word()
		
	website = {
		"displayName": display_name,
		"redirectUrl": fake.url(),
	}
	websites.append(website)   
 
for user in users:
	response = requests.post(f"http://{host}:{port}/api/v1/users", json=user)
	if response.status_code != 200:
		print(f"Error: Server returned: {response.content}")
		print(f"Data of error: {user}\n")

for log in logs:
	response = requests.post(f"http://{host}:{port}/api/v1/logs", json=log)
	if response.status_code != 200:
		print(f"Error: Server returned: {response.content}")
		print(f"Data of error: {log}\n")
		
for website in websites:
	response = requests.post(f"http://{host}:{port}/api/v1/websites", json=website)
	if response.status_code != 200:
		print(f"Error: Server returned: {response.content}")
		print(f"Data of error: {website}\n")