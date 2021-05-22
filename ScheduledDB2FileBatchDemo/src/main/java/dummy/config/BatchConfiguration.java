package dummy.config;

import java.util.Collections;

import javax.sql.DataSource;

import org.springframework.batch.core.Job;
import org.springframework.batch.core.JobExecutionListener;
import org.springframework.batch.core.Step;
import org.springframework.batch.core.configuration.annotation.JobBuilderFactory;
import org.springframework.batch.core.configuration.annotation.StepBuilderFactory;
import org.springframework.batch.core.launch.support.RunIdIncrementer;
import org.springframework.batch.item.ItemProcessor;
import org.springframework.batch.item.ItemReader;
import org.springframework.batch.item.database.Order;
import org.springframework.batch.item.database.builder.JdbcPagingItemReaderBuilder;
import org.springframework.batch.item.xml.StaxEventItemWriter;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.core.io.FileSystemResource;
import org.springframework.oxm.jaxb.Jaxb2Marshaller;

import dummy.Coffee;
import dummy.CoffeeMapper;
import dummy.JobCompletionListener;

@Configuration
public class BatchConfiguration {
	
	@Autowired
	private JobBuilderFactory jobBuilderFactory;
	
	@Value("file.input")
	private String fileinput;
	
	@Autowired
	private StepBuilderFactory stepBuilderFactory;
	
	@Bean
	public Job readDb() {
		return jobBuilderFactory.get("readDb").incrementer(new RunIdIncrementer())
				.listener(listener())
				.flow(step1()).end().build(); 
	}
	
	@Bean
	public Step step1() {
		return stepBuilderFactory.get("step1")
				.<Coffee,Coffee>chunk(1000)
				.reader(syncReader(null))
				.processor(syncProcessor())
				.writer(syncWriter())
				.build();
	}
	
	@Bean
    public ItemProcessor<Coffee, Coffee> syncProcessor() { 
        return (transaction) -> { 
            Thread.sleep(1);
            return transaction;
        };
    }
	
	@Bean
    public ItemReader<Coffee> syncReader(DataSource dataSource) {

        return new JdbcPagingItemReaderBuilder<Coffee>()
                .name("Reader")
                .dataSource(dataSource)
                .selectClause("SELECT * ")
                .fromClause("FROM COFFEE ")
                .whereClause("WHERE COFFEE_ID <= 1000000 ")
                .sortKeys(Collections.singletonMap("COFFEE_ID", Order.ASCENDING))
                .rowMapper(new CoffeeMapper())
                .build();
    }
	
	@Bean
	  public StaxEventItemWriter<Coffee> syncWriter() {
	    StaxEventItemWriter<Coffee> writer = new StaxEventItemWriter<>();
	    writer.setResource(new FileSystemResource("coffee.xml")); 
	    writer.setMarshaller(studentUnmarshaller());
	    writer.setRootTagName("coffees");
	    return writer;
	  }
	 
	@Bean
	  public Jaxb2Marshaller studentUnmarshaller() {
		  Jaxb2Marshaller unMarshaller = new Jaxb2Marshaller();
	    unMarshaller.setClassesToBeBound(Coffee.class); 
	    return unMarshaller;
	  }
	
	@Bean
	public JobExecutionListener listener() {
		return new JobCompletionListener();
	}
	


}

