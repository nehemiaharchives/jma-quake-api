import com.github.nehemiaharchives.*
import kotlinx.serialization.encodeToString
import kotlinx.serialization.json.Json
import org.junit.jupiter.api.Test
import kotlin.test.assertEquals
import kotlin.test.assertFalse
import kotlin.test.assertNotNull
import kotlin.test.assertTrue

const val JMA_BOSAI_QUAKE_DATA_LIST_JSON = "jma_bosai_quake_data_list.json"

class JmaQuakeDataTest {

    private val format = Json { prettyPrint = true }

    @Test
    fun encodeCityMaxIntensityTest() {
        val expected = """
                    {
                        "code": "1738400",
                        "maxi": "2"
                    }
        """.trimIndent()

        val cityMaxIntensity = CityMaxIntensity(code = "1738400", maxi = "2")
        val json = format.encodeToString(cityMaxIntensity)
        assertEquals(expected, json)
    }

    @Test
    fun encodePrefectureIntensityArrayTest() {
        val expected = """
                        {
                            "city": [
                                {
                                    "code": "1620900",
                                    "maxi": "1"
                                },
                                {
                                    "code": "1621100",
                                    "maxi": "1"
                                }
                            ],
                            "code": "16",
                            "maxi": "1"
                        }
        """.trimIndent()

        val prefectureIntensityArray = PrefectureIntensityArray(
            city = arrayOf(
                CityMaxIntensity(code = "1620900", maxi = "1"),
                CityMaxIntensity(code = "1621100", maxi = "1")
            ),
            code = "16", maxi = "1",
        )

        val json = format.encodeToString(prefectureIntensityArray)
        assertEquals(expected, json)
    }

    @Test
    fun encodeJmaQuakeDataTest() {

        val expected = """
    {
        "acd": "390",
        "anm": "石川県能登地方",
        "at": "2024-01-03T16:08:00+09:00",
        "cod": "+37.4+136.9-10000/",
        "ctt": "20240103161126",
        "eid": "20240103160815",
        "en_anm": "Noto, Ishikawa Prefecture",
        "en_ttl": "Earthquake and Seismic Intensity Information",
        "ift": "発表",
        "int": [
            {
                "city": [
                    {
                        "code": "1720300",
                        "maxi": "1"
                    },
                    {
                        "code": "1721100",
                        "maxi": "1"
                    }
                ],
                "code": "17",
                "maxi": "3"
            },
            {
                "city": [
                    {
                        "code": "1620900",
                        "maxi": "1"
                    },
                    {
                        "code": "1621100",
                        "maxi": "1"
                    }
                ],
                "code": "16",
                "maxi": "1"
            }
        ],
        "json": "20240103161126_20240103160815_VXSE5k_1.json",
        "mag": "4.2",
        "maxi": "3",
        "rdt": "2024-01-03T16:11:00+09:00",
        "ser": "1",
        "ttl": "震源・震度情報"
    }
        """.trimIndent()


        val prefectureIntensityArray1 = PrefectureIntensityArray(
            city = arrayOf(
                CityMaxIntensity("1720300", "1"),
                CityMaxIntensity("1721100", "1")
            ), code = "17", maxi = "3"
        )

        val prefectureIntensityArray2 = PrefectureIntensityArray(
            city = arrayOf(
                CityMaxIntensity("1620900", "1"),
                CityMaxIntensity("1621100", "1")
            ), code = "16", maxi = "1"
        )

        val jmaQuakeData = JmaQuakeData(
            acd = "390",
            anm = "石川県能登地方",
            at = "2024-01-03T16:08:00+09:00",
            cod = "+37.4+136.9-10000/",
            ctt = "20240103161126",
            eid = "20240103160815",
            enAnm = "Noto, Ishikawa Prefecture",
            enTtl = "Earthquake and Seismic Intensity Information",
            ift = "発表",
            int = arrayOf(prefectureIntensityArray1, prefectureIntensityArray2),
            json = "20240103161126_20240103160815_VXSE5k_1.json",
            mag = "4.2",
            maxi = "3",
            rdt = "2024-01-03T16:11:00+09:00",
            ser = "1",
            ttl = "震源・震度情報"
        )
        val json = format.encodeToString(jmaQuakeData)
        assertEquals(expected, json)
    }

