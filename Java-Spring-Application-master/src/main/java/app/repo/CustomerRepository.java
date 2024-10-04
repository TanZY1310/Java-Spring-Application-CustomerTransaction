package app.repo;

import app.model.Customer;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

//use of JpaRepository provides functionalities such as fetch, save, update, delete records

@Repository
public interface CustomerRepository extends JpaRepository<Customer, Long> {

//    custom query to search customer search by customer ID or account number(s) or description
    Page<Customer> findAll(Pageable pageable);
    Page<Customer> findByCustID(Long custID, Pageable pageable);
//    Page<Customer> findByAcc_number(Long accNumber, Pageable pageable); --> error could not found property
    Page<Customer> findByDescription(String description, Pageable pageable);

}
