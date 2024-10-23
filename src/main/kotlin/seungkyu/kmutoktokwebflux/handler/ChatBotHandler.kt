package seungkyu.kmutoktokwebflux.handler

import com.fasterxml.jackson.databind.ObjectMapper
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.reactor.awaitSingle
import kotlinx.coroutines.withContext
import org.springframework.beans.factory.annotation.Value
import org.springframework.http.HttpHeaders
import org.springframework.http.MediaType.APPLICATION_JSON_VALUE
import org.springframework.stereotype.Component
import org.springframework.web.reactive.function.client.WebClient
import org.springframework.web.reactive.function.server.ServerRequest
import org.springframework.web.reactive.function.server.ServerResponse
import seungkyu.kmutoktokwebflux.dto.*

@Component
class ChatBotHandler(
    @Value("\${OPEN_API_KEY}")
    private val openApiKey: String,
    private val objectMapper: ObjectMapper
) {

    private val openAIUrl = "https://api.openai.com/v1"
    private val errorMessage = "챗봇 요청 중 에러가 발생했습니다."

    private val createAndRunWebClient =
        WebClient.builder()
            .baseUrl("$openAIUrl/threads/runs")
            .defaultHeaders {
                it.set(HttpHeaders.AUTHORIZATION, "Bearer $openApiKey")
                it.set(HttpHeaders.CONTENT_TYPE, APPLICATION_JSON_VALUE)
                it.set("OpenAI-Beta", "assistants=v2")
            }
            .build()

    suspend fun post(serverRequest: ServerRequest): ServerResponse {
        return withContext(Dispatchers.IO) {
            val chatBotReq = serverRequest.bodyToMono(ChatBotReq::class.java).awaitSingle()
            val response = createAndRunWebClient
                .post()
                .bodyValue(
                    ChatGPTReq(
                        assistantId = chatBotReq.assistantId,
                        thread = Thread(
                            messages = listOf(
                                Message(
                                    role = "user",
                                    content = chatBotReq.content
                                )
                            )
                        ),
                        stream = true
                    )
                )
                .retrieve()
                .bodyToMono(String::class.java)
                .awaitSingle()


            ServerResponse.ok().bodyValue(
                try{
                objectMapper.readValue(response.split("event: thread.message.completed")[1]
                    .split("event: thread.run.step.completed")[0]
                    .trimIndent()
                    .removePrefix("data: "), ChatGPTRes::class.java).content[0].text.value
                }catch (e: IndexOutOfBoundsException){
                    errorMessage
                }
            )
                .awaitSingle()
        }
    }
}
