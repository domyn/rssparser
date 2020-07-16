import java.util.Properties
import java.io.FileInputStream
import com.jfrog.bintray.gradle.BintrayExtension

buildscript {
    repositories {
        jcenter()
        mavenCentral()
    }
}

plugins {
    kotlin("jvm") version "1.3.72"
    id("com.jfrog.bintray") version "1.8.5"
    `maven-publish`
}

val junitVersion = "5.6.2"

group = "pl.domyno"
version = "1.0.0"

repositories {
    jcenter()
    mavenCentral()
}


dependencies {
    implementation(kotlin("stdlib-jdk8"))
    implementation("org.dom4j:dom4j:2.1.3")

    testImplementation("org.junit.jupiter", "junit-jupiter-api", junitVersion)
    testImplementation("org.junit.jupiter", "junit-jupiter-params", junitVersion)
    testRuntimeOnly("org.junit.jupiter", "junit-jupiter-engine", junitVersion)
}

tasks {
    compileKotlin {
        kotlinOptions.jvmTarget = "1.8"
    }
    compileTestKotlin {
        kotlinOptions.jvmTarget = "1.8"
    }
    test {
        useJUnitPlatform()
    }
}

val sourcesJar by tasks.registering(Jar::class) {
    archiveClassifier.set("sources")
    from(sourceSets.main.get().allSource)
}

publishing {
    publications {
        create<MavenPublication>("maven") {
            from(components["java"])
            artifact(sourcesJar.get())
        }
    }
}

val localProperties = Properties().apply { load(FileInputStream(project.rootProject.file("local.properties"))) }

bintray {
    user = localProperties.getProperty("bintray.user")
    key = localProperties.getProperty("bintray.apikey")
    setPublications("maven")

    pkg(closureOf<BintrayExtension.PackageConfig> {
        repo = "maven"
        name = rootProject.name
        setLicenses("Apache-2.0")
        publish = true
        vcsUrl = "https://github.com/domyn/rssparser"
        version(closureOf<BintrayExtension.VersionConfig> {
            name = rootProject.version.toString()
        })
    })
}
