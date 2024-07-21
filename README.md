# Google Ads Implementation in Native Android

This project follows the following flow by following the Google Ads policies : 
1. https://jsonplaceholder.typicode.com/photos (Making this Api call with Retrofit, Using 
NavGraph)
2. It consists of 4 Fragments
3. First fragment is a splash Screen With a 5-second count down and Shows a Native ad on top 
4. The second fragment makes the above API call and shows the title and thumbnail of 
the images in a Recycler view
5. On Click of Recycler view item the data class is transferred to the Third fragment 
using safeArgs by showing the title with image on Full-screen Fragment with share Image 
Option on other apps and showing banner ad at the bottom of the screen
6. Showing interstitial Ad on Every Fragment Change
7. In the Last and 4th Fragment Exit App option with Two buttons Go back and Exit options are being shown,  
on exit option App will be exit, and on the go back the app will move to Recycler Fragment
