package aido.clarifai;

import android.content.Context;
import android.os.AsyncTask;
import android.util.Log;

import java.io.File;
import java.util.List;

import aido.common.CommonlyUsed;
import clarifai2.api.ClarifaiBuilder;
import clarifai2.api.ClarifaiClient;
import clarifai2.dto.input.ClarifaiInput;
import clarifai2.dto.input.image.ClarifaiImage;
import clarifai2.dto.model.output.ClarifaiOutput;
import clarifai2.dto.prediction.Concept;
import okhttp3.OkHttpClient;

/**
 * Created by sumeendranath on 09/01/17.
 */
public class ClarifyFuncs {

    //Context _maincontext;
    ClarifaiClient client;

    String _photopath = "";

    public ClarifyFuncs(String photopath) {
        //_maincontext = context;
        _photopath = photopath;
        init();
    }


    public void init()
    {
         client = new ClarifaiBuilder("YC02EuPBNW12GRRb_1pce3QUPVA7AxiKNXTOy1uG", "Yw_Wogyd6JQEMNnjBt1mTUN2H8K9LsEDLrswYcDS")
              //  .client(new OkHttpClient()) // OPTIONAL. Allows customization of OkHttp by the user
                .buildSync(); // or use .build() to get a Future<ClarifaiClient>



        class ClarifyTask extends AsyncTask<String, Void, List<ClarifaiOutput<Concept>>> {


            protected List<ClarifaiOutput<Concept>> doInBackground(String... name) {
                try {

                    final List<ClarifaiOutput<Concept>> predictionResults =
                            client.getDefaultModels().generalModel() // You can also do client.getModelByID("id") to get custom models
                                    .predict()
                                    .withInputs(
                                            ClarifaiInput.forImage(ClarifaiImage.of(new File(_photopath)))//"https://samples.clarifai.com/metro-north.jpg"))
                                    )
                                    .executeSync()
                                    .get();


                    return predictionResults;
                } catch (Exception e) {

                    return null;
                }
            }

            protected void onPostExecute(List<ClarifaiOutput<Concept>> predictionResults) {
                // TODO: check this.exception
                // TODO: do something with the feed

                String messageout = "I see the following : ";

                if(predictionResults == null)
                {
                    onComplete("Sorry I could not understand. Have you connected me to the internet ?");
                    return;
                }
                for(int i=0;i<predictionResults.size();i++)
                {
                    ClarifaiOutput<Concept> clarifyout = predictionResults.get(i);
                    for(int j=0;j<clarifyout.data().size();j++)
                    {
                        Log.i("CLARIFY","GOT : name=" + clarifyout.data().get(j).name() + ",val=" + clarifyout.data().get(j).value() + ",id=" + clarifyout.data().get(j).id());
                       // CommonlyUsed.showMsg(_maincontext,"GOT : name=" + clarifyout.data().get(j).name() + ",val=" + clarifyout.data().get(j).value() + ",id=" + clarifyout.data().get(j).id());
                        messageout += clarifyout.data().get(j).name() + ",";
                    }
                }


                onComplete(messageout);


            }
        }


        new ClarifyTask().execute("name");


    }

    protected void onComplete(String message)
    {

    }
}
