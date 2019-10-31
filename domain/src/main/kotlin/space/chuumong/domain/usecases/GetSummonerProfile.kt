package space.chuumong.domain.usecases

import io.reactivex.Single
import space.chuumong.domain.entities.SummonerProfile
import space.chuumong.domain.repositories.SummonerRepository

class GetSummonerProfile(private val repository: SummonerRepository) :
    UseCase<Map<String, String>, SummonerProfile>() {

    companion object {
        private val NAME = "params:name"
    }

    override fun run(params: Map<String, String>): Single<SummonerProfile> {
        val name = params[NAME] ?: throw NullPointerException()

        return repository.getSummonerProfile(name)
    }

    fun get(name: String): Single<SummonerProfile> {
        val params = hashMapOf<String, String>()
        params[NAME] = name

        return execute(params)
    }

}