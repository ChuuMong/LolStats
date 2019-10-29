package space.chuumong.domain.usecases

import io.reactivex.Single

abstract class UseCase<in Params, Result> {

    internal abstract fun run(params: Params): Single<Result>

    fun execute(params: Params): Single<Result> {
        return run(params)
    }

    class None
}