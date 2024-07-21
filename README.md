#Google Ads Implementation in Native Android

This project follows the following flow by following the Google Ads policies : 
1. https://jsonplaceholder.typicode.com/photos (Making this Api call with Retrofit, Using 
NavGraph)
2. It consists of 4 Fragments
3. First fragment is splash Screen With 5 second count down and Showing Native ad on top 
4. Second fragment makes the above Api call and show the title and thumbnail of 
the images in a Recycler view
5. On Click of Recycler view item the data class is transfered to Third fragment 
using safeArgs by showing title with image on Full screen Fragment with share Image 
Option on other apps and showing banner ad at bottom of screen
6. Showing interstitial Ad on Every Fragment Change
7. In Last and 4th Fragment Exit App option with Two button Go back and Exit option are being shown,  
on exit option App will be exit and on go back the app will move to Recycler Fragment
