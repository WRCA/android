package info.jiangchuan.wrca.dialogs;

import android.app.Dialog;
import android.content.Context;
import android.os.Bundle;
import android.text.TextUtils;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;

import com.google.gson.JsonObject;

import info.jiangchuan.wrca.Constant;
import info.jiangchuan.wrca.R;
import info.jiangchuan.wrca.models.Result;
import info.jiangchuan.wrca.parsers.ResultParser;
import info.jiangchuan.wrca.rest.Client;
import info.jiangchuan.wrca.util.DialogUtil;
import info.jiangchuan.wrca.util.ToastUtil;
import retrofit.Callback;
import retrofit.RetrofitError;
import retrofit.client.Response;

/**
 * Created by jiangchuan on 1/25/15.
 */
public class ForgetPassDialog extends Dialog implements android.view.View.OnClickListener{

    private static final String TAG = "ForgetPassDialog";
    Context context;

    public ForgetPassDialog(Context context) {
        super(context);
        this.context = context;
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.dialog_forget_password);
        DialogUtil.setup(context);
        setTitle("Retrieve Password");
        ((Button)findViewById(R.id.btn_send)).setOnClickListener(this);
    }


    @Override
    public void onClick(View v) {
        if (Constant.DEBUG)
            Log.d(TAG, Integer.toString(v.getId()));
        switch (v.getId()) {
            case R.id.btn_send: {
                EditText editText = (EditText) findViewById(R.id.txt_email);
                if (Constant.DEBUG)
                    Log.d(TAG, "onclick");
                String email = editText.getText().toString();
                if (TextUtils.isEmpty(email)) {
                    ToastUtil.showToast(context, "email cannot be empty");
                    return;
                }
                DialogUtil.showProgressDialog("wait...");
                Client.getApi().password(email, new Callback<JsonObject>() {
                    @Override
                    public void success(JsonObject jsonObject, Response response) {
                        DialogUtil.hideProgressDialog();
                        Result result = ResultParser.parse(jsonObject);
                        ToastUtil.showToast(context, result.getMessage());
                        dismiss();
                    }

                    @Override
                    public void failure(RetrofitError error) {
                        DialogUtil.hideProgressDialog();
                        dismiss();
                    }
                });
            }
            break;
        }

    }
}
