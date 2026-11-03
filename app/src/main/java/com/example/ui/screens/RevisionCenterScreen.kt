package com.example.ui.screens

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
import androidx.compose.material.icons.filled.Check
import androidx.compose.material.icons.filled.Info
import androidx.compose.material.icons.filled.Psychology
import androidx.compose.material.icons.filled.Repeat
import androidx.compose.material.icons.filled.Star
import androidx.compose.material.icons.filled.Warning
import androidx.compose.material.icons.outlined.Star
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableIntStateOf
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Brush
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.testTag
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.compose.ui.window.Dialog
import com.example.data.model.AcademicStream
import com.example.data.model.RevisionTask
import com.example.ui.theme.AmberGold
import com.example.ui.theme.CrimsonCoral
import com.example.ui.theme.DarkBackground
import com.example.ui.theme.DarkSurface
import com.example.ui.theme.DarkSurfaceVariant
import com.example.ui.theme.ElectricBlue
import com.example.ui.theme.EmeraldGreen
import com.example.ui.theme.VioletAccent

@Composable
fun RevisionCenterScreen(
    activeStream: AcademicStream,
    revisionTasks: List<RevisionTask>,
    onCompleteRevision: (String, Int) -> Unit,
    modifier: Modifier = Modifier
) {
    var ratingDialogTask by remember { mutableStateOf<RevisionTask?>(null) }
    val overdueCount = revisionTasks.count { it.isOverdue }

    Column(
        modifier = modifier
            .fillMaxSize()
            .padding(horizontal = 16.dp)
    ) {
        Spacer(modifier = Modifier.height(12.dp))

        // Spaced Repetition Engine Banner
        Card(
            shape = RoundedCornerShape(18.dp),
            colors = CardDefaults.cardColors(containerColor = Color(0xFF161F33)),
            border = CardDefaults.outlinedCardBorder().copy(
                brush = Brush.horizontalGradient(listOf(AmberGold, VioletAccent))
            ),
            modifier = Modifier.fillMaxWidth()
        ) {
            Column(modifier = Modifier.padding(16.dp)) {
                Row(verticalAlignment = Alignment.CenterVertically) {
                    Box(
                        modifier = Modifier
                            .size(42.dp)
                            .clip(CircleShape)
                            .background(Color(0xFF38230D)),
                        contentAlignment = Alignment.Center
                    ) {
                        Icon(
                            imageVector = Icons.Default.Psychology,
                            contentDescription = "Spaced Repetition",
                            tint = AmberGold,
                            modifier = Modifier.size(24.dp)
                        )
                    }
                    Spacer(modifier = Modifier.width(12.dp))
                    Column {
                        Text(
                            text = "SMART SPACED REPETITION",
                            style = MaterialTheme.typography.titleMedium,
                            fontWeight = FontWeight.Bold,
                            color = Color.White
                        )
                        Text(
                            text = "1d → 3d → 7d → 14d → 30d Memory Optimization",
                            style = MaterialTheme.typography.bodySmall,
                            color = Color(0xFF94A3B8)
                        )
                    }
                }
                Spacer(modifier = Modifier.height(10.dp))
                Text(
                    text = "Combats the Ebbinghaus Forgetting Curve by dynamically scheduling active recall intervals tailored to your confidence rating.",
                    style = MaterialTheme.typography.bodySmall,
                    color = Color(0xFFCBD5E1),
                    fontSize = 11.sp
                )
            }
        }

        Spacer(modifier = Modifier.height(14.dp))

        // Interval Progress Tracker Row
        Row(
            modifier = Modifier.fillMaxWidth(),
            horizontalArrangement = Arrangement.spacedBy(6.dp)
        ) {
            listOf("1d (Stg 1)", "3d (Stg 2)", "7d (Stg 3)", "14d (Stg 4)", "30d (Master)").forEachIndexed { index, label ->
                Box(
                    modifier = Modifier
                        .weight(1f)
                        .clip(RoundedCornerShape(8.dp))
                        .background(if (index <= 1) Color(0xFF1E3A8A) else DarkSurface)
                        .padding(vertical = 6.dp),
                    contentAlignment = Alignment.Center
                ) {
                    Text(
                        text = label,
                        fontSize = 9.sp,
                        fontWeight = FontWeight.Bold,
                        color = if (index <= 1) ElectricBlue else Color.Gray
                    )
                }
            }
        }

        Spacer(modifier = Modifier.height(14.dp))

        // List Header
        Row(
            modifier = Modifier.fillMaxWidth(),
            horizontalArrangement = Arrangement.SpaceBetween,
            verticalAlignment = Alignment.CenterVertically
        ) {
            Text(
                text = "Revision Queue (${revisionTasks.size})",
                style = MaterialTheme.typography.titleMedium,
                fontWeight = FontWeight.Bold,
                color = Color.White
            )
            if (overdueCount > 0) {
                Text(
                    text = "⚠️ $overdueCount Overdue",
                    color = CrimsonCoral,
                    style = MaterialTheme.typography.labelSmall,
                    fontWeight = FontWeight.Bold
                )
            }
        }

        Spacer(modifier = Modifier.height(10.dp))

        // Revision Tasks List
        if (revisionTasks.isEmpty()) {
            Card(
                shape = RoundedCornerShape(14.dp),
                colors = CardDefaults.cardColors(containerColor = DarkSurface),
                modifier = Modifier.fillMaxWidth()
            ) {
                Column(
                    modifier = Modifier.padding(24.dp),
                    horizontalAlignment = Alignment.CenterHorizontally
                ) {
                    Icon(Icons.Default.Check, contentDescription = "Done", tint = EmeraldGreen, modifier = Modifier.size(36.dp))
                    Spacer(modifier = Modifier.height(8.dp))
                    Text(
                        text = "All Revisions Completed!",
                        style = MaterialTheme.typography.titleSmall,
                        fontWeight = FontWeight.Bold,
                        color = Color.White
                    )
                    Text(
                        text = "Finish more NCERT chapters in the Library to enroll them in the spaced repetition engine.",
                        style = MaterialTheme.typography.bodySmall,
                        color = Color.Gray,
                        modifier = Modifier.padding(top = 4.dp)
                    )
                }
            }
        } else {
            LazyColumn(
                verticalArrangement = Arrangement.spacedBy(10.dp),
                contentPadding = PaddingValues(bottom = 100.dp)
            ) {
                items(revisionTasks) { task ->
                    RevisionItemCard(
                        task = task,
                        onReviewClick = { ratingDialogTask = task }
                    )
                }
            }
        }
    }

    // Confidence Rating & Completion Dialog
    ratingDialogTask?.let { task ->
        ConfidenceRatingDialog(
            task = task,
            onDismiss = { ratingDialogTask = null },
            onSubmitRating = { rating ->
                onCompleteRevision(task.chapterId, rating)
                ratingDialogTask = null
            }
        )
    }
}

