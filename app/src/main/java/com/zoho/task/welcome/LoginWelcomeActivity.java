package com.zoho.task.welcome;

import android.app.Activity;
import android.os.Bundle;
import android.widget.TextView;

import com.zoho.task.R;
import com.zoho.task.db.DatabaseHandler;
import com.zoho.task.main.MainActivity;
import com.zoho.task.model.UserInformation;


public class LoginWelcomeActivity extends Activity {
    private TextView tvName, tvEmail, tvPhoneNumber;
    private UserInformation userInformation;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login_welcome);
        initializeObject();
        Bundle bundle = this.getIntent().getExtras();
        String email = bundle.getString("email");
        DatabaseHandler dbHandler = new DatabaseHandler(MainActivity.defaultInstance());
        userInformation = dbHandler.getSingleUserInformation(email);
        dbHandler.close();
        setInformation();
    }

    private void initializeObject() {
        tvName = (TextView) findViewById(R.id.tvName);
        tvEmail = (TextView) findViewById(R.id.tvEmail);
        tvPhoneNumber = (TextView) findViewById(R.id.tvPhoneNumber);
    }

    private void setInformation() {
        tvName.setText(userInformation.getName());
        tvEmail.setText(userInformation.getEmail());
        tvPhoneNumber.setText(userInformation.getContactNumber());
    }

}
