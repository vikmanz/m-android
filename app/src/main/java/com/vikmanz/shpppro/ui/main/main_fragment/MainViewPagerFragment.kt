package com.vikmanz.shpppro.ui.main.main_fragment

import android.os.Bundle
import android.view.View
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.viewpager2.adapter.FragmentStateAdapter
import androidx.viewpager2.widget.ViewPager2
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

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        log("after create fragment view")
        viewPager = binding.viewPager2MainActivity
        viewPager.adapter = MainViewPagerFragmentStateAdapter(this)
        viewPager.currentItem = 5
        log("place adapter")
    }

    inner class MainViewPagerFragmentStateAdapter(f: Fragment) : FragmentStateAdapter(f) {
        override fun getItemCount(): Int = NUM_PAGES

        override fun createFragment(position: Int): Fragment = when (position) {
                0 -> {
                    val fragment = MyProfileFragment()
                    fragment.setEmail(viewModel.userEmail)
                    fragment
                }
                1 -> MyContactsListFragment()
                else -> throw IllegalStateException()
        }
    }
}