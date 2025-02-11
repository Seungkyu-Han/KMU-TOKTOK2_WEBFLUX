package seungkyu.kmutoktokwebflux.dto

import com.fasterxml.jackson.annotation.JsonProperty

data class ChatBotReq(
    val content: String,
    @JsonProperty("assistant_id")
    val assistantId: String
)

data class ChatGPTReq(
    @JsonProperty("assistant_id")
    val assistantId: String,
    val thread: Thread,
    val stream: Boolean
)

data class Message(
    val role: String,
    val content: String
)

data class Thread(
    val messages: List<Message>
)
