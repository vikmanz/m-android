package com.vikmanz.shpppro.ui.main.main_fragment

import android.os.Bundle
import android.view.View
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.viewpager2.adapter.FragmentStateAdapter
import androidx.viewpager2.widget.ViewPager2
import com.google.android.material.tabs.TabLayoutMediator
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

    private lateinit var viewPager: ViewPager2

//    override fun initUI() {
    override fun onReady() {
        log("after create fragment view")

//        val viewPager = binding.pager
//        val adapter = MainViewPagerFragmentStateAdapter(this)
//        viewPager.adapter = adapter

//        val tabLayout = binding.tabLayout
//        TabLayoutMediator(tabLayout, viewPager) { tab, position ->
//            tab.text = when (position) {
//                0 -> "MyProfileFragment"
//                else -> "MyContactsListFragment"
//            }
//        }.attach()

        log("${binding}")

        log("place adapter")
    }

    inner class MainViewPagerFragmentStateAdapter(f: MainViewPagerFragment) :
        FragmentStateAdapter(f) {
        override fun getItemCount(): Int = NUM_PAGES

        override fun createFragment(position: Int): Fragment {
            log("create page fragment!")
            return when (position) {
                0 -> {
                    val fragment = MyProfileFragment()
                    fragment.setEmail("sample.pager@gmail.com")
                    fragment
                }

                1 -> MyContactsListFragment()
                else -> throw IllegalStateException()
            }
        }
    }
}