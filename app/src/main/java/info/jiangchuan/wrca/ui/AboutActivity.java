package info.jiangchuan.wrca.ui;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.ActionBarActivity;

import com.facebook.Settings;
import com.facebook.widget.LikeView;

import info.jiangchuan.wrca.R;

/**
 * Created by jiangchuan on 1/28/15.
 */
public class AboutActivity extends ActionBarActivity {

    LikeView likeView;

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        LikeView.handleOnActivityResult(this, requestCode, resultCode, data);
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_about);

        Settings.sdkInitialize(this);
        likeView = (LikeView) findViewById(R.id.like_view);
        likeView.setObjectId("https://www.facebook.com/pages/Willow-Ridge-Civic-Association/167497999970646?ref=hl");

    }
}
