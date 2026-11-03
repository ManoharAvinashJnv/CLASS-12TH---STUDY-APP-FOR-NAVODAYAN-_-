package com.example.ui.components

import androidx.compose.animation.animateColorAsState
import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.statusBarsPadding
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Bolt
import androidx.compose.material.icons.filled.LocalFireDepartment
import androidx.compose.material.icons.filled.Visibility
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Brush
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.testTag
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.example.data.model.AcademicStream
import com.example.data.model.StudentProfile
import com.example.ui.theme.AmberGold
import com.example.ui.theme.ElectricBlue
import com.example.ui.theme.VioletAccent

@Composable
fun AppTopBar(
    profile: StudentProfile,
    activeStream: AcademicStream,
    onStreamChange: (AcademicStream) -> Unit,
    onOpenProfile: () -> Unit,
    onOpenEyeTheme: () -> Unit,
    onOpenSplash: (() -> Unit)? = null,
    modifier: Modifier = Modifier
) {
    Surface(
        color = MaterialTheme.colorScheme.background,
        tonalElevation = 4.dp,
        modifier = modifier
            .fillMaxWidth()
            .statusBarsPadding()
    ) {
        Column(
            modifier = Modifier
                .fillMaxWidth()
                .padding(horizontal = 16.dp, vertical = 8.dp)
        ) {
            // Row 1: Official JNV Emblem Header & Quick Status Pills
            Row(
                modifier = Modifier.fillMaxWidth(),
                horizontalArrangement = Arrangement.SpaceBetween,
                verticalAlignment = Alignment.CenterVertically
            ) {
                // Official JNV Emblem Logo Component (Header Variant)
                JnvEmblemLogo(
                    variant = JnvLogoVariant.HEADER,
                    size = 40.dp,
                    showSubtitle = true,
                    subtitleText = "Navodaya Vidyalaya Samiti",
                    onClick = onOpenSplash ?: onOpenProfile,
                    modifier = Modifier.testTag("top_bar_jnv_emblem")
                )

                // Eye theme & Streak & XP Badges
                Row(
                    verticalAlignment = Alignment.CenterVertically,
                    horizontalArrangement = Arrangement.spacedBy(6.dp)
                ) {
                    // Eye Care Theme Button
                    Box(
                        modifier = Modifier
                            .clip(RoundedCornerShape(20.dp))
                            .background(Color(0xFF1E293B))
                            .clickable(onClick = onOpenEyeTheme)
                            .padding(horizontal = 8.dp, vertical = 5.dp)
                            .testTag("top_bar_eye_theme_button"),
                        contentAlignment = Alignment.Center
                    ) {
                        Row(verticalAlignment = Alignment.CenterVertically) {
                            Icon(
                                imageVector = Icons.Default.Visibility,
                                contentDescription = "Eye Care Themes",
                                tint = AmberGold,
                                modifier = Modifier.size(14.dp)
                            )
                            Spacer(modifier = Modifier.width(4.dp))
                            Text("Eye", color = Color.White, fontSize = 11.sp, fontWeight = FontWeight.Bold)
                        }
                    }

                    // Streak Pill
                    Row(
                        modifier = Modifier
                            .clip(RoundedCornerShape(20.dp))
                            .background(Color(0xFF2E1A0F))
                            .border(1.dp, Color(0xFF78350F), RoundedCornerShape(20.dp))
                            .padding(horizontal = 9.dp, vertical = 5.dp)
                            .testTag("top_bar_streak_pill"),
                        verticalAlignment = Alignment.CenterVertically
                    ) {
                        Icon(
                            imageVector = Icons.Default.LocalFireDepartment,
                            contentDescription = "Streak",
                            tint = AmberGold,
                            modifier = Modifier.size(15.dp)
                        )
                        Spacer(modifier = Modifier.width(3.dp))
                        Text(
                            text = "${profile.streakDays}d",
                            color = AmberGold,
                            fontWeight = FontWeight.Bold,
                            fontSize = 11.5.sp
                        )
                    }

                    // XP Pill
                    Row(
                        modifier = Modifier
                            .clip(RoundedCornerShape(20.dp))
                            .background(Color(0xFF132238))
                            .border(1.dp, Color(0xFF0284C7), RoundedCornerShape(20.dp))
                            .padding(horizontal = 9.dp, vertical = 5.dp)
                            .testTag("top_bar_xp_pill"),
                        verticalAlignment = Alignment.CenterVertically
                    ) {
                        Icon(
                            imageVector = Icons.Default.Bolt,
                            contentDescription = "Points XP",
                            tint = ElectricBlue,
                            modifier = Modifier.size(15.dp)
                        )
                        Spacer(modifier = Modifier.width(3.dp))
                        Text(
                            text = "${profile.xpPoints} XP",
                            color = ElectricBlue,
                            fontWeight = FontWeight.Bold,
                            fontSize = 11.5.sp
                        )
                    }
                }
            }

            Spacer(modifier = Modifier.height(8.dp))

            // Row 2: Navodayan Student Profile Chip
            Row(
                modifier = Modifier
                    .fillMaxWidth()
                    .clip(RoundedCornerShape(12.dp))
                    .background(MaterialTheme.colorScheme.surfaceVariant.copy(alpha = 0.5f))
                    .clickable(onClick = onOpenProfile)
                    .padding(horizontal = 10.dp, vertical = 6.dp)
                    .testTag("top_bar_profile_dp_button"),
                horizontalArrangement = Arrangement.SpaceBetween,
                verticalAlignment = Alignment.CenterVertically
            ) {
                Row(verticalAlignment = Alignment.CenterVertically) {
                    Box(
                        modifier = Modifier
                            .size(28.dp)
                            .clip(CircleShape)
                            .background(Color(0xFF1E1738))
                            .border(1.dp, AmberGold, CircleShape),
                        contentAlignment = Alignment.Center
                    ) {
                        Text(text = profile.avatarIcon, fontSize = 14.sp)
                    }
                    Spacer(modifier = Modifier.width(8.dp))
                    Column {
                        Row(verticalAlignment = Alignment.CenterVertically) {
                            Text(
                                text = profile.name,
                                style = MaterialTheme.typography.titleSmall,
                                fontWeight = FontWeight.Bold,
                                color = MaterialTheme.colorScheme.onBackground
                            )
                            Spacer(modifier = Modifier.width(4.dp))
                            Text(text = "✏️", fontSize = 10.sp)
                        }
                        Text(
                            text = "${profile.designation} • ${profile.jnvBranch} (${profile.houseName})",
                            style = MaterialTheme.typography.bodySmall,
                            color = AmberGold,
                            fontSize = 10.5.sp,
                            fontWeight = FontWeight.SemiBold
                        )
                    }
                }

                // Small indicator badge
                Surface(
                    shape = RoundedCornerShape(10.dp),
                    color = VioletAccent.copy(alpha = 0.15f),
                    border = androidx.compose.foundation.BorderStroke(0.8.dp, VioletAccent.copy(alpha = 0.4f))
                ) {
                    Text(
                        text = "JNV Crest",
                        style = MaterialTheme.typography.labelSmall,
                        color = VioletAccent,
                        fontSize = 10.sp,
                        fontWeight = FontWeight.Bold,
                        modifier = Modifier.padding(horizontal = 6.dp, vertical = 2.dp)
                    )
                }
            }

            Spacer(modifier = Modifier.height(8.dp))

            // Row 3: Dual Stream Toggle Selector (Science vs Humanities)
            Row(
                modifier = Modifier
                    .fillMaxWidth()
                    .clip(RoundedCornerShape(14.dp))
                    .background(MaterialTheme.colorScheme.surfaceVariant)
                    .border(1.dp, MaterialTheme.colorScheme.outline.copy(alpha = 0.5f), RoundedCornerShape(14.dp))
                    .padding(3.dp)
            ) {
                StreamTabButton(
                    stream = AcademicStream.SCIENCE,
                    isSelected = activeStream == AcademicStream.SCIENCE,
                    onClick = { onStreamChange(AcademicStream.SCIENCE) },
                    modifier = Modifier.weight(1f)
                )
                StreamTabButton(
                    stream = AcademicStream.HUMANITIES,
                    isSelected = activeStream == AcademicStream.HUMANITIES,
                    onClick = { onStreamChange(AcademicStream.HUMANITIES) },
                    modifier = Modifier.weight(1f)
                )
            }
        }
    }
}

