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
import androidx.compose.material3.HorizontalDivider
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.rivera.bodyalert.ui.theme.*

@OptIn(ExperimentalMaterial3Api::class)
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
            DashboardTopBar()
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
                modifier = Modifier.size(56.dp)
            ) {
                Icon(
                    imageVector = if (isDeviceConnected) Icons.Filled.Bluetooth else Icons.AutoMirrored.Outlined.BluetoothSearching,
                    contentDescription = "Conectar dispositivo",
                    modifier = Modifier.size(24.dp)
                )
            }
        },
        floatingActionButtonPosition = FabPosition.End
    ) { padding ->
        LazyColumn(
            modifier = Modifier
                .fillMaxSize()
                .background(Background)
                .padding(padding),
            contentPadding = PaddingValues(16.dp),
            verticalArrangement = Arrangement.spacedBy(16.dp)
        ) {
            // Estado de conexión
            item {
                ConnectionStatusCard(isConnected = isDeviceConnected)
            }

            // Métricas principales
            item {
                MainMetricsSection()
            }

            // Gráfico de postura mejorado
            item {
                PostureAnalysisCard()
            }

            // Timeline simplificado
            item {
                RecentActivityCard()
            }

            item {
                Spacer(modifier = Modifier.height(80.dp))
            }
        }
    }
}

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun DashboardTopBar() {
    TopAppBar(
        title = {
            Column {
                Text(
                    text = "BodyAlert",
                    style = MaterialTheme.typography.titleLarge,
                    fontWeight = FontWeight.Bold,
                    color = Primary
                )
                Text(
                    text = "Monitor Postural",
                    style = MaterialTheme.typography.bodySmall,
                    color = OnSurfaceVariant
                )
            }
        },
        actions = {
            IconButton(onClick = { /* Notificaciones */ }) {
                BadgedBox(
                    badge = {
                        Badge(
                            containerColor = Error,
                            contentColor = Color.White
                        ) {
                            Text("2")
                        }
                    }
                ) {
                    Icon(
                        imageVector = Icons.Outlined.Notifications,
                        contentDescription = "Notificaciones",
                        tint = OnSurface
                    )
                }
            }
        },
        colors = TopAppBarDefaults.topAppBarColors(
            containerColor = Surface,
            titleContentColor = OnSurface
        )
    )
}

@Composable
fun DashboardBottomBar(
    selectedItem: Int,
    onItemSelected: (Int) -> Unit
) {
    NavigationBar(
        containerColor = Surface,
        contentColor = Primary
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
                        contentDescription = label
                    )
                },
                label = { Text(label) },
                selected = selectedItem == index,
                onClick = { onItemSelected(index) },
                colors = NavigationBarItemDefaults.colors(
                    selectedIconColor = Primary,
                    selectedTextColor = Primary,
                    unselectedIconColor = OnSurfaceVariant,
                    unselectedTextColor = OnSurfaceVariant,
                    indicatorColor = Primary.copy(alpha = 0.12f)
                )
            )
        }
    }
}

@Composable
fun ConnectionStatusCard(isConnected: Boolean) {
    Card(
        modifier = Modifier.fillMaxWidth(),
        shape = RoundedCornerShape(16.dp),
        colors = CardDefaults.cardColors(containerColor = Surface),
        elevation = CardDefaults.cardElevation(defaultElevation = 2.dp)
    ) {
        Row(
            modifier = Modifier
                .fillMaxWidth()
                .padding(20.dp),
            verticalAlignment = Alignment.CenterVertically,
            horizontalArrangement = Arrangement.SpaceBetween
        ) {
            Row(
                verticalAlignment = Alignment.CenterVertically,
                horizontalArrangement = Arrangement.spacedBy(12.dp)
            ) {
                Box(
                    modifier = Modifier
                        .size(48.dp)
                        .clip(CircleShape)
                        .background(if (isConnected) Success.copy(alpha = 0.1f) else Warning.copy(alpha = 0.1f)),
                    contentAlignment = Alignment.Center
                ) {
                    Icon(
                        imageVector = if (isConnected) Icons.Filled.CheckCircle else Icons.Outlined.BluetoothDisabled,
                        contentDescription = null,
                        tint = if (isConnected) Success else Warning,
                        modifier = Modifier.size(24.dp)
                    )
                }

                Column {
                    Text(
                        text = if (isConnected) "Dispositivo Conectado" else "Sin Conexión",
                        style = MaterialTheme.typography.titleMedium,
                        fontWeight = FontWeight.SemiBold,
                        color = OnSurface
                    )
                    Text(
                        text = if (isConnected) "Monitoreando tu postura" else "Toca el botón para conectar",
                        style = MaterialTheme.typography.bodySmall,
                        color = OnSurfaceVariant
                    )
                }
            }

            if (isConnected) {
                Box(
                    modifier = Modifier
                        .size(8.dp)
                        .clip(CircleShape)
                        .background(Success)
                )
            }
        }
    }
}

