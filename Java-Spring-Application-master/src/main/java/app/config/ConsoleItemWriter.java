package app.config;

import java.util.List;

import app.model.Customer;
import app.repo.CustomerRepository;
import org.springframework.batch.item.ItemWriter;
import org.springframework.beans.factory.annotation.Autowired;

public class ConsoleItemWriter<T> implements ItemWriter<T> {

    @Autowired
    private CustomerRepository customerRepository;
    @Override
    public void write(List<? extends T> items) throws Exception {
        for (T item : items) {
            System.out.println(item);
        }
    }
}
