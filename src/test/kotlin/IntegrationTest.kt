import com.github.nehemiaharchives.*
import kotlinx.serialization.json.Json
import org.junit.jupiter.api.Test
import kotlin.test.assertEquals

class IntegrationTest {

    @Test
    fun test() {
        val clazz = IntegrationTest::class.java

        val jmaQuakeDataArray =
            Json.decodeFromString<Array<JmaQuakeData>>(clazz.getResource(JMA_BOSAI_QUAKE_DATA_LIST_JSON).readText())
        val jmaCityDictionary = JmaCityDictionary(clazz.getResource("jma_multi_data_dictionary_city.json").readText())

        val jmaQuakeDataWithInt7 = jmaQuakeDataArray.first { jmaQuakeData ->
            jmaQuakeData.hasCityMaxIntensity() && jmaQuakeData.maxi == "7"
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
    }
}