package app.controller;

import app.model.Customer;

import app.repo.CustomerRepository;
import app.service.CustomerService;
import org.springframework.batch.core.*;
import org.springframework.batch.core.launch.JobLauncher;
import org.springframework.batch.core.repository.JobExecutionAlreadyRunningException;
import org.springframework.batch.core.repository.JobInstanceAlreadyCompleteException;
import org.springframework.batch.core.repository.JobRestartException;
import org.springframework.beans.factory.annotation.Autowired;


import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@RestController
@RequestMapping(path = "/batch")
public class BatchController {
    @Autowired
    private JobLauncher jobLauncher;
    @Autowired
    private Job job;

    @Autowired
    private CustomerRepository customerRepository;

    @Autowired
    private CustomerService customerService;

    @GetMapping(path = "/run")
    public void perform() throws Exception
    {
        JobParameters params = new JobParametersBuilder()
                .addString("JobID", String.valueOf(System.currentTimeMillis()))
                .toJobParameters();
        try{
            jobLauncher.run(job, params);
        } catch (JobExecutionAlreadyRunningException | JobRestartException
                 | JobInstanceAlreadyCompleteException | JobParametersInvalidException e) {
                e.printStackTrace();
        }
    }

    @GetMapping("/customers")
    Page<Customer> customersPageable (Pageable pageable) {
        return customerRepository.findAll(pageable);
    }

    //search based on customer id
    @GetMapping("/customers/searchid")
    public ResponseEntity<Map<String, Object>> getAllCustomersID(
            @RequestParam(required = false) Long custID,
            @RequestParam(defaultValue = "0") int page,
            @RequestParam(defaultValue = "3") int size
    ) {
        try {
            List<Customer> customerList = new ArrayList<Customer>();
            Pageable paging = PageRequest.of(page, size);

            Page<Customer> pageCust;
            if (custID == null)
                pageCust = customerRepository.findAll(paging);
            else
                pageCust = customerRepository.findByCustID(custID, paging);
            customerList = pageCust.getContent();
            Map<String, Object> response = new HashMap<>();
            response.put("customers", customerList);
            response.put("currentPage", pageCust.getNumber());
            response.put("totalItems", pageCust.getTotalElements());
            response.put("totalPages", pageCust.getTotalPages());
            return new ResponseEntity<>(response, HttpStatus.OK);
        } catch (Exception e) {
            return new ResponseEntity<>(null, HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

    //search and filter based on description
    @GetMapping("/customers/searchdesc")
    public ResponseEntity<Map<String, Object>> getAllCustomersDesc(
            @RequestParam(required = false) String description,
            @RequestParam(defaultValue = "0") int page,
            @RequestParam(defaultValue = "3") int size
    ) {
        try {
            List<Customer> customerList = new ArrayList<Customer>();
            Pageable paging = PageRequest.of(page, size);

            Page<Customer> pageCust;
            if (description == null)
                pageCust = customerRepository.findAll(paging);
            else
                pageCust = customerRepository.findByDescription(description, paging);
            customerList = pageCust.getContent();
            Map<String, Object> response = new HashMap<>();
            response.put("customers", customerList);
            response.put("currentPage", pageCust.getNumber());
            response.put("totalItems", pageCust.getTotalElements());
            response.put("totalPages", pageCust.getTotalPages());
            return new ResponseEntity<>(response, HttpStatus.OK);
        } catch (Exception e) {
            return new ResponseEntity<>(null, HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }


}
