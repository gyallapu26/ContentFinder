#### Project Specifications
The app should define and run 3 requests SIMULTANEOUSLY, each request is defined below:
1. Truecaller10thCharacterRequest:
   I. Grab [https://blog.truecaller.com/2018/01/22/life-as-an-android-engineer/] content from
   the web
   II. Find the 10th character and display it on the screen
2. TruecallerEvery10thCharacterRequest:
   I. Grab https://blog.truecaller.com/2018/01/22/life-as-an-android-engineer/ content from
   the web
   II. Find every 10th character (i.e. 10th, 20th, 30th, etc.) and display the array on the screen
3. TruecallerWordCounterRequest:
   I. Grab https://blog.truecaller.com/2018/01/22/life-as-an-android-engineer/ content from
   the web
   II. Split the text into words using whitespace characters (i.e. space, tab, line break, etc.),
   count the occurrence of every unique word (case insensitive) and display the count for each
   word on the screen
   Disregard any HTML/JavaScript. Consider the content as plain text and treat anything separated
   by whitespace characters as a single word.
   Example: "<p> Truecaller Hello World </p>" should produce +1 for each of these: "<p>",
   "Truecaller", "Hello", "World", and "</p>". The application should show:
1. A single Button to run the three requests SIMULTANEOUSLY
2. A single TextView for each request to be updated as soon as the processing of the
   corresponding request finishes
   

#### Tech stack used

1. MVVM 
2. Hilt for DI
3. Retrofit
4. Gson
5. State Flow
6. Coroutines for Concurrent work
7. Domain driven design principle
8. Kotlin
9. View binding
10. Mockito 


#### Approach

Rather than making three calls independently, make one call which results in same content
required for the ui state and deseralise the 10 th char, every tenth char content and word count 
in repository by ensuring main safe to deal with big list and upstream the data to ui layer.



   
