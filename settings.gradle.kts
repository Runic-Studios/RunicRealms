rootProject.name = "RunicRealms"

pluginManagement {
    plugins {
        kotlin("jvm") version "1.7.21"
        kotlin("kapt") version "1.7.21"
        id("com.github.johnrengelman.shadow") version "7.1.2"
    }
}

@Suppress("UnstableApiUsage")
dependencyResolutionManagement {
    versionCatalogs {
        create("commonLibs") {
            // RunicCommon dependencies (shadowed):
            library("jedis", "redis.clients:jedis:4.2.3")
            library("acf", "co.aikar:acf-paper:0.5.1-SNAPSHOT")
            library("taskchain", "co.aikar:taskchain-bukkit:3.7.2")
            library("springdatamongodb", "org.springframework.data:spring-data-mongodb:3.4.0")
            library("mongodbdriversync", "org.mongodb:mongodb-driver-sync:4.6.1")
            library("mongodbdrivercore", "org.mongodb:mongodb-driver-core:4.6.1")
            library("configme", "ch.jalu:configme:1.1.0")
            library("apachecommonslang", "org.apache.commons:commons-lang3:3.12.0")
            library("apachecommonsmath", "org.apache.commons:commons-math3:3.6.1")
            library("apachecommonspool", "org.apache.commons:commons-pool2:2.11.1")
            library("httpclient", "org.apache.httpcomponents:httpclient:4.5.14")
            library("commonsio", "commons-io:commons-io:2.11.0")
            library("spark", "com.sparkjava:spark-core:2.9.4")
            library("menus", "com.gmail.excel8392:menus:1.0")
            library("log4japi", "org.apache.logging.log4j:log4j-api:2.20.0")
            library("log4jcore", "org.apache.logging.log4j:log4j-core:2.20.0")

            // Plugin dependencies (provided):
            library("paper", "io.papermc.paper:paper-api:1.19.4-R0.1-SNAPSHOT")
            library("spigot", "org.spigotmc:spigot-api:1.19.4-R0.1-SNAPSHOT")
            library("placeholderapi", "me.clip:placeholderapi:2.11.3")
            library("mythicmobs", "net.elseland.xikage:mythicmobs:5.2.6")
            //library("craftbukkit", "org.bukkit:craftbukkit:1.19.4")
            library("worldguardevents", "net.raidstone:WorldGuardEvents:1.18.1")
            library("armorequip", "com.github.Arnuh:ArmorEquipEvent:1.7.1")
            library("worldguardcore", "com.sk89q.worldguard:worldguard-core:7.0.0-SNAPSHOT")
            library("worldguardlegacy", "com.sk89q.worldguard:worldguard-legacy:7.0.0-SNAPSHOT")
            library("holographicdisplays", "me.filoghost.holographicdisplays:holographicdisplays-api:3.0.2")
            library("nametagedit", "com.github.sgtcaze:NametagEdit:4.5.18")
            library("nbtapi", "dr.tr7zw:item-nbt-api:2.11.3")
            library("tabbed", "com.github.thekeenant:tabbed:v1.8")
            library("viaversion", "com.viaversion:viaversion-api:4.7.0")
            library("protocollib", "com.comphenix.protocol:ProtocolLib:5.0.0")
            library("luckperms", "net.luckperms:api:5.4")
        }
    }
}

include("Projects:Achievements")
findProject(":Projects:Achievements")?.name = "Achievements"
include("Projects:Bank")
findProject(":Projects:Bank")?.name = "Bank"
include("Projects:Chat")
findProject(":Projects:Chat")?.name = "Chat"
include("Projects:Common")
findProject(":Projects:Common")?.name = "Common"
include("Projects:Core")
findProject(":Projects:Core")?.name = "Core"
include("Projects:FilePull")
findProject(":Projects:FilePull")?.name = "FilePull"
include("Projects:Guilds")
findProject(":Projects:Guilds")?.name = "Guilds"
include("Projects:Items")
findProject(":Projects:Items")?.name = "Items"
include("Projects:Mounts")
findProject(":Projects:Mounts")?.name = "Mounts"
include("Projects:Npcs")
findProject(":Projects:Npcs")?.name = "Npcs"
include("Projects:Professions")
findProject(":Projects:Professions")?.name = "Professions"
include("Projects:PvP")
findProject(":Projects:PvP")?.name = "PvP"
include("Projects:Quests")
findProject(":Projects:Quests")?.name = "Quests"
include("Projects:Reputation")
findProject(":Projects:Reputation")?.name = "Reputation"
include("Projects:Restart")
findProject(":Projects:Restart")?.name = "Restart"
include("Projects:Database")
findProject(":Projects:Database")?.name = "Database"
include("Projects:Doors")
findProject(":Projects:Doors")?.name = "Doors"
include("Projects:Tablist")
findProject(":Projects:Tablist")?.name = "Tablist"
include("Projects:Mod")
findProject(":Projects:Mod")?.name = "Mod"