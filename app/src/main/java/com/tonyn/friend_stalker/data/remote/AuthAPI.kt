package com.tonyn.friend_stalker.data.remote

import android.util.Log
import com.google.firebase.auth.FirebaseAuth
import com.tonyn.friend_stalker.domain.model.User
import kotlinx.coroutines.tasks.await

class AuthApi(private val firebaseAuth: FirebaseAuth) {
    private val TAG = "AuthApi"

    suspend fun login(email: String, password: String): Result<User> {
        Log.d(TAG, "Login started for email: $email")
        return try {
            val result = firebaseAuth.signInWithEmailAndPassword(email, password).await()
            val user = result.user?.let {
                User(
                    id = it.uid,
                    username = it.email ?: "Unknown"
                )
            }

            if (user != null) {
                Log.i(TAG, "Login successful for email: $email, uid: ${user.id}")
                Result.success(user)
            } else {
                Log.e(TAG, "Login failed: User is null after login for email: $email")
                Result.failure(Exception("User is null after login"))
            }
        } catch (e: Exception) {
            Log.e(TAG, "Login failed for email: $email", e)
            Result.failure(e)
        }
    }
}
