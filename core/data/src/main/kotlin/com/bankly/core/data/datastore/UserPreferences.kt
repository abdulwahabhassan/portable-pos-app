package com.bankly.core.data.datastore

import android.content.Context
import androidx.datastore.core.CorruptionException
import androidx.datastore.core.Serializer
import androidx.datastore.core.handlers.ReplaceFileCorruptionHandler
import androidx.datastore.dataStore
import com.bankly.core.common.di.IODispatcher
import dagger.hilt.android.qualifiers.ApplicationContext
import java.io.InputStream
import java.io.OutputStream
import javax.inject.Inject
import javax.inject.Singleton
import kotlinx.coroutines.CoroutineDispatcher
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.first
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext
import kotlinx.serialization.Serializable
import kotlinx.serialization.SerializationException
import kotlinx.serialization.json.Json

@Serializable
data class UserPreferences(
    val token: String = "",
    val shouldShowWalletBalance: Boolean = false
)

private object UserPreferencesSerializer : Serializer<UserPreferences> {
    override val defaultValue: UserPreferences
        get() = UserPreferences()

    override suspend fun readFrom(input: InputStream): UserPreferences {
        try {
            return Json.decodeFromString(
                UserPreferences.serializer(),
                input.readBytes().decodeToString(),
            )
        } catch (serialization: SerializationException) {
            throw CorruptionException("Unable to read UserPrefs", serialization)
        }
    }

    override suspend fun writeTo(t: UserPreferences, output: OutputStream) {
        withContext(Dispatchers.IO) {
            output.write(
                Json.encodeToString(UserPreferences.serializer(), t).encodeToByteArray(),
            )
        }
    }
}

@Singleton
class UserPreferencesDataStore @Inject constructor(
    @ApplicationContext private val context: Context,
    private val scope: CoroutineScope,
    @IODispatcher private val dispatcher: CoroutineDispatcher,
) {

    private val Context.userPrefDataStore by dataStore(
        fileName = "user_pref.pb",
        serializer = UserPreferencesSerializer,
        scope = scope,
        corruptionHandler = ReplaceFileCorruptionHandler { UserPreferences() },
    )

    suspend fun update(updater: UserPreferences.() -> UserPreferences) {
        scope.launch(dispatcher) { context.userPrefDataStore.updateData { updater(it) } }.join()
    }

    suspend fun data(): UserPreferences =
        withContext(dispatcher) { context.userPrefDataStore.data.first() }
}
