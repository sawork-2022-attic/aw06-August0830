package com.example.batch.config;

import javax.sql.DataSource;

import com.example.batch.Repository.ProductRepository;
import com.example.batch.model.Product;
import com.example.batch.service.*;
import com.fasterxml.jackson.databind.JsonNode;
import org.springframework.batch.core.Job;
import org.springframework.batch.core.Step;
import org.springframework.batch.core.JobExecutionListener;
import org.springframework.batch.core.configuration.annotation.EnableBatchProcessing;
import org.springframework.batch.core.configuration.annotation.JobBuilderFactory;
import org.springframework.batch.core.configuration.annotation.StepBuilderFactory;
import org.springframework.batch.core.launch.support.RunIdIncrementer;
import org.springframework.batch.item.ItemProcessor;
import org.springframework.batch.item.ItemReader;
import org.springframework.batch.item.ItemWriter;
import org.springframework.batch.item.data.RepositoryItemWriter;
import org.springframework.batch.item.database.BeanPropertyItemSqlParameterSourceProvider;
import org.springframework.batch.item.database.JdbcBatchItemWriter;
import org.springframework.batch.item.database.builder.JdbcBatchItemWriterBuilder;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.autoconfigure.EnableAutoConfiguration;
import org.springframework.boot.autoconfigure.orm.jpa.HibernateJpaAutoConfiguration;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.core.task.TaskExecutor;
import org.springframework.scheduling.concurrent.ThreadPoolTaskExecutor;


@Configuration
//@EnableAutoConfiguration(exclude = {HibernateJpaAutoConfiguration.class})
@EnableBatchProcessing
public class BatchConfig {


    @Autowired
    public JobBuilderFactory jobBuilderFactory;

    @Autowired
    public StepBuilderFactory stepBuilderFactory;

    @Autowired
    public ProductRepository productRepository;

    @Bean
    public ItemReader<JsonNode> itemReader() {
        //return new JsonFileReader("/home/njucs/SA-2022/HW06/aw06-augustus0830/src/main/java/resources/data/meta_Magazine_Subscriptions_100.json");
        //String path = BatchConfig.class.getResource("/data/meta_Magazine_Subscriptions_100.json").getPath();
        String path = BatchConfig.class.getResource("/data/meta_Luxury_Beauty.json").getPath();
        //String path = BatchConfig.class.getResource("/data/meta_Prime_Pantry.json").getPath();
        return new JsonFileReader(path);
    }

    @Bean
    public ItemProcessor<JsonNode, Product> itemProcessor() {
        return new ProductProcessor();
    }

    // @Bean
    // public ItemWriter<Product> itemWriter() {
    //     return new ProductWriter();
    // }

    // @Bean
    // public JdbcBatchItemWriter<Product> itemJdbcWriter(DataSource dataSource){
    //     return new JdbcBatchItemWriterBuilder<Product>()
    //                     .itemSqlParameterSourceProvider(new BeanPropertyItemSqlParameterSourceProvider<>())
    //                     .sql("INSERT INTO product (main_cat, title, asin, category,imageURLHighRes) VALUES (?)")
    //                     .dataSource(dataSource)
    //                     .build();
    // }

    @Bean
    public RepositoryItemWriter<Product> crudWriter(){
        //RepositoryItemWriter<Product> writer = new RepositoryItemWriter<>();
        RepositoryItemWriter<Product> writer = new CrudWriter();
        writer.setRepository(productRepository);
        //writer.setMethodName("saveAll");
        return writer;
    }

    // @Bean
    // protected Step processProducts(ItemReader<JsonNode> reader, ItemProcessor<JsonNode, Product> processor, ItemWriter<Product> writer) {
    //     return stepBuilderFactory.get("processProducts").<JsonNode, Product>chunk(20)
    //             .reader(reader)
    //             .processor(processor)
    //             .writer(writer)
    //             .taskExecutor(taskExecutor())
    //             .build();
    // }

    // @Bean
    // protected Step processProductsJdbc(ItemReader<JsonNode> reader, ItemProcessor<JsonNode, Product> processor, JdbcBatchItemWriter<Product> writer) {
    //     return stepBuilderFactory.get("processProducts").<JsonNode, Product>chunk(20)
    //             .reader(reader)
    //             .processor(processor)
    //             .writer(writer)
    //             .taskExecutor(taskExecutor())
    //             .build();
    // }
    
    @Bean
    public Step processProductsCrud(ItemReader<JsonNode> reader, ItemProcessor<JsonNode, Product> processor,RepositoryItemWriter<Product> writer){
        return stepBuilderFactory.get("processProductCrud").<JsonNode,Product>chunk(20)
                    .reader(reader)
                    .processor(processor)
                    .writer(writer)
                    .taskExecutor(taskExecutor())
                    .build();
    }

    // @Bean
    // public Job chunksJob(Step processProducts) {
    //     return jobBuilderFactory
    //             .get("chunksJob")
    //             //.start(processProducts(itemReader(), itemProcessor(), itemWriter()))
    //             .flow(processProducts)
    //             .end()
    //             .build();
    // // return jobBuilderFactory
    // // .get("chunksJob")
    // // .start(processProducts(itemReader(), itemProcessor(), itemWriter()))
    // // .build();
    // }

    // @Bean
    // public Job chunksJobJdbc(JobCompletionNotificationListener listener, Step processProductsJdbc) {
    //     return jobBuilderFactory.get("chunksJobJdbc")
    //             .incrementer(new RunIdIncrementer())
    //             .listener(listener)
    //             .flow(processProductsJdbc)
    //             .end()
    //             .build();
    // }

    @Bean 
    public Job chunkJobCrud(JobCompletionNotificationListener listener, Step processProductsCrud) throws Exception{
        return jobBuilderFactory.get("chunkJobCrud").incrementer(new RunIdIncrementer())
            .listener(listener)
            .start(processProductsCrud)
            .build();
    }

    @Bean
    public TaskExecutor taskExecutor() {
        ThreadPoolTaskExecutor executor = new ThreadPoolTaskExecutor();
        executor.setCorePoolSize(4);
        executor.setMaxPoolSize(8);
        executor.setQueueCapacity(20);
        return executor;
    }

}
