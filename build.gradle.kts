group = "com.runicrealms"

val rootFolder = rootProject.buildDir
allprojects {
    buildDir = (parent?.buildDir ?: rootFolder).resolve(name)
}

subprojects {
    repositories {
        maven("https://repo.aikar.co/content/groups/aikar/")
        maven("https://repo.papermc.io/repository/maven-public/")
        maven("https://repo.dmulloy2.net/nexus/repository/public/")
        maven("https://repo.aikar.co/content/groups/aikar/")
        maven("https://maven.enginehub.org/repo/")
        maven("https://oss.sonatype.org/content/groups/public/")
        maven("https://repo.destroystokyo.com/repository/maven-public/")
        maven("https://repo.extendedclip.com/content/repositories/placeholderapi/")
        maven("https://jitpack.io")
        maven("https://repo.codemc.io/repository/maven-public/")
        maven("https://hub.spigotmc.org/nexus/content/repositories/snapshots/")
        maven("https://jcenter.bintray.com")
        val file = File(rootProject.projectDir, "libs").relativeTo(projectDir)
        flatDir { dirs(file.path) }
        mavenLocal()
        mavenCentral()
    }
}