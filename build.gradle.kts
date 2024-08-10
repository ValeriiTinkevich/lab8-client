plugins {
    id("java")
    id("org.openjfx.javafxplugin") version "0.1.0"
}

group = "ru.valerit"
version = "1.0-SNAPSHOT"

repositories {
    mavenCentral()
}

dependencies {
    testImplementation(platform("org.junit:junit-bom:5.10.0"))
    testImplementation("org.junit.jupiter:junit-jupiter")
}

javafx {
    version = "17"
    modules("javafx.controls", "javafx.fxml", "javafx.graphics", "javafx.base")
}

tasks.test {
    useJUnitPlatform()
}