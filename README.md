# SCD2_2021_Exam

## Test Driven Development
### Calendar Model working with the APIs
*RED:* https://github.sydney.edu.au/cden4725/SCD2_2021_Exam/commit/59012324d1706f4f32f60e00b425ac1be7b009ac  
*GREEN:* https://github.sydney.edu.au/cden4725/SCD2_2021_Exam/commit/9749855a22ed4be13834d85ad3362f6c031170f1

### Reading configuration file, and parsing Holiday API response
*RED:* https://github.sydney.edu.au/cden4725/SCD2_2021_Exam/commit/6af265685b0c5b375b97e80e9af72c88c20facc5  
*GREEN:* https://github.sydney.edu.au/cden4725/SCD2_2021_Exam/commit/5b80224089b5135ff5793030c874cbb8374c3401  
*REFACTOR:* https://github.sydney.edu.au/cden4725/SCD2_2021_Exam/commit/c5061b4f7f3b63c4979465980c271df36c6b707d

### Integrating database into implementation
*RED:* https://github.sydney.edu.au/cden4725/SCD2_2021_Exam/commit/6ab24ea4356e0473b9e5e54c537736c98792d305  
*GREEN:* https://github.sydney.edu.au/cden4725/SCD2_2021_Exam/commit/30d8e7e17c2e549efc6ac435a2ed7f23888f6887  
*REFACTOR:* https://github.sydney.edu.au/cden4725/SCD2_2021_Exam/commit/c5a55af1d7af29bb1f2f25fa63e92322af2394d5  

## Configuration
All API keys/required user information are stored in the "config.json" file in the *resources* folder
(i.e. in "src/main/resources/config.json"). The "holidayKey" field refers to the key for the Holiday API,
the "twilio" object contains fields for the user's sid, token, and the numbers for sending and receiving
messages. These fields should be changed for the user's own usage.

## Database
There is a .sql file in the 'resources' folder, which should be run on the first run of the application 
in the command line as follows: 'sqlite3 < holidays.sql'. This will create the database to be used.

## Level of Features Implemented
I have implemented the required features for distinction. 

## Citations
https://stackoverflow.com/questions/50552075/datepicker-only-mark-certain-days-with-the-color-red  
https://stackoverflow.com/questions/34681975/javafx-extract-calendar-popup-from-datepicker-only-show-popup/34684268  
https://www.baeldung.com/java-http-url-connection

## Simple extension
Simple extension was taken