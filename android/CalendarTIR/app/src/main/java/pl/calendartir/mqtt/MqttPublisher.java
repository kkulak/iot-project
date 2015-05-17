package pl.calendartir.mqtt;

import android.util.Log;

import org.eclipse.paho.client.mqttv3.MqttClient;
import org.eclipse.paho.client.mqttv3.MqttConnectOptions;
import org.eclipse.paho.client.mqttv3.MqttException;
import org.eclipse.paho.client.mqttv3.MqttMessage;
import org.eclipse.paho.client.mqttv3.persist.MemoryPersistence;

public class MqttPublisher {

    private final String led1Topic = "api/led1";
    private String on = "1";
    private String off = "0";
    private String broker = "tcp://iot.eclipse.org:1883";
    MqttClient sampleClient;
    MqttConnectOptions connOpts;

    public MqttPublisher() {
        try {
            String clientId = "IoT";
            MemoryPersistence persistence = new MemoryPersistence();
            this.sampleClient = new MqttClient(broker, clientId, persistence);
            connOpts = new MqttConnectOptions();
            connOpts.setCleanSession(true);
        } catch (MqttException e) {
            e.printStackTrace();
        }
    }

    public void startCoffee() {
        try {
            Log.d("mqtt", "Connecting to broker: " + broker);
            sampleClient.connect(connOpts);
            Log.d("mqtt", "Connected");

            MqttMessage message = new MqttMessage(on.getBytes());
            sampleClient.publish(led1Topic, message);
            Log.d("mqtt", "Message published");
            sampleClient.disconnect();
            Log.d("mqtt", "Disconnected");
        } catch (MqttException me) {
            Log.d("mqtt", me.getMessage());
        }

    }
}