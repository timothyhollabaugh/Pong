Pong
====

Java JX based Pong made to run on a Raspberry Pi. It was developed for the Hunderdon County Robostorm Robotics Club's display at the fair. It is meant to be run on a 800x600 screen, but will work on any size.

Installation
====

This requires Java 8 to run on the Raspberry Pi, get it <a href="http://www.oracle.com/technetwork/java/embedded/downloads/javame/java-embedded-java-me-download-359231.html">here</a>. I recomend first dodnloading it to a PC, then copying it(scp) to the Pi. Once there, extract it with tar to /opt. Run java 8 with /opt/<jdk_version_here>/bin/java, replacing <jdk_version_here> with the java version eg. jdk1.8.0_20.(No X11 required)

To get java 8 for a PC, download it <a href="http://www.oracle.com/technetwork/java/javase/overview/java8-2100321.html">here</a>

Usage
====

On a Pi, run with /opt/<jdk_version_here>/bin/java -jar Pong.jar, replacing <jdk_version_here> with the java version eg. jdk1.8.0_20. On a PC, use java -jar Pong.jar. Be warned, it is fullscreen!
