package td.sdx.challenge.rest

import org.junit.Assert
import org.junit.jupiter.api.Test
import org.springframework.boot.test.web.client.postForEntity
import org.springframework.http.HttpEntity
import org.springframework.http.HttpHeaders
import org.springframework.http.HttpStatus
import org.springframework.http.ResponseEntity
import td.sdx.challenge.BaseITTest
import td.sdx.challenge.dto.ErrorMessageDto
import td.sdx.challenge.dto.EventRequestDto
import td.sdx.challenge.dto.EventResponseDto
import td.sdx.challenge.dto.SmsRequestDto

class TwilioControllerIT : BaseITTest() {

    @Test
    fun `when post sms event with a valid request body should return the event with a created status`() {
        // given
        val requestBody = SmsRequestDto("sms_text", "+5511982697628", "+5511987304718")
        val request: HttpEntity<EventRequestDto> = HttpEntity<EventRequestDto>(requestBody)

        // when
        val response: ResponseEntity<EventResponseDto> = testRestTemplate.postForEntity("/twilio/sms", request)

        // then
        val eventResponseDTO = response.body
        Assert.assertTrue(HttpStatus.CREATED == response.statusCode)
        Assert.assertNotNull(eventResponseDTO?.id)
        Assert.assertNotNull(eventResponseDTO?.reason)
        Assert.assertNotNull(eventResponseDTO?.creationDate)
        Assert.assertNotNull(eventResponseDTO?.content)
    }

    @Test
    fun `when post sms event with an invalid request body should return error message body with a bad request status`() {
        // given
        val requestBody = SmsRequestDto("sms_text", null, "+5511987304718")
        val request: HttpEntity<EventRequestDto> = HttpEntity<EventRequestDto>(requestBody)

        // when
        val response: ResponseEntity<ErrorMessageDto> = testRestTemplate.postForEntity("/twilio/sms", request)

        // then
        val errorMessage = response.body
        Assert.assertTrue(HttpStatus.BAD_REQUEST == response.statusCode)
        Assert.assertNotNull(errorMessage?.error)
        Assert.assertNotNull(errorMessage?.reason)
        Assert.assertEquals("Fields must not be null", errorMessage?.reason)
    }

    @Test
    fun `when post a receive call should return the xml response that twilio can read`() {
        // given
        val headers = HttpHeaders()
        headers.add("Accept", "application/xml")
        val request = HttpEntity<Nothing>(headers)

        // when
        val response: ResponseEntity<String> = testRestTemplate.postForEntity("/twilio/receive/call", request)

        // then
        val voiceResponse = response.body
        Assert.assertTrue(HttpStatus.OK == response.statusCode)
        Assert.assertNotNull(voiceResponse)
    }
}
