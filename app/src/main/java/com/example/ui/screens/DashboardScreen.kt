package com.example.ui.screens

import androidx.compose.animation.AnimatedVisibility
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.automirrored.filled.ArrowForward
import androidx.compose.material.icons.filled.AccessTime
import androidx.compose.material.icons.filled.AutoAwesome
import androidx.compose.material.icons.filled.CheckCircle
import androidx.compose.material.icons.filled.FitnessCenter
import androidx.compose.material.icons.filled.MenuBook
import androidx.compose.material.icons.filled.PlayArrow
import androidx.compose.material.icons.filled.Quiz
import androidx.compose.material.icons.filled.Repeat
import androidx.compose.material.icons.filled.TrendingUp
import androidx.compose.material.icons.outlined.Circle
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.Icon
import androidx.compose.material3.LinearProgressIndicator
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Brush
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.platform.testTag
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.example.R
import com.example.data.model.AcademicStream
import com.example.data.model.ChapterDetail
import com.example.data.model.RevisionTask
import com.example.data.model.StudentProfile
import com.example.data.model.StudyTask
import com.example.data.model.TestResult
import com.example.ui.components.JnvEmblemLogo
import com.example.ui.components.JnvLogoVariant
import com.example.ui.theme.AmberGold
import com.example.ui.theme.CrimsonCoral
import com.example.ui.theme.DarkSurface
import com.example.ui.theme.DarkSurfaceVariant
import com.example.ui.theme.ElectricBlue
import com.example.ui.theme.EmeraldGreen
import com.example.ui.theme.VioletAccent

import com.example.data.model.LivePeerStudent
import androidx.compose.foundation.lazy.LazyRow
import androidx.compose.ui.text.style.TextAlign

