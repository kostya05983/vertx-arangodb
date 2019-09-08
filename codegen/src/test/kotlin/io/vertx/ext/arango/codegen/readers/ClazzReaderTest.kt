package io.vertx.ext.arango.codegen.readers

import org.junit.jupiter.api.Assertions.*
import org.junit.jupiter.api.Test
import java.io.File

/**
 * @author Konstantin Volivach
 */
internal class ClazzReaderTest {

    @Test
    fun testRead() {
        val clazzReader = ClazzReader()
        val file = File(javaClass.getResource("/testData.txt").path)
        val clazz = clazzReader.read(file)
        assertEquals("DataObject", clazz.name)
        assertEquals(6, clazz.data.fields.size)
        assertEquals("boolean", clazz.data.fields[0].type)
        assertEquals("waitForSync", clazz.data.fields[0].name)
        assertEquals("boolean", clazz.data.fields[1].type)
        assertEquals("ignoreRevs", clazz.data.fields[1].name)
        assertEquals("string", clazz.data.fields[2].type)
        assertEquals("ifMatch", clazz.data.fields[2].name)
        assertEquals("boolean", clazz.data.fields[3].type)
        assertEquals("returnNew", clazz.data.fields[3].name)
        assertEquals("boolean", clazz.data.fields[4].type)
        assertEquals("returnOld", clazz.data.fields[4].name)
        assertEquals("boolean", clazz.data.fields[5].type)
        assertEquals("silent", clazz.data.fields[5].name)
    }
}