package com.example.demo.Services;

import com.example.demo.Model.Order;
import  com.example.demo.Model.Product;
import com.example.demo.Model.Customer;

import java.util.List;

public interface OrderService {
    List<Order> getAllOrders();
    void saveOrder(Order order);

    Order getOrderById(Long id) throws OrderNotFoundException;



    void updateOrder(Long id, Order order, Product product, Customer customer) throws OrderNotFoundException;

    void deleteOrder(Long id) throws OrderNotFoundException;
}

