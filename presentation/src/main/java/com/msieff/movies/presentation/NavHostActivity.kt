package com.msieff.movies.presentation

import android.os.Bundle
import android.view.Menu
import android.view.MenuItem
import android.view.View
import androidx.appcompat.app.AppCompatActivity
import androidx.navigation.NavController
import androidx.navigation.findNavController
import androidx.navigation.fragment.NavHostFragment
import androidx.navigation.ui.onNavDestinationSelected
import androidx.navigation.ui.setupActionBarWithNavController
import androidx.navigation.ui.setupWithNavController
import com.msieff.movies.presentation.databinding.ActivityNavHostBinding
import com.msieff.movies.presentation.utils.hideKeyboard
import dagger.hilt.android.AndroidEntryPoint


@AndroidEntryPoint
class NavHostActivity : AppCompatActivity() {

    private lateinit var binding: ActivityNavHostBinding
    private lateinit var navController: NavController
    private var menu: Menu? = null

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityNavHostBinding.inflate(layoutInflater)
        setContentView(binding.root)
        setSupportActionBar(binding.toolbar)
        setupNavigation()

    }

    override fun onCreateOptionsMenu(menu: Menu): Boolean {
        val inflater = menuInflater
        inflater.inflate(R.menu.menu_nav_host, menu)
        this.menu = menu
        showSearchMoviesButton()
        return true
    }

    private fun setupNavigation() {
        val navHostFragment =
            supportFragmentManager.findFragmentById(R.id.navHostFragment) as NavHostFragment
        navController = navHostFragment.navController
        setupActionBarWithNavController(navController)
        binding.bottomNavigation.setupWithNavController(navController)

        navController.addOnDestinationChangedListener { _, destination, _ ->
            when (destination.id) {
                R.id.discoverMovies -> {
                    showBottomNav()
                    showSearchMoviesButton()
                    hideBackButton()
                }
                R.id.discoverTVShows -> {
                    showBottomNav()
                    showSearchTVShowButton()
                    hideBackButton()
                }
                else -> {
                    hideBottomNav()
                    hideSearchButtons()
                    showBackButton()
                }
            }
        }
    }

    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        return item.onNavDestinationSelected(navController) || super.onOptionsItemSelected(item)
    }

    private fun showSearchMoviesButton() {
        menu?.findItem(R.id.searchMovies)?.isVisible = true
        menu?.findItem(R.id.searchTVShows)?.isVisible = false
    }

    private fun showSearchTVShowButton() {
        menu?.findItem(R.id.searchTVShows)?.isVisible = true
        menu?.findItem(R.id.searchMovies)?.isVisible = false
    }

    private fun hideSearchButtons() {
        menu?.findItem(R.id.searchMovies)?.isVisible = false
        menu?.findItem(R.id.searchTVShows)?.isVisible = false
    }

    private fun showBottomNav() {
        binding.bottomNavigation.visibility = View.VISIBLE
    }

    private fun hideBottomNav() {
        binding.bottomNavigation.visibility = View.GONE
    }

    private fun showBackButton() {
        supportActionBar?.setDisplayHomeAsUpEnabled(true)
        supportActionBar?.setHomeButtonEnabled(true)
    }

    private fun hideBackButton() {
        supportActionBar?.setDisplayHomeAsUpEnabled(false)
        supportActionBar?.setHomeButtonEnabled(false)
    }

    override fun onSupportNavigateUp(): Boolean {
        hideKeyboard()
        return findNavController(R.id.navHostFragment).navigateUp()
    }

    override fun onBackPressed() {
        hideKeyboard()
        super.onBackPressed()
    }

}