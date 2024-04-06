package com.example.demo.Controller;

import com.example.demo.Model.Order;
import com.example.demo.Services.OrderNotFoundException;
import com.example.demo.Services.OrderService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import com.example.demo.DTOs.Orderview;
import com.example.demo.Model.Customer;
import com.example.demo.Model.Order;
import com.example.demo.Model.Product;
import com.example.demo.Services.CustomerService;
import com.example.demo.Services.OrderService;
import com.example.demo.Services.ProductService;
import org.springframework.validation.BindingResult;


import java.util.List;

@Controller
public class OrderController {

    @Autowired
    private OrderService orderService;

    private final ProductService productService;
    private final CustomerService customerService;
    @GetMapping("/oindex")
    public String getIndexPage() {
        return "index";
    }

    @Autowired
    public OrderController(OrderService orderService, ProductService productService, CustomerService customerService) {
        this.orderService = orderService;
        this.productService = productService;
        this.customerService = customerService;
    }

    @GetMapping("/add")
    public String showAddOrderForm(Model model) {
        model.addAttribute("order", new Orderview());
        return "order/orderAdd";
    }

    @PostMapping("/add")
    public String addOrder(@ModelAttribute("order") Orderview orderview) {
        Order order = new Order();
        order.setDate(orderview.getDate());
        order.setDeliveryStatus(orderview.getDeliveryStatus());
        order.setCity(orderview.getCity());

        Product product = productService.findByName(orderview.getPname());
        Customer customer = customerService.findByName1(orderview.getName());

        order.setProduct(product);
        order.setCustomer(customer);

        orderService.saveOrder(order);

        return "redirect:/orders";
    }


    @GetMapping("/orders")
    public String showOrderList(Model model) {
        List<Order> orders = orderService.getAllOrders();
        model.addAttribute("orders", orders);
        return "order/orderList";
    }

    @GetMapping("/orders/create")
    public String showNewOrderForm(Model model) {
        model.addAttribute("orders", new Order());
        return "order/orderAdd";
    }

   /* @PostMapping("/orders/save")
    public String saveOrder(@ModelAttribute Order order) {
        orderService.saveOrder(order);
        return "redirect:/orders";
    }*/

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
    public String updateOrder(@PathVariable("id") Long id, @ModelAttribute("order") Order order, BindingResult result) {
        if (result.hasErrors()) {

            return "order/orderList" ;
        }

        try {
            orderService.updateOrder(id, order);
        } catch (OrderNotFoundException e) {

            return "redirect:/orders";
        }

        return "redirect:/orders";
    }


    @GetMapping("/orders/delete/{id}")
    public String deleteOrder(@PathVariable("id") Long id) {
        try {
            orderService.deleteOrder(id);
        } catch (OrderNotFoundException e) {

        }
        return "redirect:/orders";
    }
}

