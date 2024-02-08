package com.salman.plugins

import com.salman.endpoints.configureFeedbackRouting
import com.salman.endpoints.configureIssueRouting
import io.ktor.server.application.*
import io.ktor.server.routing.*

fun Application.configureRouting() {
    routing {
        configureIssueRouting()
        configureFeedbackRouting()
    }
}
