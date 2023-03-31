package com.example.acronymsmeaningsapp.viewModels

import android.content.Context
import android.net.ConnectivityManager
import androidx.arch.core.executor.testing.InstantTaskExecutorRule
import com.example.acronymsmeaningsapp.model.AcronymResponseItem
import com.example.acronymsmeaningsapp.remote.Repository
import com.example.acronymsmeaningsapp.service.ServiceApi
import io.mockk.*
import junit.framework.TestCase.*
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.runBlocking
import org.junit.Before
import org.junit.Rule
import org.junit.Test
import retrofit2.Response

import kotlinx.coroutines.test.*
import org.junit.After

@OptIn(ExperimentalCoroutinesApi::class)
class HomeViewModelTest {

    @get:Rule
    val rule = InstantTaskExecutorRule()

    private val repository = mockk<Repository>(relaxed = true)
    private val serviceApi = mockk<ServiceApi>()
    private val mockContext = mockk<Context>(relaxed = true)
    private val connectivityManager = mockk<ConnectivityManager>()


    private lateinit var viewModel: HomeViewModel

    @Before
    fun setUp() {
        Dispatchers.setMain(Dispatchers.Unconfined)
        MockKAnnotations.init(this)
        viewModel = HomeViewModel(repository, connectivityManager)
//        context = ApplicationProvider.getApplicationContext()
    }

    @After
    fun tearDown() {
        clearAllMocks()
        Dispatchers.resetMain()
    }

    @Test
    fun `test getAbbreviationList() returns response`() = runBlocking {
        /**
         * ASSIGNMENT
         */
        val typedText = "test"
        val mockResponse = Response.success<List<AcronymResponseItem?>>(listOf(AcronymResponseItem("Test Abbreviation")))
        coEvery { repository.getAbbreviationList(typedText) } returns mockResponse

        /**
         * ACTION
         */
        val result = repository.getAbbreviationList(typedText)

        /**
         * ASSERTION
         */
        assertNotNull(result)
        assertEquals(mockResponse, result)
    }

}
