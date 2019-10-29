package space.chuumong.lolstats

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import org.koin.android.ext.android.get
import org.koin.android.ext.android.inject
import space.chuumong.lolstats.remote.api.ApiService

class MainActivity : AppCompatActivity() {

    companion object {
        private const val SUMMONER_NAME = "genetory"
    }

    private val apiService: ApiService by lazy { get() as ApiService }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        apiService.getSummonerInfo(SUMMONER_NAME).subscribe({

        }, {
            Log.e("test", it.message, it)
        })
    }
}
