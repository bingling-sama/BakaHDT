import com.github.jengelman.gradle.plugins.shadow.tasks.ShadowJar

plugins {
    java
    kotlin("jvm") version "1.6.10"
    kotlin("plugin.serialization") version "1.6.10"
    id("com.github.johnrengelman.shadow") version "7.0.0"
}

group = "cn.booling.bakahdt"
version = "0.3.5-SNAPSHOT"

tasks.withType<ShadowJar> {
    manifest.attributes["Main-Class"] = "cn.booling.bakahdt.MainKt"
}

repositories {
    maven {
        setUrl("https://maven.aliyun.com/repository/public/")
    }
    maven {
        setUrl("https://maven.github.com/")
    }
    mavenCentral()
}

dependencies {
    // libs
//    implementation(fileTree(mapOf("dir" to "libs", "include" to listOf("*.jar"))))

    val miraiVersion = "2.9.0"
    api("net.mamoe:mirai-core-api:$miraiVersion")
    runtimeOnly("net.mamoe:mirai-core:$miraiVersion")
    api("net.mamoe:mirai-console:$miraiVersion")

    implementation("org.jsoup:jsoup:1.14.3")
    implementation("com.squareup.okhttp3:okhttp:4.9.3")
    implementation("org.jetbrains.kotlinx:kotlinx-serialization-json:1.3.2")
    implementation("org.apache.logging.log4j:log4j-core:2.17.0")
    implementation("com.google.code.gson:gson:2.8.9")
}
