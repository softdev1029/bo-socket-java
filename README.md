# Blackocean Java Socket Library

cd src/main/
find -name "\*.java" > sources.txt
javac @sources.txt
cd java/
java com.bo.socket.instance.Server
java com.bo.socket.instance.Example 127.0.0.1 4444
