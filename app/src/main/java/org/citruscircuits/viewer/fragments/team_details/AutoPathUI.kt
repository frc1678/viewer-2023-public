package org.citruscircuits.viewer.fragments.team_details

import android.graphics.Paint
import androidx.compose.foundation.Canvas
import androidx.compose.foundation.layout.ColumnScope
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.geometry.CornerRadius
import androidx.compose.ui.geometry.Offset
import androidx.compose.ui.geometry.Size
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.ImageBitmap
import androidx.compose.ui.graphics.drawscope.DrawScope
import androidx.compose.ui.graphics.drawscope.Fill
import androidx.compose.ui.graphics.drawscope.drawIntoCanvas
import androidx.compose.ui.graphics.nativeCanvas
import androidx.compose.ui.res.imageResource
import androidx.compose.ui.unit.dp
import org.citruscircuits.viewer.R
import org.citruscircuits.viewer.data.AutoPath

val intakeOffsets = mapOf(
    "one" to Offset(820f, 375f),
    "two" to Offset(820f, 525f),
    "three" to Offset(820f, 675f),
    "four" to Offset(820f, 825f)
)

val intakeLeftOffsets = mapOf(
    "one" to Offset(700f, 450f),
    "two" to Offset(700f, 600f),
    "three" to Offset(700f, 750f),
    "four" to Offset(700f, 900f)
)

@Composable
fun ColumnScope.AutoPath(startPosition: String, autoPath: AutoPath) {
    val fieldMapImage = ImageBitmap.imageResource(id = R.drawable.field_map_auto_paths)
    Canvas(
        modifier = Modifier
            .fillMaxWidth()
            .weight(1f)
            .padding(10.dp)
    ) {
        drawImage(fieldMapImage)
        if (autoPath.intake_1_piece != null) {
            drawRoundRect(
                color = if (autoPath.intake_1_piece == "O") Color.Yellow else Color.Magenta,
                topLeft = intakeOffsets[autoPath.intake_1_position] ?: Offset.Zero,
                size = Size(100f, 100f),
                cornerRadius = CornerRadius(10f),
            )
            drawText(
                text = "${autoPath.score_2_piece_successes}/${autoPath.matches_ran}",
                offset = intakeLeftOffsets[autoPath.intake_1_position] ?: Offset.Zero,
                size = 80f
            )
        }
        if (autoPath.intake_2_piece != null) {
            drawRoundRect(
                color = if (autoPath.intake_2_piece == "O") Color.Yellow else Color.Magenta,
                topLeft = intakeOffsets[autoPath.intake_2_position] ?: Offset.Zero,
                size = Size(100f, 100f),
                cornerRadius = CornerRadius(10f),
                style = Fill
            )
            drawText(
                text = "${autoPath.score_3_piece_successes}/${autoPath.matches_ran}",
                offset = intakeLeftOffsets[autoPath.intake_2_position] ?: Offset.Zero,
                size = 80f
            )
        }
        if (autoPath.auto_charge_level != "N") {
            drawRoundRect(
                color = Color.White,
                topLeft = Offset(385f, 560f),
                size = Size(200f, 200f),
                cornerRadius = CornerRadius(10f)
            )
            drawText(
                text = "${autoPath.auto_charge_successes}/${autoPath.matches_ran}",
                offset = Offset(400f, 700f),
                size = 96f
            )
        }
    }
}

fun DrawScope.drawText(text: String, offset: Offset, size: Float) = drawIntoCanvas {
    it.nativeCanvas.drawText(text, offset.x, offset.y, Paint().apply { textSize = size })
}
