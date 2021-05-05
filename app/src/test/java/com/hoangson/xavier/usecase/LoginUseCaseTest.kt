package com.hoangson.xavier.usecase

import androidx.arch.core.executor.testing.InstantTaskExecutorRule
import com.hoangson.xavier.core.models.ErrorType
import com.hoangson.xavier.core.models.Output
import com.hoangson.xavier.domain.model.LoginEntity
import kotlinx.coroutines.runBlocking
import org.junit.Before
import org.junit.Rule
import org.junit.Test
import org.mockito.Mock
import org.mockito.Mockito
import org.mockito.MockitoAnnotations


class LoginUseCaseTest {


    companion object {

        private val USERNAME = "hani"
        private val PASSWORD = "password"

    }

    @Rule
    @JvmField
    val rule = InstantTaskExecutorRule()

    @Mock
    lateinit var loginRepository: LoginRepository

    lateinit var loginUseCase: LoginUseCase

    @Before
    fun setup() {

        MockitoAnnotations.initMocks(this)

        loginUseCase = LoginUseCase(loginRepository)

    }


    @Test
    fun loginUseCase_wrongCredentianls_invalid() {

        Mockito.`when`(loginRepository.login(USERNAME, PASSWORD))
            .thenReturn(Output.Error(ErrorType.DATA,"Invalid"))

        val output = runBlocking { loginUseCase.run(LoginUseCase.Params(USERNAME, PASSWORD)) }

        assert(output is Output.Error)

    }


    @Test
    fun loginUseCase_correctCredentials_login() {

        Mockito.`when`(loginRepository.login(USERNAME, PASSWORD))
            .thenReturn(Output.Success(LoginEntity.loggedIn()))

        runBlocking { loginUseCase.run(LoginUseCase.Params(USERNAME, PASSWORD)) }

        Mockito.verify(loginRepository).login(USERNAME, PASSWORD)

        Mockito.verifyNoMoreInteractions(loginRepository)

    }
}