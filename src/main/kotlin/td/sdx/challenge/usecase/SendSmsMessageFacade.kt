package td.sdx.challenge.usecase

import org.springframework.stereotype.Service
import td.sdx.challenge.client.TwilioClient
import td.sdx.challenge.dto.SmsRequestDto
import td.sdx.challenge.model.Event
import td.sdx.challenge.model.Message
import td.sdx.challenge.service.EventService

@Service
class SendSmsMessageFacade(private val twilioClient: TwilioClient, private val eventService: EventService) {

    fun send(smsRequestDto: SmsRequestDto): Event {
        val message = Message(smsRequestDto)
        val twilioMessageResponse = twilioClient.sendMessage(message.from, message.to, message.content)
        val event = Event(reason = "SendSMSMessage", content = message.copy(status = twilioMessageResponse.status.toString()))
        return eventService.createEvent(event)
    }
}
