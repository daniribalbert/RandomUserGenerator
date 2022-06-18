package com.randomuser.data.repository

import com.randomuser.data.api.RandomUserApi
import com.randomuser.data.database.UserEntity
import com.randomuser.data.database.UserEntity_
import com.randomuser.data.model.BaseApiResponse
import com.randomuser.data.model.RandomUserApiModel
import io.objectbox.Box
import io.objectbox.query.QueryBuilder
import kotlinx.coroutines.CoroutineDispatcher
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow
import kotlinx.coroutines.flow.flowOn

class RandomUserRepositoryImpl(
        private val api: RandomUserApi,
        private val box: Box<UserEntity>,
        private val dispatcher: CoroutineDispatcher
) :
        RandomUserRepository {

    override suspend fun loadRandomUsersPage(page: Int): Flow<BaseApiResponse<RandomUserApiModel>> = flow {
        val randomUsersPage = api.getRandomUsersPage(page)
        emit(randomUsersPage)
        saveToDb(randomUsersPage)
    }.flowOn(dispatcher)

    override suspend fun loadUser(username: String): Flow<UserEntity?> = flow {
        val query = box.query().equal(UserEntity_.username, username, QueryBuilder.StringOrder.CASE_SENSITIVE).build()
        emit(query.findFirst())
    }


    private fun saveToDb(randomUsersPage: BaseApiResponse<RandomUserApiModel>) {
        randomUsersPage.results.map {
            UserEntity(
                    id = null,
                    name = it.name.toString(),
                    email = it.email,
                    gender = it.gender,
                    age = it.dob.age,
                    birthdate = it.dob.date,
                    phone = it.phone,
                    cell = it.cell,
                    city = it.location.city,
                    street = it.location.street.toString(),
                    state = it.location.state,
                    country = it.location.country,
                    largePicture = it.picture.large,
                    thumbnail = it.picture.thumbnail,
                    uuid = it.login.uuid,
                    username = it.login.username,
                    password = it.login.password,
            )
        }.also {
            if (it.isNotEmpty()) box.put(it)
        }
    }
}
