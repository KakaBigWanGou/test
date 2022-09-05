package com.atguigu.utils;

import java.sql.Connection;
import java.sql.SQLException;

/**
 * @author QKC
 * @create 2022-08-10-15:46
 */
public class ConnUtil {

    private static ThreadLocal<Connection> threadLocal = new ThreadLocal<>();

    public static Connection getConn(){
        Connection conn = threadLocal.get();
        if (conn==null){
            conn = JdbcUtils.getConnection();
            threadLocal.set(conn);
        }
        return threadLocal.get();
    }

    public static void closeConn() throws SQLException {
        Connection conn = threadLocal.get();
        if (conn==null){
            return;
        }
        if (!conn.isClosed()){
            conn.close();
            threadLocal.set(null);
        }
    }
}
