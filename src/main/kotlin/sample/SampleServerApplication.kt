package sample

import org.springframework.boot.autoconfigure.SpringBootApplication
import org.springframework.boot.builder.SpringApplicationBuilder
import org.springframework.context.annotation.ComponentScan
import org.springframework.context.annotation.Configuration

@SpringBootApplication
@Configuration
@ComponentScan(basePackages = ["sample"])
class SampleServerApplication

fun main() {
    SpringApplicationBuilder(SampleServerApplication::class.java)
        .properties("spring.config.name:app")
        .build()
        .run()
}
