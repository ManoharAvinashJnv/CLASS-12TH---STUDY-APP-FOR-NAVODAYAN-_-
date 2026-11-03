package com.example.ui.screens

import androidx.compose.animation.AnimatedVisibility
import androidx.compose.animation.core.Animatable
import androidx.compose.animation.core.FastOutSlowInEasing
import androidx.compose.animation.core.tween
import androidx.compose.animation.fadeIn
import androidx.compose.animation.scaleIn
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.navigationBarsPadding
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.statusBarsPadding
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.ArrowForward
import androidx.compose.material.icons.filled.AutoAwesome
import androidx.compose.material.icons.filled.Favorite
import androidx.compose.material.icons.filled.School
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.Icon
import androidx.compose.material3.LinearProgressIndicator
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableFloatStateOf
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.alpha
import androidx.compose.ui.graphics.Brush
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.testTag
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.example.ui.components.JnvEmblemLogo
import com.example.ui.components.JnvLogoVariant
import com.example.ui.theme.AmberGold
import com.example.ui.theme.DarkBackground
import com.example.ui.theme.DarkSurface
import com.example.ui.theme.ElectricBlue
import com.example.ui.theme.EmeraldGreen
import com.example.ui.theme.VioletAccent
import kotlinx.coroutines.delay

/**
 * Official JNV Splash Screen featuring the majestic emblem, Sanskrit motto,
 * and academic dedication for Class 12 Navodayans.
 */
