package com.github.nehemiaharchives

import kotlinx.serialization.Serializable


@Serializable
data class Center(val children: Array<String>, val enName: String, val name: String, val officeName: String) {
    override fun equals(other: Any?): Boolean {
        if (this === other) return true
        if (javaClass != other?.javaClass) return false

        other as Center

        if (!children.contentEquals(other.children)) return false
        if (enName != other.enName) return false
        if (name != other.name) return false
        if (officeName != other.officeName) return false

        return true
    }

    override fun hashCode(): Int {
        var result = children.contentHashCode()
        result = 31 * result + enName.hashCode()
        result = 31 * result + name.hashCode()
        result = 31 * result + officeName.hashCode()
        return result
    }
}

@Serializable
data class Class10Area(val children: Array<String>, val enName: String, val name: String, val parent: String) {
    override fun equals(other: Any?): Boolean {
        if (this === other) return true
        if (javaClass != other?.javaClass) return false

        other as Class10Area

        if (!children.contentEquals(other.children)) return false
        if (enName != other.enName) return false
        if (name != other.name) return false
        if (parent != other.parent) return false

        return true
    }

    override fun hashCode(): Int {
        var result = children.contentHashCode()
        result = 31 * result + enName.hashCode()
        result = 31 * result + name.hashCode()
        result = 31 * result + parent.hashCode()
        return result
    }
}

@Serializable
data class Class15Area(val children: Array<String>, val enName: String, val name: String, val parent: String) {
    override fun equals(other: Any?): Boolean {
        if (this === other) return true
        if (javaClass != other?.javaClass) return false

        other as Class15Area

        if (!children.contentEquals(other.children)) return false
        if (enName != other.enName) return false
        if (name != other.name) return false
        if (parent != other.parent) return false

        return true
    }

    override fun hashCode(): Int {
        var result = children.contentHashCode()
        result = 31 * result + enName.hashCode()
        result = 31 * result + name.hashCode()
        result = 31 * result + parent.hashCode()
        return result
    }
}

@Serializable
data class Class20Area(val enName: String, val kana: String, val name: String, val parent: String)

@Serializable
data class Office(val children: Array<String>, val enName: String, val name: String, val officeName: String, val parent: String) {
    override fun equals(other: Any?): Boolean {
        if (this === other) return true
        if (javaClass != other?.javaClass) return false

        other as Office

        if (!children.contentEquals(other.children)) return false
        if (enName != other.enName) return false
        if (name != other.name) return false
        if (officeName != other.officeName) return false
        if (parent != other.parent) return false

        return true
    }

    override fun hashCode(): Int {
        var result = children.contentHashCode()
        result = 31 * result + enName.hashCode()
        result = 31 * result + name.hashCode()
        result = 31 * result + officeName.hashCode()
        result = 31 * result + parent.hashCode()
        return result
    }
}

@Serializable
data class JmaArea(
    val centers: Map<String, Center>,
    val class10s: Map<String, Class10Area>,
    val class15s: Map<String, Class15Area>,
    val class20s: Map<String, Class20Area>,
    val offices: Map<String, Office>
)