import com.srajgopalan.camel.route.KafkaReadRoute;
import org.apache.camel.Exchange;
import org.apache.camel.builder.RouteBuilder;
import org.apache.camel.test.junit4.CamelTestSupport;
import org.junit.Test;

public class KafkaReadRouteTest extends CamelTestSupport {

    protected RouteBuilder createRouteBuilder(){
        return new KafkaReadRoute();
    }

    @Test
    public void checkKafkaRead(){
        String expected = "I am a Kafka message";

        Exchange exchange = consumer.receive("direct:kafkaOutput");
        String payload = (String) exchange.getIn().getBody();

        assertEquals(expected, payload);
    }
}


