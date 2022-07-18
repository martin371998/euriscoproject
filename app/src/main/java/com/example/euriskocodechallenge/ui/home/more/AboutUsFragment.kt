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
    private var _binding: FragmentAboutUsBinding? = null
    private val binding get() = _binding!!

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        // Inflate the layout for this fragment
        _binding = FragmentAboutUsBinding.inflate(inflater, container, false)
        val view = binding.root

        //TODO Add Connection Test

        binding.webView.webViewClient = WebViewClient()
        binding.webView.loadUrl("http://www.euriskomobility.com")

        return view
    }

}