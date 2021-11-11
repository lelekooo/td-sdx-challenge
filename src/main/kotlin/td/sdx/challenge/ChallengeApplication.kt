package td.sdx.challenge

import org.springframework.boot.autoconfigure.SpringBootApplication
import org.springframework.boot.runApplication
import org.springframework.data.mongodb.repository.config.EnableMongoRepositories

@SpringBootApplication
@EnableMongoRepositories("td.sdx.challenge.repository")
class ChallengeApplication

fun main(args: Array<String>) {
    runApplication<ChallengeApplication>(*args)
}
