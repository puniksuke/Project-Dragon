@echo off
title Project Insanity
"C:/Program Files/Java/jdk1.8.0_71/bin/javaw.exe" -Xmx1100M -cp bin;deps/poi.jar;deps/mysql.jar;deps/mina.jar;deps/slf4j.jar;deps/slf4j-nop.jar;deps/jython.jar;log4j-1.2.15.jar; org.osrspvp.Server
pause