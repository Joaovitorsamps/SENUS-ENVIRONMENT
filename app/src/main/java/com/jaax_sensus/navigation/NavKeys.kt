package com.jaax_sensus.navigation

import androidx.navigation3.runtime.NavKey
import kotlinx.serialization.Serializable

/**
 * Navigation routes for SENSUS app using Jetpack Navigation 3.
 */
@Serializable
sealed interface AppRoute : NavKey {
    @Serializable
    data object Emocoes : AppRoute

    @Serializable
    data object Diario : AppRoute

    @Serializable
    data object Dados : AppRoute

    @Serializable
    data object EditarPerfil : AppRoute
}

typealias Emocoes = AppRoute.Emocoes
typealias Diario = AppRoute.Diario
typealias Dados = AppRoute.Dados
typealias EditarPerfil = AppRoute.EditarPerfil

