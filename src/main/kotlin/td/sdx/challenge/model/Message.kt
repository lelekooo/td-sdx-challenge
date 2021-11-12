package td.sdx.challenge.model

import td.sdx.challenge.dto.SmsRequestDto

data class Message(val content: String, val from: String, val to: String, val type: String, val status: String = "to_send") {
    constructor(smsRequest: SmsRequestDto) : this(smsRequest.text!!, smsRequest.from!!, smsRequest.to!!, "SMS")
}
