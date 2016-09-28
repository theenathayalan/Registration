package com.zoho.task.signup;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.zoho.task.R;
import com.zoho.task.main.MainActivity;
import com.zoho.task.utility.DialogUtils;


public class SignUpFragment extends Fragment implements SignUpView, View.OnClickListener {

    private View view;
    private Button butLogin, butCreateAccount;
    private EditText etName, etEmail, etPassword, etContactNumber;
    private DialogUtils dialogUtils;
    private SignUpPresenter signUpPresenter;

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        if (view != null) {
            ViewGroup parent = (ViewGroup) view.getParent();
            if (parent != null) {
                parent.removeView(view);
            }
        } else {
            view = inflater.inflate(R.layout.fragment_signup, container,
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
        butLogin = (Button) view.findViewById(R.id.butLogin);
        etName = (EditText) view.findViewById(R.id.etName);
        etEmail = (EditText) view.findViewById(R.id.etEmail);
        etPassword = (EditText) view.findViewById(R.id.etPassword);
        etContactNumber = (EditText) view.findViewById(R.id.etContactNumber);
        butCreateAccount = (Button) view.findViewById(R.id.butCreateAccount);
        dialogUtils = new DialogUtils(MainActivity.defaultInstance());
        signUpPresenter = new SignUpPresenterImpl(this);
        butLogin.setOnClickListener(this);
        butCreateAccount.setOnClickListener(this);
    }

    /**
     * setNavigationBar method is used to set the navigation bar title.
     */
    private void setNavigationBar() {
        MainActivity.defaultInstance().getTvTitle().setText(getString(R.string.sign_up));
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {

            case R.id.butLogin:
                MainActivity.defaultInstance().changeFragment(MainActivity.Fragments.LOGIN, null);
                break;
            case R.id.butCreateAccount:
                checkCredential();
                break;
            default:
                break;
        }
    }

    @Override
    public void setEmptyFieldError() {
        dialogUtils.createAlert(
                getResources().getString(R.string.invalid_error_popup_header),
                getResources().getString(
                        R.string.required_fields_message));
    }

    @Override
    public void setEmailError() {
        dialogUtils.createAlert(
                getResources().getString(R.string.invalid_error_popup_header),
                getResources().getString(
                        R.string.invalid_email));
    }

    @Override
    public void setPasswordError() {
        dialogUtils.createAlert(
                getResources().getString(R.string.invalid_error_popup_header),
                getResources().getString(
                        R.string.password_error));
    }

    @Override
    public void setEmailAlreadyRegisterError() {
        dialogUtils.createAlert(
                getResources().getString(R.string.invalid_error_popup_header),
                getResources().getString(
                        R.string.email_already_exist));
    }

    @Override
    public void setPhoneNumberError() {
        dialogUtils.createAlert(
                getResources().getString(R.string.invalid_error_popup_header),
                getResources().getString(
                        R.string.invalid_contact_number));
    }

    @Override
    public void navigateToLogin() {
        Toast.makeText(MainActivity.defaultInstance(), "Account created successfully", Toast.LENGTH_LONG).show();
        MainActivity.defaultInstance().changeFragment(MainActivity.Fragments.LOGIN, null);
    }

    /**
     * checkCredential method is used to check credentials.
     */
    private void checkCredential() {
        String name = etName.getText().toString().trim();
        String mail = etEmail.getText().toString().trim();
        String password = etPassword.getText().toString();
        String contactNumber = etContactNumber.getText().toString().trim();
        signUpPresenter.validateCredentials(MainActivity.defaultInstance(),name, mail, password, contactNumber);
    }
}
