package td.sdx.challenge.client

import com.twilio.rest.api.v2010.account.Message
import com.twilio.type.PhoneNumber
import org.springframework.context.annotation.Profile
import org.springframework.stereotype.Component

@Component
@Profile("!test")
class TwilioClientImpl : TwilioClient {
    override fun sendMessage(from: String, to: String, text: String): Message {
        return Message.creator(PhoneNumber(to), PhoneNumber(from), text).create()
    }
}
