package app.config;

import app.model.Customer;
import app.repo.CustomerRepository;
import org.springframework.batch.item.ItemWriter;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.util.List;

public class DatabaseWriter implements ItemWriter<Customer> {

    @Autowired
    private CustomerRepository customerRepository;

    @Override
    public void write(List<? extends Customer> customers) throws Exception {
        customerRepository.saveAll(customers);
        System.out.println("Data stored in database " + customers);
    }
}

