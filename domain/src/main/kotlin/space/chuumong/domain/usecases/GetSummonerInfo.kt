package space.chuumong.domain.usecases

import io.reactivex.Single
import space.chuumong.domain.repositories.SummonerRepository

class GetSummonerInfo(private val repository: SummonerRepository) :
    UseCase<Map<String, String>, UseCase.None>() {

    companion object {
        private val NAME = "params:name"
    }

    override fun run(params: Map<String, String>): Single<UseCase.None> {
        val name = params[NAME] ?: throw NullPointerException()

        return repository.getSummonerInfo(name)
    }

    fun get(name: String): Single<None> {
        val params = hashMapOf<String, String>()
        params[NAME] = name

        return execute(params)
    }

}