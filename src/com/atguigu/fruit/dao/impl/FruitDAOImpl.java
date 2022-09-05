package com.atguigu.fruit.dao.impl;

import com.atguigu.fruit.dao.FruitDAO;
import com.atguigu.fruit.pojo.Fruit;
import com.atguigu.myssm.basedao.BaseDAO;

import java.util.List;

/**
 * @author QKC
 * @create 2022-08-03-17:55
 */
public class FruitDAOImpl extends BaseDAO<Fruit> implements FruitDAO {

    @Override
    public boolean insertFruit(Fruit fruit) {
        String sql = "INSERT INTO `t_fruit`(`fname`,`price`,`fcount`,`remark`) VALUES(?,?,?,?)";
        int flag = update(sql,fruit.getFname(),fruit.getPrice(),fruit.getFcount(),fruit.getRemark());
        return (flag > 0) ? true : false;
    }

    @Override
    public boolean delFruit(int fid) {
        String sql = "DELETE FROM `t_fruit` WHERE `fid` = ?";
        int flag = update(sql,fid);
        return (flag > 0) ? true : false;
    }

    @Override
    public boolean updateFruit(Fruit fruit) {
        String sql = "UPDATE `t_fruit` SET `fname` = ?,`price` =?,`fcount`=?,`remark`=? WHERE `fid`=?";
        int flag = update(sql,fruit.getFname(),fruit.getPrice(),fruit.getFcount(),fruit.getRemark(),fruit.getFid());
        return (flag > 0) ? true : false;
    }

//    @Override
//    public List<Fruit> getFruitList() {
//        String sql = "SELECT * FROM `t_fruit`";
//        return getForList(sql);
//    }

    @Override
    public List<Fruit> getFruitList(String keyword,Integer pageNum) {
        String sql = "SELECT * FROM `t_fruit` WHERE `fname` LIKE ? OR `remark` LIKE ? LIMIT ?,5";
        return getForList(sql,"%"+keyword+"%","%"+keyword+"%",(pageNum-1)*5);
    }


    @Override
    public Fruit getFruitByFid(int fid) {
        String sql = "SELECT `fid`,`fname`,`price`,`fcount`,`remark` FROM `t_fruit` WHERE `fid` = ?";
        return (Fruit) getBean(sql,fid);
    }

    @Override
    public Fruit getFruitByFname(String fname) {
        String sql = "SELECT `fid`,`fname`,`price`,`fcount`,`remark` FROM `t_fruit` WHERE `fname` = ?";
        return (Fruit) getBean(sql,fname);
    }

    @Override
    public int getFruitCount(String keyword) {
        String sql = "SELECT COUNT(*) FROM `t_fruit` WHERE `fname` LIKE ? OR `remark` LIKE ?";
        return (int) getCount(sql,"%"+keyword+"%","%"+keyword+"%");
    }


}
