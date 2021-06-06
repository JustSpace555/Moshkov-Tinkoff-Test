package ru.tinkoff.moshkovtinkofftest.ui.main

import android.content.Context
import androidx.fragment.app.Fragment
import androidx.fragment.app.FragmentManager
import androidx.fragment.app.FragmentPagerAdapter
import ru.tinkoff.moshkovtinkofftest.R

private val TAB_TITLES = arrayOf(
    R.string.tab_latest,
    R.string.tab_best,
    R.string.tab_hot
)

class SectionsPagerAdapter(
	private val context: Context, fm: FragmentManager
) : FragmentPagerAdapter(fm, BEHAVIOR_RESUME_ONLY_CURRENT_FRAGMENT) {

    override fun getItem(position: Int): Fragment =
    	PlaceholderFragment.newInstance(position + 1)

    override fun getPageTitle(position: Int): CharSequence =
    	context.resources.getString(TAB_TITLES[position])

    override fun getCount() = 3
}