@Composable
fun DashboardScreen(
    profile: StudentProfile,
    activeStream: AcademicStream,
    chapters: List<ChapterDetail>,
    tasks: List<StudyTask>,
    revisionTasks: List<RevisionTask>,
    recentTest: TestResult?,
    livePeers: List<LivePeerStudent> = emptyList(),
    onLaunchFocus: () -> Unit,
    onNavigateLibrary: () -> Unit,
    onNavigateRevision: () -> Unit,
    onNavigateTests: () -> Unit,
    onToggleTask: (String, Boolean) -> Unit,
    modifier: Modifier = Modifier
) {
    val totalChapters = chapters.size.coerceAtLeast(1)
    val completedChapters = chapters.count { it.status.name == "COMPLETED" }
    val syllabusPercent = ((completedChapters.toFloat() / totalChapters) * 100).toInt()
    val overdueRevisions = revisionTasks.count { it.isOverdue }

    LazyColumn(
        modifier = modifier
            .fillMaxSize()
            .padding(horizontal = 16.dp),
        contentPadding = PaddingValues(top = 12.dp, bottom = 120.dp),
        verticalArrangement = Arrangement.spacedBy(16.dp)
    ) {
        // Hero Card with JNV Emblem and Developer Dedication
        item {
            Card(
                shape = RoundedCornerShape(20.dp),
                colors = CardDefaults.cardColors(containerColor = DarkSurface),
                border = CardDefaults.outlinedCardBorder().copy(brush = Brush.linearGradient(listOf(ElectricBlue.copy(0.6f), VioletAccent.copy(0.6f)))),
                modifier = Modifier
                    .fillMaxWidth()
                    .testTag("dashboard_hero_card")
            ) {
                Box(modifier = Modifier.fillMaxWidth().height(170.dp)) {
                    Image(
                        painter = painterResource(id = R.drawable.img_hub_hero_1793613764871),
                        contentDescription = "Academic Hub Hero",
                        contentScale = ContentScale.Crop,
                        modifier = Modifier.fillMaxSize()
                    )
                    // Gradient overlay
                    Box(
                        modifier = Modifier
                            .fillMaxSize()
                            .background(
                                Brush.verticalGradient(
                                    listOf(Color.Transparent, Color(0xCC0B0F19), Color(0xFF0B0F19))
                                )
                            )
                    )
                    Row(
                        modifier = Modifier
                            .align(Alignment.BottomStart)
                            .padding(14.dp),
                        verticalAlignment = Alignment.CenterVertically
                    ) {
                        JnvEmblemLogo(
                            variant = JnvLogoVariant.BADGE,
                            size = 50.dp,
                            showGlow = false,
                            showMotto = false
                        )
                        Spacer(modifier = Modifier.width(10.dp))
                        Column {
                            Text(
                                text = "CLASS 12 ULTIMATE ACADEMIC HUB",
                                style = MaterialTheme.typography.labelSmall,
                                color = ElectricBlue,
                                fontWeight = FontWeight.Bold,
                                letterSpacing = 1.2.sp
                            )
                            Text(
                                text = "Developed for Navodayans • By Manohar Avinash",
                                style = MaterialTheme.typography.titleSmall,
                                fontWeight = FontWeight.ExtraBold,
                                color = AmberGold
                            )
                            Text(
                                text = "Designation: ${profile.designation} (${profile.houseName})",
                                style = MaterialTheme.typography.bodySmall,
                                color = Color.White,
                                fontSize = 11.sp
                            )
                        }
                    }
                }
            }
        }

        // Live Competitive Study Hub Tracker (Peer Counter)
        item {
            Card(
                shape = RoundedCornerShape(16.dp),
                colors = CardDefaults.cardColors(containerColor = Color(0xFF0E1E2C)),
                border = CardDefaults.outlinedCardBorder().copy(
                    brush = Brush.horizontalGradient(listOf(EmeraldGreen.copy(0.7f), ElectricBlue.copy(0.7f)))
                ),
                modifier = Modifier.fillMaxWidth()
            ) {
                Column(modifier = Modifier.padding(14.dp)) {
                    Row(
                        modifier = Modifier.fillMaxWidth(),
                        horizontalArrangement = Arrangement.SpaceBetween,
                        verticalAlignment = Alignment.CenterVertically
                    ) {
                        Row(verticalAlignment = Alignment.CenterVertically) {
                            Box(
                                modifier = Modifier
                                    .size(10.dp)
                                    .clip(CircleShape)
                                    .background(EmeraldGreen)
                            )
                            Spacer(modifier = Modifier.width(8.dp))
                            Text(
                                text = "LIVE STUDY HUB: 14 NAVODAYANS ONLINE",
                                style = MaterialTheme.typography.labelMedium,
                                fontWeight = FontWeight.ExtraBold,
                                color = EmeraldGreen,
                                letterSpacing = 0.5.sp
                            )
                        }
                        Box(
                            modifier = Modifier
                                .clip(RoundedCornerShape(8.dp))
                                .background(Color(0xFF2E1A0F))
                                .padding(horizontal = 6.dp, vertical = 2.dp)
                        ) {
                            Text(
                                text = "Rank #3 in JNV",
                                color = AmberGold,
                                fontSize = 10.sp,
                                fontWeight = FontWeight.Bold
                            )
                        }
                    }

                    Spacer(modifier = Modifier.height(10.dp))

                    // Peer tickers
                    LazyRow(
                        horizontalArrangement = Arrangement.spacedBy(8.dp),
                        contentPadding = PaddingValues(vertical = 2.dp)
                    ) {
                        items(livePeers) { peer ->
                            Card(
                                shape = RoundedCornerShape(10.dp),
                                colors = CardDefaults.cardColors(containerColor = Color(0xFF162536)),
                                border = CardDefaults.outlinedCardBorder()
                            ) {
                                Row(
                                    modifier = Modifier.padding(horizontal = 10.dp, vertical = 6.dp),
                                    verticalAlignment = Alignment.CenterVertically
                                ) {
                                    Text(text = "🎓", fontSize = 14.sp)
                                    Spacer(modifier = Modifier.width(6.dp))
                                    Column {
                                        Text(
                                            text = peer.name,
                                            fontWeight = FontWeight.Bold,
                                            fontSize = 11.sp,
                                            color = Color.White
                                        )
                                        Text(
                                            text = "${peer.subjectStudying} (${peer.currentFocusMinutes}m)",
                                            fontSize = 9.sp,
                                            color = Color(0xFFA7F3D0)
                                        )
                                    }
                                }
                            }
                        }
                    }
                }
            }
        }

        // Quick Stats Row
        item {
            Row(
                modifier = Modifier.fillMaxWidth(),
                horizontalArrangement = Arrangement.spacedBy(10.dp)
            ) {
                StatCard(
                    title = "Today Study",
                    value = "${profile.totalStudyMinutes / 60}h ${profile.totalStudyMinutes % 60}m",
                    subtitle = "Goal: 4h 30m",
                    icon = Icons.Default.AccessTime,
                    accentColor = ElectricBlue,
                    modifier = Modifier.weight(1f)
                )
                StatCard(
                    title = "Syllabus Done",
                    value = "$syllabusPercent%",
                    subtitle = "$completedChapters of $totalChapters Chapters",
                    icon = Icons.Default.TrendingUp,
                    accentColor = EmeraldGreen,
                    modifier = Modifier.weight(1f)
                )
                StatCard(
                    title = "Revisions Due",
                    value = "${revisionTasks.size}",
                    subtitle = if (overdueRevisions > 0) "$overdueRevisions Overdue!" else "On Track",
                    icon = Icons.Default.Repeat,
                    accentColor = if (overdueRevisions > 0) CrimsonCoral else AmberGold,
                    modifier = Modifier.weight(1f)
                )
            }
        }

        // Ultimate Focus Mode Quick Launch Card
        item {
            Card(
                shape = RoundedCornerShape(18.dp),
                colors = CardDefaults.cardColors(containerColor = Color(0xFF13172C)),
                border = CardDefaults.outlinedCardBorder().copy(brush = Brush.horizontalGradient(listOf(VioletAccent, ElectricBlue))),
                modifier = Modifier
                    .fillMaxWidth()
                    .testTag("focus_quick_launch_card")
                    .clickable(onClick = onLaunchFocus)
            ) {
                Row(
                    modifier = Modifier
                        .fillMaxWidth()
                        .padding(16.dp),
                    verticalAlignment = Alignment.CenterVertically,
                    horizontalArrangement = Arrangement.SpaceBetween
                ) {
                    Row(verticalAlignment = Alignment.CenterVertically) {
                        Box(
                            modifier = Modifier
                                .size(48.dp)
                                .clip(CircleShape)
                                .background(Brush.radialGradient(listOf(VioletAccent, Color(0xFF3B1E78)))),
                            contentAlignment = Alignment.Center
                        ) {
                            Icon(
                                imageVector = Icons.Default.PlayArrow,
                                contentDescription = "Launch Focus",
                                tint = Color.White,
                                modifier = Modifier.size(28.dp)
                            )
                        }
                        Spacer(modifier = Modifier.width(12.dp))
                        Column {
                            Text(
                                text = "ULTIMATE FOCUS ENGINE",
                                style = MaterialTheme.typography.titleSmall,
                                fontWeight = FontWeight.Bold,
                                color = Color.White
                            )
                            Text(
                                text = "Distraction-free deep work & state recovery",
                                style = MaterialTheme.typography.bodySmall,
                                color = Color(0xFF94A3B8)
                            )
                        }
                    }
                    Button(
                        onClick = onLaunchFocus,
                        colors = ButtonDefaults.buttonColors(containerColor = VioletAccent),
                        shape = RoundedCornerShape(12.dp),
                        modifier = Modifier.testTag("launch_focus_button")
                    ) {
                        Text("Start", fontWeight = FontWeight.Bold, color = Color.White)
                    }
                }
            }
        }

        // Today's Study Schedule Section
        item {
            Row(
                modifier = Modifier.fillMaxWidth(),
                horizontalArrangement = Arrangement.SpaceBetween,
                verticalAlignment = Alignment.CenterVertically
            ) {
                Text(
                    text = "Today's Study Schedule",
                    style = MaterialTheme.typography.titleMedium,
                    fontWeight = FontWeight.Bold,
                    color = MaterialTheme.colorScheme.onBackground
                )
                Text(
                    text = "${tasks.count { it.isCompleted }}/${tasks.size} Completed",
                    style = MaterialTheme.typography.bodySmall,
                    color = ElectricBlue,
                    fontWeight = FontWeight.SemiBold
                )
            }
        }

        if (tasks.isEmpty()) {
            item {
                Card(
                    modifier = Modifier.fillMaxWidth(),
                    shape = RoundedCornerShape(14.dp),
                    colors = CardDefaults.cardColors(containerColor = DarkSurfaceVariant)
                ) {
                    Text(
                        text = "No study tasks scheduled for today. Tap Planner to add sessions!",
                        modifier = Modifier.padding(16.dp),
                        color = Color.Gray,
                        style = MaterialTheme.typography.bodyMedium
                    )
                }
            }
        } else {
            items(tasks) { task ->
                StudyTaskCard(
                    task = task,
                    onToggle = { onToggleTask(task.id, task.isCompleted) }
                )
            }
        }

        // Spaced Repetition Alert Banner
        item {
            Card(
                shape = RoundedCornerShape(16.dp),
                colors = CardDefaults.cardColors(containerColor = Color(0xFF1B2338)),
                border = CardDefaults.outlinedCardBorder().copy(brush = Brush.linearGradient(listOf(AmberGold.copy(0.6f), Color(0xFF78350F)))),
                modifier = Modifier
                    .fillMaxWidth()
                    .clickable(onClick = onNavigateRevision)
            ) {
                Row(
                    modifier = Modifier
                        .fillMaxWidth()
                        .padding(14.dp),
                    verticalAlignment = Alignment.CenterVertically,
                    horizontalArrangement = Arrangement.SpaceBetween
                ) {
                    Row(
                        modifier = Modifier.weight(1f),
                        verticalAlignment = Alignment.CenterVertically
                    ) {
                        Box(
                            modifier = Modifier
                                .size(40.dp)
                                .clip(CircleShape)
                                .background(Color(0xFF38230D)),
                            contentAlignment = Alignment.Center
                        ) {
                            Icon(
                                imageVector = Icons.Default.Repeat,
                                contentDescription = "Spaced Repetition",
                                tint = AmberGold,
                                modifier = Modifier.size(22.dp)
                            )
                        }
                        Spacer(modifier = Modifier.width(12.dp))
                        Column {
                            Text(
                                text = "Intelligent Spaced Repetition",
                                style = MaterialTheme.typography.titleSmall,
                                fontWeight = FontWeight.Bold,
                                color = Color.White
                            )
                            Text(
                                text = if (overdueRevisions > 0)
                                    "🚨 $overdueRevisions chapters overdue for review!"
                                else
                                    "Next cycle: 1d, 3d, 7d, 14d, 30d adaptive intervals",
                                style = MaterialTheme.typography.bodySmall,
                                color = if (overdueRevisions > 0) CrimsonCoral else Color(0xFFCBD5E1)
                            )
                        }
                    }
                    Icon(
                        imageVector = Icons.AutoMirrored.Filled.ArrowForward,
                        contentDescription = "Go to Revision",
                        tint = AmberGold
                    )
                }
            }
        }

        // Recent Test Card
        item {
            Card(
                shape = RoundedCornerShape(16.dp),
                colors = CardDefaults.cardColors(containerColor = DarkSurface),
                border = CardDefaults.outlinedCardBorder(),
                modifier = Modifier
                    .fillMaxWidth()
                    .clickable(onClick = onNavigateTests)
            ) {
                Column(modifier = Modifier.padding(16.dp)) {
                    Row(
                        modifier = Modifier.fillMaxWidth(),
                        horizontalArrangement = Arrangement.SpaceBetween,
                        verticalAlignment = Alignment.CenterVertically
                    ) {
                        Row(verticalAlignment = Alignment.CenterVertically) {
                            Icon(
                                imageVector = Icons.Default.Quiz,
                                contentDescription = "Tests",
                                tint = ElectricBlue,
                                modifier = Modifier.size(20.dp)
                            )
                            Spacer(modifier = Modifier.width(8.dp))
                            Text(
                                text = "Dynamic AI Test Engine",
                                style = MaterialTheme.typography.titleSmall,
                                fontWeight = FontWeight.Bold,
                                color = Color.White
                            )
                        }
                        Text(
                            text = "New Exam",
                            style = MaterialTheme.typography.labelSmall,
                            color = ElectricBlue,
                            fontWeight = FontWeight.Bold
                        )
                    }
                    Spacer(modifier = Modifier.height(8.dp))
                    if (recentTest != null) {
                        Text(
                            text = "Latest: ${recentTest.chapterTitle} (${recentTest.accuracyPercent}% Accuracy)",
                            style = MaterialTheme.typography.bodyMedium,
                            color = EmeraldGreen,
                            fontWeight = FontWeight.Medium
                        )
                        Text(
                            text = recentTest.feedback,
                            style = MaterialTheme.typography.bodySmall,
                            color = Color.LightGray
                        )
                    } else {
                        Text(
                            text = "Configure custom chapter tests with multiple choice, assertion-reason, and numericals.",
                            style = MaterialTheme.typography.bodySmall,
                            color = Color.Gray
                        )
                    }
                }
            }
        }

        // Dedicated Footer: Made by Manohar Avinash for Navodayans
        item {
            Card(
                shape = RoundedCornerShape(16.dp),
                colors = CardDefaults.cardColors(containerColor = Color(0xFF131024)),
                border = CardDefaults.outlinedCardBorder().copy(
                    brush = Brush.horizontalGradient(listOf(AmberGold, VioletAccent, ElectricBlue))
                ),
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(top = 10.dp)
                    .testTag("manohar_avinash_footer")
            ) {
                Column(
                    modifier = Modifier.padding(16.dp),
                    horizontalAlignment = Alignment.CenterHorizontally
                ) {
                    Text(
                        text = "MADE WITH ❤️ BY MANOHAR AVINASH",
                        style = MaterialTheme.typography.labelLarge,
                        fontWeight = FontWeight.ExtraBold,
                        color = AmberGold,
                        letterSpacing = 1.2.sp
                    )
                    Spacer(modifier = Modifier.height(4.dp))
                    Text(
                        text = "Engineered Exclusively for NAVODAYANS (JNV) • Class 12 CBSE Academic Hub",
                        style = MaterialTheme.typography.bodySmall,
                        color = Color(0xFFBAE6FD),
                        fontSize = 11.sp,
                        textAlign = TextAlign.Center
                    )
                }
            }
        }
    }
}

