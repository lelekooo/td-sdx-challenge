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
import td.sdx.challenge.dto.EventRequestDto
import td.sdx.challenge.dto.EventResponseDto
import td.sdx.challenge.model.Event
import td.sdx.challenge.model.Message
import td.sdx.challenge.repository.EventRepository

class EventControllerIT @Autowired constructor(val eventRepository: EventRepository) : BaseITTest() {

    @Test
    fun `when get event with an id that match with a document should return the event with an ok status`() {
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
    fun `when pass an id that does not match with any event document should return an empty body with not found status`() {
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
    fun `when post event with a valid request body should return the event created with a created status`() {
        // given
        val requestBody = EventRequestDto("Test")
        val request: HttpEntity<EventRequestDto> = HttpEntity<EventRequestDto>(requestBody)

        // when
        val response: ResponseEntity<EventResponseDto> = testRestTemplate.postForEntity("/event", request)

        // then
        val eventResponseDTO = response.body
        Assert.assertTrue(HttpStatus.CREATED == response.statusCode)
        Assert.assertNotNull(eventResponseDTO?.id)
        Assert.assertNotNull(eventResponseDTO?.reason)
        Assert.assertNotNull(eventResponseDTO?.creationDate)
    }

    @Test
    fun `when post event with an invalid request body should return empty body with a bad request status`() {
        // given
        val requestBody = EventRequestDto(null)
        val request: HttpEntity<EventRequestDto> = HttpEntity<EventRequestDto>(requestBody)

        // when
        val response: ResponseEntity<EventResponseDto> = testRestTemplate.postForEntity("/event", request)

        // then
        val eventResponseDTO = response.body
        Assert.assertTrue(HttpStatus.BAD_REQUEST == response.statusCode)
        Assert.assertNull(eventResponseDTO)
    }
}
