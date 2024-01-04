package com.github.nehemiaharchives

import kotlinx.serialization.Serializable
import kotlinx.serialization.json.Json

@Serializable
enum class Language {
    burmese,
    chinese_zs,
    chinese_zt,
    english,
    indonesian,
    japanese,
    khmer,
    korean,
    mongolian,
    nepali,
    portuguese,
    spanish,
    tagalog,
    thai,
    vietnamese,
}

/**
 * [ref](https://www.data.jma.go.jp/multi/data/dictionary/pref.json)
 * */
class JmaPrefDictionary(json: String) {

    private val deserializedMap: Map<String, Map<Language, String>> = Json.decodeFromString(json)

    fun getPrefName(code: String, language: Language): String? {
        if (deserializedMap.containsKey(code)) {
            val names = deserializedMap[code]
            if (names != null && names.contains(language)) {
                val name = names[language]
                if (name != null) {
                    return name
                }
            }
        }
        return null
    }
}

/**
 * [ref](https://www.data.jma.go.jp/multi/data/dictionary/epi.json)
 * */
class JmaEpiDictionary(json: String){

    private val deserializedMap: Map<String, Map<Language, String>> = Json.decodeFromString(json)

    fun getEpiName(code: String, language: Language): String? {
        if (deserializedMap.containsKey(code)) {
            val names = deserializedMap[code]
            if (names != null && names.contains(language)) {
                val name = names[language]
                if (name != null) {
                    return name
                }
            }
        }
        return null
    }
}

/**
 * [ref](https://www.data.jma.go.jp/multi/data/dictionary/city.json)
 * */
class JmaCityDictionary(json: String){

    private val deserializedMap: Map<String, Map<Language, String>> = Json.decodeFromString(json)

    fun getCityName(code: String, language: Language): String? {
        if (deserializedMap.containsKey(code)) {
            val names = deserializedMap[code]
            if (names != null && names.contains(language)) {
                val name = names[language]
                if (name != null) {
                    return name
                }
            }
        }
        return null
    }
}