package io.vertx.ext.arango.codegen.readers

import io.vertx.ext.arango.codegen.model.Clazz
import io.vertx.ext.arango.codegen.model.Data
import io.vertx.ext.arango.codegen.model.Field
import java.io.File
import java.io.FileInputStream
import java.io.InputStreamReader

/**
 * @author Konstantin Volivach
 */
class ClazzReader {

    /**
     * Read a clazz from file for next generation
     */
    fun read(file: File): Clazz {
        return InputStreamReader(FileInputStream(file)).use {
            val lines = it.readLines().filter { it.isNotBlank() }
            val nameLine = lines[0]
            val name = nameLine.substring(nameLine.indexOf("name") + 5).trim()

            val fields = mutableListOf<Field>()
            val fieldsLines = lines.subList(2, lines.size - 1)
            fieldsLines.forEach {
                val trimedline = it.trim()
                val type = trimedline.substring(0, trimedline.indexOf(" "))
                val fieldName = trimedline.substring(trimedline.indexOf(" ") + 1)
                fields.add(Field(type, fieldName))
            }
            Clazz(name, Data(fields))
        }
    }
}