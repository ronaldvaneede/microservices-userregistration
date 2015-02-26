package registrationservice.notifications;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class Receiver {

    private static final Logger logger = LoggerFactory.getLogger(Receiver.class);
    
    public void receiveMessage(String notification) {
        logger.info("Received notification for <" + notification + ">");
    }
}