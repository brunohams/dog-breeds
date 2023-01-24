package domain.core

interface Logger {
    fun d(event: String)
    fun w(event: String)
    fun e(throwable: Throwable)
}