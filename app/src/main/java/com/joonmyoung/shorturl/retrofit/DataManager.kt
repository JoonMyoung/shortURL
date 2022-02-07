package com.joonmyoung.shorturl.retrofit

import android.content.Context
import android.util.Log
import android.widget.Toast
import okhttp3.OkHttpClient
import okhttp3.logging.HttpLoggingInterceptor

import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import kotlin.coroutines.coroutineContext

class DataManager {

    private var naverService : Service ?= null
    private val client: OkHttpClient

    init {

        val interceptor = HttpLoggingInterceptor()
        interceptor.level = HttpLoggingInterceptor.Level.BODY

        client = OkHttpClient.Builder()
            .addInterceptor(interceptor)
            .build()

        createNaverApi()
    }

    private fun createNaverApi() {
        naverService = Retrofit.Builder()
            .baseUrl(Service.BASEURL)
            .client(client)
            .addConverterFactory(GsonConverterFactory.create())
            .build()
            .create(Service::class.java) //자바코드의 class 함수 사용 방법
    }

    /**
     * 단축URL 생성 요청
     */
    fun loadShorturl(url:String,
                     context : Context,
                     success: (ShortUrlData) -> Unit,
                     error: (Call<ShortUrlResult>, Throwable) -> Unit) {

        //retrofit request
        val call = naverService!!.getShortUrl(url)


        call.enqueue(object:Callback<ShortUrlResult> {

            override fun onResponse(call: Call<ShortUrlResult>?,
                                    response: Response<ShortUrlResult>?) {
                //let 으로 null 체크
                val code = response?.body()?.code
                Log.d("TAG",code.toString())
                if (code ==null){
                    Toast.makeText(context, "형식에 맞는 URL을 넣어주세요", Toast.LENGTH_SHORT).show()
                }

                val data = response?.body()?.result
                data?.let { success(data) }

            }

            override fun onFailure(call: Call<ShortUrlResult>?, t: Throwable?) {
                call?.let {
                    if (t != null) {
                        error(it, t)
                    }
                }
            }
        })
    }
}