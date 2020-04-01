package org.ros.android.json;

import org.json.JSONException;
import org.json.JSONObject;

/**
 * Created by sumeendranath on 17/08/16.
 */
public class JsonHandle {

    JSONObject _jsonobject;

    public JsonHandle(String jsonstring) {


        try {
            _jsonobject = new JSONObject(jsonstring);
        } catch (JSONException e) {
            e.printStackTrace();
        }

    }


    public String getValue(String property)
    {

        try {
            return _jsonobject.getString(property);
        } catch (JSONException e) {
            e.printStackTrace();
        }

        return "";
    }


}
