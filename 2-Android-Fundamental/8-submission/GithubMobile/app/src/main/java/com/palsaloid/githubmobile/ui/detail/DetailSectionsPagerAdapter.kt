package com.palsaloid.githubmobile.ui.detail

import androidx.fragment.app.Fragment
import androidx.viewpager2.adapter.FragmentStateAdapter
import com.palsaloid.githubmobile.ui.detail.follower.DetailFollowerFragment
import com.palsaloid.githubmobile.ui.detail.following.DetailFollowingFragment

class DetailSectionsPagerAdapter(fragment: Fragment) : FragmentStateAdapter(fragment) {

    override fun createFragment(position: Int): Fragment {
        var fragment: Fragment? = null
        when(position) {
            0 -> fragment = DetailFollowerFragment()
            1 -> fragment = DetailFollowingFragment()
        }

        return fragment as Fragment
    }

    override fun getItemCount(): Int {
        return 2
    }
}