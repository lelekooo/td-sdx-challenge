package td.sdx.challenge.rest

import org.springframework.http.HttpStatus
import org.springframework.http.ResponseEntity
import org.springframework.web.bind.annotation.GetMapping
import org.springframework.web.bind.annotation.PathVariable
import org.springframework.web.bind.annotation.PostMapping
import org.springframework.web.bind.annotation.RequestBody
import org.springframework.web.bind.annotation.RequestMapping
import org.springframework.web.bind.annotation.ResponseStatus
import org.springframework.web.bind.annotation.RestController
import td.sdx.challenge.dto.EventRequestDto
import td.sdx.challenge.dto.EventResponseDto
import td.sdx.challenge.model.Event
import td.sdx.challenge.repository.EventRepository

/**
 * @property eventRepository
 */
@RestController
@RequestMapping("/event")
class EventController(val eventRepository: EventRepository) {

    /**
     * @param id
     * @return
     */
    @GetMapping("/{id}")
    @ResponseStatus(value = HttpStatus.OK)
    fun findBy(@PathVariable id: String): ResponseEntity<EventResponseDto> {
        val optEvent = eventRepository.findById(id)
        return if (optEvent.isPresent) ResponseEntity.ok(EventResponseDto(optEvent.get())) else ResponseEntity<EventResponseDto>(HttpStatus.NOT_FOUND)
    }

    /**
     * @param eventRequest
     * @return
     */
    @PostMapping
    @ResponseStatus(value = HttpStatus.CREATED)
    fun create(@RequestBody eventRequest: EventRequestDto?): ResponseEntity<EventResponseDto> {
        eventRequest?.reason ?: return ResponseEntity<EventResponseDto>(HttpStatus.BAD_REQUEST)
        val event = eventRepository.save(Event(reason = eventRequest.reason))
        return ResponseEntity<EventResponseDto>(EventResponseDto(event), HttpStatus.CREATED)
    }
}
