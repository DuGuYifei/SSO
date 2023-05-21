ab -n 10000 -c 10 -C "token=eyJhbGciOiJIUzI1NiJ9.eyJqdGkiOiI5ZGUwNjU5NS0wMTBjLTRiNTktOTY2YS1kYzk0YzhjNjUzZjgiLCJzdWIiOiIyNWQ4ZTFhZi0yZTJhLTRkMjMtOWYyOC0xYTE3ODI1MzViZGQiLCJpYXQiOjE2ODQ4MTU2NzMsImV4cCI6MTY4NDkwMjA3M30.L0fs_rkx8vKrREWARkYGCLwI3NeAPtecfsXVrZQR_Y8" -p logs_.txt -T "application/json" http://localhost:8081/api/v1/logs

ab -n 10000 -c 10 -p users_.txt -T "application/json" http://localhost:8081/api/v1/users

ab -n 10000 -c 10 -C "token=eyJhbGciOiJIUzI1NiJ9.eyJqdGkiOiI5ZGUwNjU5NS0wMTBjLTRiNTktOTY2YS1kYzk0YzhjNjUzZjgiLCJzdWIiOiIyNWQ4ZTFhZi0yZTJhLTRkMjMtOWYyOC0xYTE3ODI1MzViZGQiLCJpYXQiOjE2ODQ4MTU2NzMsImV4cCI6MTY4NDkwMjA3M30.L0fs_rkx8vKrREWARkYGCLwI3NeAPtecfsXVrZQR_Y8" -p users_auth.txt -T "application/json" http://localhost:8081/api/v1/users/authorize

ab -n 10000 -c 10 -C "token=eyJhbGciOiJIUzI1NiJ9.eyJqdGkiOiI5ZGUwNjU5NS0wMTBjLTRiNTktOTY2YS1kYzk0YzhjNjUzZjgiLCJzdWIiOiIyNWQ4ZTFhZi0yZTJhLTRkMjMtOWYyOC0xYTE3ODI1MzViZGQiLCJpYXQiOjE2ODQ4MTU2NzMsImV4cCI6MTY4NDkwMjA3M30.L0fs_rkx8vKrREWARkYGCLwI3NeAPtecfsXVrZQR_Y8" -p users_ban.txt -T "application/json" http://localhost:8081/api/v1/users/ban

ab -n 10000 -c 10 -C "token=eyJhbGciOiJIUzI1NiJ9.eyJqdGkiOiI5ZGUwNjU5NS0wMTBjLTRiNTktOTY2YS1kYzk0YzhjNjUzZjgiLCJzdWIiOiIyNWQ4ZTFhZi0yZTJhLTRkMjMtOWYyOC0xYTE3ODI1MzViZGQiLCJpYXQiOjE2ODQ4MTU2NzMsImV4cCI6MTY4NDkwMjA3M30.L0fs_rkx8vKrREWARkYGCLwI3NeAPtecfsXVrZQR_Y8" -p users_unban.txt -T "application/json" http://localhost:8081/api/v1/users/unban

ab -n 10000 -c 10 -C "token=eyJhbGciOiJIUzI1NiJ9.eyJqdGkiOiI5ZGUwNjU5NS0wMTBjLTRiNTktOTY2YS1kYzk0YzhjNjUzZjgiLCJzdWIiOiIyNWQ4ZTFhZi0yZTJhLTRkMjMtOWYyOC0xYTE3ODI1MzViZGQiLCJpYXQiOjE2ODQ4MTU2NzMsImV4cCI6MTY4NDkwMjA3M30.L0fs_rkx8vKrREWARkYGCLwI3NeAPtecfsXVrZQR_Y8" -p websites_.txt -T "application/json" http://localhost:8081/api/v1/websites
