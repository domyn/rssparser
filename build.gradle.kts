buildscript {
    dependencies {
        classpath("com.jfrog.bintray.gradle", "gradle-bintray-plugin", "1.8.4")
    }
}

plugins {
    kotlin("jvm") version "1.3.72"
}

group = "pl.domyno"
version = "1.0"

repositories {
    jcenter()
    mavenCentral()
}

dependencies {
    implementation(kotlin("stdlib-jdk8"))
}

tasks {
    compileKotlin {
        kotlinOptions.jvmTarget = "1.8"
    }
    compileTestKotlin {
        kotlinOptions.jvmTarget = "1.8"
    }
}