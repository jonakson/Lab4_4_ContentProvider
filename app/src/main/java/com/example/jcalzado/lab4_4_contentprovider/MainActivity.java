package com.example.jcalzado.lab4_4_contentprovider;

import android.database.Cursor;
import android.net.Uri;
import android.provider.Telephony;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.EditText;
import android.widget.ProgressBar;
import android.widget.TextView;
import java.util.ArrayList;
import java.util.Timer;
import java.util.TimerTask;

public class MainActivity extends AppCompatActivity {

    private EditText inCode;
    private TextView verificationOK;
    private ProgressBar progressBar;
    private String[] validAddress;
    private String[] headers;
    private static boolean trigger;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        final Timer timer = new Timer();
        timer.schedule(new TimerTask() {
            @Override
            public void run() {
                if (trigger) {
                    verificationExec();
                } else {
                    timer.cancel();
                    timer.purge();
                }

            }
        }, 0, 5000);

        inCode = (EditText) findViewById(R.id.inCode);
        verificationOK = (TextView) findViewById(R.id.verificationOK);
        progressBar = (ProgressBar) findViewById(R.id.progressBar);
        validAddress = new String[] {"7700"};
        headers = new String[] {Telephony.Sms.Inbox.ADDRESS, Telephony.Sms.Inbox.BODY, Telephony.Sms.Inbox.DATE};
        trigger = true;

    }

    public void verificationExec() {
        this.runOnUiThread(smsVerification);
    }

    public Runnable smsVerification = new Runnable() {
        @Override
        public void run() {
            Cursor sms = getContentResolver().query(Uri.parse("content://sms/inbox"), headers, "address = ?", validAddress, "date DESC");
            if (sms.moveToFirst() && checkValidCodes(sms.getString(1))) {
                inCode.setText(sms.getString(1));
                progressBar.setVisibility(View.INVISIBLE);
                verificationOK.setText("Cuenta Verificada");
                trigger = false;
            }
        }
    };

    public boolean checkValidCodes(String code) {
        ArrayList<String> validCodes = new ArrayList<>();
        validCodes.add("9856-1235");
        validCodes.add("0589-1478");
        validCodes.add("3696-9998");
        validCodes.add("1588-4115");

        if(validCodes.contains(code)) {
            return true;
        } else {
            return false;
        }
    }

    @Override
    public void onPause() {
        super.onPause();
        inCode = (EditText) findViewById(R.id.inCode);
        inCode.setText("");
        finish();
    }
}