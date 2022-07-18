package com.example.euriskocodechallenge.ui.home.more

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.fragment.app.viewModels
import androidx.navigation.fragment.findNavController
import com.example.euriskocodechallenge.databinding.FragmentChangePasswordBinding
import com.example.euriskocodechallenge.utils.Constants
import com.example.euriskocodechallenge.viewmodel.MoreViewModel
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class ChangePasswordFragment : Fragment() {
    private var _binding: FragmentChangePasswordBinding? = null
    private val binding get() = _binding!!
    private val viewModel by viewModels<MoreViewModel>()

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        // Inflate the layout for this fragment
        _binding = FragmentChangePasswordBinding.inflate(inflater, container, false)
        val view = binding.root

        binding.btnSave.setOnClickListener {
            if(validateFields()){
                validateOldPass()
            }
        }
        return view
    }

    //Checks Empty Fields
    private fun validateFields() : Boolean{
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
                viewModel.updateUserPassword(it,binding.newPassEt.text.toString())
                Toast.makeText(requireContext(),Constants.USER_UPDATED,Toast.LENGTH_SHORT).show()
                findNavController().navigateUp()
            } else binding.oldPassEt.error = Constants.WRONG_PASSWORD
        }

    }


}