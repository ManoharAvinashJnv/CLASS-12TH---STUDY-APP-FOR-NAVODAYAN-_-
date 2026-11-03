package com.example.ui.screens

import android.content.Intent
import android.net.Uri
import androidx.compose.animation.AnimatedVisibility
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
import androidx.compose.foundation.lazy.LazyRow
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Book
import androidx.compose.material.icons.filled.Check
import androidx.compose.material.icons.filled.Close
import androidx.compose.material.icons.filled.Description
import androidx.compose.material.icons.filled.Download
import androidx.compose.material.icons.filled.HelpOutline
import androidx.compose.material.icons.filled.Lightbulb
import androidx.compose.material.icons.filled.OpenInBrowser
import androidx.compose.material.icons.filled.Search
import androidx.compose.material.icons.filled.Star
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.LinearProgressIndicator
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.OutlinedTextFieldDefaults
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Brush
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.platform.testTag
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.compose.ui.window.Dialog
import androidx.compose.ui.window.DialogProperties
import com.example.data.model.AcademicStream
import com.example.data.model.ChapterDetail
import com.example.data.model.ChapterStatus
import com.example.data.model.SubjectInfo
import com.example.data.repository.CurriculumDataProvider
import com.example.ui.theme.AmberGold
import com.example.ui.theme.DarkBackground
import com.example.ui.theme.DarkSurface
import com.example.ui.theme.DarkSurfaceVariant
import com.example.ui.theme.ElectricBlue
import com.example.ui.theme.EmeraldGreen
import com.example.ui.theme.VioletAccent

