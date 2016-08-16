package com.yanzi.rtspdemo;

import android.media.MediaPlayer;
import android.net.Uri;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.VideoView;

public class MainActivity extends AppCompatActivity implements MediaPlayer.OnPreparedListener, MediaPlayer.OnErrorListener,
MediaPlayer.OnCompletionListener{
    VideoView videoview;
    Button btn_play;

     //String mVideoUrl = "rtsp://218.204.223.237:554/live/1/66251FC11353191F/e7ooqwcfbqjoo80j.sdp";
    //String mVideoUrl = "rtsp://mpv.cdn3.bigCDN.com:554/bigCDN/definst/mp4:bigbuckbunnyiphone_400.mp4";
    String mVideoUrl="rtsp://10.0.1.124";
   // String VIDEO_URL = "/sdcard/video.txt";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        videoview = (VideoView) findViewById(R.id.videoview);
        btn_play = (Button) findViewById(R.id.btn_play);
        btn_play.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
             //   getVideoUrl();
                playRtspStream(mVideoUrl);
            //    Toast.makeText(getApplicationContext(), "Please check " + VIDEO_URL + "whether exist", Toast.LENGTH_SHORT).show();
            }
        });


    }


    private void playRtspStream(String rtspUrl) {
        videoview.setVideoURI(Uri.parse(rtspUrl));
        videoview.requestFocus();
        videoview.setOnPreparedListener(this);
        videoview.setOnCompletionListener(this);
        videoview.setOnErrorListener(this);
//        videoview.setOnInfoListener(this);
       // startActivity(new Intent(Intent.ACTION_VIEW, Uri.parse(rtspUrl)));


    }

    @Override
    public void onPrepared(MediaPlayer mp) {
        Log.e("", "---onPrepared---dur = " + mp.getDuration());
        videoview.start();
    }

   /* private boolean getVideoUrl() {
        File f = new File(VIDEO_URL);

        if (!f.exists()) {
            return false;
        }
        try {
            FileInputStream fis = new FileInputStream(f);
            byte[] buffer = new byte[fis.available()];
            fis.read(buffer);
            mVideoUrl = new String(buffer, "UTF-8");
            Toast.makeText(getApplicationContext(), "videoUrl=" + mVideoUrl, Toast.LENGTH_SHORT).show();
            fis.close();
            return true;
        } catch (FileNotFoundException e) {
            e.printStackTrace();

        } catch (IOException e) {
            e.printStackTrace();
        }
        return false;
    }
*/

    @Override
    public boolean onError(MediaPlayer mp, int what, int extra) {
        String msg = "---onError---, what = " + what + "extra = " + extra;
        Log.e("onError",msg);
        return false;
    }

    @Override
    public void onCompletion(MediaPlayer mp) {
        Log.e("completed","---onCompletion---");
    }




}
