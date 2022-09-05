package com.atguigu.myssm.basedao.impl;

import com.atguigu.fruit.service.FruitService;
import com.atguigu.fruit.dao.FruitDAO;
import com.atguigu.fruit.pojo.Fruit;

import java.util.List;

/**
 * @author QKC
 * @create 2022-08-08-15:47
 */
public class FruitServiceImpl implements FruitService {

    private FruitDAO fruitDAO = null;

    @Override
    public List<Fruit> getFruitList(String keyword, Integer pageNum) {
        return fruitDAO.getFruitList(keyword, pageNum);
    }

    @Override
    public void addFruit(Fruit fruit) {
        fruitDAO.insertFruit(fruit);
    }

    @Override
    public Fruit getFruitByFid(Integer fid) {
        return fruitDAO.getFruitByFid(fid);
    }

    @Override
    public void delFruit(Integer fid) {
        fruitDAO.delFruit(fid);
    }

    @Override
    public Integer getPageCount(String keyword) {
        int fruitCount = fruitDAO.getFruitCount(keyword);
        int pageCount = (fruitCount+5-1)/5;
        return pageCount;
    }

    @Override
    public void updateFruit(Fruit fruit) {
        fruitDAO.updateFruit(fruit);
    }
}
