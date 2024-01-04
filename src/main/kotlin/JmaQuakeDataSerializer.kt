package com.github.nehemiaharchives

import kotlinx.serialization.ExperimentalSerializationApi
import kotlinx.serialization.KSerializer
import kotlinx.serialization.descriptors.SerialDescriptor
import kotlinx.serialization.descriptors.buildClassSerialDescriptor
import kotlinx.serialization.descriptors.listSerialDescriptor
import kotlinx.serialization.descriptors.serialDescriptor
import kotlinx.serialization.encoding.*
import kotlinx.serialization.serializer

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
            encodeStringElement(descriptor, 12, value.maxi.notation)
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
                maxi = decodeStringElement(descriptor, 12).toSeismicIntensity(),
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
                maxi = maxi!!.toSeismicIntensity(),
                rdt = rdt!!,
                ser = ser!!,
                ttl = ttl!!,
            )
        }
    }
}