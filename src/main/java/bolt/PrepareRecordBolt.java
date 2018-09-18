package bolt;

import backtype.storm.topology.BasicOutputCollector;
import backtype.storm.topology.OutputFieldsDeclarer;
import backtype.storm.topology.base.BaseBasicBolt;
import backtype.storm.tuple.Fields;
import backtype.storm.tuple.Tuple;
import backtype.storm.tuple.Values;
import domain.Message;
import domain.Record;
import org.apache.commons.beanutils.BeanUtils;
import utils.MonitorHandler;

import java.lang.reflect.InvocationTargetException;

/**
 * @ Author ：liuhao
 * @ Date   ：Created in 16:41 2018/9/18
 * @ 实现把触发信息发送到负责人邮箱
 */
public class PrepareRecordBolt extends BaseBasicBolt {

    @Override
    public void execute(Tuple tuple, BasicOutputCollector basicOutputCollector) {
        String appId = tuple.getValueByField("appid") + "";
        Message message = (Message) tuple.getValueByField("message");
        // 开始邮件通知
        MonitorHandler.notify(appId, message);

        Record record = new Record();
        try {
            // 将message的字段复制给record，字段必须相对应
            BeanUtils.copyProperties(record, message);
            basicOutputCollector.emit(new Values(record));
        } catch (IllegalAccessException e) {
            e.printStackTrace();
        } catch (InvocationTargetException e) {
            e.printStackTrace();
        }
    }

    @Override
    public void declareOutputFields(OutputFieldsDeclarer outputFieldsDeclarer) {
        outputFieldsDeclarer.declare(new Fields("record"));
    }
}
