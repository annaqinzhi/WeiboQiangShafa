package com.example.weiboqiangshafa;

import android.app.NotificationChannel;
import android.app.NotificationManager;
import android.app.PendingIntent;
import android.content.Context;
import android.content.Intent;
import android.media.RingtoneManager;
import android.support.v4.app.NotificationCompat;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;

import java.io.BufferedReader;
import java.io.DataOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.URL;
import java.nio.charset.StandardCharsets;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Timer;
import java.util.TimerTask;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class MainActivity extends AppCompatActivity {

    String BASE_ADD_URL = "https://www.weibo.com/aj/v6/comment/add?";
    String content;
    Timer timer;
    //String fromUserId = "1411717124";
    String fromUserId = "1959389877";
    String toUserId = "1736988591";
    String postContent = "沙。。。。发？";
    String BASE_ALL_URL = "https://www.weibo.com/u/" + toUserId + "?is_all=1";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        final Button btn_start = (Button) findViewById(R.id.btnStart);
        final Button btn_end = (Button) findViewById(R.id.btnEnd);

        btn_end.setEnabled(false);

        btn_start.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                timer = new Timer();
                timer.schedule(new GetPost(),0, 3000);
                //timer.schedule(new GetPost(),0);

                btn_start.setEnabled(false);
                btn_end.setEnabled(true);
                Log.i("Click", "Start");
            }
        });

        btn_end.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                timer.cancel();
                btn_end.setEnabled(false);
                btn_start.setEnabled(true);
                Log.i("Click", "Stopp");
            }
        });

    }

