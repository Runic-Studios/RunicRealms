import org.gradle.configurationcache.extensions.capitalized

group = "com.runicrealms.plugin"

// Prevent nested build dirs, instead every subproject uses same build dir
val rootFolder: File = rootProject.layout.buildDirectory.asFile.get()
allprojects {
    layout.buildDirectory.fileValue((parent?.layout?.buildDirectory?.asFile?.orNull ?: rootFolder).resolve(name))
}

// From gradle.properties
val rrGroup by extra { "com.runicrealms.plugin" }
val rrVersion by extra { project.properties["rrVersion"] }
val mcVersion by extra { project.properties["mcVersion"] }
val jvmVersion by extra { project.properties["jvmVersion"] }

subprojects {
    // Ignore the Projects subproject
    if (name.equals("projects", ignoreCase = true)) return@subprojects

    // Apply during project configuration
    apply(plugin = "java")

    beforeEvaluate {
        // Set compiler encoding to work UTF-8. Important for some custom icons we use in the UI.
        tasks.withType<JavaCompile>().configureEach {
            options.encoding = "UTF-8"
        }

        // Set the toolchain language for our java plugin
        extensions.findByType<JavaPluginExtension>()!!
            .toolchain
            .languageVersion
            .set(JavaLanguageVersion.of(jvmVersion as String))

        // Important tasks to register for gradle kotlin DSL
        tasks.register("wrapper")
        tasks.register("prepareKotlinBuildScriptModel")

        // Set subproject version/group
        version = rrVersion!!
        group = rrGroup

        // Shared repos
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
            maven("https://repo.eclipse.org/content/groups/releases/")
            maven("https://mvnrepository.com/artifact/org.apache.httpcomponents/httpclient")
            maven("https://mvnrepository.com/artifact/commons-io/commons-io")
            // Load libs dir
            val file = File(rootProject.projectDir, "libs").relativeTo(projectDir)
            flatDir { dirs(file.path) }
            mavenCentral()
        }
    }

    // Custom script to copy build files to output folder
    afterEvaluate {
        // If we have a shadow jar task, then this means that two jars are going to be created,
        // One with shadow and one without. We only want to copy the one with shadow.
        val hasShadowJar = tasks.findByName("shadowJar") != null

        // This forces the default build task to also execute the shadowJar task, if it exists
        if (hasShadowJar) tasks.getByName("build").dependsOn("shadowJar")

        val copyOutput: (TaskOutputs) -> Unit = execute@{
            val buildFile = it.files.files.first()
            // Make sure that if there is a shadowJar task, we ignore the non-shadow jar if that is what we are
            // executing. This will execute again for the shadowed jar.
            if (hasShadowJar && !buildFile.name.contains("-all.jar")) return@execute
            // Copy to output dir
            val name = "Runic${name.lowercase().capitalized()}-$mcVersion-$rrVersion.jar"
            val location = File(rootProject.projectDir, "output/$name")
            it.files.files.first().copyTo(location, overwrite = true)
        }

        // We keep a local reference to the build outputs and change it to reflect the task we are interested in
        var buildOutputs: TaskOutputs? = null

        // Set find the appropriate outputs of the shadow/non-shadow task
        if (hasShadowJar) tasks.named("shadowJar") { buildOutputs = outputs }
        else tasks.withType<Jar> { buildOutputs = outputs }

        // Create a new task called buildCopy that will execute after build (itself may trigger shadowJar, if exists)
        // This just at the very end copies our build files to the right folder
        tasks.register("buildCopy") {
            doLast { copyOutput(buildOutputs!!) }
        }

        // Make the relevant task finalized by our custom build copy task
        if (hasShadowJar) tasks.getByName("shadowJar").finalizedBy("buildCopy")
        else tasks.withType<Jar> { finalizedBy("buildCopy") }
    }
}