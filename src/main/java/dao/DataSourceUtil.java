package dao;

import com.mchange.v2.c3p0.ComboPooledDataSource;

import javax.sql.DataSource;

/**
 * @ Author ：liuhao
 * @ Date   ：Created in 14:47 2018/9/18
 * @
 */
public class DataSourceUtil {
    private static DataSource dataSource;
    static{
        dataSource = new ComboPooledDataSource("logmonitor");
    }
    public static synchronized DataSource getDataSource(){
        if(dataSource == null){
            dataSource = new ComboPooledDataSource("logmonitor");
        }
        return dataSource;
    }
}
