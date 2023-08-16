package com.bankly.core.data.datastore

import android.content.Context
import androidx.datastore.core.handlers.ReplaceFileCorruptionHandler
import androidx.datastore.dataStore
import com.bankly.core.data.di.IODispatcher
import dagger.hilt.android.qualifiers.ApplicationContext
import javax.inject.Inject
import javax.inject.Singleton
import kotlinx.coroutines.CoroutineDispatcher
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.flow.first
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext

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
