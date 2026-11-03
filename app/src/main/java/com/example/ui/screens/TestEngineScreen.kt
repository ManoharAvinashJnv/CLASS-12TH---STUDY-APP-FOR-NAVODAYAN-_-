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
import androidx.compose.foundation.lazy.LazyRow
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.automirrored.filled.ArrowBack
import androidx.compose.material.icons.automirrored.filled.ArrowForward
import androidx.compose.material.icons.filled.AccessTime
import androidx.compose.material.icons.filled.Check
import androidx.compose.material.icons.filled.Close
import androidx.compose.material.icons.filled.Done
import androidx.compose.material.icons.filled.EmojiEvents
import androidx.compose.material.icons.filled.HelpOutline
import androidx.compose.material.icons.filled.PlayArrow
import androidx.compose.material.icons.filled.Quiz
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.OutlinedButton
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
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.example.data.model.AcademicStream
import com.example.data.model.ChapterDetail
import com.example.data.model.QuestionType
import com.example.data.model.SubjectInfo
import com.example.data.model.TestDifficulty
import com.example.data.model.TestResult
import com.example.data.repository.CurriculumDataProvider
import com.example.ui.LiveTestState
import com.example.ui.theme.AmberGold
import com.example.ui.theme.CrimsonCoral
import com.example.ui.theme.DarkBackground
import com.example.ui.theme.DarkSurface
import com.example.ui.theme.DarkSurfaceVariant
import com.example.ui.theme.ElectricBlue
import com.example.ui.theme.EmeraldGreen
import com.example.ui.theme.VioletAccent

@Composable
fun TestEngineScreen(
    activeStream: AcademicStream,
    chapters: List<ChapterDetail>,
    testState: LiveTestState,
    onStartTest: (SubjectInfo, String, TestDifficulty, Int) -> Unit,
    onSelectAnswer: (Int, Int, Boolean) -> Unit,
    onNavigateQuestion: (Int) -> Unit,
    onSubmitTest: () -> Unit,
    onExitResult: () -> Unit,
    modifier: Modifier = Modifier
) {
    when {
        testState.isActive -> {
            LiveTestInterface(
                state = testState,
                onSelectAnswer = onSelectAnswer,
                onNavigateQuestion = onNavigateQuestion,
                onSubmit = onSubmitTest
            )
        }
        testState.isCompleted && testState.latestResult != null -> {
            TestResultReport(
                result = testState.latestResult,
                questions = testState.questions,
                userAnswers = testState.userAnswers,
                onDismiss = onExitResult
            )
        }
        else -> {
            TestGeneratorConfig(
                activeStream = activeStream,
                chapters = chapters,
                onStartTest = onStartTest,
                modifier = modifier
            )
        }
    }
}

