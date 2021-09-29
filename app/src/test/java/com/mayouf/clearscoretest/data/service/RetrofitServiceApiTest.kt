package com.mayouf.clearscoretest.data.service

import com.google.gson.Gson
import com.google.gson.GsonBuilder
import com.mayouf.clearscoretest.data.source.RetrofitServiceApi
import com.mayouf.clearscoretest.domain.model.CreditReport
import com.mayouf.clearscoretest.domain.utils.Constants
import com.mayouf.clearscoretest.domain.utils.createOkHttpBuilderExtension
import com.nhaarman.mockitokotlin2.mock
import io.reactivex.observers.TestObserver
import okhttp3.OkHttpClient
import okhttp3.mockwebserver.MockResponse
import okhttp3.mockwebserver.MockWebServer
import org.junit.After
import org.junit.Before
import org.junit.Test
import retrofit2.Retrofit
import retrofit2.adapter.rxjava2.RxJava2CallAdapterFactory
import retrofit2.converter.gson.GsonConverterFactory

class RetrofitServiceApiTest {

    private lateinit var api: RetrofitServiceApi
    private var mockWebServer: MockWebServer = MockWebServer()
    private val builder = OkHttpClient.Builder()
    private lateinit var gson: Gson
    private lateinit var body: String

    @Before
    fun setUp() {
        api = mock()
        mockWebServer.start()
        gson = GsonBuilder()
            .setLenient()
            .create()
        body = "{\n" +
                "  \"accountIDVStatus\": \"PASS\",\n" +
                "  \"creditReportInfo\": {\n" +
                "    \"score\": 514,\n" +
                "    \"scoreBand\": 4,\n" +
                "    \"clientRef\": \"CS-SED-655426-708782\",\n" +
                "    \"status\": \"MATCH\",\n" +
                "    \"maxScoreValue\": 700,\n" +
                "    \"minScoreValue\": 0,\n" +
                "    \"monthsSinceLastDefaulted\": -1,\n" +
                "    \"hasEverDefaulted\": false,\n" +
                "    \"monthsSinceLastDelinquent\": 1,\n" +
                "    \"hasEverBeenDelinquent\": true,\n" +
                "    \"percentageCreditUsed\": 44,\n" +
                "    \"percentageCreditUsedDirectionFlag\": 1,\n" +
                "    \"changedScore\": 0,\n" +
                "    \"currentShortTermDebt\": 13758,\n" +
                "    \"currentShortTermNonPromotionalDebt\": 13758,\n" +
                "    \"currentShortTermCreditLimit\": 30600,\n" +
                "    \"currentShortTermCreditUtilisation\": 44,\n" +
                "    \"changeInShortTermDebt\": 549,\n" +
                "    \"currentLongTermDebt\": 24682,\n" +
                "    \"currentLongTermNonPromotionalDebt\": 24682,\n" +
                "    \"currentLongTermCreditLimit\": null,\n" +
                "    \"currentLongTermCreditUtilisation\": null,\n" +
                "    \"changeInLongTermDebt\": -327,\n" +
                "    \"numPositiveScoreFactors\": 9,\n" +
                "    \"numNegativeScoreFactors\": 0,\n" +
                "    \"equifaxScoreBand\": 4,\n" +
                "    \"equifaxScoreBandDescription\": \"Excellent\",\n" +
                "    \"daysUntilNextReport\": 9\n" +
                "  },\n" +
                "  \"dashboardStatus\": \"PASS\",\n" +
                "  \"personaType\": \"INEXPERIENCED\",\n" +
                "  \"coachingSummary\": {\n" +
                "    \"activeTodo\": false,\n" +
                "    \"activeChat\": true,\n" +
                "    \"numberOfTodoItems\": 0,\n" +
                "    \"numberOfCompletedTodoItems\": 0,\n" +
                "    \"selected\": true\n" +
                "  },\n" +
                "  \"augmentedCreditScore\": null\n" +
                "}"

    }

    @Test
    fun `test that get credit reports succeeds with no error`() {

        val okHttpClient: OkHttpClient = builder.createOkHttpBuilderExtension(
            body, 200
        )

        val retrofit = Retrofit.Builder().baseUrl(mockWebServer.url(Constants.BASE_URL))
            .addConverterFactory(GsonConverterFactory.create(gson))
            .addCallAdapterFactory(RxJava2CallAdapterFactory.create())
            .client(okHttpClient)
            .build()
            .create(RetrofitServiceApi::class.java)

        mockWebServer.enqueue(
            MockResponse()
                .setResponseCode(200)
                .setHeader("content-type", "application/json")
        )

        val service: RetrofitServiceApi = retrofit as RetrofitServiceApi

        val call: TestObserver<CreditReport> = service.getCreditReport().test()

        call.awaitTerminalEvent()
        call.assertNoErrors()
        call.assertComplete()
    }

    @Test
    fun `test that get credit reports succeeds with error`() {

        val okHttpClient: OkHttpClient = builder.createOkHttpBuilderExtension(
            body, 500
        )

        val retrofit = Retrofit.Builder().baseUrl(mockWebServer.url(Constants.BASE_URL))
            .addConverterFactory(GsonConverterFactory.create(gson))
            .addCallAdapterFactory(RxJava2CallAdapterFactory.create())
            .client(okHttpClient)
            .build()
            .create(RetrofitServiceApi::class.java)

        mockWebServer.enqueue(
            MockResponse()
                .setResponseCode(500)
                .setHeader("content-type", "application/json")
        )

        val service: RetrofitServiceApi = retrofit as RetrofitServiceApi

        val call: TestObserver<CreditReport> = service.getCreditReport().test()

        call.awaitTerminalEvent()
        call.assertErrorMessage("HTTP 500 OK")
    }

    @After
    fun teardown() {
        mockWebServer.shutdown()
    }
}