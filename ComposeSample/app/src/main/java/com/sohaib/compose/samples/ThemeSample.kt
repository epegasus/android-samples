package com.sohaib.compose.samples

import androidx.compose.foundation.Image
import androidx.compose.foundation.border
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.unit.dp
import com.sohaib.compose.R
import com.sohaib.compose.dataClasses.ItemMessage

/**
 * Created by: Sohaib Ahmed
 * Date: 5/30/2025
 *
 * Links:
 * - LinkedIn: https://linkedin.com/in/epegasus
 * - GitHub: https://github.com/epegasus
 */

/* ------------------------------------ Call like ------------------------------------ */
/*VisitingCard(
    itemMessage = ItemMessage("Sohaib", "How are you?"),
    modifier = Modifier.padding(innerPadding)
)*/

/* ------------------------------------ Views ------------------------------------ */

@Composable
fun VisitingCard(itemMessage: ItemMessage, modifier: Modifier = Modifier) {
    Row(
        modifier = modifier
            .fillMaxWidth()
            .padding(horizontal = 16.dp)
            .border(1.dp, MaterialTheme.colorScheme.primary, RoundedCornerShape(8.dp))
            .padding(horizontal = 16.dp, vertical = 8.dp)
    ) {
        Image(
            painterResource(id = R.drawable.img_dummy),
            contentDescription = null,
            modifier = Modifier
                .size(56.dp)
                .clip(CircleShape)
                .border(1.dp, color = MaterialTheme.colorScheme.error, shape = CircleShape)
        )
        Spacer(Modifier.width(8.dp))
        Column {
            Text(
                text = itemMessage.title,
                color = MaterialTheme.colorScheme.primary,
                style = MaterialTheme.typography.titleMedium
            )
            Surface(shape = MaterialTheme.shapes.small, shadowElevation = 1.dp) {
                Text(
                    text = itemMessage.body,
                    color = MaterialTheme.colorScheme.secondary,
                    style = MaterialTheme.typography.bodyMedium,
                    modifier = Modifier.padding(4.dp)
                )
            }
        }
    }
}