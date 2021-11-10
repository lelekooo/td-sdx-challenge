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
import td.sdx.challenge.dto.EventRequestDTO
import td.sdx.challenge.dto.EventResponseDTO
import td.sdx.challenge.model.Event
import td.sdx.challenge.repository.EventRepository
import java.io.File

class EventControllerIT  @Autowired constructor(val eventRepository: EventRepository): ChallengeApplicationTests() {

    @Test
    fun testGetEvent(){
        //given
        val id = ObjectId.get()
        val event = Event(id = id, reason = "Test Get")
        eventRepository.save(event)

        //when
        val response: ResponseEntity<EventResponseDTO> = testRestTemplate.getForEntity("/event/${id.toString()}")

        //then
        val eventResponseDTO = response.body
        Assert.assertTrue(HttpStatus.OK == response.statusCode)
        Assert.assertEquals(id.toString(), eventResponseDTO?.id)
        Assert.assertEquals(event.reason, eventResponseDTO?.reason)
        Assert.assertNotNull(eventResponseDTO?.creationDate)
    }

    @Test
    fun testPostEvent(){
        //given
        val requestBody = EventRequestDTO("Test")
        val request: HttpEntity<EventRequestDTO> = HttpEntity<EventRequestDTO>(requestBody)

        //when
        val response: ResponseEntity<EventResponseDTO> = testRestTemplate.postForEntity("/event", request)

        //then
        val eventResponseDTO = response.body
        Assert.assertTrue(HttpStatus.CREATED == response.statusCode)
        Assert.assertNotNull(eventResponseDTO?.id)
        Assert.assertNotNull(eventResponseDTO?.reason)
        Assert.assertNotNull(eventResponseDTO?.creationDate)
    }

}