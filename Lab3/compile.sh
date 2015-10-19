rm -r class/*
#javac -classpath hadoop-core-1.0.3.jar -d class part1/*
javac -classpath hadoop-core-1.0.3.jar -d class part2/*
jar -cvf Lab1.jar -C class/ .
