package aido.Recipe;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

/**
 * Created by sumeendranath on 30/06/16.
 */
public class RecipeSearchJsonModel {

    String _jsonstring = "";

    public static String JSONPROP_RECIPEARRAY = "recipes";

    public static String JSONPROP_RECIPEID = "recipe_id";


    String _recipeid = "";


    public RecipeSearchJsonModel(String _jsonstring) {
        this._jsonstring = _jsonstring;
        processJson();
    }

    public String getRecipeId()
    {
            return _recipeid;
    }

    void processJson()
    {
        JSONObject _jsonobject = null;
        try {
            _jsonobject = new JSONObject(_jsonstring);
        } catch (JSONException e) {
            e.printStackTrace();
        }
        try {
            JSONArray recipelist = _jsonobject.getJSONArray(JSONPROP_RECIPEARRAY);

            if(recipelist.length() > 0) {

               JSONObject recipeobject =  recipelist.getJSONObject(0);
                _recipeid = recipeobject.getString(JSONPROP_RECIPEID);


            }
        } catch (JSONException e) {
            e.printStackTrace();
        }


    }

}
