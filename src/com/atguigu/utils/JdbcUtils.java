package com.atguigu.utils;

import com.alibaba.druid.pool.DruidDataSourceFactory;
import org.apache.commons.dbutils.DbUtils;

import javax.sql.DataSource;
import java.io.InputStream;
import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.Properties;

/**
 * @author QKC
 * @create 2022-08-01-15:00
 */
public class JdbcUtils {

    private static DataSource source;
    static {
        try {
            Properties pros = new Properties();
//            InputStream is = ClassLoader.getSystemClassLoader().getResourceAsStream("jdbc.properties");
            InputStream is = JdbcUtils.class.getClassLoader().getResourceAsStream("jdbc.properties");

            pros.load(is);

            source = DruidDataSourceFactory.createDataSource(pros);
//            System.out.println("连接池创建成功");
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    /*
    * 获取数据库连接池中的连接
    * 获取失败返回null
    * */
    public static Connection getConnection(){
        Connection conn = null;
        try {
            conn = source.getConnection();
        } catch (SQLException e) {
            System.out.println("获取连接出错");
            e.printStackTrace();
        }

        return conn;
    }

    /**
     * 关闭连接，放回数据库连接池
     */
    public static void close(Connection conn, Statement ps, ResultSet rs){
        try {
            DbUtils.close(conn);
            DbUtils.close(ps);
            DbUtils.close(rs);
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }
    public static void close1(Connection conn){
        DbUtils.closeQuietly(conn);
    }



}