@Composable
private fun RevisionItemCard(
    task: RevisionTask,
    onReviewClick: () -> Unit
) {
    Card(
        shape = RoundedCornerShape(14.dp),
        colors = CardDefaults.cardColors(containerColor = DarkSurface),
        border = CardDefaults.outlinedCardBorder().copy(
            brush = Brush.linearGradient(
                if (task.isOverdue) listOf(CrimsonCoral, Color(0xFF7F1D1D))
                else listOf(Color(0xFF2A3A5E), Color(0xFF1E293B))
            )
        ),
        modifier = Modifier
            .fillMaxWidth()
            .testTag("revision_card_${task.chapterId}")
    ) {
        Column(modifier = Modifier.padding(14.dp)) {
            Row(
                modifier = Modifier.fillMaxWidth(),
                horizontalArrangement = Arrangement.SpaceBetween,
                verticalAlignment = Alignment.CenterVertically
            ) {
                Text(
                    text = "${task.subjectName} • STAGE ${task.stage} OF 5",
                    style = MaterialTheme.typography.labelSmall,
                    color = if (task.isOverdue) CrimsonCoral else ElectricBlue,
                    fontWeight = FontWeight.Bold
                )
                if (task.isOverdue) {
                    Box(
                        modifier = Modifier
                            .clip(RoundedCornerShape(8.dp))
                            .background(Color(0xFF450A0A))
                            .padding(horizontal = 6.dp, vertical = 2.dp)
                    ) {
                        Text(
                            text = "OVERDUE",
                            color = CrimsonCoral,
                            fontSize = 10.sp,
                            fontWeight = FontWeight.Bold
                        )
                    }
                }
            }

            Spacer(modifier = Modifier.height(6.dp))

            Text(
                text = task.chapterTitle,
                style = MaterialTheme.typography.titleMedium,
                fontWeight = FontWeight.Bold,
                color = Color.White
            )

            Spacer(modifier = Modifier.height(4.dp))

            Text(
                text = "Target Interval: ${task.intervalDays} Days • Last Confidence: ${task.confidenceRating}/5 Stars",
                style = MaterialTheme.typography.bodySmall,
                color = Color(0xFF94A3B8)
            )

            Spacer(modifier = Modifier.height(10.dp))

            Button(
                onClick = onReviewClick,
                modifier = Modifier
                    .fillMaxWidth()
                    .testTag("review_chapter_button_${task.chapterId}"),
                colors = ButtonDefaults.buttonColors(
                    containerColor = if (task.isOverdue) CrimsonCoral else VioletAccent
                ),
                shape = RoundedCornerShape(10.dp)
            ) {
                Icon(Icons.Default.Repeat, contentDescription = "Revise", tint = Color.White, modifier = Modifier.size(16.dp))
                Spacer(modifier = Modifier.width(6.dp))
                Text(
                    text = "Complete Active Recall & Rate",
                    color = Color.White,
                    fontWeight = FontWeight.Bold,
                    fontSize = 12.sp
                )
            }
        }
    }
}

