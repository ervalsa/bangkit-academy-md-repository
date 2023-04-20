package com.palsaloid.storydicoding.data.local.datastore

import androidx.datastore.preferences.core.Preferences
import androidx.datastore.core.DataStore
import androidx.datastore.preferences.core.booleanPreferencesKey
import androidx.datastore.preferences.core.edit
import androidx.datastore.preferences.core.stringPreferencesKey
import com.palsaloid.storydicoding.domain.model.User
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.map

class UserPreference private constructor(private val datastore: DataStore<Preferences>) {

    fun getUser(): Flow<User> {
        return datastore.data.map { preferences ->
            User(
                preferences[NAME_KEY] ?: "",
                preferences[TOKEN_KEY] ?: "",
                preferences[LOGIN_STATE_KEY] ?: false
            )
        }
    }

    suspend fun saveUser(user: User) {
        datastore.edit { preferences ->
            preferences[NAME_KEY] = user.name
            preferences[TOKEN_KEY] = user.token
            preferences[LOGIN_STATE_KEY] = user.isLogin
        }
    }

    suspend fun login() {
        datastore.edit { preferences ->
            preferences[LOGIN_STATE_KEY] = true
        }
    }

    suspend fun logout() {
        datastore.edit { preferences ->
            preferences[LOGIN_STATE_KEY] = false
            preferences.remove(LOGIN_STATE_KEY)
        }
    }

    companion object {
        @Volatile
        private var INSTANCE: UserPreference? = null

        private val NAME_KEY = stringPreferencesKey("name")
        private val TOKEN_KEY = stringPreferencesKey("token")
        private val LOGIN_STATE_KEY = booleanPreferencesKey("login_state")

        fun getInstance(datastore: DataStore<Preferences>): UserPreference {
            return INSTANCE ?: synchronized(this) {
                val instance = UserPreference(datastore)
                INSTANCE = instance
                instance
            }
        }
    }
}