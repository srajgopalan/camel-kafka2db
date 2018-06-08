package com.srajgopalan.camel.route;

import com.srajgopalan.camel.exception.SimpleExceptionhandlerProcessor;
import com.srajgopalan.camel.process.jdbc.SimpleJDBCInsertProcessor;
import org.apache.camel.builder.RouteBuilder;
import org.postgresql.util.PSQLException;

public class Kafka2JdbcRoute extends RouteBuilder {
    public void configure() throws Exception {

        onException(PSQLException.class).handled(true).redeliveryDelay(2000).maximumRedeliveries(3)
                .log("Exception Encountered while inserting messages to DB")
                .process(new SimpleExceptionhandlerProcessor() );

        from("kafka:my-topic?brokers=localhost:9092&groupId=group1&consumersCount=1&autoOffsetReset=latest")
                .to("log:?level=INFO&showBody=true")
                .process(new SimpleJDBCInsertProcessor())
                .to("jdbc:myDataSource")
                .to("sql:select * from kafka_messages?dataSource=myDataSource")
                .to("log:?level=INFO&showBody=true");
        //        .to("direct:jdbcOutput");
        //uncomment the direct compponent only for testing, otherwise will throw an error
        //if used for the running app
    }
}
