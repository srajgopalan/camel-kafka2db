import com.srajgopalan.camel.route.Kafka2JdbcRoute;
import org.apache.camel.CamelContext;
import org.apache.camel.builder.RouteBuilder;
import org.apache.camel.impl.DefaultCamelContext;
import org.apache.camel.impl.SimpleRegistry;
import org.apache.camel.test.junit4.CamelTestSupport;
import org.apache.commons.dbcp.BasicDataSource;
import org.junit.Test;

import javax.sql.DataSource;
import java.util.ArrayList;
import java.util.List;

public class Kafka2JdbcRouteTest extends CamelTestSupport {

    @Override
    protected RouteBuilder createRouteBuilder(){
        return new Kafka2JdbcRoute();
    }

    @Override
    public CamelContext createCamelContext() throws Exception {
        //this is where we will create the datasource
        String url = "jdbc:postgresql://localhost:5432/localdb";
        DataSource dataSource = setupDataSource(url);

        SimpleRegistry registry = new SimpleRegistry();
        registry.put("myDataSource",dataSource);

        return new DefaultCamelContext(registry);
    }

    private static DataSource setupDataSource(String url){
        BasicDataSource ds = new BasicDataSource();
        ds.setUsername("srajgopalan");
        ds.setPassword("srajgopalan");
        ds.setDriverClassName("org.postgresql.Driver");
        ds.setUrl(url);

        System.out.println( "returning datasource");

        return ds;
    }

    @Test
    public void checkRoute(){

        List response = consumer.receiveBody("direct:jdbcOutput", ArrayList.class);

        System.out.println("Retrieved "+ response.size() + "records");

        assertNotEquals(response.size(),0);
    }


}
