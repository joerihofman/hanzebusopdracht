package hanze.nl.bussimulator;

import javax.jms.Connection;
import javax.jms.ConnectionFactory;
import javax.jms.Destination;
import javax.jms.JMSException;
import javax.jms.MessageProducer;
import javax.jms.Session;
import javax.jms.TextMessage;

import org.apache.activemq.ActiveMQConnectionFactory;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class Producer {

    private static final Logger logger = LoggerFactory.getLogger(Producer.class);

    private String bericht;
    private Session session;

    public Producer(String bericht) {
        this.bericht = bericht;
    }

    public void verstuur() {
        Connection connection;

        try {
            ConnectionFactory connectionFactory = new ActiveMQConnectionFactory("tcp://localhost:61616");
            connection = connectionFactory.createConnection();
            connection.start();

            session = connection.createSession(false, Session.AUTO_ACKNOWLEDGE);

            verstuurXmlBericht();
        } catch (JMSException e) {
            logger.error("Connectie is niet gemaakt", e);
        }
    }

    private void verstuurXmlBericht() throws JMSException {
        Destination destination = session.createQueue("QueueBuzz");
        MessageProducer producer = session.createProducer(destination);
        TextMessage message = session.createTextMessage(this.bericht);

        producer.send(message);

        String logMessage = String.format("Bericht is verstuurd: %s", message);
        logger.info(logMessage);
    }


}
