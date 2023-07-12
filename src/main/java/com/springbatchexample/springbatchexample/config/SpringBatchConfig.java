package com.springbatchexample.springbatchexample.config;

import org.springframework.batch.core.Job;
import org.springframework.batch.core.Step;
import org.springframework.batch.core.configuration.annotation.EnableBatchProcessing;
import org.springframework.batch.core.configuration.annotation.JobBuilderFactory;
import org.springframework.batch.core.configuration.annotation.StepBuilderFactory;
import org.springframework.batch.core.configuration.annotation.StepScope;
import org.springframework.batch.item.data.RepositoryItemWriter;
import org.springframework.batch.item.file.FlatFileItemReader;
import org.springframework.batch.item.file.LineMapper;
import org.springframework.batch.item.file.mapping.BeanWrapperFieldSetMapper;
import org.springframework.batch.item.file.mapping.DefaultLineMapper;
import org.springframework.batch.item.file.transform.DelimitedLineTokenizer;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.core.io.ClassPathResource;
import org.springframework.core.io.FileSystemResource;
import org.springframework.core.task.SimpleAsyncTaskExecutor;
import org.springframework.core.task.TaskExecutor;

import com.springbatchexample.springbatchexample.entity.Customer;
import com.springbatchexample.springbatchexample.repo.CustomerRepo;

import lombok.AllArgsConstructor;

@Configuration
@EnableBatchProcessing
@AllArgsConstructor
public class SpringBatchConfig {

	private JobBuilderFactory jobBuilderFactory;
	
	 private StepBuilderFactory stepBuilderFactory;
	 
	 private CustomerRepo customerRepo;
	 
		/*
		 * @Bean
		 * 
		 * @StepScope public FlatFileItemReader<Customer> reader(){
		 * FlatFileItemReader<Customer> itemReader= new FlatFileItemReader<Customer>();
		 * //itemReader.setResource(new
		 * FileSystemResource("C:\\Users\\DELL\\Desktop\\customers1.cvs"));
		 * 
		 * //itemReader.setResource(new
		 * FileSystemResource("src/main/resources/customers1.cvs"));
		 * itemReader.setEncoding("UTF-8"); itemReader.setResource(new
		 * FileSystemResource("src/main/resources/customerstest.cvs"));
		 * itemReader.setName("csvReader"); itemReader.setLinesToSkip(1);
		 * itemReader.setLineMapper(lineMapper());
		 * //System.out.println(itemReader.toString());
		 * 
		 * return itemReader; }
		 */

	 
	 @Bean
	 @StepScope
	 public FlatFileItemReader<Customer> reader() {
	    
	     FlatFileItemReader<Customer> itemReader = new FlatFileItemReader<>();
	     itemReader.setResource(new ClassPathResource("customers1.csv")); // Replace with the correct file name and path
	     itemReader.setLinesToSkip(1); // Skip the header line
	     
	     // Configure line mapper
	     DefaultLineMapper<Customer> lineMapper = new DefaultLineMapper<>();
	     
	     // Configure line tokenizer
	     DelimitedLineTokenizer tokenizer = new DelimitedLineTokenizer();
	     tokenizer.setDelimiter(",");
	     tokenizer.setNames("ID","CustomerId","FirstName","LastName","Company","City","Country","Phone","alternatenumber","Email","SubscriptionDate","Website");
	     
	     // Configure field set mapper
	     BeanWrapperFieldSetMapper<Customer> fieldSetMapper = new BeanWrapperFieldSetMapper<>();
	     fieldSetMapper.setTargetType(Customer.class);
	     
	     lineMapper.setLineTokenizer(tokenizer);
	     lineMapper.setFieldSetMapper(fieldSetMapper);
	     
	     itemReader.setLineMapper(lineMapper);
	     
	     return itemReader;
	 }
	

	private LineMapper<Customer> lineMapper() {
		DefaultLineMapper<Customer> lineMapper= new DefaultLineMapper<Customer>();
		
		
		DelimitedLineTokenizer lineTokenizer = new DelimitedLineTokenizer();
		lineTokenizer.setDelimiter(",");
		lineTokenizer.setStrict(false);
		lineTokenizer.setNames("ID","CustomerId","FirstName","LastName","Company","City","Country","Phone","alternatenumber","Email","SubscriptionDate","Website");
		BeanWrapperFieldSetMapper<Customer > fieldSetMapper = new BeanWrapperFieldSetMapper<>();
		fieldSetMapper.setTargetType(Customer.class);
		lineMapper.setLineTokenizer(lineTokenizer);
		lineMapper.setFieldSetMapper(fieldSetMapper);
		
		return lineMapper;
		

	}
	@Bean
	public CustomerProcessor processor() {
		return new CustomerProcessor();
	}
	
	@Bean
	public  RepositoryItemWriter<Customer> writer(){
		RepositoryItemWriter<Customer> writer =new  RepositoryItemWriter<>();
		writer.setRepository(customerRepo);
		writer.setMethodName("save");
		return writer;
	}
	@Bean
	public Step step() {
		return stepBuilderFactory.get("csv-step").<Customer,Customer>chunk(900).reader(reader()).processor(processor()).writer(writer()).taskExecutor(taskExecutor()).build();
	}
	
	@Bean
	public Job runJob() {
		return jobBuilderFactory.get("importCustomers").flow(step()).end().build();
	}
	@Bean
	public  TaskExecutor taskExecutor() {
		SimpleAsyncTaskExecutor asyncTaskExecutor =  new SimpleAsyncTaskExecutor();
		asyncTaskExecutor.setConcurrencyLimit(800);
		return asyncTaskExecutor;
	}
}