//    class SendPost extends TimerTask {
//        public void run() {
//            try {
//                currentTime = Calendar.getInstance().getTimeInMillis();
//                Log.i("currentTime", Long.toString(currentTime));
//                String urlPlus = "ajwvr=6&__rnd=" + currentTime;
//                String urlStr = BASE_ADD_URL + urlPlus;
//                Log.i("currentlink", urlStr);
//                URL url = new URL(urlStr);
//                String urlParameters = "act=post&mid="+mid+"&uid=" + fromUserId + "&forward=0&isroot=0&content=" + postContent + "&location=page_100505_home&module=scommlist&group_source=&pdetail=100505" + toUserId + "&_t=0";
//                byte[] postData = urlParameters.getBytes(StandardCharsets.UTF_8);
//                int postDataLength = postData.length;
//                HttpURLConnection conn = (HttpURLConnection) url.openConnection();
//
//                conn.setDoOutput(true);
//                conn.setInstanceFollowRedirects(false);
//                conn.setRequestMethod("POST");
//                conn.setRequestProperty(":authority", "www.weibo.com");
//                conn.setRequestProperty(":method", "POST");
//                conn.setRequestProperty(":path", "/aj/v6/comment/add?" + urlPlus);
//                conn.setRequestProperty(":scheme", "https");
//                conn.setRequestProperty("accept", "*/*");
//                conn.setRequestProperty("accept-Encoding", "gzip, deflate, br");
//                conn.setRequestProperty("accept-language", "en-US,en;q=0.9,zh-CN;q=0.8,zh;q=0.7");
//                conn.setRequestProperty("content-length", Integer.toString(postDataLength));
//                conn.setRequestProperty("content-type", "application/x-www-form-urlencoded");
//                //conn.setRequestProperty("Cookie", "_s_tentry=passport.weibo.com; Apache=6768366249729.394.1562348161240; SINAGLOBAL=6768366249729.394.1562348161240; ULV=1562348161343:1:1:1:6768366249729.394.1562348161240:; TC-V5-G0=b1761408ab251c6e55d3a11f8415fc72; Ugrow-G0=5c7144e56a57a456abed1d1511ad79e8; WBtopGlobal_register_version=8c86b9ca67e1b502; SCF=Ap9bCm324dUvNt7cc6YSR8TNdEzX53hlts-FBSaDwOovu0YnySuFTvm7QPr6oxc4O_J4KGkWkPmFLNCy5SZeD6Q.; SUB=_2A25wG-SpDeRhGedK6lMW8SnNyTiIHXVTUVFhrDV8PUNbmtBeLRmikW9NJAJzf1is6JN6_Fg3njs2c-ISUQxs8BC0; SUBP=0033WrSXqPxfM725Ws9jqgMF55529P9D9WhgX0VS9f218eMpXo3cggZa5JpX5K2hUgL.Fo2XeK2NeKMpeoB2dJLoIpvj9PLfi--fi-zpiKnfi--ciK.ci-8si--Xi-iWi-8h; SUHB=0V5j8R8MnIkLkN; ALF=1562955640; SSOLoginState=1562350841; un=zhuly203@sina.com; TC-Page-G0=153ff31dae1cf71cc65e7e399bfce283|1562350850|1562350669; wb_view_log_1411717124=1366*7681; webim_unReadCount=%7B%22time%22%3A1562350969468%2C%22dm_pub_total%22%3A19%2C%22chat_group_pc%22%3A0%2C%22allcountNum%22%3A66%2C%22msgbox%22%3A0%7D");
//                conn.setRequestProperty("Cookie", "Ugrow-G0=e1a5a1aae05361d646241e28c550f987; login_sid_t=03e79be8e067e0d8c1125b66bbb425fd; _ga=GA1.2.585351749.1561999190; YF-V5-G0=a5a6106293f9aeef5e34a2e71f04fae4; _s_tentry=-; __gads=ID=3ff1880afc11242f:T=1561999192:S=ALNI_MYZru1RAdisl0M7WXNAmMgsYvURiA; Apache=3365018883878.6455.1561999195292; SINAGLOBAL=3365018883878.6455.1561999195292; ULV=1561999195302:1:1:1:3365018883878.6455.1561999195292:; un=zhuly203@sina.com; TC-V5-G0=841d8e04c4761f733a87c822f72195f3; cross_origin_proto=SSL; TC-Page-G0=45685168db6903150ce64a1b7437dbbb|1562355285|1562355275; wb_view_log_1959389877=1440*9001; wb_view_log_1411717124=1440*9001; wb_cmtLike_1959389877=1; UOR=,,www.sina.com.cn; _gid=GA1.2.472741711.1562443244; WBStorage=988f187486ad9919|undefined; wb_view_log=1440*9001; SUBP=0033WrSXqPxfM725Ws9jqgMF55529P9D9WhgX0VS9f218eMpXo3cggZa5JpX5K2hUgL.Fo2XeK2NeKMpeoB2dJLoIpvj9PLfi--fi-zpiKnfi--ciK.ci-8si--Xi-iWi-8h; ALF=1593979248; SSOLoginState=1562443249; SCF=AgUdXPG_5j06xuMSMl9ZGK5KIbca2vQhHme4ydAgEPYptHrHXCghgYoKmnW1kX43M6Uw7zOX5_z6ucTZprioRxk.; SUB=_2A25wJI2iDeRhGedK6lMW8SnNyTiIHXVTU_hqrDV8PUNbmtBeLVbGkW9NJAJzf2HTLNSRPpOpyqd8Yf88GMGZNiee; SUHB=05i8B5z109eUyO; wvr=6; YF-Page-G0=5161d669e1ac749e79d0f9c1904bc7bf|1562443321|1562443230; webim_unReadCount=%7B%22time%22%3A1562443410259%2C%22dm_pub_total%22%3A19%2C%22chat_group_pc%22%3A0%2C%22allcountNum%22%3A66%2C%22msgbox%22%3A0%7D");
//                conn.setRequestProperty("origin", "https://www.weibo.com");
//                conn.setRequestProperty("referer", "https://www.weibo.com/u/1959389877?topnav=1&wvr=6&topsug=1&is_all=1");
//                conn.setRequestProperty("user-agent", "Mozilla/5.0 (Windows NT 10.0; Win64; x64) AppleWebKit/537.36 (KHTML, like Gecko) Chrome/75.0.3770.100 Safari/537.36");
//                conn.setRequestProperty("x-requested-with", "XMLHttpRequest");
//                conn.setDoInput(true);
//                conn.setUseCaches(false);
//
//                DataOutputStream os = new DataOutputStream(conn.getOutputStream());
//                os.write(postData);
//                os.flush();
//                os.close();
//
//                Log.i("method", "SendPost()");
//                Log.i("postContent", postContent);
//                Log.i("status", conn.getResponseMessage());
//                //compareDate("2019-08-03", firstDate);
//                conn.disconnect();
//            } catch (Exception e) {
//                e.printStackTrace();
//            }
//        }
//    }

    class GetPost extends TimerTask {
        public void run() {
            try {
                long currentTime = Calendar.getInstance().getTimeInMillis();
                Log.i("currentTime", Long.toString(currentTime));
                Log.i("currentlink", BASE_ALL_URL);
                URL url = new URL(BASE_ALL_URL);
                HttpURLConnection conn = (HttpURLConnection) url.openConnection();

                conn.setRequestMethod("GET");
                conn.setRequestProperty("accept", "text/html,application/xhtml+xml,application/xml;q=0.9,image/webp,image/apng,*/*;q=0.8,application/signed-exchange;v=b3");
                //conn.setRequestProperty("accept-Encoding", "gzip, deflate, br");
                conn.setRequestProperty("accept-language", "en-GB,en-US;q=0.9,en;q=0.8");
                conn.setRequestProperty("cache-control", "max-age=0");
                //conn.setRequestProperty("Cookie","Ugrow-G0=e1a5a1aae05361d646241e28c550f987; login_sid_t=03e79be8e067e0d8c1125b66bbb425fd; _ga=GA1.2.585351749.1561999190; YF-V5-G0=a5a6106293f9aeef5e34a2e71f04fae4; _s_tentry=-; __gads=ID=3ff1880afc11242f:T=1561999192:S=ALNI_MYZru1RAdisl0M7WXNAmMgsYvURiA; Apache=3365018883878.6455.1561999195292; SINAGLOBAL=3365018883878.6455.1561999195292; ULV=1561999195302:1:1:1:3365018883878.6455.1561999195292:; TC-V5-G0=841d8e04c4761f733a87c822f72195f3; cross_origin_proto=SSL; wb_cmtLike_1959389877=1; wb_cmtLike_1411717124=1; YF-Page-G0=afcf131cd4181c1cbdb744cd27663d8d|1562529222|1562529155; UOR=,,login.sina.com.cn; SSOLoginState=1562698876; un=zhuly203@sina.com; wvr=6; SUBP=0033WrSXqPxfM725Ws9jqgMF55529P9D9WFaDrQb.dX.5.Q_RYKnxHWB5JpX5KMhUgL.Fo24SK.01h.RS0M2dJLoIEXLxK-LB--L1h.LxK-LBKnL1-eLxKqL1-eLBo2LxKqL1-eLBo2LxKqL1-eLBo2t; ALF=1594322754; SCF=AgUdXPG_5j06xuMSMl9ZGK5KIbca2vQhHme4ydAgEPYp22r_4fyQxJu-_xBbHZrjiVQwELTedi-KNtwNaDM3ykk.; SUB=_2A25wIkuWDeRhGedH7lsS-CfEzDuIHXVTVjperDV8PUNbmtBeLXakkW9NULnl4HGbrWnWcfyuOMWndh51GUHAWvj9; SUHB=0U1YDrXs0EHt92; TC-Page-G0=c4376343b8c98031e29230e0923842a5|1562788882|1562788875; wb_view_log_1411717124=1440*9001; wb_view_log_1959389877=1440*9001; webim_unReadCount=%7B%22time%22%3A1562862680304%2C%22dm_pub_total%22%3A24%2C%22chat_group_pc%22%3A618%2C%22allcountNum%22%3A642%2C%22msgbox%22%3A0%7D");
                //conn.setRequestProperty("Cookie","Ugrow-G0=e1a5a1aae05361d646241e28c550f987; login_sid_t=03e79be8e067e0d8c1125b66bbb425fd; _ga=GA1.2.585351749.1561999190; YF-V5-G0=a5a6106293f9aeef5e34a2e71f04fae4; _s_tentry=-; __gads=ID=3ff1880afc11242f:T=1561999192:S=ALNI_MYZru1RAdisl0M7WXNAmMgsYvURiA; Apache=3365018883878.6455.1561999195292; SINAGLOBAL=3365018883878.6455.1561999195292; ULV=1561999195302:1:1:1:3365018883878.6455.1561999195292:; TC-V5-G0=841d8e04c4761f733a87c822f72195f3; cross_origin_proto=SSL; wb_cmtLike_1959389877=1; wb_cmtLike_1411717124=1; UOR=,,login.sina.com.cn; TC-Page-G0=c4376343b8c98031e29230e0923842a5|1562788882|1562788875; wb_view_log_1411717124=1440*9001; wb_view_log_1959389877=1440*9001; _gid=GA1.2.1295463367.1562873903; wb_view_log=1440*9001; SUBP=0033WrSXqPxfM725Ws9jqgMF55529P9D9WhgX0VS9f218eMpXo3cggZa5JpX5K2hUgL.Fo2XeK2NeKMpeoB2dJLoIpvj9PLfi--fi-zpiKnfi--ciK.ci-8si--Xi-iWi-8h; SSOLoginState=1562873926; ALF=1594410010; SCF=AgUdXPG_5j06xuMSMl9ZGK5KIbca2vQhHme4ydAgEPYp16nimOGrAT8SNhqqkzESh3o7Prv3Jluu_5kRcq8ibQQ.; SUB=_2A25wI-DNDeRhGedK6lMW8SnNyTiIHXVTWVUFrDV8PUNbmtBeLVaskW9NJAJzf3VwLDy94waZPEI9SNIDWjlp5TBM; SUHB=0A0PSqJdB-vSnq; un=zhuly203@sina.com; wvr=6; YF-Page-G0=237c624133c0bee3e8a0a5d9466b74eb|1562874252|1562874022; webim_unReadCount=%7B%22time%22%3A1562875254254%2C%22dm_pub_total%22%3A0%2C%22chat_group_pc%22%3A0%2C%22allcountNum%22%3A0%2C%22msgbox%22%3A0%7D");
                conn.setRequestProperty("Cookie","Ugrow-G0=e1a5a1aae05361d646241e28c550f987; login_sid_t=03e79be8e067e0d8c1125b66bbb425fd; _ga=GA1.2.585351749.1561999190; YF-V5-G0=a5a6106293f9aeef5e34a2e71f04fae4; _s_tentry=-; __gads=ID=3ff1880afc11242f:T=1561999192:S=ALNI_MYZru1RAdisl0M7WXNAmMgsYvURiA; Apache=3365018883878.6455.1561999195292; SINAGLOBAL=3365018883878.6455.1561999195292; ULV=1561999195302:1:1:1:3365018883878.6455.1561999195292:; TC-V5-G0=841d8e04c4761f733a87c822f72195f3; cross_origin_proto=SSL; wb_cmtLike_1959389877=1; wb_cmtLike_1411717124=1; UOR=,,login.sina.com.cn; YF-Page-G0=f1e19cba80f4eeaeea445d7b50e14ebb|1562875829|1562875744; _gid=GA1.2.756339551.1563008763; WBStorage=4c75d21c5b444249|undefined; wb_view_log=1440*9001; SUBP=0033WrSXqPxfM725Ws9jqgMF55529P9D9WFaDrQb.dX.5.Q_RYKnxHWB5JpX5K2hUgL.Fo24SK.01h.RS0M2dJLoIEXLxK-LB--L1h.LxK-LBKnL1-eLxKqL1-eLBo2LxKqL1-eLBo2LxKqL1-eLBo2t; ALF=1594544811; SSOLoginState=1563008919; SCF=AgUdXPG_5j06xuMSMl9ZGK5KIbca2vQhHme4ydAgEPYp0dUtZSBciA-EsvEgUTcH1V7DBWZ0pXusmij_i9F7Lwo.; SUB=_2A25wLe99DeRhGedH7lsS-CfEzDuIHXVTW0e1rDV8PUNbmtBeLUfEkW9NULnl4B5abuTgEVpB3ePOdZONwGbTmzO8; SUHB=0A0YARs09-vSmy; un=anneqinzhi@163.com; wvr=6; wb_view_log_1959389877=1440*9001; TC-Page-G0=2f200ef68557e15c78db077758a88e1f|1563008840|1563008744; webim_unReadCount=%7B%22time%22%3A1563008869340%2C%22dm_pub_total%22%3A25%2C%22chat_group_pc%22%3A754%2C%22allcountNum%22%3A779%2C%22msgbox%22%3A0%7D");
                conn.setRequestProperty("upgrade-insecure-requests", "1");
                conn.setRequestProperty("user-agent", "Mozilla/5.0 (Macintosh; Intel Mac OS X 10_14_5) AppleWebKit/537.36 (KHTML, like Gecko) Chrome/75.0.3770.100 Safari/537.36");

                //compareDate("2019-08-03", firstDate);
                Log.i("method", "GetPost()");
                Log.i("status", conn.getResponseMessage());
                content = convertStreamToString(conn.getInputStream());
                //largeLog("getContent", content);
                long latestTime = getLatestTime(content);
                String mid = getMid(content, latestTime);

                //sendPost(postContent, mid);
                if(compareTime(currentTime, latestTime)){
                    sendPost(postContent, mid);
                }
                conn.disconnect();
            } catch (Exception e) {
                e.printStackTrace();
            }
        }

        public void sendPost(String post, String mid) {
                try {
                    long currentTime = Calendar.getInstance().getTimeInMillis();
                    Log.i("currentTime", Long.toString(currentTime));
                    String urlPlus = "ajwvr=6&__rnd=" + currentTime;
                    String urlStr = BASE_ADD_URL + urlPlus;
                    Log.i("currentlink", urlStr);
                    URL url = new URL(urlStr);
                    String urlParameters = "act=post&mid=" + mid + "&uid=" + fromUserId+ "&forward=0&isroot=0&content=" + post + "&location=page_100505_home&module=scommlist&group_source=&pdetail=100505" + toUserId + "&_t=0";
                    byte[] postData = urlParameters.getBytes(StandardCharsets.UTF_8);
                    int postDataLength = postData.length;
                    HttpURLConnection conn = (HttpURLConnection) url.openConnection();

                    conn.setDoOutput(true);
                    conn.setInstanceFollowRedirects(false);
                    conn.setRequestMethod("POST");
                    conn.setRequestProperty(":authority", "www.weibo.com");
                    conn.setRequestProperty(":method", "POST");
                    conn.setRequestProperty(":path", "/aj/v6/comment/add?" + urlPlus);
                    conn.setRequestProperty(":scheme", "https");
                    conn.setRequestProperty("accept", "*/*");
                    conn.setRequestProperty("accept-Encoding", "gzip, deflate, br");
                    conn.setRequestProperty("accept-language", "en-US,en;q=0.9,zh-CN;q=0.8,zh;q=0.7");
                    conn.setRequestProperty("content-length", Integer.toString(postDataLength));
                    conn.setRequestProperty("content-type", "application/x-www-form-urlencoded");
                    //fromUsers cookies: conn.setRequestProperty("Cookie", "_s_tentry=passport.weibo.com; Apache=6768366249729.394.1562348161240; SINAGLOBAL=6768366249729.394.1562348161240; ULV=1562348161343:1:1:1:6768366249729.394.1562348161240:; TC-V5-G0=b1761408ab251c6e55d3a11f8415fc72; Ugrow-G0=5c7144e56a57a456abed1d1511ad79e8; WBtopGlobal_register_version=8c86b9ca67e1b502; SCF=Ap9bCm324dUvNt7cc6YSR8TNdEzX53hlts-FBSaDwOovu0YnySuFTvm7QPr6oxc4O_J4KGkWkPmFLNCy5SZeD6Q.; SUB=_2A25wG-SpDeRhGedK6lMW8SnNyTiIHXVTUVFhrDV8PUNbmtBeLRmikW9NJAJzf1is6JN6_Fg3njs2c-ISUQxs8BC0; SUBP=0033WrSXqPxfM725Ws9jqgMF55529P9D9WhgX0VS9f218eMpXo3cggZa5JpX5K2hUgL.Fo2XeK2NeKMpeoB2dJLoIpvj9PLfi--fi-zpiKnfi--ciK.ci-8si--Xi-iWi-8h; SUHB=0V5j8R8MnIkLkN; ALF=1562955640; SSOLoginState=1562350841; un=zhuly203@sina.com; TC-Page-G0=153ff31dae1cf71cc65e7e399bfce283|1562350850|1562350669; wb_view_log_1411717124=1366*7681; webim_unReadCount=%7B%22time%22%3A1562350969468%2C%22dm_pub_total%22%3A19%2C%22chat_group_pc%22%3A0%2C%22allcountNum%22%3A66%2C%22msgbox%22%3A0%7D");
                    //fromUsers cookies: conn.setRequestProperty("Cookie", "Ugrow-G0=e1a5a1aae05361d646241e28c550f987; login_sid_t=03e79be8e067e0d8c1125b66bbb425fd; _ga=GA1.2.585351749.1561999190; YF-V5-G0=a5a6106293f9aeef5e34a2e71f04fae4; _s_tentry=-; __gads=ID=3ff1880afc11242f:T=1561999192:S=ALNI_MYZru1RAdisl0M7WXNAmMgsYvURiA; Apache=3365018883878.6455.1561999195292; SINAGLOBAL=3365018883878.6455.1561999195292; ULV=1561999195302:1:1:1:3365018883878.6455.1561999195292:; un=zhuly203@sina.com; TC-V5-G0=841d8e04c4761f733a87c822f72195f3; cross_origin_proto=SSL; TC-Page-G0=45685168db6903150ce64a1b7437dbbb|1562355285|1562355275; wb_view_log_1959389877=1440*9001; wb_view_log_1411717124=1440*9001; wb_cmtLike_1959389877=1; UOR=,,www.sina.com.cn; _gid=GA1.2.472741711.1562443244; WBStorage=988f187486ad9919|undefined; wb_view_log=1440*9001; SUBP=0033WrSXqPxfM725Ws9jqgMF55529P9D9WhgX0VS9f218eMpXo3cggZa5JpX5K2hUgL.Fo2XeK2NeKMpeoB2dJLoIpvj9PLfi--fi-zpiKnfi--ciK.ci-8si--Xi-iWi-8h; ALF=1593979248; SSOLoginState=1562443249; SCF=AgUdXPG_5j06xuMSMl9ZGK5KIbca2vQhHme4ydAgEPYptHrHXCghgYoKmnW1kX43M6Uw7zOX5_z6ucTZprioRxk.; SUB=_2A25wJI2iDeRhGedK6lMW8SnNyTiIHXVTU_hqrDV8PUNbmtBeLVbGkW9NJAJzf2HTLNSRPpOpyqd8Yf88GMGZNiee; SUHB=05i8B5z109eUyO; wvr=6; YF-Page-G0=5161d669e1ac749e79d0f9c1904bc7bf|1562443321|1562443230; webim_unReadCount=%7B%22time%22%3A1562443410259%2C%22dm_pub_total%22%3A19%2C%22chat_group_pc%22%3A0%2C%22allcountNum%22%3A66%2C%22msgbox%22%3A0%7D");
                    //toUsers cookies(send post to self):  conn.setRequestProperty("Cookie","Ugrow-G0=e1a5a1aae05361d646241e28c550f987; login_sid_t=03e79be8e067e0d8c1125b66bbb425fd; _ga=GA1.2.585351749.1561999190; YF-V5-G0=a5a6106293f9aeef5e34a2e71f04fae4; _s_tentry=-; __gads=ID=3ff1880afc11242f:T=1561999192:S=ALNI_MYZru1RAdisl0M7WXNAmMgsYvURiA; Apache=3365018883878.6455.1561999195292; SINAGLOBAL=3365018883878.6455.1561999195292; ULV=1561999195302:1:1:1:3365018883878.6455.1561999195292:; TC-V5-G0=841d8e04c4761f733a87c822f72195f3; cross_origin_proto=SSL; wb_cmtLike_1959389877=1; UOR=,,www.sina.com.cn; wb_cmtLike_1411717124=1; YF-Page-G0=afcf131cd4181c1cbdb744cd27663d8d|1562529222|1562529155; SSOLoginState=1562530077; un=anneqinzhi@163.com; wvr=6; wb_view_log_1959389877=1440*9001; wb_view_log_1411717124=1440*9001; webim_unReadCount=%7B%22time%22%3A1562616474236%2C%22dm_pub_total%22%3A21%2C%22chat_group_pc%22%3A263%2C%22allcountNum%22%3A284%2C%22msgbox%22%3A0%7D; TC-Page-G0=2f200ef68557e15c78db077758a88e1f|1562619464|1562619464; SUBP=0033WrSXqPxfM725Ws9jqgMF55529P9D9WFaDrQb.dX.5.Q_RYKnxHWB5JpX5KMhUgL.Fo24SK.01h.RS0M2dJLoIEXLxK-LB--L1h.LxK-LBKnL1-eLxKqL1-eLBo2LxKqL1-eLBo2LxKqL1-eLBo2t; ALF=1594155466; SCF=AgUdXPG_5j06xuMSMl9ZGK5KIbca2vQhHme4ydAgEPYpHqMmgAJXljxrGo_GgQPFI6R0u4A50GZ7VdqaNyp_hBA.; SUB=_2A25wJ94eDeRhGedH7lsS-CfEzDuIHXVTVUjWrDV8PUNbmtBeLWLdkW9NULnl4J83kOuZ9ooVe4VP27W1ilP3jG5R; SUHB=0OM14FbrWELBZ7");
                    // 朱的cookies: conn.setRequestProperty("Cookie","Ugrow-G0=e1a5a1aae05361d646241e28c550f987; login_sid_t=03e79be8e067e0d8c1125b66bbb425fd; _ga=GA1.2.585351749.1561999190; YF-V5-G0=a5a6106293f9aeef5e34a2e71f04fae4; _s_tentry=-; __gads=ID=3ff1880afc11242f:T=1561999192:S=ALNI_MYZru1RAdisl0M7WXNAmMgsYvURiA; Apache=3365018883878.6455.1561999195292; SINAGLOBAL=3365018883878.6455.1561999195292; ULV=1561999195302:1:1:1:3365018883878.6455.1561999195292:; TC-V5-G0=841d8e04c4761f733a87c822f72195f3; cross_origin_proto=SSL; wb_cmtLike_1959389877=1; wb_cmtLike_1411717124=1; YF-Page-G0=afcf131cd4181c1cbdb744cd27663d8d|1562529222|1562529155; wb_view_log_1959389877=1440*9001; wb_view_log_1411717124=1440*9001; UOR=,,login.sina.com.cn; _gid=GA1.2.659838515.1562620284; WBStorage=988f187486ad9919|undefined; wb_view_log=1440*9001; SUBP=0033WrSXqPxfM725Ws9jqgMF55529P9D9WhgX0VS9f218eMpXo3cggZa5JpX5K2hUgL.Fo2XeK2NeKMpeoB2dJLoIpvj9PLfi--fi-zpiKnfi--ciK.ci-8si--Xi-iWi-8h; SSOLoginState=1562620298; ALF=1594156311; SCF=AgUdXPG_5j06xuMSMl9ZGK5KIbca2vQhHme4ydAgEPYp3-QUo3abKOxlz1zmhSADogx3UdHDuzbjfIUvFhAbeb0.; SUB=_2A25wJ8HJDeRhGedK6lMW8SnNyTiIHXVTVLQBrDV8PUNbmtBeLRHVkW9NJAJzf3PA8AghZaRJL-Jr-kLQEjZXhVUw; SUHB=0jBOQTKk6BRHNY; un=zhuly203@sina.com; wvr=6; TC-Page-G0=c4376343b8c98031e29230e0923842a5|1562620319|1562620174; webim_unReadCount=%7B%22time%22%3A1562620350349%2C%22dm_pub_total%22%3A19%2C%22chat_group_pc%22%3A0%2C%22allcountNum%22%3A66%2C%22msgbox%22%3A0%7D");
                    //conn.setRequestProperty("Cookie","Ugrow-G0=e1a5a1aae05361d646241e28c550f987; login_sid_t=03e79be8e067e0d8c1125b66bbb425fd; _ga=GA1.2.585351749.1561999190; YF-V5-G0=a5a6106293f9aeef5e34a2e71f04fae4; _s_tentry=-; __gads=ID=3ff1880afc11242f:T=1561999192:S=ALNI_MYZru1RAdisl0M7WXNAmMgsYvURiA; Apache=3365018883878.6455.1561999195292; SINAGLOBAL=3365018883878.6455.1561999195292; ULV=1561999195302:1:1:1:3365018883878.6455.1561999195292:; TC-V5-G0=841d8e04c4761f733a87c822f72195f3; cross_origin_proto=SSL; wb_cmtLike_1959389877=1; wb_cmtLike_1411717124=1; YF-Page-G0=afcf131cd4181c1cbdb744cd27663d8d|1562529222|1562529155; UOR=,,login.sina.com.cn; _gid=GA1.2.659838515.1562620284; wb_view_log_1411717124=1440*9001; WBStorage=988f187486ad9919|undefined; wb_view_log=1440*9001; wb_view_log_1959389877=1440*9001; SUBP=0033WrSXqPxfM725Ws9jqgMF55529P9D9WFaDrQb.dX.5.Q_RYKnxHWB5JpX5K2hUgL.Fo24SK.01h.RS0M2dJLoIEXLxK-LB--L1h.LxK-LBKnL1-eLxKqL1-eLBo2LxKqL1-eLBo2LxKqL1-eLBo2t; ALF=1594234875; SSOLoginState=1562698876; SCF=AgUdXPG_5j06xuMSMl9ZGK5KIbca2vQhHme4ydAgEPYp8tAdojyBMRecX0mjfqhcu0oBEUWLYE4ShZfQWSF0tUo.; SUB=_2A25wIJQtDeRhGedH7lsS-CfEzDuIHXVTV4LlrDV8PUNbmtBeLVPxkW9NULnl4JmsQ7w52SLxOYmj_U1lBQ7Q95YP; SUHB=0P1dObTDmSQsRV; un=anneqinzhi@163.com; wvr=6; TC-Page-G0=45685168db6903150ce64a1b7437dbbb|1562698944|1562698936; webim_unReadCount=%7B%22time%22%3A1562699016496%2C%22dm_pub_total%22%3A22%2C%22chat_group_pc%22%3A337%2C%22allcountNum%22%3A360%2C%22msgbox%22%3A0%7D");
                    //conn.setRequestProperty("Cookie","Ugrow-G0=e1a5a1aae05361d646241e28c550f987; login_sid_t=03e79be8e067e0d8c1125b66bbb425fd; _ga=GA1.2.585351749.1561999190; YF-V5-G0=a5a6106293f9aeef5e34a2e71f04fae4; _s_tentry=-; __gads=ID=3ff1880afc11242f:T=1561999192:S=ALNI_MYZru1RAdisl0M7WXNAmMgsYvURiA; Apache=3365018883878.6455.1561999195292; SINAGLOBAL=3365018883878.6455.1561999195292; ULV=1561999195302:1:1:1:3365018883878.6455.1561999195292:; TC-V5-G0=841d8e04c4761f733a87c822f72195f3; cross_origin_proto=SSL; wb_cmtLike_1959389877=1; wb_cmtLike_1411717124=1; UOR=,,login.sina.com.cn; SSOLoginState=1562698876; un=un=zhuly203@sina.com; wvr=6; SUBP=0033WrSXqPxfM725Ws9jqgMF55529P9D9WFaDrQb.dX.5.Q_RYKnxHWB5JpX5KMhUgL.Fo24SK.01h.RS0M2dJLoIEXLxK-LB--L1h.LxK-LBKnL1-eLxKqL1-eLBo2LxKqL1-eLBo2LxKqL1-eLBo2t; ALF=1594322754; SCF=AgUdXPG_5j06xuMSMl9ZGK5KIbca2vQhHme4ydAgEPYp22r_4fyQxJu-_xBbHZrjiVQwELTedi-KNtwNaDM3ykk.; SUB=_2A25wIkuWDeRhGedH7lsS-CfEzDuIHXVTVjperDV8PUNbmtBeLXakkW9NULnl4HGbrWnWcfyuOMWndh51GUHAWvj9; SUHB=0U1YDrXs0EHt92; TC-Page-G0=c4376343b8c98031e29230e0923842a5|1562788882|1562788875; wb_view_log_1411717124=1440*9001; wb_view_log_1959389877=1440*9001; YF-Page-G0=237c624133c0bee3e8a0a5d9466b74eb|1562862690|1562862680; webim_unReadCount=%7B%22time%22%3A1562862929996%2C%22dm_pub_total%22%3A24%2C%22chat_group_pc%22%3A618%2C%22allcountNum%22%3A642%2C%22msgbox%22%3A0%7D");
                    conn.setRequestProperty("Cookie","Ugrow-G0=e1a5a1aae05361d646241e28c550f987; login_sid_t=03e79be8e067e0d8c1125b66bbb425fd; _ga=GA1.2.585351749.1561999190; YF-V5-G0=a5a6106293f9aeef5e34a2e71f04fae4; _s_tentry=-; __gads=ID=3ff1880afc11242f:T=1561999192:S=ALNI_MYZru1RAdisl0M7WXNAmMgsYvURiA; Apache=3365018883878.6455.1561999195292; SINAGLOBAL=3365018883878.6455.1561999195292; ULV=1561999195302:1:1:1:3365018883878.6455.1561999195292:; TC-V5-G0=841d8e04c4761f733a87c822f72195f3; cross_origin_proto=SSL; wb_cmtLike_1959389877=1; wb_cmtLike_1411717124=1; UOR=,,login.sina.com.cn; YF-Page-G0=f1e19cba80f4eeaeea445d7b50e14ebb|1562875829|1562875744; _gid=GA1.2.756339551.1563008763; wb_view_log=1440*9001; SUBP=0033WrSXqPxfM725Ws9jqgMF55529P9D9WFaDrQb.dX.5.Q_RYKnxHWB5JpX5K2hUgL.Fo24SK.01h.RS0M2dJLoIEXLxK-LB--L1h.LxK-LBKnL1-eLxKqL1-eLBo2LxKqL1-eLBo2LxKqL1-eLBo2t; ALF=1594544811; SSOLoginState=1563008919; SCF=AgUdXPG_5j06xuMSMl9ZGK5KIbca2vQhHme4ydAgEPYp0dUtZSBciA-EsvEgUTcH1V7DBWZ0pXusmij_i9F7Lwo.; SUB=_2A25wLe99DeRhGedH7lsS-CfEzDuIHXVTW0e1rDV8PUNbmtBeLUfEkW9NULnl4B5abuTgEVpB3ePOdZONwGbTmzO8; SUHB=0A0YARs09-vSmy; un=anneqinzhi@163.com; wvr=6; wb_view_log_1959389877=1440*9001; TC-Page-G0=2f200ef68557e15c78db077758a88e1f|1563008888|1563008744; webim_unReadCount=%7B%22time%22%3A1563010838226%2C%22dm_pub_total%22%3A25%2C%22chat_group_pc%22%3A755%2C%22allcountNum%22%3A780%2C%22msgbox%22%3A0%7D");
                    conn.setRequestProperty("origin", "https://www.weibo.com");
                    conn.setRequestProperty("referer", "https://www.weibo.com/charlieper?is_all=1");
                    conn.setRequestProperty("user-agent", "Mozilla/5.0 (Windows NT 10.0; Win64; x64) AppleWebKit/537.36 (KHTML, like Gecko) Chrome/75.0.3770.100 Safari/537.36");
                    conn.setRequestProperty("x-requested-with", "XMLHttpRequest");
                    conn.setDoInput(true);
                    conn.setUseCaches(false);

                    DataOutputStream os = new DataOutputStream(conn.getOutputStream());
                    os.write(postData);
                    os.flush();
                    os.close();

                    Log.i("method", "SendPost()");
                    Log.i("postContent", post);
                    Log.i("status", conn.getResponseMessage());
                    conn.disconnect();
                } catch (Exception e) {
                    e.printStackTrace();
                }
            }
    }

        private void largeLog(String tag, String content) {
            if (content.length() > 4000) {
                Log.i(tag, content.substring(0, 4000));
                largeLog(tag, content.substring(4000));
            } else {
                Log.i(tag, content);
            }
        }

        private String convertStreamToString(InputStream is) {

            BufferedReader reader = new BufferedReader(new InputStreamReader(is));
            StringBuilder sb = new StringBuilder();

            String line = null;
            try {
                while ((line = reader.readLine()) != null) {
                    sb.append(line + "\n");
                }
            } catch (IOException e) {
                e.printStackTrace();
            } finally {
                try {
                    is.close();
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
            return sb.toString();
        }
//example: mid=\"4391254802397257\"
        private String getMid(String source, long latestTime) {
            int distance = source.length();
            int indexOfLTime = content.indexOf(Long.toString(latestTime));
            ArrayList<String> midList = new ArrayList<String>();
            String midStr = "mid not found";
            Pattern pattern = Pattern.compile("(mid=)(\\\\\")(\\d{16}\\b)");
            Matcher m = pattern.matcher(source);
            while (m.find()) {
                String s = m.group(3);
                int indexOfMid = content.indexOf(s);
                int dis = Math.abs(indexOfLTime - indexOfMid);
                if (dis < distance) {
                    distance = dis;
                    midStr = s;
                }
            }
            Log.i("mid found", midStr);
            return midStr;
        }
//date=\"1562440193000\"
        private long getLatestTime(String source) {

            long latestTime = 0;
            Pattern pattern = Pattern.compile("(date=)(\\\\\")(\\d{13}\\b)");
            Matcher m = pattern.matcher(source);
            if (m.find()) {
                String s = m.group(3);
                Log.i("latestTime found ", s);
                latestTime = Long.parseLong(s);
            } else {
                Log.i("latestTime not found ", "not found");
            }
            return latestTime;
        }


        private boolean compareTime(long nowTime, long msgTime) {
            long result = nowTime-msgTime;
            if (result < 6000) {
                Log.i("抢抢抢", "nowTime = " + nowTime + "; " + "msgTime = " + msgTime);
                showNotification("抢抢抢", "There is a new Weibo!");
                return true;
            } else {
                Log.i("不抢", "nowTime = " + nowTime + "; " + "msgTime= " + msgTime);
                return false;
            }
        }


        private void showNotification(String title, String message) {
            long[] pattern = {500, 500, 500, 500, 500, 500, 500, 500, 500};
            NotificationManager mNotificationManager =
                    (NotificationManager) getSystemService(Context.NOTIFICATION_SERVICE);
            if (android.os.Build.VERSION.SDK_INT >= android.os.Build.VERSION_CODES.O) {
                NotificationChannel channel = new NotificationChannel("YOUR_CHANNEL_ID",
                        "YOUR_CHANNEL_NAME",
                        NotificationManager.IMPORTANCE_DEFAULT);
                channel.setDescription("YOUR_NOTIFICATION_CHANNEL_DISCRIPTION");
                mNotificationManager.createNotificationChannel(channel);
            }
            NotificationCompat.Builder mBuilder = new NotificationCompat.Builder(getApplicationContext(), "YOUR_CHANNEL_ID")
                    .setSmallIcon(R.mipmap.ic_launcher) // notification icon
                    .setContentTitle(title) // title for notification
                    .setContentText(message)// message for notification
                    .setSound(RingtoneManager.getDefaultUri(RingtoneManager.TYPE_NOTIFICATION))
                    .setVibrate(pattern)
                    .setAutoCancel(true); // clear notification after click
            Intent intent = new Intent(getApplicationContext(), MainActivity.class);
            PendingIntent pi = PendingIntent.getActivity(getApplicationContext(), 0, intent, PendingIntent.FLAG_UPDATE_CURRENT);
            mBuilder.setContentIntent(pi);
            mNotificationManager.notify(0, mBuilder.build());
        }
    }