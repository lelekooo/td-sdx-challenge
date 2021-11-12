package td.sdx.challenge

import org.junit.jupiter.api.BeforeAll
import org.junit.runner.RunWith
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.boot.test.context.SpringBootTest
import org.springframework.boot.test.web.client.TestRestTemplate
import org.springframework.test.context.ActiveProfiles
import org.springframework.test.context.DynamicPropertyRegistry
import org.springframework.test.context.DynamicPropertySource
import org.springframework.test.context.junit4.SpringRunner
import org.testcontainers.containers.MongoDBContainer
import org.testcontainers.containers.Network
import org.testcontainers.junit.jupiter.Container
import org.testcontainers.junit.jupiter.Testcontainers
import org.testcontainers.utility.DockerImageName

@ActiveProfiles("test")
@RunWith(SpringRunner::class)
@SpringBootTest(classes = [ChallengeApplication::class], webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
@Testcontainers
open class BaseITTest {

    @Autowired
    protected lateinit var testRestTemplate: TestRestTemplate

    companion object {
        @Container
        private val mongoDBContainer = MongoDBContainer(DockerImageName.parse("mongo:4.4.3"))

        @DynamicPropertySource
        @JvmStatic
        fun registerDynamicProperties(registry: DynamicPropertyRegistry) {
            registry.add("spring.data.mongodb.uri", { "mongodb://localhost:${mongoDBContainer.firstMappedPort}/event?connectTimeoutMS=30000&socketTimeoutMS=30000" })
        }

        @BeforeAll
        @JvmStatic
        fun beforeAll() {
            mongoDBContainer.withNetwork(Network.newNetwork())
            mongoDBContainer.start()
        }
    }
}
