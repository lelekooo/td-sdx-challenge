package td.sdx.challenge.dto

import td.sdx.challenge.model.Event
import td.sdx.challenge.model.Message
import java.time.Instant

/**
 * @property id
 * @property creationDate
 * @property reason
 */
class EventResponseDto(
    val id: String? = null,
    val creationDate: Instant? = null,
    val reason: String? = null,
    val content: Message? = null
) : BaseResponseDto() { constructor(event: Event) : this(event.id.toHexString(), event.creationDate, event.reason, event.content) }
