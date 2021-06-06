package ru.tinkoff.moshkovtinkofftest

import android.os.Bundle
import com.google.android.material.tabs.TabLayout
import androidx.viewpager.widget.ViewPager
import androidx.appcompat.app.AppCompatActivity
import androidx.viewpager2.widget.ViewPager2
import com.google.android.material.tabs.TabLayoutMediator
import org.koin.android.ext.koin.androidContext
import org.koin.core.context.startKoin
import org.koin.core.context.stopKoin
import ru.tinkoff.data.di.dataModule
import ru.tinkoff.domain.di.domainModule
import ru.tinkoff.moshkovtinkofftest.ui.main.SectionsPagerAdapter
import ru.tinkoff.moshkovtinkofftest.databinding.ActivityMainBinding
import ru.tinkoff.moshkovtinkofftest.di.viewModelModule

class MainActivity : AppCompatActivity() {

    private lateinit var binding: ActivityMainBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        startKoin {
            androidContext(applicationContext)
            modules(viewModelModule + domainModule + dataModule)
        }

        binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)

        val sectionsPagerAdapter = SectionsPagerAdapter(supportFragmentManager, lifecycle)

        val viewPager: ViewPager2 = binding.viewPager
        viewPager.adapter = sectionsPagerAdapter

        val tabTitles = listOf(R.string.tab_latest, R.string.tab_top, R.string.tab_hot)
        TabLayoutMediator(binding.tabs, viewPager) { tab, position ->
            tab.text = getString(tabTitles[position])
        }.attach()

    }

    override fun onDestroy() {
        super.onDestroy()
        stopKoin()
    }
}