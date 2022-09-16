package com.faculdade.tgi.ui.main

import android.content.Intent
import android.os.Bundle
import android.view.Menu
import android.widget.TextView
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.GravityCompat
import androidx.drawerlayout.widget.DrawerLayout
import androidx.lifecycle.ViewModelProvider
import androidx.navigation.findNavController
import androidx.navigation.ui.*
import com.faculdade.tgi.R
import com.faculdade.tgi.databinding.ActivityMainBinding
import com.faculdade.tgi.ui.contactsform.ContactFormActivity
import com.google.android.material.navigation.NavigationView

class MainActivity : AppCompatActivity() {

    private lateinit var appBarConfiguration: AppBarConfiguration
    private lateinit var binding: ActivityMainBinding
    private lateinit var viewModel: MainViewModel

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)

        viewModel = ViewModelProvider(this)[MainViewModel::class.java]

        setSupportActionBar(binding.appBarMain.toolbar)

        binding.appBarMain.fab.setOnClickListener {
            startActivity(Intent(applicationContext, ContactFormActivity::class.java))
        }

        setupNavigation()

        viewModel.loadUserName()
        observe()
    }

    override fun onResume() {
        super.onResume()
        viewModel.loadUserName()
    }

    override fun onCreateOptionsMenu(menu: Menu): Boolean {
        // Inflate the menu; this adds items to the action bar if it is present.
        menuInflater.inflate(R.menu.main, menu)
        return true
    }

    override fun onSupportNavigateUp(): Boolean {
        val navController = findNavController(R.id.nav_host_fragment_content_main)
        return navController.navigateUp(appBarConfiguration) || super.onSupportNavigateUp()
    }

    private fun setupNavigation() {
        val drawerLayout: DrawerLayout = binding.drawerLayout
        val navView: NavigationView = binding.navView
        val navController = findNavController(R.id.nav_host_fragment_content_main)
        // Passing each menu ID as a set of Ids because each
        // menu should be considered as top level destinations.
        appBarConfiguration = AppBarConfiguration(
            setOf(R.id.nav_home, R.id.nav_personal_data, R.id.nav_contacts, R.id.nav_prediction), drawerLayout
        )
        setupActionBarWithNavController(navController, appBarConfiguration)
        navView.setupWithNavController(navController)

        navView.setNavigationItemSelectedListener {
            NavigationUI.onNavDestinationSelected(it, navController)
            when (it.itemId) {
                R.id.nav_home -> {
                    binding.appBarMain.fab.show()
                    drawerLayout.closeDrawer(GravityCompat.START)
                    true
                }
                R.id.nav_personal_data -> {
                    binding.appBarMain.fab.hide()
                    drawerLayout.closeDrawer(GravityCompat.START)
                    true
                }
                R.id.nav_contacts -> {
                    binding.appBarMain.fab.show()
                    drawerLayout.closeDrawer(GravityCompat.START)
                    true
                }
                R.id.nav_prediction -> {
                    binding.appBarMain.fab.hide()
                    drawerLayout.closeDrawer(GravityCompat.START)
                    true
                }
                else -> {
                    drawerLayout.closeDrawer(GravityCompat.START)
                    false
                }
            }
        }
    }

    private fun observe() {
        viewModel.name.observe(this) {
            var header = binding.navView.getHeaderView(0)
            header.findViewById<TextView>(R.id.text_name).text = it
        }
    }

}