package com.example.datastore

import androidx.datastore.core.Serializer
import kotlinx.serialization.SerializationException
import kotlinx.serialization.json.Json
import java.io.InputStream
import java.io.OutputStream

object AppSettingSerializer : Serializer<AppSetting> {

    override val defaultValue: AppSetting
        get() = AppSetting()

    override suspend fun readFrom(input: InputStream): AppSetting {
        return try {
            Json.decodeFromString(
                deserializer = AppSetting.serializer(),
                string = input.readBytes().decodeToString()
            )
        }catch (e: SerializationException){
            e.printStackTrace()
            defaultValue
        }
    }

    override suspend fun writeTo(t: AppSetting, output: OutputStream) {
        output.write(
            Json.encodeToString(
                serializer = AppSetting.serializer(),
                value = t
            ).encodeToByteArray()
        )
    }
}