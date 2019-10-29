package space.chuumong.domain.usecases

import io.reactivex.Single
import space.chuumong.domain.entities.SummonerInfo
import space.chuumong.domain.repositories.SummonerRepository

class GetSummonerInfo(private val repository: SummonerRepository) :
    UseCase<Map<String, String>, SummonerInfo>() {

    companion object {
        private val NAME = "params:name"
    }

    override fun run(params: Map<String, String>): Single<SummonerInfo> {
        val name = params[NAME] ?: throw NullPointerException()

        return repository.getSummonerInfo(name)
    }

    fun get(name: String): Single<SummonerInfo> {
        val params = hashMapOf<String, String>()
        params[NAME] = name

        return execute(params)
    }

}