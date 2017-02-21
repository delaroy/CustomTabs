package com.delaroystudios.chromecustomtab;

import android.net.Uri;
import android.os.Bundle;
import android.support.customtabs.CustomTabsIntent;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.EditText;

/**
 * Created by delaroy on 2/19/17.
 */
public class SimpleCustomTabActivity extends AppCompatActivity implements View.OnClickListener {

    private EditText mUrlEditText;

    @Override
    protected  void onCreate(Bundle savedInstanceState){
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_simple_custom_tab);
        findViewById(R.id.start_custom_tab).setOnClickListener(this);

        mUrlEditText = (EditText)findViewById(R.id.url);
    }

    @Override
    public void onClick(View v){
        int viewId = v.getId();

        switch (viewId){
            case R.id.start_custom_tab:
                String url = mUrlEditText.getText().toString();
                CustomTabsIntent customTabsIntent = new CustomTabsIntent.Builder().build();
                CustomTabActivityHelper.openCustomTab(
                        this, customTabsIntent, Uri.parse(url), new WebviewFallback()
                );
                break;
            default:
        }

    }
}
