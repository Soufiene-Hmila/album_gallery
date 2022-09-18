package com.dmsh.data.api

import androidx.arch.core.executor.testing.InstantTaskExecutorRule
import androidx.test.ext.junit.runners.AndroidJUnit4
import androidx.test.filters.SmallTest
import com.dmsh.data.repositories.AlbumRemoteDataSourceImpl
import com.dmsh.domain.common.Result
import com.google.gson.Gson
import com.google.gson.JsonParser
import kotlinx.coroutines.*
import okhttp3.mockwebserver.MockResponse
import okhttp3.mockwebserver.MockWebServer
import org.junit.*
import org.junit.runner.RunWith
import java.io.FileNotFoundException


@ExperimentalCoroutinesApi
@RunWith(AndroidJUnit4::class)
@SmallTest
class NetworkModuleTest {

    @get:Rule
    val instantExecutorRule = InstantTaskExecutorRule()

    private lateinit var mockedResponse: String
    private lateinit var mockWebServer: MockWebServer
    private lateinit var networkModule: NetworkModule
    private lateinit var albumRepository: AlbumRemoteDataSourceImpl


    @Before
    fun setup() {

        mockWebServer = MockWebServer()
        networkModule = NetworkModule()
        albumRepository = AlbumRemoteDataSourceImpl(
            networkModule.createAlbumApi("${mockWebServer.url("/")}"))

    }

    @Test
    fun getRemoteAlbum() = runBlocking {

        mockedResponse = getStringFromFile("SuccessResponse.json", true)

        mockWebServer.enqueue(MockResponse().setResponseCode(200).setBody(mockedResponse))

            when(val response = albumRepository.getRemoteAlbum()){
                is Result.Error -> {}
                is Result.Success -> {

                    val json = Gson().toJson(response.data)

                    val resultResponse = JsonParser.parseString(json)
                    val expectedResponse = JsonParser.parseString(mockedResponse)

                    Assert.assertNotNull(response.data)
                    Assert.assertTrue(resultResponse.equals(expectedResponse))
            }
        }

    }


    @After
    fun tearDown() {
        mockWebServer.shutdown()
    }

    @OptIn(ExperimentalCoroutinesApi::class)
    fun getStringFromFile(filePath: String, debug: Boolean = false): String {
        val classLoader = NetworkModuleTest::class.java.classLoader
        if (classLoader != null) {
            try {
                val inPutString = classLoader.getResourceAsStream(filePath)!!.bufferedReader().use { it.readText() }
                if (debug) println("Output from inPutFile is: $inPutString")
                return inPutString
            } catch (e: FileNotFoundException) {
                println("Could not find the specified file: $filePath")
                throw e
            }
        } else {
            throw IllegalStateException(
                """Classloader is null. Can't open an inPutStream for the specified file: $filePath without it."""
            )
        }
    }

}

