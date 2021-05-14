package com.example.codeblack.models;

public class ModelBook {

    String email,name,phone,province;

    public ModelBook() {
    }

    public ModelBook(String email, String name, String phone, String province) {
        this.email = email;
        this.name = name;
        this.phone = phone;
        this.province = province;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

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

    public String getProvince() {
        return province;
    }

    public void setProvince(String province) {
        this.province = province;
    }
}
