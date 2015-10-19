rm -r class/*
#javac -classpath hadoop-core-1.0.3.jar -d class part1/*
javac -classpath hadoop-core-1.0.3.jar -d class code/*
jar -cvf HW2.jar -C class/ .
