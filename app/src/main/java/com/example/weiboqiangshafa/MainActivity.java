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
import android.widget.EditText;

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
    String cookies;
    String cookiePart1;
    String cookiePart2;
    EditText inputContent;
    Timer timer;
    //String fromUserId = "1411717124";
    String fromUserId = "1959389877";
    String toUserId = "1736988591";
    String postContent;
    String BASE_ALL_URL = "https://www.weibo.com/u/" + toUserId + "?is_all=1";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        final Button btn_start = (Button) findViewById(R.id.btnStart);
        final Button btn_end = (Button) findViewById(R.id.btnEnd);
        inputContent = (EditText) findViewById(R.id.postContent);


        btn_end.setEnabled(false);
        btn_start.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                postContent = inputContent.getText().toString();
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
                //conn.setRequestProperty("Cookie","_ga=GA1.2.585351749.1561999190; __gads=ID=3ff1880afc11242f:T=1561999192:S=ALNI_MYZru1RAdisl0M7WXNAmMgsYvURiA; SINAGLOBAL=3365018883878.6455.1561999195292; wb_cmtLike_1959389877=1; wb_cmtLike_1411717124=1; UOR=,,login.sina.com.cn; un=anneqinzhi@163.com; Ugrow-G0=6fd5dedc9d0f894fec342d051b79679e; YF-V5-G0=2583080cfb7221db1341f7a137b6762e; _s_tentry=login.sina.com.cn; Apache=3016796784644.038.1563095308546; ULV=1563095308561:2:2:1:3016796784644.038.1563095308546:1561999195302; WBtopGlobal_register_version=8c86b9ca67e1b502; SSOLoginState=1563362588; TC-V5-G0=1ac1bd7677fc7b61611a0c3a9b6aa0b4; SUBP=0033WrSXqPxfM725Ws9jqgMF55529P9D9WFaDrQb.dX.5.Q_RYKnxHWB5JpX5KMhUgL.Fo24SK.01h.RS0M2dJLoIEXLxK-LB--L1h.LxK-LBKnL1-eLxKqL1-eLBo2LxKqL1-eLBo2LxKqL1-eLBo2t; ALF=1595242375; SCF=AgUdXPG_5j06xuMSMl9ZGK5KIbca2vQhHme4ydAgEPYplnvJ2x7lPFMMaRNO88h7Hkw2s0P0oqUM7_Mwhh4_vvA.; SUB=_2A25wMDRbDeRhGedH7lsS-CfEzDuIHXVTRCKTrDV8PUNbmtBeLUXFkW9NULnl4FCfkUDwIXveYi9fubStcXtUbVt_; SUHB=0id6_pHBBzCRJT; wb_view_log_1959389877=1440*9001; YF-Page-G0=e57fcdc279d2f9295059776dec6d0214|1563706650|1563706373; webim_unReadCount=%7B%22time%22%3A1563706658896%2C%22dm_pub_total%22%3A32%2C%22chat_group_pc%22%3A1431%2C%22allcountNum%22%3A1463%2C%22msgbox%22%3A0%7D");
                //conn.setRequestProperty("Cookie","_ga=GA1.2.585351749.1561999190; __gads=ID=3ff1880afc11242f:T=1561999192:S=ALNI_MYZru1RAdisl0M7WXNAmMgsYvURiA; SINAGLOBAL=3365018883878.6455.1561999195292; wb_cmtLike_1959389877=1; wb_cmtLike_1411717124=1; UOR=,,login.sina.com.cn; Ugrow-G0=6fd5dedc9d0f894fec342d051b79679e; YF-V5-G0=2583080cfb7221db1341f7a137b6762e; _s_tentry=login.sina.com.cn; Apache=3016796784644.038.1563095308546; ULV=1563095308561:2:2:1:3016796784644.038.1563095308546:1561999195302; WBtopGlobal_register_version=8c86b9ca67e1b502; SSOLoginState=1563362588; TC-V5-G0=1ac1bd7677fc7b61611a0c3a9b6aa0b4; YF-Page-G0=4b5a51adf43e782f0f0fb9c1ea76df93|1563710619|1563710619; SUBP=0033WrSXqPxfM725Ws9jqgMF55529P9D9WFaDrQb.dX.5.Q_RYKnxHWB5JpX5KMhUgL.Fo24SK.01h.RS0M2dJLoIEXLxK-LB--L1h.LxK-LBKnL1-eLxKqL1-eLBo2LxKqL1-eLBo2LxKqL1-eLBo2t; ALF=1595857031; SCF=AgUdXPG_5j06xuMSMl9ZGK5KIbca2vQhHme4ydAgEPYpunsBlOpKsCatZPE8MC93nVv4WheT-SP0BnS42JqMA1k.; SUB=_2A25wOdVbDeRhGedH7lsS-CfEzDuIHXVTT0GTrDV8PUNbmtBeLU6nkW9NULnl4D6z01_TsogDyvv0yo9vzb8qC7P5; SUHB=0C1T0_fhckfwW3; wb_view_log_1959389877=1440*9001; TC-Page-G0=51e9db4bd1cd84f5fb5f9b32772c2750|1564321238|1564321029; webim_unReadCount=%7B%22time%22%3A1564321266847%2C%22dm_pub_total%22%3A32%2C%22chat_group_client%22%3A643%2C%22allcountNum%22%3A675%2C%22msgbox%22%3A0%7D");
                //conn.setRequestProperty("Cookie","_ga=GA1.2.585351749.1561999190; __gads=ID=3ff1880afc11242f:T=1561999192:S=ALNI_MYZru1RAdisl0M7WXNAmMgsYvURiA; SINAGLOBAL=3365018883878.6455.1561999195292; UOR=,,login.sina.com.cn; _s_tentry=login.sina.com.cn; Apache=3016796784644.038.1563095308546; ULV=1563095308561:2:2:1:3016796784644.038.1563095308546:1561999195302; SSOLoginState=1563362588; webim_unReadCount=%7B%22time%22%3A1564378799343%2C%22dm_pub_total%22%3A32%2C%22chat_group_client%22%3A882%2C%22allcountNum%22%3A914%2C%22msgbox%22%3A0%7D; SUBP=0033WrSXqPxfM725Ws9jqgMF55529P9D9WFaDrQb.dX.5.Q_RYKnxHWB5JpX5KMhUgL.Fo24SK.01h.RS0M2dJLoIEXLxK-LB--L1h.LxK-LBKnL1-eLxKqL1-eLBo2LxKqL1-eLBo2LxKqL1-eLBo2t; ALF=1595950840; SCF=AgUdXPG_5j06xuMSMl9ZGK5KIbca2vQhHme4ydAgEPYpiX8UVtLkFUUgpzMg4NO5M0A003qiYdRdKGOWASGkvow.; SUB=_2A25wO2MtDeRhGedH7lsS-CfEzDuIHXVTMdPlrDV8PUNbmtBeLXT-kW9NULnl4FFbr2JBk7Q7diXFwhKZbrjlb7Nm; SUHB=0h1YhDkZC1PmmZ");
                conn.setRequestProperty("Cookie","_ga=GA1.2.585351749.1561999190; __gads=ID=3ff1880afc11242f:T=1561999192:S=ALNI_MYZru1RAdisl0M7WXNAmMgsYvURiA; SINAGLOBAL=3365018883878.6455.1561999195292; wb_cmtLike_1959389877=1; wb_cmtLike_1411717124=1; UOR=,,login.sina.com.cn; Ugrow-G0=6fd5dedc9d0f894fec342d051b79679e; YF-V5-G0=2583080cfb7221db1341f7a137b6762e; _s_tentry=login.sina.com.cn; Apache=3016796784644.038.1563095308546; ULV=1563095308561:2:2:1:3016796784644.038.1563095308546:1561999195302; WBtopGlobal_register_version=8c86b9ca67e1b502; TC-V5-G0=1ac1bd7677fc7b61611a0c3a9b6aa0b4; YF-Page-G0=70333fc8bc96e3a01b1d703feab3b41c|1564415521|1564415521; wb_view_log_1959389877=1440*9001; SCF=AgUdXPG_5j06xuMSMl9ZGK5KIbca2vQhHme4ydAgEPYpahnnxOmDuGB40pVkp5ETlVN6-Ue7uVGDCEWz4B0buLA.; SUB=_2A25wRAfZDeRhGedH7lsS-CfEzDuIHXVTMH4RrDV8PUNbmtBeLRfTkW9NULnl4Jzoh1vYNsL3-M3Lh3W9eUtT2ulw; SUBP=0033WrSXqPxfM725Ws9jqgMF55529P9D9WFaDrQb.dX.5.Q_RYKnxHWB5JpX5KMhUgL.Fo24SK.01h.RS0M2dJLoIEXLxK-LB--L1h.LxK-LBKnL1-eLxKqL1-eLBo2LxKqL1-eLBo2LxKqL1-eLBo2t; SUHB=0V50uqZlbIsCOB; webim_unReadCount=%7B%22time%22%3A1564506002525%2C%22dm_pub_total%22%3A34%2C%22chat_group_client%22%3A1275%2C%22allcountNum%22%3A1309%2C%22msgbox%22%3A0%7D");
                conn.setRequestProperty("upgrade-insecure-requests", "1");
                conn.setRequestProperty("user-agent", "Mozilla/5.0 (Macintosh; Intel Mac OS X 10_14_5) AppleWebKit/537.36 (KHTML, like Gecko) Chrome/75.0.3770.100 Safari/537.36");

                Log.i("method", "GetPost()");
                Log.i("status", conn.getResponseMessage());
                content = convertStreamToString(conn.getInputStream());
                String setCookie = conn.getHeaderField("set-cookie");
                Log.i("set-cookie", setCookie);
                String [] parts = setCookie.split(";");
                cookies = parts[0];
                Log.i("cookies", cookies);
                String [] cookieParts = cookies.split("\\|");
                cookiePart1 = cookieParts[0];
                cookiePart2 = cookieParts[2];
                Log.i("cookiePart1", cookiePart1);
                Log.i("cookiePart2", cookiePart2);

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

                    //String cookieSend = "_ga=GA1.2.585351749.1561999190; __gads=ID=3ff1880afc11242f:T=1561999192:S=ALNI_MYZru1RAdisl0M7WXNAmMgsYvURiA; SINAGLOBAL=3365018883878.6455.1561999195292; wb_cmtLike_1959389877=1; wb_cmtLike_1411717124=1; UOR=,,login.sina.com.cn; Ugrow-G0=6fd5dedc9d0f894fec342d051b79679e; YF-V5-G0=2583080cfb7221db1341f7a137b6762e; _s_tentry=login.sina.com.cn; Apache=3016796784644.038.1563095308546; ULV=1563095308561:2:2:1:3016796784644.038.1563095308546:1561999195302; WBtopGlobal_register_version=8c86b9ca67e1b502; TC-V5-G0=1ac1bd7677fc7b61611a0c3a9b6aa0b4; YF-Page-G0=70333fc8bc96e3a01b1d703feab3b41c|1564415521|1564415521; wb_view_log_1959389877=1440*9001; SCF=AgUdXPG_5j06xuMSMl9ZGK5KIbca2vQhHme4ydAgEPYpahnnxOmDuGB40pVkp5ETlVN6-Ue7uVGDCEWz4B0buLA.; SUB=_2A25wRAfZDeRhGedH7lsS-CfEzDuIHXVTMH4RrDV8PUNbmtBeLRfTkW9NULnl4Jzoh1vYNsL3-M3Lh3W9eUtT2ulw; SUBP=0033WrSXqPxfM725Ws9jqgMF55529P9D9WFaDrQb.dX.5.Q_RYKnxHWB5JpX5KMhUgL.Fo24SK.01h.RS0M2dJLoIEXLxK-LB--L1h.LxK-LBKnL1-eLxKqL1-eLBo2LxKqL1-eLBo2LxKqL1-eLBo2t; SUHB=0V50uqZlbIsCOB; wvr=6; "+ cookies + "; webim_unReadCount=%7B%22time%22%3A1564510021471%2C%22dm_pub_total%22%3A34%2C%22chat_group_client%22%3A1277%2C%22allcountNum%22%3A1313%2C%22msgbox%22%3A0%7D";
                    //String cookieSend = "_ga=GA1.2.585351749.1561999190; __gads=ID=3ff1880afc11242f:T=1561999192:S=ALNI_MYZru1RAdisl0M7WXNAmMgsYvURiA; SINAGLOBAL=3365018883878.6455.1561999195292; wb_cmtLike_1959389877=1; wb_cmtLike_1411717124=1; UOR=,,login.sina.com.cn; Ugrow-G0=6fd5dedc9d0f894fec342d051b79679e; YF-V5-G0=2583080cfb7221db1341f7a137b6762e; _s_tentry=login.sina.com.cn; Apache=3016796784644.038.1563095308546; ULV=1563095308561:2:2:1:3016796784644.038.1563095308546:1561999195302; WBtopGlobal_register_version=8c86b9ca67e1b502; TC-V5-G0=1ac1bd7677fc7b61611a0c3a9b6aa0b4; YF-Page-G0=70333fc8bc96e3a01b1d703feab3b41c|1564415521|1564415521; wb_view_log_1959389877=1440*9001; SCF=AgUdXPG_5j06xuMSMl9ZGK5KIbca2vQhHme4ydAgEPYpahnnxOmDuGB40pVkp5ETlVN6-Ue7uVGDCEWz4B0buLA.; SUB=_2A25wRAfZDeRhGedH7lsS-CfEzDuIHXVTMH4RrDV8PUNbmtBeLRfTkW9NULnl4Jzoh1vYNsL3-M3Lh3W9eUtT2ulw; SUBP=0033WrSXqPxfM725Ws9jqgMF55529P9D9WFaDrQb.dX.5.Q_RYKnxHWB5JpX5KMhUgL.Fo24SK.01h.RS0M2dJLoIEXLxK-LB--L1h.LxK-LBKnL1-eLxKqL1-eLBo2LxKqL1-eLBo2LxKqL1-eLBo2t; SUHB=0V50uqZlbIsCOB; wvr=6; TC-Page-G0=51e9db4bd1cd84f5fb5f9b32772c2750; webim_unReadCount=%7B%22time%22%3A1564510021471%2C%22dm_pub_total%22%3A34%2C%22chat_group_client%22%3A1277%2C%22allcountNum%22%3A1313%2C%22msgbox%22%3A0%7D";
                    //String cookieSend = "_ga=GA1.2.585351749.1561999190; __gads=ID=3ff1880afc11242f:T=1561999192:S=ALNI_MYZru1RAdisl0M7WXNAmMgsYvURiA; SINAGLOBAL=3365018883878.6455.1561999195292; wb_cmtLike_1959389877=1; wb_cmtLike_1411717124=1; UOR=,,login.sina.com.cn; Ugrow-G0=6fd5dedc9d0f894fec342d051b79679e; YF-V5-G0=2583080cfb7221db1341f7a137b6762e; _s_tentry=login.sina.com.cn; Apache=3016796784644.038.1563095308546; ULV=1563095308561:2:2:1:3016796784644.038.1563095308546:1561999195302; WBtopGlobal_register_version=8c86b9ca67e1b502; TC-V5-G0=1ac1bd7677fc7b61611a0c3a9b6aa0b4; TC-Page-G0=b993e9b6e353749ed3459e1837a0ae89|1564512982|1564512869; wb_view_log_1959389877=1440*9001; login_sid_t=658cf4ab6ebee41fe9d8f1fa7b43b116; cross_origin_proto=SSL; _gid=GA1.2.784757784.1564582116; wb_view_log=1440*9001; appkey=; WB_register_version=307744aa77dd5677; SCF=AgUdXPG_5j06xuMSMl9ZGK5KIbca2vQhHme4ydAgEPYptUxNXwBr2m9oDqQMz33bnL4GcGojGB_WwONRIO6AkaE.; SUB=_2A25wRaIaDeRhGedH7lsS-CfEzDuIHXVTMpTSrDV8PUNbmtBeLRfDkW9NULnl4F-uZ-YNyVR1qQSI2zDFLUG0pszg; SUBP=0033WrSXqPxfM725Ws9jqgMF55529P9D9WFaDrQb.dX.5.Q_RYKnxHWB5JpX5K2hUgL.Fo24SK.01h.RS0M2dJLoIEXLxK-LB--L1h.LxK-LBKnL1-eLxKqL1-eLBo2LxKqL1-eLBo2LxKqL1-eLBo2t; SUHB=03oUXpkQGo8B2l; ALF=1565199560; SSOLoginState=1564594762; un=anneqinzhi@163.com; YF-Page-G0=112e41ab9e0875e1b6850404cae8fa0e|1564597619|1564597335; webim_unReadCount=%7B%22time%22%3A1564597648312%2C%22dm_pub_total%22%3A35%2C%22chat_group_client%22%3A2193%2C%22allcountNum%22%3A2228%2C%22msgbox%22%3A0%7D";
                    //String cookieSend = "_ga=GA1.2.585351749.1561999190; __gads=ID=3ff1880afc11242f:T=1561999192:S=ALNI_MYZru1RAdisl0M7WXNAmMgsYvURiA; SINAGLOBAL=3365018883878.6455.1561999195292; wb_cmtLike_1959389877=1; wb_cmtLike_1411717124=1; UOR=,,login.sina.com.cn; Ugrow-G0=6fd5dedc9d0f894fec342d051b79679e; YF-V5-G0=2583080cfb7221db1341f7a137b6762e; _s_tentry=login.sina.com.cn; Apache=3016796784644.038.1563095308546; ULV=1563095308561:2:2:1:3016796784644.038.1563095308546:1561999195302; WBtopGlobal_register_version=8c86b9ca67e1b502; TC-V5-G0=1ac1bd7677fc7b61611a0c3a9b6aa0b4; TC-Page-G0=b993e9b6e353749ed3459e1837a0ae89|1564512982|1564512869; wb_view_log_1959389877=1440*9001; login_sid_t=658cf4ab6ebee41fe9d8f1fa7b43b116; cross_origin_proto=SSL; _gid=GA1.2.784757784.1564582116; wb_view_log=1440*9001; appkey=; WB_register_version=307744aa77dd5677; SCF=AgUdXPG_5j06xuMSMl9ZGK5KIbca2vQhHme4ydAgEPYptUxNXwBr2m9oDqQMz33bnL4GcGojGB_WwONRIO6AkaE.; SUB=_2A25wRaIaDeRhGedH7lsS-CfEzDuIHXVTMpTSrDV8PUNbmtBeLRfDkW9NULnl4F-uZ-YNyVR1qQSI2zDFLUG0pszg; SUBP=0033WrSXqPxfM725Ws9jqgMF55529P9D9WFaDrQb.dX.5.Q_RYKnxHWB5JpX5K2hUgL.Fo24SK.01h.RS0M2dJLoIEXLxK-LB--L1h.LxK-LBKnL1-eLxKqL1-eLBo2LxKqL1-eLBo2LxKqL1-eLBo2t; SUHB=03oUXpkQGo8B2l; ALF=1565199560; SSOLoginState=1564594762; un=anneqinzhi@163.com; YF-Page-G0=112e41ab9e0875e1b6850404cae8fa0e|1564597619|1564597335; webim_unReadCount=%7B%22time%22%3A1564597648312%2C%22dm_pub_total%22%3A35%2C%22chat_group_client%22%3A2193%2C%22allcountNum%22%3A2228%2C%22msgbox%22%3A0%7D";
                    //String cookieSend = "_ga=GA1.2.585351749.1561999190; __gads=ID=3ff1880afc11242f:T=1561999192:S=ALNI_MYZru1RAdisl0M7WXNAmMgsYvURiA; SINAGLOBAL=3365018883878.6455.1561999195292; wb_cmtLike_1959389877=1; wb_cmtLike_1411717124=1; UOR=,,login.sina.com.cn; Ugrow-G0=6fd5dedc9d0f894fec342d051b79679e; YF-V5-G0=2583080cfb7221db1341f7a137b6762e; _s_tentry=login.sina.com.cn; Apache=3016796784644.038.1563095308546; ULV=1563095308561:2:2:1:3016796784644.038.1563095308546:1561999195302; WBtopGlobal_register_version=8c86b9ca67e1b502; TC-V5-G0=1ac1bd7677fc7b61611a0c3a9b6aa0b4; TC-Page-G0=b993e9b6e353749ed3459e1837a0ae89|1564512982|1564512869; wb_view_log_1959389877=1440*9001; login_sid_t=658cf4ab6ebee41fe9d8f1fa7b43b116; cross_origin_proto=SSL; _gid=GA1.2.784757784.1564582116; wb_view_log=1440*9001; appkey=; WB_register_version=307744aa77dd5677; SCF=AgUdXPG_5j06xuMSMl9ZGK5KIbca2vQhHme4ydAgEPYptUxNXwBr2m9oDqQMz33bnL4GcGojGB_WwONRIO6AkaE.; SUB=_2A25wRaIaDeRhGedH7lsS-CfEzDuIHXVTMpTSrDV8PUNbmtBeLRfDkW9NULnl4F-uZ-YNyVR1qQSI2zDFLUG0pszg; SUBP=0033WrSXqPxfM725Ws9jqgMF55529P9D9WFaDrQb.dX.5.Q_RYKnxHWB5JpX5K2hUgL.Fo24SK.01h.RS0M2dJLoIEXLxK-LB--L1h.LxK-LBKnL1-eLxKqL1-eLBo2LxKqL1-eLBo2LxKqL1-eLBo2t; SUHB=03oUXpkQGo8B2l; ALF=1565199560; SSOLoginState=1564594762; un=anneqinzhi@163.com; YF-Page-G0=aac25801fada32565f5c5e59c7bd227b|1564598180|1564598071; webim_unReadCount=%7B%22time%22%3A1564598217759%2C%22dm_pub_total%22%3A35%2C%22chat_group_client%22%3A2193%2C%22allcountNum%22%3A2228%2C%22msgbox%22%3A0%7D";
                    //String cookieSend = "_ga=GA1.2.585351749.1561999190; __gads=ID=3ff1880afc11242f:T=1561999192:S=ALNI_MYZru1RAdisl0M7WXNAmMgsYvURiA; SINAGLOBAL=3365018883878.6455.1561999195292; wb_cmtLike_1959389877=1; wb_cmtLike_1411717124=1; UOR=,,login.sina.com.cn; Ugrow-G0=6fd5dedc9d0f894fec342d051b79679e; YF-V5-G0=2583080cfb7221db1341f7a137b6762e; _s_tentry=login.sina.com.cn; Apache=3016796784644.038.1563095308546; ULV=1563095308561:2:2:1:3016796784644.038.1563095308546:1561999195302; WBtopGlobal_register_version=8c86b9ca67e1b502; TC-V5-G0=1ac1bd7677fc7b61611a0c3a9b6aa0b4; TC-Page-G0=b993e9b6e353749ed3459e1837a0ae89|1564512982|1564512869; wb_view_log_1959389877=1440*9001; login_sid_t=658cf4ab6ebee41fe9d8f1fa7b43b116; cross_origin_proto=SSL; _gid=GA1.2.784757784.1564582116; wb_view_log=1440*9001; appkey=; WB_register_version=307744aa77dd5677; SCF=AgUdXPG_5j06xuMSMl9ZGK5KIbca2vQhHme4ydAgEPYptUxNXwBr2m9oDqQMz33bnL4GcGojGB_WwONRIO6AkaE.; SUB=_2A25wRaIaDeRhGedH7lsS-CfEzDuIHXVTMpTSrDV8PUNbmtBeLRfDkW9NULnl4F-uZ-YNyVR1qQSI2zDFLUG0pszg; SUBP=0033WrSXqPxfM725Ws9jqgMF55529P9D9WFaDrQb.dX.5.Q_RYKnxHWB5JpX5K2hUgL.Fo24SK.01h.RS0M2dJLoIEXLxK-LB--L1h.LxK-LBKnL1-eLxKqL1-eLBo2LxKqL1-eLBo2LxKqL1-eLBo2t; SUHB=03oUXpkQGo8B2l; ALF=1565199560; SSOLoginState=1564594762; un=anneqinzhi@163.com; YF-Page-G0=aac25801fada32565f5c5e59c7bd227b|1564598180|1564598071; webim_unReadCount=%7B%22time%22%3A1564598217759%2C%22dm_pub_total%22%3A35%2C%22chat_group_client%22%3A2193%2C%22allcountNum%22%3A2228%2C%22msgbox%22%3A0%7D";
                    //String cookieSend = "_ga=GA1.2.585351749.1561999190; __gads=ID=3ff1880afc11242f:T=1561999192:S=ALNI_MYZru1RAdisl0M7WXNAmMgsYvURiA; SINAGLOBAL=3365018883878.6455.1561999195292; wb_cmtLike_1959389877=1; wb_cmtLike_1411717124=1; UOR=,,login.sina.com.cn; Ugrow-G0=6fd5dedc9d0f894fec342d051b79679e; YF-V5-G0=2583080cfb7221db1341f7a137b6762e; _s_tentry=login.sina.com.cn; Apache=3016796784644.038.1563095308546; ULV=1563095308561:2:2:1:3016796784644.038.1563095308546:1561999195302; WBtopGlobal_register_version=8c86b9ca67e1b502; TC-V5-G0=1ac1bd7677fc7b61611a0c3a9b6aa0b4; TC-Page-G0=b993e9b6e353749ed3459e1837a0ae89|1564512982|1564512869; wb_view_log_1959389877=1440*9001; login_sid_t=658cf4ab6ebee41fe9d8f1fa7b43b116; cross_origin_proto=SSL; _gid=GA1.2.784757784.1564582116; wb_view_log=1440*9001; appkey=; WB_register_version=307744aa77dd5677; SCF=AgUdXPG_5j06xuMSMl9ZGK5KIbca2vQhHme4ydAgEPYptUxNXwBr2m9oDqQMz33bnL4GcGojGB_WwONRIO6AkaE.; SUB=_2A25wRaIaDeRhGedH7lsS-CfEzDuIHXVTMpTSrDV8PUNbmtBeLRfDkW9NULnl4F-uZ-YNyVR1qQSI2zDFLUG0pszg; SUBP=0033WrSXqPxfM725Ws9jqgMF55529P9D9WFaDrQb.dX.5.Q_RYKnxHWB5JpX5K2hUgL.Fo24SK.01h.RS0M2dJLoIEXLxK-LB--L1h.LxK-LBKnL1-eLxKqL1-eLBo2LxKqL1-eLBo2LxKqL1-eLBo2t; SUHB=03oUXpkQGo8B2l; ALF=1565199560; SSOLoginState=1564594762; un=anneqinzhi@163.com; YF-Page-G0=aac25801fada32565f5c5e59c7bd227b|1564598180|1564598071; webim_unReadCount=%7B%22time%22%3A1564598217759%2C%22dm_pub_total%22%3A35%2C%22chat_group_client%22%3A2193%2C%22allcountNum%22%3A2228%2C%22msgbox%22%3A0%7D";
                    String cookieSend = "_ga=GA1.2.585351749.1561999190; __gads=ID=3ff1880afc11242f:T=1561999192:S=ALNI_MYZru1RAdisl0M7WXNAmMgsYvURiA; SINAGLOBAL=3365018883878.6455.1561999195292; wb_cmtLike_1959389877=1; wb_cmtLike_1411717124=1; UOR=,,login.sina.com.cn; Ugrow-G0=6fd5dedc9d0f894fec342d051b79679e; YF-V5-G0=2583080cfb7221db1341f7a137b6762e; _s_tentry=login.sina.com.cn; Apache=3016796784644.038.1563095308546; ULV=1563095308561:2:2:1:3016796784644.038.1563095308546:1561999195302; WBtopGlobal_register_version=8c86b9ca67e1b502; TC-V5-G0=1ac1bd7677fc7b61611a0c3a9b6aa0b4; login_sid_t=658cf4ab6ebee41fe9d8f1fa7b43b116; cross_origin_proto=SSL; appkey=; WB_register_version=307744aa77dd5677; SSOLoginState=1564594762; un=anneqinzhi@163.com; wb_view_log_1959389877=1440*9001; TC-Page-G0=914ba011d20e5b7f25fe12574c186eda|1564636794|1564636675; SUBP=0033WrSXqPxfM725Ws9jqgMF55529P9D9WFaDrQb.dX.5.Q_RYKnxHWB5JpX5KMhUgL.Fo24SK.01h.RS0M2dJLoIEXLxK-LB--L1h.LxK-LBKnL1-eLxKqL1-eLBo2LxKqL1-eLBo2LxKqL1-eLBo2t; ALF=1596229655; SCF=AgUdXPG_5j06xuMSMl9ZGK5KIbca2vQhHme4ydAgEPYpy81IJznU1egE7uPNB2mO6DKyL7Q33AddvTrMVdnQeks.; SUB=_2A25wRyTLDeRhGedH7lsS-CfEzDuIHXVTNREDrDV8PUNbmtBeLWPykW9NULnl4AO69EpYmfh1gvy3NZgWLwHQ8ski; SUHB=0laUEbXzUG0zXt; "+cookiePart1+"|"+cookiePart2+"; webim_unReadCount=%7B%22time%22%3A1564694060935%2C%22dm_pub_total%22%3A36%2C%22chat_group_client%22%3A1869%2C%22allcountNum%22%3A1905%2C%22msgbox%22%3A0%7D";
                    //String cookieSend = "_ga=GA1.2.585351749.1561999190; __gads=ID=3ff1880afc11242f:T=1561999192:S=ALNI_MYZru1RAdisl0M7WXNAmMgsYvURiA; SINAGLOBAL=3365018883878.6455.1561999195292; wb_cmtLike_1959389877=1; wb_cmtLike_1411717124=1; UOR=,,login.sina.com.cn; Ugrow-G0=6fd5dedc9d0f894fec342d051b79679e; YF-V5-G0=2583080cfb7221db1341f7a137b6762e; _s_tentry=login.sina.com.cn; Apache=3016796784644.038.1563095308546; ULV=1563095308561:2:2:1:3016796784644.038.1563095308546:1561999195302; WBtopGlobal_register_version=8c86b9ca67e1b502; TC-V5-G0=1ac1bd7677fc7b61611a0c3a9b6aa0b4; login_sid_t=658cf4ab6ebee41fe9d8f1fa7b43b116; cross_origin_proto=SSL; appkey=; WB_register_version=307744aa77dd5677; SSOLoginState=1564594762; un=anneqinzhi@163.com; wb_view_log_1959389877=1440*9001; TC-Page-G0=914ba011d20e5b7f25fe12574c186eda|1564636794|1564636675; SUBP=0033WrSXqPxfM725Ws9jqgMF55529P9D9WFaDrQb.dX.5.Q_RYKnxHWB5JpX5KMhUgL.Fo24SK.01h.RS0M2dJLoIEXLxK-LB--L1h.LxK-LBKnL1-eLxKqL1-eLBo2LxKqL1-eLBo2LxKqL1-eLBo2t; ALF=1596229655; SCF=AgUdXPG_5j06xuMSMl9ZGK5KIbca2vQhHme4ydAgEPYpy81IJznU1egE7uPNB2mO6DKyL7Q33AddvTrMVdnQeks.; SUB=_2A25wRyTLDeRhGedH7lsS-CfEzDuIHXVTNREDrDV8PUNbmtBeLWPykW9NULnl4AO69EpYmfh1gvy3NZgWLwHQ8ski; SUHB=0laUEbXzUG0zXt; YF-Page-G0=bd9e74eeae022c6566619f45b931d426|1564694052|1564694045; webim_unReadCount=%7B%22time%22%3A1564694060935%2C%22dm_pub_total%22%3A36%2C%22chat_group_client%22%3A1869%2C%22allcountNum%22%3A1905%2C%22msgbox%22%3A0%7D";
                    conn.setRequestProperty("Cookie", cookieSend);
                    conn.setRequestProperty("origin", "https://www.weibo.com");
                    conn.setRequestProperty("referer", "https://www.weibo.com/charlieper?is_all=1");
                    conn.setRequestProperty("user-agent", "Mozilla/5.0 (Windows NT 10.0; Win64; x64) AppleWebKit/537.36 (KHTML, like Gecko) Chrome/75.0.3770.100 Safari/537.36");
                    conn.setRequestProperty("x-requested-with", "XMLHttpRequest");
                    conn.setDoInput(true);
                    conn.setUseCaches(false);

                    Log.i("sendPost cookie", cookieSend);

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
            //Pattern pattern = Pattern.compile("(mid=)(\\\\\")(\\d{16}\\b)");
            Pattern pattern = Pattern.compile("(feed_time:)(\\d{16}\\b)");
            Matcher m = pattern.matcher(source);
            while (m.find()) {
                String s = m.group(2);
                int indexOfMid = content.indexOf(s);
                int disToLtime = Math.abs(indexOfLTime - indexOfMid);
                if (disToLtime < distance) {
                    distance = disToLtime;
                    midStr = s;
                    Log.i("mid found", midStr);
                }
            }
            Log.i("final mid found", midStr);
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