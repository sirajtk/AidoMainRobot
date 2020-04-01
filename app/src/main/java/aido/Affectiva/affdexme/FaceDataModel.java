package aido.Affectiva.affdexme;

import android.graphics.Point;
import android.graphics.PointF;

import com.affectiva.android.affdex.sdk.detector.Face;

import java.lang.reflect.InvocationTargetException;
import java.util.HashMap;
import java.util.Map;

/**
 * Created by sumeendranath on 24/06/16.
 */
public class FaceDataModel {


    private MetricDisplay[] _metricDisplays;
    private Face _face;


    Map<String, Float> _emotionScoreMap = new HashMap<String, Float>();


    //aMap.put("com.rovio.baba", "https://play.google.com/store/apps/details?id=com.rovio.baba&hl=en");

/*

    */


    String _dominantEmotion = "";
    Float _dominantScore = 0f;



    public FaceDataModel(MetricDisplay[] metricDisplays, Face face) {

        _metricDisplays = metricDisplays;
        _face = face;

        computeEmotions();
        computeDominantEmotion();

    }


    void computeEmotions()
    {
        for (MetricDisplay metricDisplay : _metricDisplays) {

            try {


                if(metricDisplay.getMetricToDisplay().getType() == MetricsManager.MetricType.Emotion) {
                    float score = (Float) metricDisplay.getFaceScoreMethod().invoke(_face.emotions);

                    _emotionScoreMap.put(MetricsManager.getUpperCaseName(metricDisplay.getMetricToDisplay()),score);
                }

            } catch (IllegalAccessException e) {
                e.printStackTrace();
            } catch (InvocationTargetException e) {
                e.printStackTrace();
            }

        }
    }



    void computeDominantEmotion()
    {

        _dominantEmotion = "";
        _dominantScore = 0f;
        for (Map.Entry<String, Float> entry : _emotionScoreMap.entrySet())
        {
            //System.out.println(entry.getKey() + "/" + entry.getValue());
            if(entry.getValue() > _dominantScore)
            {
                _dominantEmotion = entry.getKey();
                _dominantScore = entry.getValue();
            }
        }

    }


    public String getDominantEmotion_irrespectiveofscore()
    {

        return _dominantEmotion;
    }

    public String getDominantEmotion()
    {
        if(_dominantScore > 30f) {
            return _dominantEmotion;
        }
        else
        {
            return "";
        }
    }

    public Float getDominantScore()
    {
        return _dominantScore;
    }


    public PointF getFacePoint()
    {

        if(_face.getFacePoints().length > 0) {
            return _face.getFacePoints()[0];
        }
        else
        {
            return new PointF(10,10);
        }

    }

}
