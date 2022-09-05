package com.atguigu.myssm;

import com.atguigu.utils.ConnUtil;
import com.atguigu.utils.JdbcUtils;

import java.sql.Connection;
import java.sql.SQLException;

/**
 * @author QKC
 * @create 2022-08-10-15:15
 */
public class TransactionManager {

    //开启事务
    public static void beginTrans() throws SQLException {
        ConnUtil.getConn().setAutoCommit(false);
    }

    //提交事务
    public static void commit() throws SQLException {
        Connection conn = ConnUtil.getConn();
        conn.commit();
        ConnUtil.closeConn();
    }

    //回滚事务
    public static void rollback() throws SQLException {
        Connection conn = ConnUtil.getConn();
        conn.rollback();
        ConnUtil.closeConn();
    }


}

















