package app.service;

import app.model.Customer;
import app.repo.CustomerRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

//Service Layer - responsible for business logic
@Service //knows where to get reference @Service/@Component is the same

//implementation for ICustomerService method
public class CustomerService {
    @Autowired
    private static CustomerRepository customerRepository;

    public static List<Customer> getAllCustomers(int pageNo, int pageSize, String sortBy) {
        Pageable paging = PageRequest.of(pageNo, pageSize, Sort.by(sortBy));
        Page<Customer> pagedResult = customerRepository.findAll(paging);

        if(pagedResult.hasContent()) {
            return pagedResult.getContent();
        } else {
            return new ArrayList<Customer>();
        }
    }
}
