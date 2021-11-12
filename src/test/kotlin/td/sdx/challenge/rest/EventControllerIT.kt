package td.sdx.challenge.rest

import org.bson.types.ObjectId
import org.junit.Assert
import org.junit.jupiter.api.Test
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.boot.test.web.client.getForEntity
import org.springframework.boot.test.web.client.postForEntity
import org.springframework.http.HttpEntity
import org.springframework.http.HttpStatus
import org.springframework.http.ResponseEntity
import td.sdx.challenge.BaseITTest
import td.sdx.challenge.dto.ErrorMessageDto
import td.sdx.challenge.dto.EventRequestDto
import td.sdx.challenge.dto.EventResponseDto
import td.sdx.challenge.dto.SmsRequestDto
import td.sdx.challenge.model.Event
import td.sdx.challenge.model.Message
import td.sdx.challenge.repository.EventRepository

class EventControllerIT @Autowired constructor(val eventRepository: EventRepository) : BaseITTest() {

    @Test
    fun testGetEventWithAnIdThatMatchWithADocument_shouldReturnTheEventWithAnOKStatus() {
        // given
        val id = ObjectId.get()
        val message = Message("new sms text", "+5511980785634", "+5511980785633", "SMS")
        val event = Event(id = id, reason = "Test Get", content = message)
        eventRepository.save(event)

        // when
        val response: ResponseEntity<EventResponseDto> = testRestTemplate.getForEntity("/event/${id.toHexString()}")

        // then
        val eventResponseDTO = response.body
        Assert.assertTrue(HttpStatus.OK == response.statusCode)
        Assert.assertEquals(id.toString(), eventResponseDTO?.id)
        Assert.assertEquals(event.reason, eventResponseDTO?.reason)
        Assert.assertNotNull(eventResponseDTO?.creationDate)
    }

    @Test
    fun passAnIdThatDoesNotMatchWithAnyEventDocument_shouldReturnAnEmptyBodyWithNotFoundStatus() {
        // given
        val id = ObjectId.get()

        // when
        val response: ResponseEntity<EventResponseDto> = testRestTemplate.getForEntity("/event/${id.toHexString()}")

        // then
        val eventResponseDTO = response.body
        Assert.assertTrue(HttpStatus.NOT_FOUND == response.statusCode)
        Assert.assertNull(eventResponseDTO)
    }

    @Test
    fun testPostSmsEventWithAValidRequestBody_shouldReturnTheEventWithACreatedStatus() {
        // given
        val requestBody = SmsRequestDto("sms_text", "+5511982697628", "+5511987304718")
        val request: HttpEntity<EventRequestDto> = HttpEntity<EventRequestDto>(requestBody)

        // when
        val response: ResponseEntity<EventResponseDto> = testRestTemplate.postForEntity("/event/sms/send", request)

        // then
        val eventResponseDTO = response.body
        Assert.assertTrue(HttpStatus.CREATED == response.statusCode)
        Assert.assertNotNull(eventResponseDTO?.id)
        Assert.assertNotNull(eventResponseDTO?.reason)
        Assert.assertNotNull(eventResponseDTO?.creationDate)
        Assert.assertNotNull(eventResponseDTO?.content)
    }

    @Test
    fun testPostSmsEventWithAnInvalidRequestBody_shouldReturnErrorMessageBodyWithABadRequestStatus() {
        // given
        val requestBody = SmsRequestDto("sms_text", null, "+5511987304718")
        val request: HttpEntity<EventRequestDto> = HttpEntity<EventRequestDto>(requestBody)

        // when
        val response: ResponseEntity<ErrorMessageDto> = testRestTemplate.postForEntity("/event/sms/send", request)

        // then
        val errorMessage = response.body
        Assert.assertTrue(HttpStatus.BAD_REQUEST == response.statusCode)
        Assert.assertNotNull(errorMessage?.error)
        Assert.assertNotNull(errorMessage?.reason)
        Assert.assertEquals("Field 'from' must not be null", errorMessage?.reason)
    }
}
