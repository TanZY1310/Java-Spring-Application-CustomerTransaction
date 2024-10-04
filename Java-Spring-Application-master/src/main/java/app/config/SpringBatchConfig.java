package app.config;

import app.model.Customer;

import javax.sql.DataSource;
import org.springframework.batch.core.Job;
import org.springframework.batch.core.Step;
import org.springframework.batch.core.configuration.annotation.EnableBatchProcessing;
import org.springframework.batch.core.configuration.annotation.JobBuilderFactory;
import org.springframework.batch.core.configuration.annotation.StepBuilderFactory;
import org.springframework.batch.core.launch.support.RunIdIncrementer;
import org.springframework.batch.item.file.FlatFileItemReader;
import org.springframework.batch.item.file.LineMapper;
import org.springframework.batch.item.file.mapping.BeanWrapperFieldSetMapper;
import org.springframework.batch.item.file.mapping.DefaultLineMapper;
import org.springframework.batch.item.file.transform.DelimitedLineTokenizer;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Lazy;

import org.springframework.core.io.ClassPathResource;
import app.repo.CustomerRepository;

import java.beans.PropertyEditor;
import java.beans.PropertyEditorSupport;
import java.time.LocalDate;
import java.time.LocalTime;
import java.time.format.DateTimeFormatter;
import java.util.HashMap;
import java.util.List;

@Configuration
@EnableBatchProcessing

public class SpringBatchConfig {

    @Autowired
    private JobBuilderFactory jobBuilderFactory;

    @Autowired //allows Spring to resolve and inject collaborating beans into our bean - annotations driven dependency injection
    private StepBuilderFactory stepBuilderFactory;

    @Autowired
    @Lazy
    private CustomerRepository customerRepository;

    @Autowired
    private DataSource dataSource;

    @Bean
    public Job readTXTFilesJob() {
        return jobBuilderFactory
                .get("readTXTFilesJob")
                .incrementer(new RunIdIncrementer()) //create an id for job created
                .start(step1())
                .build();
    }

    @Bean
    public Step step1() {
        return stepBuilderFactory.get("step1").<Customer, Customer>chunk(10) //chunk reads in small batches of data one at a time
                .reader(customerItemReader())
                .writer(writer())
                .writer(databaseWriter())
                .build();
    }

    @Bean
    public FlatFileItemReader<Customer> customerItemReader() {
            FlatFileItemReader<Customer> itemReader = new FlatFileItemReader<>();
            itemReader.setResource(new ClassPathResource("dataSource.txt"));
            itemReader.setLinesToSkip(1); //skip header

            itemReader.setLineMapper(customerLineMapper());

            return itemReader;
    }

    @Bean
    public LineMapper<Customer> customerLineMapper() {
        //Delimited Line Tokenizer - read and map line by line in a file
        DelimitedLineTokenizer lineTokenizer = new DelimitedLineTokenizer();
        lineTokenizer.setDelimiter("|");
        lineTokenizer.setNames("acc_number", "trx_amount",
                "description", "trx_date", "trx_time", "custID");
        lineTokenizer.setIncludedFields(new int[]{0, 1, 2, 3, 4, 5});

        //Date parsing logic
        HashMap<Class, PropertyEditor> customEditor = new HashMap<>();
        customEditor.put(LocalDate.class, new PropertyEditorSupport()
        {
            public void setAsText(String text) throws IllegalArgumentException {
                setValue(LocalDate.parse(text, DateTimeFormatter.ISO_LOCAL_DATE));
            }
        });
        //Time parsing logic
        customEditor.put(LocalTime.class, new PropertyEditorSupport()
        {
            public void setAsText(String text) throws IllegalArgumentException {
                setValue(LocalTime.parse(text, DateTimeFormatter.ISO_LOCAL_TIME));
            }
        });

        //Bean Wrapper Field SetMapper
        BeanWrapperFieldSetMapper<Customer> fieldSetMapper = new BeanWrapperFieldSetMapper<>();
        fieldSetMapper.setTargetType(Customer.class);

        fieldSetMapper.setCustomEditors(customEditor);

        //Default Line Mapper
        DefaultLineMapper<Customer> lineMapper = new DefaultLineMapper<>();
        lineMapper.setLineTokenizer(lineTokenizer);
        lineMapper.setFieldSetMapper(fieldSetMapper);

        return lineMapper;
    }

    @Bean
    public DatabaseWriter databaseWriter()
    {
        return new DatabaseWriter();
    }

    @Bean
    public ConsoleItemWriter<Customer> writer()
    {
        return new ConsoleItemWriter<Customer>();
    }

}
