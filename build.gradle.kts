import org.jetbrains.kotlin.gradle.tasks.KotlinCompile

buildscript {
	repositories {
		jcenter()
	}
	dependencies {
		classpath("org.jfrog.buildinfo:build-info-extractor-gradle:4.+")
	}
}

plugins {
	kotlin("jvm") version "1.4.30"
    kotlin("plugin.serialization") version "1.4.30"
    id("org.jetbrains.dokka") version "1.4.20"
	`maven-publish`
}

group = "io.github.ytg1234.kordextbackgroundcat"

repositories {
	mavenCentral()
    jcenter()
    maven(url = "https://oss.sonatype.org/content/repositories/snapshots")
    maven(url = "https://maven.kotlindiscord.com/repository/maven-snapshots/")
    maven(url = "https://maven.kotlindiscord.com/repository/maven-releases/")
    maven(url = "https://dl.bintray.com/kordlib/Kord")
    maven(url = "https://jitpack.io/")
}

dependencies {
    implementation(kotlin("stdlib"))

    // Discord
    implementation("dev.kord", "kord-core", "0.7.0-SNAPSHOT")
    implementation("com.kotlindiscord.kord.extensions", "kord-extensions", "1.4.0-RC6") {
        exclude(group = "dev.kord", module = "kord-core")
    }

    implementation("com.github.YTG1234", "kordext-backgroundcat-base", "main-SNAPSHOT")
}

tasks.withType<KotlinCompile> {
	kotlinOptions.jvmTarget = "1.8"
	kotlinOptions.useIR = true
}

tasks.withType<org.jetbrains.dokka.gradle.DokkaTask>().configureEach {
	dokkaSourceSets {
        named("main") {
            displayName.set("KordExt BackgroundCat - Defaults")
            includes.from("Module.md")
        }
    }
}

tasks.withType<Wrapper> {
    gradleVersion = "6.8.2"
    distributionType = Wrapper.DistributionType.ALL
}

publishing {
	publications {
		create<MavenPublication>("mavenJava") {
			from(components["kotlin"])
		}
	}
}

apply(from = "https://raw.githubusercontent.com/YTG1234/scripts/main/scripts/gradle/artifactory.gradle")
