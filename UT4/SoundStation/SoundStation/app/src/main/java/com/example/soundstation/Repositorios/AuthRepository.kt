package com.example.soundstation.Repositorios

import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.auth.FirebaseUser
import com.google.firebase.auth.UserProfileChangeRequest
import kotlinx.coroutines.tasks.await // Permite usar await() para esperar la ejecución de las tareas asincrónicas

class AuthRepository {

    // Instancia de FirebaseAuth para gestionar las operaciones de autenticación
    private val auth: FirebaseAuth = FirebaseAuth.getInstance()

    // Registrar usuario con email y contraseña
    suspend fun registrarUsuario(email: String, password: String, username: String): Boolean {
        return try {
            // Intentamos crear un nuevo usuario con el email y la contraseña proporcionados
            val result = auth.createUserWithEmailAndPassword(email, password).await()
            val user = result.user

            // Configuración del nombre de usuario (displayName) del usuario recién creado
            val profileUpdates = UserProfileChangeRequest.Builder()
                .setDisplayName(username) // Establece el nombre de usuario proporcionado
                .build()
            user?.updateProfile(profileUpdates)?.await() // Actualiza el perfil del usuario con el nombre de usuario

            true // Devuelvo true si el registro es exitoso
        } catch (e: Exception) {
            false // Devuelvo false si hay algún error
        }
    }

    // Iniciar sesión con email y contraseña
    suspend fun loginUsuario(email: String, password: String): FirebaseUser? {
        return try {
            // Intento iniciar sesión con el email y la contraseña proporcionados
            val result = auth.signInWithEmailAndPassword(email, password).await()
            result.user // Devuelvo el usuario si la autenticación es exitosa
        } catch (e: Exception) {
            null // Devuelvo null si ocurre un error al intentar iniciar sesión
        }
    }

    // Cerrar sesión
    fun signOut() {
        auth.signOut() // Llamo al método de FirebaseAuth para cerrar la sesión del usuario actual
    }

    // Obtener el usuario actual
    fun getCurrentUser(): FirebaseUser? {
        return auth.currentUser // Devuelvo el usuario actualmente autenticado o null si no hay ninguno
    }
}
