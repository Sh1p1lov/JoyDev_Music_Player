package com.sh1p1lov.joydevmusicplayer.presentation.viewmodels

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.sh1p1lov.joydevmusicplayer.domain.models.LoginResult
import com.sh1p1lov.joydevmusicplayer.domain.models.LoginUserParams
import com.sh1p1lov.joydevmusicplayer.domain.usecase.LoginByUsernameOrEmailUseCase

class LoginViewModel(private val loginByUsernameOrEmailUseCase: LoginByUsernameOrEmailUseCase) : ViewModel() {

    private var mutableLoginResult = MutableLiveData<LoginResult>()
    val loginResult: LiveData<LoginResult> = mutableLoginResult

    fun login(usernameOrEmail: String, password: String) {
        val isEmail = android.util.Patterns.EMAIL_ADDRESS.matcher(usernameOrEmail).matches()
        val loginUserParams = LoginUserParams(usernameOrEmail, password, isEmail)
        mutableLoginResult.value = loginByUsernameOrEmailUseCase.execute(loginUserParams)
    }
}