package com.sohaib.compose.samples

import androidx.compose.animation.animateColorAsState
import androidx.compose.animation.animateContentSize
import androidx.compose.foundation.Image
import androidx.compose.foundation.border
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
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
/*Conversation(
    modifier = Modifier.padding(innerPadding)
)*/

/* ------------------------------------ Views ------------------------------------ */

@Composable
fun Conversation(modifier: Modifier = Modifier) {
    LazyColumn(modifier = modifier) {
        items(SampleData.conversationSample) {
            ItemCard(itemMessage = it)
        }
    }
}

@Composable
fun ItemCard(itemMessage: ItemMessage, modifier: Modifier = Modifier) {
    Column {
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
            )
            Spacer(Modifier.width(8.dp))

            // Adding Expandable behavior to Content
            // Is it expanded or not? oh ho, we need to save(remember) the state, isn't it?
            // Now, let's add remember my state

            var isExpanded by remember { mutableStateOf(false) }

            // surfaceColor will be updated gradually from one color to the other
            val surfaceColor by animateColorAsState(
                if (isExpanded) MaterialTheme.colorScheme.primary else MaterialTheme.colorScheme.surface,
            )

            Column(
                modifier = Modifier
                    .fillMaxWidth()
                    .clickable { isExpanded = !isExpanded }
            ) {
                Text(text = itemMessage.title, style = MaterialTheme.typography.titleMedium)
                Spacer(modifier = Modifier.height(4.dp))

                Surface(
                    shape = MaterialTheme.shapes.medium,
                    shadowElevation = 1.dp,
                    color = surfaceColor,
                    // animateContentSize will change the Surface size gradually
                    modifier = Modifier
                        .padding(1.dp)
                        .animateContentSize()
                ) {
                    Text(
                        text = itemMessage.body,
                        modifier = Modifier.padding(all = 4.dp),
                        style = MaterialTheme.typography.bodyMedium,
                        maxLines = if (isExpanded) Int.MAX_VALUE else 1
                    )
                }
            }
        }
        Spacer(Modifier.height(8.dp))
    }
}

object SampleData {
    val conversationSample = listOf(
        ItemMessage(
            "Lexi",
            "Test...Test...Test..."
        ),
        ItemMessage(
            "Lexi",
            """List of Android versions:
            |Android KitKat (API 19)
            |Android Lollipop (API 21)
            |Android Marshmallow (API 23)
            |Android Nougat (API 24)
            |Android Oreo (API 26)
            |Android Pie (API 28)
            |Android 10 (API 29)
            |Android 11 (API 30)
            |Android 12 (API 31)""".trim()
        ),
        ItemMessage(
            "Lexi",
            """I think Kotlin is my favorite programming language.
            |It's so much fun!""".trim()
        ),
        ItemMessage(
            "Lexi",
            "Searching for alternatives to XML layouts..."
        ),
        ItemMessage(
            "Lexi",
            """Hey, take a look at Jetpack Compose, it's great!
            |It's the Android's modern toolkit for building native UI.
            |It simplifies and accelerates UI development on Android.
            |Less code, powerful tools, and intuitive Kotlin APIs :)""".trim()
        ),
        ItemMessage(
            "Lexi",
            "It's available from API 21+ :)"
        ),
        ItemMessage(
            "Lexi",
            "Writing Kotlin for UI seems so natural, Compose where have you been all my life?"
        ),
        ItemMessage(
            "Lexi",
            "Android Studio next version's name is Arctic Fox"
        ),
        ItemMessage(
            "Lexi",
            "Android Studio Arctic Fox tooling for Compose is top notch ^_^"
        ),
        ItemMessage(
            "Lexi",
            "I didn't know you can now run the emulator directly from Android Studio"
        ),
        ItemMessage(
            "Lexi",
            "Compose Previews are great to check quickly how a composable layout looks like"
        ),
        ItemMessage(
            "Lexi",
            "Previews are also interactive after enabling the experimental setting"
        ),
        ItemMessage(
            "Lexi",
            "Have you tried writing build.gradle with KTS?"
        ),
    )
}
