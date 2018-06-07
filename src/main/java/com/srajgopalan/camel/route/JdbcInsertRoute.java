package com.srajgopalan.camel.route;

import com.srajgopalan.camel.process.jdbc.SimpleJDBCInsertProcessor;
import org.apache.camel.builder.RouteBuilder;

public class JdbcInsertRoute extends RouteBuilder {
    public void configure() throws Exception {

        from("direct:jdbcInput")
                .to("log:?level=INFO&showBody=true")
                .process(new SimpleJDBCInsertProcessor())
                .to("jdbc:myDataSource")
                .to("sql:select * from kafka_messages?dataSource=myDataSource")
                .to("log:?level=INFO&showBody=true");
    }
}
