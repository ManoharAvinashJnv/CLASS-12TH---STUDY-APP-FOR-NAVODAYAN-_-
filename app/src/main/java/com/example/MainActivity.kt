package com.example

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import androidx.compose.animation.Crossfade
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxHeight
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.navigationBarsPadding
import androidx.compose.foundation.layout.padding
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.AutoAwesome
import androidx.compose.material.icons.filled.CalendarMonth
import androidx.compose.material.icons.filled.Home
import androidx.compose.material.icons.filled.Lock
import androidx.compose.material.icons.filled.MenuBook
import androidx.compose.material.icons.filled.Quiz
import androidx.compose.material.icons.filled.Repeat
import androidx.compose.material.icons.filled.TrendingUp
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.NavigationBar
import androidx.compose.material3.NavigationBarItem
import androidx.compose.material3.NavigationBarItemDefaults
import androidx.compose.material3.NavigationRail
import androidx.compose.material3.NavigationRailItem
import androidx.compose.material3.NavigationRailItemDefaults
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableIntStateOf
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.compose.ui.platform.LocalConfiguration
import androidx.compose.ui.platform.testTag
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import androidx.lifecycle.viewmodel.compose.viewModel
import com.example.ui.AcademicViewModel
import com.example.ui.components.AppTopBar
import com.example.ui.components.ProfileLoginDialog
import com.example.ui.components.WelcomeGreetingDialog
import com.example.ui.screens.AiStudyAssistantScreen
import com.example.ui.screens.AnalyticsScreen
import com.example.ui.screens.DashboardScreen
import com.example.ui.screens.FocusEngineScreen
import com.example.ui.screens.JnvSplashScreen
import com.example.ui.screens.NcertLibraryScreen
import com.example.ui.screens.RevisionCenterScreen
import com.example.ui.screens.StudyPlannerScreen
import com.example.ui.screens.TestEngineScreen
import com.example.ui.theme.DarkBackground
import com.example.ui.theme.DarkSurface
import com.example.ui.theme.ElectricBlue
import com.example.ui.theme.EyeComfortTheme
import com.example.ui.theme.MyApplicationTheme

enum class HubTab(val label: String, val icon: ImageVector, val tag: String) {
    DASHBOARD("Hub", Icons.Default.Home, "tab_dashboard"),
    LIBRARY("Library", Icons.Default.MenuBook, "tab_library"),
    FOCUS("Focus", Icons.Default.Lock, "tab_focus"),
    REVISION("Revision", Icons.Default.Repeat, "tab_revision"),
    TESTS("AI Tests", Icons.Default.Quiz, "tab_tests"),
    AI_TUTOR("AI Tutor", Icons.Default.AutoAwesome, "tab_ai"),
    PLANNER("Planner", Icons.Default.CalendarMonth, "tab_planner"),
    ANALYTICS("Analytics", Icons.Default.TrendingUp, "tab_analytics")
}

class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContent {
            AcademicHubRoot()
        }
    }
}

@Composable
fun AcademicHubRoot(
    viewModel: AcademicViewModel = viewModel()
) {
    val eyeTheme by viewModel.eyeTheme.collectAsStateWithLifecycle()

    MyApplicationTheme(eyeTheme = eyeTheme) {
        AcademicHubApp(viewModel = viewModel)
    }
}

