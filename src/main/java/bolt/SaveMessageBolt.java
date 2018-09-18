package bolt;

import backtype.storm.topology.BasicOutputCollector;
import backtype.storm.topology.OutputFieldsDeclarer;
import backtype.storm.topology.base.BaseBasicBolt;
import backtype.storm.tuple.Tuple;
import domain.Record;
import utils.MonitorHandler;

/**
 * @ Author ：liuhao
 * @ Date   ：Created in 17:03 2018/9/18
 * @ 将触发信息保存到MySql
 */
public class SaveMessageBolt extends BaseBasicBolt {

    @Override
    public void execute(Tuple tuple, BasicOutputCollector basicOutputCollector) {
        Record record = (Record) tuple.getValueByField("record");

        MonitorHandler.save(record);
    }

    @Override
    public void declareOutputFields(OutputFieldsDeclarer outputFieldsDeclarer) {

    }
}
