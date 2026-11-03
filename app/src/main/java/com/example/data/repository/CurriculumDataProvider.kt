package com.example.data.repository

import com.example.data.local.ChapterEntity
import com.example.data.local.StudyTaskEntity
import com.example.data.local.StudentProfileEntity
import com.example.data.model.AcademicStream
import com.example.data.model.QuestionType
import com.example.data.model.QuizQuestion
import com.example.data.model.SubjectInfo

object CurriculumDataProvider {

    val scienceSubjects = listOf(
        SubjectInfo(
            id = "sci_physics",
            stream = AcademicStream.SCIENCE,
            name = "Physics",
            code = "042",
            bookTitle = "NCERT Physics Part 1 & 2",
            iconEmoji = "⚡",
            description = "Electrostatics, Optics, Magnetism, Modern Physics & Semiconductor Devices",
            colorHex = 0xFF38BDF8
        ),
        SubjectInfo(
            id = "sci_chemistry",
            stream = AcademicStream.SCIENCE,
            name = "Chemistry",
            code = "043",
            bookTitle = "NCERT Chemistry Part 1 & 2",
            iconEmoji = "🧪",
            description = "Solutions, Electrochemistry, Kinetics, d & f Block, Coordination, Organic Chemistry",
            colorHex = 0xFF10B981
        ),
        SubjectInfo(
            id = "sci_maths",
            stream = AcademicStream.SCIENCE,
            name = "Mathematics",
            code = "041",
            bookTitle = "NCERT Mathematics Part 1 & 2",
            iconEmoji = "📐",
            description = "Calculus, Integrals, Differential Equations, Vectors, 3D Geometry, Linear Programming",
            colorHex = 0xFF8B5CF6
        ),
        SubjectInfo(
            id = "sci_biology",
            stream = AcademicStream.SCIENCE,
            name = "Biology",
            code = "044",
            bookTitle = "NCERT Biology",
            iconEmoji = "🧬",
            description = "Reproduction, Genetics, Evolution, Biotechnology, Ecology & Environment",
            colorHex = 0xFF22C55E
        ),
        SubjectInfo(
            id = "sci_cs",
            stream = AcademicStream.SCIENCE,
            name = "Computer Science",
            code = "083",
            bookTitle = "NCERT Computer Science with Python",
            iconEmoji = "💻",
            description = "Computational Thinking, Python Functions, Data Files, SQL, Computer Networks",
            colorHex = 0xFF06B6D4
        )
    )

    val humanitiesSubjects = listOf(
        SubjectInfo(
            id = "hum_history",
            stream = AcademicStream.HUMANITIES,
            name = "History",
            code = "027",
            bookTitle = "Themes in Indian History Part I, II & III",
            iconEmoji = "🏺",
            description = "Harappan Archaeology, Political & Economic History, Bhakti-Sufi, Vijayanagara, Colonialism",
            colorHex = 0xFFF59E0B
        ),
        SubjectInfo(
            id = "hum_pol_sci",
            stream = AcademicStream.HUMANITIES,
            name = "Political Science",
            code = "028",
            bookTitle = "Contemporary World Politics & Politics in India",
            iconEmoji = "🏛️",
            description = "Cold War Era, Non-Aligned Movement, UN Security, Democratic Resurgence, Globalization",
            colorHex = 0xFFEC4899
        ),
        SubjectInfo(
            id = "hum_economics",
            stream = AcademicStream.HUMANITIES,
            name = "Economics",
            code = "030",
            bookTitle = "Introductory Macroeconomics & Indian Economic Development",
            iconEmoji = "📈",
            description = "National Income Accounting, Money & Banking, Balance of Payments, Five Year Plans",
            colorHex = 0xFF10B981
        ),
        SubjectInfo(
            id = "hum_geography",
            stream = AcademicStream.HUMANITIES,
            name = "Geography",
            code = "029",
            bookTitle = "Fundamentals of Human Geography & India: People and Economy",
            iconEmoji = "🌍",
            description = "Population Dynamics, Human Development, Primary Activities, Transport & International Trade",
            colorHex = 0xFF38BDF8
        ),
        SubjectInfo(
            id = "hum_psychology",
            stream = AcademicStream.HUMANITIES,
            name = "Psychology",
            code = "037",
            bookTitle = "Psychology Class XII",
            iconEmoji = "🧠",
            description = "Variations in Psychological Attributes, Self and Personality, Meeting Life Challenges, Disorders",
            colorHex = 0xFFA855F7
        ),
        SubjectInfo(
            id = "hum_english",
            stream = AcademicStream.HUMANITIES,
            name = "English Core",
            code = "301",
            bookTitle = "Flamingo & Vistas",
            iconEmoji = "📖",
            description = "The Last Lesson, Lost Spring, My Mother at Sixty-six, The Tiger King, Memories of Childhood",
            colorHex = 0xFFEAB308
        )
    )

    fun getSubjectsForStream(stream: AcademicStream): List<SubjectInfo> {
        return if (stream == AcademicStream.SCIENCE) scienceSubjects else humanitiesSubjects
    }

