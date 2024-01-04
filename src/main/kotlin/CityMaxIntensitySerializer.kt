package com.github.nehemiaharchives

import kotlinx.serialization.KSerializer
import kotlinx.serialization.descriptors.SerialDescriptor
import kotlinx.serialization.descriptors.buildClassSerialDescriptor
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