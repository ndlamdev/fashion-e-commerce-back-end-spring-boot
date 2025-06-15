docker run --name mongodb -v ./data/db:/data/db -v ./data/configdb:/data/configdb -p 27017:27017 -d mongodb/mongodb-community-server:latest

