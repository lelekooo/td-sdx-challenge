package td.sdx.challenge.service

import org.springframework.stereotype.Service
import td.sdx.challenge.model.Event
import td.sdx.challenge.repository.EventRepository

@Service
class EventService(private val eventRepository: EventRepository) {
    fun createEvent(event: Event): Event {
        return eventRepository.save(event)
    }
}
