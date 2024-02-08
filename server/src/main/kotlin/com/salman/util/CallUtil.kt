package com.salman.util

import io.ktor.http.*
import io.ktor.server.application.*
import io.ktor.server.response.*
import kotlinx.serialization.Serializable

@Serializable
private data class BadRequest(val message: String)

suspend fun ApplicationCall.respondBadRequest() = respond(
    status = HttpStatusCode.BadRequest,
    message = BadRequest("Invalid request")
)