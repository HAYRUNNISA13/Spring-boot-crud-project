package com.example.demo.Controller;

import com.example.demo.Model.Customer;
import com.example.demo.Services.CustomerService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;

import java.util.List;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.ui.Model;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

@Controller
public class CustomerController {
    @Autowired private CustomerService service;
    @GetMapping("/Customer")
    public String showCustomerList(Model model)
    {
        List<Customer> listCustomer =service.listall();
        model.addAttribute("listCustomer" , listCustomer);
        return "Customer/CustomerList";
    }
    @PostMapping("/Customer/save")
    public String saveCustomer(@ModelAttribute Customer customer, RedirectAttributes ra) {
        service.save(customer);
        ra.addFlashAttribute("message", "The customer has been saved successfully :)");
        return "redirect:/Customer";
    }

    @GetMapping("/Customer/create")
    public String showNewForm(Model model) {
        model.addAttribute("Customer", new Customer());
        return "Customer/CustomerAdd";
    }



}
