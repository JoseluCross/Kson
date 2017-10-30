#!/bin/bash


javac -d bin src/*/*/*/*
cd bin
jar -cf kson-$1.jar com
mv kson* ..
cd ..
cd src
jkazip -c kson-$1-src.zip com
mv kson* ..
cd ..
