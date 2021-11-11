package td.sdx.challenge.dto

import td.sdx.challenge.model.Event
import java.time.Instant

/**
 * @property id
 * @property creationDate
 * @property reason
 */
class EventResponseDto(
    val id: String? = null,
    val creationDate: Instant? = null,
    val reason: String? = null
) { constructor(event: Event) : this(event.id.toHexString(), event.creationDate, event.reason) }
