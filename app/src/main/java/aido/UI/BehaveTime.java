package aido.UI;
/*
                        Intent intent = new Intent();
                        intent.setAction(AidoFace.PROP_HTTPDOWNLOAD_INTENTID);
                        intent.putExtra(AidoFace.HTTPDOWNLOAD_MESSSAGEFIELD, ui);
                        intent.addFlags(Intent.FLAG_INCLUDE_STOPPED_PACKAGES);
                        _maincontext.sendBroadcast(intent);

 */
/**
 * Created by sumeendranath on 07/09/16.
 */
public class BehaveTime {

    long _guidance = 0;

    double _factor = 0;

    public BehaveTime(int guidance) {
        _guidance = guidance;
        _guidance = _guidance * 1000; /// millis
        _factor = _guidance/8;
    }

    public long getAnticipateTime(int override)
    {
        if(override > 0)
        {
            return _guidance * override / 100;
        }
        return (long) (_factor);
    }

    public long getEaseInTime(int override)
    {
        if(override > 0)
        {
            return _guidance * override / 100;
        }
        return (long) (_factor * 0.5d);
    }
    public long getKeyStageTime(int override)
    {
        if(override > 0)
        {
            return _guidance * override / 100;
        }
        return (long) (_factor * 5.0d);
    }
    public long getEaseOutTime(int override)
    {
        if(override > 0)
        {
            return _guidance * override / 100;
        }

        return (long) (_factor * 0.5);
    }
    public long getFollowThroughTime(int override)
    {
        if(override > 0)
        {
            return _guidance * override / 100;
        }

        return (long) (_factor );
    }



}
