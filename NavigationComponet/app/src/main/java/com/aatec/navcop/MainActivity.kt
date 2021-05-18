package com.aatec.navcop

import android.os.Bundle
import android.view.Menu
import android.view.MenuItem
import androidx.appcompat.app.AppCompatActivity
import androidx.fragment.app.Fragment
import androidx.navigation.NavController
import androidx.navigation.fragment.NavHostFragment
import androidx.navigation.fragment.findNavController
import androidx.navigation.ui.onNavDestinationSelected
import androidx.navigation.ui.setupActionBarWithNavController
import androidx.navigation.ui.setupWithNavController
import com.aatec.navcop.databinding.ActivityMainBinding
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class MainActivity : AppCompatActivity() {

    private lateinit var navController: NavController
    private var homeFragment: HomeFragment? = null
    private var searchFragment: SearchFragment? = null
    private lateinit var active: Fragment
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        val binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)
        val navHostFragment =
            supportFragmentManager.findFragmentById(R.id.fragment) as NavHostFragment
        navController = navHostFragment.findNavController()
        homeFragment = if (savedInstanceState == null) {
            HomeFragment()
        } else {
            if (supportFragmentManager.findFragmentByTag("1") == null) {
                HomeFragment()
            } else {
                supportFragmentManager.findFragmentByTag("1") as HomeFragment
            }
        }

        active = homeFragment!!

        searchFragment = if (savedInstanceState == null) {
            SearchFragment()
        } else {
            if (supportFragmentManager.findFragmentByTag("2") == null) {
                SearchFragment()
            } else {
                supportFragmentManager.findFragmentByTag("2") as SearchFragment
            }
        }

        binding.apply {
            setSupportActionBar(toolbar)
            setupActionBarWithNavController(navController)
            bottomNav.setupWithNavController(navController)
            bottomNav.setOnNavigationItemReselectedListener { }
        }
    }

    override fun onCreateOptionsMenu(menu: Menu?): Boolean {
        menuInflater.inflate(R.menu.menu, menu)
        return true
    }

    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        return item.onNavDestinationSelected(navController) || super.onOptionsItemSelected(item)
    }

    override fun onSupportNavigateUp(): Boolean {
        return navController.navigateUp() || super.onSupportNavigateUp()
    }

}