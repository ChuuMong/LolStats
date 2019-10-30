package space.chuumong.data.remote.utils

import android.util.Log
import okhttp3.Interceptor
import okhttp3.Response
import space.chuumong.data.remote.const.FAIL_RESPONSE
import space.chuumong.data.remote.exception.ServerErrorException


class NetworkErrorInterceptor : Interceptor {

    companion object {

        private const val TAG = "NetworkErrorInterceptor"

        /**
         * 재시도 횟수
         */
        private const val RETRY_COUNT = 3

        /**
         * 재시도 딜레이 타임
         */
        private const val RETRY_DELAY_TIME = 3 * 1000L
    }

    @Throws(Exception::class)
    override fun intercept(chain: Interceptor.Chain): Response {
        val request = chain.request()

        var response: Response?

        response = try {
            chain.proceed(request)
        } catch (e: Exception) {
            Log.e(TAG, e.message, e)
            null
        }

        var tryCount = 0
        while ((response == null || !response.isSuccessful) && tryCount < RETRY_COUNT) {
            try {
                Thread.sleep(RETRY_DELAY_TIME)
            } catch (e: InterruptedException) {
                e.printStackTrace()
            }

            response?.close()
            response = try {
                chain.proceed(request)
            } catch (e: Exception) {
                Log.e(TAG, e.message, e)
                null
            }

            tryCount++

            Log.e(
                TAG,
                "Server response is not successful, try count : $tryCount, url : ${request.url}"
            )
        }

        if (response == null || !response.isSuccessful) {
            throw ServerErrorException(response?.code ?: FAIL_RESPONSE)
        }

        return response
    }
}