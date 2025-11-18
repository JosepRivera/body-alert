package com.rivera.bodyalert.ui.screens.dashboard

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.automirrored.filled.TrendingUp
import androidx.compose.material.icons.automirrored.outlined.BluetoothSearching
import androidx.compose.material.icons.filled.*
import androidx.compose.material.icons.outlined.*
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Brush
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.rivera.bodyalert.ui.theme.*

@Composable
fun DashboardScreen(
    onNavigateToAuthenticate: () -> Unit = {},
    onNavigateToHistory: () -> Unit = {},
    onNavigateToSettings: () -> Unit = {},
    onNavigateToProfile: () -> Unit = {}
) {
    var selectedBottomItem by remember { mutableStateOf(0) }
    var isDeviceConnected by remember { mutableStateOf(false) }

    Scaffold(
        topBar = {
            DashboardTopBar(
                onNotificationClick = { /* Handle notifications */ }
            )
        },
        bottomBar = {
            DashboardBottomBar(
                selectedItem = selectedBottomItem,
                onItemSelected = { index ->
                    selectedBottomItem = index
                    when (index) {
                        1 -> onNavigateToHistory()
                        2 -> onNavigateToSettings()
                        3 -> onNavigateToProfile()
                    }
                }
            )
        },
        floatingActionButton = {
            FloatingActionButton(
                onClick = onNavigateToAuthenticate,
                containerColor = if (isDeviceConnected) Success else Primary,
                contentColor = Color.White,
                shape = RoundedCornerShape(16.dp)
            ) {
                Icon(
                    imageVector = if (isDeviceConnected) Icons.Filled.Bluetooth else Icons.AutoMirrored.Outlined.BluetoothSearching,
                    contentDescription = "Conectar dispositivo",
                    modifier = Modifier.size(24.dp)
                )
            }
        },
        floatingActionButtonPosition = FabPosition.End,
        containerColor = Background
    ) { padding ->
        LazyColumn(
            modifier = Modifier
                .fillMaxSize()
                .padding(padding),
            contentPadding = PaddingValues(horizontal = 16.dp, vertical = 20.dp),
            verticalArrangement = Arrangement.spacedBy(20.dp)
        ) {
            // Estado de conexión
            item {
                ConnectionStatusCompact(isConnected = isDeviceConnected)
            }

            // Métricas principales
            item {
                MetricsGridSection()
            }

            // Gráfico con degradado azul
            item {
                PostureAnalysisChart()
            }

            // Insights
            item {
                InsightsSection()
            }

            // Timeline de actividad
            item {
                ActivityTimelineCard()
            }

            item {
                Spacer(modifier = Modifier.height(80.dp))
            }
        }
    }
}

@Composable
fun DashboardTopBar(
    onNotificationClick: () -> Unit = {}
) {
    Surface(
        modifier = Modifier
            .fillMaxWidth()
            .statusBarsPadding(),
        color = Surface
    ) {
        Row(
            modifier = Modifier
                .fillMaxWidth()
                .padding(horizontal = 20.dp, vertical = 12.dp),
            horizontalArrangement = Arrangement.SpaceBetween,
            verticalAlignment = Alignment.CenterVertically
        ) {
            // Logo minimalista
            Box(
                modifier = Modifier
                    .size(40.dp)
                    .clip(RoundedCornerShape(10.dp))
                    .background(Primary.copy(alpha = 0.1f)),
                contentAlignment = Alignment.Center
            ) {
                Text(
                    text = "BA",
                    style = MaterialTheme.typography.titleMedium,
                    fontWeight = FontWeight.ExtraBold,
                    color = Primary,
                    letterSpacing = (-1).sp
                )
            }

            // Notificaciones
            IconButton(
                onClick = onNotificationClick,
                modifier = Modifier.size(40.dp)
            ) {
                BadgedBox(
                    badge = {
                        Badge(
                            containerColor = Error,
                            contentColor = Color.White
                        ) {
                            Text("2", fontSize = 10.sp, fontWeight = FontWeight.Bold)
                        }
                    }
                ) {
                    Icon(
                        imageVector = Icons.Outlined.Notifications,
                        contentDescription = "Notificaciones",
                        tint = Gray700,
                        modifier = Modifier.size(24.dp)
                    )
                }
            }
        }
    }
}

