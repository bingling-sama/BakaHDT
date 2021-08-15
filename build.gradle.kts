plugins {
    java
    kotlin("jvm") version "1.5.21"
    kotlin("plugin.serialization") version "1.5.21"
    id("com.github.johnrengelman.shadow") version "7.0.0"
}

group = "cn.booling.bakahdt"
version = "0.2.7-SNAPSHOT"

repositories {
    mavenCentral()
}

dependencies {
    val miraiVersion = "2.6.7"
    api("net.mamoe:mirai-core-api:$miraiVersion")
    runtimeOnly("net.mamoe:mirai-core:$miraiVersion")
    implementation("org.jetbrains.kotlinx:kotlinx-serialization-json:1.2.2")
    implementation("com.google.code.gson:gson:2.8.7")
}