@Composable
fun AcademicHubApp(
    viewModel: AcademicViewModel
) {
    val activeStream by viewModel.activeStream.collectAsStateWithLifecycle()
    val eyeTheme by viewModel.eyeTheme.collectAsStateWithLifecycle()
    val showWelcomePopup by viewModel.showWelcomePopup.collectAsStateWithLifecycle()
    val showProfileDialog by viewModel.showProfileDialog.collectAsStateWithLifecycle()
    val livePeers by viewModel.livePeers.collectAsStateWithLifecycle()
    val profile by viewModel.studentProfile.collectAsStateWithLifecycle()
    val chapters by viewModel.streamChapters.collectAsStateWithLifecycle()
    val tasks by viewModel.studyTasks.collectAsStateWithLifecycle()
    val revisionTasks by viewModel.revisionTasks.collectAsStateWithLifecycle()
    val testResults by viewModel.testResults.collectAsStateWithLifecycle()
    val focusUiState by viewModel.focusUiState.collectAsStateWithLifecycle()
    val recentFocusSessions by viewModel.recentFocusSessions.collectAsStateWithLifecycle()
    val liveTestState by viewModel.liveTestState.collectAsStateWithLifecycle()
    val aiMessages by viewModel.aiMessages.collectAsStateWithLifecycle()
    val isAiLoading by viewModel.isAiLoading.collectAsStateWithLifecycle()

    var currentTab by remember { mutableIntStateOf(0) }
    var showEyeThemeDialog by remember { mutableStateOf(false) }
    var showSplashScreen by remember { mutableStateOf(true) }

    // Splash screen display on startup or manual launch
    if (showSplashScreen) {
        JnvSplashScreen(
            onDismissSplash = { showSplashScreen = false }
        )
        return
    }

    val configuration = LocalConfiguration.current
    val isTablet = configuration.screenWidthDp >= 600

    // Full screen focus lockdown overlay check
    if (focusUiState.isDistractionFreeActive) {
        FocusEngineScreen(
            focusState = focusUiState,
            recentSessions = recentFocusSessions,
            onConfigureSession = viewModel::configureFocusSession,
            onStartSession = viewModel::startFocusSession,
            onPauseSession = viewModel::pauseFocusSession,
            onResumeSession = viewModel::resumeFocusSession,
            onCancelSession = viewModel::cancelFocusSession,
            onExitFocus = viewModel::exitFocusMode
        )
        return
    }

    Scaffold(
        modifier = Modifier.fillMaxSize(),
        containerColor = MaterialTheme.colorScheme.background,
        topBar = {
            if (!liveTestState.isActive) {
                AppTopBar(
                    profile = profile,
                    activeStream = activeStream,
                    onStreamChange = viewModel::switchStream,
                    onOpenProfile = { viewModel.setProfileDialogVisible(true) },
                    onOpenEyeTheme = { showEyeThemeDialog = true },
                    onOpenSplash = { showSplashScreen = true }
                )
            }
        },
        bottomBar = {
            if (!isTablet && !liveTestState.isActive) {
                NavigationBar(
                    containerColor = MaterialTheme.colorScheme.surface,
                    tonalElevation = 8.dp,
                    modifier = Modifier.navigationBarsPadding()
                ) {
                    val mobileTabs = listOf(
                        HubTab.DASHBOARD,
                        HubTab.LIBRARY,
                        HubTab.FOCUS,
                        HubTab.REVISION,
                        HubTab.TESTS,
                        HubTab.AI_TUTOR
                    )
                    mobileTabs.forEachIndexed { index, tab ->
                        val isSelected = currentTab == index
                        NavigationBarItem(
                            selected = isSelected,
                            onClick = { currentTab = index },
                            icon = {
                                Icon(
                                    imageVector = tab.icon,
                                    contentDescription = tab.label
                                )
                            },
                            label = {
                                Text(
                                    text = tab.label,
                                    fontSize = 10.sp,
                                    fontWeight = if (isSelected) FontWeight.Bold else FontWeight.Normal
                                )
                            },
                            colors = NavigationBarItemDefaults.colors(
                                selectedIconColor = Color(0xFF03142B),
                                selectedTextColor = MaterialTheme.colorScheme.primary,
                                indicatorColor = MaterialTheme.colorScheme.primary,
                                unselectedIconColor = MaterialTheme.colorScheme.onSurfaceVariant,
                                unselectedTextColor = MaterialTheme.colorScheme.onSurfaceVariant
                            ),
                            modifier = Modifier.testTag(tab.tag)
                        )
                    }
                }
            }
        }
    ) { innerPadding ->
        Row(
            modifier = Modifier
                .fillMaxSize()
                .padding(innerPadding)
        ) {
            // Navigation Rail on wider screens / tablets
            if (isTablet && !liveTestState.isActive) {
                NavigationRail(
                    containerColor = MaterialTheme.colorScheme.surface,
                    modifier = Modifier.fillMaxHeight()
                ) {
                    HubTab.values().forEachIndexed { index, tab ->
                        NavigationRailItem(
                            selected = currentTab == index,
                            onClick = { currentTab = index },
                            icon = { Icon(tab.icon, contentDescription = tab.label) },
                            label = { Text(tab.label, fontSize = 11.sp) },
                            colors = NavigationRailItemDefaults.colors(
                                selectedIconColor = Color(0xFF03142B),
                                selectedTextColor = MaterialTheme.colorScheme.primary,
                                indicatorColor = MaterialTheme.colorScheme.primary,
                                unselectedIconColor = MaterialTheme.colorScheme.onSurfaceVariant,
                                unselectedTextColor = MaterialTheme.colorScheme.onSurfaceVariant
                            ),
                            modifier = Modifier.testTag("rail_${tab.tag}")
                        )
                    }
                }
            }

            // Screen content switcher
            Box(modifier = Modifier.fillMaxSize()) {
                Crossfade(targetState = currentTab, label = "tab_crossfade") { tabIndex ->
                    when (tabIndex) {
                        0 -> DashboardScreen(
                            profile = profile,
                            activeStream = activeStream,
                            chapters = chapters,
                            tasks = tasks,
                            revisionTasks = revisionTasks,
                            recentTest = testResults.firstOrNull(),
                            livePeers = livePeers,
                            onLaunchFocus = { currentTab = 2 },
                            onNavigateLibrary = { currentTab = 1 },
                            onNavigateRevision = { currentTab = 3 },
                            onNavigateTests = { currentTab = 4 },
                            onToggleTask = viewModel::toggleTask
                        )
                        1 -> NcertLibraryScreen(
                            activeStream = activeStream,
                            chapters = chapters,
                            onUpdateProgress = viewModel::markChapterStatus
                        )
                        2 -> FocusEngineScreen(
                            focusState = focusUiState,
                            recentSessions = recentFocusSessions,
                            onConfigureSession = viewModel::configureFocusSession,
                            onStartSession = viewModel::startFocusSession,
                            onPauseSession = viewModel::pauseFocusSession,
                            onResumeSession = viewModel::resumeFocusSession,
                            onCancelSession = viewModel::cancelFocusSession,
                            onExitFocus = viewModel::exitFocusMode
                        )
                        3 -> RevisionCenterScreen(
                            activeStream = activeStream,
                            revisionTasks = revisionTasks,
                            onCompleteRevision = viewModel::completeRevision
                        )
                        4 -> TestEngineScreen(
                            activeStream = activeStream,
                            chapters = chapters,
                            testState = liveTestState,
                            onStartTest = viewModel::startTest,
                            onSelectAnswer = viewModel::selectTestAnswer,
                            onNavigateQuestion = viewModel::navigateQuestion,
                            onSubmitTest = viewModel::submitTest,
                            onExitResult = viewModel::exitTestResult
                        )
                        5 -> AiStudyAssistantScreen(
                            activeStream = activeStream,
                            messages = aiMessages,
                            isLoading = isAiLoading,
                            onSendMessage = viewModel::sendAiPrompt
                        )
                        6 -> StudyPlannerScreen(
                            activeStream = activeStream,
                            tasks = tasks,
                            onToggleTask = viewModel::toggleTask,
                            onAddTask = viewModel::addCustomTask,
                            onDeleteTask = viewModel::deleteTask
                        )
                        7 -> AnalyticsScreen(
                            profile = profile,
                            activeStream = activeStream,
                            chapters = chapters,
                            testResults = testResults
                        )
                        else -> DashboardScreen(
                            profile = profile,
                            activeStream = activeStream,
                            chapters = chapters,
                            tasks = tasks,
                            revisionTasks = revisionTasks,
                            recentTest = testResults.firstOrNull(),
                            livePeers = livePeers,
                            onLaunchFocus = { currentTab = 2 },
                            onNavigateLibrary = { currentTab = 1 },
                            onNavigateRevision = { currentTab = 3 },
                            onNavigateTests = { currentTab = 4 },
                            onToggleTask = viewModel::toggleTask
                        )
                    }
                }
            }
        }
    }

    // Welcome Greeting Pop-Up Dialog at startup
    if (showWelcomePopup || showEyeThemeDialog) {
        WelcomeGreetingDialog(
            currentTheme = eyeTheme,
            onSelectTheme = { theme ->
                viewModel.setEyeTheme(theme)
            },
            onDismiss = {
                viewModel.dismissWelcomePopup()
                showEyeThemeDialog = false
            }
        )
    }

    // Navodayan Scholar Profile Login & DP Dialog
    if (showProfileDialog) {
        ProfileLoginDialog(
            profile = profile,
            onSaveProfile = { name, jnvBranch, house, avatar, stream ->
                viewModel.updateStudentProfile(name, jnvBranch, house, avatar, stream)
            },
            onDismiss = { viewModel.setProfileDialogVisible(false) }
        )
    }
}
