package hanze.nl.infobord;

import org.codehaus.jackson.JsonParseException;
import org.codehaus.jackson.map.ObjectMapper;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import javax.jms.JMSException;
import javax.jms.Message;
import javax.jms.MessageListener;
import javax.jms.TextMessage;
import java.io.IOException;

public class QueueListener implements MessageListener {

    QueueListener() {
        InfoBord.getInfoBord();
    }

    private static final Logger logger = LoggerFactory.getLogger(QueueListener.class);

    @Override
    public void onMessage(Message message) {
        try {
            logger.info("halte " + message.getStringProperty("HALTE"));
            logger.info("richting " + message.getStringProperty("RICHTING"));
            TextMessage textMessage = (TextMessage) message;
            InfoBord infoBord = InfoBord.getInfoBord();


            InfoBord.verwerktBericht(makeJsonMessage(textMessage.getText()));


            infoBord.setRegels();
        } catch (JMSException e) {
            logger.error("Textmessage niet verwerkt", e);
        }
    }

    private JSONBericht makeJsonMessage(String text) {
        JSONBericht jsonBericht = null;
        try {
            jsonBericht = new ObjectMapper().readValue(text, JSONBericht.class);
        } catch (IOException e) {
            e.printStackTrace();
        }
        return jsonBericht;
    }

//TODO 	implementeer de messagelistener die het bericht ophaald en
//		doorstuurd naar verwerkBericht van het infoBord.
//		Ook moet setRegels aangeroepen worden.
}

