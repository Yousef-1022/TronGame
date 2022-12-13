# Tron
- Simple implementation of the Tron game using Java Swing. The Scores are connected to an SQL database.

<h2> Game preview </h2>

![GamePlay](https://user-images.githubusercontent.com/99287718/207205836-3e972899-e9e0-4d79-9196-5848b15f3bb9.jpeg)
![Database](https://user-images.githubusercontent.com/99287718/207205852-5e4a9122-31da-457e-91f0-d3ea61bf9c66.jpeg)

## Way of running the project:
- NetBeans IDE can simply run this project quickly and efficiently.
- MySQL80 Service must be running.
- You must create a DataBase using SQL (you can use MySQL Workbench), then you can connect it from the services section in NetBeans.
- DataBase URL is similar to: "jdbc:mysql://localhost:PORT/DB_NAME?serverTimezone=UTC&user=USERNAME&password=PASSWORD"
- Modify "HighScores.java" variables: PORT, DB_NAME, USERNAME, password. According to the values you have on your SQL in order to successfully allow the game connect to the database.
