package info.jiangchuan.wrca.ui;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.ActionBarActivity;
import android.view.Menu;
import android.view.MenuItem;
import android.webkit.WebView;

import info.jiangchuan.wrca.R;
import info.jiangchuan.wrca.ui.SettingsActivity;

/**
 * Created by jiangchuan on 1/25/15.
 */
public class WebActivity extends ActionBarActivity{
    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        int id = item.getItemId();
        switch (id) {
            case R.id.action_settings: {
                Intent intent = new Intent(this, SettingsActivity.class);
                startActivity(intent);
                break;
            }
        }
        return super.onOptionsItemSelected(item);
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.menu_web, menu);
        return true;
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_web);
        getSupportActionBar().setTitle("WRCA Site");

        setupBrowser();
    }

    private void setupBrowser() {
        WebView browser = (WebView) findViewById(R.id.web_view);
        browser.getSettings().setBuiltInZoomControls(true);
        browser.loadUrl("http://www.wrca.info");

    }
}
