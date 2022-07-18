package com.example.euriskocodechallenge.ui.home.more

import android.graphics.Bitmap
import android.graphics.drawable.BitmapDrawable
import android.net.Uri
import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.activity.result.contract.ActivityResultContracts
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.lifecycle.lifecycleScope
import androidx.navigation.fragment.findNavController
import coil.ImageLoader
import coil.load
import coil.request.ImageRequest
import coil.request.SuccessResult
import com.example.euriskocodechallenge.R
import com.example.euriskocodechallenge.databinding.FragmentEditProfileBinding
import com.example.euriskocodechallenge.utils.Constants
import com.example.euriskocodechallenge.viewmodel.MoreViewModel
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.coroutines.launch

@AndroidEntryPoint
class EditProfileFragment : Fragment() {
    private var _binding: FragmentEditProfileBinding? = null
    private val binding get() = _binding!!
    private val viewModel by viewModels<MoreViewModel>()
    private lateinit var selectedImage: Bitmap

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        // Inflate the layout for this fragment
        _binding = FragmentEditProfileBinding.inflate(inflater, container, false)
        val view = binding.root

        initUI()

        //Handles Image Selected From Gallery
        val getImage = registerForActivityResult(ActivityResultContracts.GetContent()) {
            lifecycleScope.launch {
                selectedImage = getBitmap(it)
                binding.userImage.load(selectedImage)
                Log.d(Constants.TAG, "${getBitmap(it)}") //BITMAP
            }
        }

        //Launches Gallery Image Picker
        binding.editFab.setOnClickListener {
            getImage.launch("image/*")
        }

        binding.btnSave.setOnClickListener {
            if (validateFields()) {
                viewModel.updateUser(
                    binding.fNameEt.text?.trim().toString(),
                    binding.lNameEt.text.toString(),
                    selectedImage
                )
                Toast.makeText(requireContext(), Constants.USER_UPDATED, Toast.LENGTH_SHORT).show()
                findNavController().navigateUp()
            }
        }

        return view
    }

    private suspend fun getBitmap(uri: Uri): Bitmap {
        val loader = ImageLoader(requireContext())
        val request = ImageRequest.Builder(requireContext())
            .data(uri)
            .build()
        val result = (loader.execute(request) as SuccessResult).drawable
        return (result as BitmapDrawable).bitmap
    }

    private fun validateFields(): Boolean {
        var isValid = true
        when {
            binding.fNameEt.text.isNullOrEmpty() -> {
                binding.fNameEt.error = Constants.EMPTY_FIELD
                isValid = false
            }
            !binding.fNameEt.text!!.trim().toString()
                .matches(Constants.WHITE_SPACE_REGEX.toRegex()) -> {
                binding.fNameEt.error = Constants.CONTAINS_WHITESPACE
                isValid = false
            }
        }
        when {
            binding.lNameEt.text.isNullOrEmpty() -> {
                binding.lNameEt.error = Constants.EMPTY_FIELD
                isValid = false
            }
            !binding.lNameEt.text!!.trim().toString()
                .matches(Constants.WHITE_SPACE_REGEX.toRegex()) -> {
                binding.lNameEt.error = Constants.CONTAINS_WHITESPACE
                isValid = false
            }
        }
        return isValid
    }


    private fun initUI() {
        viewModel.loggedInUser.observe(viewLifecycleOwner) {
            binding.userImage.load(it.imageSrc)
            selectedImage = it.imageSrc
            binding.fNameEt.setText(it.fName)
            binding.lNameEt.setText(it.lName)
            binding.emailTv.text = it.email
        }
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }
}