import backtype.storm.Config;
import backtype.storm.StormSubmitter;
import backtype.storm.generated.AlreadyAliveException;
import backtype.storm.generated.InvalidTopologyException;
import backtype.storm.topology.TopologyBuilder;
import bolt.FilterBolt;
import bolt.PrepareRecordBolt;
import bolt.SaveMessageBolt;
import storm.kafka.KafkaSpout;
import storm.kafka.SpoutConfig;
import storm.kafka.ZkHosts;

/**
 * @ Author ：liuhao
 * @ Date   ：Created in 14:27 2018/9/18
 * @
 * 驱动类，实现获取kafka的数据
 */
public class LogMonitorTopology {
    public static void main(String[] args) throws AlreadyAliveException, InvalidTopologyException {
        TopologyBuilder builder = new TopologyBuilder();

        // 指定请求Kafka的topic
        String topic = "logmonitor";
        // 指定请求Kafka的参数
        ZkHosts hosts = new ZkHosts("hadoop05:2181,hadoop07:2181,hadoop08:2181");
        // 在ZK里面创建一个目录，存储元数据
        SpoutConfig spoutConfig = new SpoutConfig(hosts, topic, "/storm", "logmonitor");

        // 配置Spout和Bolt
        builder.setSpout("kafkaspout", new KafkaSpout(spoutConfig), 1);
        builder.setBolt("filterbolt", new FilterBolt(), 3).shuffleGrouping("kafkaspout");
        builder.setBolt("preparerecordbolt", new PrepareRecordBolt(), 1).shuffleGrouping("filterbolt");
        builder.setBolt("savemessagebolt", new SaveMessageBolt(), 1).shuffleGrouping("preparerecordbolt");

        Config conf = new Config();
        conf.setDebug(false);

        if(args!=null && args.length > 0){
            conf.setNumWorkers(3);
            StormSubmitter.submitTopologyWithProgressBar(args[0], conf, builder.createTopology());
        }else{
            conf.setMaxTaskParallelism(3);
            StormSubmitter.submitTopologyWithProgressBar("logmonitor", conf, builder.createTopology());
        }

    }
}
