package td.sdx.challenge

import org.junit.runner.RunWith
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.boot.test.context.SpringBootTest
import org.springframework.boot.test.web.client.TestRestTemplate
import org.springframework.test.context.ActiveProfiles
import org.springframework.test.context.junit4.SpringRunner

@ActiveProfiles("test")
@RunWith(SpringRunner::class)
@SpringBootTest(classes = [ChallengeApplication::class], webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
open class BaseITTest {

    @Autowired protected lateinit var testRestTemplate: TestRestTemplate
}
