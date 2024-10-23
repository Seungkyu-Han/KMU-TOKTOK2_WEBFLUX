package seungkyu.kmutoktokwebflux.router

import org.springframework.context.annotation.Bean
import org.springframework.context.annotation.Configuration
import org.springframework.web.reactive.function.server.coRouter
import seungkyu.kmutoktokwebflux.handler.ChatBotHandler

@Configuration
class ChatBotRouter {

    @Bean
    fun chatBotRouterMapping(
        chatBotHandler: ChatBotHandler
    ) = coRouter {
        POST("/", chatBotHandler::post)
    }
}