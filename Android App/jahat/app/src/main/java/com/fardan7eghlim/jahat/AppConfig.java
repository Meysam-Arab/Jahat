package com.fardan7eghlim.jahat;

import android.graphics.Bitmap;

/**
 * Created by Amir on 10/12/2016.
 */
public class AppConfig {

//    private static String localHost="http://192.168.1.51:4797/Jahat/";
//    private static String localHost="http://api.jahat.net/";
    private static String localHost="http://136.243.120.147/~jahat/api/";

//    private static String Host="http://10.0.2.2:4797";
    public static Double version=1.0;
    public static Bitmap avatar=null;

    // Server addChannel
    public static String URL_addChannel= localHost+"addChannel.php";

    // Server editChannel
    public static String URL_editChannel = localHost+"editChannel.php";

    // Server search
    public static String URL_search = localHost+"search.php";

    // Server select
    public static String URL_select = localHost+"select.php";

    // Server version
    public static String URL_getVersionOFAndroid = localHost+"version.php";

    // Server get info about channel
    public static String URL_getInfoOFChannel = localHost+"getInfoOFchannel.php";

    // Server send report
    public static String URL_report = "http://136.243.120.147/~jahat/Feedback.php";

    // pwr tools
    public static String URL_pwrGetChat = "https://api.pwrtelegram.xyz/bot300974265:AAGqKQ04kWjliAyYPEtVX4MorNWmYeIahu0/getChat?chat_id=@";
}
