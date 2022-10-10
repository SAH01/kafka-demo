

模拟随机数据，把数据**实时传输到Linux虚拟机文件**中。

使用Flume**实时监控**该文件，如果发现文件内容变动则进行处理，将**数据抓取并传递到Kafka消息队列**中。

之后**使用Spark Streaming 实时处理Kafka中的数据**，并写入Windows本机mysql数据库中，之后python**读取mysql数据库中的数据并基于Echart图表对数据进行实时动态展示。**

实现效果：图线会随着数据的变化动态改变

![image-20220318200827044](https://gitee.com/yang-chuanwei/typora-img/raw/master/img/image-20220318200827044.png)