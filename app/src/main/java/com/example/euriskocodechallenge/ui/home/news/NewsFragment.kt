package com.example.euriskocodechallenge.ui.home.news

import android.app.AlertDialog
import android.content.Intent
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.activity.OnBackPressedCallback
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.navigation.Navigation
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.euriskocodechallenge.R
import com.example.euriskocodechallenge.common.utilityfunctions
import com.example.euriskocodechallenge.databinding.FragmentNewsBinding
import com.example.euriskocodechallenge.databinding.FragmentNewsDetailsBinding
import com.example.euriskocodechallenge.utils.ConnectivityLiveData
import com.example.euriskocodechallenge.ui.home.viewmodel.NewsViewModel
import com.example.euriskocodechallenge.utils.Constants
import com.google.android.material.snackbar.Snackbar
import dagger.hilt.android.AndroidEntryPoint


@AndroidEntryPoint
class NewsFragment : Fragment() {
    private lateinit var binding : FragmentNewsBinding
    private val viewModel: NewsViewModel by viewModels()
    private val recyclerViewAdapter by lazy { NewsRVAdapter() }
    private lateinit var connectionStatus: ConnectivityLiveData


    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        // Inflate the layout for this fragment
        binding = FragmentNewsBinding.inflate(inflater, container, false)
        val view = binding.root

        binding.internetTv.visibility = View.VISIBLE
        checkConnection(view)

        return view
    }

    private fun checkConnection(view: View) {
        ConnectivityLiveData(requireActivity().application).observe(viewLifecycleOwner) {
            if (it) {
                binding.internetTv.visibility = View.GONE
                binding.newsProgress.visibility = View.VISIBLE
                initRecyclerView()
                observeViewModel(view)
            } else if (!it) {
                utilityfunctions.showtoast(requireContext(), "Lost Connection")
                binding.newsRecycler.visibility = View.GONE
                binding.internetTv.visibility = View.VISIBLE
            }
        }
    }

    //Initialize Recycler View
    private fun initRecyclerView() {
        binding.newsRecycler.visibility = View.VISIBLE
        binding.newsRecycler.apply {
            layoutManager = LinearLayoutManager(requireContext())
            adapter = recyclerViewAdapter.apply {
                setOnItemClickListener(object : NewsRVAdapter.onItemClickedListener{
                    override fun onItemClick(position: Int) {
                        val action = NewsFragmentDirections.actionNewsFragmentToNewsDetailsFragment(position)
                        Navigation.findNavController(binding.root).navigate(action)
                        utilityfunctions.showtoast(requireContext(), "$position")
                    }
                })
            }
        }
    }

    private fun observeViewModel(view: View) {
        //Observe Retrofit Result -> Set Recycler View Data, Dismiss Progress Bar
        viewModel.response.observe(viewLifecycleOwner) {
            if (!it.equals(null)) {
                recyclerViewAdapter.setData(it)
                binding.newsProgress.visibility = View.GONE
            }
        }

        //Observer Error Message -> Toast
        viewModel.error.observe(viewLifecycleOwner) {
            Snackbar.make(view, it, Snackbar.LENGTH_INDEFINITE).show()
        }
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        //Handle OnBackPressed
        requireActivity().onBackPressedDispatcher.addCallback(
            viewLifecycleOwner,
            object : OnBackPressedCallback(true) {
                override fun handleOnBackPressed() {
                    showAlertDialog()
                }
            })
    }

    //Show Alert Dialog if user presses back button
    private fun showAlertDialog() {
        AlertDialog.Builder(requireContext()).setTitle(getString(R.string.alert_message))
            .setPositiveButton(getString(R.string.alert_exit)) { _, _ ->
                val a = Intent(Intent.ACTION_MAIN)
                a.addCategory(Intent.CATEGORY_HOME)
                a.flags = Intent.FLAG_ACTIVITY_NEW_TASK
                startActivity(a)
            }
            .setNegativeButton(getString(R.string.alert_cancel)) { _, _ -> }
            .show()
    }


}