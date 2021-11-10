package td.sdx.challenge.dto

import java.time.LocalDateTime

class EventResponseDTO(
    val id: String,
    val creationDate: LocalDateTime = LocalDateTime.now(),
    val reason: String)