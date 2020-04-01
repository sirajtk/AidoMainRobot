package com.whitesuntech.aidobox;

import android.content.Context;
import android.content.Intent;
import android.net.Uri;

import aido.common.CommonlyUsed;

/**
 * Created by sumeendranath on 27/07/16.
 */
public class InstallApps {

    Context _maincontext;
    public InstallApps(Context context) {

        _maincontext = context;


    }


    public void installPackage(String packagename)
    {
        if(CommonlyUsed.checkIfPackageIsInstalled(_maincontext, packagename))
        {
            Intent goToMarket = new Intent(Intent.ACTION_VIEW)
                    .setData(Uri.parse("market://details?id=" + packagename));
            //_maincontext.startActivityFor(goToMarket);

        }
    }

}
