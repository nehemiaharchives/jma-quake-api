import com.github.nehemiaharchives.JmaCityDictionary
import com.github.nehemiaharchives.JmaEpiDictionary
import com.github.nehemiaharchives.JmaPrefDictionary
import com.github.nehemiaharchives.Language
import org.junit.jupiter.api.Test
import kotlin.test.assertEquals

class JmaCityTest {

    private val clazz = JmaCityTest::class.java

    @Test
    fun deserializeJmaPrefTest() {
        val json = clazz.getResource("jma_multi_data_dictionary_pref.json").readText()
        val prefDictionary = JmaPrefDictionary(json)
        assertEquals("石川県", prefDictionary.getPrefName("17", Language.japanese))
    }

    @Test
    fun deserializeJmaEpiTest(){
        val json = clazz.getResource("jma_multi_data_dictionary_epi.json").readText()
        val epiDictionary = JmaEpiDictionary(json)
        assertEquals("石川県能登地方", epiDictionary.getEpiName("390", Language.japanese))
    }

    @Test
    fun deserializeJmaCityTest() {
        val json = clazz.getResource("jma_multi_data_dictionary_city.json").readText()
        val cityDictionary = JmaCityDictionary(json)
        assertEquals("志賀町", cityDictionary.getCityName("1738400", Language.japanese))
    }
}