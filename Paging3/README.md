# Paging 3 sample

Android sample app that loads [Quotable](https://quotable.io/) quotes in pages and displays them in a `RecyclerView` with **infinite scroll**, using **Paging 3**, **Retrofit**, **Kotlin coroutines / Flow**, **ViewBinding**, and **Koin**.

## Features

- Paginated network requests (`page` query param) via a custom `PagingSource`
- `PagingDataAdapter` for list binding; footer load-state adapter for loading / error / retry on append
- Full-screen loading, error, and empty states driven by `CombinedLoadStates`
- `PagingData` cached in the `ViewModel` with `cachedIn(viewModelScope)`

## Requirements

- **Android Studio** with a recent AGP (project uses AGP **9.1.0**)
- **JDK 17**
- Device or emulator with **API 24+** (targets **API 36**)

## Build and run

From the project root (`Paging3/`):

```bash
./gradlew installDebug
```

Or open the folder in Android Studio and run the **app** configuration. The app needs **network access** (INTERNET permission is declared) to call the API.

## Stack

| Area | Library |
|------|---------|
| UI | AppCompat, Material, ViewBinding, ConstraintLayout |
| Pagination | [AndroidX Paging 3](https://developer.android.com/topic/libraries/architecture/paging/v3-overview) |
| Networking | Retrofit 3 + Gson |
| Async | Kotlin coroutines, Lifecycle `repeatOnLifecycle` |
| DI | Koin Android |

Versions are centralized in `gradle/libs.versions.toml`.

## How it fits together

1. **`QuotesPagingSource`** — Implements `PagingSource<Int, Quote>`. Loads one API page per `load()`, maps success to `LoadResult.Page` with `prevKey` / `nextKey`, and maps `IOException` / `HttpException` to `LoadResult.Error`.
2. **`QuotesRepository`** — Builds a `Pager` with `PagingConfig` (page size **20**, placeholders disabled) and exposes `Flow<PagingData<Quote>>`.
3. **`QuotesViewModel`** — Exposes the flow with `.cachedIn(viewModelScope)`.
4. **`MainActivity`** — Collects `PagingData` into `QuotePagingAdapter`, attaches `QuotesLoadStateAdapter` as the load-state footer, and toggles progress / error / empty UI from `loadStateFlow`.

## Project layout (main sources)

```
app/src/main/java/com/sohaib/paging3/
├── App.kt
├── di/KoinModules.kt
├── data/
│   ├── paging/QuotesPagingSource.kt
│   ├── repository/QuotesRepository.kt
│   └── retrofit/…
└── presentation/
    ├── ui/MainActivity.kt
    ├── viewModels/QuotesViewModel.kt
    └── adapter/…
```

## API

- **Base URL:** `https://quotable.io/`
- **Endpoint:** `GET quotes?page={page}`
- Data classes live under `data/retrofit/entities/`.

## Paging 3 building blocks (quick reference)

| Piece | Role |
|-------|------|
| **PagingSource** | Loads a single chunk of data and defines prev/next keys |
| **Pager** | Configures page size, prefetch, placeholders; produces `Flow<PagingData<T>>` |
| **PagingDataAdapter** | Binds paged items to the `RecyclerView` |
| **LoadStateAdapter** | Header/footer for refresh/append/prepend loading and retry |

---

Sample for learning Paging 3 network paging patterns; not production-hardened (no offline cache, analytics, or API key).
