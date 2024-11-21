package td.sdx.challenge.rest

import org.springframework.web.bind.annotation.GetMapping
import org.springframework.web.bind.annotation.RequestMapping
import org.springframework.web.bind.annotation.RequestParam
import org.springframework.web.bind.annotation.RestController

@RestController
@RequestMapping("/greeting")
class GreetingController {
    /**
     * @param name
     */
    @GetMapping
    fun sayHelloTo(@RequestParam name: String) = "Hello $name"
}
