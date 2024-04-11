package com.example.demo.DTOs;

import com.example.demo.Model.Customer;
import com.example.demo.Model.Product;
import lombok.Data;

import java.util.Date;
import org.springframework.format.annotation.DateTimeFormat;

@Data
public class Orderview {
    private Long id;
    private String name;
    private String pname;
    private String city;
    private String deliveryStatus;
    @DateTimeFormat(pattern = "yyyy-MM-dd")
    private Date date;
    private Product product;
    private Customer customer;
    public Long getId() {
        return id;
    }

    public Product getProduct() {
        return product;
    }

    public void setProduct(Product product) {
        this.product = product;
    }

    public Customer getCustomer() {
        return customer;
    }

    public void setCustomer(Customer customer) {
        this.customer = customer;
    }
    public String getDeliveryStatus() {
        return deliveryStatus;
    }

    public void setDeliveryStatus(String deliveryStatus) {
        this.deliveryStatus = deliveryStatus;
    }
    public Date getDate() {
        return date;
    }

    public void setDate(Date date) {
        this.date = date;
    }
    public String getpname() {
        return pname;
    }

    public void setProductName(String pname) {
        this.pname = pname;
    }
}
