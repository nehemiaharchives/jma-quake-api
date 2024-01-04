# jma-quake-api [![](https://jitpack.io/v/nehemiaharchives/jma-quake-api.svg)](https://jitpack.io/#nehemiaharchives/jma-quake-api)

jma-quake-api parses [earthquake data json](https://www.jma.go.jp/bosai/quake/data/list.json) provided by Japan Meteorological Agency into Kotlin data class `Array` of [`JmaQuakeData`](https://github.com/nehemiaharchives/jma-quake-api/blob/master/src/main/kotlin/JmaQuakeData.kt).

```kotlin
val json: String = /* http request get https://www.jma.go.jp/bosai/quake/data/list.json */
val jmaQuakeDataArray = Json.decodeFromString<Array<JmaQuakeData>>(json)
```

It can also parse names of cities from [city name dictionary json](https://www.data.jma.go.jp/multi/data/dictionary/city.json)
```kotlin
val json: String = /* http request get https://www.data.jma.go.jp/multi/data/dictionary/city.json */
val cityDictionary = JmaCityDictionary(json)
assertEquals("志賀町", cityDictionary.getCityName("1738400", Language.japanese))
```

Then you can do something like following:
```kotlin
val jmaQuakeDataWithInt7 = jmaQuakeDataArray.first { jmaQuakeData ->
    jmaQuakeData.hasCityMaxIntensity() && jmaQuakeData.maxi == SeismicIntensity.SEVEN
}

val cityMaxIntensityList = mutableListOf<CityMaxIntensity>()

jmaQuakeDataWithInt7.int.forEach { prefectureIntensityArray ->
    prefectureIntensityArray.city.forEach { cityMaxIntensity ->
        cityMaxIntensityList.add(cityMaxIntensity)
    }
}

val city = cityMaxIntensityList.first { it.maxi.greaterThanEqual(SeismicIntensity.SEVEN) }
val name = jmaCityDictionary.getCityName(city.code, Language.japanese)
assertEquals("志賀町", name)
```

## Installation
Add following to your root build.gradle
```groovy
dependencyResolutionManagement {
    repositoriesMode.set(RepositoriesMode.FAIL_ON_PROJECT_REPOS)
    repositories {
        mavenCentral()
        maven { url 'https://jitpack.io' }
    }
}
```
Add dependency
```groovy
dependencies {
    implementation 'com.github.nehemiaharchives:jma-quake-api:1.0.0'
}
```
