package com.example.ui.components

import androidx.compose.animation.core.FastOutSlowInEasing
import androidx.compose.animation.core.RepeatMode
import androidx.compose.animation.core.animateFloat
import androidx.compose.animation.core.infiniteRepeatable
import androidx.compose.animation.core.rememberInfiniteTransition
import androidx.compose.animation.core.tween
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.clickable
import androidx.compose.foundation.interaction.MutableInteractionSource
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.draw.scale
import androidx.compose.ui.draw.shadow
import androidx.compose.ui.graphics.Brush
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.platform.testTag
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.FontFamily
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.Dp
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.example.R
import com.example.ui.theme.AmberGold
import com.example.ui.theme.DarkSurface
import com.example.ui.theme.ElectricBlue
import com.example.ui.theme.VioletAccent

enum class JnvLogoVariant {
    SPLASH,    // Large, grand emblem with golden aura glow & motto for splash/welcome
    HEADER,    // Crisp, horizontal lockup for AppTopBar
    BADGE,     // Medium circular badge for dialogs and hero banners
    COMPACT    // Small crest for list items, peer cards, and chips
}

/**
 * Official JNV Emblem and Brand Logo Component
 * Designed for Jawahar Navodaya Vidyalaya brand identity, featuring:
 * - Authentic JNV Torch and Sacred Book Crest
 * - Official Sanskrit Motto: "॥ प्रज्ञानं ब्रह्म ॥" (Wisdom is Divine)
 * - Animated Gold Aura Breathing Transition
 * - Responsive variants for Splash, Header, Badge, and Compact contexts
 */
@Composable
fun JnvEmblemLogo(
    modifier: Modifier = Modifier,
    variant: JnvLogoVariant = JnvLogoVariant.HEADER,
    size: Dp? = null,
    showGlow: Boolean = true,
    showMotto: Boolean = true,
    showSubtitle: Boolean = true,
    subtitleText: String? = null,
    onClick: (() -> Unit)? = null
) {
    when (variant) {
        JnvLogoVariant.SPLASH -> {
            SplashJnvEmblem(
                modifier = modifier,
                emblemSize = size ?: 130.dp,
                showGlow = showGlow,
                showMotto = showMotto,
                showSubtitle = showSubtitle,
                customSubtitle = subtitleText,
                onClick = onClick
            )
        }
        JnvLogoVariant.HEADER -> {
            HeaderJnvEmblem(
                modifier = modifier,
                emblemSize = size ?: 42.dp,
                showSubtitle = showSubtitle,
                customSubtitle = subtitleText,
                onClick = onClick
            )
        }
        JnvLogoVariant.BADGE -> {
            BadgeJnvEmblem(
                modifier = modifier,
                emblemSize = size ?: 72.dp,
                showGlow = showGlow,
                showMotto = showMotto,
                onClick = onClick
            )
        }
        JnvLogoVariant.COMPACT -> {
            CompactJnvEmblem(
                modifier = modifier,
                emblemSize = size ?: 32.dp,
                onClick = onClick
            )
        }
    }
}

/**
 * Splash Variant: Majestic presentation with breathing aura, dual golden rings,
 * Sanskrit motto badge, and national educational authority titles.
 */