@Composable
fun DashboardBottomBar(
    selectedItem: Int,
    onItemSelected: (Int) -> Unit
) {
    NavigationBar(
        containerColor = Surface,
        tonalElevation = 0.dp
    ) {
        val items = listOf(
            Triple(Icons.Outlined.Home, Icons.Filled.Home, "Inicio"),
            Triple(Icons.Outlined.History, Icons.Filled.History, "Historial"),
            Triple(Icons.Outlined.Settings, Icons.Filled.Settings, "Ajustes"),
            Triple(Icons.Outlined.Person, Icons.Filled.Person, "Perfil")
        )

        items.forEachIndexed { index, (outlinedIcon, filledIcon, label) ->
            NavigationBarItem(
                icon = {
                    Icon(
                        if (selectedItem == index) filledIcon else outlinedIcon,
                        contentDescription = label,
                        modifier = Modifier.size(24.dp)
                    )
                },
                label = {
                    Text(
                        label,
                        fontSize = 12.sp,
                        fontWeight = if (selectedItem == index) FontWeight.SemiBold else FontWeight.Normal
                    )
                },
                selected = selectedItem == index,
                onClick = { onItemSelected(index) },
                colors = NavigationBarItemDefaults.colors(
                    selectedIconColor = Primary,
                    selectedTextColor = Primary,
                    unselectedIconColor = Gray600,
                    unselectedTextColor = Gray600,
                    indicatorColor = InfoBackground
                )
            )
        }
    }
}

@Composable
fun ConnectionStatusCompact(isConnected: Boolean) {
    Surface(
        modifier = Modifier.fillMaxWidth(),
        shape = RoundedCornerShape(16.dp),
        color = if (isConnected) SuccessBackground else Surface,
        border = androidx.compose.foundation.BorderStroke(
            1.dp,
            if (isConnected) Success else Border
        )
    ) {
        Row(
            modifier = Modifier.padding(horizontal = 20.dp, vertical = 16.dp),
            verticalAlignment = Alignment.CenterVertically,
            horizontalArrangement = Arrangement.spacedBy(16.dp)
        ) {
            Icon(
                imageVector = if (isConnected) Icons.Filled.CheckCircle else Icons.Outlined.BluetoothDisabled,
                contentDescription = null,
                tint = if (isConnected) Success else Gray600,
                modifier = Modifier.size(24.dp)
            )

            Column(modifier = Modifier.weight(1f)) {
                Text(
                    text = if (isConnected) "Dispositivo conectado" else "Sin dispositivo",
                    style = MaterialTheme.typography.bodyLarge,
                    fontWeight = FontWeight.SemiBold,
                    color = OnSurface
                )
                Text(
                    text = if (isConnected) "Monitoreando en tiempo real" else "Presiona el botón para conectar",
                    style = MaterialTheme.typography.bodyMedium,
                    color = OnSurfaceVariant,
                    fontSize = 13.sp
                )
            }

            if (isConnected) {
                Box(
                    modifier = Modifier
                        .size(10.dp)
                        .clip(CircleShape)
                        .background(Success)
                )
            }
        }
    }
}

@Composable
fun MetricsGridSection() {
    Column(verticalArrangement = Arrangement.spacedBy(12.dp)) {
        Text(
            text = "Resumen de Hoy",
            style = MaterialTheme.typography.titleMedium,
            fontWeight = FontWeight.Bold,
            color = OnSurface
        )

        Row(
            modifier = Modifier.fillMaxWidth(),
            horizontalArrangement = Arrangement.spacedBy(12.dp)
        ) {
            MetricCardMinimal(
                modifier = Modifier.weight(1f),
                value = "2h 34m",
                label = "Tiempo\nActivo",
                icon = Icons.Outlined.AccessTime
            )
            MetricCardMinimal(
                modifier = Modifier.weight(1f),
                value = "86%",
                label = "Postura\nÓptima",
                icon = Icons.Outlined.Check
            )
        }

        Row(
            modifier = Modifier.fillMaxWidth(),
            horizontalArrangement = Arrangement.spacedBy(12.dp)
        ) {
            MetricCardMinimal(
                modifier = Modifier.weight(1f),
                value = "12",
                label = "Alertas\nCorregidas",
                icon = Icons.Outlined.NotificationsActive
            )
            MetricCardMinimal(
                modifier = Modifier.weight(1f),
                value = "+12%",
                label = "Mejora\nSemanal",
                icon = Icons.AutoMirrored.Filled.TrendingUp
            )
        }
    }
}

