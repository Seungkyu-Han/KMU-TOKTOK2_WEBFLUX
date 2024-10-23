package seungkyu.kmutoktokwebflux.dto

import com.fasterxml.jackson.annotation.JsonProperty
data class ChatGPTRes(
    val id: String,
    @JsonProperty("object")
    val objectType: String,
    @JsonProperty("created_at")
    val createdAt: Long,
    @JsonProperty("assistant_id")
    val assistantId: String,
    @JsonProperty("thread_id")
    val threadId: String,
    @JsonProperty("run_id")
    val runId: String,
    val status: String,
    @JsonProperty("incomplete_details")
    val incompleteDetails: String?,
    @JsonProperty("incomplete_at")
    val incompleteAt: Long?,
    @JsonProperty("completed_at")
    val completedAt: Long,
    val role: String,
    val content: List<Content>,
    val attachments: List<Any>,
    val metadata: Map<String, Any>?
)

data class Content(
    val type: String,
    val text: Text
)

data class Text(
    val value: String,
    val annotations: List<Annotation>?
)

data class Annotation(
    val type: String,
    val text: String,
    @JsonProperty("start_index")
    val startIndex: Int,
    @JsonProperty("end_index")
    val endIndex: Int,
    @JsonProperty("file_citation")
    val fileCitation: FileCitation?
)

data class FileCitation(
    @JsonProperty("file_id")
    val fileId: String?
)
