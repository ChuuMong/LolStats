package space.chuumong.data

interface Result<T> {

    fun onSuccess(result: T)

    fun onFail(t: Throwable)
}