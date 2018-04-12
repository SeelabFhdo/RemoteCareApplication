package de.fhdortmund.service.constant;

/**
 * Created by jonas on 19.10.16.
 */
public class MQTTConstants {
    public static final String BASE_TOPIC  = "/controlservice";
    public static final String REGISTER_TOPIC = BASE_TOPIC + "/register";
    public static final String CLIENT_NAME = "Controlservice";
    public static final String URL = "ssl://localhost:8883";
    public static final String USERNAME = "openhab1";
    public static final String PASSWORD = "password";
}
