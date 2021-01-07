package sample.infrastructure.user

import org.hibernate.annotations.Type
import java.util.UUID
import javax.persistence.Column
import javax.persistence.Entity
import javax.persistence.Id
import javax.persistence.Table

@Entity
@Table(name = "user")
data class User(

    @Id
    @Type(type = "uuid-char")
    val id: UUID = UUID.randomUUID(),

    @Column
    var name: String
)
