package com.example.pokedex.fragments

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import com.example.pokedex.R
import com.example.pokedex.adapter.ViewPagerDetailsAdapter
import com.example.pokedex.databinding.FragmentDetailsBinding
import com.google.android.material.tabs.TabLayoutMediator

class DetailsFragment : Fragment(R.layout.fragment_details) {
    val titles = listOf<String>("About", "Evolution")
    private lateinit var adapter: ViewPagerDetailsAdapter
    private var _binding: FragmentDetailsBinding? = null
    private val binding: FragmentDetailsBinding get() = _binding!!

    override fun onCreateView(
            inflater: LayoutInflater,
            container: ViewGroup?,
            savedInstanceState: Bundle?
    ): View {
        _binding = FragmentDetailsBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        adapter = ViewPagerDetailsAdapter(this)
        binding.viewPager.adapter = adapter
        TabLayoutMediator(binding.tabID, binding.viewPager) { tab, position ->
            tab.text = titles[position]
        }.attach()
    }
}