package td.sdx.challenge.config

import com.twilio.Twilio
import org.springframework.beans.factory.InitializingBean
import org.springframework.beans.factory.annotation.Value
import org.springframework.context.annotation.Configuration

@Configuration
class TwilioConfiguration : InitializingBean {

    @Value("\${twilio.accountSid}")
    lateinit var accountSid: String
    @Value("\${twilio.authToke}")
    lateinit var authToke: String

    override fun afterPropertiesSet() {
        Twilio.init(accountSid, authToke)
    }
}
