package space.chuumong.lolstats.ui.main

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import org.koin.android.viewmodel.ext.android.getViewModel
import space.chuumong.data.Result
import space.chuumong.domain.usecases.UseCase
import space.chuumong.lolstats.R
import space.chuumong.lolstats.databinding.ActivityMainBinding
import space.chuumong.lolstats.ui.BaseActivity
import space.chuumong.lolstats.viewmodel.SummonerViewModel

class MainActivity : BaseActivity<ActivityMainBinding>() {

    companion object {
        private const val TAG = "MainActivity"
        private const val SUMMONER_NAME = "genetory"
    }

    private val summonerViewModel: SummonerViewModel by lazy { getViewModel() as SummonerViewModel }

    override fun getLayoutId() = R.layout.activity_main

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        getSummonerInfo(SUMMONER_NAME)
    }

    @Suppress("SameParameterValue")
    private fun getSummonerInfo(name: String) {
        summonerViewModel.getSummonerInfo(name, object : Result<UseCase.None> {
            override fun onSuccess(result: UseCase.None) {

            }

            override fun onFail(t: Throwable) {
                Log.e(TAG, t.message, t)
            }
        })
    }
}
