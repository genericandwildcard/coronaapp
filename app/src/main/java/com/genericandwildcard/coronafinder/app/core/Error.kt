package com.genericandwildcard.coronafinder.app.core

sealed class Error {
    object NetworkError : Error()
    object GenericError : Error()
}
