package com.zoho.task.login;

import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;

import com.zoho.task.R;
import com.zoho.task.main.MainActivity;
import com.zoho.task.utility.DialogUtils;
import com.zoho.task.welcome.LoginWelcomeActivity;


public class LoginFragment extends Fragment implements LoginView, View.OnClickListener {
    private View view;
    private Button butSignUp, butSignIn;
    private EditText etEmail, etPassword;
    private LoginPresenter presenter;
    private DialogUtils dialogUtils;

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        if (view != null) {
            ViewGroup parent = (ViewGroup) view.getParent();
            if (parent != null) {
                parent.removeView(view);
            }
        } else {
            view = inflater.inflate(R.layout.fragment_login, container,
                    false);
        }
        initializeObject();
        setNavigationBar();
        return view;
    }

    /**
     * initializeObject method is used to create all objects.
     */
    private void initializeObject() {
        butSignUp = (Button) view.findViewById(R.id.butSignUp);
        etEmail = (EditText) view.findViewById(R.id.etEmail);
        etPassword = (EditText) view.findViewById(R.id.etPassword);
        butSignIn = (Button) view.findViewById(R.id.butSignin);
        presenter = new LoginPresenterImpl(this);
        dialogUtils = new DialogUtils(MainActivity.defaultInstance());
        butSignUp.setOnClickListener(this);
        butSignIn.setOnClickListener(this);
    }

    /**
     * setNavigationBar method is used to set the navigation bar title.
     */
    private void setNavigationBar() {
        MainActivity.defaultInstance().getTvTitle().setText(getString(R.string.login));
    }

    /**
     * checkCredential method is used to check credentials.
     */
    private void checkCredential() {
        String mail = etEmail.getText().toString().trim();
        String password = etPassword.getText().toString();
        presenter.validateCredentials(MainActivity.defaultInstance(), mail, password);
    }

    @Override
    public void setEmailError() {
        dialogUtils.createAlert(
                getResources().getString(R.string.invalid_error_popup_header),
                getResources().getString(
                        R.string.invalid_email));

    }

    @Override
    public void setEmailNotRegisterError() {
        dialogUtils.createAlert(
                getResources().getString(R.string.invalid_error_popup_header),
                getResources().getString(
                        R.string.email_not_registered));
    }

    @Override
    public void setEmptyFieldError() {
        dialogUtils.createAlert(
                getResources().getString(R.string.invalid_error_popup_header),
                getResources().getString(
                        R.string.required_fields_message));
    }

    @Override
    public void setPasswordError() {
        dialogUtils.createAlert(
                getResources().getString(R.string.invalid_error_popup_header),
                getResources().getString(
                        R.string.wrong_password));
    }

    @Override
    public void navigateToWelcome(String mail) {
        Intent intent = new Intent(MainActivity.defaultInstance(), LoginWelcomeActivity.class);
        intent.putExtra("email", mail);
        startActivity(intent);
        MainActivity.defaultInstance().finish();
    }


    @Override
    public void onClick(View v) {
        switch (v.getId()) {

            case R.id.butSignUp:
                MainActivity.defaultInstance().changeFragment(MainActivity.Fragments.SIGN_UP, null);
                break;
            case R.id.butSignin:
                checkCredential();
                break;
            default:
                break;
        }
    }

}
