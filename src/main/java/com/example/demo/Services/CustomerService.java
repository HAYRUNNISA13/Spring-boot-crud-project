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
    public void updateCustomer(Long id, Customer customer) throws CustomerNotFoundException {
        Optional<Customer> optionalCustomer = crepo.findById(id);
        if (optionalCustomer.isPresent()) {
            Customer existingCustomer = optionalCustomer.get();
            existingCustomer.setName(customer.getName());
            existingCustomer.setAddress(customer.getAddress());
            existingCustomer.setTelephone(customer.getTelephone());
            crepo.save(existingCustomer);
        } else {
            throw new CustomerNotFoundException("Could not find any customer with ID " + id);
        }
    }
    public void delete(Long id) throws CustomerNotFoundException {
        Long count=crepo.countById(id);
        if (count==null||count==0){
            throw new CustomerNotFoundException("Could not find any user with ID " +id);

        }
        crepo.deleteById(id);

    }
}
