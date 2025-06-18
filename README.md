Random City App
Every 5 seconds some producer will produce the next value with random City and Color
combination from below lists (only if the app is in foreground):

The app will start on some splash screen and on the first emission from the producer it will go to
the Main Screen.
Main Screen will be a master-details view where:
- Master will be a list (sorted alphabetically). Each item on the list will contain 2 texts: 1 for
the emitted city and 1 for the date & time of the emission. The text color of the first text
should be based on the emitted color.
- When new item is emitted, it should be added to the list in a proper place
- When an item is clicked, the app should open the Details screen.
- Details Screen should have the name of the emitted city in the Toolbar and the toolbar
should have a background color of the emitted color. Below the Toolbar there will be a
map centered on the chosen city.
- Opening the Details Screen should schedule an event for a WorkManager to display a
Toast saying “Welcome to [city name]” after 5 seconds.
- When viewed on a tablet in landscape mode (only) those views should be side by side.

Additional requirements:
- Use Kotlin and Compose
- The app should work both in landscape and portrait modes
- Any API keys should be provided out of the repository
- Business logic should be unit-tested