@Composable
fun NcertLibraryScreen(
    activeStream: AcademicStream,
    chapters: List<ChapterDetail>,
    onUpdateProgress: (String, ChapterStatus, Int) -> Unit,
    modifier: Modifier = Modifier
) {
    val subjects = CurriculumDataProvider.getSubjectsForStream(activeStream)
    var selectedSubjectId by remember(activeStream) { mutableStateOf<String?>(null) }
    var searchQuery by remember { mutableStateOf("") }
    var detailedChapter by remember { mutableStateOf<ChapterDetail?>(null) }

    val filteredChapters = chapters.filter { chapter ->
        val matchesSubject = selectedSubjectId == null || chapter.subjectId == selectedSubjectId
        val matchesQuery = searchQuery.isBlank() ||
                chapter.title.contains(searchQuery, ignoreCase = true) ||
                chapter.introduction.contains(searchQuery, ignoreCase = true) ||
                chapter.keyConcepts.any { it.contains(searchQuery, ignoreCase = true) }
        matchesSubject && matchesQuery
    }

    Column(
        modifier = modifier
            .fillMaxSize()
            .padding(horizontal = 16.dp)
    ) {
        // Search & Filter header
        Spacer(modifier = Modifier.height(8.dp))
        OutlinedTextField(
            value = searchQuery,
            onValueChange = { searchQuery = it },
            placeholder = { Text("Search NCERT chapters, formulas, concepts...") },
            leadingIcon = {
                Icon(Icons.Default.Search, contentDescription = "Search", tint = ElectricBlue)
            },
            trailingIcon = {
                if (searchQuery.isNotEmpty()) {
                    IconButton(onClick = { searchQuery = "" }) {
                        Icon(Icons.Default.Close, contentDescription = "Clear", tint = Color.Gray)
                    }
                }
            },
            singleLine = true,
            modifier = Modifier
                .fillMaxWidth()
                .testTag("ncert_search_field"),
            shape = RoundedCornerShape(14.dp),
            colors = OutlinedTextFieldDefaults.colors(
                focusedContainerColor = DarkSurface,
                unfocusedContainerColor = DarkSurface,
                focusedBorderColor = ElectricBlue,
                unfocusedBorderColor = MaterialTheme.colorScheme.outline
            )
        )

        Spacer(modifier = Modifier.height(10.dp))

        // Subject Horizontal Filter Chips
        LazyRow(
            horizontalArrangement = Arrangement.spacedBy(8.dp),
            contentPadding = PaddingValues(vertical = 4.dp)
        ) {
            item {
                SubjectChip(
                    name = "All Subjects",
                    emoji = "📚",
                    isSelected = selectedSubjectId == null,
                    onClick = { selectedSubjectId = null }
                )
            }
            items(subjects) { subj ->
                SubjectChip(
                    name = subj.name,
                    emoji = subj.iconEmoji,
                    isSelected = selectedSubjectId == subj.id,
                    onClick = { selectedSubjectId = subj.id }
                )
            }
        }

        Spacer(modifier = Modifier.height(8.dp))

        // Official NCERT Verification Banner
        Card(
            shape = RoundedCornerShape(12.dp),
            colors = CardDefaults.cardColors(containerColor = Color(0xFF0F1E2E)),
            border = CardDefaults.outlinedCardBorder().copy(brush = Brush.horizontalGradient(listOf(ElectricBlue.copy(0.4f), Color(0xFF0369A1)))),
            modifier = Modifier.fillMaxWidth()
        ) {
            Row(
                modifier = Modifier.padding(10.dp),
                verticalAlignment = Alignment.CenterVertically
            ) {
                Text(text = "🏛️", fontSize = 16.sp)
                Spacer(modifier = Modifier.width(8.dp))
                Column {
                    Text(
                        text = "Official NCERT Repository (ncert.nic.in)",
                        style = MaterialTheme.typography.labelMedium,
                        fontWeight = FontWeight.Bold,
                        color = ElectricBlue
                    )
                    Text(
                        text = "Authentic Class 12 textbooks, syllabus units & verified textbook PDF links",
                        style = MaterialTheme.typography.bodySmall,
                        color = Color(0xFF94A3B8),
                        fontSize = 11.sp
                    )
                }
            }
        }

        Spacer(modifier = Modifier.height(12.dp))

        // Chapters List
        LazyColumn(
            verticalArrangement = Arrangement.spacedBy(12.dp),
            contentPadding = PaddingValues(bottom = 100.dp)
        ) {
            items(filteredChapters) { chapter ->
                ChapterCard(
                    chapter = chapter,
                    onClick = { detailedChapter = chapter }
                )
            }
        }
    }

    // Comprehensive Chapter Detail Dialog / Full Reader
    detailedChapter?.let { chapter ->
        ChapterDetailDialog(
            chapter = chapter,
            onDismiss = { detailedChapter = null },
            onUpdateProgress = { status, percent ->
                onUpdateProgress(chapter.id, status, percent)
                detailedChapter = chapter.copy(status = status, completionPercent = percent)
            }
        )
    }
}

@Composable
private fun SubjectChip(
    name: String,
    emoji: String,
    isSelected: Boolean,
    onClick: () -> Unit
) {
    Box(
        modifier = Modifier
            .clip(RoundedCornerShape(20.dp))
            .background(if (isSelected) ElectricBlue else DarkSurface)
            .border(
                1.dp,
                if (isSelected) ElectricBlue else MaterialTheme.colorScheme.outline,
                RoundedCornerShape(20.dp)
            )
            .clickable(onClick = onClick)
            .padding(horizontal = 14.dp, vertical = 7.dp),
        contentAlignment = Alignment.Center
    ) {
        Row(verticalAlignment = Alignment.CenterVertically) {
            Text(text = emoji, fontSize = 12.sp)
            Spacer(modifier = Modifier.width(6.dp))
            Text(
                text = name,
                style = MaterialTheme.typography.labelMedium,
                fontWeight = if (isSelected) FontWeight.Bold else FontWeight.Medium,
                color = if (isSelected) Color(0xFF03142B) else Color.White
            )
        }
    }
}

