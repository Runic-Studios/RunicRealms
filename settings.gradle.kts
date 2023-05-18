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
            library("paper", "com.destroystokyo.paper:paper-api:1.16.5-R0.1-SNAPSHOT")
            library("spigot", "org.spigotmc:spigot-api:1.16.5-R0.1-SNAPSHOT")
            library("jedis", "redis.clients:jedis:4.2.3")
            library("acf", "co.aikar:acf-paper:0.5.0-SNAPSHOT")
            library("taskchain", "co.aikar:taskchain-bukkit:3.7.2")
            library("springdatamongodb", "org.springframework.data:spring-data-mongodb:3.4.0")
            library("mongodbdriversync", "org.mongodb:mongodb-driver-sync:4.6.1")
            library("mongodbdrivercore", "org.mongodb:mongodb-driver-core:4.6.1")
            library("placeholderapi", "me.clip:placeholderapi:2.10.3")
            library("configme", "ch.jalu:configme:1.1.0")
            library("mythicmobs", "net.elseland.xikage:mythicmobs:4.12.0")
            library("craftbukkit", "org.bukkit:craftbukkit:1.16.5")
            library("worldguardevents", "net.raidstone:WorldGuardEvents:1.18.1")
            library("armorequip", "com.github.Arnuh:ArmorEquipEvent:1.7.1")
            library("worldguardcore", "com.sk89q.worldguard:worldguard-core:7.0.0-SNAPSHOT")
            library("worldguardlegacy", "com.sk89q.worldguard:worldguard-legacy:7.0.0-SNAPSHOT")
            library("holographicdisplays", "com.gmail.filoghost.holographicdisplays:holographicdisplays-api:2.4.0")
            library("nametagedit", "com.github.sgtcaze:NametagEdit:master-6a2acbd995-1")
            library("nbtapi", "dr.tr7zw:item-nbt-api:2.7.1")
            library("tabbed", "com.github.thekeenant:tabbed:v1.8")
            library("viaversion", "com.github.MylesIsCool.ViaVersion:viaversion:3.2.0")
            library("protocollib", "com.comphenix.protocol:ProtocolLib:4.5.0")
        }
    }
    with (repositories) {
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
        flatDir { dirs("libs") }
        mavenLocal()
        mavenCentral()
    }
}
include("Projects:Achievements")
findProject(":Projects:Achievements")?.name = "Achievements"
include("Projects:Artifacts")
findProject(":Projects:Artifacts")?.name = "Artifacts"
include("Projects:Bank")
findProject(":Projects:Bank")?.name = "Bank"
include("Projects:Chat")
findProject(":Projects:Chat")?.name = "Chat"
include("Projects:Core")
findProject(":Projects:Core")?.name = "Core"
include("Projects:FilePull")
findProject(":Projects:FilePull")?.name = "FilePull"
include("Projects:Groups")
findProject(":Projects:Groups")?.name = "Groups"
include("Projects:GuildLeaderboards")
findProject(":Projects:GuildLeaderboards")?.name = "GuildLeaderboards"
include("Projects:Guilds")
findProject(":Projects:Guilds")?.name = "Guilds"
include("Projects:Items")
findProject(":Projects:Items")?.name = "Items"
include("Projects:Mounts")
findProject(":Projects:Mounts")?.name = "Mounts"
include("Projects:Npcs")
findProject(":Projects:Npcs")?.name = "Npcs"
include("Projects:PlayerShops")
findProject(":Projects:PlayerShops")?.name = "PlayerShops"
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
include("Projects:SkillCheck")
findProject(":Projects:SkillCheck")?.name = "SkillCheck"