@Composable
private fun SplashJnvEmblem(
    modifier: Modifier = Modifier,
    emblemSize: Dp = 130.dp,
    showGlow: Boolean = true,
    showMotto: Boolean = true,
    showSubtitle: Boolean = true,
    customSubtitle: String? = null,
    onClick: (() -> Unit)? = null
) {
    val infiniteTransition = rememberInfiniteTransition(label = "jnv_splash_aura")
    val auraScale by infiniteTransition.animateFloat(
        initialValue = 1f,
        targetValue = 1.08f,
        animationSpec = infiniteRepeatable(
            animation = tween(1800, easing = FastOutSlowInEasing),
            repeatMode = RepeatMode.Reverse
        ),
        label = "aura_scale"
    )
    val auraAlpha by infiniteTransition.animateFloat(
        initialValue = 0.35f,
        targetValue = 0.75f,
        animationSpec = infiniteRepeatable(
            animation = tween(1800, easing = FastOutSlowInEasing),
            repeatMode = RepeatMode.Reverse
        ),
        label = "aura_alpha"
    )

    val goldBorderBrush = Brush.sweepGradient(
        listOf(
            AmberGold,
            Color(0xFFFFDF7A),
            VioletAccent,
            ElectricBlue,
            AmberGold
        )
    )

    Column(
        modifier = modifier.testTag("jnv_emblem_splash"),
        horizontalAlignment = Alignment.CenterHorizontally,
        verticalArrangement = Arrangement.Center
    ) {
        // Outer glowing aura box
        Box(
            contentAlignment = Alignment.Center,
            modifier = Modifier
                .then(
                    if (onClick != null) {
                        Modifier.clickable(onClick = onClick)
                    } else Modifier
                )
        ) {
            if (showGlow) {
                // Soft background ambient pulse
                Box(
                    modifier = Modifier
                        .size(emblemSize + 36.dp)
                        .scale(auraScale)
                        .clip(CircleShape)
                        .background(
                            Brush.radialGradient(
                                colors = listOf(
                                    AmberGold.copy(alpha = auraAlpha * 0.45f),
                                    VioletAccent.copy(alpha = auraAlpha * 0.25f),
                                    Color.Transparent
                                )
                            )
                        )
                )
            }

            // Outer metallic rim
            Box(
                modifier = Modifier
                    .size(emblemSize + 12.dp)
                    .clip(CircleShape)
                    .background(Color(0xFF070B16))
                    .border(2.5.dp, goldBorderBrush, CircleShape)
                    .shadow(12.dp, CircleShape, spotColor = AmberGold),
                contentAlignment = Alignment.Center
            ) {
                // Inner bezel
                Box(
                    modifier = Modifier
                        .size(emblemSize + 4.dp)
                        .clip(CircleShape)
                        .background(Color(0xFF0F172A))
                        .border(1.dp, Color(0x66FFD700), CircleShape),
                    contentAlignment = Alignment.Center
                ) {
                    // Authentic JNV Crest Image
                    Image(
                        painter = painterResource(id = R.drawable.img_jnv_logo_1793616036833),
                        contentDescription = "Official Jawahar Navodaya Vidyalaya Emblem",
                        contentScale = ContentScale.Crop,
                        modifier = Modifier
                            .size(emblemSize)
                            .clip(CircleShape)
                    )
                }
            }
        }

        Spacer(modifier = Modifier.height(18.dp))

        if (showMotto) {
            // Sanskrit Motto Banner: ॥ प्रज्ञानं ब्रह्म ॥
            Surface(
                shape = RoundedCornerShape(20.dp),
                color = Color(0x26FFB800),
                border = androidx.compose.foundation.BorderStroke(
                    1.dp,
                    Brush.horizontalGradient(listOf(AmberGold.copy(0.4f), AmberGold, AmberGold.copy(0.4f)))
                ),
                modifier = Modifier.padding(horizontal = 16.dp)
            ) {
                Row(
                    modifier = Modifier.padding(horizontal = 14.dp, vertical = 6.dp),
                    verticalAlignment = Alignment.CenterVertically
                ) {
                    Text(
                        text = "॥  प्रज्ञानं ब्रह्म  ॥",
                        style = MaterialTheme.typography.titleMedium,
                        color = AmberGold,
                        fontWeight = FontWeight.ExtraBold,
                        letterSpacing = 1.5.sp
                    )
                    Spacer(modifier = Modifier.width(8.dp))
                    Text(
                        text = "• Wisdom is Divine",
                        style = MaterialTheme.typography.bodySmall,
                        color = Color(0xFFE2E8F0),
                        fontWeight = FontWeight.Medium,
                        fontSize = 12.sp
                    )
                }
            }
            Spacer(modifier = Modifier.height(12.dp))
        }

        // Institutional Title
        Text(
            text = "JAWAHAR NAVODAYA VIDYALAYA",
            style = MaterialTheme.typography.titleLarge,
            color = Color.White,
            fontWeight = FontWeight.Black,
            letterSpacing = 1.2.sp,
            textAlign = TextAlign.Center
        )

        if (showSubtitle) {
            Spacer(modifier = Modifier.height(4.dp))
            Text(
                text = customSubtitle ?: "नवोदय विद्यालय समिति • Ministry of Education, Govt. of India",
                style = MaterialTheme.typography.bodySmall,
                color = Color(0xFF94A3B8),
                fontSize = 11.5.sp,
                textAlign = TextAlign.Center
            )
        }
    }
}

/**
 * Header Variant: Compact, professional horizontal lockup for top bar navigation.
 * Cleanly displays the official emblem with gold rim, institution title, and student stream/branch tag.
 */
