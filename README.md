# My Personal Project

## A subtitle

A *bulleted* list:
- item 1
- item 2
- item 3

An example of text with **bold** and *italic* fonts.  

# Calendar App  

### About

This project will be a **calendar app** designed to help users organize their schedules, set reminders, and manage important events in a clear way. The app will allow users to **create**, **edit**, and **delete** events, as well as view their schedules in daily, weekly, or monthly formats.  

The primary users will be students and professionals who need an easy to use, and clutter free tool to stay on top of deadlines, classes, meetings, and personal tasks. This project interests me because I use a calendar app daily, be it to keep track of my class schedules, project deadlines, or important events, and I want to design a tool that not only helps me stay organized but can also benefit others with similar needs.

### Features I plan to implement:
- **Events**: add, edit, and delete
- **Reminders**
- **Multiple Views**: daily, weekly, monthly (maybe?)
- **Settings**
- Import and Export (maybe?)
- minimalistic and user friendly interface (maybe?)

### User Stories:
- As a user, I want to be able to add a reminder to my calendar
- As a user, I want to be able to view the list of reminders in my calendar
- As a user, I want to be able to edit a reminder in my calendar
- As a user, I want to be able to delete a reminder from my calendar
- As a user, I want to be able to quit my app
- As a user, I want to be able to save my calendars
- As a user, I want to be able to load my calendars

### To Do:

[x] schedulable interface
- Methods getDate, getName, getInfo

[x] Event class
- Fields date, name, info, startTime, endTime
- Methods getters, setters, edit(), toString()

[x] Reminder class
- Fields date, name, info, time
- Methods getters, setters, edit(), toString()

[x] Calendar class
- Fields List
- Methods addTask(), removeTask(), viewTasks(), editTask(), addInfo() (for Events), getCurrentTime()
- Implements method requirements
- Exception propagation

[x] Calendar app
- Methods addCalendar(), removeCalendar(), editCalendar
- Implements method requirements
- Exception propagation

# Phase 4: Task 2

Fri Nov 28 14:01:52 PST 2025


New CalendarList has been created


Fri Nov 28 14:02:02 PST 2025


New calendar: calendar1 has been created


Fri Nov 28 14:02:02 PST 2025


CalendarList added a new calendar: calendar1


Fri Nov 28 14:02:26 PST 2025


A new Task: task1 with information has been created


Fri Nov 28 14:02:26 PST 2025


Task: task1 has been added to calendar: calendar1

# Phase 4: Task 3

Something I really hated about this was the fact that it is just SO messy to look at, partly due to the helper methods, but despite that, the helper methods were really important to keep my project object oriented. One thing I found while drawing my UML diagram was that I had forgotten to use helper methods in some of the calendar panels classes, so I should replace those. Another thing I really hate about this was how helper methods required the help of other helper methds, which creates this giant web of things. In the lectures we talked about some design principles, one of those being cohesion and coupling. There is so much coupling due to so many of my classes beingn dependent on the helper methods at the top, if one of those helper methods break, my entire project would not compile. Some refactoringg changes I would make are maybe CalendarManager and TaskManager classes, because my current design requires me to pass down CalendarGUI and CalendarList down the constructor. Having those manager classes would reduce connections from helper methods to CalendarGUI and CalendarList, which makes the project cleaner, reduces coupling, and potentially easier to extend and test.