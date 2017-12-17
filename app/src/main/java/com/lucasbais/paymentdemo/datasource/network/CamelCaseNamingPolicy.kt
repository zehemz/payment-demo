package com.lucasbais.paymentdemo.datasource.network


import com.google.gson.FieldNamingStrategy
import java.lang.reflect.Field
import java.util.*

class CamelCaseNamingPolicy : FieldNamingStrategy {

    private fun separateCamelCase(name: CharSequence): String {
        val translation = StringBuilder()
        for (i in 0 until name.length) {
            val character = name[i]
            if (Character.isUpperCase(character) && translation.length > 0) {
                translation.append(SEPARATOR)
            }
            translation.append(character)
        }
        return translation.toString()
    }

    override fun translateName(f: Field): String {
        return separateCamelCase(f.name).toLowerCase(Locale.ENGLISH)
    }

    companion object {

        private val SEPARATOR = "_"
    }
}