@Composable
fun MainMetricsSection() {
    Row(
        modifier = Modifier.fillMaxWidth(),
        horizontalArrangement = Arrangement.spacedBy(12.dp)
    ) {
        MetricCard(
            modifier = Modifier.weight(1f),
            icon = Icons.Filled.AccessTime,
            value = "2h 34m",
            label = "Tiempo Activo",
            color = Primary
        )
        MetricCard(
            modifier = Modifier.weight(1f),
            icon = Icons.Filled.Check,
            value = "86%",
            label = "Postura Correcta",
            color = Success
        )
        MetricCard(
            modifier = Modifier.weight(1f),
            icon = Icons.AutoMirrored.Filled.TrendingUp,
            value = "+12%",
            label = "Mejora Semanal",
            color = Secondary
        )
    }
}

@Composable
fun MetricCard(
    modifier: Modifier = Modifier,
    icon: ImageVector,
    value: String,
    label: String,
    color: Color
) {
    Card(
        modifier = modifier,
        shape = RoundedCornerShape(12.dp),
        colors = CardDefaults.cardColors(containerColor = Surface),
        elevation = CardDefaults.cardElevation(defaultElevation = 1.dp)
    ) {
        Column(
            modifier = Modifier.padding(16.dp),
            horizontalAlignment = Alignment.CenterHorizontally,
            verticalArrangement = Arrangement.spacedBy(8.dp)
        ) {
            Icon(
                imageVector = icon,
                contentDescription = null,
                tint = color,
                modifier = Modifier.size(24.dp)
            )
            Text(
                text = value,
                style = MaterialTheme.typography.titleLarge,
                fontWeight = FontWeight.Bold,
                color = OnSurface
            )
            Text(
                text = label,
                style = MaterialTheme.typography.bodySmall,
                color = OnSurfaceVariant,
                maxLines = 1
            )
        }
    }
}

@Composable
fun PostureAnalysisCard() {
    Card(
        modifier = Modifier.fillMaxWidth(),
        shape = RoundedCornerShape(16.dp),
        colors = CardDefaults.cardColors(containerColor = Surface),
        elevation = CardDefaults.cardElevation(defaultElevation = 2.dp)
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
                Text(
                    text = "Análisis del Día",
                    style = MaterialTheme.typography.titleMedium,
                    fontWeight = FontWeight.SemiBold,
                    color = OnSurface
                )
                Row(
                    horizontalArrangement = Arrangement.spacedBy(12.dp),
                    verticalAlignment = Alignment.CenterVertically
                ) {
                    LegendItem(color = ChartPrimary, label = "Óptima")
                    LegendItem(color = ChartWarning, label = "Alerta")
                }
            }

            // Gráfico de barras mejorado
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
                        Triple("9h", 0.85f, 0.15f),
                        Triple("11h", 0.70f, 0.30f),
                        Triple("13h", 0.65f, 0.35f),
                        Triple("15h", 0.80f, 0.20f),
                        Triple("17h", 0.90f, 0.10f),
                        Triple("19h", 0.75f, 0.25f)
                    )

                    data.forEach { (hour, correct, alert) ->
                        EnhancedBarItem(
                            hour = hour,
                            correctPercentage = correct,
                            alertPercentage = alert
                        )
                    }
                }
            }

            // Resumen
            Row(
                modifier = Modifier
                    .fillMaxWidth()
                    .clip(RoundedCornerShape(12.dp))
                    .background(SurfaceVariant)
                    .padding(16.dp),
                horizontalArrangement = Arrangement.SpaceEvenly
            ) {
                SummaryItem(
                    value = "86%",
                    label = "Postura Correcta",
                    color = ChartPrimary
                )
                Divider(
                    modifier = Modifier
                        .height(40.dp)
                        .width(1.dp),
                    color = OnSurfaceVariant.copy(alpha = 0.2f)
                )
                SummaryItem(
                    value = "14%",
                    label = "Alertas",
                    color = ChartWarning
                )
            }
        }
    }
}

