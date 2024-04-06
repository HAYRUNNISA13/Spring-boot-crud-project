package com.example.demo.Repository;
import com.example.demo.Model.Customer;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;
@Repository

public interface CustomerRepository extends CrudRepository<Customer, Long> {
    public Long countById(Long id);
    Customer findByName(String name);
}
