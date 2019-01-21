package hanze.nl.mockdatabaselogger;

import javax.jms.*;


public class ArrivaLogger {

    public static void main (String[] args){
        ArrivaConnection arrivaConnection = new ArrivaConnection();
        arrivaConnection.createConsumer();

        MessageConsumer consumer = arrivaConnection.getConsumer();
        Messages messages = new Messages(consumer);

        boolean newMessage = true;

        while (newMessage) {
            messages.handleMessage();
            newMessage = messages.getNewMessage();
        }

        arrivaConnection.closeAll();
        messages.printAantalBerichten();
    }

}
