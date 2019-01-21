package hanze.nl.mockdatabaselogger;

import com.thoughtworks.xstream.XStream;

import javax.jms.JMSException;
import javax.jms.Message;
import javax.jms.MessageConsumer;
import javax.jms.TextMessage;

class Messages {

    private MessageConsumer consumer;
    private boolean newMessage;
    private int aantalBerichten = 0;
    private int aantalETAs = 0;

    Messages(MessageConsumer consumer) {
        this.consumer = consumer;
    }

    void handleMessage() {

        newMessage = false;

        try {
            Message message = consumer.receive(2000);

            if (message instanceof TextMessage) {
                TextMessage textMessage = (TextMessage) message;
                String text = textMessage.getText();
                newMessage = true;

                Bericht bericht = maakBericht(text);

                aantalBerichten++;
                aantalETAs += bericht.ETAs.size();
            } else {
                System.out.println("Received: " + message);
            }
        } catch (JMSException e) {
            e.printStackTrace();
        }
    }

    boolean getNewMessage() {
        return newMessage;
    }

    void printAantalBerichten() {
        System.out.println(aantalBerichten + " berichten met " + aantalETAs + " ETAs verwerkt.");
    }

    private Bericht maakBericht(String text) {
        XStream xstream = new XStream();
        xstream.alias("Bericht", Bericht.class);
        xstream.alias("ETA", ETA.class);
        return (Bericht) xstream.fromXML(text);
    }
}
