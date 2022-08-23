package com.example.euriskocodechallenge.ui.home.more

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.navigation.fragment.findNavController
import com.example.euriskocodechallenge.common.Utilityfunctions
import com.example.euriskocodechallenge.databinding.FragmentChangePasswordBinding
import com.example.euriskocodechallenge.ui.home.viewmodel.MoreViewModel
import com.example.euriskocodechallenge.utils.Constants
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class ChangePasswordFragment : Fragment() {
    private lateinit var binding: FragmentChangePasswordBinding
    private val viewModel by viewModels<MoreViewModel>()

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        // Inflate the layout for this fragment
        binding = FragmentChangePasswordBinding.inflate(inflater, container, false)
        val view = binding.root
        return view
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        implementListeners()
    }

    private fun implementListeners() {
        binding.btnSave.setOnClickListener {
            if (validateFields()) {
                validateOldPass()
            }
        }
    }

    //Checks Empty Fields
    private fun validateFields(): Boolean {
        var isValid = true
        if (binding.oldPassEt.text.isNullOrEmpty()) {
            binding.oldPassEt.error = Constants.EMPTY_FIELD
            isValid = false
        }
        if (binding.newPassEt.text.isNullOrEmpty()) {
            binding.newPassEt.error = Constants.EMPTY_FIELD
            isValid = false
        }
        if (binding.newPassEt.text?.length!! < 7) {
            binding.newPassEt.error = Constants.TOO_SHORT
        }
        return isValid
    }

    //Validates contents of old pass to the pass in db, then updates table
    private fun validateOldPass() {
        viewModel.loggedInUser.observe(viewLifecycleOwner) {
            if (binding.oldPassEt.text.toString() == it.password) {
                viewModel.updateUserPassword(it, binding.newPassEt.text.toString())
                Utilityfunctions.showtoast(requireContext(), Constants.USER_UPDATED)
                findNavController().navigateUp()
            } else binding.oldPassEt.error = Constants.WRONG_PASSWORD
        }

    }


}