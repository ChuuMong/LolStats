package space.chuumong.domain.repositories

import io.reactivex.Single
import space.chuumong.domain.usecases.UseCase


/**
 * Created by Home on 2019-10-29.
 */
interface SummonerRepository {

    fun getSummonerInfo(name: String): Single<UseCase.None>
}