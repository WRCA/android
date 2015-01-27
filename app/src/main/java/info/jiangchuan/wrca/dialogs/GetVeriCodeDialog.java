package info.jiangchuan.wrca.dialogs;

import android.app.Dialog;
import android.content.Context;
import android.os.Bundle;
import android.text.TextUtils;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;

import com.google.gson.JsonObject;

import info.jiangchuan.wrca.R;
import info.jiangchuan.wrca.models.Result;
import info.jiangchuan.wrca.parsers.ResultParser;
import info.jiangchuan.wrca.rest.Client;
import info.jiangchuan.wrca.rest.RestConst;
import info.jiangchuan.wrca.util.NetworkUtil;
import info.jiangchuan.wrca.util.ToastUtil;
import retrofit.Callback;
import retrofit.RetrofitError;

/**
 * Created by jiangchuan on 1/27/15.
 */
public class GetVeriCodeDialog extends Dialog implements android.view.View.OnClickListener{
    private static final String TAG = "GetVeriCodeDialog";
    Context context;
    public GetVeriCodeDialog(Context context) {
        super(context);
        this.context = context;
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.dialog_get_vericode);
        setTitle("Get Verification Code");
        ((Button)findViewById(R.id.btn_send)).setOnClickListener(this);
    }

    @Override
    public void onClick(View v) {
       switch (v.getId()) {
           case R.id.btn_send: {
               if (NetworkUtil.hasInternet(context) == false) {
                   ToastUtil.showToast(context, R.string.toast_system_offline);
                   break;
               }
               EditText editText = (EditText) findViewById(R.id.txt_email);
               String email = editText.getText().toString();
               if (TextUtils.isEmpty(email)) {
                   ToastUtil.showToast(context, "email cannot be empty");
                   break;
               }
               Client.getApi().vericode(email, new Callback<JsonObject>() {
                   @Override
                   public void success(JsonObject jsonObject, retrofit.client.Response response) {
                       Result result = ResultParser.parse(jsonObject);
                       switch (result.getStatus()) {
                           case RestConst.INT_STATUS_200: {
                               ToastUtil.showToast(context, result.getMessage());
                               break;
                           }
                       }
                   }

                   @Override
                   public void failure(RetrofitError error) {

                   }
               });
               break;
           }
       }
    }
}
