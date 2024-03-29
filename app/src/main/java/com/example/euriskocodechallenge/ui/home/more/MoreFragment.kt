package com.example.euriskocodechallenge.ui.home.more

import android.content.Intent
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.navigation.findNavController
import coil.load
import com.example.euriskocodechallenge.R
import com.example.euriskocodechallenge.common.Utilityfunctions
import com.example.euriskocodechallenge.databinding.FragmentMoreBinding
import com.example.euriskocodechallenge.ui.home.viewmodel.MoreViewModel
import com.example.euriskocodechallenge.ui.login.LoginActivity
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class MoreFragment : Fragment() {
    private lateinit var binding: FragmentMoreBinding
    private val viewModel: MoreViewModel by viewModels()

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        binding = FragmentMoreBinding.inflate(inflater, container, false)
        val view = binding.root
        //Load User Info and Display Image
        return view
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        implementListeners()
        setupViews()
    }

    private fun implementListeners() {


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

            Utilityfunctions.showtoast(requireContext(), "Logged Out")
            startActivity(Intent(requireContext(), LoginActivity::class.java))
        }

    }

    private fun setupViews() {
        viewModel.loggedInUser.observe(viewLifecycleOwner) {
            binding.userImage.load(it.imageSrc)
        }
    }

    override fun onDestroyView() {
        super.onDestroyView()

    }
}