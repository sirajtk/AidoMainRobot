package aido.Recipe;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

/**
 * Created by sumeendranath on 30/06/16.
 */
public class RecipeGetJsonModel {

    String _jsonstring = "";
    public static String JSONPROP_RECIPETITLE = "title";
    public static String JSONPROP_INGREDIENTSRRAY = "ingredients";
    public static String JSONPROP_IMAGEURL = "image_url";

    public static String JSONPROP_MAINTAG = "recipe";


    String _recipetitle = "";
    String _ingredients = "";
    String _recipeimage = "";


    public RecipeGetJsonModel(String jsonstring) {
        this._jsonstring = jsonstring;
        processJson();

    }

    public String get_recipetitle() {
        return _recipetitle;
    }

    public String get_ingredients() {
        return _ingredients;
    }

    public String get_recipeimage() {
        return _recipeimage;
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
            JSONObject mainobject = _jsonobject.getJSONObject(JSONPROP_MAINTAG);


            _recipetitle = mainobject.getString(JSONPROP_RECIPETITLE);
            _recipeimage = mainobject.getString(JSONPROP_IMAGEURL);

            JSONArray ingrdientsarray = mainobject.getJSONArray(JSONPROP_INGREDIENTSRRAY);

            if(ingrdientsarray.length() > 0) {

                for(int i=0;i<ingrdientsarray.length();i++)
                {
                    _ingredients += ingrdientsarray.getString(i) + "\n";

                }



            }
        } catch (JSONException e) {
            e.printStackTrace();
        }


    }

}