    fun getDefaultChapters(): List<ChapterEntity> {
        val now = System.currentTimeMillis()
        val oneDay = 86_400_000L
        return listOf(
            // SCIENCE - PHYSICS
            ChapterEntity(
                id = "phy_ch1",
                subjectId = "sci_physics",
                stream = AcademicStream.SCIENCE.name,
                chapterNo = 1,
                title = "Electric Charges and Fields",
                bookName = "Physics Part 1",
                difficulty = "Medium",
                estimatedHours = 12,
                status = "COMPLETED",
                completionPercent = 100,
                lastRevisedAt = now - 2 * oneDay,
                revisionCount = 2,
                nextRevisionDue = now - oneDay, // overdue for demo
                confidenceRating = 4,
                ncertPdfUrl = "https://ncert.nic.in/textbook.pdf?leph1=1-8",
                introduction = "Explores the fundamental properties of electric charges, Coulomb's inverse square law, superposition principle, continuous charge distributions, electric field lines, electric dipole in uniform field, and Gauss's law applications.",
                learningObjectives = "State and apply Coulomb's Law; Calculate electric flux and verify Gauss's Law; Deduce field for spherical shells and infinite charged sheets; Analyze electric dipole torque.",
                keyConcepts = "Coulomb's Law (F = k*q1*q2/r^2); Electric Field Intensity (E = F/q); Electric Flux (Φ = ∮ E · dA = q_enc / ε_0); Gauss's theorem; Dipole Moment (p = 2aq); Torque (τ = p × E).",
                formulasOrSummary = "E_axial = 2kp/r^3; E_equatorial = kp/r^3; Gauss's Law: ∮ E·dA = Q/ε₀; Field due to infinite wire: E = λ/(2πε₀r); Field due to plane sheet: E = σ/(2ε₀).",
                pyqNotes = "High weightage in CBSE Boards. 5-marker frequently asked: Proof of Gauss Law and field derivation for infinitely long charged wire."
            ),
            ChapterEntity(
                id = "phy_ch2",
                subjectId = "sci_physics",
                stream = AcademicStream.SCIENCE.name,
                chapterNo = 2,
                title = "Electrostatic Potential and Capacitance",
                bookName = "Physics Part 1",
                difficulty = "Medium",
                estimatedHours = 10,
                status = "COMPLETED",
                completionPercent = 100,
                lastRevisedAt = now - 5 * oneDay,
                revisionCount = 1,
                nextRevisionDue = now + 2 * oneDay,
                confidenceRating = 4,
                ncertPdfUrl = "https://ncert.nic.in/textbook.pdf?leph1=2-8",
                introduction = "Covers conservative nature of electrostatic fields, potential due to point charge & dipole, equipotential surfaces, capacitance of parallel plate capacitor, and dielectric effects.",
                learningObjectives = "Derive potential due to dipole; Understand equipotential surface properties; Calculate equivalent capacitance in series and parallel; Energy stored in capacitor.",
                keyConcepts = "Electrostatic Potential V = kq/r; Equipotential surfaces never intersect; Capacitance C = Q/V; Parallel plate capacitor C = ε₀A/d; Energy U = 1/2 CV².",
                formulasOrSummary = "V_dipole = kp cosθ / r²; C = Kε₀A/d (with dielectric); U = 1/2 CV² = Q²/(2C); Energy density u = 1/2 ε₀E².",
                pyqNotes = "Parallel plate capacitor with dielectric slab insertion derivation asked in 2020, 2023 CBSE Board Papers."
            ),
            ChapterEntity(
                id = "phy_ch3",
                subjectId = "sci_physics",
                stream = AcademicStream.SCIENCE.name,
                chapterNo = 3,
                title = "Current Electricity",
                bookName = "Physics Part 1",
                difficulty = "Hard",
                estimatedHours = 14,
                status = "IN_PROGRESS",
                completionPercent = 65,
                lastRevisedAt = now - 7 * oneDay,
                revisionCount = 1,
                nextRevisionDue = now + 4 * oneDay,
                confidenceRating = 3,
                ncertPdfUrl = "https://ncert.nic.in/textbook.pdf?leph1=3-8",
                introduction = "Electric current, drift velocity, Ohm's law, electrical resistivity, temperature dependence of resistance, Kirchhoff's laws, and Wheatstone bridge principle.",
                learningObjectives = "Derive relation between drift velocity and current (I = nAev_d); Apply Kirchhoff's Current & Voltage rules; Solve balanced Wheatstone bridge circuits.",
                keyConcepts = "Drift velocity v_d = eEτ/m; Current density J = σE; Ohm's law vector form; Temperature coefficient α; Kirchhoff's Loop & Junction rules; Wheatstone bridge condition P/Q = R/S.",
                formulasOrSummary = "I = n A e v_d; ρ = m/(n e² τ); V = E - Ir (discharging); Balanced Wheatstone: P/Q = R/S; Kirchhoff's junction: ΣI = 0; Loop rule: ΣΔV = 0.",
                pyqNotes = "Numerical questions on Kirchhoff's laws circuit analysis are guaranteed in CBSE 3-mark section."
            ),
            ChapterEntity(
                id = "phy_ch4",
                subjectId = "sci_physics",
                stream = AcademicStream.SCIENCE.name,
                chapterNo = 4,
                title = "Moving Charges and Magnetism",
                bookName = "Physics Part 1",
                difficulty = "Hard",
                estimatedHours = 14,
                status = "NOT_STARTED",
                completionPercent = 0,
                lastRevisedAt = 0L,
                revisionCount = 0,
                nextRevisionDue = 0L,
                confidenceRating = 2,
                ncertPdfUrl = "https://ncert.nic.in/textbook.pdf?leph1=4-8",
                introduction = "Biot-Savart law, Ampere's circuital law, magnetic force on current-carrying conductor, cyclotron, galvanometer conversion into ammeter and voltmeter.",
                learningObjectives = "Master Biot-Savart law for circular loop; Ampere circuital law applications; Torque on current loop; Moving coil galvanometer principle and sensitivity.",
                keyConcepts = "Lorentz Force F = q(E + v × B); Biot-Savart Law dB = (μ₀/4π) * (I dl × r̂)/r²; Ampere Law ∮ B·dl = μ₀I_enc; Force between parallel currents F/L = μ₀I₁I₂/(2πd).",
                formulasOrSummary = "B_axis_ring = μ₀ I R² / 2(R² + x²)^(3/2); B_solenoid = μ₀ n I; τ = M × B = NIAB sinθ; S = (I_g * G)/(I - I_g) for Ammeter.",
                pyqNotes = "Galvanometer figure of merit and conversion derivation is a frequent 3-mark CBSE question."
            ),
            ChapterEntity(
                id = "phy_ch9",
                subjectId = "sci_physics",
                stream = AcademicStream.SCIENCE.name,
                chapterNo = 9,
                title = "Ray Optics and Optical Instruments",
                bookName = "Physics Part 2",
                difficulty = "Hard",
                estimatedHours = 16,
                status = "NOT_STARTED",
                completionPercent = 0,
                lastRevisedAt = 0L,
                revisionCount = 0,
                nextRevisionDue = 0L,
                confidenceRating = 3,
                ncertPdfUrl = "https://ncert.nic.in/textbook.pdf?leph2=1-6",
                introduction = "Reflection, refraction at spherical surfaces, lens maker's formula, dispersion through prism, optical instruments (compound microscope & astronomical telescope).",
                learningObjectives = "Derive Lens Maker's formula; Prism deviation formula; Magnifying power of Compound Microscope & Telescope in normal adjustment and near point.",
                keyConcepts = "Total Internal Reflection (sin C = 1/μ); Refraction at spherical surface: μ₂/v - μ₁/u = (μ₂ - μ₁)/R; Lens maker formula: 1/f = (μ - 1)(1/R₁ - 1/R₂); Telescope m = f_o / f_e.",
                formulasOrSummary = "1/f = 1/v - 1/u; Prism μ = sin((A+δ_m)/2) / sin(A/2); Microscope m = (-L/f_o) * (1 + D/f_e); Telescope normal: m = -f_o/f_e, L = f_o + f_e.",
                pyqNotes = "Lens maker formula and Compound Microscope ray diagram with derivation carry 5 marks."
            ),

            // SCIENCE - CHEMISTRY
            ChapterEntity(
                id = "chem_ch1",
                subjectId = "sci_chemistry",
                stream = AcademicStream.SCIENCE.name,
                chapterNo = 1,
                title = "Solutions",
                bookName = "Chemistry Part 1",
                difficulty = "Medium",
                estimatedHours = 10,
                status = "COMPLETED",
                completionPercent = 100,
                lastRevisedAt = now - 4 * oneDay,
                revisionCount = 2,
                nextRevisionDue = now - 2 * oneDay, // Overdue
                confidenceRating = 5,
                ncertPdfUrl = "https://ncert.nic.in/textbook.pdf?lech1=1-5",
                introduction = "Types of solutions, expression of concentration of solutions of solids in liquids, solubility of gases in liquids, solid solutions, Raoult's law, colligative properties, van't Hoff factor.",
                learningObjectives = "Calculate Molarity, Molality, Mole fraction; State Henry's Law & Raoult's Law; Solve numericals on elevation in boiling point and depression in freezing point.",
                keyConcepts = "Henry's law p = K_H * x; Raoult's law p_A = p°_A * x_A; Ideal vs Non-ideal solutions; Colligative properties: ΔT_b = K_b * m, ΔT_f = K_f * m, Π = iCRT.",
                formulasOrSummary = "Relative lowering: (p° - p)/p° = i * x_B; Osmotic pressure Π = i C R T; Van't Hoff factor i = 1 + (n-1)α for dissociation.",
                pyqNotes = "Numericals on abnormal molar mass with van't Hoff factor 'i' are guaranteed 3 marks."
            ),
            ChapterEntity(
                id = "chem_ch2",
                subjectId = "sci_chemistry",
                stream = AcademicStream.SCIENCE.name,
                chapterNo = 2,
                title = "Electrochemistry",
                bookName = "Chemistry Part 1",
                difficulty = "Hard",
                estimatedHours = 12,
                status = "IN_PROGRESS",
                completionPercent = 40,
                lastRevisedAt = 0L,
                revisionCount = 0,
                nextRevisionDue = 0L,
                confidenceRating = 3,
                ncertPdfUrl = "https://ncert.nic.in/textbook.pdf?lech1=2-5",
                introduction = "Redox reactions, conductance in electrolytic solutions, specific and molar conductivity, Kohlrausch's Law, electrolysis, Galvanic cells, Nernst equation, EMF of a cell.",
                learningObjectives = "Apply Nernst equation to galvanic cell; Calculate Gibbs free energy ΔG° = -nFE°_cell; Kohlrausch law for limiting molar conductivity.",
                keyConcepts = "Nernst equation E_cell = E°_cell - (0.0591/n) log Q; Molar conductivity Λ_m = 1000κ/M; Kohlrausch's law of independent migration; Faraday's laws of electrolysis.",
                formulasOrSummary = "E°_cell = E°_cathode - E°_anode; ΔrG° = -n F E°_cell; log K_c = n E°_cell / 0.0591; Faraday m = ZIt = (M * I * t)/(n * 96500).",
                pyqNotes = "Nernst equation calculation for Daniel cell and Kohlrausch law numericals are CBSE essentials."
            ),

            // SCIENCE - MATHEMATICS
            ChapterEntity(
                id = "math_ch1",
                subjectId = "sci_maths",
                stream = AcademicStream.SCIENCE.name,
                chapterNo = 1,
                title = "Relations and Functions",
                bookName = "Mathematics Part 1",
                difficulty = "Medium",
                estimatedHours = 8,
                status = "COMPLETED",
                completionPercent = 100,
                lastRevisedAt = now - 1 * oneDay,
                revisionCount = 3,
                nextRevisionDue = now + 6 * oneDay,
                confidenceRating = 4,
                ncertPdfUrl = "https://ncert.nic.in/textbook.pdf?lemh1=1-6",
                introduction = "Types of relations: reflexive, symmetric, transitive and equivalence relations. One to one and onto functions, composite functions, inverse of a function.",
                learningObjectives = "Identify and prove equivalence relations; Distinguish injective, surjective, and bijective mappings.",
                keyConcepts = "Equivalence Relation: Reflexive (a,a)∈R, Symmetric (a,b)∈R => (b,a)∈R, Transitive (a,b)∈R & (b,c)∈R => (a,c)∈R; Bijective = Injective + Surjective.",
                formulasOrSummary = "Number of reflexive relations = 2^(n² - n); Number of symmetric relations = 2^(n(n+1)/2); Condition for bijection: f(x1)=f(x2) => x1=x2 and range = codomain.",
                pyqNotes = "Proving that R is an equivalence relation is a classic 3-mark question."
            ),
            ChapterEntity(
                id = "math_ch5",
                subjectId = "sci_maths",
                stream = AcademicStream.SCIENCE.name,
                chapterNo = 5,
                title = "Continuity and Differentiability",
                bookName = "Mathematics Part 1",
                difficulty = "Hard",
                estimatedHours = 14,
                status = "IN_PROGRESS",
                completionPercent = 50,
                lastRevisedAt = now - 3 * oneDay,
                revisionCount = 1,
                nextRevisionDue = now + 1 * oneDay,
                confidenceRating = 3,
                ncertPdfUrl = "https://ncert.nic.in/textbook.pdf?lemh1=5-6",
                introduction = "Continuity and differentiability, chain rule, derivative of implicit functions, exponential and logarithmic functions, logarithmic differentiation, second order derivatives.",
                learningObjectives = "Test continuity at a point; Differentiate parametric forms; Apply logarithmic differentiation for complex products; Verify second order derivative equations.",
                keyConcepts = "Continuous at c if lim(x->c) f(x) = f(c); Differentiability implies continuity; Parametric dy/dx = (dy/dt)/(dx/dt); Logarithmic differentiation for y = [u(x)]^[v(x)].",
                formulasOrSummary = "d/dx(u/v) = (v u' - u v')/v²; d/dx(a^x) = a^x ln a; d/dx(tan⁻¹x) = 1/(1+x²); Parametric: d²y/dx² = [d/dt(dy/dx)] / (dx/dt).",
                pyqNotes = "Second order differential proof questions carry 5 marks consistently in CBSE."
            ),

            // SCIENCE - COMPUTER SCIENCE
            ChapterEntity(
                id = "cs_ch1",
                subjectId = "sci_cs",
                stream = AcademicStream.SCIENCE.name,
                chapterNo = 1,
                title = "Computational Thinking and Programming - Python",
                bookName = "Computer Science XII",
                difficulty = "Medium",
                estimatedHours = 12,
                status = "COMPLETED",
                completionPercent = 100,
                lastRevisedAt = now - 10 * oneDay,
                revisionCount = 1,
                nextRevisionDue = now - 3 * oneDay, // Overdue
                confidenceRating = 4,
                ncertPdfUrl = "https://ncert.nic.in/textbook.pdf?lecs1=1-6",
                introduction = "Review of Python fundamentals, user defined functions, parameter passing, scope of variables, text files, binary files with pickle module, and CSV files.",
                learningObjectives = "Write modular Python programs; File handling (read, write, append); Binary serialization with pickle.dump/load; CSV reader and writer operations.",
                keyConcepts = "Positional vs Default arguments; Global vs Local scope; File modes (r, w, a, r+, wb, rb); pickle.dump(data, file) and pickle.load(file); csv.DictWriter.",
                formulasOrSummary = "open(filename, mode); file.seek(offset, whence); file.tell(); pickle.dump(obj, f); pickle.load(f); csv.writer(f).writerow(row).",
                pyqNotes = "Write a function to count words in text file or search records in binary file: guaranteed 4 marks."
            ),

            // HUMANITIES - HISTORY
            ChapterEntity(
                id = "hist_ch1",
                subjectId = "hum_history",
                stream = AcademicStream.HUMANITIES.name,
                chapterNo = 1,
                title = "Bricks, Beads and Bones - The Harappan Civilisation",
                bookName = "Themes in Indian History Part 1",
                difficulty = "Medium",
                estimatedHours = 10,
                status = "COMPLETED",
                completionPercent = 100,
                lastRevisedAt = now - 3 * oneDay,
                revisionCount = 2,
                nextRevisionDue = now - oneDay, // Overdue
                confidenceRating = 4,
                ncertPdfUrl = "https://ncert.nic.in/textbook.pdf?lehs1=1-4",
                introduction = "Covers the Mature Harappan culture, urban planning of Mohenjo-Daro (Citadel and Lower Town), drainage system, subsistence strategies, craft production, trade networks, and decipherment debates.",
                learningObjectives = "Examine town planning & Great Bath of Mohenjodaro; Identify strategies for procuring materials like lapis lazuli and carnelian; Analyze reasons for the collapse of Harappa.",
                keyConcepts = "Grid pattern town planning; Great Bath with bitumen waterproofing; Chanhudaro craft production center; Seals, scripts and weights (chert stone binary system); Wheeler and Marshall archaeology.",
                formulasOrSummary = "Cunningham's confusion; Citadel (ritual/administrative) vs Lower Town (residential); Drainage system with covered drains and sumps; Decline hypotheses (climate change, deforestation, Aryan invasion debate).",
                pyqNotes = "Source-based questions and 8-mark question on Town Planning of Mohenjo-Daro are frequently set."
            ),
            ChapterEntity(
                id = "hist_ch2",
                subjectId = "hum_history",
                stream = AcademicStream.HUMANITIES.name,
                chapterNo = 2,
                title = "Kings, Farmers and Towns - Early States and Economies",
                bookName = "Themes in Indian History Part 1",
                difficulty = "Medium",
                estimatedHours = 10,
                status = "IN_PROGRESS",
                completionPercent = 60,
                lastRevisedAt = now - 2 * oneDay,
                revisionCount = 1,
                nextRevisionDue = now + 3 * oneDay,
                confidenceRating = 4,
                ncertPdfUrl = "https://ncert.nic.in/textbook.pdf?lehs1=2-4",
                introduction = "Traces the 16 Mahajanapadas, rise of Magadha, Mauryan Empire administration, Ashoka's Dhamma inscriptions, James Prinsep's decipherment of Brahmi & Kharosthi, and land grants.",
                learningObjectives = "Explain why Magadha emerged as the most powerful Mahajanapada; Assess Ashoka's Dhamma policies; Analyze the historical value of epigraphical sources.",
                keyConcepts = "Sixteen Mahajanapadas; Magadha advantages (iron mines, elephants, Ganga trade, fortified Rajgir/Pataliputra); Mauryan provinces and Megasthenes account; Inscriptions of Ashoka.",
                formulasOrSummary = "James Prinsep deciphered Brahmi (1838); Dhamma Mahamattas; Six committees for military coordination; Prashastis (Prayag Prashasti by Harishena); Agrahara land grants.",
                pyqNotes = "Administration of Mauryan Empire (Megasthenes + Arthashastra) is an 8-marker classic."
            ),

            // HUMANITIES - POLITICAL SCIENCE
            ChapterEntity(
                id = "pol_ch1",
                subjectId = "hum_pol_sci",
                stream = AcademicStream.HUMANITIES.name,
                chapterNo = 1,
                title = "The End of Bipolarity",
                bookName = "Contemporary World Politics",
                difficulty = "Medium",
                estimatedHours = 9,
                status = "COMPLETED",
                completionPercent = 100,
                lastRevisedAt = now - 5 * oneDay,
                revisionCount = 2,
                nextRevisionDue = now + 2 * oneDay,
                confidenceRating = 5,
                ncertPdfUrl = "https://ncert.nic.in/textbook.pdf?leps1=1-9",
                introduction = "The fall of the Berlin Wall (1989), collapse of the Soviet Union, Gorbachev's Glasnost & Perestroika, Shock Therapy in post-communist regimes, and emerging multi-polar geopolitics.",
                learningObjectives = "Identify causes for USSR disintegration; Evaluate consequences of Shock Therapy on Russian economy; Analyze Indo-Russian strategic partnership.",
                keyConcepts = "Berlin Wall demolition (9 Nov 1989); Gorbachev reforms; Disintegration in Dec 1991 (Russia, Ukraine, Belarus); Shock Therapy (mass privatization, mafia emergence, ruble collapse).",
                formulasOrSummary = "Soviet system: state ownership, subsidized welfare, bureaucratic centralization; CIS formation; Russia inherited UN Security Council seat; NATO expansion eastward.",
                pyqNotes = "Explain the features of Shock Therapy and its impact on the Russian currency and economy (6 marks)."
            ),

            // HUMANITIES - ECONOMICS
            ChapterEntity(
                id = "eco_ch1",
                subjectId = "hum_economics",
                stream = AcademicStream.HUMANITIES.name,
                chapterNo = 1,
                title = "National Income Accounting",
                bookName = "Introductory Macroeconomics",
                difficulty = "Hard",
                estimatedHours = 14,
                status = "IN_PROGRESS",
                completionPercent = 55,
                lastRevisedAt = now - 4 * oneDay,
                revisionCount = 1,
                nextRevisionDue = now + 1 * oneDay,
                confidenceRating = 3,
                ncertPdfUrl = "https://ncert.nic.in/textbook.pdf?leec1=1-6",
                introduction = "Circular flow of income, aggregate concepts (GDP, GNP, NDP, NNP at market price and factor cost), measurement of national income via value added, income, and expenditure methods.",
                learningObjectives = "Distinguish intermediate goods from final goods; Compute GDP via Value Added, Income, and Expenditure methods; Differentiate Nominal GDP vs Real GDP.",
                keyConcepts = "Two-sector circular flow; Final vs Intermediate goods; Factor Cost = Market Price - Net Indirect Taxes (NIT); Domestic to National: add NFIA; Gross to Net: subtract Depreciation.",
                formulasOrSummary = "GDP_mp = C + I + G + (X - M); NNP_fc = National Income; Value Added = Value of Output - Intermediate Consumption; GDP Deflator = (Nominal GDP / Real GDP) * 100.",
                pyqNotes = "Numericals computing National Income (NNP_fc) using Income and Expenditure methods are compulsory 6-markers."
            )
        )
    }

