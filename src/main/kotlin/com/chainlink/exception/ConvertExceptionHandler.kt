package com.chainlink.exception

import io.micronaut.context.annotation.Requires
import io.micronaut.http.HttpRequest
import io.micronaut.http.HttpResponse
import io.micronaut.http.annotation.Produces
import io.micronaut.http.server.exceptions.ExceptionHandler
import jakarta.inject.Singleton

@Produces
@Singleton
@Requires(classes = [Exception::class, ExceptionHandler::class])
class ConvertExceptionHandler : ExceptionHandler<Exception, HttpResponse<*>> {

    override fun handle(httpRequest: HttpRequest<*>, exception: Exception): HttpResponse<*> {
        return HttpResponse.serverError("Sorry - an error occurred, unable to fulfil request.")
    }
}
