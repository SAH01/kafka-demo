import org.apache.kafka.common.serialization.StringDeserializer
import org.apache.spark.SparkConf
import org.apache.spark.streaming.kafka010.ConsumerStrategies.Subscribe
import org.apache.spark.streaming.kafka010.KafkaUtils
import org.apache.spark.streaming.kafka010.LocationStrategies.PreferConsistent
import java.sql.DriverManager
import java.text.SimpleDateFormat
import java.util.Date

/**
  * 主要是计算X秒内数据条数的变化
  * 比如5秒内进来4条数据
  */

import org.apache.spark.streaming.{Seconds, StreamingContext}
/** Utility functions for Spark Streaming examples.*/
object StreamingExamples extends  App{
  /** Set reasonable logging levels for streaming if the user has not configured log4j.*/
//  def setStreamingLogLevels() {
//    val log4jInitialized = Logger.getRootLogger.getAllAppenders.hasMoreElements
//    if (!log4jInitialized) {
//      // We first log Appsomething to initialize Spark's default logging, then we override the
//      // logging level.
//      logInfo("Setting log level to [WARN] for streaming example." +
//        " To override add a custom log4j.properties to the classpath.")
//      Logger.getRootLogger.setLevel(Level.WARN)
//    }
//  }
  val conf=new SparkConf().setMaster("local").setAppName("jm")
    .set("spark.streaming.kafka.MaxRatePerPartition","3")
    .set("spark.local.dir","./tmp")
    .set("spark.serializer", "org.apache.spark.serializer.KryoSerializer")
  //创建上下文，5s为批处理间隔
  val ssc = new StreamingContext(conf,Seconds(5))

  //配置kafka参数，根据broker和topic创建连接Kafka 直接连接 direct kafka
  val KafkaParams = Map[String,Object](
    //brokers地址
    "bootstrap.servers"->"hadoop102:9092,hadoop103:9092,hadoop104:9092",
    //序列化类型
    "key.deserializer"->classOf[StringDeserializer],
    "value.deserializer" -> classOf[StringDeserializer],
    "group.id" -> "MyGroupId",
    //设置手动提交消费者offset
    "enable.auto.commit" -> (false: java.lang.Boolean)//默认是true
  )

  //获取KafkaDStream
  val kafkaDirectStream = KafkaUtils.createDirectStream[String,String](ssc,
    PreferConsistent,Subscribe[String,String](List("first"),KafkaParams))
  kafkaDirectStream.print()
  var num=kafkaDirectStream.count()
  var num_1=""
  num foreachRDD (x => {
//var res=x.map(line=>line.split(","))
     val connection = getCon()
     var time = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss").format(new Date).toString
     var sql = "insert into content_num values('" + time + "'," + x.collect()(0) + ")"
     connection.createStatement().execute(sql)
     connection.close()
   })
//  print("sdfasdf")
//  print(num_1)
  //根据得到的kafak信息，切分得到用户电话DStream
//  val nameAddrStream = kafkaDirectStream.map(_.value()).filter(record=>{
//    val tokens: Array[String] = record.split(",")
//    tokens(1).toInt==0
//  })
//
//  nameAddrStream.print()
//  .map(record=>{
//    val tokens = record.split("\t")
//    (tokens(0),tokens(1))
//  })
//
//
//  val namePhoneStream = kafkaDirectStream.map(_.value()).filter(
//    record=>{
//      val tokens = record.split("\t")
//      tokens(2).toInt == 1
//    }
//  ).map(record=>{
//    val tokens = record.split("\t")
//    (tokens(0),tokens(1))
//  })
//
//  //以用户名为key，将地址电话配对在一起，并产生固定格式的地址电话信息
//  val nameAddrPhoneStream = nameAddrStream.join(namePhoneStream).map(
//    record=>{
//      s"姓名：${record._1},地址：${record._2._1},邮编：${record._2._2}"
//    }
//  )
//  //打印输出
//  nameAddrPhoneStream.print()
  //开始计算
  ssc.start()
  ssc.awaitTermination()
  def getCon()={
    Class.forName("com.mysql.cj.jdbc.Driver")
    DriverManager.getConnection("jdbc:mysql://localhost:3306/spark?serverTimezone=UTC&useUnicode=true&characterEncoding=utf8","root","reliable")
  }
}

