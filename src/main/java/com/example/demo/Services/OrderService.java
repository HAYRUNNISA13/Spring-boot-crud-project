package com.example.demo.Services;

import com.example.demo.Model.Order;

import java.util.List;

public interface OrderService {
    List<Order> getAllOrders();

    Order getOrderById(Long id) throws OrderNotFoundException;

    void saveOrder(Order order);

    void updateOrder(Long id, Order order) throws OrderNotFoundException;

    void deleteOrder(Long id) throws OrderNotFoundException;
}