@Composable
private fun ChapterCard(
    chapter: ChapterDetail,
    onClick: () -> Unit
) {
    val statusColor = when (chapter.status) {
        ChapterStatus.COMPLETED -> EmeraldGreen
        ChapterStatus.IN_PROGRESS -> AmberGold
        ChapterStatus.NOT_STARTED -> Color.Gray
    }

    Card(
        shape = RoundedCornerShape(16.dp),
        colors = CardDefaults.cardColors(containerColor = DarkSurface),
        border = CardDefaults.outlinedCardBorder(),
        modifier = Modifier
            .fillMaxWidth()
            .clickable(onClick = onClick)
            .testTag("chapter_card_${chapter.id}")
    ) {
        Column(modifier = Modifier.padding(16.dp)) {
            Row(
                modifier = Modifier.fillMaxWidth(),
                horizontalArrangement = Arrangement.SpaceBetween,
                verticalAlignment = Alignment.CenterVertically
            ) {
                Text(
                    text = "CHAPTER ${chapter.chapterNo} • ${chapter.bookName.uppercase()}",
                    style = MaterialTheme.typography.labelSmall,
                    color = ElectricBlue,
                    fontWeight = FontWeight.Bold,
                    letterSpacing = 1.sp
                )
                // Status pill
                Box(
                    modifier = Modifier
                        .clip(RoundedCornerShape(12.dp))
                        .background(statusColor.copy(alpha = 0.15f))
                        .border(1.dp, statusColor.copy(alpha = 0.5f), RoundedCornerShape(12.dp))
                        .padding(horizontal = 8.dp, vertical = 3.dp)
                ) {
                    Text(
                        text = chapter.status.name.replace("_", " "),
                        color = statusColor,
                        style = MaterialTheme.typography.labelSmall,
                        fontWeight = FontWeight.Bold,
                        fontSize = 10.sp
                    )
                }
            }

            Spacer(modifier = Modifier.height(6.dp))

            Text(
                text = chapter.title,
                style = MaterialTheme.typography.titleMedium,
                fontWeight = FontWeight.Bold,
                color = Color.White
            )

            Spacer(modifier = Modifier.height(6.dp))

            Text(
                text = chapter.introduction,
                style = MaterialTheme.typography.bodySmall,
                color = Color(0xFF94A3B8),
                maxLines = 2
            )

            Spacer(modifier = Modifier.height(10.dp))

            // Progress bar
            Row(
                modifier = Modifier.fillMaxWidth(),
                verticalAlignment = Alignment.CenterVertically,
                horizontalArrangement = Arrangement.SpaceBetween
            ) {
                LinearProgressIndicator(
                    progress = { chapter.completionPercent / 100f },
                    modifier = Modifier
                        .weight(1f)
                        .height(6.dp)
                        .clip(RoundedCornerShape(3.dp)),
                    color = statusColor,
                    trackColor = DarkSurfaceVariant
                )
                Spacer(modifier = Modifier.width(12.dp))
                Text(
                    text = "${chapter.completionPercent}%",
                    style = MaterialTheme.typography.labelSmall,
                    color = Color.White,
                    fontWeight = FontWeight.Bold
                )
            }

            Spacer(modifier = Modifier.height(8.dp))

            Row(
                modifier = Modifier.fillMaxWidth(),
                horizontalArrangement = Arrangement.SpaceBetween
            ) {
                Text(
                    text = "⏱️ ${chapter.estimatedHours} hrs • Difficulty: ${chapter.difficulty}",
                    style = MaterialTheme.typography.bodySmall,
                    color = Color.Gray,
                    fontSize = 11.sp
                )
                Text(
                    text = "Tap to read & practice →",
                    style = MaterialTheme.typography.bodySmall,
                    color = ElectricBlue,
                    fontWeight = FontWeight.SemiBold,
                    fontSize = 11.sp
                )
            }
        }
    }
}

