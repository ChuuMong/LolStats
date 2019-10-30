package space.chuumong.data.mapper

import space.chuumong.domain.entities.SummonerProfile
import io.reactivex.functions.Function
import space.chuumong.data.remote.model.SummonerInfoResponse
import space.chuumong.data.remote.model.SummonerMatchGameResponse
import space.chuumong.domain.entities.SummonerMatchGame

class SummonerMapper {

    fun toSummonerProfileEntity() = Function<SummonerInfoResponse, SummonerProfile> {
        it.toEntity()
    }

    fun toSummonerMatchGameEntity() = Function<SummonerMatchGameResponse, SummonerMatchGame> {
        it.toEntity()
    }
}