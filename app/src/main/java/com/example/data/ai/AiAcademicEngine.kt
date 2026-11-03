package com.example.data.ai

import com.example.data.model.AcademicStream
import com.example.data.model.QuestionType
import com.example.data.model.QuizQuestion
import com.example.data.model.TestDifficulty
import kotlinx.coroutines.delay
import java.util.UUID

object AiAcademicEngine {

    suspend fun explainConcept(stream: AcademicStream, subject: String, concept: String): String {
        delay(600) // Realistic interactive delay
        return when {
            concept.contains("Gauss", ignoreCase = true) || concept.contains("Flux", ignoreCase = true) ->
                "### 📘 Gauss's Law Simplified (Class 12 Physics)\n\n" +
                "**1. The Fundamental Principle:**\n" +
                "The total electric flux passing through any closed Gaussian surface equals 1/ε₀ times the total net charge enclosed within it.\n\n" +
                "∮ E · dA = Q_enclosed / ε₀\n\n" +
                "**2. Key Insights for CBSE Board Exam:**\n" +
                "• Flux is completely independent of the shape, size, or radius of the Gaussian surface.\n" +
                "• Charges outside the surface contribute to the field E at individual points, but their net flux through the closed surface is exactly zero.\n\n" +
                "**3. Standard Derivation Shortcut:**\n" +
                "For an infinitely long straight wire with linear charge density λ:\n" +
                "E = λ / (2πε₀r)\n\n" +
                "**💡 Board Exam Tip:** Always draw the cylindrical Gaussian surface with radius r and length L, and show that flux through flat circular caps is zero because E ⊥ A."

            concept.contains("Raoult", ignoreCase = true) || concept.contains("Solution", ignoreCase = true) ->
                "### 🧪 Raoult's Law & Azeotropes (Class 12 Chemistry)\n\n" +
                "**1. Core Statement:**\n" +
                "For a solution of volatile liquids, the partial vapor pressure of each component in the solution is directly proportional to its mole fraction present in the solution:\n\n" +
                "p_A = p°_A · x_A\n\n" +
                "**2. Positive vs. Negative Deviations:**\n" +
                "• **Positive Deviation:** A-B intermolecular attractions are weaker than A-A and B-B. ΔH_mix > 0, ΔV_mix > 0. Forms minimum boiling azeotropes (e.g., Ethanol + Water).\n" +
                "• **Negative Deviation:** A-B interactions are stronger (e.g., Acetone + Chloroform forming H-bonds). ΔH_mix < 0, ΔV_mix < 0. Forms maximum boiling azeotropes."

            concept.contains("Harappan", ignoreCase = true) || concept.contains("Mohenjo", ignoreCase = true) ->
                "### 🏺 Harappan Urban Architecture (Class 12 History)\n\n" +
                "**1. Dual Settlement Division:**\n" +
                "• **The Citadel:** Smaller, built on high mud-brick platforms, walled off. Housed monumental public structures like the Great Bath and Granary.\n" +
                "• **The Lower Town:** Much larger, residential zone. Carefully planned grid layout where streets intersected at clean 90° right angles.\n\n" +
                "**2. Unmatched Sanitation & Drains:**\n" +
                "Every single house had at least one wall adjoining a street. Wastewater first emptied into an indoor soak pit / sump so solid silt settled before flowing out into covered limestone-paved street channels."

            concept.contains("Shock Therapy", ignoreCase = true) ->
                "### 🏛️ Shock Therapy in Post-Soviet States (Class 12 Political Science)\n\n" +
                "**1. What was it?**\n" +
                "A painful, rapid transition from an authoritarian socialist system to a democratic capitalist system in Russia, Central Asia, and Eastern Europe, influenced by the World Bank and IMF.\n\n" +
                "**2. Catastrophic Consequences:**\n" +
                "• **The Largest Garage Sale in History:** 90% of state-owned enterprises were sold off to private individuals at throwaway prices.\n" +
                "• **Hyperinflation & Currency Collapse:** The Russian Ruble value plummeted dramatically, obliterating citizens' life savings.\n" +
                "• **Social Safety Net Destroyed:** Government subsidies on healthcare, food, and education were withdrawn, pushing 70%+ of the population into poverty."

            else ->
                "### 📚 Concept Breakdown: $concept\n\n" +
                "**Stream:** ${stream.displayName} | **Subject:** $subject\n\n" +
                "**1. NCERT Core Essence:**\n" +
                "In the CBSE Class 12 syllabus, this topic emphasizes foundational definitions, clear diagrammatic representations, and mathematical/historical contextual accuracy.\n\n" +
                "**2. Memory Hook & Summary:**\n" +
                "Focus on the underlying physical law or structural cause-and-effect relationship. Practice breaking down the topic into: (a) Definition, (b) Key parameters/agents, (c) Governing equations or historical dates, and (d) Practical significance.\n\n" +
                "**3. Recommended Revision Strategy:**\n" +
                "Attempt 3-5 active recall questions on this subtopic and write out the formal 3-point answer format preferred by CBSE examiners."
        }
    }

    suspend fun generateDynamicQuestions(
        stream: AcademicStream,
        subjectId: String,
        chapterTitle: String,
        difficulty: TestDifficulty,
        count: Int
    ): List<QuizQuestion> {
        delay(400)
        val curated = com.example.data.repository.CurriculumDataProvider.getCuratedQuestions(subjectId)
        if (curated.isNotEmpty()) {
            return curated.take(count)
        }
        // Generic syllabus question generator
        return (1..count).map { index ->
            QuizQuestion(
                id = "dyn_q_${UUID.randomUUID().toString().take(6)}",
                chapterId = chapterTitle,
                questionText = "Q$index: In the study of '$chapterTitle' (${difficulty.label}), which principle governs primary theoretical behavior?",
                type = QuestionType.MCQ,
                options = listOf(
                    "Standard NCERT conservation law & boundary conditions",
                    "Inverse quadratic divergence without damping",
                    "Spontaneous entropy decrease in closed systems",
                    "Isolated random fluctuation theory"
                ),
                correctIndices = listOf(0),
                explanation = "According to the Class 12 NCERT curriculum, primary principles adhere strictly to conservation and defined boundary frameworks.",
                marks = if (difficulty == TestDifficulty.EXAM_LEVEL) 2 else 1
            )
        }
    }
}
