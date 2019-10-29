package space.chuumong.data.mapper

import space.chuumong.domain.entities.SummonerInfo
import io.reactivex.functions.Function
import space.chuumong.data.remote.model.SummonerResponse

class SummonerMapper {
    fun toSummonerInfoEntity() = Function<SummonerResponse, SummonerInfo> {
        it.toEntity()
    }
}