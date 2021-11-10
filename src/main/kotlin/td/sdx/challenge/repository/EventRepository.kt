package td.sdx.challenge.repository

import org.bson.types.ObjectId
import org.springframework.data.mongodb.repository.MongoRepository
import td.sdx.challenge.model.Event


interface EventRepository : MongoRepository<Event, String> {
    /**
     * @param id
     */
    fun findById(id: ObjectId): Event
}

