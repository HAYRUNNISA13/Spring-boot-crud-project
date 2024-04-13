package com.example.demo.Services;

import com.example.demo.Model.Customer;
import com.example.demo.Model.Order;
import com.example.demo.Model.Product;
import com.example.demo.Repository.CustomerRepository;
import com.example.demo.Repository.OrderRepository;
import com.example.demo.Repository.ProductRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;


import java.util.List;
import java.util.Optional;

@Service
public class OrderServiceImpl implements OrderService {

    @Autowired
    private OrderRepository orderRepository;
    @Autowired
    private CustomerRepository crepo;
    @Autowired
    private ProductRepository productRepository;


    @Override
    public void saveOrder(Order order) {
        orderRepository.save(order);

    }

    @Override
    public List<Order> getAllOrders() {
        return (List<Order>) orderRepository.findAll();
    }

    @Override
    public Order getOrderById(Long id) throws OrderNotFoundException {
        return orderRepository.findById(id)
                .orElseThrow(() -> new OrderNotFoundException("Order not found with id: " + id));
    }

    @Override
    public void updateOrder(Long id, Order updatedOrder, Product product, Customer customer) throws OrderNotFoundException {
        Optional<Order> optionalOrder = orderRepository.findById(id);

        if (optionalOrder.isPresent()) {
            Order existingOrder = optionalOrder.get();
            existingOrder.setDate(updatedOrder.getDate());
            existingOrder.setDeliveryStatus(updatedOrder.getDeliveryStatus());
            existingOrder.setCity(updatedOrder.getCity());



            orderRepository.save(existingOrder);
        } else {
            throw new OrderNotFoundException("Could not find any order with ID " + id);
        }
    }






    @Override
    public void deleteOrder(Long id) throws OrderNotFoundException {
        if (orderRepository.existsById(id)) {
            orderRepository.deleteById(id);
        } else {
            throw new OrderNotFoundException("Order not found with id: " + id);
        }
    }
}