@Composable
private fun ConfidenceRatingDialog(
    task: RevisionTask,
    onDismiss: () -> Unit,
    onSubmitRating: (Int) -> Unit
) {
    var selectedStars by remember { mutableIntStateOf(task.confidenceRating.coerceAtLeast(3)) }

    Dialog(onDismissRequest = onDismiss) {
        Surface(
            shape = RoundedCornerShape(18.dp),
            color = DarkBackground,
            border = CardDefaults.outlinedCardBorder().copy(brush = Brush.linearGradient(listOf(AmberGold, VioletAccent)))
        ) {
            Column(
                modifier = Modifier.padding(20.dp),
                horizontalAlignment = Alignment.CenterHorizontally
            ) {
                Text(
                    text = "Record Revision Feedback",
                    style = MaterialTheme.typography.titleMedium,
                    fontWeight = FontWeight.Bold,
                    color = Color.White
                )
                Text(
                    text = task.chapterTitle,
                    style = MaterialTheme.typography.bodySmall,
                    color = ElectricBlue,
                    modifier = Modifier.padding(top = 4.dp)
                )

                Spacer(modifier = Modifier.height(16.dp))

                Text(
                    text = "How confident are you with the formulas & concepts?",
                    style = MaterialTheme.typography.bodySmall,
                    color = Color(0xFFCBD5E1)
                )

                Spacer(modifier = Modifier.height(12.dp))

                // 5-Star Rating Selector
                Row(
                    horizontalArrangement = Arrangement.spacedBy(8.dp),
                    verticalAlignment = Alignment.CenterVertically
                ) {
                    (1..5).forEach { star ->
                        IconButton(onClick = { selectedStars = star }) {
                            Icon(
                                imageVector = if (star <= selectedStars) Icons.Filled.Star else Icons.Outlined.Star,
                                contentDescription = "$star Stars",
                                tint = if (star <= selectedStars) AmberGold else Color.Gray,
                                modifier = Modifier.size(32.dp)
                            )
                        }
                    }
                }

                Text(
                    text = when (selectedStars) {
                        1 -> "⚠️ Weak recall. Algorithm will re-schedule in 1 day."
                        2 -> "🔄 Moderate difficulty. Next revision in 2 days."
                        3 -> "👍 Average retention. Standard interval applied."
                        4 -> "🌟 Strong mastery! Moving to next spaced repetition stage."
                        else -> "🏆 Flawless retention! Advancing to 14/30 day cycle."
                    },
                    style = MaterialTheme.typography.bodySmall,
                    color = AmberGold,
                    fontSize = 11.sp,
                    modifier = Modifier.padding(vertical = 8.dp)
                )

                Spacer(modifier = Modifier.height(16.dp))

                Button(
                    onClick = { onSubmitRating(selectedStars) },
                    modifier = Modifier
                        .fillMaxWidth()
                        .testTag("submit_revision_rating_button"),
                    colors = ButtonDefaults.buttonColors(containerColor = EmeraldGreen),
                    shape = RoundedCornerShape(12.dp)
                ) {
                    Text("Save & Update Interval (+150 XP)", fontWeight = FontWeight.Bold, color = Color.White)
                }
            }
        }
    }
}
