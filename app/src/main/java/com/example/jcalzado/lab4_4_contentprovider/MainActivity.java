package com.example.jcalzado.lab4_4_contentprovider;

import android.database.Cursor;
import android.net.Uri;
import android.provider.Telephony;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.EditText;

public class MainActivity extends AppCompatActivity {

    private EditText inCode;
    private String[] validCodes;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        inCode = (EditText) findViewById(R.id.inCode);
        validCodes = new String[]{"1456-5878","0012-6897","9657-4114","3397-4125"};
    }

    public void smsVerification (View v) {
        String[] headers = new String[] {Telephony.Sms.ADDRESS,Telephony.Sms.BODY};
        Cursor sms = getContentResolver().query(Uri.parse("content://sms/inbox"),headers,null,null,null);
    }
}