@Composable
private fun HeaderJnvEmblem(
    modifier: Modifier = Modifier,
    emblemSize: Dp = 42.dp,
    showSubtitle: Boolean = true,
    customSubtitle: String? = null,
    onClick: (() -> Unit)? = null
) {
    val goldBrush = Brush.sweepGradient(
        listOf(AmberGold, Color(0xFFFFE082), AmberGold)
    )

    Row(
        modifier = modifier
            .testTag("jnv_emblem_header")
            .then(
                if (onClick != null) {
                    Modifier
                        .clip(RoundedCornerShape(24.dp))
                        .clickable(onClick = onClick)
                        .padding(horizontal = 4.dp, vertical = 2.dp)
                } else Modifier
            ),
        verticalAlignment = Alignment.CenterVertically
    ) {
        // Crest in gold ring
        Box(
            modifier = Modifier
                .size(emblemSize)
                .clip(CircleShape)
                .background(Color(0xFF070B16))
                .border(1.6.dp, goldBrush, CircleShape),
            contentAlignment = Alignment.Center
        ) {
            Image(
                painter = painterResource(id = R.drawable.img_jnv_logo_1793616036833),
                contentDescription = "JNV Crest",
                contentScale = ContentScale.Crop,
                modifier = Modifier
                    .size(emblemSize - 4.dp)
                    .clip(CircleShape)
            )
        }

        if (showSubtitle || customSubtitle != null) {
            Spacer(modifier = Modifier.width(10.dp))
            Column {
                Row(verticalAlignment = Alignment.CenterVertically) {
                    Text(
                        text = "JNV",
                        style = MaterialTheme.typography.titleSmall,
                        fontWeight = FontWeight.Black,
                        color = AmberGold,
                        letterSpacing = 0.8.sp
                    )
                    Spacer(modifier = Modifier.width(5.dp))
                    Text(
                        text = "CLASS 12",
                        style = MaterialTheme.typography.titleSmall,
                        fontWeight = FontWeight.Bold,
                        color = MaterialTheme.colorScheme.onBackground
                    )
                }
                Text(
                    text = customSubtitle ?: "Academic Hub • Navodayan",
                    style = MaterialTheme.typography.bodySmall,
                    color = Color(0xFF94A3B8),
                    fontSize = 10.5.sp,
                    fontWeight = FontWeight.Medium
                )
            }
        }
    }
}

/**
 * Badge Variant: Medium circular emblem for pop-ups, modal sheets, and hero cards.
 */
@Composable
private fun BadgeJnvEmblem(
    modifier: Modifier = Modifier,
    emblemSize: Dp = 72.dp,
    showGlow: Boolean = true,
    showMotto: Boolean = false,
    onClick: (() -> Unit)? = null
) {
    val goldBorderBrush = Brush.linearGradient(
        listOf(AmberGold, VioletAccent, ElectricBlue, AmberGold)
    )

    Column(
        modifier = modifier.testTag("jnv_emblem_badge"),
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        Box(
            contentAlignment = Alignment.Center,
            modifier = Modifier.then(
                if (onClick != null) {
                    Modifier.clickable(onClick = onClick)
                } else Modifier
            )
        ) {
            if (showGlow) {
                Box(
                    modifier = Modifier
                        .size(emblemSize + 16.dp)
                        .clip(CircleShape)
                        .background(
                            Brush.radialGradient(
                                colors = listOf(
                                    AmberGold.copy(alpha = 0.35f),
                                    Color.Transparent
                                )
                            )
                        )
                )
            }

            Box(
                modifier = Modifier
                    .size(emblemSize)
                    .clip(CircleShape)
                    .background(Color(0xFF070B16))
                    .border(2.dp, goldBorderBrush, CircleShape)
                    .shadow(8.dp, CircleShape),
                contentAlignment = Alignment.Center
            ) {
                Image(
                    painter = painterResource(id = R.drawable.img_jnv_logo_1793616036833),
                    contentDescription = "JNV Badge Crest",
                    contentScale = ContentScale.Crop,
                    modifier = Modifier
                        .size(emblemSize - 4.dp)
                        .clip(CircleShape)
                )
            }
        }

        if (showMotto) {
            Spacer(modifier = Modifier.height(8.dp))
            Text(
                text = "॥ प्रज्ञानं ब्रह्म ॥",
                style = MaterialTheme.typography.labelMedium,
                color = AmberGold,
                fontWeight = FontWeight.Bold,
                fontSize = 11.sp
            )
        }
    }
}

/**
 * Compact Variant: Lightweight icon with gold outline for chips, list items, and mini avatars.
 */
@Composable
private fun CompactJnvEmblem(
    modifier: Modifier = Modifier,
    emblemSize: Dp = 32.dp,
    onClick: (() -> Unit)? = null
) {
    Box(
        modifier = modifier
            .size(emblemSize)
            .clip(CircleShape)
            .background(Color(0xFF070B16))
            .border(1.2.dp, AmberGold, CircleShape)
            .testTag("jnv_emblem_compact")
            .then(
                if (onClick != null) {
                    Modifier.clickable(onClick = onClick)
                } else Modifier
            ),
        contentAlignment = Alignment.Center
    ) {
        Image(
            painter = painterResource(id = R.drawable.img_jnv_logo_1793616036833),
            contentDescription = "JNV Mini Crest",
            contentScale = ContentScale.Crop,
            modifier = Modifier
                .size(emblemSize - 3.dp)
                .clip(CircleShape)
        )
    }
}
