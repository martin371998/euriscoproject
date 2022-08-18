package com.example.euriskocodechallenge.ui.home.more

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.webkit.WebViewClient
import com.example.euriskocodechallenge.R
import com.example.euriskocodechallenge.databinding.FragmentAboutUsBinding
import com.example.euriskocodechallenge.databinding.FragmentEditProfileBinding

class AboutUsFragment : Fragment() {
    private lateinit var binding: FragmentAboutUsBinding

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        // Inflate the layout for this fragment
        binding = FragmentAboutUsBinding.inflate(inflater, container, false)
        val view = binding.root
        setupViews()

        return view
    }

    //TODO Add Connection Test
    private fun setupViews() {
        binding.webView.webViewClient = WebViewClient()
        binding.webView.loadUrl("http://www.euriskomobility.com")

    }

}