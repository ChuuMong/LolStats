package space.chuumong.lolstats.ui.main

import android.os.Bundle
import android.util.Log
import androidx.lifecycle.Observer
import androidx.recyclerview.widget.LinearLayoutManager
import org.koin.android.viewmodel.ext.android.getViewModel
import space.chuumong.data.Result
import space.chuumong.domain.entities.SummonerGame
import space.chuumong.domain.entities.SummonerMatchGame
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
import space.chuumong.lolstats.viewmodel.SummonerGameViewModel
import space.chuumong.lolstats.viewmodel.SummonerProfileViewModel

class MainActivity : BaseActivity<ActivityMainBinding>() {

    companion object {
        private const val TAG = "MainActivity"
        private const val SUMMONER_NAME = "genetory"
    }

    private val summonerProfileViewModel: SummonerProfileViewModel by lazy { getViewModel() as SummonerProfileViewModel }
    private val summonerGameViewModel: SummonerGameViewModel by lazy { getViewModel() as SummonerGameViewModel }

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

        binding.summonerProfileViewModel = summonerProfileViewModel
        binding.summonerGameViewModel = summonerGameViewModel

        binding.rvLeague.layoutManager =
            LinearLayoutManager(this, LinearLayoutManager.HORIZONTAL, false)
        binding.rvLeague.addItemDecoration(SummonerLeagueItemSpaceDecoration())
        binding.rvLeague.adapter = summonerLeagueAdapter

        binding.rvGame.layoutManager = LinearLayoutManager(this)
        binding.rvGame.adapter = summonerGameAdapter

        binding.scrollView.setOnScrollChangeListener(gameLoadMoreScrollListener)

        summonerProfileViewModel.onClickSummonerRefresh.observe(this, Observer {
            getSummonerProfile(SUMMONER_NAME)
        })

        getSummonerProfile(SUMMONER_NAME)
    }

    @Suppress("SameParameterValue")
    private fun getSummonerProfile(name: String) {
        gameLoadMoreScrollListener.isLoad = false

        summonerProfileViewModel.getProfile(name, object : Result<SummonerProfile> {
            override fun onSuccess(result: SummonerProfile) {
                summonerLeagueAdapter.addAll(result.leagues)

                getSummonerMatchGame(SUMMONER_NAME)
            }

            override fun onFail(t: Throwable) {
                Log.e(TAG, t.message, t)

                showNoTitleTwoButtonsDialog(
                    getString(R.string.network_error_retry),
                    getString(R.string.retry),
                    { getSummonerProfile(SUMMONER_NAME) },
                    getString(android.R.string.cancel),
                    { finish() }
                )
            }
        })
    }

    @Suppress("SameParameterValue")
    private fun getSummonerMatchGame(name: String) {
        summonerGameViewModel.getMatchGame(name, object : Result<SummonerMatchGame> {
            override fun onSuccess(result: SummonerMatchGame) {
                summonerGameAdapter.addAll(result.games)

                lastMatchGameDate = result.games.last().createDate

                gameLoadMoreScrollListener.isLoad = true
            }

            override fun onFail(t: Throwable) {
                Log.e(TAG, t.message, t)

                showNoTitleTwoButtonsDialog(
                    getString(R.string.network_error_retry),
                    getString(R.string.retry),
                    { getSummonerMatchGame(SUMMONER_NAME) },
                    getString(android.R.string.cancel),
                    { finish() }
                )
            }
        })
    }

    @Suppress("SameParameterValue")
    private fun getMoreMatchGame(name: String, date: Int) {
        gameLoadMoreScrollListener.isLoad = false

        summonerGameViewModel.getMoreMatchGame(name, date, object : Result<List<SummonerGame>> {
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
