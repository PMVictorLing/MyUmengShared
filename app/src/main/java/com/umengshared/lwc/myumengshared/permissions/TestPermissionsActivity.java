package com.umengshared.lwc.myumengshared.permissions;

import android.os.Bundle;

import com.umengshared.lwc.myumengshared.R;

public class TestPermissionsActivity extends PermissionsCheckActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_test_permissions);
    }
}
