Ølpriser i Kristiansand
======
This project is done by 3 students at the University of Agder in Norway.
We have created this android app for comparing beer prices in Kristiansand.
The app is in Norwegian, but the code is in English (except some parts).

Our website: http://priser.leisegang.no    (In Norwegian)

To launch the app on your phone: 
  1. Open the "bin"-folder
  2. Copy Priser.apk over to your phone
      - You might need to enable "Unknown sources" in your phone's settings in order to install it
  3. Install the app by clicking on the Priser.apk in your phone
  4. ...
  5. Launch the app by navigating to the app's icon on your phone.
  
If you are a developer and would like to develop more on this app, you are welcome to do so.
Just create a fork or a branch and start coding!
We have included the PHP-files and our database so you can see how we have done it.
The PHP-files need to be uploaded to a webserver in order to get the app to use them.

Development
======
To get started with this app, we used a tutorial from androidhive.info as a base. 
This showed us how to connect android with a database, and how to save and display user input.
We developed this furter to save and display the data that were relevant for us. This means that we
had to crate new PHP-files that could save and retrieve rating and price from their own table in the database.

Further on we created a new design to match the theme of the app. We chose colors that are assosiated with beer, 
and that looks good together. We created a user interface that is easy to use, and the user should not have a 
problem reading the text in the app.
We also implemented a "Tips us"-function. That way, the users can tips us with bars and restaurants that
we have not listed. We can then add them to the database, and the users can fill out the price and rating.

We started out with the option to have the users adding bars and restaurants, but in fear of them adding places like a
private party, we implemented the "Tips us"-function instead. This gives us more control of what data the database contains. 

Tools
======
Eclipse and Android Stuid has been our primary tools for developing the app.  
We have use PHP-scripts to store and retrieve data from our database. Our database is hosted on the domain of one of our
group memebers, and we have used PHPMyAdmin to access it. 
Our website has been created with WordPress and is also hosted on the same domain as our database. 

Problems
======
We have a problem with displaying "ÆØÅ" from the JSON object in the app. If a field in the database contains "ÆØÅ" the 
app will display "null". This is something we will continue to look at and hopefully fix.

We also have a problem displaying different images for each of the nightspots in the list. This is also something we will
look at.

Future plans
======
Our future plans with the app is to fix the problems that have occured, change some of the functions for the better, develop the app furter, and at some point hopefully release it in the Play Store. 
We also want to add a map-function where users can click on a link/button that will show them the way to the bar/restaurant
on a map. 
Price and rating statistics is also a function that we want to explore in the future.
