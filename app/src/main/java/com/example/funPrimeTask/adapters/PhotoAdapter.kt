package com.example.funPrimeTask.adapters

import android.annotation.SuppressLint
import android.app.Activity
import android.app.ProgressDialog
import android.content.Context
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import androidx.navigation.NavController
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.example.funprimetask.R
import com.example.funPrimeTask.dataclasses.Photo
import com.example.funPrimeTask.fragments.PhotosFragmentDirections
import com.google.android.gms.ads.AdError
import com.google.android.gms.ads.AdRequest
import com.google.android.gms.ads.FullScreenContentCallback
import com.google.android.gms.ads.LoadAdError
import com.google.android.gms.ads.interstitial.InterstitialAd
import com.google.android.gms.ads.interstitial.InterstitialAdLoadCallback


class PhotoAdapter(private val context: Context, private val navController: NavController) : RecyclerView.Adapter<PhotoAdapter.PhotoViewHolder>() {
    private var photos: List<Photo> = emptyList()

    @SuppressLint("NotifyDataSetChanged")
    fun setData(photos: List<Photo>) {
        this.photos = photos
        notifyDataSetChanged()
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): PhotoViewHolder {
        val view = LayoutInflater.from(parent.context).inflate(R.layout.item_photo, parent, false)
        return PhotoViewHolder(view)
    }

    override fun onBindViewHolder(holder: PhotoViewHolder, position: Int) {
        val photo = photos[position]
        holder.bind(photo)
        holder.itemView.setOnClickListener {
            // Display a loading dialog while the ad is loading
            val progressDialog = ProgressDialog(context)
            progressDialog.setMessage("Please wait, ad is loading...")
            progressDialog.setCancelable(false)
            progressDialog.show()

            // Load the interstitial ad
            val adRequest = AdRequest.Builder().build()
            InterstitialAd.load(
                context,
                "ca-app-pub-3940256099942544/1033173712", // Test ad unit ID
                adRequest,
                object : InterstitialAdLoadCallback() {
                    override fun onAdLoaded(interstitialAd: InterstitialAd) {
                        super.onAdLoaded(interstitialAd)
                        Log.d("AdLoad", "Ad loaded successfully")
                        // Dismiss the loading dialog when the ad is loaded
                        progressDialog.dismiss()
                        // Show the interstitial ad
                        interstitialAd.show(context as Activity)
                        interstitialAd.fullScreenContentCallback = object : FullScreenContentCallback() {
                            override fun onAdDismissedFullScreenContent() {
                                Log.d("AdDismiss", "Ad dismissed")
                                // Handle the ad closed event
                                val action = PhotosFragmentDirections.actionPhotosFragmentToFullScreenFragment(photo)
                                navController.navigate(action)
                            }

                            override fun onAdFailedToShowFullScreenContent(adError: AdError) {
                                Log.e("AdShowError", adError.message)
                                // Handle the ad failed to display error
                                val action = PhotosFragmentDirections.actionPhotosFragmentToFullScreenFragment(photo)
                                navController.navigate(action)
                            }
                        }
                    }

                    override fun onAdFailedToLoad(loadAdError: LoadAdError) {
                        super.onAdFailedToLoad(loadAdError)
                        Log.e("AdLoadError", loadAdError.message)
                        // Dismiss the loading dialog if ad loading fails
                        progressDialog.dismiss()
                        // Handle the ad load failure
                        val action = PhotosFragmentDirections.actionPhotosFragmentToFullScreenFragment(photo)
                        navController.navigate(action)
                    }
                }
            )
        }
    }

    override fun getItemCount(): Int {
        return photos.size
    }

    class PhotoViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
        private val titleTextView: TextView = itemView.findViewById(R.id.title_text_view)
        private val thumbnailImageView: ImageView = itemView.findViewById(R.id.thumbnail_image_view)

        fun bind(photo: Photo) {
            titleTextView.text = photo.title
            Glide.with(itemView)
                .load(photo.thumbnailUrl)
                .into(thumbnailImageView)
        }
    }
}