package com.example.jileme.presentation.ui.nav

import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.clickable
import androidx.compose.foundation.interaction.MutableInteractionSource
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.navigationBarsPadding
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.LocalFireDepartment
import androidx.compose.material.icons.filled.Person
import androidx.compose.material.icons.filled.Spa
import androidx.compose.material.icons.filled.WaterDrop
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.draw.shadow
import androidx.compose.ui.graphics.Brush
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp

/**
 * 底栏绘制在主内容之上，阴影会伸进内容区；需同时：
 * 1）缩小 NavHost 可用高度
 * 2）可滚动页面末尾留出同等量级空白，才能把最后一整块卡片滚出遮挡区
 */
val FloatingBottomBarContentExtra = 56.dp

/** 日历等竖向滚动列表底部「垫高」，与 [FloatingBottomBarContentExtra] 叠加使用 */
val GlassBarScrollTailPadding = 96.dp

private data class TabItem(
    val route: String,
    val label: String,
    val icon: ImageVector
)

private val bottomTabs = listOf(
    TabItem(Screen.ModuleJie.route, "戒了么", Icons.Filled.Spa),
    TabItem(Screen.ModuleChou.route, "抽了么", Icons.Filled.LocalFireDepartment),
    TabItem(Screen.ModuleLa.route, "拉了么", Icons.Filled.WaterDrop),
    TabItem(Screen.Profile.route, "我的", Icons.Filled.Person)
)

/** 浅色磨砂玻璃底栏：高透白渐变 + 白边高光 + 柔阴影，接近 iOS 近期 Tab Bar 液态玻璃观感 */
private val GlassBarShape = RoundedCornerShape(36.dp)

/** 液态玻璃：偏低不透明度白，透出背后内容 */
private val GlassFillBrush = Brush.verticalGradient(
    colors = listOf(
        Color(0x8CFFFFFF),
        Color(0x5CFFFFFF)
    )
)

private val GlassTopSheenBrush = Brush.horizontalGradient(
    colors = listOf(
        Color(0x00FFFFFF),
        Color(0x40FFFFFF),
        Color(0x00FFFFFF)
    )
)

@Composable
fun GlassIosStyleBottomBar(
    currentRoute: String?,
    onTabSelected: (String) -> Unit,
    modifier: Modifier = Modifier
) {
    val scheme = MaterialTheme.colorScheme
    val primary = scheme.primary
    val iosUnselected = Color(0xFF8E8E93)

    Box(
        modifier = modifier
            .fillMaxWidth()
            .navigationBarsPadding()
            .padding(horizontal = 22.dp)
            .padding(top = 4.dp, bottom = 12.dp)
    ) {
        Box(
            modifier = Modifier
                .fillMaxWidth()
                .shadow(
                    elevation = 10.dp,
                    shape = GlassBarShape,
                    spotColor = Color(0x12000000),
                    ambientColor = Color(0x08000000)
                )
                .clip(GlassBarShape)
                .background(GlassFillBrush)
                .border(
                    width = 1.dp,
                    brush = Brush.verticalGradient(
                        colors = listOf(
                            Color(0x99FFFFFF),
                            Color(0x44FFFFFF)
                        )
                    ),
                    shape = GlassBarShape
                )
        ) {
            Column(modifier = Modifier.fillMaxWidth()) {
                Box(
                    modifier = Modifier
                        .fillMaxWidth()
                        .height(1.dp)
                        .background(GlassTopSheenBrush)
                )
                Row(
                    modifier = Modifier
                        .fillMaxWidth()
                        .padding(horizontal = 6.dp, vertical = 10.dp),
                    horizontalArrangement = Arrangement.SpaceEvenly,
                    verticalAlignment = Alignment.CenterVertically
                ) {
                bottomTabs.forEach { tab ->
                    val selected = currentRoute == tab.route
                    val interaction = remember { MutableInteractionSource() }
                    Column(
                        horizontalAlignment = Alignment.CenterHorizontally,
                        modifier = Modifier
                            .weight(1f)
                            .clip(RoundedCornerShape(24.dp))
                            .background(
                                if (selected) Color(0x1FFFFFFF) else Color.Transparent
                            )
                            .clickable(
                                interactionSource = interaction,
                                indication = null,
                                onClick = { onTabSelected(tab.route) }
                            )
                            .padding(vertical = 6.dp, horizontal = 2.dp)
                    ) {
                        Icon(
                            imageVector = tab.icon,
                            contentDescription = tab.label,
                            modifier = Modifier.size(24.dp),
                            tint = if (selected) primary else iosUnselected
                        )
                        Text(
                            text = tab.label,
                            style = MaterialTheme.typography.labelSmall.copy(
                                fontSize = 9.sp,
                                fontWeight = if (selected) FontWeight.SemiBold else FontWeight.Medium
                            ),
                            color = if (selected) primary else iosUnselected,
                            maxLines = 1,
                            overflow = TextOverflow.Ellipsis
                        )
                    }
                }
                }
            }
        }
    }
}
