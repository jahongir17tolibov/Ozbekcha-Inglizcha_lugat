package com.example.ozbekcha_inglizchalugat.presentation.screens

import android.os.Bundle
import android.view.View
import android.widget.ImageView
import androidx.appcompat.app.AppCompatDelegate
import androidx.fragment.app.Fragment
import com.example.ozbekcha_inglizchalugat.R
import com.example.ozbekcha_inglizchalugat.databinding.FragmentBaseBinding
import com.example.ozbekcha_inglizchalugat.databinding.MainBaseViewLyBinding
import com.example.ozbekcha_inglizchalugat.presentation.adapters.ViewPagerAdapter
import com.example.ozbekcha_inglizchalugat.presentation.viewmodels.ThemeViewModel
import com.google.android.material.tabs.TabLayoutMediator
import org.koin.androidx.viewmodel.ext.android.viewModel

class BaseFragment : Fragment(R.layout.fragment_base) {
    private var _binding: FragmentBaseBinding? = null
    private lateinit var binding: FragmentBaseBinding
    private lateinit var mainViewBinding: MainBaseViewLyBinding

    private val viewModel by viewModel<ThemeViewModel>()
    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        binding = FragmentBaseBinding.bind(view)
        mainViewBinding = binding.fragmentsView
        _binding = binding

        setupDrawerLy()
        setupViewPager()
        changeThemeViewModel()
        initClicks()

    }

    private fun changeThemeViewModel() = viewModel.getTheme.observe(viewLifecycleOwner) { isDark ->
        changeTheme(isDark)
    }

    private fun initClicks() {
        val headerView = binding.menuNavigationView.getHeaderView(0)
        val darkLightBtn = headerView.findViewById<ImageView>(R.id.light_dark_btn)

        darkLightBtn.setOnClickListener {
            viewModel.setTheme(viewModel.getTheme.value?.not() ?: false)
        }
    }

    private fun changeTheme(themeState: Boolean) = when (themeState) {
        true -> AppCompatDelegate.setDefaultNightMode(AppCompatDelegate.MODE_NIGHT_YES)
        false -> AppCompatDelegate.setDefaultNightMode(AppCompatDelegate.MODE_NIGHT_NO)
    }

    private fun setupViewPager() {
        val fragmentList = listOf(EngUzbFragment(), UzbEngFragment())
        val tabItems = arrayOf("English-Uzbek", "Uzbek-English")
        val tabLayout = mainViewBinding.tabLayout
        val viewPager = mainViewBinding.viewPager

        val adapter = ViewPagerAdapter(
            fragmentList,
            childFragmentManager,
            lifecycle
        )

        mainViewBinding.viewPager.adapter = adapter

        TabLayoutMediator(tabLayout, viewPager) { tab, pos ->
            tab.text = tabItems[pos]
        }.attach()

        viewPager.currentItem = 0

    }

    private fun setupDrawerLy() {
        mainViewBinding.toolbar.setNavigationOnClickListener {
            binding.drawerLy.open()
        }

        binding.menuNavigationView.setNavigationItemSelectedListener {
            it.isChecked = true
            binding.drawerLy.close()
            true
        }

    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }

}