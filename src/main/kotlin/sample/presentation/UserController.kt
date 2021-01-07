package sample.presentation

import org.springframework.http.HttpStatus
import org.springframework.http.ResponseEntity
import org.springframework.web.bind.annotation.DeleteMapping
import org.springframework.web.bind.annotation.GetMapping
import org.springframework.web.bind.annotation.PostMapping
import org.springframework.web.bind.annotation.RequestMapping
import org.springframework.web.bind.annotation.RestController
import sample.infrastructure.user.User
import sample.infrastructure.user.UserJpaRepository
import java.util.UUID

@RestController
@RequestMapping("/users")
class UserController(
    private val userJpaRepository: UserJpaRepository
) {
    @GetMapping
    fun getAll(): ResponseEntity<List<User>> {
        return userJpaRepository
            .findAll()
            .let {
                ResponseEntity(it, HttpStatus.OK)
            }
    }
}
