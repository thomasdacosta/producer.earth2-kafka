package kafka.producer.earth2;

import java.util.Date;
import java.util.Properties;
import java.util.Random;

import kafka.javaapi.producer.Producer;
import kafka.producer.KeyedMessage;
import kafka.producer.ProducerConfig;

public class KafkaProducer {
	
	public static void main(String[] args) {
        long events = 15000l;
        Random rnd = new Random();
 
        Properties props = new Properties();
        props.put("metadata.broker.list", "192.168.0.24:9092");
        props.put("serializer.class", "kafka.serializer.StringEncoder");
        props.put("partitioner.class", "kafka.producer.earth2.SimplePartitioner");
        props.put("request.required.acks", "1");
 
        ProducerConfig config = new ProducerConfig(props);
 
        Producer<String, String> producer = new Producer<String, String>(config);
 
        for (long nEvents = 0; nEvents < events; nEvents++) { 
               long runtime = new Date().getTime();  
               String ip = "192.168.2." + rnd.nextInt(255); 
               String msg = runtime + ",http://www.dccomics.com/blog/2014/08/18/the-map-of-the-multiverse," + ip; 
               KeyedMessage<String, String> data = new KeyedMessage<String, String>("earth2", ip, msg);
               producer.send(data);
        }
        producer.close();		
	}

}
