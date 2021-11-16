package td.sdx.challenge.rest

import org.springframework.http.HttpStatus
import org.springframework.http.ResponseEntity
import org.springframework.web.bind.annotation.PostMapping
import org.springframework.web.bind.annotation.RequestBody
import org.springframework.web.bind.annotation.RequestMapping
import org.springframework.web.bind.annotation.ResponseStatus
import org.springframework.web.bind.annotation.RestController
import td.sdx.challenge.dto.BaseResponseDto
import td.sdx.challenge.dto.ErrorMessageDto
import td.sdx.challenge.dto.EventResponseDto
import td.sdx.challenge.dto.SmsRequestDto
import td.sdx.challenge.usecase.SendSmsMessageFacade

/**
 * @property sendSmsMessageFacade
 */
@RestController
@RequestMapping("/twilio")
class TwilioController(val sendSmsMessageFacade: SendSmsMessageFacade) {

    /**
     * @param smsEventRequest
     * @return
     */
    @PostMapping("/sms")
    @ResponseStatus(value = HttpStatus.CREATED)
    fun send(@RequestBody smsEventRequest: SmsRequestDto?): ResponseEntity<BaseResponseDto> {
        if (smsEventRequest?.text == null || smsEventRequest.from == null || smsEventRequest.to == null) {
            return ResponseEntity<BaseResponseDto>(ErrorMessageDto("All fields are required", "Fields must not be null"), HttpStatus.BAD_REQUEST)
        }
        val event = sendSmsMessageFacade.send(smsEventRequest)
        return ResponseEntity<BaseResponseDto>(EventResponseDto(event), HttpStatus.CREATED)
    }
}
