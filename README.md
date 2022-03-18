# Calendar GUI using web APIs

## How to Run  
Set up the configuration file to use your own Twilio authentication, mobile numbers and Holiday API key.
Use "gradle run" command in the gradle project's home directory.

## Configuration
All API keys/required user information are stored in the "config.json" file in the *resources* folder
(i.e. in "src/main/resources/config.json"). The "holidayKey" field refers to the key for the Holiday API,
the "twilio" object contains fields for the user's sid, token, and the numbers for sending and receiving
messages. These fields should be changed for the user's own usage.

## Database
There is a .sql file in the 'src/main/resources' folder, which should be run on the first run of the application.
This should be executed on a file named 'holidaysDB.sqlite'.  

## Citations
https://stackoverflow.com/questions/50552075/datepicker-only-mark-certain-days-with-the-color-red  
https://stackoverflow.com/questions/34681975/javafx-extract-calendar-popup-from-datepicker-only-show-popup/34684268  
https://www.baeldung.com/java-http-url-connection
