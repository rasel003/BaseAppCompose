package com.rasel.baseappcompose.data.network.retrofit


import java.io.IOException

class ApiException(message: String) : IOException(message)
class NoInternetException(message: String) : IOException(message)