package aido.properties;

import java.util.Collections;
import java.util.HashMap;
import java.util.Map;

/**
 * Created by sumeendranath on 15/02/17.
 */
public class ControllerProperties {
    public static String LOGTAG = "MOTORANDROID";

    public static boolean DEBUG = false;
    public static boolean MOTOR_CONTROLLER_ANDROID = false;
    public static boolean CONTROLLER_ENABLED = true;
    public static boolean SPEED_ALWAYS_DEFAULT = true;

    public static String DEFAULT_SPEED = "15";



    public static int FIELD_CONTROLLER_RESET                            = 0;
    public static int FIELD_CONTROLLER_LEDA_ON                          = 1;
    public static int FIELD_CONTROLLER_LEDA_OFF                         = 2;
    public static int FIELD_CONTROLLER_AUDIO_ON                         = 3;
    public static int FIELD_CONTROLLER_AUDIO_OFF                        = 4;
    public static int FIELD_CONTROLLER_BACKLIT_ON                       = 5;
    public static int FIELD_CONTROLLER_BACKLIT_OFF                      = 6;
    public static int FIELD_CONTROLLER_HEADPROJECTOR_ON                 = 7;
    public static int FIELD_CONTROLLER_HEADPROJECTOR_OFF                = 8;
    public static int FIELD_CONTROLLER_BODYPROJECTOR_ON                 = 9;
    public static int FIELD_CONTROLLER_BODYPROJECTOR_OFF                = 10;


    public static String CONTROLLER_EXE_LINUX = "sudo /home/sarath/i2c_new/i2c/travel/controller/controller";
    public static String CONTROLLER_EXE_ODROID = "/data/local/tmp/controller";

    public static boolean EXECUTION_IN_ODROID = true;



    public static String CONTROLLER_EXE = CONTROLLER_EXE_ODROID;//CONTROLLER_EXE_LINUX;
    public static String CONTROLLER_RESET= CONTROLLER_EXE + " reset=1";
    public static String CONTROLLER_LEDA_ON= CONTROLLER_EXE + " led_a=1";
    public static String CONTROLLER_LEDA_OFF= CONTROLLER_EXE + " led_a=0";
    public static String CONTROLLER_AUDIO_ON= CONTROLLER_EXE + " audio_mute=0";
    public static String CONTROLLER_AUDIO_OFF= CONTROLLER_EXE + " audio_mute=1";
    public static String CONTROLLER_BACKLIT_ON= CONTROLLER_EXE + " backlit=1";
    public static String CONTROLLER_BACKLIT_OFF= CONTROLLER_EXE + " backlit=0";
    public static String CONTROLLER_HEADPROJECTOR_ON= CONTROLLER_EXE + " head=1";
    public static String CONTROLLER_HEADPROJECTOR_OFF= CONTROLLER_EXE + " head=0";
    public static String CONTROLLER_BODYPROJECTOR_ON= CONTROLLER_EXE + " body=1";
    public static String CONTROLLER_BODYPROJECTOR_OFF= CONTROLLER_EXE + " body=0";



    public static final Map<Integer, String> CONTROLLERFUNCTIONS;

    static {
        Map<Integer, String> aMap = new HashMap<Integer, String>();

        aMap.put(FIELD_CONTROLLER_RESET,CONTROLLER_RESET);
        aMap.put(FIELD_CONTROLLER_LEDA_ON,CONTROLLER_LEDA_ON);
        aMap.put(FIELD_CONTROLLER_LEDA_OFF,CONTROLLER_LEDA_OFF);
        aMap.put(FIELD_CONTROLLER_AUDIO_ON,CONTROLLER_AUDIO_ON);
        aMap.put(FIELD_CONTROLLER_AUDIO_OFF,CONTROLLER_AUDIO_OFF);
        aMap.put(FIELD_CONTROLLER_BACKLIT_ON,CONTROLLER_BACKLIT_ON);
        aMap.put(FIELD_CONTROLLER_BACKLIT_OFF,CONTROLLER_BACKLIT_OFF);
        aMap.put(FIELD_CONTROLLER_HEADPROJECTOR_ON,CONTROLLER_HEADPROJECTOR_ON);
        aMap.put(FIELD_CONTROLLER_HEADPROJECTOR_OFF,CONTROLLER_HEADPROJECTOR_OFF);
        aMap.put(FIELD_CONTROLLER_BODYPROJECTOR_ON,CONTROLLER_BODYPROJECTOR_ON);
        aMap.put(FIELD_CONTROLLER_BODYPROJECTOR_OFF,CONTROLLER_BODYPROJECTOR_OFF);

        CONTROLLERFUNCTIONS = Collections.unmodifiableMap(aMap);
    }





    /*
./controller pan=0 , range 0 - 4095
./controller tilt=0 , range 0 - 4095
./controller speed=0 , range 0 - 4095
./controller led_a=0 , range 0 - 4095
./controller led_b=0 , range 0 - 4095
./controller led_c=0 , range 0 - 4095
./controller led_d=0 , range 0 - 4095
./controller led_f=0 , range 0 - 4096
./controller audio_sw=0 , 0 or 1
./controller audio_mute=0 , 0 or 1
./controller audio_fan_a=0 , 0 or 1
./controller audio_fan_b=0 , 0 or 1
./controller freq =0 , range 0 - 4095
./controller pan_motor=0 , range 0 - 4095
./controller backlit=0 , 0 or 1

     */
    public static String CONTROLLER_PANTILT = CONTROLLER_EXE + " pan=%s tilt=%s speed=%s";
    public static String CONTROLLER_PAN = CONTROLLER_EXE + " pan=%s speed=%s";
    public static String CONTROLLER_TILT = CONTROLLER_EXE + " tilt=%s speed=%s";


    public static String PREVPAN_SHAREDPREF = "prevpan";
    public static String PREVTILT_SHAREDPREF = "prevtilt";



    //// defaults

    public static int HOME_PAN=330;
    public static int HOME_TILT=330;

    public static int MAX_PAN=440;
    public static int MIN_PAN=220;
    public static int PAN_FACTOR=2;

    public static int MAX_TILT=500;
    public static int MIN_TILT=220;
    public static int TILT_FACTOR=2;








}
