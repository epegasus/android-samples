package com.sohaib.compose.samples

import androidx.compose.foundation.Image
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.padding
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Email
import androidx.compose.material3.Icon
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
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
/*Greeting(
    itemMessage = ItemMessage("Sohaib", "How are you?"),
    modifier = Modifier.padding(innerPadding)
)*/

/* ------------------------------------ Views ------------------------------------ */

@Composable
fun Greeting(itemMessage: ItemMessage, modifier: Modifier = Modifier) {
    Column(modifier = modifier) {
        Box {
            Image(painterResource(R.drawable.img_dummy), contentDescription = null)
            Text(
                text = "Good Morning",
                color = Color.White, modifier = Modifier.padding(16.dp)
            )
        }
        Row {
            Icon(
                imageVector = Icons.Default.Email,
                contentDescription = "Favorite",
                tint = Color.Red
            )
            Text(text = "sohaibmughal93@gmail.com")
        }
        Text(text = "Hello ${itemMessage.title}!")
        Text(text = itemMessage.body)
    }
}