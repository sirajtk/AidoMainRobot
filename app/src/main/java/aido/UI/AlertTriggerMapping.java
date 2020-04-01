package aido.UI;

import android.util.Log;

import java.util.List;

import aido.common.CommonlyUsed;
import aido.file.fileReadWrite;
import aido.properties.BehaveProperties;

/**
 * Created by sumeendranath on 20/02/17.
 */
public class AlertTriggerMapping {

    String _triggerfile = "";

    List<String> _triggerlines;


    public static int FIELD_KEYWORDS = 0;
    public static int FIELD_BEHAVIOR = 1;
    public static int FIELD_DESCRIPTION = 2;
    public static int FIELD_QUESTION = 3;



    public AlertTriggerMapping(String triggerfile) {

        _triggerfile = triggerfile;

        init();
    }


    public boolean isValid()
    {
        return fileReadWrite.fileExists(_triggerfile);
    }

    void init()
    {
        _triggerlines = fileReadWrite.readFileLinesIntoList(_triggerfile);



    }


    int matchpercent = 0;

    String matcheddescription = "";
    String matchedquestion = "";
    public String getMatchedBehavior(String sentence)
    {
        String matchedbehavior = "";
        int highestmatch = 0;
        matchpercent = 0;

        String matchedkeywordlist = "";
        for(int i=0;i<_triggerlines.size();i++)
        {
            List<String> tokens = CommonlyUsed.splitToList(_triggerlines.get(i),",");
            String keywordstr = tokens.get(FIELD_KEYWORDS).toLowerCase().trim();
            String behavior = tokens.get(FIELD_BEHAVIOR);
            String description = tokens.get(FIELD_DESCRIPTION);
            String question = "";
            if(tokens.size() > FIELD_QUESTION) {
                 question = tokens.get(FIELD_QUESTION);
            }
            //List<String> tokens_sentence = CommonlyUsed.splitToList(sentence," ");

            int matchnumber = matchLine(keywordstr,sentence);


            Log.e("stringmatch",highestmatch + " trying '" + keywordstr + "' got ->" + matchnumber + " sent=" + sentence);

            if(matchnumber > highestmatch)
            {
                highestmatch = matchnumber;
                matchedbehavior = behavior;
                matcheddescription = description;
                matchedkeywordlist = keywordstr;
                matchedquestion = question;
            }

        }


        Log.i("ALERT","read " + _triggerlines.size() + " lines");


        List<String> strtok = CommonlyUsed.splitToList(matchedkeywordlist," ");
        if(strtok.size() > 0) {
            matchpercent = (highestmatch / strtok.size() ) * 100;
        }

        return matchedbehavior;
    }

    public int getMatchPercent()
    {
        return matchpercent;
    }
    public String getMatchQuestion()
    {
        return matchedquestion;
    }


    public String getDesc()
    {
        return matcheddescription;
    }

    int matchLine(String line,String sentence)
    {
        List<String> tokens = CommonlyUsed.splitToList(line," ");


        int occur = 0;
        for(int i=0;i<tokens.size();i++)
        {
            if(CommonlyUsed.stringIsNullOrEmpty(tokens.get(i).trim()))
            {
                continue;
            }
//           //new change
//            String s = " "+tokens.get(i).trim()+" ";
//            if(sentence.contains(s))
//            {
//                occur++;
            //}

            if(sentence.contains(tokens.get(i).trim()))
            {
                occur++;
            }
        }

        return occur;

    }

}
