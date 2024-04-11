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
import org.springframework.web.servlet.mvc.support.RedirectAttributes;


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
    public String addOrder(@ModelAttribute("order") Orderview orderview, RedirectAttributes ra) {
        try {
            Order order = new Order();
            order.setId(orderview.getId());

            order.setDate(orderview.getDate());
            order.setDeliveryStatus(orderview.getDeliveryStatus());
            order.setCity(orderview.getCity());

            Customer customer = customerService.findByName1(orderview.getName());
            Product product = productService.findByName2(orderview.getpname());

            order.setProduct(product);
            order.setCustomer(customer);

            orderService.saveOrder(order);
            ra.addFlashAttribute("message", "The order successfully added");
        }
        catch (Exception e) {
            ra.addFlashAttribute("error", "Failed to add order: " + e.getMessage());
        }


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
    public String showEditOrderForm(@PathVariable("id") Long id, Model model, RedirectAttributes ra) {
        try {
            Order order = orderService.getOrderById(id);
            model.addAttribute("order", order);

        } catch (OrderNotFoundException e) {
            ra.addFlashAttribute("message", "Product not found :(");
            return "redirect:/orders";
        }
        return "order/orderEdit";

    }

    @PostMapping("/orders/update/{id}")
    public String updateOrder(@PathVariable("id") Long id, @ModelAttribute("order") Order order,Product product,Customer customer, BindingResult result, RedirectAttributes ra) {
        if (result.hasErrors()) {
            return "order/orderList";
        }

        try {
            Order existingOrder = orderService.getOrderById(id);
            orderService.updateOrder(id, order,product,customer );
            ra.addFlashAttribute("message", "The order has been updated successfully :)");

        } catch (OrderNotFoundException e) {
            return "redirect:/orders";
        }

        return "redirect:/orders";
    }


    @GetMapping("/orders/delete/{id}")
    public String deleteOrder(@PathVariable("id") Long id , RedirectAttributes ra) {
        try {
            orderService.deleteOrder(id);
            ra.addFlashAttribute("message", "Order deleted successfully");
        } catch (OrderNotFoundException e) {
            ra.addFlashAttribute("error", "Failed to delete order: " + e.getMessage());

        }
        return "redirect:/orders";
    }
}

