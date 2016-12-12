package app

import org.apache.spark.sql.cassandra._
import org.apache.spark.sql.execution.debug._

object CassandraSpark extends App with ConnectorInit {
  sqlContext.read.cassandraFormat(tableName, keyspace).load().createOrReplaceTempView("users_devices")
  val dfUserDevices = sqlContext.sql("SELECT user_id FROM users_devices LIMIT 6") // <- using LIMIT with value less then the table size
  val dfVertices = dfUserDevices.distinct()
  try {
    println(dfVertices.debugCodegen())
    dfVertices.show(20, truncate = false)
/*
    Caused by: java.lang.NullPointerException
    at org.apache.spark.sql.catalyst.expressions.GeneratedClass$GeneratedIterator.agg_doAggregateWithKeys$(Unknown Source)
    ...
*/
  } finally {
    sc.stop()
  }
}
