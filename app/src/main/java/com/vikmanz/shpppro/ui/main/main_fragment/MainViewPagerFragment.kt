package com.vikmanz.shpppro.ui.main.main_fragment

import androidx.activity.OnBackPressedCallback
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.viewpager2.adapter.FragmentStateAdapter
import androidx.viewpager2.widget.ViewPager2
import com.google.android.material.tabs.TabLayoutMediator
import com.vikmanz.shpppro.R
import com.vikmanz.shpppro.base.BaseFragment
import com.vikmanz.shpppro.databinding.FragmentMainViewPagerBinding
import com.vikmanz.shpppro.ui.main.main_fragment.my_contacts_list.MyContactsListFragment
import com.vikmanz.shpppro.ui.main.main_fragment.my_profile.MyProfileFragment
import com.vikmanz.shpppro.utilits.extensions.log
import dagger.hilt.android.AndroidEntryPoint

private const val NUM_PAGES = 2

@AndroidEntryPoint
class MainViewPagerFragment :
    BaseFragment<FragmentMainViewPagerBinding, MainViewPagerFragmentViewModel>(
        FragmentMainViewPagerBinding::inflate
    ) {

    override val viewModel: MainViewPagerFragmentViewModel by viewModels()

    lateinit var viewPager: ViewPager2


    override fun setListeners() {
       onBackPressedListener()
    }

    override fun onReady() {
        log("after create fragment view")

        viewPager = binding.pager
        val adapter = MainViewPagerFragmentStateAdapter(this)
        viewPager.adapter = adapter

        val tabLayout = binding.tabLayout
        TabLayoutMediator(tabLayout, viewPager) { tab, position ->
            tab.text = when (position) {
                0 -> getString(R.string.main_my_profile_tab)
                1 -> getString(R.string.main_my_contacts_tab)
                else -> throw IllegalStateException()
            }
        }.attach()

    }

    private fun onBackPressedListener() {
        requireActivity()
            .onBackPressedDispatcher
            .addCallback(this, object : OnBackPressedCallback(true) {
                override fun handleOnBackPressed() {
                    if (viewPager.currentItem == 0) {
                        // If the user is currently looking at the first step, allow the system to handle
                        // the Back button. This calls finish() on this activity and pops the back stack.
                        @Suppress("DEPRECATION")
                        requireActivity().onBackPressed()       //todo navigation + crash
                    } else {
                        // Otherwise, select the previous step.
                        viewPager.currentItem = viewPager.currentItem - 1
                    }
                }
            })
    }

    inner class MainViewPagerFragmentStateAdapter(f: MainViewPagerFragment) :
        FragmentStateAdapter(f) {
        override fun getItemCount(): Int = NUM_PAGES

        override fun createFragment(position: Int): Fragment {
            log("create page fragment!")
            return when (position) {
                0 -> {
                    val fragment = MyProfileFragment()
                    fragment.arguments = arguments
                    fragment
                }
                1 -> MyContactsListFragment()
                else -> throw IllegalStateException()
            }
        }
    }
}