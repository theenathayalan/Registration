package com.zoho.task.main;

import android.os.Bundle;
import android.support.v4.app.FragmentTransaction;
import android.support.v7.app.AppCompatActivity;
import android.widget.TextView;

import com.zoho.task.R;
import com.zoho.task.login.LoginFragment;
import com.zoho.task.signup.SignUpFragment;

public class MainActivity extends AppCompatActivity {

    private TextView tvTitle;
    private static MainActivity rootInstance;
    private LoginFragment loginFragment;
    private SignUpFragment signUpFragment;

    public enum Fragments {
        LOGIN, SIGN_UP
    }

    /**
     * defaultInstance contain the root instance of this activity used in
     * fragments.
     *
     * @return instance of this activity to the called function
     */
    public static MainActivity defaultInstance() {
        return rootInstance;
    }


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        rootInstance = this;
        initializeObject();
        changeFragment(Fragments.LOGIN, null);
    }

    private void initializeObject() {
        tvTitle = (TextView) findViewById(R.id.tv_title);
    }

    /**
     * changeFragment method is used to change the fragment.
     *
     * @param fragment
     * @param bundle
     */
    public void changeFragment(final Fragments fragment, final Bundle bundle) {
        FragmentTransaction ft = getSupportFragmentManager().beginTransaction();
        switch (fragment) {
            case LOGIN:
                loginFragment = new LoginFragment();
                loginFragment.setArguments(bundle);
                ft.replace(R.id.maincontent, loginFragment);
                ft.commitAllowingStateLoss();
                break;
            case SIGN_UP:
                signUpFragment = new SignUpFragment();
                signUpFragment.setArguments(bundle);
                ft.replace(R.id.maincontent, signUpFragment);
                ft.commitAllowingStateLoss();
                break;
            default:
                break;
        }
    }

    public TextView getTvTitle() {
        return tvTitle;
    }
}
