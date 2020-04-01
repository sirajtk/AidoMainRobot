package aido.properties;

import java.util.Collections;
import java.util.HashMap;
import java.util.Map;

/**
 * Created by sumeendranath on 15/02/17.
 */
public class AlertBehaviorProperties {


    public static int None                  = -1 ;
    public static int FaceRecognition       = 0 ;
    public static int FaceDetection         = 1 ;
    public static int FaceTracking          = 2 ;
    public static int HELLO        = 3;
   // public static int EmotionRecognition    = 3 ;
    public static int TemperatureSensor     = 4 ;
    public static int Airquality            = 5 ;
    public static int HeadProjector_ON      = 6 ;
    public static int HeadProjector_OFF     = 7 ;
    public static int MainProjector_ON      = 8 ;
    public static int MainProjector_OFF     = 9 ;
    public static int PiCamera_ON           = 10 ;
    public static int SkypeCall_Receive     = 11 ;
    public static int SkypeCall_Make        = 12 ;
    public static int Email_Received        = 13 ;
    public static int InternetConnected     = 14 ;
    public static int Battery_Low           = 15 ;
    public static int Battery_Full          = 16 ;
    public static int Shutdown              = 17 ;
    public static int ShowingNotifications  = 18 ;
    public static int idle                  = 19 ;
    public static int scene                  = 20 ;
    public static int shopping                  = 21 ;
    public static int LEDA_ON                  = 22 ;
    public static int LEDA_OFF                  = 23 ;
    public static int PressureSensor            = 24 ;
    public static int PowerRemaining            = 26 ;

    public static int directrun                  = 100 ;





    public static final Map<Integer, String> ALERTBEHAVIORHASH;

    static {
        Map<Integer, String> aMap = new HashMap<Integer, String>();

        aMap.put(FaceRecognition,"facerecognition.txt");
        aMap.put(FaceDetection,"facedetection");
        aMap.put(FaceTracking,"facetracking");
        //aMap.put(EmotionRecognition,"emotionrecognition");
        aMap.put(HELLO,"hello");
        aMap.put(TemperatureSensor,"temperaturesensorreading");
        aMap.put(Airquality,"airqualitysensorreading");
        aMap.put(HeadProjector_ON,"headprojectoron");
        aMap.put(HeadProjector_OFF,"headprojectoroff");
        aMap.put(MainProjector_ON,"mainprojectoron");
        aMap.put(MainProjector_OFF,"mainprojectoroff");
        aMap.put(PiCamera_ON,"picameraon");
        aMap.put(SkypeCall_Receive,"receivingskypecall");
        aMap.put(SkypeCall_Make,"callingskype");
        aMap.put(Email_Received,"emailreceived");
        aMap.put(InternetConnected,"internetconnected");
        aMap.put(Battery_Low,"lowbattery");
        aMap.put(Battery_Full,"batteryfull");
        aMap.put(Shutdown,"shutdownmsg");
        aMap.put(ShowingNotifications,"notification");
        aMap.put(idle,"idle.txt");
        aMap.put(scene,"scene");
        aMap.put(directrun,"");
        aMap.put(shopping,"shopping");
        aMap.put(LEDA_ON,"ledaon");
        aMap.put(LEDA_OFF,"ledaoff");
        aMap.put(PressureSensor,"pressuresensor");
        aMap.put(Airquality,"airquality");
        aMap.put(PowerRemaining,"poweremaining");



        ALERTBEHAVIORHASH = Collections.unmodifiableMap(aMap);
    }


    public static final Map<Integer, Integer> ALERTBEHAVIORPRIORITY;

    static {
        Map<Integer, Integer> aMap = new HashMap<Integer, Integer>();

        aMap.put(Shutdown,100);
        aMap.put(Battery_Low,50);
        aMap.put(directrun,40);
        aMap.put(InternetConnected,31);
        aMap.put(scene,30);
        aMap.put(SkypeCall_Receive,25);
        aMap.put(SkypeCall_Make,25);
        aMap.put(Email_Received,24);
        aMap.put(LEDA_ON,24);
        aMap.put(LEDA_OFF,24);
        aMap.put(shopping,23);
        aMap.put(ShowingNotifications,22);
        aMap.put(HeadProjector_ON,21);
        aMap.put(HeadProjector_OFF,21);
        aMap.put(MainProjector_ON,21);
        aMap.put(MainProjector_OFF,21);
        aMap.put(FaceRecognition,20);
        //aMap.put(EmotionRecognition,19);
        aMap.put(HELLO,19);
        aMap.put(FaceDetection,18);
        aMap.put(FaceTracking,10);
        aMap.put(TemperatureSensor,8);
        aMap.put(PowerRemaining,8);
        aMap.put(Airquality,8);
        aMap.put(PressureSensor,7);
        aMap.put(PiCamera_ON,6);
        aMap.put(idle,0);


        ALERTBEHAVIORPRIORITY = Collections.unmodifiableMap(aMap);
    }


