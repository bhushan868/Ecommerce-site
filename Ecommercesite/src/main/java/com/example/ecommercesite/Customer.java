package com.example.ecommercesite;

public class Customer {
    public String password;
    private int id;
    String name;
    String email;
    String mobile;


    public Customer(int id, String name, String email, String mobile) {
        this.id = id;
        this.name = name;
        this.email = email;
        this.mobile = mobile;
    }

    public int getId() {
        return id;
    }

    public String getName() {
        return name;
    }

    public String getEmail() {
        return email;
    }

    public String getMobile() {

        return mobile;
    }
}