    fun getDefaultStudyTasks(): List<StudyTaskEntity> {
        return listOf(
            StudyTaskEntity(
                id = "task_1",
                stream = AcademicStream.SCIENCE.name,
                subjectId = "sci_physics",
                chapterTitle = "Electric Charges and Fields",
                timeSlot = "07:00 AM - 08:30 AM",
                durationMinutes = 90,
                isCompleted = true,
                taskType = "Formula Sheet & Gauss Law Derivation"
            ),
            StudyTaskEntity(
                id = "task_2",
                stream = AcademicStream.SCIENCE.name,
                subjectId = "sci_chemistry",
                chapterTitle = "Solutions",
                timeSlot = "10:00 AM - 11:30 AM",
                durationMinutes = 90,
                isCompleted = false,
                taskType = "Colligative Properties Numericals Practice"
            ),
            StudyTaskEntity(
                id = "task_3",
                stream = AcademicStream.SCIENCE.name,
                subjectId = "sci_maths",
                chapterTitle = "Continuity and Differentiability",
                timeSlot = "02:00 PM - 03:30 PM",
                durationMinutes = 90,
                isCompleted = false,
                taskType = "Parametric Differentiation Exercise 5.6"
            ),
            StudyTaskEntity(
                id = "task_4",
                stream = AcademicStream.SCIENCE.name,
                subjectId = "sci_cs",
                chapterTitle = "Computational Thinking - Python",
                timeSlot = "05:00 PM - 06:15 PM",
                durationMinutes = 75,
                isCompleted = false,
                taskType = "Binary File Pickle Serialization Code"
            ),
            // Humanities Tasks
            StudyTaskEntity(
                id = "task_hum_1",
                stream = AcademicStream.HUMANITIES.name,
                subjectId = "hum_history",
                chapterTitle = "Bricks, Beads and Bones",
                timeSlot = "08:00 AM - 09:30 AM",
                durationMinutes = 90,
                isCompleted = true,
                taskType = "Town Planning Map & Citadel Notes"
            ),
            StudyTaskEntity(
                id = "task_hum_2",
                stream = AcademicStream.HUMANITIES.name,
                subjectId = "hum_pol_sci",
                chapterTitle = "The End of Bipolarity",
                timeSlot = "11:00 AM - 12:30 PM",
                durationMinutes = 90,
                isCompleted = false,
                taskType = "Shock Therapy Causes & Impact Essay"
            ),
            StudyTaskEntity(
                id = "task_hum_3",
                stream = AcademicStream.HUMANITIES.name,
                subjectId = "hum_economics",
                chapterTitle = "National Income Accounting",
                timeSlot = "03:00 PM - 04:30 PM",
                durationMinutes = 90,
                isCompleted = false,
                taskType = "Expenditure Method Numericals Solve"
            )
        )
    }

