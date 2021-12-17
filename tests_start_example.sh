#!/usr/bin/sh

# Без форков - не ясно пока почему не работает с форками (2021.12.17)
mvn test -DforkCount=0; mvn allure:serve
