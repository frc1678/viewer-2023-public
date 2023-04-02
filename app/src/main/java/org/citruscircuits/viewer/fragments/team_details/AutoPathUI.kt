package org.citruscircuits.viewer.fragments.team_details

import android.graphics.Paint
import androidx.compose.foundation.Canvas
import androidx.compose.foundation.layout.ColumnScope
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.scale
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
import nl.birdly.zoombox.zoomable
import org.citruscircuits.viewer.R
import org.citruscircuits.viewer.data.AutoPath

/**
 * Coordinates for the colored rectangles on the intake positions.
 *
 * Keys are intake positions, values are coordinates as [Offset]s.
 */
val intakeOffsets = mapOf(
    "one" to Offset(820f, 375f),
    "two" to Offset(820f, 525f),
    "three" to Offset(820f, 675f),
    "four" to Offset(820f, 825f)
)

/**
 * Coordinates for the text to the left of each intake position.
 *
 * Keys are intake positions, values are coordinates as [Offset]s.
 */
val intakeLeftOffsets = mapOf(
    "one" to Offset(650f, 450f),
    "two" to Offset(650f, 600f),
    "three" to Offset(650f, 750f),
    "four" to Offset(650f, 900f)
)

/**
 * Coordinates for the text to the right of each intake position.
 *
 * Keys are intake positions, values are coordinates as [Offset]s.
 */
val intakeRightOffsets = mapOf(
    "one" to Offset(950f, 450f),
    "two" to Offset(950f, 600f),
    "three" to Offset(950f, 750f),
    "four" to Offset(950f, 900f)
)

/**
 * Coordinates for the colored rectangles covering start positions.
 *
 * Keys are start positions, values are pairs of [Offset]s for the top left coordinates of each
 * rectangle to the [Size] of each rectangle.
 */
val startPosOffsets = mapOf(
    "1" to (Offset(180f, 320f) to Size(230f, 180f)),
    "2" to (Offset(180f, 500f) to Size(200f, 300f)),
    "3" to (Offset(180f, 800f) to Size(200f, 190f)),
    "4" to (Offset(360f, 800f) to Size(250f, 190f))
)

/**
 * Coordinates for the top text on start positions.
 *
 * Keys are start positions, values are coordinates as [Offset]s.
 */
val startPosTopOffsets = mapOf(
    "1" to Offset(230f, 400f),
    "2" to Offset(230f, 600f),
    "3" to Offset(230f, 880f),
    "4" to Offset(410f, 880f)
)

/**
 * Coordinates for the bottom text on start positions.
 *
 * Keys are start positions, values are coordinates as [Offset]s.
 */
val startPosBottomOffsets = mapOf(
    "1" to Offset(230f, 480f),
    "2" to Offset(230f, 680f),
    "3" to Offset(230f, 960f),
    "4" to Offset(410f, 960f)
)

/**
 * The map showing the given [autoPath].
 *
 * @param startPosition The starting position for this auto path.
 * @param autoPath The [AutoPath] object giving the data for this auto path.
 */
@Composable
fun ColumnScope.AutoPath(startPosition: String, autoPath: AutoPath) {
    // Get the image of the map
    val fieldMapImage = ImageBitmap.imageResource(id = R.drawable.field_map_auto_paths)
    // Canvas to show the field map and all the things on it
    Canvas(
        modifier = Modifier
            .fillMaxWidth()
            .weight(1f)
            .scale(0.8f)
            .zoomable()
    ) {
        // Draw the field map
        drawImage(fieldMapImage)
        // Draw the starting position rectangle, color depends on intake piece
        drawRect(
            color = if (autoPath.preloaded_gamepiece == "O") Color.Yellow else Color.Magenta,
            topLeft = startPosOffsets[startPosition]!!.first,
            size = startPosOffsets[startPosition]!!.second
        )
        // Draw the text for the success-to-attempt ratio of scoring the preloaded piece
        drawText(
            text = "${autoPath.score_1_piece_successes}/${autoPath.matches_ran}",
            offset = startPosTopOffsets[startPosition] ?: Offset.Zero,
            size = 60f
        )
        // Draw the text for the level the preloaded piece was scored at
        drawText(
            text = autoPath.score_1_position ?: "-",
            offset = startPosBottomOffsets[startPosition] ?: Offset.Zero,
            size = 60f
        )
        // Only show the intake if it exists
        if (autoPath.intake_1_piece != null) {
            // Draw the rectangle for the intake, color depends on intake piece
            drawRoundRect(
                color = if (autoPath.intake_1_piece == "cone") Color.Yellow else Color.Magenta,
                topLeft = intakeOffsets[autoPath.intake_1_position] ?: Offset.Zero,
                size = Size(100f, 100f),
                cornerRadius = CornerRadius(10f),
            )
            // Draw the text for the success-to-attempt ratio of scoring this piece after the intake
            drawText(
                text = "${autoPath.score_2_piece_successes}/${autoPath.matches_ran}",
                offset = intakeLeftOffsets[autoPath.intake_1_position] ?: Offset.Zero,
                size = 80f
            )
            // Draw the text for the level this piece was scored at after the intake
            drawText(
                text = autoPath.score_2_position ?: "-",
                offset = intakeRightOffsets[autoPath.intake_1_position] ?: Offset.Zero,
                size = 80f
            )
        }
        // Only show the intake if it exists
        if (autoPath.intake_2_piece != null) {
            // Draw the rectangle for the intake, color depends on intake piece
            drawRoundRect(
                color = if (autoPath.intake_2_piece == "cone") Color.Yellow else Color.Magenta,
                topLeft = intakeOffsets[autoPath.intake_2_position] ?: Offset.Zero,
                size = Size(100f, 100f),
                cornerRadius = CornerRadius(10f),
                style = Fill
            )
            // Draw the text for the success-to-attempt ratio of scoring this piece after the intake
            drawText(
                text = "${autoPath.score_3_piece_successes}/${autoPath.matches_ran}",
                offset = intakeLeftOffsets[autoPath.intake_2_position] ?: Offset.Zero,
                size = 80f
            )
            // Draw the text for the level this piece was scored at after the intake
            drawText(
                text = autoPath.score_3_position ?: "-",
                offset = intakeRightOffsets[autoPath.intake_2_position] ?: Offset.Zero,
                size = 80f
            )
        }
        // Only show the charge display if the robot tries to charge
        if (autoPath.auto_charge_level != "N") {
            // Draw a white rectangle to make the charge text more readable
            drawRoundRect(
                color = Color.White,
                topLeft = Offset(385f, 560f),
                size = Size(200f, 200f),
                cornerRadius = CornerRadius(10f)
            )
            // Draw the text for the engage-to-attempt ratio for charging
            drawText(
                text = "${autoPath.auto_charge_successes}/${autoPath.matches_ran}",
                offset = Offset(400f, 700f),
                size = 96f
            )
        }
    }
}

/**
 * Helper for drawing text in a [Canvas].
 *
 * @param text The text to draw.
 * @param offset The coordinates to draw the text at.
 * @param size The size of the text.
 */
fun DrawScope.drawText(text: String, offset: Offset, size: Float) = drawIntoCanvas {
    it.nativeCanvas.drawText(text, offset.x, offset.y, Paint().apply { textSize = size })
}
