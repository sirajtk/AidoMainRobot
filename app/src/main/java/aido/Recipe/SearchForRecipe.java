package aido.Recipe;

import android.content.Context;

import aido.common.CommonlyUsed;
import aido.http.asynchttp;
import aido.properties.HttpProperties;
import aido.setdelay.SetDelay;

/**
 * Created by sumeendranath on 30/06/16.
 */
public class SearchForRecipe {


    String _recipeName = "";

    Context _maincontext;

    public SearchForRecipe(Context context, String recipename) {

        _maincontext = context;
        _recipeName = recipename;

        performSearch();
    }



    void performSearch()
    {
        asynchttp httpdownload = new asynchttp(_maincontext);
        httpdownload.download(HttpProperties.getFoodToForkSearchApi(HttpProperties.PROP_FOOD2FORK_APIKEY,_recipeName));
        CommonlyUsed.showMsg(_maincontext,HttpProperties.getFoodToForkSearchApi(HttpProperties.PROP_FOOD2FORK_APIKEY,_recipeName));
        httpdownload.setOnDownloadCompletedListener(new asynchttp.OnDownloadCompletedListener() {
            @Override
            public void onDownloadIsCompleted(String downloadstring) {


                RecipeSearchJsonModel recipesearch = new RecipeSearchJsonModel(downloadstring);


                String recipeid = recipesearch.getRecipeId();
                getTheRecipe(recipeid);

            }
        });
    }


    void getTheRecipe(String recipeid)
    {
        asynchttp httpdownload = new asynchttp(_maincontext);
        httpdownload.download(HttpProperties.getFoodToForkRecipeApi(HttpProperties.PROP_FOOD2FORK_APIKEY,recipeid));
        httpdownload.setOnDownloadCompletedListener(new asynchttp.OnDownloadCompletedListener() {
            @Override
            public void onDownloadIsCompleted(String downloadstring) {



               final  RecipeGetJsonModel recipegetmodel = new RecipeGetJsonModel(downloadstring);

                SetDelay sd = new SetDelay(1000);
                sd.setOnDelayCompletedListener(new SetDelay.OnDelayCompletedListener() {
                    @Override
                    public void onDelayCompleted() {
                        onReceivedRecipe(recipegetmodel.get_recipetitle(), recipegetmodel.get_ingredients(), recipegetmodel.get_recipeimage());

                    }
                });
            }
        });
    }

    protected void onReceivedRecipe(String recipename, String recipedetails, String imageurl)
    {

    }


}