@Composable
private fun StatCard(
    title: String,
    value: String,
    subtitle: String,
    icon: androidx.compose.ui.graphics.vector.ImageVector,
    accentColor: Color,
    modifier: Modifier = Modifier
) {
    Card(
        shape = RoundedCornerShape(14.dp),
        colors = CardDefaults.cardColors(containerColor = DarkSurface),
        border = CardDefaults.outlinedCardBorder(),
        modifier = modifier
    ) {
        Column(
            modifier = Modifier.padding(12.dp)
        ) {
            Icon(
                imageVector = icon,
                contentDescription = title,
                tint = accentColor,
                modifier = Modifier.size(18.dp)
            )
            Spacer(modifier = Modifier.height(6.dp))
            Text(
                text = value,
                style = MaterialTheme.typography.titleMedium,
                fontWeight = FontWeight.Bold,
                color = Color.White
            )
            Text(
                text = title,
                style = MaterialTheme.typography.labelSmall,
                color = Color(0xFF94A3B8),
                fontSize = 11.sp
            )
            Text(
                text = subtitle,
                style = MaterialTheme.typography.labelSmall,
                color = accentColor.copy(alpha = 0.9f),
                fontSize = 10.sp,
                fontWeight = FontWeight.Medium
            )
        }
    }
}

