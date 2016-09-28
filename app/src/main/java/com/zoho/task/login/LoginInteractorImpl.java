package com.zoho.task.login;

import android.content.Context;
import android.text.TextUtils;

import com.zoho.task.db.DatabaseHandler;

public class LoginInteractorImpl implements LoginInteractor {

    @Override
    public void login(Context context, final String mail, final String password, final OnLoginFinishedListener listener) {

        if (TextUtils.isEmpty(mail) || TextUtils.isEmpty(password)) {
            listener.onEmptyFieldError();
        } else if (!android.util.Patterns.EMAIL_ADDRESS.matcher(mail).matches()) {
            listener.onEmailError();
        } else {
            DatabaseHandler dbHandler = new DatabaseHandler(context);
            if (dbHandler.isEmailAlreadyExist(mail)) {
                if (dbHandler.isValidEmailAndPassword(mail, password)) {
                    listener.onSuccess(mail);
                } else {
                    listener.onPasswordError();
                }
            } else {
                listener.onEmailNotRegisterError();
            }
            dbHandler.close();
        }

    }
}
