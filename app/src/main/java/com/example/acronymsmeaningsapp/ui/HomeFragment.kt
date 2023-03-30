package com.example.acronymsmeaningsapp.ui

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.core.view.ViewCompat
import androidx.core.view.WindowInsetsCompat
import androidx.databinding.BindingAdapter
import androidx.databinding.DataBindingUtil
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.lifecycle.ViewModelProvider
import com.example.acronymsmeaningsapp.AcronymsApp
import com.example.acronymsmeaningsapp.R
import com.example.acronymsmeaningsapp.base.LiveDataResource
import com.example.acronymsmeaningsapp.databinding.FragmentHomeBinding
import com.example.acronymsmeaningsapp.model.LfsItem
import com.example.acronymsmeaningsapp.viewModels.HomeViewModel
import dagger.hilt.android.AndroidEntryPoint
import dagger.hilt.android.HiltAndroidApp
import javax.inject.Inject

@AndroidEntryPoint
class HomeFragment: Fragment() {

    private val homeFragmentViewModel by viewModels<HomeViewModel>()

    private lateinit var acronymResults: MutableList<LfsItem>
    private lateinit var acronymResultsAdapter: AcronymResultsAdapter

    lateinit var binding: FragmentHomeBinding

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        binding = DataBindingUtil.inflate(inflater, R.layout.fragment_home, container, false)
        binding.viewmodel = homeFragmentViewModel
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        setupAdapter()
        setObservers()
    }

    companion object {
        @JvmStatic
        @BindingAdapter("customOnClickListener")
        fun View.customOnClickListener(viewModel: HomeViewModel) {
            setOnClickListener {
                viewModel.getAcronymResults()
                ViewCompat.getWindowInsetsController(it)
                    ?.hide(WindowInsetsCompat.Type.ime())
            }
        }
    }

    private fun setupAdapter() {
        acronymResults = mutableListOf()
        acronymResultsAdapter = AcronymResultsAdapter(acronymResults)
        binding.rvAcronymList.apply {
            adapter = acronymResultsAdapter
        }
    }

    private fun setObservers() {
        homeFragmentViewModel.weatherData.observe(viewLifecycleOwner) {
            it?.let {
                when (it.status) {
                    LiveDataResource.Status.LOADING -> {
                        binding.clProgressbar.show()
                    }
                    LiveDataResource.Status.SUCCESS -> {
                        it.data?.successResponse?.let { it1 ->
                            binding.clProgressbar.hide()
                            if (it1.isNotEmpty()) {
                                binding.tvAcronymMeaning.text = getString(R.string.acronym_meaning_for_1_s, binding.etAcronymWord.text.toString())
                                binding.etAcronymWord.setText("")
                                it1[0]?.lfs?.let { it2 ->
                                    acronymResults.clear()
                                    acronymResults.addAll(it2)
                                }
                                acronymResultsAdapter.notifyDataSetChanged()
                            } else {
                                //Log.d(TAG, "no data found")
                                acronymResults.clear()
                                acronymResultsAdapter.notifyDataSetChanged()
                                binding.tvAcronymMeaning.text = getString(R.string.no_data_found_for_1_s, binding.etAcronymWord.text.toString())
                                binding.etAcronymWord.setText("")
                            }
                        }
                    }
                    else -> {
                        //Log.d(TAG, "an error happened")
                        binding.clProgressbar.hide()
                        acronymResults.clear()
                        acronymResultsAdapter.notifyDataSetChanged()
                        it.data?.errorResponse?.let {
                            binding.tvAcronymMeaning.text = it
                        }
                    }
                }
            }
        }
    }
}