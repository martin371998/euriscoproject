package com.example.euriskocodechallenge.ui.home.more

import android.content.Intent
import android.net.Uri
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.navigation.findNavController
import coil.load
import com.example.euriskocodechallenge.R
import com.example.euriskocodechallenge.databinding.FragmentMoreBinding
import com.example.euriskocodechallenge.ui.LoginActivity
import com.example.euriskocodechallenge.viewmodel.MoreViewModel
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class MoreFragment : Fragment() {
    private var _binding: FragmentMoreBinding? = null
    private val binding get() = _binding!!
    private val viewModel: MoreViewModel by viewModels()

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        _binding = FragmentMoreBinding.inflate(inflater, container, false)
        val view = binding.root

        //Load User Info and Display Image
        initUI()

        binding.btnEditProfile.setOnClickListener {
            it.findNavController().navigate(R.id.action_moreFragment_to_editProfileFragment)
        }

        binding.btnChangePassword.setOnClickListener {
            it.findNavController().navigate(R.id.action_moreFragment_to_changePasswordFragment)
        }

        binding.btnAbout.setOnClickListener {
            it.findNavController().navigate(R.id.aboutUsFragment)
        }

        binding.btnLogout.setOnClickListener {
            viewModel.logOutUser()
            Toast.makeText(requireContext(), "Logged Out", Toast.LENGTH_SHORT).show()
            startActivity(Intent(requireContext(), LoginActivity::class.java))
        }

        return view
    }


    private fun initUI() {
        viewModel.loggedInUser.observe(viewLifecycleOwner) {
            binding.userImage.load(it.imageSrc)
        }
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }
}