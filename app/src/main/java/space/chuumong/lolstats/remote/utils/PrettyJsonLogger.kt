package space.chuumong.lolstats.remote.utils

import com.google.gson.GsonBuilder
import com.google.gson.JsonParser
import com.google.gson.JsonSyntaxException
import okhttp3.internal.platform.Platform
import okhttp3.logging.HttpLoggingInterceptor

class PrettyJsonLogger : HttpLoggingInterceptor.Logger {

    companion object {
        private const val JSON_OBJECT_PREFIX = "{"
        private const val JSON_ARRAY_PREFIX = "["
    }

    override fun log(message: String) {
        if (message.startsWith(JSON_OBJECT_PREFIX) || message.startsWith(JSON_ARRAY_PREFIX)) {
            try {
                val prettyPrintJson =
                    GsonBuilder().setPrettyPrinting().create().toJson(JsonParser().parse(message))
                Platform.get().log(Platform.INFO, prettyPrintJson, null)
            } catch (e: JsonSyntaxException) {
                Platform.get().log(Platform.INFO, message, null)
            }
        } else {
            Platform.get().log(Platform.INFO, message, null)
        }
    }
}
