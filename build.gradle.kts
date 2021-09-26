plugins {
    java
    kotlin("jvm") version "1.4.31"
    id("com.github.johnrengelman.shadow") version "2.0.4"
    kotlin("plugin.serialization") version "1.5.20"
}

group = "brys.dev.SpotifyWeb"
version = "1.0.0"

repositories {
    mavenCentral()
    jcenter()
}

dependencies {
    implementation(kotlin("stdlib"))
    implementation("ch.qos.logback:logback-classic:1.2.3")
    implementation("io.ktor:ktor:1.5.0")
    implementation("io.ktor:ktor-server-netty:1.5.0")
    implementation("com.googlecode.json-simple:json-simple:1.1.1")
    implementation("com.github.ajalt:mordant:1.2.1")
    implementation("com.adamratzman:spotify-api-kotlin-core:3.6.01")
    implementation("com.fasterxml.jackson.module:jackson-module-kotlin:2.13.0-rc2")


}
tasks.withType<org.jetbrains.kotlin.gradle.tasks.KotlinCompile> {
    kotlinOptions.suppressWarnings = true
    kotlinOptions.jvmTarget = JavaVersion.VERSION_1_8.toString()
}
val jar by tasks.getting(Jar::class) {
    manifest {
        attributes["Main-Class"] = "brys.dev.SpotifyWeb.LauncherKt"
    }
}
tasks.withType<com.github.jengelman.gradle.plugins.shadow.tasks.ShadowJar> {
    baseName = group
    classifier = "sws"
    version = version
}
val compileKotlin: org.jetbrains.kotlin.gradle.tasks.KotlinCompile by tasks
compileKotlin.kotlinOptions.useIR = true