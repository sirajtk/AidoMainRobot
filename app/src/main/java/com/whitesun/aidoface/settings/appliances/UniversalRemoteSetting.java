package com.whitesun.aidoface.settings.appliances;

import android.content.Context;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ListView;
import android.widget.Toast;

import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;
import com.whitesuntech.aidohomerobot.R;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import aido.camera.fileReadWrite;
import aido.common.CommonlyUsed;
import aido.properties.SettingProperties;

public class UniversalRemoteSetting extends AppCompatActivity {

    Context _maincontext;
    String _behaviorfile = "";
    List<String> _names = new ArrayList<>();
   // String url = "http://192.168.0.111:4000";
    RequestQueue queue;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_universal_remote_setting);
        _maincontext = UniversalRemoteSetting.this;

        _behaviorfile = SettingProperties.getUniversalRemoteSettingsFile();

        setTitle("Universal Remote Settings");
        _names.add("Remote:");
        _names.add("Model:");
        _names.add("No.:");

        findViews();
        fillCommandsinList();

    }

    private EditText etTvNickname;
    private EditText etTvModelname;
    private EditText etTvModelno;
    private Button btRawbehaveSubmit;
    private ListView lvTvCommands;

    /**
     * Find the Views in the layout<br />
     * <br />
     * Auto-created on 2017-02-04 06:18:17 by Android Layout Finder
     * (http://www.buzzingandroid.com/tools/android-layout-finder)
     */

    List<String> _listdata = new ArrayList<>();
    ArrayAdapter<String> _listadapter;

    private void findViews() {
        etTvNickname = findViewById( R.id.et_tv_nickname );
        etTvModelname = findViewById( R.id.et_tv_modelname );
        etTvModelno = findViewById( R.id.et_tv_modelno );
        btRawbehaveSubmit = findViewById( R.id.bt_rawbehave_submit );
        lvTvCommands = findViewById( R.id.lv_tv_commands );
        _listadapter = new ArrayAdapter<String>(this,
                R.layout.simpleliscustomitem, android.R.id.text1, _listdata);
        lvTvCommands.setAdapter(_listadapter);

        btRawbehaveSubmit.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                List<String> vallist = checkValues();
                PostData();

                if(vallist.size() <=0)
                {
                    CommonlyUsed.showMsg(getApplicationContext(),"Invalid values");
                    return;
                }


                String strtowrite = CommonlyUsed.createCSVstringFromList(vallist,",") + "\n";
                /*
                String strtowrite = spRawbehaveTime.getSelectedText() /// time
                + "," + moviename + "," + spRawbehaveMotorPan.getSelectedText() + ","
                        + spRawbehaveMotorTilt.getSelectedText() + "," + audiofile + ",50"
                        +"\n";

                */

                CommonlyUsed.showMsg(getApplicationContext(),"file: " + _behaviorfile);
                fileReadWrite.addStringToTextFile(strtowrite,_behaviorfile);

                fillCommandsinList();
                etTvNickname.setText("");
                etTvModelname.setText("");
                etTvModelno.setText("");


            }
        });

        lvTvCommands.setOnItemLongClickListener(new AdapterView.OnItemLongClickListener() {
            @Override
            public boolean onItemLongClick(AdapterView<?> adapterView, View view, int i, long l) {

                String line = fileReadWrite.readLineFromFile(_behaviorfile,i+1);
                fileReadWrite.removeLine(_behaviorfile,i);
                fillCommandsinList();

                if(CommonlyUsed.stringIsNullOrEmpty(line))
                {
                    return false;
                }
                List<String> tokens = CommonlyUsed.splitToList(line,",");

                etTvNickname.setText(tokens.get(0));
                etTvModelname.setText(tokens.get(1));
                etTvModelno.setText(tokens.get(2));

                return false;
            }
        });

        lvTvCommands.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> adapterView, View view, int i, long l) {


            }
        });

    }







    void fillCommandsinList()
    {
        _listdata.clear();
        if(fileReadWrite.fileExists(_behaviorfile))
        {
            List<String> lines = fileReadWrite.readFileLinesIntoList(_behaviorfile);

            for(int i=0;i<lines.size();i++)
            {
                //_listdata.add(lines.get(i));
                List<String> tokens = CommonlyUsed.splitToList(lines.get(i),",");

                String str = _names.get(0) + " " + tokens.get(0) + "\n" +
                        _names.get(1) + " " + tokens.get(1) + "\n" +
                        _names.get(2) + " " + tokens.get(2) + "\n";

                _listdata.add(str);
            }


        }
        _listadapter.notifyDataSetChanged();
        lvTvCommands.smoothScrollToPosition(_listdata.size() - 1);

    }


    List<String> checkValues()
    {
        List<String> retlist = new ArrayList<>();

        if(CommonlyUsed.stringIsNullOrEmpty(etTvModelname.getText().toString()))
        {
            CommonlyUsed.showMsg(_maincontext,"Invalid Nick Name");
            retlist.clear();
            return retlist;
        }

        if(CommonlyUsed.stringIsNullOrEmpty(etTvModelname.getText().toString()))
        {
            CommonlyUsed.showMsg(_maincontext,"Invalid Model");
            retlist.clear();
            return retlist;
        }
        if(CommonlyUsed.stringIsNullOrEmpty(etTvModelno.getText().toString()))
        {
            CommonlyUsed.showMsg(_maincontext,"Invalid Model No.");
            retlist.clear();
            return retlist;
        }

        retlist.add(etTvNickname.getText().toString());
        retlist.add(etTvModelname.getText().toString());
        retlist.add(etTvModelno.getText().toString());



        return retlist;
    }
    public void PostData() {
        Toast.makeText(getApplicationContext(), "Post request", Toast.LENGTH_LONG).show();
        final String UniversalNickname = etTvNickname.getText().toString();
        final String UniversalModalname = etTvModelname.getText().toString();
        final String UniversalModalno = etTvModelno.getText().toString();
        String url = "http://10.10.10.1/Api.php?apicall=createtvsettings&name="+UniversalNickname+"&modelname="+UniversalModalname+"&modelno="+UniversalModalno;
        queue = Volley.newRequestQueue(getApplicationContext());
        StringRequest jsonObjReq = new StringRequest(Request.Method.GET, url, new Response.Listener<String>() {

            @Override
            public void onResponse(String response) {
                Toast.makeText(getApplicationContext(), response, Toast.LENGTH_SHORT).show();
            }
        }, new Response.ErrorListener() {

            @Override
            public void onErrorResponse(VolleyError error) {

                // hide the progress dialog
                Toast.makeText(getApplicationContext() ,error.toString(),Toast.LENGTH_LONG).show();
            }


        }) {
            @Override
            protected Map<String, String> getParams() {
                Map<String, String> params = new HashMap<String, String>();
                params.put("UniversalNickName", UniversalNickname);
                params.put("UniversalModalName", UniversalModalname);
                params.put("UniversalModalNo", UniversalModalno);
                return params;
            }

        };

        queue.add(jsonObjReq);
    }

}
