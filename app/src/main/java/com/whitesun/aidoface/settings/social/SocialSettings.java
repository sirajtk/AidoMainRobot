package com.whitesun.aidoface.settings.social;

import android.content.Context;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ListView;

import com.whitesuntech.aidohomerobot.R;

import java.util.ArrayList;
import java.util.List;

import aido.camera.fileReadWrite;
import aido.common.CommonlyUsed;
import aido.properties.SettingProperties;

public class SocialSettings extends AppCompatActivity {

    Context _maincontext;
    String _behaviorfile = "";
    List<String> _names = new ArrayList<>();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_social_settings);
        _maincontext = SocialSettings.this;

        _behaviorfile = SettingProperties.getSocialSettingsFile();

        setTitle("Social Settings");
        _names.add("Group: ");
        _names.add("Emails: ");
        _names.add("Description: ");

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

}
