package td.sdx.challenge.rest

import org.springframework.web.bind.annotation.GetMapping
import org.springframework.web.bind.annotation.PathVariable
import org.springframework.web.bind.annotation.RequestMapping
import org.springframework.web.bind.annotation.RestController

@RestController
@RequestMapping("/greeting")
class GreetingController {

    @GetMapping("/{name}")
    fun sayHelloTo(@PathVariable name : String) : String{
        return "Hello $name"
    }
}