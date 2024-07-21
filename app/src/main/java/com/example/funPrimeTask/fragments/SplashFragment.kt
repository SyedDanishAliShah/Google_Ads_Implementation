package com.example.funPrimeTask.fragments

import android.annotation.SuppressLint
import android.app.ProgressDialog
import android.os.Bundle
import android.os.CountDownTimer
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Button
import android.widget.FrameLayout
import android.widget.ImageView
import android.widget.TextView
import androidx.fragment.app.Fragment
import androidx.navigation.fragment.findNavController
import com.example.funprimetask.R
import com.facebook.shimmer.ShimmerFrameLayout
import com.google.android.gms.ads.AdError
import com.google.android.gms.ads.AdLoader
import com.google.android.gms.ads.AdRequest
import com.google.android.gms.ads.FullScreenContentCallback
import com.google.android.gms.ads.LoadAdError
import com.google.android.gms.ads.interstitial.InterstitialAd
import com.google.android.gms.ads.interstitial.InterstitialAdLoadCallback
import com.google.android.gms.ads.nativead.NativeAd
import com.google.android.gms.ads.nativead.NativeAdView

class SplashFragment : Fragment() {
    private lateinit var countdownText: TextView
    private lateinit var adContainer: FrameLayout
    private lateinit var shimmer: ShimmerFrameLayout
    private var interstitialAd: InterstitialAd? = null

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        val view = inflater.inflate(R.layout.fragment_splash, container, false)
        countdownText = view.findViewById(R.id.countdown_text)
        adContainer = view.findViewById(R.id.ad_container)
        shimmer = view.findViewById(R.id.shimmer_view_container_main)
        startCountdown()
        loadNativeAd()
        return view
    }

    private fun startCountdown() {
        object : CountDownTimer(5000, 1000) {
            override fun onTick(millisUntilFinished: Long) {
                countdownText.text = (millisUntilFinished / 1000).toString()
            }

            override fun onFinish() {
                loadInterstitialAd()
            }
        }.start()
    }

    private fun loadInterstitialAd() {
        // Display a loading dialog while the interstitial ad is loading
        val loadingDialog = ProgressDialog(requireContext())
        loadingDialog.setMessage("Please wait, ad is loading...")
        loadingDialog.setCancelable(false)
        loadingDialog.show()

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
                            navigateToPhotosFragment()
                        }

                        override fun onAdFailedToShowFullScreenContent(adError: AdError) {
                            super.onAdFailedToShowFullScreenContent(adError)
                            navigateToPhotosFragment()
                        }
                    }
                    showInterstitialAd()
                    loadingDialog.dismiss()  // Dismiss the loading dialog when the ad is ready
                }

                override fun onAdFailedToLoad(loadAdError: LoadAdError) {
                    super.onAdFailedToLoad(loadAdError)
                    navigateToPhotosFragment()
                    loadingDialog.dismiss()  // Dismiss the loading dialog if ad loading fails
                }
            }
        )
    }

    private fun showInterstitialAd() {
        interstitialAd?.show(requireActivity())
    }

    private fun navigateToPhotosFragment() {
        findNavController().navigate(R.id.action_splashScreenFragment_to_photosFragment)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        startCountdown()
    }

    override fun onResume() {
        super.onResume()
        // If the interstitial ad is shown and the user navigates back to SplashFragment,
        // prevent accidental back press from navigating to PhotosFragment again.
        if (interstitialAd != null) {
            interstitialAd = null
            navigateToPhotosFragment()
        }
    }

    override fun onDestroyView() {
        // Clean up the interstitial ad when the fragment is destroyed
        interstitialAd?.fullScreenContentCallback = null
        interstitialAd = null
        super.onDestroyView()
    }


    @SuppressLint("InflateParams")
    private fun loadNativeAd() {
        val adLoader = AdLoader.Builder(requireContext(), "ca-app-pub-3940256099942544/2247696110") // test ID
            .forNativeAd { nativeAd ->
                val adView = layoutInflater.inflate(R.layout.native_ad_layout, null) as NativeAdView
                populateNativeAdView(nativeAd, adView)
                adContainer.removeAllViews()
                adContainer.addView(adView)

                // Hide shimmer effect when ad is loaded
                shimmer.hideShimmer()
            }
            .build()
        adLoader.loadAd(AdRequest.Builder().build())
    }

    private fun populateNativeAdView(nativeAd: NativeAd, adView: NativeAdView) {
        adView.findViewById<ImageView>(R.id.ad_image).setImageDrawable(nativeAd.images[0].drawable)
        adView.findViewById<Button>(R.id.ad_call_to_action).text = nativeAd.callToAction

        adView.setNativeAd(nativeAd)
    }
}