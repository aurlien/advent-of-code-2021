plugins {
    kotlin("jvm") version "1.6.0"
}

group = "org.example"
version = "1.0-SNAPSHOT"

repositories {
    mavenCentral()
}

sourceSets {
    main {
        java.srcDir("src")
    }
}

dependencies {
    implementation(kotlin("stdlib"))
}