@Composable
private fun StreamTabButton(
    stream: AcademicStream,
    isSelected: Boolean,
    onClick: () -> Unit,
    modifier: Modifier = Modifier
) {
    val bgColor by animateColorAsState(
        targetValue = if (isSelected) {
            if (stream == AcademicStream.SCIENCE) Color(0xFF0F3B66) else Color(0xFF5A380A)
        } else {
            Color.Transparent
        },
        label = "tab_bg"
    )

    val textColor by animateColorAsState(
        targetValue = if (isSelected) Color.White else MaterialTheme.colorScheme.onSurfaceVariant,
        label = "tab_text"
    )

    Box(
        modifier = modifier
            .testTag("stream_tab_${stream.name.lowercase()}")
            .clip(RoundedCornerShape(11.dp))
            .background(bgColor)
            .clickable(onClick = onClick)
            .padding(vertical = 8.dp),
        contentAlignment = Alignment.Center
    ) {
        Row(verticalAlignment = Alignment.CenterVertically) {
            Text(text = stream.iconEmoji, fontSize = 14.sp)
            Spacer(modifier = Modifier.width(6.dp))
            Text(
                text = stream.displayName,
                fontWeight = if (isSelected) FontWeight.Bold else FontWeight.Medium,
                fontSize = 13.sp,
                color = textColor
            )
        }
    }
}