@Composable
fun MetricCardMinimal(
    modifier: Modifier = Modifier,
    value: String,
    label: String,
    icon: ImageVector
) {
    Surface(
        modifier = modifier,
        shape = RoundedCornerShape(12.dp),
        color = Surface,
        border = androidx.compose.foundation.BorderStroke(1.dp, Border)
    ) {
        Column(
            modifier = Modifier.padding(16.dp),
            verticalArrangement = Arrangement.spacedBy(8.dp)
        ) {
            Icon(
                imageVector = icon,
                contentDescription = null,
                tint = Primary,
                modifier = Modifier.size(20.dp)
            )

            Text(
                text = value,
                style = MaterialTheme.typography.headlineSmall,
                fontWeight = FontWeight.Bold,
                color = OnSurface,
                letterSpacing = (-0.5).sp
            )

            Text(
                text = label,
                style = MaterialTheme.typography.bodySmall,
                color = OnSurfaceVariant,
                fontSize = 11.sp,
                lineHeight = 14.sp,
                maxLines = 2,
                overflow = TextOverflow.Ellipsis
            )
        }
    }
}

@Composable
fun PostureAnalysisChart() {
    Surface(
        modifier = Modifier.fillMaxWidth(),
        shape = RoundedCornerShape(16.dp),
        color = Surface,
        border = androidx.compose.foundation.BorderStroke(1.dp, Border)
    ) {
        Column(
            modifier = Modifier.padding(20.dp),
            verticalArrangement = Arrangement.spacedBy(20.dp)
        ) {
            Row(
                modifier = Modifier.fillMaxWidth(),
                horizontalArrangement = Arrangement.SpaceBetween,
                verticalAlignment = Alignment.CenterVertically
            ) {
                Column {
                    Text(
                        text = "Análisis Postural",
                        style = MaterialTheme.typography.titleMedium,
                        fontWeight = FontWeight.Bold,
                        color = OnSurface
                    )
                    Text(
                        text = "Últimas 6 horas",
                        style = MaterialTheme.typography.bodySmall,
                        color = OnSurfaceVariant,
                        fontSize = 12.sp
                    )
                }
            }

            // Gráfico con degradado azul icebreaker
            Box(
                modifier = Modifier
                    .fillMaxWidth()
                    .height(200.dp)
            ) {
                Row(
                    modifier = Modifier
                        .fillMaxSize()
                        .padding(top = 8.dp, bottom = 32.dp),
                    horizontalArrangement = Arrangement.SpaceEvenly,
                    verticalAlignment = Alignment.Bottom
                ) {
                    val data = listOf(
                        Pair("14h", 0.85f),
                        Pair("15h", 0.70f),
                        Pair("16h", 0.65f),
                        Pair("17h", 0.80f),
                        Pair("18h", 0.90f),
                        Pair("19h", 0.75f)
                    )

                    data.forEach { (hour, percentage) ->
                        GradientBarItem(
                            hour = hour,
                            percentage = percentage
                        )
                    }
                }
            }

            HorizontalDivider(color = Border)

            // Resumen estadístico
            Row(
                modifier = Modifier.fillMaxWidth(),
                horizontalArrangement = Arrangement.SpaceEvenly
            ) {
                StatItem(
                    value = "86%",
                    label = "Óptima",
                    color = ChartPrimary
                )
                Box(
                    modifier = Modifier
                        .width(1.dp)
                        .height(48.dp)
                        .background(Border)
                )
                StatItem(
                    value = "14%",
                    label = "Alertas",
                    color = Gray600
                )
                Box(
                    modifier = Modifier
                        .width(1.dp)
                        .height(48.dp)
                        .background(Border)
                )
                StatItem(
                    value = "4.2h",
                    label = "Promedio",
                    color = Gray600
                )
            }
        }
    }
}

@Composable
fun GradientBarItem(
    hour: String,
    percentage: Float
) {
    Column(
        horizontalAlignment = Alignment.CenterHorizontally,
        verticalArrangement = Arrangement.Bottom,
        modifier = Modifier.height(200.dp)
    ) {
        Column(
            modifier = Modifier.weight(1f),
            verticalArrangement = Arrangement.Bottom
        ) {
            Box(
                modifier = Modifier
                    .width(32.dp)
                    .height(140.dp * percentage)
                    .clip(RoundedCornerShape(topStart = 8.dp, topEnd = 8.dp))
                    .background(
                        brush = Brush.verticalGradient(
                            colors = listOf(
                                ChartGradientEnd,
                                ChartGradientMid,
                                ChartGradientStart
                            )
                        )
                    )
            )
        }
        Spacer(modifier = Modifier.height(8.dp))
        Text(
            text = hour,
            style = MaterialTheme.typography.labelSmall,
            color = OnSurfaceVariant,
            fontSize = 11.sp
        )
    }
}

