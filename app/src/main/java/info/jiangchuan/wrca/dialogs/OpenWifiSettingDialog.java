package info.jiangchuan.wrca.dialogs;

import android.app.Dialog;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;

import info.jiangchuan.wrca.R;

/**
 * Created by jiangchuan on 1/27/15.
 */
public class OpenWifiSettingDialog extends Dialog  implements android.view.View.OnClickListener {
    Context context;

    public OpenWifiSettingDialog(Context context) {
        super(context);
        this.context = context;
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.dialog_open_wifi_setting);
        setTitle("You are offline");
        registerListener();
    }

    void registerListener() {
        ((Button)findViewById(R.id.btn_cancel)).setOnClickListener(this);
        ((Button)findViewById(R.id.btn_wifi_setting)).setOnClickListener(this);
    }
    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.btn_cancel: {
                dismiss();
                break;
            }
            case R.id.btn_wifi_setting: {
                context.startActivity(new Intent(android.provider.Settings.ACTION_WIRELESS_SETTINGS));
                dismiss();
                break;
            }
        }
    }
}
