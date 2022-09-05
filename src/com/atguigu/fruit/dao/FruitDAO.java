package com.atguigu.fruit.dao;

import com.atguigu.fruit.pojo.Fruit;

import java.util.List;

/**
 * @author QKC
 * @create 2022-08-03-17:54
 */
public interface FruitDAO {

    //新增一条数据
    boolean insertFruit(Fruit fruit);

    //删除指定名称库存
    boolean delFruit(int fid);

    //修改库存
    boolean updateFruit(Fruit fruit);

//    //查询库存列表
//    List<Fruit> getFruitList();
    //获取指定页码上的库存信息，每页5条
    List<Fruit> getFruitList(String keyword,Integer pageNum);

    //根据名称查询对应库存
    Fruit getFruitByFname(String fname);

    //根据序号查询对应库存
    Fruit getFruitByFid(int fid);

    //查询记录条数
    int getFruitCount(String keyword);


}
