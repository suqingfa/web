plugins {
    id("java")
    id("idea")
}

group = "org.example"
version = "1.0-SNAPSHOT"

idea {
    module {
        isDownloadSources = true
        isDownloadJavadoc = true
    }
}

repositories {
    mavenCentral()
}

subprojects {
    apply(plugin = "java")

    dependencies {
        implementation("org.slf4j:slf4j-api:2.0.6")

        testImplementation("org.slf4j:slf4j-simple:2.0.6")
        testImplementation("org.junit.jupiter:junit-jupiter-api:5.9.0")
        testRuntimeOnly("org.junit.jupiter:junit-jupiter-engine:5.9.0")
    }

    tasks.getByName<Test>("test") {
        useJUnitPlatform()
    }
}