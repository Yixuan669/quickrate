# QuickRate – Exchange Rate Utility App

QuickRate is a simple utility app for **CP3406 Assessment 1**.
It shows today's exchange rate from **Singapore Dollars (SGD)** to the user's chosen currency **at a glance**, so the user can quickly decide whether it is a good time to change money or shop online.

Built with **Jetpack Compose**, **Material Design 3**, **ViewModel**, a **Repository** with manual **dependency injection**, and **Retrofit** for live data.

---

## Features

- **At-a-glance main rate** – a large card shows e.g. `1 SGD = 5.20 CNY` with a flag
- **Daily change indicator** – ▲ green / ▼ red shows whether the rate went up or down compared with the previous day
- **Other currencies** – CNY, USD, MYR and AUD listed below in a clean card
- **Refresh button** – fetches the latest rates and updates the "Last updated" time
- **Loading and error states** – a spinner while loading, and a red error card with **Try again** if there is no internet (the app does not crash)
- **Settings screen** that controls the main screen:
    - Which currency is shown as the main rate
    - Number of decimal places (2 or 4)

---

## Getting Started

### How to Run
1. Clone or download this repo
2. Open in Android Studio
3. Run on an emulator or physical device (API 26+ recommended)
4. An internet connection is needed to load live exchange rates

---

## Web API

Exchange rates come from the free **Frankfurter API** (no API key needed):

- Latest rates: `https://api.frankfurter.dev/v2/rates?base=SGD&quotes=CNY,USD,MYR,AUD`
- Recent history (for the daily change): the same endpoint with `&from=yyyy-MM-dd`

Rates are published once per working day, so the app compares each rate with the **previous available day** (this handles weekends and holidays).

---

## Project Structure

```
au.edu.jcu.assessment.utilityapp
├── MainActivity.kt             – UtilityApp(): Scaffold + bottom navigation
├── QuickRateApplication.kt     – creates the AppContainer when the app starts
├── data/
│   ├── AppContainer.kt           – dependency injection (builds Retrofit + repository)
│   ├── ExchangeRateApiService.kt – Retrofit interface + RateDto
│   └── RateRepository.kt         – RateRepository interface, NetworkRateRepository, FakeRateRepository
└── ui/
    ├── QuickRateViewModel.kt     – holds QuickRateUiState, loading and error handling
    ├── components/
    │   └── RateComponents.kt     – RateCard(), CurrencyRow(), currencyFlag()
    └── screens/
        ├── UtilityScreen.kt      – main screen
        └── SettingsScreen.kt     – settings screen
```

---

## Composables

### UtilityApp()
- Contains the screen layout using a Scaffold and a bottom NavigationBar
- Toggles content between the Utility and Settings screens
- Reads app state from `QuickRateViewModel` with `collectAsState()`

### UtilityScreen()
- Stateless: all data is passed in from the ViewModel
- Shows the main `RateCard`, the other currencies, a loading spinner, an error card, and the Refresh button

### SettingsScreen()
- Radio buttons to choose the main currency and the number of decimal places
- Sends the user's choice back up through callbacks (state hoisting)

### RateCard() and CurrencyRow()
- Reusable components with their own `@Preview`
- `RateCard` uses `MaterialTheme.colorScheme.primaryContainer` so the key information stands out

---

## Key Concepts Covered

| Week | Concept                          | Used In                                                                |
|------|----------------------------------|------------------------------------------------------------------------|
| 1    | Kotlin + Android Studio          | MainActivity.kt, all Kotlin files                                      |
| 2    | Jetpack Compose Layouts + State  | UtilityApp(), UtilityScreen(), SettingsScreen(), state hoisting        |
| 3    | Material Design 3                | RateCard (primaryContainer), OutlinedCard, error colours, icons        |
| 4    | ViewModel, Repository, DI        | QuickRateViewModel, RateRepository, AppContainer, QuickRateApplication |
| 5    | Retrofit                         | ExchangeRateApiService, NetworkRateRepository                          |

---

## What I Learned

- Moving state from `remember` into a **ViewModel** fixed a bug where "Last updated" reset every time I switched tabs.
- Using a **Repository interface** and an **AppContainer** meant I could switch from placeholder data to the live web API by changing only one line.
- Wrapping network calls in `try/catch` lets the app show a friendly error instead of crashing when there is no internet.

---

## License
This project was built from the CP3406 template for educational use.