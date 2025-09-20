package com.example.smartlatch.presenter

import com.example.smartlatch.model.WelcomePageModel
import com.example.smartlatch.view.WelcomePageView

class WelcomePagePresenter(
    private val view: WelcomePageView,
    private val model: WelcomePageModel
) {
    fun onGetStartedClicked() {
        if (model.isFirstTimeUser()) {
            view.navigateToLogin()
        } else {
            view.navigateToLogin() // you can extend logic later
        }
    }
}