@Composable
fun LegendItem(color: Color, label: String) {
    Row(
        verticalAlignment = Alignment.CenterVertically,
        horizontalArrangement = Arrangement.spacedBy(6.dp)
    ) {
        Box(
            modifier = Modifier
                .size(12.dp)
                .clip(CircleShape)
                .background(color)
        )
        Text(
            text = label,
            style = MaterialTheme.typography.bodySmall,
            color = OnSurfaceVariant
        )
    }
}

@Composable
fun EnhancedBarItem(
    hour: String,
    correctPercentage: Float,
    alertPercentage: Float
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
            // Barra de alerta
            if (alertPercentage > 0) {
                Box(
                    modifier = Modifier
                        .width(32.dp)
                        .height(140.dp * alertPercentage)
                        .clip(RoundedCornerShape(topStart = 6.dp, topEnd = 6.dp))
                        .background(ChartWarning)
                )
            }
            // Barra correcta
            if (correctPercentage > 0) {
                Box(
                    modifier = Modifier
                        .width(32.dp)
                        .height(140.dp * correctPercentage)
                        .clip(
                            if (alertPercentage > 0) {
                                RoundedCornerShape(0.dp)
                            } else {
                                RoundedCornerShape(topStart = 6.dp, topEnd = 6.dp)
                            }
                        )
                        .background(ChartPrimary)
                )
            }
        }
        Spacer(modifier = Modifier.height(8.dp))
        Text(
            text = hour,
            style = MaterialTheme.typography.labelSmall,
            color = OnSurfaceVariant
        )
    }
}

@Composable
fun SummaryItem(
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
            color = color
        )
        Text(
            text = label,
            style = MaterialTheme.typography.bodySmall,
            color = OnSurfaceVariant
        )
    }
}

@Composable
fun RecentActivityCard() {
    Card(
        modifier = Modifier.fillMaxWidth(),
        shape = RoundedCornerShape(16.dp),
        colors = CardDefaults.cardColors(containerColor = Surface),
        elevation = CardDefaults.cardElevation(defaultElevation = 2.dp)
    ) {
        Column(
            modifier = Modifier.padding(20.dp),
            verticalArrangement = Arrangement.spacedBy(16.dp)
        ) {
            Text(
                text = "Actividad Reciente",
                style = MaterialTheme.typography.titleMedium,
                fontWeight = FontWeight.SemiBold,
                color = OnSurface
            )

            ActivityItem(
                icon = Icons.Filled.Check,
                iconColor = Success,
                time = "Hace 5 min",
                title = "Postura Corregida",
                isLast = false
            )

            ActivityItem(
                icon = Icons.Filled.Warning,
                iconColor = Warning,
                time = "Hace 23 min",
                title = "Alerta de Inclinación",
                isLast = false
            )

            ActivityItem(
                icon = Icons.Filled.WbSunny,
                iconColor = Info,
                time = "Hace 1h",
                title = "Recordatorio de Descanso",
                isLast = true
            )
        }
    }
}

@Composable
fun ActivityItem(
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
                        .background(SurfaceVariant)
                )
            }
        }

        Column(
            modifier = Modifier.weight(1f),
            verticalArrangement = Arrangement.spacedBy(2.dp)
        ) {
            Row(
                modifier = Modifier.fillMaxWidth(),
                horizontalArrangement = Arrangement.SpaceBetween
            ) {
                Text(
                    text = title,
                    style = MaterialTheme.typography.bodyMedium,
                    fontWeight = FontWeight.Medium,
                    color = OnSurface
                )
                Text(
                    text = time,
                    style = MaterialTheme.typography.bodySmall,
                    color = OnSurfaceVariant
                )
            }
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