package com.atguigu.myssm.basedao;

import com.atguigu.utils.JdbcUtils;
import org.apache.commons.dbutils.QueryRunner;
import org.apache.commons.dbutils.handlers.BeanHandler;
import org.apache.commons.dbutils.handlers.BeanListHandler;
import org.apache.commons.dbutils.handlers.ScalarHandler;

import java.lang.reflect.ParameterizedType;
import java.lang.reflect.Type;
import java.sql.Connection;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

/**
 * @author QKC
 * @create 2022-08-01-14:48
 */
public class BaseDAO<T> {

    //使用QueryRunner来简化修改操作
    private QueryRunner queryRunner = new QueryRunner();

    //定义一个变量来接收泛型的类型
    private Class<T> type;
    //获取T的Class对象，泛型是在被子类继承时才确定
    public BaseDAO(){
        //获取子类的类型
        Class clazz = this.getClass();
        //获取父类的类型
            //getGenericSuperclass(): 获取当前类的父类的类型
            //ParameterizedType: 带有泛型的类型
        ParameterizedType P_SuperClass = (ParameterizedType) clazz.getGenericSuperclass();
        //获取具体的泛型类型
            //getActualTypeArguments(): 获取当前类运行时期的具体实际类型，返回一个Type数组
        Type[] types = P_SuperClass.getActualTypeArguments();

        this.type = (Class<T>) types[0];
    }

    /*
    * 通用的增删改 */
    public int update(String sql,Object... params){
        Connection conn = JdbcUtils.getConnection();
        int count = 0;
        try {
            count = queryRunner.update(conn,sql,params);
        } catch (SQLException e) {
            e.printStackTrace();
        }finally {
            JdbcUtils.close(conn,null,null);
        }
        return count;
    }

    /*
    * 查询一个对象 */
    public T getBean(String sql,Object... params){
        Connection conn = JdbcUtils.getConnection();
        T t = null;
        try {
            t = queryRunner.query(conn,sql,new BeanHandler<T>(type),params);
        } catch (SQLException e) {
            e.printStackTrace();
        }finally {
            JdbcUtils.close(conn,null,null);
        }
        return t;
    }

    /*
    * 查询多条记录 */
    public List<T> getForList(String sql,Object... params){
        Connection conn = JdbcUtils.getConnection();
//        System.out.println("成功创建数据库连接");
        ArrayList<T> list = new ArrayList<T>();
        try {
            list = (ArrayList<T>) queryRunner.query(conn,sql,new BeanListHandler<T>(type),params);
        } catch (SQLException e) {
            System.out.println("执行异常");
            e.printStackTrace();
        }finally {
            JdbcUtils.close(conn,null,null);
        }
        return list;
    }

    /*
    * 查询记录条数 */
    public long getCount(String sql,Object... params){
        Connection conn = JdbcUtils.getConnection();
        long count = 0;
        try {
            count = queryRunner.query(conn,sql,new ScalarHandler<>(),params);
        } catch (SQLException e) {
            e.printStackTrace();
        }finally {
            JdbcUtils.close(conn,null,null);
        }
        return count;
    }







}
