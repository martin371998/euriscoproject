package com.example.euriskocodechallenge.ui.home.more

import android.Manifest
import android.app.Activity
import android.content.Intent
import android.content.pm.PackageManager
import android.graphics.Bitmap
import android.graphics.drawable.BitmapDrawable
import android.net.Uri
import android.os.Bundle
import android.provider.MediaStore
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.activity.result.contract.ActivityResultContracts
import androidx.core.app.ActivityCompat
import androidx.core.content.ContextCompat
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.lifecycle.lifecycleScope
import androidx.navigation.fragment.findNavController
import coil.ImageLoader
import coil.load
import coil.request.ImageRequest
import coil.request.SuccessResult
import com.example.euriskocodechallenge.R
import com.example.euriskocodechallenge.common.UtilityFunctions
import com.example.euriskocodechallenge.common.toBase64
import com.example.euriskocodechallenge.common.toBitmap
import com.example.euriskocodechallenge.databinding.FragmentEditProfileBinding
import com.example.euriskocodechallenge.ui.home.viewmodel.MoreViewModel
import com.example.euriskocodechallenge.utils.Constants
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.coroutines.launch

@AndroidEntryPoint
class EditProfileFragment : Fragment() {
    private lateinit var binding: FragmentEditProfileBinding
    private val viewModel by viewModels<MoreViewModel>()
    private lateinit var selectedImage: Bitmap


    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        // Inflate the layout for this fragment
        binding = FragmentEditProfileBinding.inflate(inflater, container, false)
        val view = binding.root

        initUI()

        //Handles Image Selected From Gallery
        val getImage = registerForActivityResult(ActivityResultContracts.GetContent()) {
            lifecycleScope.launch {
                selectedImage = getBitmap(it)
                binding.userImage.load(selectedImage)
                UtilityFunctions.printLogs(Constants.TAG, "${getBitmap(it)}")
            }
        }
        //Handles Image Selected From Camera
        val getImageFromCamera =
            registerForActivityResult(ActivityResultContracts.StartActivityForResult()) { result ->
                if (result.resultCode == Activity.RESULT_OK) {
                    val photo = result.data?.extras?.get("data") as? Bitmap
                    photo?.let {
                        lifecycleScope.launch {
                            selectedImage = it
                            binding.userImage.load(selectedImage)
                            UtilityFunctions.printLogs(Constants.TAG, "$it")
                        }
                    }
                }
            }

        //Launches Gallery Image Picker
        binding.editFab.setOnClickListener {

            BottomSheetFragment().apply {
                setOnCameraClickListener {
                    if (setupPermissions()) {
                        getImageFromCamera.launch(Intent(MediaStore.ACTION_IMAGE_CAPTURE))
                    } else {
                        makeRequest()
                    }
                    setOnStorageClickListener {
                        getImage.launch("image/*")
                    }
                }
            }.show(parentFragmentManager, "TAG")
        }

        binding.btnSave.setOnClickListener {
            if (validateFields()) {
                viewModel.updateUser(
                    binding.fNameEt.text?.trim().toString(),
                    binding.lNameEt.text.toString(),
                    selectedImage.toBase64()
                )
                UtilityFunctions.showtoast(requireContext(), Constants.USER_UPDATED)
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

    private fun setupPermissions(): Boolean {
        val permission = ContextCompat.checkSelfPermission(
            requireActivity(),
            Manifest.permission.CAMERA
        )

        if (permission != PackageManager.PERMISSION_GRANTED) {
            UtilityFunctions.printLogs(
                getString(R.string.error_tag),
                getString(R.string.permission_error)
            )
            return false
        }
        return true
    }

    private fun makeRequest() {
        ActivityCompat.requestPermissions(
            requireActivity(),
            arrayOf(Manifest.permission.CAMERA),
            Constants.CAMERA_REQUEST_CODE
        )
    }


    private fun validateFields(): Boolean {
        var isValid = true
        when {
            binding.fNameEt.text.isNullOrEmpty() -> {
                binding.fNameEt.error = Constants.EMPTY_FIELD
                isValid = false
            }
            !binding.fNameEt.text?.trim().toString()
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
            !binding.lNameEt.text?.trim().toString()
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
            it.imageSrc.let {
                it?.let {
                    selectedImage = it.toBitmap()
                }
            }
            binding.fNameEt.setText(it.firstName)
            binding.lNameEt.setText(it.lastName)
            binding.emailTv.text = it.email
        }
    }

    override fun onDestroyView() {
        super.onDestroyView()
    }
}