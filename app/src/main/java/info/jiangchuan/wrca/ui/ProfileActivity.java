package info.jiangchuan.wrca.ui;

import android.os.Bundle;
import android.support.v7.app.ActionBarActivity;
import android.widget.TextView;

import info.jiangchuan.wrca.R;
import info.jiangchuan.wrca.WillowRidge;
import info.jiangchuan.wrca.models.User;

/**
 * Created by jiangchuan on 1/25/15.
 */
public class ProfileActivity extends ActionBarActivity{
    TextView txtEmail;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_profile);

        txtEmail = (TextView) findViewById(R.id.txt_email);
        User user = WillowRidge.getInstance().getUser();
        if (user == null) {
            return;
        }
        txtEmail.setText(user.getEmail());
    }

}
