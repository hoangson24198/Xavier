package com.hoangson.xavier.domain

abstract class UseCase<in P, R> {

    operator fun invoke(parameters: P): R = execute(parameters)

    @Throws(RuntimeException::class)
    protected abstract suspend fun execute(parameters: P): R
}