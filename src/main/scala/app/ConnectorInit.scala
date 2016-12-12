package app

import com.datastax.spark.connector.cql.CassandraConnector
import org.apache.spark.SparkConf
import org.apache.spark.sql.SparkSession

import scala.collection.JavaConverters._

/**
 * Project: cassandraSpark
 * Package: app
 * Created by asoloviov on 12/13/16 3:10 PM.
 */
trait ConnectorInit {
  val cnf = AppConfig()
  val sparkConf = new SparkConf().setAppName(cnf.appName)
    .setMaster(cnf.appConfig.getString("spark.master.url"))
    .set("spark.ui.port", "44040")
    .set("spark.executor.memory", "2g")
    .set("spark.sql.crossJoin.enabled", "true")
    .set("spark.cassandra.sql.cluster", cnf.appConfig.getString("cassandra.cluster"))
    .set("spark.cassandra.connection.host", cnf.appConfig.getStringList("cassandra.seeds").asScala.mkString(","))
    .set("spark.cassandra.connection.port", cnf.appConfig.getString("cassandra.port"))
    .set("spark.cassandra.connection.compression", cnf.appConfig.getString("cassandra.compression"))
    .set("spark.cassandra.auth.username", cnf.appConfig.getString("cassandra.username"))
    .set("spark.cassandra.auth.password", cnf.appConfig.getString("cassandra.password"))
  val sc = SparkSession.builder().config(sparkConf).getOrCreate()
  val sqlContext = sc.sqlContext
  val keyspace = cnf.appConfig.getString("cassandra.keyspace")
  val tableName = "sql_demo"
    CassandraConnector(sparkConf).withSessionDo { session =>
      session.execute(s"CREATE KEYSPACE IF NOT EXISTS $keyspace WITH REPLICATION = {'class': 'SimpleStrategy', 'replication_factor': 1 }")
      session.execute(s"DROP TABLE IF EXISTS $keyspace.$tableName")
      session.execute(s"CREATE TABLE $keyspace.$tableName (user_id ASCII, device_id ASCII, PRIMARY KEY (user_id, device_id))")
      session.execute(s"INSERT INTO $keyspace.$tableName(user_id,device_id) VALUES ('user_1', 'LTroEVcxCCmrjm7ergytH')")
      session.execute(s"INSERT INTO $keyspace.$tableName(user_id,device_id) VALUES ('user_2', 'iPOYljNV86j0XMUy4T86o')")
      session.execute(s"INSERT INTO $keyspace.$tableName(user_id,device_id) VALUES ('user_3', '160GstM5vbywEmvwxldMG')")
      session.execute(s"INSERT INTO $keyspace.$tableName(user_id,device_id) VALUES ('user_1', '3ImHvKKzNWcvnmPYLWMhS')")
      session.execute(s"INSERT INTO $keyspace.$tableName(user_id,device_id) VALUES ('user_1', 'SWm0e8UssWtvUmO1kqtRh')")
      session.execute(s"INSERT INTO $keyspace.$tableName(user_id,device_id) VALUES ('user_2', 'XvgLCKsInUbvxmTugKdtw')")
      session.execute(s"INSERT INTO $keyspace.$tableName(user_id,device_id) VALUES ('user_1', 'boI9ayOzV3fyKmMdH3DSh')")
    }
}
