package com.logger.practicletask.common

sealed class ResultWrapper<out T> {
    class InProgress: ResultWrapper<Nothing>()
    data class Success<out T>(val value: T?): ResultWrapper<T>()
    data class Error(val code: Int? = null, val exception: Throwable): ResultWrapper<Nothing>()
}