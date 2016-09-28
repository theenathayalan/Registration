package com.zoho.task.signup;

import android.content.Context;
import android.text.TextUtils;

import com.zoho.task.db.DatabaseHandler;

public class SignUpInteractorImpl implements SignUpInteractor {

    @Override
    public void login(Context context, final String name, final String mail, final String password, final String contactNumber, final OnSignUpFinishedListener listener) {

        if (TextUtils.isEmpty(name) || TextUtils.isEmpty(mail) ||
                TextUtils.isEmpty(password) || TextUtils.isEmpty(contactNumber)) {
            listener.onEmptyFieldError();
        } else if (!android.util.Patterns.EMAIL_ADDRESS.matcher(mail).matches()) {
            listener.onEmailError();
        } else if (password.length() < 6 || !isLegalPassword(password)) {
            listener.onPasswordError();
        } else if (contactNumber.length() < 10) {
            listener.onPhoneNumberError();
        } else {
            DatabaseHandler dbHandler = new DatabaseHandler(context);
            if (!dbHandler.isEmailAlreadyExist(mail)) {
                listener.onNavigateToLogin();
            } else {
                listener.onEmailAlreadyRegisterError();
            }
            dbHandler.close();
        }

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