@Composable
private fun TestGeneratorConfig(
    activeStream: AcademicStream,
    chapters: List<ChapterDetail>,
    onStartTest: (SubjectInfo, String, TestDifficulty, Int) -> Unit,
    modifier: Modifier = Modifier
) {
    val subjects = CurriculumDataProvider.getSubjectsForStream(activeStream)
    var selectedSubject by remember(activeStream) { mutableStateOf(subjects.first()) }
    var selectedDifficulty by remember { mutableStateOf(TestDifficulty.INTERMEDIATE) }
    var selectedCount by remember { mutableIntStateOf(5) }

    val subjectChapters = chapters.filter { it.subjectId == selectedSubject.id }
    var selectedChapterTitle by remember(selectedSubject) {
        mutableStateOf(subjectChapters.firstOrNull()?.title ?: "Full Subject Mock")
    }

    LazyColumn(
        modifier = modifier
            .fillMaxSize()
            .padding(horizontal = 16.dp),
        contentPadding = PaddingValues(top = 12.dp, bottom = 100.dp),
        verticalArrangement = Arrangement.spacedBy(16.dp)
    ) {
        // Banner
        item {
            Card(
                shape = RoundedCornerShape(18.dp),
                colors = CardDefaults.cardColors(containerColor = Color(0xFF13192B)),
                border = CardDefaults.outlinedCardBorder().copy(
                    brush = Brush.horizontalGradient(listOf(ElectricBlue, VioletAccent))
                ),
                modifier = Modifier.fillMaxWidth()
            ) {
                Row(modifier = Modifier.padding(16.dp), verticalAlignment = Alignment.CenterVertically) {
                    Box(
                        modifier = Modifier
                            .size(46.dp)
                            .clip(CircleShape)
                            .background(Brush.radialGradient(listOf(ElectricBlue, Color(0xFF0369A1)))),
                        contentAlignment = Alignment.Center
                    ) {
                        Icon(Icons.Default.Quiz, contentDescription = "Quiz", tint = Color.White, modifier = Modifier.size(24.dp))
                    }
                    Spacer(modifier = Modifier.width(12.dp))
                    Column {
                        Text(
                            text = "DYNAMIC AI TEST GENERATOR",
                            style = MaterialTheme.typography.titleMedium,
                            fontWeight = FontWeight.Bold,
                            color = Color.White
                        )
                        Text(
                            text = "Calibrated to Class 12 CBSE standard & syllabus coverage",
                            style = MaterialTheme.typography.bodySmall,
                            color = Color(0xFF94A3B8)
                        )
                    }
                }
            }
        }

        // 1. Select Subject
        item {
            Text(text = "1. Select Subject", style = MaterialTheme.typography.titleSmall, fontWeight = FontWeight.Bold, color = Color.White)
            Spacer(modifier = Modifier.height(6.dp))
            LazyRow(horizontalArrangement = Arrangement.spacedBy(8.dp)) {
                items(subjects) { subj ->
                    val isSelected = selectedSubject.id == subj.id
                    Box(
                        modifier = Modifier
                            .clip(RoundedCornerShape(12.dp))
                            .background(if (isSelected) ElectricBlue else DarkSurface)
                            .clickable {
                                selectedSubject = subj
                                selectedChapterTitle = chapters.firstOrNull { it.subjectId == subj.id }?.title ?: "Full Subject Mock"
                            }
                            .padding(horizontal = 14.dp, vertical = 8.dp)
                    ) {
                        Row(verticalAlignment = Alignment.CenterVertically) {
                            Text(subj.iconEmoji, fontSize = 13.sp)
                            Spacer(modifier = Modifier.width(6.dp))
                            Text(
                                subj.name,
                                fontWeight = FontWeight.Bold,
                                color = if (isSelected) Color(0xFF03142B) else Color.White,
                                fontSize = 13.sp
                            )
                        }
                    }
                }
            }
        }

        // 2. Select Chapter
        item {
            Text(text = "2. Select Chapter Focus", style = MaterialTheme.typography.titleSmall, fontWeight = FontWeight.Bold, color = Color.White)
            Spacer(modifier = Modifier.height(6.dp))
            Column(verticalArrangement = Arrangement.spacedBy(6.dp)) {
                subjectChapters.forEach { ch ->
                    val isSelected = selectedChapterTitle == ch.title
                    Card(
                        shape = RoundedCornerShape(10.dp),
                        colors = CardDefaults.cardColors(containerColor = if (isSelected) Color(0xFF1E293B) else DarkSurface),
                        border = if (isSelected) CardDefaults.outlinedCardBorder().copy(brush = Brush.horizontalGradient(listOf(ElectricBlue, VioletAccent))) else CardDefaults.outlinedCardBorder(),
                        modifier = Modifier
                            .fillMaxWidth()
                            .clickable { selectedChapterTitle = ch.title }
                    ) {
                        Row(
                            modifier = Modifier.padding(12.dp),
                            verticalAlignment = Alignment.CenterVertically,
                            horizontalArrangement = Arrangement.SpaceBetween
                        ) {
                            Column(modifier = Modifier.weight(1f)) {
                                Text(
                                    text = "Ch ${ch.chapterNo}: ${ch.title}",
                                    fontWeight = FontWeight.SemiBold,
                                    fontSize = 13.sp,
                                    color = if (isSelected) ElectricBlue else Color.White
                                )
                                Text(
                                    text = "${ch.bookName} • ${ch.difficulty}",
                                    fontSize = 11.sp,
                                    color = Color.Gray
                                )
                            }
                            if (isSelected) {
                                Icon(Icons.Default.Check, contentDescription = "Selected", tint = ElectricBlue, modifier = Modifier.size(18.dp))
                            }
                        }
                    }
                }
            }
        }

        // 3. Difficulty Level
        item {
            Text(text = "3. Examination Difficulty", style = MaterialTheme.typography.titleSmall, fontWeight = FontWeight.Bold, color = Color.White)
            Spacer(modifier = Modifier.height(6.dp))
            Row(modifier = Modifier.fillMaxWidth(), horizontalArrangement = Arrangement.spacedBy(8.dp)) {
                TestDifficulty.values().forEach { diff ->
                    val isSelected = selectedDifficulty == diff
                    Box(
                        modifier = Modifier
                            .weight(1f)
                            .clip(RoundedCornerShape(10.dp))
                            .background(if (isSelected) VioletAccent else DarkSurface)
                            .clickable { selectedDifficulty = diff }
                            .padding(vertical = 10.dp),
                        contentAlignment = Alignment.Center
                    ) {
                        Text(
                            text = diff.label,
                            fontSize = 11.sp,
                            fontWeight = FontWeight.Bold,
                            color = if (isSelected) Color.White else Color(0xFFCBD5E1),
                            textAlign = TextAlign.Center
                        )
                    }
                }
            }
        }

        // 4. Questions count
        item {
            Text(text = "4. Number of Questions", style = MaterialTheme.typography.titleSmall, fontWeight = FontWeight.Bold, color = Color.White)
            Spacer(modifier = Modifier.height(6.dp))
            Row(modifier = Modifier.fillMaxWidth(), horizontalArrangement = Arrangement.spacedBy(8.dp)) {
                listOf(3 to "3 Questions (6m)", 5 to "5 Questions (10m)", 10 to "10 Questions (20m)").forEach { (count, label) ->
                    val isSelected = selectedCount == count
                    Box(
                        modifier = Modifier
                            .weight(1f)
                            .clip(RoundedCornerShape(10.dp))
                            .background(if (isSelected) EmeraldGreen else DarkSurface)
                            .clickable { selectedCount = count }
                            .padding(vertical = 10.dp),
                        contentAlignment = Alignment.Center
                    ) {
                        Text(
                            text = label,
                            fontSize = 11.sp,
                            fontWeight = FontWeight.Bold,
                            color = if (isSelected) Color.White else Color(0xFFCBD5E1),
                            textAlign = TextAlign.Center
                        )
                    }
                }
            }
        }

        // Start Test Button
        item {
            Spacer(modifier = Modifier.height(8.dp))
            Button(
                onClick = {
                    onStartTest(selectedSubject, selectedChapterTitle, selectedDifficulty, selectedCount)
                },
                modifier = Modifier
                    .fillMaxWidth()
                    .height(52.dp)
                    .testTag("start_generated_test_button"),
                colors = ButtonDefaults.buttonColors(containerColor = ElectricBlue),
                shape = RoundedCornerShape(14.dp)
            ) {
                Icon(Icons.Default.PlayArrow, contentDescription = "Start", tint = Color(0xFF03142B))
                Spacer(modifier = Modifier.width(8.dp))
                Text(
                    text = "Begin Timed Exam ($selectedCount Questions)",
                    fontWeight = FontWeight.Bold,
                    fontSize = 15.sp,
                    color = Color(0xFF03142B)
                )
            }
        }
    }
}

