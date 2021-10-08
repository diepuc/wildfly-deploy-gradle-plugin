import org.jetbrains.kotlin.gradle.plugin.KotlinPluginWrapper
import org.jetbrains.kotlin.gradle.tasks.KotlinCompile

group = "com.mkring.wildlydeplyplugin"
version = "0.3.1"

plugins {
    kotlin("jvm") version "1.5.31"
    `java-gradle-plugin`
    `maven-publish`
    id("com.gradle.plugin-publish").version("0.10.0")
}
val kotlinVersion = plugins.getPlugin(KotlinPluginWrapper::class.java).kotlinPluginVersion

repositories {
    jcenter()
    mavenCentral()
}

gradlePlugin {
    plugins {
        register("deploy-wildfly-plugin") {
            id = "com.mkring.wildlydeplyplugin.deploy-wildfly-plugin"
            implementationClass = "com.mkring.wildlydeplyplugin.DeployWildflyPlugin"
            displayName = "Wildfly Deploy Gradle Plugin"
        }
    }
}

pluginBundle {
    website = "https://github.com/Ingwersaft/wildfly-deploy-gradle-plugin"
    vcsUrl = "https://github.com/Ingwersaft/wildfly-deploy-gradle-plugin"
    description = "Deploys files to a Wildfly und reloads it afterwards"
    tags = listOf("deploy", "wildfly", "jboss-as-cli")
}

dependencies {
    implementation(kotlin("stdlib-jdk8", kotlinVersion))
    runtimeOnly(kotlin("reflect", kotlinVersion))
    implementation("org.wildfly.core", "wildfly-cli", "10.0.3.Final")

    implementation(gradleApi())

    implementation("org.slf4j:slf4j-api:1.7.25")

    testCompileOnly(kotlin("test", kotlinVersion))
    testCompileOnly(kotlin("test-junit", kotlinVersion))

}
tasks.withType<KotlinCompile> {
    kotlinOptions {
        kotlinOptions.jvmTarget = "1.8"
    }
}

publishing {
    repositories {
        mavenLocal()
        maven(url = "build/lib")
    }
}
