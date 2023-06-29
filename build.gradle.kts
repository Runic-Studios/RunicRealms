import org.gradle.configurationcache.extensions.capitalized

group = "com.runicrealms.plugin"

val rootFolder: File = rootProject.buildDir
allprojects {
    buildDir = (parent?.buildDir ?: rootFolder).resolve(name)
}

val rrGroup by extra { "com.runicrealms.plugin" }
val rrVersion by extra { "2.1.0" }
val mcVersion by extra { "1.19.4" }

subprojects {
    beforeEvaluate {
        tasks.withType<JavaCompile> {
            options.encoding = "UTF-8"
        }
        tasks.register("wrapper")
        tasks.register("prepareKotlinBuildScriptModel")
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
            maven("https://repo.viaversion.com")
            maven("https://mvn.lumine.io/repository/maven-public/")
            val file = File(rootProject.projectDir, "libs").relativeTo(projectDir)
            flatDir { dirs(file.path) }
            mavenLocal()
            mavenCentral()
        }
    }
    // Custom script to copy build files to output folder
    afterEvaluate {
        val hasShadowJar = tasks.findByName("shadowJar") != null
        if (hasShadowJar) tasks.getByName("build").dependsOn("shadowJar")
        var buildOutputs: TaskOutputs? = null
        val execute: (TaskOutputs) -> Unit = execute@{
            val buildFile = it.files.files.first()
            if (hasShadowJar && !buildFile.name.contains("-all.jar")) return@execute // Don't copy non-shadowJar
            val name = "Runic${name.lowercase().capitalized()}-$mcVersion-$rrVersion.jar"
            val location = File(rootProject.projectDir, "output/$name")
            it.files.files.first().copyTo(location, overwrite = true)
        }

        if (hasShadowJar) tasks.named("shadowJar") { buildOutputs = outputs }
        else tasks.withType<Jar> { buildOutputs = outputs }

        tasks.register("buildCopy") {
            doLast { execute(buildOutputs!!) }
        }

        if (hasShadowJar) tasks.getByName("shadowJar").finalizedBy("buildCopy")
        else tasks.withType<Jar> { finalizedBy("buildCopy") }
    }

}

