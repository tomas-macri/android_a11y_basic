package com.tomasmacri.accessibilitybasicviews.ui

import android.os.Bundle
import androidx.activity.enableEdgeToEdge
import androidx.appcompat.app.AppCompatActivity
import androidx.appcompat.content.res.AppCompatResources
import androidx.core.content.ContextCompat
import androidx.core.view.ViewCompat
import androidx.core.view.WindowInsetsCompat
import androidx.core.view.marginBottom
import androidx.navigation.NavController
import androidx.navigation.fragment.NavHostFragment
import androidx.navigation.ui.NavigationUI.setupWithNavController
import androidx.navigation.ui.setupWithNavController
import com.tomasmacri.accessibilitybasicviews.R
import com.tomasmacri.accessibilitybasicviews.databinding.ActivityMainBinding

class MainActivity : AppCompatActivity() {

    private lateinit var navController: NavController

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        val binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)
        setSupportActionBar(binding.toolbar)

        enableEdgeToEdge()
        ViewCompat.setOnApplyWindowInsetsListener(binding.toolbar) { v, windowInsets ->
            val insets = windowInsets.getInsets(WindowInsetsCompat.Type.systemBars() or WindowInsetsCompat.Type.displayCutout())
            v.setPadding(insets.left, insets.top, insets.right, v.marginBottom)
            binding.bottomNavigationView.setPadding(insets.left, insets.top, insets.right, insets.bottom)
            WindowInsetsCompat.CONSUMED
        }

        with(binding) {
            val navHostFragment = supportFragmentManager.findFragmentById(R.id.mainNavHostFragment) as NavHostFragment
            navController = navHostFragment.navController
            setupWithNavController(bottomNavigationView, navController)

            binding.toolbar.setupWithNavController(navController)
            navController.addOnDestinationChangedListener {_, destination, _ ->
                if (destination.id == R.id.coins) {
                    binding.toolbar.navigationIcon = null
                } else {
                    binding.toolbar.navigationIcon = AppCompatResources.getDrawable(this@MainActivity, R.drawable.baseline_arrow_back_24)
                    binding.toolbar.navigationIcon?.setTint(ContextCompat.getColor(this@MainActivity, R.color.white))
                }

            }
        }
    }
}