    public static final Map<Integer, String> ALERTBEHAVIORVARIABLE;

    static {
        Map<Integer, String> aMap = new HashMap<Integer, String>();

        aMap.put(Shutdown,"aSHUTDOWNa");
        aMap.put(Battery_Low,"aBATTERYLOWa");
        aMap.put(directrun,"aDIRECTRUNa");
        aMap.put(InternetConnected,"aINTERNETCONNECTEDa");
        aMap.put(scene,"aSCENEa");
        aMap.put(SkypeCall_Receive,"aSKYPERECEIVEa");
        aMap.put(SkypeCall_Make,"aSKYPEMAKEa");
        aMap.put(Email_Received,"aEMAILRECEIVEa");
        aMap.put(ShowingNotifications,"aSHOWNOTIFIATIONSa");
        aMap.put(HeadProjector_ON,"aHEADPROJECTORONa");
        aMap.put(HeadProjector_OFF,"aHEADPROJECTOROFFa");
        aMap.put(MainProjector_ON,"aMAINPROJECTORONa");
        aMap.put(MainProjector_OFF,"aMAINPROJECTOROFFa");
        aMap.put(FaceRecognition,"aFACERECOGa");
        aMap.put(HELLO,"aHELLOa");
        //aMap.put(EmotionRecognition,"aEMOTIONRECOGa");
        aMap.put(FaceDetection,"aFACEDETECTa");
        aMap.put(FaceTracking,"aFACETRACKa");
        aMap.put(TemperatureSensor,"aTEMPSENSORa");
        aMap.put(Airquality,"aAIRQUALITYa");
        aMap.put(PiCamera_ON,"aPICAMERAa");

        aMap.put(PowerRemaining,"aPOWERREMAINa");
        aMap.put(PressureSensor,"aPRESSUREa");


        aMap.put(idle,"aIDLEa");


        ALERTBEHAVIORVARIABLE = Collections.unmodifiableMap(aMap);
    }





    public static String getDefaultPersonalityFile()
    {
        String name= getPeronalityPath() + "personality1.csv";

        return name;

    }


    public static String getPeronalityPath()
    {
        String name= StorageProperties.getRootDirectory();

        return name;

    }



    public static boolean checkIfBehaviorNameMatchesRequest(int behavecode, String requestedBehavior)
    {

        if(!ALERTBEHAVIORHASH.containsKey(behavecode))
        {
            return false;
        }


        String behaviorfile = ALERTBEHAVIORHASH.get(behavecode);

        return behaviorfile.equalsIgnoreCase(requestedBehavior);
    }
    
    
    
    
    //// object definitions

    public static String _lastFaceRecognition       = "" ;
    public static  String _lastFaceDetection         = "" ;
    public static  String _lastFaceTracking          = "" ;
    public static  String _lastEmotionRecognition    = "" ;
    public static  String _lastTemperatureSensor     = "" ;
    public static  String _lastAirquality            = "" ;
    public static  String _lastPowerRemaining            = "" ;
    public static  String _lastPressure            = "" ;
    public static  String _lastHeadProjector_ON      = "" ;
    public static  String _lastHeadProjector_OFF     = "" ;
    public static  String _lastMainProjector_ON      = "" ;
    public static  String _lastMainProjector_OFF     = "" ;
    public static  String _lastPiCamera_ON           = "" ;
    public static  String _lastSkypeCall_Receive     = "" ;
    public static  String _lastSkypeCall_Make        = "" ;
    public static  String _lastEmail_Received        = "" ;
    public static  String _lastInternetConnected     = "" ;
    public static  String _lastBattery_Low           = "" ;
    public static  String _lastBattery_Full          = "" ;
    public static  String _lastShutdown              = "" ;
    public static  String _lastShowingNotifications  = "" ;
    public static  String _lastidle                  = "" ;
    public static  String _lastscene                  = "" ;





}
