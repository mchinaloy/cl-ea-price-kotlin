import org.jetbrains.kotlin.gradle.tasks.KotlinCompile

plugins {
    application
    id("org.jetbrains.kotlin.jvm") version "1.5.30"
    id("org.jetbrains.kotlin.kapt") version "1.5.30"
    id("org.jetbrains.kotlin.plugin.allopen") version "1.5.30"
    id("io.micronaut.application") version "3.1.0"
    id("com.github.johnrengelman.shadow") version "6.1.0"
}

group = "com.chainlink"
version = "1.0-SNAPSHOT"

micronaut {
    version.set("3.1.1")
}

repositories {
    mavenCentral()
}

dependencies {
    // KOTLIN
    implementation("org.jetbrains.kotlin:kotlin-reflect:1.5.30")
    implementation("org.jetbrains.kotlin:kotlin-stdlib-jdk8:1.5.30")

    // MICRONAUT
    implementation("io.micronaut:micronaut-inject:3.3.3")
    implementation("io.micronaut:micronaut-http-server-netty")
    implementation("io.micronaut:micronaut-http-client:3.3.3")
    implementation("io.micronaut.cache:micronaut-cache-caffeine:3.2.0")
    implementation("io.micronaut.aws:micronaut-aws-sdk-v2:3.1.1")
    implementation("io.micronaut.aws:micronaut-aws-common:3.1.1")
    implementation("io.micronaut.aws:micronaut-function-aws:3.1.1")
    annotationProcessor("io.micronaut:micronaut-inject-java:3.3.3")

    // LOGGING
    runtimeOnly("ch.qos.logback:logback-classic")

    // TESTING
    testRuntimeOnly("org.junit.jupiter:junit-jupiter-engine")
    testImplementation("org.mockito.kotlin:mockito-kotlin:4.0.0")
    testImplementation("io.micronaut.test:micronaut-test-junit5")
}

tasks.test {
    useJUnitPlatform()
}

tasks.withType<KotlinCompile> {
    kotlinOptions.jvmTarget = "15"
}

application {
    mainClass.set("com.chainlink.MainKt")
}
