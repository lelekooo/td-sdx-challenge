package td.sdx.challenge.dto

class SmsRequestDto(val text: String?, val from: String?, val to: String?) : EventRequestDto("SMS Send")
