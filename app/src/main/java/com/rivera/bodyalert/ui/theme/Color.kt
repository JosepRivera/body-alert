package com.rivera.bodyalert.ui.theme

import androidx.compose.ui.graphics.Color

// ============================================
// PALETA PROFESIONAL MINIMALISTA
// Enfoque: Limpio, sofisticado, colores con propósito
// ============================================

// Escala de Grises Principal
val Gray50 = Color(0xFFFAFAFA)
val Gray100 = Color(0xFFF5F5F5)
val Gray200 = Color(0xFFEEEEEE)
val Gray300 = Color(0xFFE0E0E0)
val Gray400 = Color(0xFFBDBDBD)
val Gray500 = Color(0xFF9E9E9E)
val Gray600 = Color(0xFF757575)
val Gray700 = Color(0xFF616161)
val Gray800 = Color(0xFF424242)
val Gray900 = Color(0xFF212121)

// Acento Principal - Azul Profesional (para acciones principales únicamente)
val AccentPrimary = Color(0xFF2962FF)        // Azul eléctrico
val AccentSecondary = Color(0xFF448AFF)      // Azul más claro

// Colores de Estado - Solo para feedback
val StatusSuccess = Color(0xFF00C853)        // Verde puro
val StatusWarning = Color(0xFFFFAB00)        // Ámbar
val StatusError = Color(0xFFFF1744)          // Rojo brillante
val StatusInfo = Color(0xFF2979FF)           // Azul info

// Fondos de Estado (muy sutiles)
val SuccessBackground = Color(0xFFF1F8F4)
val WarningBackground = Color(0xFFFFF8E8)
val ErrorBackground = Color(0xFFFFF1F0)
val InfoBackground = Color(0xFFF0F4FF)

// Gradiente para gráficos - Azul Icebreaker
val ChartGradientStart = Color(0xFF1A237E)   // Azul marino profundo
val ChartGradientMid = Color(0xFF283593)     // Azul índigo
val ChartGradientEnd = Color(0xFF3949AB)     // Azul índigo claro

// ============================================
// TEMA CLARO (Único)
// ============================================
val Background = Gray50
val Surface = Color.White
val SurfaceVariant = Gray100

val Primary = AccentPrimary
val PrimaryVariant = AccentSecondary
val Secondary = Gray700

val OnPrimary = Color.White
val OnSecondary = Color.White
val OnBackground = Gray900
val OnSurface = Gray800
val OnSurfaceVariant = Gray600

val Success = StatusSuccess
val Warning = StatusWarning
val Error = StatusError
val Info = StatusInfo

// Colores para gráficos - Solo para visualizaciones
val ChartPrimary = ChartGradientStart
val ChartSecondary = ChartGradientMid
val ChartAccent = ChartGradientEnd
val ChartNeutral = Gray400

// Bordes y Separadores
val Divider = Gray300
val Border = Gray200