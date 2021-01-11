buildscript {
    repositories {
        jcenter()
        mavenCentral()
    }

    dependencies {
        classpath("mysql:mysql-connector-java:8.0.22")
    }
}

repositories {
    jcenter()
    mavenCentral()
}

plugins {
    kotlin("jvm") version "1.4.21"
    kotlin("plugin.spring") version "1.4.21"
    kotlin("plugin.jpa") version "1.4.21"
    id("org.springframework.boot") version "2.3.7.RELEASE"
    jacoco
    id("org.flywaydb.flyway") version "6.4.4"
}

jacoco {
    toolVersion = "0.8.6"
}

dependencies {
    implementation(kotlin("stdlib-jdk8"))
    implementation(kotlin("reflect"))
    implementation("org.springframework.boot:spring-boot-starter:2.3.7.RELEASE")
    implementation("org.springframework.boot:spring-boot-starter-web:2.3.7.RELEASE")
    implementation("org.springframework.boot:spring-boot-starter-data-jpa:2.3.7.RELEASE")
    testImplementation("org.springframework.boot:spring-boot-starter-test:2.3.7.RELEASE") {
        exclude(group = "org.junit.vintage", module = "junit-vintage-engine")
    }

    runtimeOnly("mysql:mysql-connector-java:8.0.22")
}

tasks.withType<Test> {
    useJUnitPlatform {
        excludeTags("integration")
    }

    testLogging {
        showExceptions = true
        exceptionFormat = org.gradle.api.tasks.testing.logging.TestExceptionFormat.FULL
        showCauses = true
        showStackTraces = true
        showStandardStreams = false
    }
}

tasks.create("integrationTest", Test::class.java) {
    useJUnitPlatform {
        includeTags("integration")
    }
    configure<JacocoTaskExtension> {
        isEnabled = true
    }

    testLogging {
        showExceptions = true
        exceptionFormat = org.gradle.api.tasks.testing.logging.TestExceptionFormat.FULL
        showCauses = true
        showStackTraces = true
        showStandardStreams = false
    }
}

tasks.create("dbMigrateIt", org.flywaydb.gradle.task.FlywayMigrateTask::class.java) {
    url = "jdbc:mysql://127.0.0.1:3306/sample"
    user = "sample"
    password = "sample"
    locations = arrayOf(
        "db/migrations"
    )
        .map { "filesystem:/${file(it).absolutePath}" }
        .toTypedArray()
}

tasks.create("dbMigrateLocal", org.flywaydb.gradle.task.FlywayMigrateTask::class.java) {
    url = "jdbc:mysql://127.0.0.1:4306/sample"
    user = "local"
    password = "local"
    locations = arrayOf(
        "db/migrations",
        "db/seeds/local"
    )
        .map { "filesystem:/${file(it).absolutePath}" }
        .toTypedArray()
}
