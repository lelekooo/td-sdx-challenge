package td.sdx.challenge.client

import com.twilio.rest.api.v2010.account.Message

interface TwilioClient {
    fun sendMessage(from: String, to: String, text: String): Message
}
