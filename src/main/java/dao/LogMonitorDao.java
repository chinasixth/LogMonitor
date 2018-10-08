package dao;

import domain.App;
import domain.Record;
import domain.Rule;
import domain.User;
import org.springframework.jdbc.core.BeanPropertyRowMapper;
import org.springframework.jdbc.core.JdbcTemplate;

import java.util.Date;
import java.util.List;

/**
 * @ Author ：liuhao
 * @ Date   ：Created in 14:50 2018/9/18
 * @
 */
public class LogMonitorDao {
    private JdbcTemplate jdbcTemplate;

    public LogMonitorDao() {
        jdbcTemplate = new JdbcTemplate(DataSourceUtil.getDataSource());
    }

    /*
     * 查询所有用户信息
     * */
    public List<User> getUserList() {
        String sql = "select id,name,mobile,email,isValid from log_monitor_user where isValid = 1";
        return jdbcTemplate.query(sql, new BeanPropertyRowMapper<User>(User.class));
    }

    /*
     * 查询所有的规则信息
     * */
    public List<Rule> getRuleList() {
        String sql = "select id, name,keyword,isValid,appId from log_monitor_rule where isValid = 1";
        return jdbcTemplate.query(sql, new BeanPropertyRowMapper<Rule>(Rule.class));
    }

    /*
     * 查询所有的应用程序信息
     * */
    public List<App> getAppList() {
        String sql = "select id,name,isOnline,typeId,userId from log_monitor_app where isOnline = 1";
        return jdbcTemplate.query(sql, new BeanPropertyRowMapper<App>(App.class));
    }

    /*
     * 把新的触发规则信息存储到数据库
     * */
    public void saveRecord(Record record) {
        String sql = "insert into log_monitor_rule_record(appId,ruleId,isEmail,isPhone,isClose,noticeInfo,updateDate) values(?,?,?,?,?,?,?)";
        jdbcTemplate.update(sql, record.getAppId(), record.getRuleId(), record.getIsEmail(), record.getIsPhone(), record.getIsClose(), record.getLine(), new Date());
    }
}
