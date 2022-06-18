package com.randomuser

import androidx.arch.core.executor.testing.InstantTaskExecutorRule
import com.randomuser.data.api.RandomUserApi
import com.randomuser.data.database.MyObjectBox
import com.randomuser.data.database.UserEntity
import com.randomuser.data.model.*
import com.randomuser.data.repository.RandomUserRepository
import com.randomuser.data.repository.RandomUserRepositoryImpl
import com.randomuser.ui.domain.GetRandomUserPageUseCase
import com.randomuser.ui.domain.GetRandomUserPageUseCaseImpl
import io.mockk.coEvery
import io.mockk.mockk
import io.objectbox.BoxStore
import io.objectbox.DebugFlags
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.flow
import kotlinx.coroutines.runBlocking
import org.junit.After
import org.junit.Assert.*
import org.junit.Before
import org.junit.Rule
import org.junit.Test
import org.junit.runner.RunWith
import org.mockito.junit.MockitoJUnitRunner
import java.io.File

/**
 * Example local unit test, which will execute on the development machine (host).
 *
 * See [testing documentation](http://d.android.com/tools/testing).
 */

@RunWith(MockitoJUnitRunner::class)
class RandomUserUnitTest {

    private val TEST_DIRECTORY: File = File("objectbox/test-db")

    @get:Rule
    var instantExecutorRule = InstantTaskExecutorRule()

    private val dispatcher = Dispatchers.Unconfined

    lateinit var repo: RandomUserRepository
    lateinit var mockRepo: RandomUserRepository
    lateinit var mockApi: RandomUserApi

    private lateinit var getRandomUserUseCase: GetRandomUserPageUseCase
    private lateinit var mockApiResponse: BaseApiResponse<RandomUserApiModel>

    private var _store: BoxStore? = null
    protected val store: BoxStore
        get() = _store!!

    @Before
    fun setup() {
        // Delete any files in the test directory before each test to start with a clean database.
        BoxStore.deleteAllFiles(TEST_DIRECTORY)
        _store = MyObjectBox.builder()
                // Use a custom directory to store the database files in.
                .directory(TEST_DIRECTORY)
                // Optional: add debug flags for more detailed ObjectBox log output.
                .debugFlags(DebugFlags.LOG_QUERIES or DebugFlags.LOG_QUERY_PARAMETERS)
                .build()

        mockApi = mockk()
        mockRepo = mockk()
        mockApiResponse =  mockk()

        coEvery { mockRepo.loadRandomUsersPage(any()) } returns flow { mockApiResponse }
        repo = RandomUserRepositoryImpl(mockApi, store.boxFor(UserEntity::class.java), dispatcher)
    }

    @After
    fun tearDown() {
        _store?.close()
        _store = null
        BoxStore.deleteAllFiles(TEST_DIRECTORY)
    }

    @Test
    fun testLoadUserDetail() = runBlocking {
        val sampleApiResponse = BaseApiResponse(generateSampleUserList(10), InfoApiModel("", 10, 1, "1.0"))

        coEvery { mockApi.getRandomUsersPage(any()) } returns sampleApiResponse

        val flow = repo.loadRandomUsersPage(0)
        flow.collect {
            assertEquals(sampleApiResponse, it)
        }
    }

    @Test
    fun testRepositoryLoadUserDetails() = runBlocking {
        val singleUserList = generateSampleUserList(1)
        val user = singleUserList.first()
        val sampleApiResponse = BaseApiResponse(singleUserList, InfoApiModel("", 10, 1, "1.0"))
        coEvery { mockApi.getRandomUsersPage(any()) } returns sampleApiResponse

        // Automatically saves to db
        val loadRandomUsersFlow = repo.loadRandomUsersPage(0)
        loadRandomUsersFlow.collect {
            assertEquals(sampleApiResponse, it)
        }
        // Check user is loaded from db.
        val loadUserDetailsFlow = repo.loadUser(user.login.username)
        loadUserDetailsFlow.collect {
            assertEquals(user.login.username, it?.username)
        }
    }

    @Test
    fun testGetRandomUsersListUseCase() = runBlocking {
        getRandomUserUseCase = GetRandomUserPageUseCaseImpl(mockRepo)

        val flow = getRandomUserUseCase.execute(0)
        flow.collect {
            assertEquals(mockApiResponse, it)
        }
    }

    private fun generateSampleUserList(size: Int): List<RandomUserApiModel> {
       return (1..size).map { RandomUserApiModel(
               gender = "",
               name = UsernameApiModel("", it.toString(), ""),
               phone = "",
               cell = "123",
               email = "email",
               dob = DateOfBirthApiModel("", 10),
               location = UserLocationApiModel(StreetApiModel(1, ""), "", "", ""),
               login = LoginInfoApiModel(it.toString(), it.toString(), ""),
               picture = UserPictureApiModel("", "", "")
       )
       }
    }
}
