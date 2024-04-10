
plugins {
  `java-library`
  id("io.papermc.paperweight.userdev") version "1.5.13"
  id("xyz.jpenilla.run-paper") version "2.2.3"
}

java {
  toolchain.languageVersion = JavaLanguageVersion.of(17)
}

dependencies {
  paperweight.paperDevBundle("1.20.4-R0.1-SNAPSHOT")
  compileOnly("net.luckperms:api:5.4")
  compileOnly( "com.sk89q.worldguard:worldguard-bukkit:7.0.9-SNAPSHOT" )
  compileOnly( "com.sk89q.worldedit:worldedit-bukkit:7.3.0-SNAPSHOT" )
}

repositories {
  maven ("https://jitpack.io")
  mavenCentral()
  maven("https://maven.enginehub.org/repo/")
  gradlePluginPortal()
  maven("https://repo.papermc.io/repository/maven-public/")
}
tasks {
  assemble {
    dependsOn(reobfJar)
  }

  compileJava {
    options.encoding = Charsets.UTF_8.name()
    options.release = 17
  }
  javadoc {
    options.encoding = Charsets.UTF_8.name()
  }
}
