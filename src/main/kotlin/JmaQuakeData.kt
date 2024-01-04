package com.github.nehemiaharchives

import kotlinx.serialization.*

@Serializable
enum class SeismicIntensity(val level: Int, val notation: String, val japanese: String) {
    NA(0, "empty", "値なし"),
    ZERO(1, "0", "震度0"),
    ONE(2, "1", "震度1"),
    TWO(3, "2", "震度2"),
    TREE(4, "3", "震度3"),
    FOUR(5, "4", "震度4"),
    FIVE_LOWER(6, "5-", "震度5弱"),
    FIVE_UPPER(7, "5+", "震度5強"),
    SIX_LOWER(8, "6-", "震度6弱"),
    SIX_UPPER(9, "6+", "震度6強"),
    SEVEN(10, "7", "震度7");

    fun greaterThanEqual(other: SeismicIntensity): Boolean = level >= other.level
}

fun String.toSeismicIntensity() = SeismicIntensity.entries.firstOrNull { it.notation == this }
    ?: SeismicIntensity.NA

@Serializable(with = CityMaxIntensitySerializer::class)
data class CityMaxIntensity(val code: String, val maxi: SeismicIntensity)

@Serializable(with = PrefectureIntensityArraySerializer::class)
data class PrefectureIntensityArray(val city: Array<CityMaxIntensity>, val code: String, val maxi: SeismicIntensity) {

    override fun equals(other: Any?): Boolean {
        if (this === other) return true
        if (javaClass != other?.javaClass) return false

        other as PrefectureIntensityArray

        if (code != other.code) return false
        if (maxi != other.maxi) return false
        if (!city.contentEquals(other.city)) return false

        return true
    }

    override fun hashCode(): Int {
        var result = code.hashCode()
        result = 31 * result + maxi.hashCode()
        result = 31 * result + city.contentHashCode()
        return result
    }
}

/**
 * Compatible with VXSE51 or VXSE52 for now
 * [ref1](https://www.jma.go.jp/bosai/quake/data/list.json)
 * [ref2](https://www.data.jma.go.jp/add/suishin/cgi-bin/catalogue/make_product_page.cgi?id=Jishin)
 * [ref3](https://xml.kishou.go.jp/jmaxml_20221209_format_v1_3.pdf)
 * */
@Serializable(with = JmaQuakeDataSerializer::class)
data class JmaQuakeData(
    val acd: String,
    val anm: String,
    val at: String,
    val cod: String,
    val ctt: String,
    val eid: String,
    @SerialName("en_anm") val enAnm: String,
    @SerialName("en_ttl") val enTtl: String,
    val ift: String,
    val int: Array<PrefectureIntensityArray>,
    val json: String,
    val mag: String,
    val maxi: SeismicIntensity,
    val rdt: String,
    val ser: String,
    val ttl: String
) {
    fun hasCityMaxIntensity(): Boolean {
        if (int.isEmpty()) return false
        int.forEach { prefectureIntensityArray ->
            if (prefectureIntensityArray.city.isNotEmpty()) return true
        }
        return false
    }

    override fun equals(other: Any?): Boolean {
        if (this === other) return true
        if (javaClass != other?.javaClass) return false

        other as JmaQuakeData

        if (acd != other.acd) return false
        if (anm != other.anm) return false
        if (at != other.at) return false
        if (cod != other.cod) return false
        if (ctt != other.ctt) return false
        if (eid != other.eid) return false
        if (enAnm != other.enAnm) return false
        if (enTtl != other.enTtl) return false
        if (ift != other.ift) return false
        if (!int.contentEquals(other.int)) return false
        if (json != other.json) return false
        if (mag != other.mag) return false
        if (maxi != other.maxi) return false
        if (rdt != other.rdt) return false
        if (ser != other.ser) return false
        if (ttl != other.ttl) return false

        return true
    }

    override fun hashCode(): Int {
        var result = acd.hashCode()
        result = 31 * result + anm.hashCode()
        result = 31 * result + at.hashCode()
        result = 31 * result + cod.hashCode()
        result = 31 * result + ctt.hashCode()
        result = 31 * result + eid.hashCode()
        result = 31 * result + enAnm.hashCode()
        result = 31 * result + enTtl.hashCode()
        result = 31 * result + ift.hashCode()
        result = 31 * result + int.contentHashCode()
        result = 31 * result + json.hashCode()
        result = 31 * result + mag.hashCode()
        result = 31 * result + maxi.hashCode()
        result = 31 * result + rdt.hashCode()
        result = 31 * result + ser.hashCode()
        result = 31 * result + ttl.hashCode()
        return result
    }
}
