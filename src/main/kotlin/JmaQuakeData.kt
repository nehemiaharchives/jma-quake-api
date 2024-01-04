package com.github.nehemiaharchives

import kotlinx.serialization.*
import kotlinx.serialization.descriptors.SerialDescriptor
import kotlinx.serialization.descriptors.buildClassSerialDescriptor
import kotlinx.serialization.descriptors.listSerialDescriptor
import kotlinx.serialization.descriptors.serialDescriptor
import kotlinx.serialization.encoding.*

object CityMaxIntensitySerializer : KSerializer<CityMaxIntensity> {
    override val descriptor: SerialDescriptor = buildClassSerialDescriptor("CityMaxIntensity") {
        element("code", serialDescriptor<String>())
        element("maxi", serialDescriptor<String>())
    }

    override fun serialize(encoder: Encoder, value: CityMaxIntensity) {
        encoder.encodeStructure(descriptor) {
            encodeStringElement(descriptor, 0, value.code)
            encodeStringElement(descriptor, 1, value.maxi.notation)
        }
    }

    override fun deserialize(decoder: Decoder): CityMaxIntensity = decoder.decodeStructure(descriptor) {
        if (decodeSequentially()) {
            CityMaxIntensity(
                code = decodeStringElement(descriptor, 0),
                maxi = decodeStringElement(descriptor, 1).toSeismicIntensity()
            )
        } else {
            var code: String? = null
            var maxi: String? = null
            while (true) {
                when (val index = decodeElementIndex(descriptor)) {
                    0 -> code = decodeStringElement(descriptor, index)
                    1 -> maxi = decodeStringElement(descriptor, index)
                    CompositeDecoder.DECODE_DONE -> break
                    else -> error("Unexpected index: $index")
                }
            }
            CityMaxIntensity(code!!, maxi!!.toSeismicIntensity())
        }
    }
}

@Serializable
enum class SeismicIntensity(val level: Int, val notation: String, val japanese: String) {
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

fun String.toSeismicIntensity() = SeismicIntensity.entries.firstOrNull { it.notation == this }?: throw IllegalArgumentException("Unknown SeismicIntensity: $this")

@Serializable(with = CityMaxIntensitySerializer::class)
data class CityMaxIntensity(val code: String, val maxi: SeismicIntensity)

@Serializable
data class PrefectureIntensityArray(val city: Array<CityMaxIntensity>, val code: String, val maxi: String) {

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

@ExperimentalSerializationApi
object JmaQuakeDataSerializer : KSerializer<JmaQuakeData> {
    override val descriptor: SerialDescriptor = buildClassSerialDescriptor("JmaQuakeData") {
        arrayOf("acd", "anm", "at", "cod", "ctt", "eid", "en_anm", "en_ttl", "ift").forEach {
            element(it, serialDescriptor<String>())
        }
        element("int", listSerialDescriptor<PrefectureIntensityArray>())
        arrayOf("json", "mag", "maxi", "rdt").forEach {
            element(it, serialDescriptor<String>())
        }
        element("ser", serialDescriptor<String>())
        element("ttl", serialDescriptor<String>())
    }

    override fun serialize(encoder: Encoder, value: JmaQuakeData) {
        encoder.encodeStructure(descriptor) {
            encodeStringElement(descriptor, 0, value.acd)
            encodeStringElement(descriptor, 1, value.anm)
            encodeStringElement(descriptor, 2, value.at)
            encodeStringElement(descriptor, 3, value.cod)
            encodeStringElement(descriptor, 4, value.ctt)
            encodeStringElement(descriptor, 5, value.eid)
            encodeStringElement(descriptor, 6, value.enAnm)
            encodeStringElement(descriptor, 7, value.enTtl)
            encodeStringElement(descriptor, 8, value.ift)
            encodeSerializableElement(descriptor, 9, serializer<Array<PrefectureIntensityArray>>(), value.int)
            encodeStringElement(descriptor, 10, value.json)
            encodeStringElement(descriptor, 11, value.mag)
            encodeStringElement(descriptor, 12, value.maxi)
            encodeStringElement(descriptor, 13, value.rdt)
            encodeStringElement(descriptor, 14, value.ser)
            encodeStringElement(descriptor, 15, value.ttl)
        }
    }

