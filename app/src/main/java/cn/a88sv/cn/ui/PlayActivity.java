package cn.a88sv.cn.ui;

import android.app.Activity;
import android.content.Intent;
import android.content.pm.ActivityInfo;
import android.content.res.Configuration;
import android.os.Bundle;
import android.view.KeyEvent;
import android.view.Window;
import android.view.WindowManager;
import android.widget.TextView;

import com.pili.pldroid.player.widget.PLVideoTextureView;
import com.pili.pldroid.player.widget.PLVideoView;

import cn.a88sv.cn.MediaController;
import cn.a88sv.cn.R;

public class PlayActivity extends Activity {

    private PLVideoTextureView mVideoView;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        //全屏
        requestWindowFeature(Window.FEATURE_NO_TITLE);
        getWindow().setFlags(WindowManager.LayoutParams. FLAG_FULLSCREEN , WindowManager.LayoutParams. FLAG_FULLSCREEN);
        setContentView(R.layout.playlayout);
        if(this.getResources().getConfiguration().orientation == Configuration.ORIENTATION_PORTRAIT){
            setRequestedOrientation(ActivityInfo.SCREEN_ORIENTATION_LANDSCAPE);
        }
        mVideoView = (PLVideoTextureView) findViewById(R.id.PLVideoTextureView);
        TextView playname = (TextView) findViewById(R.id.playtvname);
        Intent intent=getIntent();
        Bundle bundle=intent.getExtras();
        playname.setText(bundle.getString("channelsname"));
        MediaController mMediaController = new MediaController(this);
        mVideoView.setMediaController(mMediaController);
        mVideoView.setDisplayAspectRatio(PLVideoView.ASPECT_RATIO_PAVED_PARENT);
        mVideoView.setVideoPath(bundle.getString("channelsurl"));

        mVideoView.start();
    }
    @Override
    public boolean onKeyDown(int keyCode, KeyEvent event) {
        if(keyCode==KeyEvent.KEYCODE_BACK){
            mVideoView.pause();
            finish();
        }
        return super.onKeyDown(keyCode, event);
    }
}
