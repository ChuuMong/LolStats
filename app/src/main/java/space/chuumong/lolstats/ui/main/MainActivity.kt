package space.chuumong.lolstats.ui.main

import android.os.Bundle
import android.util.Log
import androidx.lifecycle.Observer
import androidx.recyclerview.widget.LinearLayoutManager
import org.koin.android.viewmodel.ext.android.getViewModel
import space.chuumong.data.Result
import space.chuumong.domain.entities.SummonerInfo
import space.chuumong.lolstats.R
import space.chuumong.lolstats.databinding.ActivityMainBinding
import space.chuumong.lolstats.ui.BaseActivity
import space.chuumong.lolstats.ui.adapter.SummonerLeagueAdapter
import space.chuumong.lolstats.ui.utils.SummonerLeagueItemSpaceDecoration
import space.chuumong.lolstats.ui.utils.setLightStatusBar
import space.chuumong.lolstats.ui.utils.showNoTitleTwoButtonsDialog
import space.chuumong.lolstats.viewmodel.SummonerViewModel

class MainActivity : BaseActivity<ActivityMainBinding>() {

    companion object {
        private const val TAG = "MainActivity"
        private const val SUMMONER_NAME = "genetory"
    }

    private val summonerViewModel: SummonerViewModel by lazy { getViewModel() as SummonerViewModel }

    private val summonerLeagueAdapter by lazy { SummonerLeagueAdapter() }

    override fun getLayoutId() = R.layout.activity_main

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        setLightStatusBar()

        binding.summonerViewModel = summonerViewModel

        binding.rvLeague.layoutManager =
            LinearLayoutManager(this, LinearLayoutManager.HORIZONTAL, false)
        binding.rvLeague.addItemDecoration(SummonerLeagueItemSpaceDecoration())
        binding.rvLeague.adapter = summonerLeagueAdapter

        summonerViewModel.onClickSummonerRefresh.observe(this, Observer {
            getSummonerInfo(SUMMONER_NAME)
        })

        getSummonerInfo(SUMMONER_NAME)
    }

    @Suppress("SameParameterValue")
    private fun getSummonerInfo(name: String) {
        summonerViewModel.getSummonerInfo(name, object : Result<SummonerInfo> {
            override fun onSuccess(result: SummonerInfo) {
                summonerLeagueAdapter.addAll(result.leagues)
            }

            override fun onFail(t: Throwable) {
                Log.e(TAG, t.message, t)

                showNoTitleTwoButtonsDialog(
                    getString(R.string.network_error_retry),
                    getString(R.string.retry),
                    { getSummonerInfo(SUMMONER_NAME) },
                    getString(android.R.string.cancel),
                    { finish() }
                )
            }
        })
    }
}
