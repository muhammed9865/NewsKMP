package com.salman.endpoints

import com.salman.news.model.FeedbackRequest
import com.salman.news.model.url.EndPoints
import com.salman.service.FeedbackService
import com.salman.util.respondBadRequest
import io.ktor.http.*
import io.ktor.server.application.call
import io.ktor.server.request.*
import io.ktor.server.response.*
import io.ktor.server.routing.*

fun Route.configureFeedbackRouting() {
    val service = FeedbackService()
    route(EndPoints.FEEDBACK_ROUTE) {
        feedbackPost(service)
    }
}

private fun Route.feedbackPost(service: FeedbackService) {
    post(EndPoints.SEND_FEEDBACK) {
        val feedback = call.receiveNullable<FeedbackRequest>()
        if (feedback == null) {
            call.respondBadRequest()
            return@post
        }
        val result = service.insertFeedback(feedback)
        if (result.isSuccess) {
            call.respond(
                status = HttpStatusCode.OK,
                message = result.getOrNull()!!,
            )
        } else {
            call.respond(
                status = HttpStatusCode.InternalServerError,
                message = result.exceptionOrNull()!!.message!!,
            )
        }
    }
}