@Composable
private fun LiveTestInterface(
    state: LiveTestState,
    onSelectAnswer: (Int, Int, Boolean) -> Unit,
    onNavigateQuestion: (Int) -> Unit,
    onSubmit: () -> Unit
) {
    val qIndex = state.currentQuestionIndex
    val question = state.questions.getOrNull(qIndex) ?: return
    val userSelected = state.userAnswers[qIndex] ?: emptyList()
    val isMultiSelect = question.type == QuestionType.MULTI_SELECT

    val mins = state.remainingTimeSeconds / 60
    val secs = state.remainingTimeSeconds % 60
    val timerStr = String.format("%02d:%02d", mins, secs)

    Surface(
        modifier = Modifier
            .fillMaxSize()
            .testTag("live_test_screen"),
        color = DarkBackground
    ) {
        Column(
            modifier = Modifier
                .fillMaxSize()
                .padding(16.dp),
            verticalArrangement = Arrangement.SpaceBetween
        ) {
            // Header: Subject, Timer, Question Index
            Column {
                Row(
                    modifier = Modifier.fillMaxWidth(),
                    horizontalArrangement = Arrangement.SpaceBetween,
                    verticalAlignment = Alignment.CenterVertically
                ) {
                    Column {
                        Text(
                            text = state.subjectName.uppercase(),
                            style = MaterialTheme.typography.labelSmall,
                            color = ElectricBlue,
                            fontWeight = FontWeight.Bold
                        )
                        Text(
                            text = state.chapterTitle,
                            style = MaterialTheme.typography.titleMedium,
                            fontWeight = FontWeight.Bold,
                            color = Color.White
                        )
                    }
                    // Timer pill
                    Row(
                        modifier = Modifier
                            .clip(RoundedCornerShape(12.dp))
                            .background(if (state.remainingTimeSeconds < 60) Color(0xFF450A0A) else Color(0xFF1E293B))
                            .padding(horizontal = 10.dp, vertical = 6.dp),
                        verticalAlignment = Alignment.CenterVertically
                    ) {
                        Icon(
                            Icons.Default.AccessTime,
                            contentDescription = "Timer",
                            tint = if (state.remainingTimeSeconds < 60) CrimsonCoral else ElectricBlue,
                            modifier = Modifier.size(16.dp)
                        )
                        Spacer(modifier = Modifier.width(4.dp))
                        Text(
                            text = timerStr,
                            fontWeight = FontWeight.Bold,
                            color = if (state.remainingTimeSeconds < 60) CrimsonCoral else Color.White
                        )
                    }
                }

                Spacer(modifier = Modifier.height(12.dp))

                // Question Progress dots / row
                LazyRow(
                    modifier = Modifier.fillMaxWidth(),
                    horizontalArrangement = Arrangement.spacedBy(6.dp)
                ) {
                    items(state.questions.indices.toList()) { i ->
                        val isAnswered = state.userAnswers.containsKey(i) && state.userAnswers[i]!!.isNotEmpty()
                        val isCurrent = i == qIndex
                        Box(
                            modifier = Modifier
                                .size(34.dp)
                                .clip(CircleShape)
                                .background(
                                    when {
                                        isCurrent -> ElectricBlue
                                        isAnswered -> EmeraldGreen
                                        else -> DarkSurfaceVariant
                                    }
                                )
                                .clickable { onNavigateQuestion(i) },
                            contentAlignment = Alignment.Center
                        ) {
                            Text(
                                text = "${i + 1}",
                                fontWeight = FontWeight.Bold,
                                color = if (isCurrent) Color(0xFF03142B) else Color.White,
                                fontSize = 12.sp
                            )
                        }
                    }
                }
            }

            // Question Card & Options
            Card(
                shape = RoundedCornerShape(18.dp),
                colors = CardDefaults.cardColors(containerColor = DarkSurface),
                border = CardDefaults.outlinedCardBorder(),
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(vertical = 12.dp)
            ) {
                Column(modifier = Modifier.padding(16.dp)) {
                    Row(
                        modifier = Modifier.fillMaxWidth(),
                        horizontalArrangement = Arrangement.SpaceBetween
                    ) {
                        Text(
                            text = "Question ${qIndex + 1} of ${state.questions.size}",
                            style = MaterialTheme.typography.labelSmall,
                            color = AmberGold,
                            fontWeight = FontWeight.Bold
                        )
                        Text(
                            text = "${question.type.label} (${question.marks}m)",
                            style = MaterialTheme.typography.labelSmall,
                            color = Color.LightGray
                        )
                    }

                    Spacer(modifier = Modifier.height(8.dp))

                    Text(
                        text = question.questionText,
                        style = MaterialTheme.typography.titleMedium,
                        fontWeight = FontWeight.SemiBold,
                        color = Color.White
                    )

                    Spacer(modifier = Modifier.height(14.dp))

                    // Options list
                    Column(verticalArrangement = Arrangement.spacedBy(8.dp)) {
                        question.options.forEachIndexed { optIndex, optionText ->
                            val isChosen = userSelected.contains(optIndex)
                            Box(
                                modifier = Modifier
                                    .fillMaxWidth()
                                    .clip(RoundedCornerShape(12.dp))
                                    .background(if (isChosen) Color(0xFF1E3A8A) else DarkSurfaceVariant)
                                    .border(
                                        1.dp,
                                        if (isChosen) ElectricBlue else Color.Transparent,
                                        RoundedCornerShape(12.dp)
                                    )
                                    .clickable { onSelectAnswer(qIndex, optIndex, isMultiSelect) }
                                    .padding(12.dp)
                            ) {
                                Row(verticalAlignment = Alignment.CenterVertically) {
                                    Box(
                                        modifier = Modifier
                                            .size(24.dp)
                                            .clip(CircleShape)
                                            .background(if (isChosen) ElectricBlue else Color(0xFF334155)),
                                        contentAlignment = Alignment.Center
                                    ) {
                                        Text(
                                            text = ('A' + optIndex).toString(),
                                            fontWeight = FontWeight.Bold,
                                            fontSize = 11.sp,
                                            color = if (isChosen) Color(0xFF03142B) else Color.White
                                        )
                                    }
                                    Spacer(modifier = Modifier.width(10.dp))
                                    Text(
                                        text = optionText,
                                        style = MaterialTheme.typography.bodyMedium,
                                        color = if (isChosen) Color.White else Color(0xFFE2E8F0)
                                    )
                                }
                            }
                        }
                    }
                }
            }

            // Bottom Navigation Controls
            Row(
                modifier = Modifier.fillMaxWidth(),
                horizontalArrangement = Arrangement.SpaceBetween,
                verticalAlignment = Alignment.CenterVertically
            ) {
                OutlinedButton(
                    onClick = { onNavigateQuestion(qIndex - 1) },
                    enabled = qIndex > 0,
                    shape = RoundedCornerShape(10.dp)
                ) {
                    Icon(Icons.AutoMirrored.Filled.ArrowBack, contentDescription = "Prev")
                    Spacer(modifier = Modifier.width(4.dp))
                    Text("Prev")
                }

                if (qIndex < state.questions.size - 1) {
                    Button(
                        onClick = { onNavigateQuestion(qIndex + 1) },
                        colors = ButtonDefaults.buttonColors(containerColor = VioletAccent),
                        shape = RoundedCornerShape(10.dp)
                    ) {
                        Text("Next", color = Color.White)
                        Spacer(modifier = Modifier.width(4.dp))
                        Icon(Icons.AutoMirrored.Filled.ArrowForward, contentDescription = "Next")
                    }
                } else {
                    Button(
                        onClick = onSubmit,
                        colors = ButtonDefaults.buttonColors(containerColor = EmeraldGreen),
                        shape = RoundedCornerShape(10.dp),
                        modifier = Modifier.testTag("submit_exam_button")
                    ) {
                        Icon(Icons.Default.Done, contentDescription = "Submit")
                        Spacer(modifier = Modifier.width(4.dp))
                        Text("Submit Exam", fontWeight = FontWeight.Bold, color = Color.White)
                    }
                }
            }
        }
    }
}

