package com.randomuser.di

import com.randomuser.data.api.RandomUserApi
import com.randomuser.data.api.RandomUserApiProvider
import com.randomuser.data.database.ObjectBox
import com.randomuser.data.database.UserEntity
import com.randomuser.data.repository.RandomUserRepository
import com.randomuser.data.repository.RandomUserRepositoryImpl
import com.randomuser.ui.RandomUserGenViewModel
import com.randomuser.ui.domain.GetDetailedUserUseCase
import com.randomuser.ui.domain.GetDetailedUserUseCaseImpl
import com.randomuser.ui.domain.GetRandomUserPageUseCase
import com.randomuser.ui.domain.GetRandomUserPageUseCaseImpl
import kotlinx.coroutines.Dispatchers
import org.koin.androidx.viewmodel.dsl.viewModel
import org.koin.dsl.module

val randomUserModule = module {

    factory<RandomUserApi> { RandomUserApiProvider().getApi() }

    factory<RandomUserRepository> { RandomUserRepositoryImpl(get(), ObjectBox.store.boxFor(UserEntity::class.java), Dispatchers.Default) }
    factory<GetRandomUserPageUseCase> { GetRandomUserPageUseCaseImpl(get()) }
    factory<GetDetailedUserUseCase> { GetDetailedUserUseCaseImpl(get()) }

    viewModel { RandomUserGenViewModel(get(), get()) }

}
