package org.ros.android.json;

/**
 * Created by sumeendranath on 17/08/16.
 */
public class ConfigProperties {
    public static String TEXT_TO_SPEECH = "TEXT_TO_SPEECH";
    public static String SPEECH_TO_TEXT = "SPEECH_TO_TEXT";
    public static String ROS_APP = "ROS_APP";
    public static String MEDIA = "MEDIA";
    public static String BOX = "BOX";
    public static String FACE_RECOG = "FACE_RECOG";
    public static String ROS_IP = "ROS_IP";
    public static String IMAGE_LIST = "IMAGE_LIST";

    public static String DEFAULT_JSON =
            "{\n" +
                    "  \"TEXT_TO_SPEECH\":\"com.whitesuntech.texttospeech.TSService\",\n" +
                    "  \"SPEECH_TO_TEXT\":\"com.whitesuntech.speechtotext.STService\",\n" +
                    "  \"ROS_APP\":\"org.ollide.rosandroid\",\n" +
                    "  \"MEDIA\":\"com.androidhive.musicplayer\",\n" +
                    "  \"FACE_RECOG\":\"com.whitesuntech.facerecognitionpics\",\n" +
                    "  \"BOX\":\"com.whitesuntech.aidobox\",\n" +
                    "  \"ROS_IP\":\"http://192.168.0.5:11311\"\n" +
                   // "  \"IMAGE_LIST\":[]\n" +
                    "}";

}
