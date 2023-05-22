package com.example.ozbekcha_inglizchalugat.presentation.screens

import android.os.Bundle
import android.view.View
import android.widget.ImageView
import androidx.appcompat.app.AppCompatDelegate
import androidx.core.view.isVisible
import androidx.fragment.app.Fragment
import androidx.navigation.fragment.findNavController
import androidx.viewpager2.widget.ViewPager2
import com.example.ozbekcha_inglizchalugat.R
import com.example.ozbekcha_inglizchalugat.databinding.FragmentBaseBinding
import com.example.ozbekcha_inglizchalugat.databinding.MainBaseViewLyBinding
import com.example.ozbekcha_inglizchalugat.presentation.adapters.ViewPagerAdapter
import com.example.ozbekcha_inglizchalugat.presentation.viewmodels.ThemeViewModel
import com.example.ozbekcha_inglizchalugat.utils.BaseUtils.slideLeftAnim
import com.google.android.material.bottomsheet.BottomSheetDialog
import com.google.android.material.bottomsheet.BottomSheetDialogFragment
import com.google.android.material.tabs.TabLayoutMediator
import org.koin.androidx.viewmodel.ext.android.viewModel

class BaseFragment : Fragment(R.layout.fragment_base) {
    private var _binding: FragmentBaseBinding? = null
    private lateinit var binding: FragmentBaseBinding
    private lateinit var mainViewBinding: MainBaseViewLyBinding

    private val viewModel by viewModel<ThemeViewModel>()
    private val navigation by lazy { findNavController() }
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

        mainViewBinding.searchBtn.setOnClickListener {

            SearchBottomSheet().show(
                childFragmentManager,
                SearchBottomSheet.TAG
            )
        }

        binding.menuNavigationView.setNavigationItemSelectedListener { menuItem ->
            when (menuItem.itemId) {
                R.id.favouritesFragment -> {
                    navigation.navigate(R.id.favouritesFragment)
                    true
                }

                else -> false
            }
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

        val registerViewPagerWithNC = object : ViewPager2.OnPageChangeCallback() {
            override fun onPageSelected(position: Int) {
                when (position) {
                    0 -> navigation.navigate(R.id.action_baseFragment_to_engUzbFragment)
                    1 -> navigation.navigate(R.id.action_baseFragment_to_uzbEngFragment)
                }
            }
        }

        viewPager.apply {
            currentItem = 0
//            registerOnPageChangeCallback(registerViewPagerWithNC)
        }

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