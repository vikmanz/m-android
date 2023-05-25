package com.vikmanz.shpppro.ui.main.main_fragment

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

    override fun initUI() {
        log("after create fragment view")
        viewPager = binding.viewPager2MainActivity
        viewPager.adapter = MainViewPagerFragmentStateAdapter(this@MainViewPagerFragment)
        TabLayoutMediator(binding.tabLayoutMainActivity, viewPager) { tab, position ->
            tab.text = when (position) {
                0 -> "MyProfileFragment"
                else -> "MyContactsListFragment"
            }
        }.attach()
        log("place adapter")
    }

    override fun onResume() {
        super.onResume()
        log("on resume")
        viewPager.currentItem = 5
    }

    inner class MainViewPagerFragmentStateAdapter(f: Fragment) : FragmentStateAdapter(f) {
        override fun getItemCount(): Int = NUM_PAGES

        override fun createFragment(position: Int): Fragment {
            log("create page fragment!")
            return when (position) {
                1 -> {
                    val fragment = MyProfileFragment()
                    fragment.setEmail(viewModel.userEmail)
                    fragment
                }

                0 -> MyContactsListFragment()
                else -> throw IllegalStateException()
            }
        }
    }
}