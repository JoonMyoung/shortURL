package com.joonmyoung.shorturl.retrofit

import com.google.gson.annotations.SerializedName

data class ShortUrlResult(

    @SerializedName("message") val message: String ="",
    @SerializedName("result") val result:ShortUrlData,
    @SerializedName("code") val code: String = ""

    )

