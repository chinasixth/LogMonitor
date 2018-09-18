package bolt;

import backtype.storm.task.OutputCollector;
import backtype.storm.task.TopologyContext;
import backtype.storm.topology.BasicOutputCollector;
import backtype.storm.topology.OutputFieldsDeclarer;
import backtype.storm.topology.base.BaseBasicBolt;
import backtype.storm.topology.base.BaseRichBolt;
import backtype.storm.tuple.Fields;
import backtype.storm.tuple.Tuple;
import backtype.storm.tuple.Values;
import domain.Message;
import utils.MonitorHandler;

import javax.management.monitor.Monitor;
import java.util.Map;

/**
 * @ Author ：liuhao
 * @ Date   ：Created in 16:14 2018/9/18
 * 滤触发信息
 */
public class FilterBolt extends BaseBasicBolt {

    @Override
    public void execute(Tuple tuple, BasicOutputCollector basicOutputCollector) {
        // 获取接收的数据
        Object value = tuple.getValue(0);
        // 将Object类型的数据转换成一个字符串
        String line = new String((byte[]) value);
        // 验证数据是否合法
        Message message = MonitorHandler.parser(line);
        if (message == null) {
            return;
        }
        // 对获取的数据进行判断，判断是否触发规则
        if (MonitorHandler.trigger(message)) {
            basicOutputCollector.emit(new Values(message.getAppId(), message));
        }

        //
        MonitorHandler.schedulerLoad();
    }

    @Override
    public void declareOutputFields(OutputFieldsDeclarer outputFieldsDeclarer) {
        outputFieldsDeclarer.declare(new Fields("appId", "message"));
    }
}
