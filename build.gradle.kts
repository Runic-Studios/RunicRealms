group = "com.runicrealms"

val rootFolder = rootProject.buildDir
allprojects {
    buildDir = (parent?.buildDir ?: rootFolder).resolve(name)
}