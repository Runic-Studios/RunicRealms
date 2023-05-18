group = "com.runicrealms"

val rootFolder = rootProject.buildDir
allprojects {
//    println("allproject name: " + this.name)
//    println("allproject subprojects: " + this.childProjects.entries)
    buildDir = (parent?.buildDir ?: rootFolder).resolve(name)
//    if (this.name == "Projects") {
//        this.childProjects.entries.forEach {
//            println("SETTING VERSION FOR: " + it.key)
//            //it.value.tasks.create("wrapper")
///*            it.value.tasks.wrapper {
//                gradleVersion = "7.6.1"
//            }*/
//        }
//    }

}

//subprojects {
//    println("NAME: " + this.name)
//    buildDir = (parent?.buildDir ?: rootFolder).resolve(name)
//    tasks.wrapper {
//        gradleVersion = "7.6.1"
//    }
//}

//subprojects {
//    println(this.name)
//    if (this.childProjects.)
//}