    fun getDefaultProfile(): StudentProfileEntity {
        return StudentProfileEntity(
            id = 1,
            name = "Avinash Kumar",
            designation = "Class 12th Navodayan",
            jnvBranch = "JNV Varanasi",
            houseName = "Aravali House",
            avatarIcon = "avatar_1",
            activeStream = AcademicStream.SCIENCE.name,
            streakDays = 7,
            totalStudyMinutes = 1580,
            totalFocusMinutes = 960,
            chaptersCompleted = 5,
            testsAttempted = 14,
            avgTestAccuracy = 88,
            xpPoints = 4850,
            rankBadge = "National Navodaya Scholar",
            isLoggedIn = true
        )
    }

    fun getLivePeers(): List<com.example.data.model.LivePeerStudent> {
        return listOf(
            com.example.data.model.LivePeerStudent(id = "p1", name = "Manohar Avinash", designation = "Class 12th Navodayan", jnvSchool = "JNV Varanasi", subjectStudying = "Physics - Gauss Law", currentFocusMinutes = 48, streakDays = 14, isOnline = true),
            com.example.data.model.LivePeerStudent(id = "p2", name = "Rahul Ranjan", designation = "Class 12th Navodayan", jnvSchool = "JNV Patna", subjectStudying = "Chemistry - Solutions", currentFocusMinutes = 35, streakDays = 9, isOnline = true),
            com.example.data.model.LivePeerStudent(id = "p3", name = "Priya Kumari", designation = "Class 12th Navodayan", jnvSchool = "JNV Muzaffarpur", subjectStudying = "Maths - Derivatives", currentFocusMinutes = 52, streakDays = 11, isOnline = true),
            com.example.data.model.LivePeerStudent(id = "p4", name = "Aditya Vardhan", designation = "Class 12th Navodayan", jnvSchool = "JNV Lucknow", subjectStudying = "History - Harappan Town", currentFocusMinutes = 24, streakDays = 8, isOnline = true),
            com.example.data.model.LivePeerStudent(id = "p5", name = "Sneha Patel", designation = "Class 12th Navodayan", jnvSchool = "JNV Ahmedabad", subjectStudying = "Pol Sci - Shock Therapy", currentFocusMinutes = 40, streakDays = 12, isOnline = true)
        )
    }

