package td.sdx.challenge

import org.bson.types.ObjectId
import org.junit.Assert
import org.junit.jupiter.api.Test
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.boot.test.web.client.getForEntity
import org.springframework.boot.test.web.client.postForEntity
import org.springframework.http.HttpEntity
import org.springframework.http.HttpStatus
import org.springframework.http.ResponseEntity
import td.sdx.challenge.dto.EventRequestDto
import td.sdx.challenge.dto.EventResponseDto
import td.sdx.challenge.model.Event
import td.sdx.challenge.repository.EventRepository

class EventControllerIT @Autowired constructor(val eventRepository: EventRepository) : BaseITTest() {

    @Test
    fun testGetEventWithAnIdThatMatchWithADocument_shouldReturnTheEventWithAnOKStatus() {
        // given
        val id = ObjectId.get()
        val event = Event(id = id, reason = "Test Get")
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
    fun testPostEventWithAValidRequestBody_shouldReturnTheEventCreatedWithACreatedStatus() {
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
    fun testPostEventWithAnInvalidRequestBody_shouldReturnEmptyBodyWithABadRequestStatus() {
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
