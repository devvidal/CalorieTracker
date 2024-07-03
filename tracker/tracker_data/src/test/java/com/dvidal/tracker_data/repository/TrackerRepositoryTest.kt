package com.dvidal.tracker_data.repository

import com.dvidal.tracker_data.remote.OpenFoodApi
import com.dvidal.tracker_data.remote.malformedFoodResponse
import com.dvidal.tracker_data.remote.validFoodResponse
import com.dvidal.tracker_domain.repository.TrackerRepository
import com.google.common.truth.Truth.assertThat
import com.squareup.moshi.Moshi
import io.mockk.mockk
import kotlinx.coroutines.test.runTest
import okhttp3.OkHttpClient
import okhttp3.mockwebserver.MockResponse
import okhttp3.mockwebserver.MockWebServer
import org.junit.After
import org.junit.Before
import org.junit.Test
import retrofit2.Retrofit
import retrofit2.converter.moshi.MoshiConverterFactory
import java.util.concurrent.TimeUnit

class TrackerRepositoryTest {

    private lateinit var repository: TrackerRepository
    private lateinit var mockWebServer: MockWebServer
    private lateinit var okHttpClient: OkHttpClient
    private lateinit var api: OpenFoodApi

    @Before
    fun setUp() {
        mockWebServer = MockWebServer()
        okHttpClient = OkHttpClient.Builder()
            .writeTimeout(1, TimeUnit.SECONDS)
            .readTimeout(1, TimeUnit.SECONDS)
            .connectTimeout(1, TimeUnit.SECONDS)
            .build()
        api = Retrofit.Builder()
            .addConverterFactory(MoshiConverterFactory.create())
            .client(okHttpClient)
            .baseUrl(mockWebServer.url("/"))
            .build()
            .create(OpenFoodApi::class.java)

        repository = TrackerRepositoryImpl(
            dao = mockk(relaxed = true),
            api = api
        )
    }

    @After
    fun tearDown() {
        mockWebServer.shutdown()
    }

    @Test
    fun `search food, valid response, returns results`() = runTest {
        mockWebServer.enqueue(
            MockResponse()
                .setResponseCode(200)
                .setBody(validFoodResponse)
        )

        val result = repository.searchFood("banana", 1, 40)
        assertThat(result.isSuccess).isTrue()
    }

    @Test
    fun `search food, invalid response, returns failure`() = runTest {
        mockWebServer.enqueue(
            MockResponse()
                .setResponseCode(401)
                .setBody(validFoodResponse)
        )

        val result = repository.searchFood("banana", 1, 40)
        assertThat(result.isFailure).isTrue()
    }

    @Test
    fun `search food, malformed response, returns failure`() = runTest {
        mockWebServer.enqueue(
            MockResponse()
                .setBody(malformedFoodResponse)
        )

        val result = repository.searchFood("banana", 1, 40)
        assertThat(result.isFailure).isTrue()
    }
}