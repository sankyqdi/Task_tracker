plugins {
    application
}

application {
    mainClass.set("app.Main")
    applicationDefaultJvmArgs = listOf("-Dfile.encoding=UTF-8")
}

group = "app"
version = "1.0-SNAPSHOT"

repositories {
    mavenCentral()
}

dependencies {
    testImplementation(platform("org.junit:junit-bom:5.10.0"))
    testImplementation("org.junit.jupiter:junit-jupiter")
    testRuntimeOnly("org.junit.platform:junit-platform-launcher")

    compileOnly("org.projectlombok:lombok:1.18.36")
    annotationProcessor("org.projectlombok:lombok:1.18.36")
    implementation("com.fasterxml.jackson.core:jackson-core:2.15.+")
    implementation("com.fasterxml.jackson.core:jackson-annotations:2.15.+")
    implementation("com.fasterxml.jackson.core:jackson-databind:2.15.+")
    implementation("com.fasterxml.jackson.datatype:jackson-datatype-jsr310:2.15.+")

    implementation("org.slf4j:slf4j-api:2.0.9")
    implementation("ch.qos.logback:logback-classic:1.4.11")
    implementation("org.fusesource.jansi:jansi:2.4.0")

    implementation("org.apache.commons:commons-lang3:3.20.0")
}

tasks.test {
    useJUnitPlatform()
}

tasks.withType<JavaExec> {
    standardInput = System.`in`
}


