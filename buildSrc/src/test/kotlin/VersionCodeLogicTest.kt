import org.junit.Assert.assertEquals
import org.junit.Test

class VersionCodeLogicTest {

    @Test
    fun `parses version name 100-0000`() {
        val versionCode = "1.0.0-0000".toVersionCode()
        assertEquals(100000000, versionCode)
    }

    @Test
    fun `parses version name 1110`() {
        val versionCode = "1.11.0".toVersionCode()
        assertEquals(111000000, versionCode)
    }

    @Test
    fun `parses version name 99999-9999`() {
        val versionCode = "9.99.99-9999".toVersionCode()
        assertEquals(999999999, versionCode)
    }

    @Test
    fun `parses version name 21234-16`() {
        val versionCode = "2.12.34-16".toVersionCode()
        assertEquals(212340016, versionCode)
    }
}
