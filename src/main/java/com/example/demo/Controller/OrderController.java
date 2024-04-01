package com.example.demo.Controller;

import com.example.demo.Model.Order;
import com.example.demo.Services.OrderNotFoundException;
import com.example.demo.Services.OrderService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@Controller
public class OrderController {

    @Autowired
    private OrderService orderService;

    @GetMapping("/orders")
    public String showOrderList(Model model) {
        List<Order> orders = orderService.getAllOrders();
        model.addAttribute("orders", orders);
        return "order/orderList";
    }

    @GetMapping("/orders/create")
    public String showNewOrderForm(Model model) {
        model.addAttribute("order", new Order());
        return "order/orderAdd";
    }

    @PostMapping("/orders/save")
    public String saveOrder(@ModelAttribute Order order) {
        orderService.saveOrder(order);
        return "redirect:/orders";
    }

    @GetMapping("/orders/edit/{id}")
    public String showEditOrderForm(@PathVariable("id") Long id, Model model) {
        try {
            Order order = orderService.getOrderById(id);
            model.addAttribute("order", order);
            return "order/orderEdit";
        } catch (OrderNotFoundException e) {
            return "redirect:/orders";
        }
    }

    @PostMapping("/orders/update/{id}")
    public String updateOrder(@PathVariable("id") Long id, @ModelAttribute Order order) {
        try {
            orderService.updateOrder(id, order);
        } catch (OrderNotFoundException e) {
            // Handle exception
        }
        return "redirect:/orders";
    }

    @GetMapping("/orders/delete/{id}")
    public String deleteOrder(@PathVariable("id") Long id) {
        try {
            orderService.deleteOrder(id);
        } catch (OrderNotFoundException e) {
            // Handle exception
        }
        return "redirect:/orders";
    }
}

