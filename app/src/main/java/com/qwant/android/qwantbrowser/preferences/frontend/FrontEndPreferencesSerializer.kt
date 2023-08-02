package com.qwant.android.qwantbrowser.preferences.frontend

import androidx.datastore.core.CorruptionException
import androidx.datastore.core.Serializer
import com.google.protobuf.InvalidProtocolBufferException
import java.io.InputStream
import java.io.OutputStream


object FrontEndPreferencesSerializer : Serializer<FrontEndPreferences> {
    override val defaultValue: FrontEndPreferences = FrontEndPreferences.getDefaultInstance()
        .toBuilder()
        .build()

    override suspend fun readFrom(input: InputStream): FrontEndPreferences {
        try {
            return FrontEndPreferences.parseFrom(input)
        } catch (exception: InvalidProtocolBufferException) {
            throw CorruptionException("Cannot read protobuf.", exception)
        }
    }

    override suspend fun writeTo(t: FrontEndPreferences, output: OutputStream) = t.writeTo(output)
}