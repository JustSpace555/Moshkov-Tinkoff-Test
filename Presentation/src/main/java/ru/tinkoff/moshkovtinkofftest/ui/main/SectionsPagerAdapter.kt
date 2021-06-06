package ru.tinkoff.moshkovtinkofftest.ui.main

import androidx.fragment.app.FragmentManager
import androidx.lifecycle.Lifecycle
import androidx.viewpager2.adapter.FragmentStateAdapter
import ru.tinkoff.data.network.API_PATHS

class SectionsPagerAdapter(fm: FragmentManager, lifeCycle: Lifecycle) : FragmentStateAdapter(fm, lifeCycle) {

	override fun getItemCount() = 3

	override fun createFragment(position: Int) =
		PlaceholderFragment.newInstance(API_PATHS[position])
}