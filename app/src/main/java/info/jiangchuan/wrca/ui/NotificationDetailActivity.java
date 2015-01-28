package info.jiangchuan.wrca.ui;

import android.os.Bundle;
import android.support.v7.app.ActionBarActivity;
import android.widget.TextView;

import info.jiangchuan.wrca.R;
import info.jiangchuan.wrca.models.Notification;

/**
 * Created by jiangchuan on 1/27/15.
 */
public class NotificationDetailActivity extends ActionBarActivity{
    TextView txtTitle;
    TextView txtContent;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_notif_detail);
        txtTitle = (TextView) findViewById(R.id.txt_time);
        txtContent = (TextView) findViewById(R.id.txt_content);

        setupContent();
    }

    void setupContent() {
        Notification notification = (Notification) getIntent().getSerializableExtra("notification");
        txtTitle.setText(notification.getTime());
        txtContent.setText(notification.getContent());
    }
}
