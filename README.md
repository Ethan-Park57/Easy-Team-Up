# Easy-Team-Up

2.5 Sprint
We made the following improvements to our application in project 2.5.

Implement user story for users registering for an event which is sent to them privately. The user should be able to view all Received Events, click on any of them, and register for them on a page displaying Event Details. 

Implement user story for users editing an event that they have sent to other users. The user should be able to view all Sent Events, click on any of them, and edit the details of that event from the EditEventActivity. 

Notifications
When a user withdraws from an event that they registered for, this action should notify the host of the event. 
When a user registers for an event they have been invited to, the host of the event should be notified. 
When a user edits an event that they have sent to other users, each participant (user registered for that event) should receive a notification.
When a user views the details of any event, they should be able to view all three proposed times, not just one. Before this, users could only see the first proposed time. 


Implementation of Voting System where Proposed Event Times votes are recorded and when the due time is finished for the event, the most voted on Proposed time is set as the event time.

Let the user choose a location from a Google API  drop down bar of locations.

Implementation of showing the specific location of each event on a map using the Google Map Marker Api. Moreover, the user can differentiate between the events by clicking on them and seeing the title of the event on the marker. 


2.4 - Testing
We tested our app on a Pixel 2 API 30.

To run our app, open Android Studio and hit the green triangle Run button in the top right. 

You can register a user which will log you in and take you to the Events List (leftmost icon in NavBar). Your password must be at least 6 characters long.
Then you can create some events by following the directions on the screen in the second icon in the NavBar.
If you make the event public (!isPrivate) then the event will appear in the Events List tab.
Then, you can go to the third icon (Manage) on the NavBar and see that the event is under "Sent Events".
If you go to a event on the Events List (first tab) and click on one, then click the register button, the event will then appear under Registered Events in the Manage tab.
Received Events is not complete. 
You can also go to the Registered Events and withdraw from an event.
You can also go to the last icon (Profile) and edit details about your profile including setting an profile picture.

Put this GoogleMapsAPI key in the local.properties file as "MAPS_API_KEY=AIzaSyABm-K2oYJy7jmJSdpjtavL-xlbQpqbIqe"

You've been added to our Firebase project with your email zhaoxuzh@usc.edu if you think it will be helpful to look at that.

In order to go back from certain screens you may need to use the back button on the bottom of the Pixel 2 device because the NavBar doesn't appear on every screen.
