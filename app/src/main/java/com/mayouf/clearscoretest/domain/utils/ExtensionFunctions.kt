package com.mayouf.clearscoretest.domain.utils

import android.widget.ImageView
import androidx.lifecycle.LiveData
import com.squareup.picasso.Picasso
import okhttp3.MediaType.Companion.toMediaTypeOrNull
import okhttp3.OkHttpClient
import okhttp3.Protocol
import okhttp3.ResponseBody.Companion.toResponseBody

fun ImageView.loadImageFull(url: String?) =
    Picasso.get().load(url).into(this)

fun <T> LiveData<T>.testObserver() = TestObserver<T>().also {
    observeForever(it)
}

fun OkHttpClient.Builder.createOkHttpBuilderExtension(body: String, code: Int) =
    this.addNetworkInterceptor { chain ->
        val proceed = chain.proceed(chain.request())

        return@addNetworkInterceptor proceed
            .newBuilder()
            .code(code)
            .body(body = body.toResponseBody("text/plain; charset=utf-8".toMediaTypeOrNull()))
            .build()
    }
        .protocols(listOf(Protocol.HTTP_1_1))
        .build()