    // High Quality Verified Question Banks for Tests
    fun getCuratedQuestions(subjectId: String): List<QuizQuestion> {
        return when (subjectId) {
            "sci_physics" -> listOf(
                QuizQuestion(
                    id = "q_phy_1",
                    chapterId = "phy_ch1",
                    questionText = "An electric dipole of moment p is placed in a uniform electric field E. What is the torque experienced by the dipole?",
                    type = QuestionType.MCQ,
                    options = listOf("p · E", "p × E", "E × p", "Zero at all angles"),
                    correctIndices = listOf(1),
                    explanation = "Torque τ on a dipole in a uniform electric field is given by vector cross product τ = p × E = pE sin θ.",
                    marks = 1
                ),
                QuizQuestion(
                    id = "q_phy_2",
                    chapterId = "phy_ch1",
                    questionText = "Assertion (A): Gauss's law is valid for any closed surface, regardless of its shape or size.\nReason (R): The total electric flux through a closed surface depends only on the net charge enclosed divided by ε₀.",
                    type = QuestionType.ASSERTION_REASON,
                    options = listOf(
                        "Both A and R are true and R is the correct explanation of A",
                        "Both A and R are true but R is not the correct explanation of A",
                        "A is true but R is false",
                        "A is false but R is true"
                    ),
                    correctIndices = listOf(0),
                    explanation = "Gauss's law ∮ E · dA = q_enc / ε₀ holds for arbitrary closed Gaussian surfaces enclosing charge q_enc.",
                    marks = 1
                ),
                QuizQuestion(
                    id = "q_phy_3",
                    chapterId = "phy_ch1",
                    questionText = "Which of the following statements about electric field lines are CORRECT?",
                    type = QuestionType.MULTI_SELECT,
                    options = listOf(
                        "They never intersect each other",
                        "They form closed loops in electrostatic fields",
                        "They originate on positive charge and terminate on negative charge",
                        "The tangent at any point gives the direction of electric field"
                    ),
                    correctIndices = listOf(0, 2, 3),
                    explanation = "Electrostatic field is conservative, so electrostatic field lines never form closed continuous loops.",
                    marks = 2
                ),
                QuizQuestion(
                    id = "q_phy_4",
                    chapterId = "phy_ch2",
                    questionText = "The work done in moving a test charge over an equipotential surface between two points is:",
                    type = QuestionType.MCQ,
                    options = listOf("Positive", "Negative", "Zero", "Dependent on the path taken"),
                    correctIndices = listOf(2),
                    explanation = "On an equipotential surface, potential difference ΔV = 0. Therefore, W = q * ΔV = 0.",
                    marks = 1
                ),
                QuizQuestion(
                    id = "q_phy_5",
                    chapterId = "phy_ch3",
                    questionText = "When a dielectric slab of dielectric constant K is inserted completely between the plates of an isolated charged capacitor, the capacitance becomes:",
                    type = QuestionType.MCQ,
                    options = listOf("K times", "1/K times", "Remains unchanged", "Increases by K²"),
                    correctIndices = listOf(0),
                    explanation = "Capacitance with dielectric slab is C = K * C₀.",
                    marks = 1
                )
            )
            "sci_chemistry" -> listOf(
                QuizQuestion(
                    id = "q_chm_1",
                    chapterId = "chem_ch1",
                    questionText = "Which concentration term is independent of temperature?",
                    type = QuestionType.MCQ,
                    options = listOf("Molarity", "Normality", "Molality", "Formality"),
                    correctIndices = listOf(2),
                    explanation = "Molality is defined per kilogram of solvent mass, and mass does not expand or contract with temperature changes.",
                    marks = 1
                ),
                QuizQuestion(
                    id = "q_chm_2",
                    chapterId = "chem_ch1",
                    questionText = "An azeotropic mixture of two liquids boils at a lower temperature than either of them when:",
                    type = QuestionType.MCQ,
                    options = listOf(
                        "It shows positive deviation from Raoult's law",
                        "It shows negative deviation from Raoult's law",
                        "It behaves like an ideal solution",
                        "It has van't Hoff factor i = 1"
                    ),
                    correctIndices = listOf(0),
                    explanation = "Minimum boiling azeotropes exhibit strong positive deviations from Raoult's law (intermolecular forces are weaker).",
                    marks = 1
                ),
                QuizQuestion(
                    id = "q_chm_3",
                    chapterId = "chem_ch2",
                    questionText = "The SI unit of molar conductivity (Λm) is:",
                    type = QuestionType.MCQ,
                    options = listOf("S cm² mol⁻¹", "S m² mol⁻¹", "Ω⁻¹ cm⁻¹", "S m⁻¹"),
                    correctIndices = listOf(1),
                    explanation = "Λm = κ / c, having units of S m⁻¹ / (mol m⁻³) = S m² mol⁻¹.",
                    marks = 1
                )
            )
            "sci_maths" -> listOf(
                QuizQuestion(
                    id = "q_mth_1",
                    chapterId = "math_ch1",
                    questionText = "A relation R on set A is an equivalence relation if and only if it is:",
                    type = QuestionType.MULTI_SELECT,
                    options = listOf("Reflexive", "Symmetric", "Transitive", "Anti-symmetric"),
                    correctIndices = listOf(0, 1, 2),
                    explanation = "By definition, an equivalence relation must satisfy reflexivity, symmetry, and transitivity.",
                    marks = 2
                ),
                QuizQuestion(
                    id = "q_mth_2",
                    chapterId = "math_ch5",
                    questionText = "If y = log(sec x + tan x), then dy/dx equals:",
                    type = QuestionType.MCQ,
                    options = listOf("sec x", "tan x", "sec² x", "cos x"),
                    correctIndices = listOf(0),
                    explanation = "dy/dx = (sec x tan x + sec² x) / (sec x + tan x) = sec x(tan x + sec x) / (sec x + tan x) = sec x.",
                    marks = 1
                )
            )
            "hum_history" -> listOf(
                QuizQuestion(
                    id = "q_his_1",
                    chapterId = "hist_ch1",
                    questionText = "The Great Bath and a granary structure were excavated at which major Harappan site?",
                    type = QuestionType.MCQ,
                    options = listOf("Kalibangan", "Mohenjo-Daro", "Lothal", "Dholavira"),
                    correctIndices = listOf(1),
                    explanation = "The Great Bath, an elaborate brick-lined public ritual bathing pool with gypsum mortar, was discovered at Mohenjo-Daro.",
                    marks = 1
                ),
                QuizQuestion(
                    id = "q_his_2",
                    chapterId = "hist_ch1",
                    questionText = "Assertion (A): Harappan drainage system was planned along a grid pattern.\nReason (R): Domestic wastewater first entered a sump outside before flowing into main street drains.",
                    type = QuestionType.ASSERTION_REASON,
                    options = listOf(
                        "Both A and R are true and R is the correct explanation of A",
                        "Both A and R are true but R is not the correct explanation of A",
                        "A is true but R is false",
                        "A is false but R is true"
                    ),
                    correctIndices = listOf(0),
                    explanation = "Every house was connected to the street drains laid out in intersecting grids, with individual inspection sumps to prevent clogging.",
                    marks = 1
                ),
                QuizQuestion(
                    id = "q_his_3",
                    chapterId = "hist_ch2",
                    questionText = "Who deciphered the Brahmi and Kharosthi scripts in 1838, unlocking Ashokan inscriptions?",
                    type = QuestionType.MCQ,
                    options = listOf("Alexander Cunningham", "James Prinsep", "John Marshall", "Mortimer Wheeler"),
                    correctIndices = listOf(1),
                    explanation = "James Prinsep, an officer in the mint of the East India Company, successfully deciphered Brahmi and Kharosthi in 1838.",
                    marks = 1
                )
            )
            "hum_pol_sci" -> listOf(
                QuizQuestion(
                    id = "q_pol_1",
                    chapterId = "pol_ch1",
                    questionText = "The Berlin Wall, symbol of the Cold War division, was brought down in which year?",
                    type = QuestionType.MCQ,
                    options = listOf("1985", "1989", "1991", "1993"),
                    correctIndices = listOf(1),
                    explanation = "The Berlin Wall was toppled by public protests on November 9, 1989, inaugurating the collapse of the Eastern Bloc.",
                    marks = 1
                ),
                QuizQuestion(
                    id = "q_pol_2",
                    chapterId = "pol_ch1",
                    questionText = "Which model of transition from communism to capitalism was prescribed by the World Bank and IMF in Russia and Eastern Europe?",
                    type = QuestionType.MCQ,
                    options = listOf("Marshall Plan", "Shock Therapy", "Glasnost", "Perestroika"),
                    correctIndices = listOf(1),
                    explanation = "Shock Therapy was the painful economic model of sudden privatization and market trade liberalization in post-Soviet states.",
                    marks = 1
                )
            )
            "hum_economics" -> listOf(
                QuizQuestion(
                    id = "q_eco_1",
                    chapterId = "eco_ch1",
                    questionText = "To obtain Net National Product at Factor Cost (NNP_fc) from GDP at Market Price (GDP_mp), we must:",
                    type = QuestionType.MCQ,
                    options = listOf(
                        "Subtract Depreciation, add NFIA, and subtract Net Indirect Taxes",
                        "Add Depreciation, subtract NFIA, and add Net Indirect Taxes",
                        "Subtract Depreciation and add subsidies only",
                        "Multiply by GDP deflator"
                    ),
                    correctIndices = listOf(0),
                    explanation = "NNP_fc = GDP_mp - Depreciation + NFIA - Net Indirect Taxes (NIT).",
                    marks = 1
                )
            )
            else -> listOf(
                QuizQuestion(
                    id = "q_gen_1",
                    chapterId = "gen_ch1",
                    questionText = "Which academic strategy is proven by cognitive science to maximize long-term retention?",
                    type = QuestionType.MCQ,
                    options = listOf("Cramming the night before", "Spaced Repetition & Active Recall", "Highlighting full textbooks", "Passive rereading"),
                    correctIndices = listOf(1),
                    explanation = "Spaced repetition and active recall interrupt the Ebbinghaus forgetting curve and build solid neural pathways.",
                    marks = 1
                )
            )
        }
    }
}
