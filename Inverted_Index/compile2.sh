rm -r class/*
#javac -classpath hadoop-core-1.0.3.jar -d class part1/*
javac -classpath hadoop-core-1.0.3.jar -d class retrieval/*
jar -cvf HW2_retrieval.jar -C class/ .
