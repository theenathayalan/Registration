package com.zoho.task.signup;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.text.TextUtils;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;

import com.zoho.task.main.MainActivity;
import com.zoho.task.R;
import com.zoho.task.db.DatabaseHandler;
import com.zoho.task.model.UserInformation;
import com.zoho.task.utility.DialogUtils;


public class SignUpFragment extends Fragment implements View.OnClickListener {

    private View view;
    private Button butLogin, butCreateAccount;
    private EditText etName, etEmail, etPassword, etContactNumber;

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

    /**
     * checkCredential method is used to check credentials.
     */
    private void checkCredential() {
        String name = etName.getText().toString();
        String mail = etEmail.getText().toString();
        String password = etPassword.getText().toString();
        String contactNumber = etContactNumber.getText().toString();
        if (isValid(name, mail, password, contactNumber)) {
            DatabaseHandler dbHandler = new DatabaseHandler(MainActivity.defaultInstance());
            if (!dbHandler.isEmailAlreadyExist(mail)) {
                dbHandler.addUserInformation(new UserInformation(name,
                        mail, password, contactNumber));
            } else {
                DialogUtils dialogUtils = new DialogUtils(MainActivity.defaultInstance());
                dialogUtils.createAlert(
                        getResources().getString(R.string.invalid_error_popup_header),
                        getResources().getString(
                                R.string.email_already_exist));
            }
            dbHandler.close();
        }
    }

    /**
     * isValid method is used to check the name,mail,password and contactNumber is valid.
     *
     * @param name
     * @param mail
     * @param password
     * @param contactNumber
     * @return boolean
     */
    private boolean isValid(String name, String mail, String password, String contactNumber) {
        DialogUtils dialogUtils = new DialogUtils(MainActivity.defaultInstance());
        if (TextUtils.isEmpty(name) || TextUtils.isEmpty(mail) ||
                TextUtils.isEmpty(password) || TextUtils.isEmpty(contactNumber)) {
            dialogUtils.createAlert(
                    getResources().getString(R.string.invalid_error_popup_header),
                    getResources().getString(
                            R.string.required_fields_message));
        } else if (!android.util.Patterns.EMAIL_ADDRESS.matcher(etEmail.getText().toString()).matches()) {
            dialogUtils.createAlert(
                    getResources().getString(R.string.invalid_error_popup_header),
                    getResources().getString(
                            R.string.invalid_email));
        } else if (password.length() < 6 || !isLegalPassword(password)) {
            dialogUtils.createAlert(
                    getResources().getString(R.string.invalid_error_popup_header),
                    getResources().getString(
                            R.string.password_error));
        } else if (contactNumber.length() < 10) {
            dialogUtils.createAlert(
                    getResources().getString(R.string.invalid_error_popup_header),
                    getResources().getString(
                            R.string.invalid_contact_number));
        } else {
            return true;
        }
        return false;
    }

    /**
     * isLegalPassword method is used to check the password with correct data.
     *
     * @param pass
     * @return boolean
     */
    private boolean isLegalPassword(String pass) {

        if (!pass.matches(".*[A-Z].*")) return false;

        if (!pass.matches(".*[a-z].*")) return false;

        if (!pass.matches(".*\\d.*")) return false;

        if (pass.matches("[a-zA-Z0-9.? ]*")) return false;

        return true;
    }
}
