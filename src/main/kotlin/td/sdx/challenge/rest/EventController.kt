package td.sdx.challenge.rest

import org.bson.types.ObjectId
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.data.mongodb.core.MongoTemplate
import org.springframework.http.HttpStatus
import org.springframework.web.bind.annotation.*
import td.sdx.challenge.dto.EventRequestDTO
import td.sdx.challenge.dto.EventResponseDTO
import td.sdx.challenge.model.Event
import td.sdx.challenge.repository.EventRepository
import javax.validation.Valid

@RestController
@RequestMapping("/event")
class EventController(val eventRepository: EventRepository) {

    @Autowired
    lateinit var mongoTemplate: MongoTemplate
    /**
     * @param id
     * @return
     */
    @GetMapping("/{id}")
    @ResponseStatus(value = HttpStatus.OK)
    fun findBy(@PathVariable id: String): EventResponseDTO{
        val event = eventRepository.findById(ObjectId(id))
        return EventResponseDTO(event.id.toHexString(), event.creationDate, event.reason)
    }

    @PostMapping
    @ResponseStatus(value = HttpStatus.CREATED)
    fun create(@RequestBody eventRequest: EventRequestDTO): EventResponseDTO{
        val event = eventRepository.save(Event(reason = eventRequest.reason))
        return EventResponseDTO(event.id.toHexString(), event.creationDate, event.reason)
    }
}
