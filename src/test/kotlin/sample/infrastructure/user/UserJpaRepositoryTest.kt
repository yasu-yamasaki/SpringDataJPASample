package sample.infrastructure.user

import org.junit.jupiter.api.Assertions.assertEquals
import org.junit.jupiter.api.Tag
import org.junit.jupiter.api.Test
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.data.jpa.repository.config.EnableJpaRepositories
import org.springframework.test.context.junit.jupiter.SpringJUnitConfig
import org.springframework.transaction.annotation.Transactional
import sample.infrastructure.system.DBConfig

@Tag("integration")
@Transactional
@SpringJUnitConfig(classes = [UserJpaRepositoryTest.Config::class])
class UserJpaRepositoryTest {

    @Autowired
    lateinit var userJpaRepository: UserJpaRepository

    @EnableJpaRepositories
    class Config : DBConfig()

    @Test
    fun test() {
        val expectedUser = User(name = "Finatext")

        userJpaRepository.save(expectedUser)
        userJpaRepository
            .findById(expectedUser.id)
            .get()
            .let {
                assertEquals(expectedUser, it)
            }
    }
}
