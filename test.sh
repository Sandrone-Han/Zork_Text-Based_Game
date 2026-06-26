#!/bin/sh

rm -rf out
mkdir -p out

javac -d out -Xlint:none \
-cp .:lib/junit-jupiter-api-5.11.1.jar:lib/hamcrest-core-3.0.jar \
src/org/uob/a2/*.java \
src/org/uob/a2/engine/*.java \
src/org/uob/a2/commands/*.java \
src/org/uob/a2/events/*.java \
src/org/uob/a2/factory/*.java \
src/org/uob/a2/gameobjects/*.java \
src/org/uob/a2/parser/*.java \
src/org/uob/a2/utils/*.java \
test/org/uob/a2/parser/*.java \
test/org/uob/a2/commands/*.java \
test/org/uob/a2/events/*.java \
test/org/uob/a2/factory/*.java \
test/org/uob/a2/gameobjects/*.java \
test/org/uob/a2/utils/*.java

COMPILE_STATUS=$?

if [ $COMPILE_STATUS -ne 0 ]; then
    chmod 755 ./*
    exit $COMPILE_STATUS
fi

java -jar lib/junit-platform-console-standalone-1.9.0.jar --details=tree --class-path ./out/ --scan-classpath

TEST_STATUS=$?

chmod 755 ./*

exit $TEST_STATUS