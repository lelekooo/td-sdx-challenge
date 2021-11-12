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
import td.sdx.challenge.dto.BaseResponseDto
import td.sdx.challenge.dto.ErrorMessageDto
import td.sdx.challenge.dto.EventResponseDto
import td.sdx.challenge.dto.SmsRequestDto
import td.sdx.challenge.repository.EventRepository
import td.sdx.challenge.usecase.SendSmsMessageFacade

/**
 * @property eventRepository
 */
@RestController
@RequestMapping("/event")
class EventController(val eventRepository: EventRepository, val sendSmsMessageFacade: SendSmsMessageFacade) {

    /**
     * @param id
     * @return
     */
    @GetMapping("/{id}")
    @ResponseStatus(value = HttpStatus.OK)
    fun findBy(@PathVariable id: String): ResponseEntity<BaseResponseDto> {
        val optEvent = eventRepository.findById(id)
        return if (optEvent.isPresent) ResponseEntity.ok(EventResponseDto(optEvent.get())) else ResponseEntity<BaseResponseDto>(HttpStatus.NOT_FOUND)
    }

    /**
     * @param smsEventRequest
     * @return
     */
    @PostMapping("/sms/send")
    @ResponseStatus(value = HttpStatus.CREATED)
    fun smsSend(@RequestBody smsEventRequest: SmsRequestDto?): ResponseEntity<BaseResponseDto> {
        smsEventRequest?.text ?: return ResponseEntity<BaseResponseDto>(ErrorMessageDto("Field is Required", "Field 'text' must not be null"), HttpStatus.BAD_REQUEST)
        smsEventRequest.from ?: return ResponseEntity<BaseResponseDto>(ErrorMessageDto("Field is Required", "Field 'from' must not be null"), HttpStatus.BAD_REQUEST)
        smsEventRequest.to ?: return ResponseEntity<BaseResponseDto>(ErrorMessageDto("Field is Required", "Field to 'must' not be null"), HttpStatus.BAD_REQUEST)

        val event = sendSmsMessageFacade.send(smsEventRequest)
        return ResponseEntity<BaseResponseDto>(EventResponseDto(event), HttpStatus.CREATED)
    }
}
