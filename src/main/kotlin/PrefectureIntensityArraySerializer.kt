package com.github.nehemiaharchives

import kotlinx.serialization.KSerializer
import kotlinx.serialization.SerializationException
import kotlinx.serialization.descriptors.SerialDescriptor
import kotlinx.serialization.descriptors.buildClassSerialDescriptor
import kotlinx.serialization.descriptors.serialDescriptor
import kotlinx.serialization.encoding.*
import kotlinx.serialization.serializer

object PrefectureIntensityArraySerializer : KSerializer<PrefectureIntensityArray> {
    override val descriptor: SerialDescriptor = buildClassSerialDescriptor("PrefectureIntensityArray") {
        element("city", serialDescriptor<Array<CityMaxIntensity>>())
        element("code", serialDescriptor<String>())
        element("maxi", serialDescriptor<String>())
    }

    override fun serialize(encoder: Encoder, value: PrefectureIntensityArray) {
        encoder.encodeStructure(descriptor) {
            encodeSerializableElement(descriptor, 0, serializer<Array<CityMaxIntensity>>(), value.city)
            encodeStringElement(descriptor, 1, value.code)
            encodeStringElement(descriptor, 2, value.maxi.notation)
        }
    }

    override fun deserialize(decoder: Decoder): PrefectureIntensityArray = decoder.decodeStructure(descriptor) {
        if (decodeSequentially()) {
            PrefectureIntensityArray(
                decodeSerializableElement(descriptor, 0, serializer<Array<CityMaxIntensity>>()),
                decodeStringElement(descriptor, 1),
                decodeStringElement(descriptor, 2).toSeismicIntensity()
            )
        } else {
            var city: Array<CityMaxIntensity>? = null
            var code: String? = null
            var maxi: String? = null

            while (true) {
                when (val index = decodeElementIndex(descriptor)) {
                    0 -> city = decodeSerializableElement(descriptor, 0, serializer<Array<CityMaxIntensity>>())
                    1 -> code = decodeStringElement(descriptor, 1)
                    2 -> maxi = decodeStringElement(descriptor, 2)
                    CompositeDecoder.DECODE_DONE -> break
                    else -> throw SerializationException("Unexpected index $index")
                }
            }
            PrefectureIntensityArray(
                city = city!!,
                code = code!!,
                maxi = maxi!!.toSeismicIntensity()
            )
        }
    }
}