@Composable
private fun TestResultReport(
    result: TestResult,
    questions: List<com.example.data.model.QuizQuestion>,
    userAnswers: Map<Int, List<Int>>,
    onDismiss: () -> Unit
) {
    Surface(
        modifier = Modifier
            .fillMaxSize()
            .testTag("test_result_report_screen"),
        color = DarkBackground
    ) {
        LazyColumn(
            modifier = Modifier
                .fillMaxSize()
                .padding(16.dp),
            contentPadding = PaddingValues(bottom = 100.dp),
            verticalArrangement = Arrangement.spacedBy(14.dp)
        ) {
            item {
                Card(
                    shape = RoundedCornerShape(18.dp),
                    colors = CardDefaults.cardColors(containerColor = Color(0xFF131D2D)),
                    border = CardDefaults.outlinedCardBorder().copy(
                        brush = Brush.horizontalGradient(listOf(EmeraldGreen, ElectricBlue))
                    ),
                    modifier = Modifier.fillMaxWidth()
                ) {
                    Column(
                        modifier = Modifier.padding(20.dp),
                        horizontalAlignment = Alignment.CenterHorizontally
                    ) {
                        Icon(Icons.Default.EmojiEvents, contentDescription = "Trophy", tint = AmberGold, modifier = Modifier.size(48.dp))
                        Spacer(modifier = Modifier.height(8.dp))
                        Text(
                            text = "${result.accuracyPercent}% ACCURACY",
                            style = MaterialTheme.typography.displaySmall,
                            fontWeight = FontWeight.ExtraBold,
                            color = Color.White
                        )
                        Text(
                            text = "${result.scoredMarks} / ${result.totalMarks} Marks Scored • ${result.subjectName}",
                            style = MaterialTheme.typography.bodyMedium,
                            color = ElectricBlue
                        )
                        Spacer(modifier = Modifier.height(8.dp))
                        Text(
                            text = result.feedback,
                            style = MaterialTheme.typography.bodySmall,
                            color = Color(0xFFCBD5E1),
                            textAlign = TextAlign.Center
                        )
                    }
                }
            }

            // Stats breakdown
            item {
                Row(
                    modifier = Modifier.fillMaxWidth(),
                    horizontalArrangement = Arrangement.spacedBy(10.dp)
                ) {
                    StatCardSmall("Correct", "${result.correctCount}", EmeraldGreen, Modifier.weight(1f))
                    StatCardSmall("Incorrect", "${result.incorrectCount}", CrimsonCoral, Modifier.weight(1f))
                    StatCardSmall("Time Taken", "${result.durationSeconds}s", ElectricBlue, Modifier.weight(1f))
                }
            }

            // Detailed question by question explanation
            item {
                Text(
                    text = "Question-by-Question Diagnostic Review",
                    style = MaterialTheme.typography.titleMedium,
                    fontWeight = FontWeight.Bold,
                    color = Color.White
                )
            }

            items(questions.indices.toList()) { i ->
                val q = questions[i]
                val userChosen = userAnswers[i] ?: emptyList()
                val isCorrect = userChosen.sorted() == q.correctIndices.sorted()

                Card(
                    shape = RoundedCornerShape(12.dp),
                    colors = CardDefaults.cardColors(containerColor = DarkSurface),
                    border = CardDefaults.outlinedCardBorder().copy(
                        brush = Brush.linearGradient(
                            if (isCorrect) listOf(EmeraldGreen.copy(0.6f), EmeraldGreen.copy(0.2f))
                            else listOf(CrimsonCoral.copy(0.6f), CrimsonCoral.copy(0.2f))
                        )
                    ),
                    modifier = Modifier.fillMaxWidth()
                ) {
                    Column(modifier = Modifier.padding(14.dp)) {
                        Row(verticalAlignment = Alignment.CenterVertically) {
                            Icon(
                                imageVector = if (isCorrect) Icons.Default.Check else Icons.Default.Close,
                                contentDescription = null,
                                tint = if (isCorrect) EmeraldGreen else CrimsonCoral,
                                modifier = Modifier.size(18.dp)
                            )
                            Spacer(modifier = Modifier.width(6.dp))
                            Text(
                                text = "Question ${i + 1} (${if (isCorrect) "+${q.marks} Mark" else "0 Marks"})",
                                fontWeight = FontWeight.Bold,
                                color = if (isCorrect) EmeraldGreen else CrimsonCoral,
                                fontSize = 12.sp
                            )
                        }
                        Spacer(modifier = Modifier.height(4.dp))
                        Text(text = q.questionText, color = Color.White, fontWeight = FontWeight.SemiBold, style = MaterialTheme.typography.bodyMedium)
                        Spacer(modifier = Modifier.height(8.dp))
                        Text(
                            text = "💡 NCERT Explanation: ${q.explanation}",
                            color = Color(0xFFA7F3D0),
                            style = MaterialTheme.typography.bodySmall
                        )
                    }
                }
            }

            // Action
            item {
                Button(
                    onClick = onDismiss,
                    modifier = Modifier
                        .fillMaxWidth()
                        .height(48.dp),
                    colors = ButtonDefaults.buttonColors(containerColor = ElectricBlue),
                    shape = RoundedCornerShape(12.dp)
                ) {
                    Text("Return to Hub (+XP Saved)", fontWeight = FontWeight.Bold, color = Color(0xFF03142B))
                }
            }
        }
    }
}

@Composable
private fun StatCardSmall(
    title: String,
    value: String,
    accent: Color,
    modifier: Modifier = Modifier
) {
    Card(
        shape = RoundedCornerShape(12.dp),
        colors = CardDefaults.cardColors(containerColor = DarkSurface),
        modifier = modifier
    ) {
        Column(
            modifier = Modifier.padding(12.dp),
            horizontalAlignment = Alignment.CenterHorizontally
        ) {
            Text(text = value, style = MaterialTheme.typography.titleMedium, fontWeight = FontWeight.Bold, color = accent)
            Text(text = title, style = MaterialTheme.typography.labelSmall, color = Color.Gray, fontSize = 10.sp)
        }
    }
}
