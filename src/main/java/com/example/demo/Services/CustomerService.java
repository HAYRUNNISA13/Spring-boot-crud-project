package com.example.demo.Services;

import com.example.demo.Repository.CustomerRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import com.example.demo.Model.Customer;
import java.util.List;

import java.util.List;
import java.util.Optional;

@Service

public class CustomerService {
    @Autowired private CustomerRepository crepo;

    public List<Customer> listall(){
        return(List<Customer>) crepo.findAll();

    }
    public void save(Customer customer) {
        crepo.save(customer);
    }
    public Customer get(Long id) throws CustomerNotFoundException {
       Optional<Customer> result= crepo.findById(id);
       if (result.isPresent())
       {
           return result.get();
       }
       throw new CustomerNotFoundException("Could not find any customer with ID " + id);
    }
}
