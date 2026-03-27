## 🧱 Scaffold in Jetpack Compose

`Scaffold` provides a structured layout to easily place top app bars, FABs, bottom bars, and snackbars.
Think of it as the Compose replacement for `CoordinatorLayout`.

### 🧪 Sample Code

```kotlin
Scaffold(
    topBar = { TopAppBar(title = { Text("Title") }) },
    floatingActionButton = {
        FloatingActionButton(onClick = {}) {
            Icon(Icons.Default.Add, contentDescription = null)
        }
    },
    bottomBar = { BottomAppBar { Text("Bottom") } }
) { innerPadding ->
    Text("Main Content", modifier = Modifier.padding(innerPadding))
}
```

---

## 📏 Column

Arranges children **vertically**, like stacking blocks.
👉 Equivalent to `LinearLayout (vertical)` in XML.

```kotlin
Column {
    Image(painter = painterResource(R.drawable.some_image), contentDescription = null)
    Text("Hello")
}
```

---

## 🖐️ Row

Arranges children **horizontally**, side by side.
👉 Equivalent to `LinearLayout (horizontal)` in XML.

```kotlin
Row {
    Icon(imageVector = Icons.Default.Star, contentDescription = null)
    Text("Starred")
}
```

---

## 🧪 Box

Stacks children **on top of each other**—great for overlays.
👉 Equivalent to `FrameLayout` in XML.

```kotlin
Box(modifier = Modifier.fillMaxSize()) {
    Image(
        painter = painterResource(R.drawable.background),
        contentDescription = null,
        modifier = Modifier.fillMaxSize()
    )
    Text(
        text = "Overlay Text",
        modifier = Modifier.align(Alignment.Center),
        color = Color.White
    )
}
```

---

## 🖌️ Modifier in Jetpack Compose

Modifiers allow you to **decorate, layout, and interact** with Composables. They’re chained functions that transform how your UI behaves or looks.

### 🧪 Sample Code

```kotlin
@Composable
fun GreetingCard(itemMessage: ItemMessage, modifier: Modifier = Modifier) {
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
        Column {
            Text(text = itemMessage.title, style = MaterialTheme.typography.titleMedium)
            Text(text = itemMessage.body)
        }
    }
}
```

### 🔧 What this shows:

* `modifier` lets you reuse styling and layout behavior.
* Chained modifiers (`padding`, `border`, `clip`, etc.) define both appearance and layout.

> ✨ Think of `Modifier` as the universal styling toolkit for Composables. Chain responsibly!

<p align="center">
  <img src="https://github.com/user-attachments/assets/3391a5e9-61ce-4597-99bf-3063ec8c88c2" alt="Demo GIF" width="400"/>
</p>

---

## 🌟 Material Design in Jetpack Compose

Jetpack Compose supports **Material Design 3** with built-in theming, supporting dynamic color (Android 12+) and day-night modes.

### 🔄 Core Pillars of Material Theme

* 🖌️ **Color** – Dynamic colors, dark/light support
* ✏️ **Typography** – Font styles, weights, hierarchy
* □ **Shape** – Rounded corners, cutouts, and more

<p align="center">
  <img src="https://github.com/user-attachments/assets/44ec253b-1466-4acd-a022-cc953f193c90" alt="Demo GIF" width="400"/>
</p>

---

## 🌟 List in Jetpack Compose

* LazyColumn
* LazyRow

<p align="center">
  <img src="https://github.com/user-attachments/assets/7925d34c-aeb4-408a-a62b-0877077b898c" alt="Demo GIF" width="400"/>
</p>

---
