package com.example.ui.screens

import androidx.compose.foundation.background
import androidx.compose.foundation.border
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
import androidx.compose.material.icons.filled.AutoAwesome
import androidx.compose.material.icons.filled.Bolt
import androidx.compose.material.icons.filled.CheckCircle
import androidx.compose.material.icons.filled.EmojiEvents
import androidx.compose.material.icons.filled.LocalFireDepartment
import androidx.compose.material.icons.filled.Lock
import androidx.compose.material.icons.filled.Psychology
import androidx.compose.material.icons.filled.School
import androidx.compose.material.icons.filled.TrendingUp
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
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.example.data.model.AcademicStream
import com.example.data.model.AchievementBadge
import com.example.data.model.ChapterDetail
import com.example.data.model.StudentProfile
import com.example.data.model.TestResult
import com.example.ui.theme.AmberGold
import com.example.ui.theme.DarkSurface
import com.example.ui.theme.DarkSurfaceVariant
import com.example.ui.theme.ElectricBlue
import com.example.ui.theme.EmeraldGreen
import com.example.ui.theme.VioletAccent

@Composable
fun AnalyticsScreen(
    profile: StudentProfile,
    activeStream: AcademicStream,
    chapters: List<ChapterDetail>,
    testResults: List<TestResult>,
    modifier: Modifier = Modifier
) {
    val totalChapters = chapters.size.coerceAtLeast(1)
    val completedCount = chapters.count { it.status.name == "COMPLETED" }
    val syllabusPercent = ((completedCount.toFloat() / totalChapters) * 100).toInt()

    val badges = listOf(
        AchievementBadge(
            id = "b1",
            title = "7-Day Streak Master",
            description = "Maintained uninterrupted daily NCERT study for a week",
            iconEmoji = "🔥",
            isUnlocked = profile.streakDays >= 7,
            progress = profile.streakDays.coerceAtMost(7),
            maxProgress = 7,
            category = "Consistency"
        ),
        AchievementBadge(
            id = "b2",
            title = "Deep Focus Titan",
            description = "Logged over 10 hours of distraction-free lockdown focus",
            iconEmoji = "🧘",
            isUnlocked = profile.totalFocusMinutes >= 600,
            progress = (profile.totalFocusMinutes / 60).coerceAtMost(10),
            maxProgress = 10,
            category = "Productivity"
        ),
        AchievementBadge(
            id = "b3",
            title = "Board Exam Ace",
            description = "Achieved 85%+ score in 10 dynamic chapter tests",
            iconEmoji = "🏆",
            isUnlocked = profile.testsAttempted >= 10,
            progress = profile.testsAttempted.coerceAtMost(10),
            maxProgress = 10,
            category = "Academics"
        ),
        AchievementBadge(
            id = "b4",
            title = "Spaced Recall Champion",
            description = "Completed all Stage 3 (7-day) spaced repetitions",
            iconEmoji = "🧠",
            isUnlocked = true,
            progress = 5,
            maxProgress = 5,
            category = "Retention"
        )
    )

    LazyColumn(
        modifier = modifier
            .fillMaxSize()
            .padding(horizontal = 16.dp),
        contentPadding = PaddingValues(top = 12.dp, bottom = 100.dp),
        verticalArrangement = Arrangement.spacedBy(16.dp)
    ) {
        // Scholar Level & XP Card
        item {
            Card(
                shape = RoundedCornerShape(18.dp),
                colors = CardDefaults.cardColors(containerColor = Color(0xFF131B30)),
                border = CardDefaults.outlinedCardBorder().copy(
                    brush = Brush.horizontalGradient(listOf(AmberGold, ElectricBlue))
                ),
                modifier = Modifier.fillMaxWidth()
            ) {
                Column(modifier = Modifier.padding(18.dp)) {
                    Row(
                        modifier = Modifier.fillMaxWidth(),
                        horizontalArrangement = Arrangement.SpaceBetween,
                        verticalAlignment = Alignment.CenterVertically
                    ) {
                        Column {
                            Text(
                                text = "ACADEMIC RANK",
                                style = MaterialTheme.typography.labelSmall,
                                color = AmberGold,
                                fontWeight = FontWeight.Bold,
                                letterSpacing = 1.sp
                            )
                            Text(
                                text = profile.rankBadge,
                                style = MaterialTheme.typography.titleLarge,
                                fontWeight = FontWeight.ExtraBold,
                                color = Color.White
                            )
                        }
                        Box(
                            modifier = Modifier
                                .clip(RoundedCornerShape(12.dp))
                                .background(Color(0xFF2E1A0F))
                                .padding(horizontal = 10.dp, vertical = 6.dp)
                        ) {
                            Text(
                                text = "${profile.xpPoints} XP",
                                fontWeight = FontWeight.Bold,
                                color = AmberGold,
                                fontSize = 14.sp
                            )
                        }
                    }

                    Spacer(modifier = Modifier.height(14.dp))

                    Text(
                        text = "Next Tier: Grandmaster Scholar (5,000 XP)",
                        style = MaterialTheme.typography.bodySmall,
                        color = Color(0xFF94A3B8)
                    )
                    Spacer(modifier = Modifier.height(6.dp))
                    LinearProgressIndicator(
                        progress = { (profile.xpPoints % 5000) / 5000f },
                        modifier = Modifier
                            .fillMaxWidth()
                            .height(8.dp)
                            .clip(RoundedCornerShape(4.dp)),
                        color = AmberGold,
                        trackColor = DarkSurfaceVariant
                    )
                }
            }
        }

        // Academic KPIs Overview
        item {
            Text(
                text = "Performance Metrics",
                style = MaterialTheme.typography.titleMedium,
                fontWeight = FontWeight.Bold,
                color = Color.White
            )
            Spacer(modifier = Modifier.height(10.dp))
            Row(
                modifier = Modifier.fillMaxWidth(),
                horizontalArrangement = Arrangement.spacedBy(10.dp)
            ) {
                MetricBox(
                    label = "Total Study",
                    value = "${profile.totalStudyMinutes / 60}h ${profile.totalStudyMinutes % 60}m",
                    color = ElectricBlue,
                    modifier = Modifier.weight(1f)
                )
                MetricBox(
                    label = "Focus Hours",
                    value = "${profile.totalFocusMinutes / 60}h",
                    color = VioletAccent,
                    modifier = Modifier.weight(1f)
                )
                MetricBox(
                    label = "Avg Accuracy",
                    value = "${profile.avgTestAccuracy}%",
                    color = EmeraldGreen,
                    modifier = Modifier.weight(1f)
                )
            }
        }

        // Weekly Study Consistency Graph (Day Bars)
        item {
            Card(
                shape = RoundedCornerShape(16.dp),
                colors = CardDefaults.cardColors(containerColor = DarkSurface),
                border = CardDefaults.outlinedCardBorder(),
                modifier = Modifier.fillMaxWidth()
            ) {
                Column(modifier = Modifier.padding(16.dp)) {
                    Text(
                        text = "Weekly Study Consistency",
                        style = MaterialTheme.typography.titleSmall,
                        fontWeight = FontWeight.Bold,
                        color = Color.White
                    )
                    Spacer(modifier = Modifier.height(14.dp))
                    Row(
                        modifier = Modifier.fillMaxWidth(),
                        horizontalArrangement = Arrangement.SpaceBetween,
                        verticalAlignment = Alignment.Bottom
                    ) {
                        val days = listOf("Mon" to 3.5f, "Tue" to 4.2f, "Wed" to 5.0f, "Thu" to 3.8f, "Fri" to 4.5f, "Sat" to 6.2f, "Sun" to 4.8f)
                        days.forEach { (day, hours) ->
                            Column(
                                horizontalAlignment = Alignment.CenterHorizontally,
                                verticalArrangement = Arrangement.Bottom
                            ) {
                                Text(
                                    text = "${hours}h",
                                    fontSize = 9.sp,
                                    color = Color.Gray,
                                    modifier = Modifier.padding(bottom = 4.dp)
                                )
                                Box(
                                    modifier = Modifier
                                        .width(28.dp)
                                        .height((hours * 15).dp)
                                        .clip(RoundedCornerShape(6.dp))
                                        .background(
                                            Brush.verticalGradient(
                                                listOf(ElectricBlue, VioletAccent)
                                            )
                                        )
                                )
                                Spacer(modifier = Modifier.height(6.dp))
                                Text(
                                    text = day,
                                    fontSize = 11.sp,
                                    color = Color(0xFFCBD5E1),
                                    fontWeight = FontWeight.Medium
                                )
                            }
                        }
                    }
                }
            }
        }

        // Subject Breakdown Progress Bars
        item {
            Card(
                shape = RoundedCornerShape(16.dp),
                colors = CardDefaults.cardColors(containerColor = DarkSurface),
                border = CardDefaults.outlinedCardBorder(),
                modifier = Modifier.fillMaxWidth()
            ) {
                Column(modifier = Modifier.padding(16.dp)) {
                    Text(
                        text = "${activeStream.displayName} Syllabus Coverage",
                        style = MaterialTheme.typography.titleSmall,
                        fontWeight = FontWeight.Bold,
                        color = Color.White
                    )
                    Spacer(modifier = Modifier.height(12.dp))
                    chapters.groupBy { it.bookName }.forEach { (book, chList) ->
                        val done = chList.count { it.status.name == "COMPLETED" }
                        val pct = ((done.toFloat() / chList.size) * 100).toInt()
                        Column(modifier = Modifier.padding(vertical = 4.dp)) {
                            Row(
                                modifier = Modifier.fillMaxWidth(),
                                horizontalArrangement = Arrangement.SpaceBetween
                            ) {
                                Text(text = book, fontSize = 12.sp, color = Color.White, fontWeight = FontWeight.SemiBold)
                                Text(text = "$pct% ($done/${chList.size})", fontSize = 11.sp, color = ElectricBlue)
                            }
                            Spacer(modifier = Modifier.height(4.dp))
                            LinearProgressIndicator(
                                progress = { pct / 100f },
                                modifier = Modifier
                                    .fillMaxWidth()
                                    .height(6.dp)
                                    .clip(RoundedCornerShape(3.dp)),
                                color = if (pct >= 80) EmeraldGreen else ElectricBlue,
                                trackColor = DarkSurfaceVariant
                            )
                        }
                    }
                }
            }
        }

        // Achievements & Badges
        item {
            Text(
                text = "Milestone Achievements",
                style = MaterialTheme.typography.titleMedium,
                fontWeight = FontWeight.Bold,
                color = Color.White
            )
        }

        items(badges) { badge ->
            Card(
                shape = RoundedCornerShape(14.dp),
                colors = CardDefaults.cardColors(containerColor = DarkSurface),
                border = CardDefaults.outlinedCardBorder().copy(
                    brush = Brush.horizontalGradient(
                        if (badge.isUnlocked) listOf(AmberGold.copy(0.4f), Color(0xFF2A3A5E))
                        else listOf(Color(0xFF2A3A5E), Color(0xFF1E293B))
                    )
                ),
                modifier = Modifier.fillMaxWidth()
            ) {
                Row(
                    modifier = Modifier.padding(14.dp),
                    verticalAlignment = Alignment.CenterVertically
                ) {
                    Box(
                        modifier = Modifier
                            .size(44.dp)
                            .clip(CircleShape)
                            .background(if (badge.isUnlocked) Color(0xFF38230D) else DarkSurfaceVariant),
                        contentAlignment = Alignment.Center
                    ) {
                        Text(text = badge.iconEmoji, fontSize = 22.sp)
                    }
                    Spacer(modifier = Modifier.width(12.dp))
                    Column(modifier = Modifier.weight(1f)) {
                        Row(verticalAlignment = Alignment.CenterVertically) {
                            Text(
                                text = badge.title,
                                fontWeight = FontWeight.Bold,
                                color = if (badge.isUnlocked) AmberGold else Color.LightGray,
                                fontSize = 14.sp
                            )
                            if (badge.isUnlocked) {
                                Spacer(modifier = Modifier.width(6.dp))
                                Icon(Icons.Default.CheckCircle, contentDescription = "Unlocked", tint = EmeraldGreen, modifier = Modifier.size(16.dp))
                            }
                        }
                        Text(
                            text = badge.description,
                            style = MaterialTheme.typography.bodySmall,
                            color = Color(0xFF94A3B8),
                            fontSize = 11.sp
                        )
                    }
                }
            }
        }
    }
}

@Composable
private fun MetricBox(
    label: String,
    value: String,
    color: Color,
    modifier: Modifier = Modifier
) {
    Card(
        shape = RoundedCornerShape(12.dp),
        colors = CardDefaults.cardColors(containerColor = DarkSurface),
        border = CardDefaults.outlinedCardBorder(),
        modifier = modifier
    ) {
        Column(
            modifier = Modifier.padding(12.dp),
            horizontalAlignment = Alignment.CenterHorizontally
        ) {
            Text(text = value, style = MaterialTheme.typography.titleMedium, fontWeight = FontWeight.Bold, color = color)
            Text(text = label, style = MaterialTheme.typography.labelSmall, color = Color.Gray, fontSize = 10.sp)
        }
    }
}
