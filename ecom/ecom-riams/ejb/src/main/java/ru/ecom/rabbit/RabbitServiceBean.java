package ru.ecom.rabbit;

import com.google.gson.GsonBuilder;
import org.apache.log4j.Logger;
import ru.ecom.api.form.PromedPolyclinicTapForm;

import javax.ejb.Local;
import javax.ejb.Remote;
import javax.ejb.Stateless;

@Stateless
@Remote(IRabbitService.class)
@Local(IRabbitService.class)
public class RabbitServiceBean implements IRabbitService {
    private static final Logger log = Logger.getLogger(RabbitServiceBean.class);

    private final static String PROMED_EXCHANGE_NAME = "PROMEDATOR_QUEUE";
//    private Channel channel;

    private boolean isEnabled = false;

/*    public RabbitServiceBean() {
        try {
            ConnectionFactory factory = new ConnectionFactory();
            factory.setUri("amqp://oog:oog@127.0.0.1:5672");
            try (Connection connection = factory.newConnection();
            ) {
                channel = connection.createChannel();
                channel.queueDeclare(PROMED_EXCHANGE_NAME, false, false, false, null);
                String helloMessage = "Hello Rabbit, your JBOSS is coming";
                channel.basicPublish("", PROMED_EXCHANGE_NAME, null, helloMessage.getBytes(StandardCharsets.UTF_8));
                isEnabled = true;
            } catch (IOException | TimeoutException e) {
                channel = null;
                e.printStackTrace();
            }
        } catch (Exception e) {
            log.error("Error sending message2 " + e.getMessage(), e);
        }
    }*/

    @Override
    public void sendPromedPolycMessage(PromedPolyclinicTapForm promedPolyclinicTapForm) {
        log.info("start sending pol: " + promedPolyclinicTapForm);
//        if (isEnabled) {
        publishMessage(toString(promedPolyclinicTapForm));
//        }
    }

    private String toString(PromedPolyclinicTapForm form) {
        return new GsonBuilder().setDateFormat("yyyy-MM-dd").create().toJson(form);
    }

    @Override
    public void sendPromedHospitalMessage(Object someObject) {
        log.info("start sending stac: " + someObject);
    }

    private void publishMessage(String message) {
        log.warn("========== message: " + message);
      /*  try {
            channel.basicPublish("", PROMED_EXCHANGE_NAME, null, message.getBytes(StandardCharsets.UTF_8));
        } catch (IOException e) {
            log.error("Error sending message1 " + e.getMessage(), e);
            e.printStackTrace();
        }*/
    }
}
