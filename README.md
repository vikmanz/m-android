# Messenger App
CRUD Contact app where users can create their own profile and manage existing users.

## Features
- Registration, login, auto-login.
- Adding a user to your contacts.
- Get contacts list from server.
- Search in list of contacts.
- Going to the user’s profile.
- Multi-select deletion, swipe deletion, deleted user recovery.

## Tech Stack
Android XML Views, Retrofit2, Hilt, Coroutines

## App architecture
The App consists of two activities (Auth and Main) with fragments in them. Each activity uses its own nav graph to navigate between fragments.

<p>

## Auth activity
The Auth activity allows the user to register their account in the test social network or log into the application if they already have an account. The autologin function is also implemented here.

### Auth screen
<img src="https://github.com/vikmanz/ShppPro/raw/task5/readme/screen_01.jpg" width="300">

### Auth nav graph
<img src="https://github.com/vikmanz/ShppPro/raw/task5/readme/nav_auth.jpg" width="900">

<p>

## Main activity
Main activity contains the main functions of the application.

### Main nav graph
<img src="https://github.com/vikmanz/ShppPro/raw/task5/readme/nav_main.jpg" width="900">

First of all, this is the user’s profile and his contact list. The contact list is pulled from the server.
<img src="https://github.com/vikmanz/ShppPro/raw/task5/readme/screen_02.jpg" width="600">

The user can add new contacts by searching for them on the server. At the same time, there is a filter by name and the ability to open the details of each contact in the list. Contacts are added asynchronously upon request to the server.

<img src="https://github.com/vikmanz/ShppPro/raw/task5/readme/screen_03.jpg" width="900">

The user can delete unnecessary contacts in the contact list. To do this, he can either swipe the contact to the left, or by clicking on the trash button on the corresponding item. This will result in asynchronous deletion of the contact via a request to the server. The last deleted contact can be restored - for this, when deleting, a snack bar with a cancel button pops up at the bottom. You can also delete contacts in bulk at once - a multi-select mode is provided for this. It is activated by pressing one element in the list.

<img src="https://github.com/vikmanz/ShppPro/raw/task5/readme/screen_04.jpg" width="900">
