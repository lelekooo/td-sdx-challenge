package td.sdx.challenge.model

import org.bson.types.ObjectId
import org.springframework.data.annotation.Id
import org.springframework.data.mongodb.core.mapping.Document
import java.time.Instant

/**
 * @property id
 * @property creationDate
 * @property reason
 */
@Document(collection = "events")
data class Event(
    @Id val id: ObjectId = ObjectId.get(),
    val creationDate: Instant = Instant.now(),
    val reason: String
)
