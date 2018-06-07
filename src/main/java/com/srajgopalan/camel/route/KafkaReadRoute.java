package com.srajgopalan.camel.route;

import org.apache.camel.builder.RouteBuilder;

public class KafkaReadRoute extends RouteBuilder {
    public void configure() throws Exception {
        from("kafka:test?brokers=localhost:9092&groupId=group1&consumersCount=1&autoOffsetReset=latest")
                .log("${body}")
                .to("direct:kafkaOutput");
    }
}
