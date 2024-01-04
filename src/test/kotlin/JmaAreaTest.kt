import com.github.nehemiaharchives.JmaArea
import kotlinx.serialization.json.Json
import org.junit.jupiter.api.Test

const val JMA_BOSAI_COMMON_CONST_AREA_JSON = "jma_bosai_common_const_area.json"

class JmaAreaTest {

    private val format = Json { prettyPrint = true }

    @Test
    fun decodeJmaAreaTest() {
        val json = JmaAreaTest::class.java.getResource(JMA_BOSAI_COMMON_CONST_AREA_JSON).readText()
        format.decodeFromString<JmaArea>(json)
    }
}