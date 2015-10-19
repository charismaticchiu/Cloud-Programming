import org.apache.spark.SparkContext
import org.apache.spark.SparkContext._
import org.apache.spark.SparkConf

object SimpleApp {
    def main(args: Array[String]) {
        val filePath = args(0)
        val name = args(1)
        var iters = args(2).toInt

        val conf = new SparkConf().setAppName("DescendantQuery Application")
        val sc = new SparkContext(conf)    
        val lines = sc.textFile(filePath)

        // Step 1: initial friend list, map each line in file to a key-value pair
        // ex: "Tom,Alice" => ("Tom", "Alice"), ("Alice", "Tom")
        // hint: use map() and union()
        // ...
        
	val namePair = lines.map{line => 
                val parts = line.split(",")
                (parts(0),parts(1))
                }.cache();
	
	val namePair2 = lines.map{line =>
                val parts = line.split(",")
                (parts(1),parts(0))
                }.cache();
	val namePairAll = namePair.union(namePair2).distinct();
        
	// Step 2: make name be a RDD
        // hint: use sc.parallelize()
        // var res = ...
        var res = sc.parallelize(List(name));

        // Step3: use initail result and friend list to find all friends within H hops
        // hint: use join()
        
        for( i <- 1 to iters ){
            val temp = res.map(x => (x,x)).join(namePairAll).values;
            res = res.union(temp.map(x => x._2)).distinct();
        }


        // Output
        val ans = res.count()
        println(ans)
        res.map(x => (x,x))
            .sortByKey()
            .collect()
            .foreach(x => println(x._1))
        System.exit(0)
    }
}
