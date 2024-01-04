# jma-quake-api

jma-quake-api parses [earthquake data json](https://www.jma.go.jp/bosai/quake/data/list.json) provided by Japan Meteorological Agency into Kotlin data class `Array` of [`JmaQuakeData`](https://github.com/nehemiaharchives/jma-quake-api/blob/master/src/main/kotlin/JmaQuakeData.kt).

```kotlin
val json: String = /* http request get https://www.jma.go.jp/bosai/quake/data/list.json */
val jmaQuakeDataArray = Json.decodeFromString<Array<JmaQuakeData>>(json)
```
