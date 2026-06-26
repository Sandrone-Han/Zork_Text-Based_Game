#!/bin/sh

rm -rf out
mkdir -p out

javac -d out \
src/org/uob/a2/*.java \
src/org/uob/a2/engine/*.java \
src/org/uob/a2/commands/*.java \
src/org/uob/a2/events/*.java \
src/org/uob/a2/factory/*.java \
src/org/uob/a2/gameobjects/*.java \
src/org/uob/a2/parser/*.java \
src/org/uob/a2/utils/*.java

java -cp out org.uob.a2.Game

chmod 755 ./*