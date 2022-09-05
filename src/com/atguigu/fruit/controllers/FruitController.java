package com.atguigu.fruit.controllers;

import com.atguigu.fruit.service.FruitService;
import com.atguigu.fruit.pojo.Fruit;
import com.atguigu.utils.StringUtils;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;
import java.util.List;

/**
 * @author QKC
 * @create 2022-08-05-16:49
 */

public class FruitController {

    private FruitService fruitService = null;


    private String index(String oper,String keyword, Integer pageNum,HttpServletRequest request) {

        HttpSession session = request.getSession();

        if (pageNum==null){
            pageNum = 1;
        }

        if (StringUtils.isNotEmpty(oper) && "search".equals(oper)){
            pageNum = 1;
            if (StringUtils.isEmpty(keyword))
                keyword = "";

            session.setAttribute("keyword",keyword);
        } else {
            Object keywordObj = session.getAttribute("keyword");
            if (keywordObj!=null)
                keyword = (String) keywordObj;
            else keyword="";
        }

        session.setAttribute("pageNum",pageNum);

        List<Fruit> fruitList = fruitService.getFruitList(keyword,pageNum);
        //保存到session作用域
        session.setAttribute("fruitList",fruitList);

        //计算并保存总页数
        int pageCount = fruitService.getPageCount(keyword);
        session.setAttribute("pageCount",pageCount);

        return "index";
    }

    private String add(String fname,Integer price,Integer fcount,String remark) {
        Fruit fruit = new Fruit(0, fname, price, fcount, remark);
        fruitService.addFruit(fruit);
        return "redirect:fruit.do";
    }

    private String del(Integer fid) {
        if (fid!=null){
            fruitService.delFruit(fid);
            return "redirect:fruit.do";
        }
        return "error";
    }

    private String edit(Integer fid,HttpServletRequest request) {
        if (fid!=null){
            Fruit fruit = fruitService.getFruitByFid(fid);
            request.setAttribute("fruit",fruit);
            return "edit";
        }
        return "error";
    }

    private String update(Integer fid,String fname,Integer price,Integer fcount,String remark) {

        fruitService.updateFruit(new Fruit(fid,fname,price,fcount,remark));

        return "redirect:fruit.do";
    }


}
