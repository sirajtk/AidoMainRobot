package com.whitesun.aidoface.behavior;

import android.content.Context;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.text.Editable;
import android.text.TextWatcher;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ListView;
import android.widget.TextView;

import com.whitesuntech.aidohomerobot.R;

import org.w3c.dom.Text;

import java.io.File;
import java.util.ArrayList;
import java.util.List;

import aido.UI.ComboBoxClass;
import aido.camera.fileReadWrite;
import aido.common.CommonlyUsed;
import aido.properties.BehaveProperties;
import aido.properties.StorageProperties;

public class BehaviorRawFileCreate extends AppCompatActivity {

    Context _maincontext;


    String _behaviorfile = "";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_behavior_raw_file_create);

        _maincontext = BehaviorRawFileCreate.this;

        findViews();
    }

    private EditText etRawbehaveTitle;
    private ComboBoxClass sp_rawbehave_files;
    private ComboBoxClass spRawbehaveTime;
    private ComboBoxClass spRawbehaveCategory;
    private ComboBoxClass spRawbehaveAnimation;
    private ComboBoxClass spRawbehaveMotorPan;
    private ComboBoxClass spRawbehaveMotorTilt;
    private ComboBoxClass spRawbehaveAudio;
    private EditText spRawbehaveTts;
    private Button btRawbehaveSubmit;
    private ListView lv_rawbehave_commands;


    /**
     * Find the Views in the layout<br />
     * <br />
     * Auto-created on 2017-01-26 18:04:16 by Android Layout Finder
     * (http://www.buzzingandroid.com/tools/android-layout-finder)
     */

    List<String> _listdata = new ArrayList<>();
    ArrayAdapter<String> _listadapter;
    private void findViews() {

        lv_rawbehave_commands = findViewById(R.id.lv_rawbehave_commands);
        _listadapter = new ArrayAdapter<String>(this,
                R.layout.simpleliscustomitem, android.R.id.text1, _listdata);


        // Assign adapter to ListView
        lv_rawbehave_commands.setAdapter(_listadapter);


        etRawbehaveTitle = findViewById( R.id.et_rawbehave_title );
        etRawbehaveTitle.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence charSequence, int i, int i1, int i2) {

            }

            @Override
            public void onTextChanged(CharSequence charSequence, int i, int i1, int i2) {

            }

            @Override
            public void afterTextChanged(Editable editable) {

                if(CommonlyUsed.stringIsNullOrEmpty(etRawbehaveTitle.getText().toString()))
                {
                    return;
                }

                _behaviorfile = BehaveProperties.getRawBehaviorDir() + etRawbehaveTitle.getText().toString();



                sp_rawbehave_files.setSelectedIndex(0);
                fillCommandsinList();
            }
        });


        sp_rawbehave_files = findViewById( R.id.sp_rawbehave_files );
        fillFiles();

        sp_rawbehave_files.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> adapterView, View view, int i, long l) {
                if(sp_rawbehave_files.getSelectedIndex() > 0) {
                    _behaviorfile = BehaveProperties.getRawBehaviorDir() + sp_rawbehave_files.getSelectedText();
                    fillCommandsinList();
                    etRawbehaveTitle.setText(sp_rawbehave_files.getSelectedText());


                }
            }

            @Override
            public void onNothingSelected(AdapterView<?> adapterView) {

            }
        });


        spRawbehaveTime = findViewById( R.id.sp_rawbehave_time );
        fillTime();
        spRawbehaveCategory = findViewById( R.id.sp_rawbehave_category );
        fillCategory();
        spRawbehaveCategory.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> adapterView, View view, int i, long l) {
                fillAnimation();
            }

            @Override
            public void onNothingSelected(AdapterView<?> adapterView) {

            }
        });
        spRawbehaveAnimation = findViewById( R.id.sp_rawbehave_animation );
        spRawbehaveMotorPan = findViewById( R.id.sp_rawbehave_motor_pan );
        spRawbehaveMotorTilt = findViewById( R.id.sp_rawbehave_motor_tilt );
        fillPanTilt();
        spRawbehaveAudio = findViewById( R.id.sp_rawbehave_audio );
        spRawbehaveTts = findViewById( R.id.sp_rawbehave_tts );
        btRawbehaveSubmit = findViewById( R.id.bt_rawbehave_submit );

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



            }
        });




        lv_rawbehave_commands.setOnItemLongClickListener(new AdapterView.OnItemLongClickListener() {
            @Override
            public boolean onItemLongClick(AdapterView<?> adapterView, View view, int i, long l) {


                fileReadWrite.removeLine(_behaviorfile,i);
                fillCommandsinList();
                return false;
            }
        });




    }


    void fillFiles()
    {
        sp_rawbehave_files.appendItem("Select Behavior");

        List<String> files = fileReadWrite.getAllLeafFilesWithin(BehaveProperties.getRawBehaviorDir());


        //CommonlyUsed.showMsg(getApplicationContext(),"files = " + files.size() + "," + BehaveProperties.getRawBehaviorDir());

        for(int i=0;i<files.size();i++)
        {
            sp_rawbehave_files.appendItem("" + files.get(i));
        }

        sp_rawbehave_files.setSelectedIndex(-1);

    }


    void fillCommandsinList()
    {
        _listdata.clear();
        if(fileReadWrite.fileExists(_behaviorfile))
        {
            List<String> lines = fileReadWrite.readFileLinesIntoList(_behaviorfile);

            for(int i=0;i<lines.size();i++)
            {
                _listdata.add(lines.get(i));
            }


        }
        _listadapter.notifyDataSetChanged();
        lv_rawbehave_commands.smoothScrollToPosition(_listdata.size() - 1);

    }

    void fillTime()
    {

        spRawbehaveTime.appendItem("Select Time");

        for(int i=0;i<500;i++)
        {
            spRawbehaveTime.appendItem("" + i);
        }

        spRawbehaveTime.setSelectedIndex(-1);
    }


    void fillCategory()
    {

        spRawbehaveCategory.appendItem("Select Category");

        List<String> categories = fileReadWrite.getAllDirectoryNamesWithin(BehaveProperties.getUiVideosDir());

        for(int i=0;i<categories.size();i++)
        {
            spRawbehaveCategory.appendItem("" + categories.get(i));
        }

        spRawbehaveCategory.setSelectedIndex(-1);
    }


    void fillAnimation()
    {

        spRawbehaveAnimation.clearAllItems(true);

        if(CommonlyUsed.stringIsNullOrEmpty(spRawbehaveCategory.getSelectedText())
                || spRawbehaveCategory.getSelectedIndex() < 0


                )
        {
            CommonlyUsed.showMsg(_maincontext,"Invalid Category");
            return;
        }


        CommonlyUsed.showMsg(_maincontext,"selected : " + spRawbehaveCategory.getSelectedText());
        spRawbehaveAnimation.appendItem("Select Animation");

        List<String> animations = fileReadWrite.getAllLeafFilesWithin(BehaveProperties.getUiVideosDir() + File.separator + spRawbehaveCategory.getSelectedText());

        for(int i=0;i<animations.size();i++)
        {
            spRawbehaveAnimation.appendItem("" + animations.get(i));
        }

        spRawbehaveAnimation.setSelectedIndex(-1);
    }


    void fillPanTilt()
    {

        spRawbehaveMotorPan.appendItem("Select Pan");
        int start = 120;
        spRawbehaveMotorPan.appendItem("" + "HOME");
        for( start = 120;start >= -120;start-=10)
        {
            spRawbehaveMotorPan.appendItem("" + start);
        }

        for( start = 120;start >= -120;start-=10)
        {
            spRawbehaveMotorPan.appendItem("abs:" + start);
        }



        spRawbehaveMotorPan.setSelectedIndex(-1);

        spRawbehaveMotorTilt.appendItem("Select Tilt");

        spRawbehaveMotorTilt.appendItem("" + "HOME");
        start = 120;
        for( start = 120;start >= -120;start-=10)
        {
            spRawbehaveMotorTilt.appendItem("" + start);
        }

        for( start = 120;start >= -120;start-=10)
        {
            spRawbehaveMotorTilt.appendItem("abs:" + start);
        }

        spRawbehaveMotorTilt.setSelectedIndex(-1);


    }


    List<String> checkValues()
    {
        List<String> retlist = new ArrayList<>();

        if(CommonlyUsed.stringIsNullOrEmpty(etRawbehaveTitle.getText().toString()))
        {
            CommonlyUsed.showMsg(_maincontext,"Invalid title");
            retlist.clear();
            return retlist;
        }

        // retlist.add(etRawbehaveTitle.getText().toString());


        if(CommonlyUsed.stringIsNullOrEmpty(spRawbehaveTime.getSelectedText())

                || spRawbehaveTime.getSelectedIndex() <= 0

                )
        {
            CommonlyUsed.showMsg(_maincontext,"Invalid Time");
            retlist.clear();
            return retlist;
        }

        retlist.add(spRawbehaveTime.getSelectedText());


        if(CommonlyUsed.stringIsNullOrEmpty(spRawbehaveCategory.getSelectedText())
                || spRawbehaveCategory.getSelectedIndex() <= 0


                )
        {
            //CommonlyUsed.showMsg(_maincontext,"Invalid Category");
            //return false;
        }
        if(CommonlyUsed.stringIsNullOrEmpty(spRawbehaveAnimation.getSelectedText())
                || spRawbehaveAnimation.getSelectedIndex() < 0
                )
        {
            //CommonlyUsed.showMsg(_maincontext,"Invalid Animation");
            //return false;
        }

        String moviename = "0";
        if(spRawbehaveAnimation.getSelectedIndex() >0) {

            moviename = BehaveProperties.getUiVideosDirLeaf() + spRawbehaveCategory.getSelectedText()
                    + StorageProperties.getSeparator() + spRawbehaveAnimation.getSelectedText();

        }

        retlist.add(moviename);


        if(CommonlyUsed.stringIsNullOrEmpty(spRawbehaveMotorPan.getSelectedText())
                || spRawbehaveMotorPan.getSelectedIndex() <= 0
                )
        {
            CommonlyUsed.showMsg(_maincontext,"Invalid PAN");
            //return false;
        }

        String pan = "0";
        if(spRawbehaveMotorPan.getSelectedIndex() >0) {
            pan = spRawbehaveMotorPan.getSelectedText();
        }

        retlist.add(pan);


        if(CommonlyUsed.stringIsNullOrEmpty(spRawbehaveMotorTilt.getSelectedText())
                || spRawbehaveMotorTilt.getSelectedIndex() <= 0
                )
        {
            CommonlyUsed.showMsg(_maincontext,"Invalid Tilt");
            //return false;
        }


        String tilt = "0";
        if(spRawbehaveMotorTilt.getSelectedIndex() >0) {
            tilt = spRawbehaveMotorTilt.getSelectedText();
        }

        retlist.add(tilt);



        if(
                (CommonlyUsed.stringIsNullOrEmpty(spRawbehaveAudio.getSelectedText())
                        || (spRawbehaveAudio.getSelectedIndex() <= 0))


                        && CommonlyUsed.stringIsNullOrEmpty(etRawbehaveTitle.getText().toString())


                )
        {
            CommonlyUsed.showMsg(_maincontext,"Invalid Audio");
            //return false;
        }



        String audiofile = spRawbehaveTts.getText().toString();
        if(CommonlyUsed.stringIsNullOrEmpty(audiofile))
        {
            audiofile = spRawbehaveAudio.getSelectedText();
        }


        if(CommonlyUsed.stringIsNullOrEmpty(audiofile))
        {
            audiofile = "0";
        }


        retlist.add(audiofile);
        retlist.add("50");


        return retlist;
    }

}
