package aido.motor;

import android.content.SharedPreferences;
import android.preference.PreferenceManager;

import aido.common.CommonlyUsed;
import aido.properties.ControllerProperties;

/**
 * Created by sumeendranath on 15/02/17.
 */
public class MotorExecute {

    String pan="200";
    String tilt = "150";
    String speed = "15";

    String prevpan = "0";
    String prevtilt = "0";


    public MotorExecute(String pp, String pt,String p, String t, String sp) {

        pan = p;
        tilt = t;
        speed = sp;

        prevpan = pp;
        prevtilt = pt;


        checkVals();

        tuneValuesPan();
        tuneValuesTilt();

        contraintValuesPan();
        contraintValuesTilt();


    }


    public void run()
    {

        String cmd = "";
        if(pan.equalsIgnoreCase("0") && tilt.equalsIgnoreCase("0"))
        {
            /// do nothing

            return;
        }


        if(ControllerProperties.SPEED_ALWAYS_DEFAULT)
        {
            speed = ControllerProperties.DEFAULT_SPEED;
        }

        if(pan.equalsIgnoreCase("0"))
        {
            cmd = String.format(ControllerProperties.CONTROLLER_TILT,tilt,speed);
        }
        if(tilt.equalsIgnoreCase("0"))
        {
            cmd = String.format(ControllerProperties.CONTROLLER_PAN,pan,speed);
        }

        if(!pan.equalsIgnoreCase("0") && !tilt.equalsIgnoreCase("0"))
        {
            cmd = String.format(ControllerProperties.CONTROLLER_PANTILT,pan,tilt,speed);
        }


        SystemCommand.exec(cmd);


    }


    public String getPanForStorage()
    {
        if(pan.equalsIgnoreCase("0"))
        {
                return prevpan;
        }

        return pan;
    }

    public String getTiltForStorage()
    {
        if(tilt.equalsIgnoreCase("0"))
        {
            return prevtilt;
        }

        return tilt;
    }

    void tuneValuesPan()
    {
        if(pan.startsWith("HOME"))
        {
            pan=ControllerProperties.HOME_PAN + "";
            return;
        }


        if(pan.equalsIgnoreCase("0"))
        {
            return;
        }


        if(pan.startsWith("abs:"))
        {
            pan = pan.replace("abs:","");
            int panint = ControllerProperties.MIN_PAN + ControllerProperties.PAN_FACTOR * CommonlyUsed.getIntegerValueFromString(pan);

            pan = "" + panint;

            return;
        }


        int panint = CommonlyUsed.getIntegerValueFromString(prevpan) + ControllerProperties.PAN_FACTOR * CommonlyUsed.getIntegerValueFromString(pan);

        pan = "" + panint;

        return;

    }



    void contraintValuesPan()
    {
        if(pan.equalsIgnoreCase("0"))
        {
            return;
        }

        if(CommonlyUsed.getIntegerValueFromString(pan) > ControllerProperties.MAX_PAN)
        {
            pan = "" + ControllerProperties.MAX_PAN;
        }
        if(CommonlyUsed.getIntegerValueFromString(pan) < ControllerProperties.MIN_PAN)
        {
            pan = "" + ControllerProperties.MIN_PAN;
        }

    }

    void contraintValuesTilt()
    {
        if(tilt.equalsIgnoreCase("0"))
        {
            return;
        }

        if(CommonlyUsed.getIntegerValueFromString(tilt) > ControllerProperties.MAX_TILT)
        {
            tilt = "" + ControllerProperties.MAX_TILT;
        }
        if(CommonlyUsed.getIntegerValueFromString(tilt) < ControllerProperties.MIN_TILT)
        {
            tilt = "" + ControllerProperties.MIN_TILT;
        }

    }


    void tuneValuesTilt()
    {
        if(tilt.startsWith("HOME"))
        {
            tilt=ControllerProperties.HOME_TILT + "";
            return;
        }

        if(tilt.equalsIgnoreCase("0"))
        {
            return;
        }



        if(tilt.startsWith("abs:"))
        {
            tilt = tilt.replace("abs:","");
            int tiltint = ControllerProperties.MIN_TILT + ControllerProperties.TILT_FACTOR * CommonlyUsed.getIntegerValueFromString(tilt);

            tilt = "" + tiltint;

            return;
        }


        int tiltint = CommonlyUsed.getIntegerValueFromString(prevtilt) + ControllerProperties.TILT_FACTOR * CommonlyUsed.getIntegerValueFromString(tilt);

        tilt = "" + tiltint;

        return;

    }




    void checkVals()
    {
        if(CommonlyUsed.stringIsNullOrEmpty(pan))
        {
            pan = "0";
        }
        if(CommonlyUsed.stringIsNullOrEmpty(tilt))
        {
            tilt = "0";
        }
        if(CommonlyUsed.stringIsNullOrEmpty(speed))
        {
            speed = "15";
        }

    }


    public  static void reset()
    {
        SystemCommand.exec(ControllerProperties.CONTROLLER_RESET);
    }




}