@Composable
private fun ChapterDetailDialog(
    chapter: ChapterDetail,
    onDismiss: () -> Unit,
    onUpdateProgress: (ChapterStatus, Int) -> Unit
) {
    val context = LocalContext.current

    Dialog(
        onDismissRequest = onDismiss,
        properties = DialogProperties(usePlatformDefaultWidth = false)
    ) {
        Surface(
            modifier = Modifier
                .fillMaxSize()
                .padding(12.dp),
            shape = RoundedCornerShape(20.dp),
            color = DarkBackground,
            border = CardDefaults.outlinedCardBorder().copy(brush = Brush.linearGradient(listOf(ElectricBlue, VioletAccent)))
        ) {
            Column(
                modifier = Modifier
                    .fillMaxSize()
                    .padding(20.dp)
            ) {
                // Dialog Header
                Row(
                    modifier = Modifier.fillMaxWidth(),
                    horizontalArrangement = Arrangement.SpaceBetween,
                    verticalAlignment = Alignment.CenterVertically
                ) {
                    Column(modifier = Modifier.weight(1f)) {
                        Text(
                            text = "CH ${chapter.chapterNo}: ${chapter.title}",
                            style = MaterialTheme.typography.titleLarge,
                            fontWeight = FontWeight.Bold,
                            color = Color.White
                        )
                        Text(
                            text = "${chapter.bookName} • ${chapter.stream.displayName}",
                            style = MaterialTheme.typography.bodySmall,
                            color = ElectricBlue
                        )
                    }
                    IconButton(onClick = onDismiss) {
                        Icon(Icons.Default.Close, contentDescription = "Close", tint = Color.White)
                    }
                }

                Spacer(modifier = Modifier.height(12.dp))

                // Scrollable content
                LazyColumn(
                    modifier = Modifier
                        .weight(1f)
                        .fillMaxWidth(),
                    verticalArrangement = Arrangement.spacedBy(14.dp)
                ) {
                    // In-Built Textbook Reader Banner & Mode
                    item {
                        var isBuiltInReaderOpen by remember { mutableStateOf(false) }
                        var fontSizeSp by remember { mutableStateOf(14.sp) }

                        Card(
                            shape = RoundedCornerShape(14.dp),
                            colors = CardDefaults.cardColors(containerColor = Color(0xFF0F2537)),
                            border = CardDefaults.outlinedCardBorder().copy(
                                brush = Brush.horizontalGradient(listOf(ElectricBlue, VioletAccent))
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
                                        Text(text = "📖", fontSize = 20.sp)
                                        Spacer(modifier = Modifier.width(8.dp))
                                        Column {
                                            Text(
                                                text = "In-App NCERT Textbook Reader",
                                                style = MaterialTheme.typography.titleSmall,
                                                fontWeight = FontWeight.Bold,
                                                color = Color.White
                                            )
                                            Text(
                                                text = "100% Offline • In-Built Full Chapter & Formulas",
                                                style = MaterialTheme.typography.bodySmall,
                                                color = ElectricBlue,
                                                fontSize = 11.sp
                                            )
                                        }
                                    }
                                    Button(
                                        onClick = { isBuiltInReaderOpen = !isBuiltInReaderOpen },
                                        colors = ButtonDefaults.buttonColors(containerColor = if (isBuiltInReaderOpen) VioletAccent else ElectricBlue),
                                        shape = RoundedCornerShape(10.dp)
                                    ) {
                                        Text(
                                            text = if (isBuiltInReaderOpen) "Collapse" else "Read Inside App",
                                            color = if (isBuiltInReaderOpen) Color.White else Color(0xFF03142B),
                                            fontWeight = FontWeight.Bold,
                                            fontSize = 12.sp
                                        )
                                    }
                                }

                                if (isBuiltInReaderOpen) {
                                    Spacer(modifier = Modifier.height(12.dp))

                                    // Font Zoom controls
                                    Row(
                                        modifier = Modifier.fillMaxWidth(),
                                        horizontalArrangement = Arrangement.SpaceBetween,
                                        verticalAlignment = Alignment.CenterVertically
                                    ) {
                                        Text(text = "Reader Font Size:", color = Color.Gray, fontSize = 11.sp)
                                        Row(horizontalArrangement = Arrangement.spacedBy(6.dp)) {
                                            Box(
                                                modifier = Modifier
                                                    .clip(RoundedCornerShape(6.dp))
                                                    .background(DarkSurfaceVariant)
                                                    .clickable { fontSizeSp = (fontSizeSp.value - 2).coerceAtLeast(11f).sp }
                                                    .padding(horizontal = 8.dp, vertical = 4.dp)
                                            ) {
                                                Text("A-", color = Color.White, fontSize = 11.sp, fontWeight = FontWeight.Bold)
                                            }
                                            Box(
                                                modifier = Modifier
                                                    .clip(RoundedCornerShape(6.dp))
                                                    .background(DarkSurfaceVariant)
                                                    .clickable { fontSizeSp = (fontSizeSp.value + 2).coerceAtMost(22f).sp }
                                                    .padding(horizontal = 8.dp, vertical = 4.dp)
                                            ) {
                                                Text("A+", color = Color.White, fontSize = 11.sp, fontWeight = FontWeight.Bold)
                                            }
                                        }
                                    }

                                    Spacer(modifier = Modifier.height(10.dp))

                                    // In-Built Full Chapter Reading Content
                                    Card(
                                        shape = RoundedCornerShape(12.dp),
                                        colors = CardDefaults.cardColors(containerColor = Color(0xFF131B2B)),
                                        modifier = Modifier.fillMaxWidth()
                                    ) {
                                        Column(modifier = Modifier.padding(14.dp)) {
                                            Text(
                                                text = "NCERT CLASS 12 — CHAPTER ${chapter.chapterNo}",
                                                style = MaterialTheme.typography.labelSmall,
                                                color = AmberGold,
                                                fontWeight = FontWeight.Bold
                                            )
                                            Spacer(modifier = Modifier.height(4.dp))
                                            Text(
                                                text = chapter.title,
                                                style = MaterialTheme.typography.titleMedium,
                                                fontWeight = FontWeight.Bold,
                                                color = Color.White
                                            )
                                            Spacer(modifier = Modifier.height(10.dp))
                                            Text(
                                                text = "SECTION 1.1 — THEORETICAL FOUNDATION\n" + chapter.introduction,
                                                color = Color(0xFFE2E8F0),
                                                fontSize = fontSizeSp,
                                                lineHeight = (fontSizeSp.value + 8).sp
                                            )
                                            Spacer(modifier = Modifier.height(10.dp))
                                            Text(
                                                text = "SECTION 1.2 — GOVERNING EQUATIONS & LAWS\n" + chapter.formulasOrSummary,
                                                color = Color(0xFFBAE6FD),
                                                fontSize = fontSizeSp,
                                                lineHeight = (fontSizeSp.value + 8).sp
                                            )
                                            Spacer(modifier = Modifier.height(10.dp))
                                            Text(
                                                text = "SECTION 1.3 — EXAM DERIVATIONS & CORE THEOREMS\n" + chapter.keyConcepts.joinToString("\n• "),
                                                color = Color(0xFFCBD5E1),
                                                fontSize = fontSizeSp,
                                                lineHeight = (fontSizeSp.value + 8).sp
                                            )
                                        }
                                    }
                                }
                            }
                        }
                    }

                    // Progress Toggle Buttons
                    item {
                        Card(
                            shape = RoundedCornerShape(14.dp),
                            colors = CardDefaults.cardColors(containerColor = DarkSurface),
                            modifier = Modifier.fillMaxWidth()
                        ) {
                            Column(modifier = Modifier.padding(14.dp)) {
                                Text(
                                    text = "Update Chapter Study Status",
                                    style = MaterialTheme.typography.labelMedium,
                                    fontWeight = FontWeight.Bold,
                                    color = Color.White
                                )
                                Spacer(modifier = Modifier.height(8.dp))
                                Row(
                                    modifier = Modifier.fillMaxWidth(),
                                    horizontalArrangement = Arrangement.spacedBy(8.dp)
                                ) {
                                    Button(
                                        onClick = { onUpdateProgress(ChapterStatus.IN_PROGRESS, 50) },
                                        colors = ButtonDefaults.buttonColors(
                                            containerColor = if (chapter.status == ChapterStatus.IN_PROGRESS) AmberGold else DarkSurfaceVariant
                                        ),
                                        shape = RoundedCornerShape(10.dp),
                                        modifier = Modifier.weight(1f)
                                    ) {
                                        Text("In Progress (50%)", fontSize = 11.sp, color = Color.White)
                                    }
                                    Button(
                                        onClick = { onUpdateProgress(ChapterStatus.COMPLETED, 100) },
                                        colors = ButtonDefaults.buttonColors(
                                            containerColor = if (chapter.status == ChapterStatus.COMPLETED) EmeraldGreen else DarkSurfaceVariant
                                        ),
                                        shape = RoundedCornerShape(10.dp),
                                        modifier = Modifier.weight(1f)
                                    ) {
                                        Text("Completed (100%)", fontSize = 11.sp, color = Color.White)
                                    }
                                }
                            }
                        }
                    }

                    // Introduction
                    item {
                        DetailSectionCard(title = "Chapter Overview", icon = Icons.Default.Description, color = ElectricBlue) {
                            Text(text = chapter.introduction, color = Color(0xFFCBD5E1), style = MaterialTheme.typography.bodyMedium)
                        }
                    }

                    // Learning Objectives
                    item {
                        DetailSectionCard(title = "Key Learning Objectives", icon = Icons.Default.Lightbulb, color = AmberGold) {
                            Column(verticalArrangement = Arrangement.spacedBy(6.dp)) {
                                chapter.learningObjectives.forEach { obj ->
                                    Row(verticalAlignment = Alignment.Top) {
                                        Text(text = "✓ ", color = EmeraldGreen, fontWeight = FontWeight.Bold)
                                        Text(text = obj, color = Color(0xFFE2E8F0), style = MaterialTheme.typography.bodySmall)
                                    }
                                }
                            }
                        }
                    }

                    // Important Formulas / Concepts
                    item {
                        DetailSectionCard(title = "Formula Sheets & Essential Theorems", icon = Icons.Default.Book, color = VioletAccent) {
                            Text(
                                text = chapter.formulasOrSummary,
                                color = Color(0xFFE2E8F0),
                                style = MaterialTheme.typography.bodyMedium,
                                fontWeight = FontWeight.Medium
                            )
                        }
                    }

                    // Previous Years Questions (PYQs)
                    item {
                        DetailSectionCard(title = "CBSE Board Previous Year Trends (PYQs)", icon = Icons.Default.Star, color = AmberGold) {
                            Text(text = chapter.pyqNotes, color = Color(0xFFFEF3C7), style = MaterialTheme.typography.bodyMedium)
                        }
                    }

                    // Practice Questions
                    if (chapter.sampleQuestions.isNotEmpty()) {
                        item {
                            DetailSectionCard(title = "NCERT Practice Questions", icon = Icons.Default.HelpOutline, color = EmeraldGreen) {
                                Column(verticalArrangement = Arrangement.spacedBy(10.dp)) {
                                    chapter.sampleQuestions.forEachIndexed { i, q ->
                                        Column {
                                            Text(
                                                text = "Q${i + 1}: ${q.questionText}",
                                                color = Color.White,
                                                fontWeight = FontWeight.SemiBold,
                                                style = MaterialTheme.typography.bodySmall
                                            )
                                            Text(
                                                text = "💡 Answer: ${q.explanation}",
                                                color = EmeraldGreen,
                                                style = MaterialTheme.typography.bodySmall,
                                                fontSize = 11.sp
                                            )
                                        }
                                    }
                                }
                            }
                        }
                    }
                }
            }
        }
    }
}

@Composable
private fun DetailSectionCard(
    title: String,
    icon: androidx.compose.ui.graphics.vector.ImageVector,
    color: Color,
    content: @Composable () -> Unit
) {
    Card(
        shape = RoundedCornerShape(14.dp),
        colors = CardDefaults.cardColors(containerColor = DarkSurface),
        border = CardDefaults.outlinedCardBorder(),
        modifier = Modifier.fillMaxWidth()
    ) {
        Column(modifier = Modifier.padding(14.dp)) {
            Row(verticalAlignment = Alignment.CenterVertically) {
                Icon(imageVector = icon, contentDescription = title, tint = color, modifier = Modifier.size(18.dp))
                Spacer(modifier = Modifier.width(8.dp))
                Text(text = title, style = MaterialTheme.typography.titleSmall, fontWeight = FontWeight.Bold, color = color)
            }
            Spacer(modifier = Modifier.height(8.dp))
            content()
        }
    }
}
