package com.srajgopalan.camel.process.jdbc;

import org.apache.camel.Exchange;

public class SimpleJDBCInsertProcessor implements org.apache.camel.Processor {

public static int count = 0;

    public void process(Exchange exchange) throws Exception {

        count ++;

        String payload  = exchange.getIn().getBody(String.class);

        System.out.println("Payload:"+payload);
        String insertSQL = "INSERT INTO kafka_messages values ('"+count+"','" + payload + "')";
        System.out.println("Insert statement:"+insertSQL);

        exchange.getIn().setBody(insertSQL);
    }
}
