package com.salman.endpoints

import com.salman.news.model.IssueRequest
import com.salman.news.model.url.EndPoints
import com.salman.service.IssueService
import com.salman.util.respondBadRequest
import io.ktor.http.*
import io.ktor.server.application.*
import io.ktor.server.request.*
import io.ktor.server.response.*
import io.ktor.server.routing.*


fun Route.configureIssueRouting() {
    route(EndPoints.ISSUE_ROUTE) {
        issuePost()
        issueGet()
    }
}

private fun Route.issuePost() {
    post(EndPoints.SEND_ISSUE) {
        val issue = call.receiveNullable<IssueRequest>()
        if (issue == null) {
            call.respondBadRequest()
            return@post
        }

        val result = IssueService().saveIssue(issue)
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
private fun Route.issueGet() {
    get(EndPoints.GET_ISSUE) {
        val id = call.parameters["issue_id"]?.toIntOrNull()
        if (id == null) {
            call.respondBadRequest()
            return@get
        }
        val issue = IssueService().getIssueByID(id)
        if (issue == null) {
            call.respond(
                status = HttpStatusCode.NotFound,
                message = "Issue not found"
            )
        } else {
            call.respond(
                status = HttpStatusCode.OK,
                message = issue
            )
        }
    }
}