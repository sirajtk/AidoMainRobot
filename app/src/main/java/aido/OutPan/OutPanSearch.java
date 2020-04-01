package aido.OutPan;

import android.content.Context;
import android.os.AsyncTask;

import aido.common.CommonlyUsed;
import aido.properties.HttpProperties;

/**
 * Created by sumeendranath on 21/06/16.
 */
public class OutPanSearch extends AsyncTask<String, Void, OutpanObject> {


    private Exception exception;
    OutpanAPI _outpan_api;
    OutpanObject _outpan_object;


    Context _maincontext;

    public interface LoadingTaskFinishedListener {
        void onTaskFinished(OutpanObject outpanobject);
    }


    private  LoadingTaskFinishedListener finishedListener;


    public void setOnTaskCompletedListener(LoadingTaskFinishedListener l)
    {
        finishedListener = l;
    }

    public OutPanSearch(Context context) {

        super();

        _maincontext = context;

    }




    @Override
    protected OutpanObject doInBackground(String... barcodes) {
        try {

            String bc = barcodes[0];

            _outpan_api = new OutpanAPI(HttpProperties.OUTPAN_KEY);
            _outpan_object = _outpan_api.getProduct(bc);

            return _outpan_object;
        } catch (Exception e) {
            this.exception = e;
            return null;
        }
    }



    @Override
    protected void onPostExecute(OutpanObject outpanobject) {
        // TODO: check this.exception
        // TODO: do something with the feed

        super.onPostExecute(outpanobject);


//        CommonlyUsed.showMsg(_maincontext,"Product name = " + outpanobject.name + ",url = " + outpanobject.outpan_url );


        finishedListener.onTaskFinished(outpanobject); // Tell whoever was listening we have finished


    }


    public OutpanObject getObject()
    {
        return _outpan_object;
    }
}

