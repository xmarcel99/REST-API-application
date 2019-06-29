#!/usr/bin/env bash

openGetTasksPage()
{
    x-www-browser --no-sandbox http://localhost:8080/crud/v1/task/getTasks
}
errorMessage()
{
    echo "Error with running your Tomcat server"
}

if ./runcrud.sh; then
    openGetTasksPage
else
    errorMessage
fi
