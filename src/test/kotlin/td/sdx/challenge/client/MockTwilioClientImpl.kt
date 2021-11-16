package td.sdx.challenge.clientEvent

import com.fasterxml.jackson.module.kotlin.jacksonObjectMapper
import com.twilio.rest.api.v2010.account.Message
import org.springframework.context.annotation.Profile
import org.springframework.stereotype.Component
import td.sdx.challenge.client.TwilioClient

@Component
@Profile("test")
class MockTwilioClientImpl : TwilioClient {

    override fun sendMessage(from: String, to: String, text: String): Message {
        return Message.fromJson(
            "{\n" +
                "  \"account_sid\": \"ACXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXX\",\n" +
                "  \"api_version\": \"2010-04-01\",\n" +
                "  \"body\": \"Hi there\",\n" +
                "  \"date_created\": \"Thu, 30 Jul 2015 20:12:31 +0000\",\n" +
                "  \"date_sent\": \"Thu, 30 Jul 2015 20:12:33 +0000\",\n" +
                "  \"date_updated\": \"Thu, 30 Jul 2015 20:12:33 +0000\",\n" +
                "  \"direction\": \"outbound-api\",\n" +
                "  \"error_code\": null,\n" +
                "  \"error_message\": null,\n" +
                "  \"from\": \"+14155552345\",\n" +
                "  \"messaging_service_sid\": null,\n" +
                "  \"num_media\": \"0\",\n" +
                "  \"num_segments\": \"1\",\n" +
                "  \"price\": null,\n" +
                "  \"price_unit\": null,\n" +
                "  \"sid\": \"SMXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXX\",\n" +
                "  \"status\": \"sent\",\n" +
                "  \"subresource_uris\": {\n" +
                "    \"media\": \"/2010-04-01/Accounts/ACXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXX/Messages/SMXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXX/Media.json\"\n" +
                "  },\n" +
                "  \"to\": \"+14155552345\",\n" +
                "  \"uri\": \"/2010-04-01/Accounts/ACXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXX/Messages/SMXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXX.json\"\n" +
                "}",
            jacksonObjectMapper()
        )
    }
}
