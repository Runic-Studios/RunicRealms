group = "com.runicrealms"

val rootFolder = rootProject.buildDir
allprojects {
    buildDir = (parent?.buildDir ?: rootFolder).resolve(name)
}

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
            val file = File(rootProject.projectDir, "libs").relativeTo(projectDir)
            flatDir { dirs(file.path) }
            mavenLocal()
            mavenCentral()
        }
    }

    //tasks.findByName("build") ?: return@subprojects
    /*println(tasks.findByName("shadowJar") != null)
    val hasShadowJar = try {
        tasks.findByName("shadowJar")
        true
    } catch (exception: UnknownTaskException) {
        false
    }*/
//    evalu
    afterEvaluate {
        val hasShadowJar = tasks.findByName("shadowJar") != null
        val execute: (Task) -> Unit = {
            with(it) {
                val buildFile = outputs.files.files.first()
                if (hasShadowJar && !buildFile.name.contains("-all.jar")) return@with // Don't copy non-shadowJar
                val name = if (!hasShadowJar) buildFile.name else buildFile.name.replace(
                    "-all.jar",
                    ""
                ) + ".jar" // Change shadowJar name to be normal
                val location = File(rootProject.projectDir, "output/$name")
                outputs.files.files.first().copyTo(location, overwrite = true)
            }
        }
        if (hasShadowJar) {
            tasks.named("shadowJar") {
                execute(this)
            }
        } else {
            tasks.withType<Jar> {
                execute(this)
            }
        }
    }
    
}