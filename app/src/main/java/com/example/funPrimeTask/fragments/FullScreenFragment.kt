package com.example.funPrimeTask.fragments

import android.app.ProgressDialog
import android.content.Intent
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.*
import androidx.fragment.app.Fragment
import com.bumptech.glide.Glide
import com.example.funprimetask.R
import com.example.funPrimeTask.ExitActivity
import com.example.funPrimeTask.dataclasses.Photo
import com.google.android.gms.ads.*
import com.google.android.gms.ads.interstitial.InterstitialAd
import com.google.android.gms.ads.interstitial.InterstitialAdLoadCallback
import com.facebook.shimmer.ShimmerFrameLayout

class FullScreenFragment : Fragment() {
    private lateinit var photo: Photo
    private lateinit var shimmerFrameLayout: ShimmerFrameLayout
    private lateinit var adView: AdView
    private var interstitialAd: InterstitialAd? = null
    private lateinit var loadingDialog: ProgressDialog

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        return inflater.inflate(R.layout.fragment_full_screen, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        shimmerFrameLayout = view.findViewById(R.id.shimmer_view_container_main_full_screen)

        // Initialize the AdView
        adView = view.findViewById(R.id.adView_activity_main)

        // Load the ad
        val adRequest = AdRequest.Builder().build()
        adView.loadAd(adRequest)

        photo = FullScreenFragmentArgs.fromBundle(requireArguments()).photo

        // Set up the views with the received data
        val titleTextView: TextView = view.findViewById(R.id.title_text_view_full_screen)
        val fullScreenImageView: ImageView = view.findViewById(R.id.full_screen_image_view)
        val shareButton: ImageButton = view.findViewById(R.id.share_button)
        val nextButton: Button = view.findViewById(R.id.next_button)

        // Add a listener to the AdView to hide the shimmer when the ad is loaded
        adView.adListener = object : AdListener() {
            override fun onAdLoaded() {
                super.onAdLoaded()
                // Hide the shimmer effect
                shimmerFrameLayout.hideShimmer()
            }
        }

        titleTextView.text = photo.title
        Glide.with(this)
            .load(photo.url)
            .into(fullScreenImageView)

        // Set up the share button
        shareButton.setOnClickListener {
            shareImage(photo.url)
        }

        nextButton.setOnClickListener {
            showLoadingDialog()
            loadInterstitialAd()
        }
    }

    private fun showLoadingDialog() {
        loadingDialog = ProgressDialog(requireContext())
        loadingDialog.setMessage("Please wait, ad is loading...")
        loadingDialog.setCancelable(false)
        loadingDialog.show()
    }

    private fun loadInterstitialAd() {
        val adRequest = AdRequest.Builder().build()
        InterstitialAd.load(
            requireContext(),
            "ca-app-pub-3940256099942544/1033173712",  // Test ad unit ID
            adRequest,
            object : InterstitialAdLoadCallback() {
                override fun onAdLoaded(ad: InterstitialAd) {
                    super.onAdLoaded(ad)
                    interstitialAd = ad
                    ad.fullScreenContentCallback = object : FullScreenContentCallback() {
                        override fun onAdDismissedFullScreenContent() {
                            navigateToExitActivity()
                        }

                        override fun onAdFailedToShowFullScreenContent(adError: AdError) {
                            super.onAdFailedToShowFullScreenContent(adError)
                            navigateToExitActivity()
                        }
                    }
                    showInterstitialAd()
                }

                override fun onAdFailedToLoad(loadAdError: LoadAdError) {
                    super.onAdFailedToLoad(loadAdError)
                    navigateToExitActivity()
                }
            }
        )
    }

    private fun showInterstitialAd() {
        loadingDialog.dismiss()  // Dismiss the loading dialog when the ad is ready
        interstitialAd?.show(requireActivity())
    }

    private fun navigateToExitActivity() {
        loadingDialog.dismiss()  // Dismiss the loading dialog if ad loading fails
        val intent = Intent(requireContext(), ExitActivity::class.java)
        startActivity(intent)
    }

    private fun shareImage(imageUrl: String) {
        val intent = Intent(Intent.ACTION_SEND).apply {
            type = "text/plain"
            putExtra(Intent.EXTRA_TEXT, "Check out this image: $imageUrl")
        }
        startActivity(Intent.createChooser(intent, "Share Image"))
    }

    override fun onDestroyView() {
        // Clean up the interstitial ad when the fragment is destroyed
        interstitialAd?.fullScreenContentCallback = null
        interstitialAd = null
        super.onDestroyView()
    }
}