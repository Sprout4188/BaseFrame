package com.sprout.frame.baseframe.database.simple;

import com.sprout.frame.baseframe.database.annotation.Column;
import com.sprout.frame.baseframe.database.annotation.Table;

/**
 * Created by Sprout on 2017/8/23
 */

@Table(name = "user")
public class User {

    @Column(name = "_id", primaryKey = true)
    private int uid;

    @Column(name = "name")
    private String uname;

    @Column(name = "addr")
    private String uaddress;

    @Column(name = "sex")
    private String sex;

    public String getUname() {
        return uname;
    }

    public void setUname(String uname) {
        this.uname = uname;
    }

    public String getUaddress() {
        return uaddress;
    }

    public void setUaddress(String uaddress) {
        this.uaddress = uaddress;
    }

    public int getUid() {
        return uid;
    }

    public void setUid(int uid) {
        this.uid = uid;
    }

    public String getSex() {
        return sex;
    }

    public void setSex(String sex) {
        this.sex = sex;
    }

    @Override
    public String toString() {
        return "User{" +
                "uid=" + uid +
                ", uname='" + uname + '\'' +
                ", uaddress='" + uaddress + '\'' +
                ", sex='" + sex + '\'' +
                '}';
    }
}
