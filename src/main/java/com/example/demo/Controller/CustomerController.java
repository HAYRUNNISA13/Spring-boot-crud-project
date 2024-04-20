package com.example.demo.Controller;

import com.example.demo.Model.Customer;
import com.example.demo.Services.CustomerAlreadyExistsException;
import com.example.demo.Services.CustomerNotFoundException;
import com.example.demo.Services.CustomerService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;

import java.util.List;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.ui.Model;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

@Controller
public class CustomerController {
    @Autowired private CustomerService service;
    @GetMapping("/index")
    public String getIndexPage() {
        return "index";
    }
    @GetMapping("/Customer")
    public String showCustomerList(Model model)
    {
        List<Customer> listCustomer =service.listAll();
        model.addAttribute("listCustomer" , listCustomer);
        return "Customer/CustomerList";
    }
    @PostMapping("/Customer/save")
    public String saveCustomer(@ModelAttribute Customer customer, RedirectAttributes ra) throws CustomerAlreadyExistsException {
        try {
            service.save(customer);
            ra.addFlashAttribute("message", "The customer has been saved successfully :)");
        } catch (CustomerAlreadyExistsException e) {
            ra.addFlashAttribute("errorMessage", e.getMessage());
        }
        return "redirect:/Customer";
    }

    @GetMapping("/Customer/create")
    public String showNewForm(Model model) {
        model.addAttribute("Customer", new Customer());
        return "Customer/CustomerAdd";
    }
    @GetMapping("/Customer/edit/{id}")
    public String showEditForm(@PathVariable("id") Long id, Model model, RedirectAttributes ra) {
        try {
            Customer customer = service.get(id);
            model.addAttribute("customer", customer);
        } catch (CustomerNotFoundException e) {
            ra.addFlashAttribute("message", "Customer not found :(");
            return "redirect:/Customer";
        }
        return "Customer/CustomerEdit";
    }

    @PostMapping("/Customer/edit/{id}")
    public String updateCustomer(@PathVariable("id") Long id, @ModelAttribute Customer customer, RedirectAttributes ra) {
        try {
            service.updateCustomer(id, customer);
            ra.addFlashAttribute("message", "The customer has been updated successfully :)");
        } catch (CustomerNotFoundException e) {
            ra.addFlashAttribute("message", "Customer not found :(");
        }
        return "redirect:/Customer";
    }

    @GetMapping("/Customer/delete/{id}")
    public String deleteCustomer(@PathVariable("id") Long id,  RedirectAttributes ra){
        try {
            service.delete(id);
            ra.addFlashAttribute("message","The customer with ID " +id+" has been deleted");


        }
        catch (CustomerNotFoundException e){
            ra.addFlashAttribute("message", e.getMessage());

        }
        return "redirect:/Customer";

    }




}
