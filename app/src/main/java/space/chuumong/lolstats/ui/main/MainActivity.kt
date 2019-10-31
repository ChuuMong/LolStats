package space.chuumong.lolstats.ui.main

import android.os.Bundle
import android.util.Log
import androidx.lifecycle.Observer
import androidx.recyclerview.widget.LinearLayoutManager
import org.koin.android.viewmodel.ext.android.getViewModel
import space.chuumong.data.Result
import space.chuumong.domain.entities.Summoner
import space.chuumong.domain.entities.SummonerGame
import space.chuumong.domain.entities.SummonerProfile
import space.chuumong.lolstats.R
import space.chuumong.lolstats.databinding.ActivityMainBinding
import space.chuumong.lolstats.ui.BaseActivity
import space.chuumong.lolstats.ui.adapter.SummonerGameAdapter
import space.chuumong.lolstats.ui.adapter.SummonerLeagueAdapter
import space.chuumong.lolstats.ui.utils.SummonerLeagueItemSpaceDecoration
import space.chuumong.lolstats.ui.utils.setLightStatusBar
import space.chuumong.lolstats.ui.utils.showNoTitleTwoButtonsDialog
import space.chuumong.lolstats.ui.view.LoadMoreScrollListener
import space.chuumong.lolstats.viewmodel.SummonerViewModel

class MainActivity : BaseActivity<ActivityMainBinding>() {

    companion object {
        private const val TAG = "MainActivity"
        private const val SUMMONER_NAME = "genetory"
    }

    private val summonerViewModel: SummonerViewModel by lazy { getViewModel() as SummonerViewModel }

    private val summonerLeagueAdapter by lazy { SummonerLeagueAdapter() }
    private val summonerGameAdapter by lazy { SummonerGameAdapter() }

    private val gameLoadMoreScrollListener by lazy {
        object : LoadMoreScrollListener() {
            override fun loadDate() {
                getMoreMatchGame(SUMMONER_NAME, lastMatchGameDate)
            }
        }
    }

    override fun getLayoutId() = R.layout.activity_main

    private var lastMatchGameDate = 0

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        setLightStatusBar()

        binding.summonerViewModel = summonerViewModel

        binding.rvLeague.layoutManager =
            LinearLayoutManager(this, LinearLayoutManager.HORIZONTAL, false)
        binding.rvLeague.addItemDecoration(SummonerLeagueItemSpaceDecoration())
        binding.rvLeague.adapter = summonerLeagueAdapter

        binding.rvGame.layoutManager = LinearLayoutManager(this)
        binding.rvGame.adapter = summonerGameAdapter

        binding.scrollView.setOnScrollChangeListener(gameLoadMoreScrollListener)

        summonerViewModel.onClickSummonerRefresh.observe(this, Observer {
            getSummonerInfo(SUMMONER_NAME)
        })

        getSummonerInfo(SUMMONER_NAME)
    }

    @Suppress("SameParameterValue")
    private fun getSummonerInfo(name: String) {
        summonerViewModel.getSummonerInfo(name, object : Result<Summoner> {
            override fun onSuccess(result: Summoner) {
                summonerLeagueAdapter.addAll(result.profile.leagues)
                summonerGameAdapter.addAll(result.matchGame.games)

                lastMatchGameDate = result.matchGame.games.last().createDate

                gameLoadMoreScrollListener.isLoad = true
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

    private fun getMoreMatchGame(name: String, date: Int) {
        gameLoadMoreScrollListener.isLoad = false

        summonerViewModel.getMoreMatchGame(name, date, object : Result<List<SummonerGame>> {
            override fun onSuccess(result: List<SummonerGame>) {
                summonerGameAdapter.addMore(result)

                lastMatchGameDate = result.last().createDate

                gameLoadMoreScrollListener.isLoad = true
            }

            override fun onFail(t: Throwable) {
                Log.e(TAG, t.message, t)

                showNoTitleTwoButtonsDialog(
                    getString(R.string.network_error_retry),
                    getString(R.string.retry),
                    { getMoreMatchGame(SUMMONER_NAME, lastMatchGameDate) },
                    getString(android.R.string.cancel),
                    { finish() }
                )
            }
        })
    }
}
