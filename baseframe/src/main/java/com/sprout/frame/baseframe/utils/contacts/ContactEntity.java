package com.sprout.frame.baseframe.utils.contacts;

/**
 * Created by Sprout on 2018/4/18
 */

public class ContactEntity {
    /**
     * name : 张三
     * phone : 15812345671
     * email : example@sina.com
     * address : 重庆市沙坪坝
     * remark : 朋友
     */
    private String name;
    private String phone;
    private String email;
    private String address;
    private String remark;

    public String getPhoneBrand() {
        return phoneBrand;
    }

    public void setPhoneBrand(String phoneBrand) {
        this.phoneBrand = phoneBrand;
    }

    private String phoneBrand;

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getPhone() {
        return phone;
    }

    public void setPhone(String phone) {
        this.phone = phone;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getAddress() {
        return address;
    }

    public void setAddress(String address) {
        this.address = address;
    }

    public String getRemark() {
        return remark;
    }

    public void setRemark(String remark) {
        this.remark = remark;
    }
}
