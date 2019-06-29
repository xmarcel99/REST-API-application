#!/usr/bin/env bash

openGetTasksPage()
{
    x-www-browser http://localhost:8080/crud/v1/task/getTasks
}
errorMessage()
{
    echo "We can't run Tomcat server"
}

if ./runcrud.sh; then
    openGetTasksPage
else
    errorMessage
fi