@Composable
private fun StudyTaskCard(
    task: StudyTask,
    onToggle: () -> Unit,
    modifier: Modifier = Modifier
) {
    Card(
        shape = RoundedCornerShape(12.dp),
        colors = CardDefaults.cardColors(
            containerColor = if (task.isCompleted) Color(0xFF0F1E19) else DarkSurface
        ),
        border = CardDefaults.outlinedCardBorder().copy(
            brush = Brush.linearGradient(
                if (task.isCompleted) listOf(EmeraldGreen.copy(0.5f), EmeraldGreen.copy(0.2f))
                else listOf(Color(0xFF2A3A5E), Color(0xFF1E293B))
            )
        ),
        modifier = modifier
            .fillMaxWidth()
            .clickable(onClick = onToggle)
            .testTag("study_task_${task.id}")
    ) {
        Row(
            modifier = Modifier
                .fillMaxWidth()
                .padding(12.dp),
            verticalAlignment = Alignment.CenterVertically
        ) {
            Icon(
                imageVector = if (task.isCompleted) Icons.Default.CheckCircle else Icons.Outlined.Circle,
                contentDescription = "Toggle Task",
                tint = if (task.isCompleted) EmeraldGreen else Color.Gray,
                modifier = Modifier.size(24.dp)
            )
            Spacer(modifier = Modifier.width(12.dp))
            Column(modifier = Modifier.weight(1f)) {
                Text(
                    text = task.chapterTitle,
                    style = MaterialTheme.typography.bodyMedium,
                    fontWeight = FontWeight.SemiBold,
                    color = if (task.isCompleted) Color(0xFFA7F3D0) else Color.White
                )
                Text(
                    text = "${task.timeSlot} • ${task.taskType} (${task.durationMinutes}m)",
                    style = MaterialTheme.typography.bodySmall,
                    color = Color(0xFF94A3B8),
                    fontSize = 11.sp
                )
            }
        }
    }
}
