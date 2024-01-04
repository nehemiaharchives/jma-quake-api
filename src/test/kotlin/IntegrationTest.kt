import com.github.nehemiaharchives.CityMaxIntensity
import com.github.nehemiaharchives.JmaArea
import com.github.nehemiaharchives.JmaQuakeData
import kotlinx.serialization.json.Json
import org.junit.jupiter.api.Test

class IntegrationTest {

    @Test
    fun test() {
        val clazz = IntegrationTest::class.java
        val jmaArea = Json.decodeFromString<JmaArea>(clazz.getResource(JMA_BOSAI_COMMON_CONST_AREA_JSON).readText())
        val jmaQuakeDataArray =
            Json.decodeFromString<Array<JmaQuakeData>>(clazz.getResource(JMA_BOSAI_QUAKE_DATA_LIST_JSON).readText())

        val jmaQuakeDataWithInt7 = jmaQuakeDataArray.first { jmaQuakeData ->
            jmaQuakeData.hasCityMaxIntensity() && jmaQuakeData.maxi == "7"
        }

        val cityMaxIntensityList = mutableListOf<CityMaxIntensity>()

        jmaQuakeDataWithInt7.int.forEach { prefectureIntensityArray ->
            prefectureIntensityArray.city.forEach { cityMaxIntensity ->
                cityMaxIntensityList.add(cityMaxIntensity)
            }
        }


        cityMaxIntensityList.forEach { city ->
            val area = jmaArea.class20s[city.code]
            println("${city.maxi} $area")
        }
    }
}