@Composable
fun StatItem(
    value: String,
    label: String,
    color: Color
) {
    Column(
        horizontalAlignment = Alignment.CenterHorizontally,
        verticalArrangement = Arrangement.spacedBy(4.dp)
    ) {
        Text(
            text = value,
            style = MaterialTheme.typography.headlineSmall,
            fontWeight = FontWeight.Bold,
            color = color,
            letterSpacing = (-0.5).sp
        )
        Text(
            text = label,
            style = MaterialTheme.typography.bodySmall,
            color = OnSurfaceVariant,
            fontSize = 11.sp
        )
    }
}

@Composable
fun InsightsSection() {
    Surface(
        modifier = Modifier.fillMaxWidth(),
        shape = RoundedCornerShape(16.dp),
        color = InfoBackground,
        border = androidx.compose.foundation.BorderStroke(1.dp, Primary.copy(alpha = 0.3f))
    ) {
        Row(
            modifier = Modifier.padding(20.dp),
            horizontalArrangement = Arrangement.spacedBy(16.dp),
            verticalAlignment = Alignment.CenterVertically
        ) {
            Icon(
                imageVector = Icons.Outlined.Lightbulb,
                contentDescription = null,
                tint = Primary,
                modifier = Modifier.size(24.dp)
            )
            Column(modifier = Modifier.weight(1f)) {
                Text(
                    text = "Recomendación",
                    style = MaterialTheme.typography.labelLarge,
                    fontWeight = FontWeight.Bold,
                    color = Primary,
                    fontSize = 12.sp
                )
                Spacer(modifier = Modifier.height(4.dp))
                Text(
                    text = "Tu postura mejora después de las 17h. Intenta mantener esa consistencia durante todo el día.",
                    style = MaterialTheme.typography.bodyMedium,
                    color = OnSurface,
                    fontSize = 13.sp,
                    lineHeight = 19.sp
                )
            }
        }
    }
}

@Composable
fun ActivityTimelineCard() {
    Surface(
        modifier = Modifier.fillMaxWidth(),
        shape = RoundedCornerShape(16.dp),
        color = Surface,
        border = androidx.compose.foundation.BorderStroke(1.dp, Border)
    ) {
        Column(
            modifier = Modifier.padding(20.dp),
            verticalArrangement = Arrangement.spacedBy(16.dp)
        ) {
            Text(
                text = "Actividad Reciente",
                style = MaterialTheme.typography.titleMedium,
                fontWeight = FontWeight.Bold,
                color = OnSurface
            )

            ActivityItemMinimal(
                icon = Icons.Outlined.Check,
                iconColor = Success,
                time = "Hace 5 min",
                title = "Postura corregida automáticamente",
                isLast = false
            )

            ActivityItemMinimal(
                icon = Icons.Outlined.NotificationsActive,
                iconColor = Warning,
                time = "Hace 23 min",
                title = "Alerta de inclinación detectada",
                isLast = false
            )

            ActivityItemMinimal(
                icon = Icons.Outlined.WbSunny,
                iconColor = Info,
                time = "Hace 1h",
                title = "Recordatorio de descanso visual",
                isLast = true
            )
        }
    }
}

@Composable
fun ActivityItemMinimal(
    icon: ImageVector,
    iconColor: Color,
    time: String,
    title: String,
    isLast: Boolean
) {
    Row(
        horizontalArrangement = Arrangement.spacedBy(12.dp)
    ) {
        Column(
            horizontalAlignment = Alignment.CenterHorizontally
        ) {
            Box(
                modifier = Modifier
                    .size(32.dp)
                    .clip(CircleShape)
                    .background(iconColor.copy(alpha = 0.1f)),
                contentAlignment = Alignment.Center
            ) {
                Icon(
                    imageVector = icon,
                    contentDescription = null,
                    tint = iconColor,
                    modifier = Modifier.size(16.dp)
                )
            }
            if (!isLast) {
                Box(
                    modifier = Modifier
                        .width(1.dp)
                        .height(32.dp)
                        .background(Border)
                )
            }
        }

        Column(
            modifier = Modifier.weight(1f),
            verticalArrangement = Arrangement.spacedBy(2.dp)
        ) {
            Text(
                text = title,
                style = MaterialTheme.typography.bodyMedium,
                color = OnSurface,
                fontSize = 13.sp
            )
            Text(
                text = time,
                style = MaterialTheme.typography.bodySmall,
                color = OnSurfaceVariant,
                fontSize = 11.sp
            )
        }
    }
}

@Preview(showBackground = true, showSystemUi = true)
@Composable
fun DashboardScreenPreview() {
    BodyAlertTheme {
        DashboardScreen()
    }
}