@Composable
fun JnvSplashScreen(
    onDismissSplash: () -> Unit,
    modifier: Modifier = Modifier
) {
    var isContentVisible by remember { mutableStateOf(false) }
    var progress by remember { mutableFloatStateOf(0f) }

    LaunchedEffect(Unit) {
        isContentVisible = true
        // Smooth 2.2 second progress timer before automatic entry
        val steps = 50
        for (i in 1..steps) {
            delay(44)
            progress = i / steps.toFloat()
        }
        delay(200)
        onDismissSplash()
    }

    Box(
        modifier = modifier
            .fillMaxSize()
            .background(
                Brush.verticalGradient(
                    colors = listOf(
                        Color(0xFF030712), // Deep midnight abyss
                        Color(0xFF0B132B), // Navodaya deep navy
                        Color(0xFF1C1335), // Royal violet depth
                        Color(0xFF030712)
                    )
                )
            )
            .statusBarsPadding()
            .navigationBarsPadding()
            .testTag("jnv_splash_screen")
    ) {
        // Decorative radial glow behind center
        Box(
            modifier = Modifier
                .size(380.dp)
                .align(Alignment.Center)
                .background(
                    Brush.radialGradient(
                        colors = listOf(
                            VioletAccent.copy(alpha = 0.22f),
                            AmberGold.copy(alpha = 0.12f),
                            Color.Transparent
                        )
                    )
                )
        )

        Column(
            modifier = Modifier
                .fillMaxSize()
                .padding(horizontal = 24.dp, vertical = 20.dp),
            horizontalAlignment = Alignment.CenterHorizontally,
            verticalArrangement = Arrangement.SpaceBetween
        ) {
            // Top institutional emblem header
            AnimatedVisibility(
                visible = isContentVisible,
                enter = fadeIn(tween(600))
            ) {
                Row(
                    verticalAlignment = Alignment.CenterVertically,
                    horizontalArrangement = Arrangement.Center,
                    modifier = Modifier.padding(top = 16.dp)
                ) {
                    Icon(
                        imageVector = Icons.Default.School,
                        contentDescription = null,
                        tint = AmberGold,
                        modifier = Modifier.size(18.dp)
                    )
                    Spacer(modifier = Modifier.width(8.dp))
                    Text(
                        text = "NAVODAYA VIDYALAYA SAMITI",
                        style = MaterialTheme.typography.labelMedium,
                        color = Color(0xFFCBD5E1),
                        fontWeight = FontWeight.Bold,
                        letterSpacing = 1.4.sp
                    )
                }
            }

            // Center Hero: Official JNV Logo Emblem with animated aura & Sanskrit motto
            AnimatedVisibility(
                visible = isContentVisible,
                enter = fadeIn(tween(800)) + scaleIn(tween(800, easing = FastOutSlowInEasing), initialScale = 0.85f)
            ) {
                Column(
                    horizontalAlignment = Alignment.CenterHorizontally,
                    modifier = Modifier.padding(vertical = 12.dp)
                ) {
                    JnvEmblemLogo(
                        variant = JnvLogoVariant.SPLASH,
                        size = 135.dp,
                        showGlow = true,
                        showMotto = true,
                        showSubtitle = true,
                        subtitleText = "Autonomous Body under Ministry of Education, Govt. of India"
                    )

                    Spacer(modifier = Modifier.height(24.dp))

                    // Academic Tier Badge
                    Surface(
                        shape = RoundedCornerShape(12.dp),
                        color = Color(0x33000000),
                        border = androidx.compose.foundation.BorderStroke(
                            1.dp,
                            Brush.horizontalGradient(listOf(ElectricBlue, VioletAccent))
                        )
                    ) {
                        Text(
                            text = "CLASS 12 ULTIMATE ACADEMIC HUB",
                            style = MaterialTheme.typography.labelLarge,
                            fontWeight = FontWeight.ExtraBold,
                            color = ElectricBlue,
                            letterSpacing = 1.6.sp,
                            modifier = Modifier.padding(horizontal = 16.dp, vertical = 8.dp)
                        )
                    }

                    Spacer(modifier = Modifier.height(10.dp))

                    Text(
                        text = "Science • Humanities • NCERT • Focus Engine",
                        style = MaterialTheme.typography.bodyMedium,
                        color = Color(0xFF94A3B8),
                        fontWeight = FontWeight.Normal
                    )
                }
            }

            // Bottom Section: Progress bar, skip button, and developer attribution
            AnimatedVisibility(
                visible = isContentVisible,
                enter = fadeIn(tween(1000))
            ) {
                Column(
                    modifier = Modifier.fillMaxWidth(),
                    horizontalAlignment = Alignment.CenterHorizontally
                ) {
                    // Loading progress indicator
                    LinearProgressIndicator(
                        progress = { progress },
                        modifier = Modifier
                            .fillMaxWidth(0.65f)
                            .height(4.dp),
                        color = AmberGold,
                        trackColor = Color(0x33FFD700)
                    )

                    Spacer(modifier = Modifier.height(16.dp))

                    // Instant Enter Button with spring ripple
                    Button(
                        onClick = onDismissSplash,
                        colors = ButtonDefaults.buttonColors(
                            containerColor = ElectricBlue,
                            contentColor = Color.White
                        ),
                        shape = RoundedCornerShape(24.dp),
                        modifier = Modifier
                            .fillMaxWidth(0.75f)
                            .height(46.dp)
                            .testTag("splash_enter_hub_button")
                    ) {
                        Row(
                            verticalAlignment = Alignment.CenterVertically,
                            horizontalArrangement = Arrangement.Center
                        ) {
                            Text(
                                text = "ENTER ACADEMIC HUB",
                                fontWeight = FontWeight.Bold,
                                letterSpacing = 1.sp
                            )
                            Spacer(modifier = Modifier.width(8.dp))
                            Icon(
                                imageVector = Icons.Default.ArrowForward,
                                contentDescription = "Enter",
                                modifier = Modifier.size(18.dp)
                            )
                        }
                    }

                    Spacer(modifier = Modifier.height(18.dp))

                    // Developer & Navodayan Dedication Card
                    Card(
                        shape = RoundedCornerShape(12.dp),
                        colors = CardDefaults.cardColors(containerColor = Color(0xFF13112A)),
                        border = CardDefaults.outlinedCardBorder().copy(
                            brush = Brush.horizontalGradient(listOf(AmberGold.copy(0.7f), VioletAccent.copy(0.7f)))
                        ),
                        modifier = Modifier.fillMaxWidth()
                    ) {
                        Column(
                            modifier = Modifier
                                .fillMaxWidth()
                                .padding(horizontal = 14.dp, vertical = 10.dp),
                            horizontalAlignment = Alignment.CenterHorizontally
                        ) {
                            Row(verticalAlignment = Alignment.CenterVertically) {
                                Text(text = "MADE WITH ❤️ BY ", fontSize = 11.5.sp, color = Color.White, fontWeight = FontWeight.Bold)
                                Text(text = "MANOHAR AVINASH", fontSize = 12.sp, color = AmberGold, fontWeight = FontWeight.Black)
                            }
                            Text(
                                text = "🎓 Developed by & for NAVODAYANS (JNV) 🎓",
                                fontSize = 11.sp,
                                color = EmeraldGreen,
                                fontWeight = FontWeight.SemiBold,
                                modifier = Modifier.padding(top = 2.dp)
                            )
                        }
                    }

                    Spacer(modifier = Modifier.height(8.dp))
                }
            }
        }
    }
}