    @Test
    fun encodeJmaQuakeDataSerIntZeroTest() {

        val serIntZero = """
    {
        "acd": "390",
        "anm": "石川県能登地方",
        "at": "2024-01-03T16:08:00+09:00",
        "cod": "+37.4+136.9-10000/",
        "ctt": "20240103161126",
        "eid": "20240103160815",
        "en_anm": "Noto, Ishikawa Prefecture",
        "en_ttl": "Earthquake and Seismic Intensity Information",
        "ift": "発表",
        "int": [
            {
                "city": [
                    {
                        "code": "1720300",
                        "maxi": "1"
                    },
                    {
                        "code": "1721100",
                        "maxi": "1"
                    }
                ],
                "code": "17",
                "maxi": "3"
            },
            {
                "city": [
                    {
                        "code": "1620900",
                        "maxi": "1"
                    },
                    {
                        "code": "1621100",
                        "maxi": "1"
                    }
                ],
                "code": "16",
                "maxi": "1"
            }
        ],
        "json": "20240103161126_20240103160815_VXSE5k_1.json",
        "mag": "4.2",
        "maxi": "3",
        "rdt": "2024-01-03T16:11:00+09:00",
        "ser": 0,
        "ttl": "震源・震度情報"
    }
        """.trimIndent()

        val prefectureIntensityArray1 = PrefectureIntensityArray(
            city = arrayOf(
                CityMaxIntensity("1720300", "1"),
                CityMaxIntensity("1721100", "1")
            ), code = "17", maxi = "3"
        )

        val prefectureIntensityArray2 = PrefectureIntensityArray(
            city = arrayOf(
                CityMaxIntensity("1620900", "1"),
                CityMaxIntensity("1621100", "1")
            ), code = "16", maxi = "1"
        )

        val expected = JmaQuakeData(
            acd = "390",
            anm = "石川県能登地方",
            at = "2024-01-03T16:08:00+09:00",
            cod = "+37.4+136.9-10000/",
            ctt = "20240103161126",
            eid = "20240103160815",
            enAnm = "Noto, Ishikawa Prefecture",
            enTtl = "Earthquake and Seismic Intensity Information",
            ift = "発表",
            int = arrayOf(prefectureIntensityArray1, prefectureIntensityArray2),
            json = "20240103161126_20240103160815_VXSE5k_1.json",
            mag = "4.2",
            maxi = "3",
            rdt = "2024-01-03T16:11:00+09:00",
            ser = "0",
            ttl = "震源・震度情報"
        )

        val data = format.decodeFromString<JmaQuakeData>(serIntZero)
        assertEquals(expected, data)
    }

    @Test
    fun decodeJmaBosaiQuakeDataJsonTest() {
        val json = JmaQuakeDataTest::class.java.getResource("jma_bosai_quake_data_list.json")!!.readText()

        val data = format.decodeFromString<Array<JmaQuakeData>>(json)

        val jmaQuakeDataNoto = data.firstOrNull { jmaQuakeData ->
            jmaQuakeData.eid == "20240101161010" && jmaQuakeData.int.isNotEmpty()
        }
        assertNotNull(jmaQuakeDataNoto)

        val prefectureIntensityArrayIshikawa = jmaQuakeDataNoto.int.firstOrNull { prefectureIntensityArray ->
            prefectureIntensityArray.code == "17"
        }

        assertNotNull(prefectureIntensityArrayIshikawa)

        val shikaMachi = prefectureIntensityArrayIshikawa.city.firstOrNull { cityMaxIntensity ->
            cityMaxIntensity.code == "1738400"
        }

        assertNotNull(shikaMachi)
        assertEquals("7", shikaMachi.maxi)
    }

    @Test
    fun hasCityMaxIntensityTest(){

        val prefectureIntensityArray = PrefectureIntensityArray(
            city = arrayOf(CityMaxIntensity(code = "17", maxi = "3")),
            code = "17", maxi = "3"
        )

        val withMaxIntensity = JmaQuakeData(
            acd = "390",
            anm = "石川県能登地方",
            at = "2024-01-03T16:08:00+09:00",
            cod = "+37.4+136.9-10000/",
            ctt = "20240103161126",
            eid = "20240103160815",
            enAnm = "Noto, Ishikawa Prefecture",
            enTtl = "Earthquake and Seismic Intensity Information",
            ift = "発表",
            int = arrayOf(prefectureIntensityArray),
            json = "20240103161126_20240103160815_VXSE5k_1.json",
            mag = "4.2",
            maxi = "3",
            rdt = "2024-01-03T16:11:00+09:00",
            ser = "0",
            ttl = "震源・震度情報"
        )

        assertTrue(withMaxIntensity.hasCityMaxIntensity())

        val withoutPrefectureIntensityArray = JmaQuakeData(
            acd = "390",
            anm = "石川県能登地方",
            at = "2024-01-03T16:08:00+09:00",
            cod = "+37.4+136.9-10000/",
            ctt = "20240103161126",
            eid = "20240103160815",
            enAnm = "Noto, Ishikawa Prefecture",
            enTtl = "Earthquake and Seismic Intensity Information",
            ift = "発表",
            int = emptyArray(),
            json = "20240103161126_20240103160815_VXSE5k_1.json",
            mag = "4.2",
            maxi = "3",
            rdt = "2024-01-03T16:11:00+09:00",
            ser = "0",
            ttl = "震源・震度情報"
        )
        assertFalse(withoutPrefectureIntensityArray.hasCityMaxIntensity())

        val prefectureIntensityArrayWithoutCityMaxIntensity = PrefectureIntensityArray(
            city = emptyArray(),
            code = "17", maxi = "3"
        )

        val withoutCityMaxIntensity = JmaQuakeData(
            acd = "390",
            anm = "石川県能登地方",
            at = "2024-01-03T16:08:00+09:00",
            cod = "+37.4+136.9-10000/",
            ctt = "20240103161126",
            eid = "20240103160815",
            enAnm = "Noto, Ishikawa Prefecture",
            enTtl = "Earthquake and Seismic Intensity Information",
            ift = "発表",
            int = arrayOf(prefectureIntensityArrayWithoutCityMaxIntensity),
            json = "20240103161126_20240103160815_VXSE5k_1.json",
            mag = "4.2",
            maxi = "3",
            rdt = "2024-01-03T16:11:00+09:00",
            ser = "0",
            ttl = "震源・震度情報"
        )

        assertFalse(withoutCityMaxIntensity.hasCityMaxIntensity())
    }
}