    override fun deserialize(decoder: Decoder): JmaQuakeData = decoder.decodeStructure(descriptor) {
        if (decodeSequentially()) {
            JmaQuakeData(
                acd = decodeStringElement(descriptor, 0),
                anm = decodeStringElement(descriptor, 1),
                at = decodeStringElement(descriptor, 2),
                cod = decodeStringElement(descriptor, 3),
                ctt = decodeStringElement(descriptor, 4),
                eid = decodeStringElement(descriptor, 5),
                enAnm = decodeStringElement(descriptor, 6),
                enTtl = decodeStringElement(descriptor, 7),
                ift = decodeStringElement(descriptor, 8),
                int = decodeSerializableElement(descriptor, 9, serializer<Array<PrefectureIntensityArray>>()),
                json = decodeStringElement(descriptor, 10),
                mag = decodeStringElement(descriptor, 11),
                maxi = decodeStringElement(descriptor, 12),
                rdt = decodeStringElement(descriptor, 13),
                ser = decodeStringElement(descriptor, 14),
                ttl = decodeStringElement(descriptor, 15)
            )
        } else {

            var acd: String? = null
            var anm: String? = null
            var at: String? = null
            var cod: String? = null
            var ctt: String? = null
            var eid: String? = null
            var enAnm: String? = null
            var enTtl: String? = null
            var ift: String? = null
            var int: Array<PrefectureIntensityArray>? = null
            var json: String? = null
            var mag: String? = null
            var maxi: String? = null
            var rdt: String? = null
            var ser: String? = null
            var ttl: String? = null

            while (true) {
                when (val index = decodeElementIndex(descriptor)) {
                    0 -> acd = decodeStringElement(descriptor, 0)
                    1 -> anm = decodeStringElement(descriptor, 1)
                    2 -> at = decodeStringElement(descriptor, 2)
                    3 -> cod = decodeStringElement(descriptor, 3)
                    4 -> ctt = decodeStringElement(descriptor, 4)
                    5 -> eid = decodeStringElement(descriptor, 5)
                    6 -> enAnm = decodeStringElement(descriptor, 6)
                    7 -> enTtl = decodeStringElement(descriptor, 7)
                    8 -> ift = decodeStringElement(descriptor, 8)
                    9 -> int = decodeSerializableElement(descriptor, 9, serializer<Array<PrefectureIntensityArray>>())
                    10 -> json = decodeStringElement(descriptor, 10)
                    11 -> mag = decodeStringElement(descriptor, 11)
                    12 -> maxi = decodeStringElement(descriptor, 12)
                    13 -> rdt = decodeStringElement(descriptor, 13)
                    14 -> ser = try {
                        decodeStringElement(descriptor, 14)
                    } catch (e: Exception) {
                        /*e.printStackTrace() ignore this case */
                        "0"
                    }

                    15 -> ttl = decodeStringElement(descriptor, 15)
                    CompositeDecoder.DECODE_DONE -> break
                    else -> error("Unexpected index: $index")
                }
            }

            JmaQuakeData(
                acd = acd!!,
                anm = anm!!,
                at = at!!,
                cod = cod!!,
                ctt = ctt!!,
                eid = eid!!,
                enAnm = enAnm!!,
                enTtl = enTtl!!,
                ift = ift!!,
                int = int!!,
                json = json!!,
                mag = mag!!,
                maxi = maxi!!,
                rdt = rdt!!,
                ser = ser!!,
                ttl = ttl!!,
            )
        }
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
    val maxi: String,
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
