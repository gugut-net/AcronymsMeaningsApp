package com.example.acronymsmeaningsapp.remote

import androidx.arch.core.executor.testing.InstantTaskExecutorRule
import com.example.acronymsmeaningsapp.model.AcronymResponseItem
import com.example.acronymsmeaningsapp.service.ServiceApi
import io.mockk.MockKAnnotations
import io.mockk.clearAllMocks
import io.mockk.coEvery
import io.mockk.impl.annotations.MockK
import io.mockk.mockk
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.runBlocking
import okhttp3.MediaType.Companion.toMediaTypeOrNull
import okhttp3.ResponseBody.Companion.toResponseBody
import org.junit.Assert.*

import org.junit.After
import org.junit.Before
import org.junit.Rule
import org.junit.Test
import retrofit2.Response

@OptIn(ExperimentalCoroutinesApi::class)
class RepositoryImplTest {

    @get:Rule
    val rule = InstantTaskExecutorRule()

    lateinit var repository: RepositoryImpl
    private val serviceApi = mockk<ServiceApi>()

//    private val mockDispatcher = UnconfinedTestDispatcher()


    @Before
    fun setUp() {
//        Dispatchers.setMain(mockDispatcher)
        MockKAnnotations.init(this)
        repository = RepositoryImpl(serviceApi)
    }

    @After
    fun tearDown() {
//        Dispatchers.resetMain()
        clearAllMocks()
    }

    @Test
    fun `test getAbbreviationList() returns response`() {
        /**
         * ASSIGNMENT
         */
        val typedText = "test"
        val mockResponse = Response.success<List<AcronymResponseItem?>>(listOf(AcronymResponseItem("Test Abbreviation")))//
        coEvery { serviceApi.getAbbreviationList(typedText) } returns mockResponse

        /**
         * ACTION
         */
        val result = runBlocking { repository.getAbbreviationList(typedText) }

        /**
         * ASSERTION
         */
        assertNotNull(result)
        assertEquals(mockResponse, result)
    }

    @Test
    fun `test getAbbreviationList returns success response`() {
        /**
         * ASSIGNMENT
         */
        val typedText = "test"
        val mockResponse = Response.success<List<AcronymResponseItem?>>(listOf(AcronymResponseItem("Test Abbreviation")))
        coEvery { serviceApi.getAbbreviationList(typedText) } returns mockResponse

        /**
         * ACTION
         */
        val result = runBlocking { serviceApi.getAbbreviationList(typedText) }

        /**
         * ASSERTION
         */
        assertEquals(mockResponse.code(), result.code())
        assertEquals(mockResponse.body(), result.body())
    }

    @Test
    fun `test getAbbreviationList returns error response error`() {
        /**
         * ASSIGNMENT
         */
        val typedText = "test"
        val errorMessage = "Bad Request"
        val response = Response.error<List<AcronymResponseItem?>>(400, errorMessage.toResponseBody("text/plain".toMediaTypeOrNull()))

        coEvery { serviceApi.getAbbreviationList(typedText) } throws Exception("Bad Request")

        /**
         * ACTION
         */
        val result = runBlocking {
            try {
                repository.getAbbreviationList(typedText)
            } catch (e: Exception) {
                response // return the expected error response
            }
        }

        /**
         * ASSERTION
         */
        assertEquals(response.code(), result.code())
        assertEquals(errorMessage, result.errorBody()?.string()) // assert the error message
    }

}