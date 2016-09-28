/*
 *
 *  * Copyright (C) 2014 Antonio Leiva Gordillo.
 *  *
 *  * Licensed under the Apache License, Version 2.0 (the "License");
 *  * you may not use this file except in compliance with the License.
 *  * You may obtain a copy of the License at
 *  *
 *  *      http://www.apache.org/licenses/LICENSE-2.0
 *  *
 *  * Unless required by applicable law or agreed to in writing, software
 *  * distributed under the License is distributed on an "AS IS" BASIS,
 *  * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 *  * See the License for the specific language governing permissions and
 *  * limitations under the License.
 *
 */

package com.zoho.task.signup;

import android.content.Context;

public class SignUpPresenterImpl implements SignUpPresenter, SignUpInteractor.OnSignUpFinishedListener {

    private SignUpView signUpView;
    private SignUpInteractor signUpInteractor;

    public SignUpPresenterImpl(SignUpView signUpView) {
        this.signUpView = signUpView;
        this.signUpInteractor = new SignUpInteractorImpl();
    }

    @Override
    public void validateCredentials(Context context, String name, String mail, String password, String contactNumber) {
        signUpInteractor.login(context, name, mail, password, contactNumber, this);
    }

    @Override
    public void onDestroy() {
        signUpView = null;
    }

    @Override
    public void onEmailError() {
        if (signUpView != null) {
            signUpView.setEmailError();
        }
    }

    @Override
    public void onEmptyFieldError() {
        if (signUpView != null) {
            signUpView.setEmptyFieldError();
        }
    }

    @Override
    public void onPasswordError() {
        if (signUpView != null) {
            signUpView.setPasswordError();
        }
    }

    @Override
    public void onEmailAlreadyRegisterError() {
        if (signUpView != null) {
            signUpView.setEmailAlreadyRegisterError();
        }
    }

    @Override
    public void onPhoneNumberError() {
        if (signUpView != null) {
            signUpView.setPhoneNumberError();
        }
    }

    @Override
    public void onNavigateToLogin() {
        if (signUpView != null) {
            signUpView.navigateToLogin();
        }
    }
}
