package id.co.ultrajaya.safeandro

import android.content.Intent
import android.os.Bundle
import android.support.design.widget.NavigationView
import android.support.v4.app.Fragment
import android.support.v4.view.GravityCompat
import android.support.v7.app.ActionBarDrawerToggle
import android.support.v7.app.AppCompatActivity
import android.view.Menu
import android.view.MenuItem
import android.view.View
import android.widget.Toast
import id.co.ultrajaya.safeandro.model.response.general.ItemMenuNav
import id.co.ultrajaya.safeandro.model.util.Config
import id.co.ultrajaya.safeandro.module.activity.*
import id.co.ultrajaya.safeandro.module.contract.LoginActivity
import kotlinx.android.synthetic.main.activity_main.*
import kotlinx.android.synthetic.main.nav_app_bar_main.*
import kotlinx.android.synthetic.main.nav_header_main.*
import kotlinx.android.synthetic.main.nav_header_main.view.*

class MainActivity : AppCompatActivity(), NavigationView.OnNavigationItemSelectedListener {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        setSupportActionBar(toolbar)

        val toggle = ActionBarDrawerToggle(
            this, drawer_layout, toolbar, R.string.navigation_drawer_open, R.string.navigation_drawer_close
        )
        drawer_layout.addDrawerListener(toggle)
        toggle.syncState()

        nav_view.setNavigationItemSelectedListener(this)

        var alistMenuNav = intent.getSerializableExtra("list_menu_nav") as ArrayList<ItemMenuNav>

        addMenuItemInNavMenuDrawer(alistMenuNav)
        setTitle("Welcome");
    }

    private fun addMenuItemInNavMenuDrawer(ilistMenuNav : ArrayList<ItemMenuNav>) {
        val anavView = findViewById<View>(R.id.nav_view) as NavigationView

        val headerView = anavView.getHeaderView(0)
        headerView.id_user.text = Config.myuser!!._IdUser
        headerView.nama_user.text = Config.myuser!!._NamaUser

        val amenu = anavView.getMenu()

        for(itemMenuNav : ItemMenuNav in ilistMenuNav){
            amenu.add(R.id.nav_group, 100, Menu.NONE, itemMenuNav.at4).setIcon(R.drawable.ic_menu_label)
            /*amenu.add(R.id.nav_group, 200, Menu.NONE, "Pindah Beaker").setIcon(R.drawable.ic_menu_label)
            amenu.add(R.id.nav_group, 300, Menu.NONE, "Analisa Sample").setIcon(R.drawable.ic_menu_label)
            amenu.add(R.id.nav_group, 400, Menu.NONE, "Resume Analisa").setIcon(R.drawable.ic_menu_label)*/
            //amenu.add(R.id.nav_group, 0, Menu.NONE, "Title2").setIcon(R.drawable.ic_menu_label)
        }
        amenu.setGroupCheckable(R.id.nav_group, true, true)
        anavView.invalidate()
    }

    override fun onBackPressed() {
        if (drawer_layout.isDrawerOpen(GravityCompat.START)) {
            drawer_layout.closeDrawer(GravityCompat.START)
        } else {
            super.onBackPressed()
        }
    }

    override fun onCreateOptionsMenu(menu: Menu): Boolean {
        menuInflater.inflate(R.menu.main, menu)
        return true
    }

    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        //tidak ada menu action bar
        when (item.itemId) {
            R.id.logout -> {
                val intent = Intent(applicationContext, LoginActivity::class.java)
                startActivity(intent)
                Toast.makeText(this, "Anda sudah logout !", Toast.LENGTH_LONG)
                return true
            }
            else -> return super.onOptionsItemSelected(item)
        }
        return super.onOptionsItemSelected(item)
    }

    override fun onNavigationItemSelected(item: MenuItem): Boolean {
        lateinit var afragment: Fragment
        lateinit var afragmentClass: Class<*>
        when (item.title.toString().toLowerCase()) {
            "serah terima qc" -> {
                afragmentClass = SerahTerimaQCFragment::class.java
            }
            "pindah beaker qc" -> {
                afragmentClass = PindahBeakerFragment::class.java
            }
            "analisa sample qc" -> {
                afragmentClass = AnalisaSampleFragment::class.java
            }
            "resume analisa qc" -> {
                afragmentClass = ReportAnalisaFragment::class.java
            }
            "" -> {
                afragmentClass = UnderContruction::class.java
            }
            else -> {
                Toast.makeText(this, "Tidak ada menu tersebut di safeandro", Toast.LENGTH_LONG).show()
                afragmentClass = UnderContruction::class.java
            }
        }

        try {
            afragment = afragmentClass.newInstance() as Fragment
        } catch (e: Exception) {
            e.printStackTrace()
        }

        val fragmentManager = supportFragmentManager
        fragmentManager.beginTransaction().replace(R.id.content_frame, afragment).commit()
        title = item.title

        drawer_layout.closeDrawer(GravityCompat.START)
        return true
    }
}
