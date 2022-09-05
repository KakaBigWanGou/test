package com.atguigu.fruit.pojo;

/**
 * @author QKC
 * @create 2022-08-01-14:47
 */
public class Fruit {
    private Integer fid=0;
    private String fname="NULL";
    private Integer price=0;
    private Integer fcount=0;
    private String remark="NULL";

    public Fruit() { }

    public Fruit(Integer fid, String fname, Integer price, Integer fcount, String remark) {
        this.fid = fid;
        this.fname = fname;
        this.price = price;
        this.fcount = fcount;
        this.remark = remark;
    }

    public Fruit(Integer fid, String fname, Integer price, Integer fcount) {
        this.fid = fid;
        this.fname = fname;
        this.price = price;
        this.fcount = fcount;
    }

    public Integer getFid() {return fid;}
    public void setFid(Integer fid) {this.fid = fid;}

    public String getFname() {return fname;}
    public void setFname(String fname) {this.fname = fname;}

    public Integer getPrice() {return price;}
    public void setPrice(Integer price) {this.price = price;}

    public Integer getFcount() {return fcount;}
    public void setFcount(Integer fcount) {this.fcount = fcount;}

    public String getRemark() {return remark;}
    public void setRemark(String remark) {this.remark = remark;}

    @Override
    public String toString() {
        return "fruit{" +
                "fid=" + fid +
                ", fname='" + fname + '\'' +
                ", price=" + price +
                ", fcount=" + fcount +
                ", remark='" + remark + '\'' +
                '}';
    }
}
