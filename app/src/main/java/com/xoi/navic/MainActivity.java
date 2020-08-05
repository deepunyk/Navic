package com.xoi.navic;
 import android.support.annotation.NonNull;
        import android.support.design.widget.NavigationView;
 import android.support.v4.app.Fragment;
 import android.support.v4.view.GravityCompat;
        import android.support.v4.widget.DrawerLayout;
        import android.support.v7.app.ActionBarDrawerToggle;
        import android.support.v7.app.AppCompatActivity;
        import android.os.Bundle;
        import android.support.v7.widget.Toolbar;
        import android.view.Menu;
        import android.view.MenuInflater;
        import android.view.MenuItem;
        import android.view.View;
        import android.widget.Toast;

        import com.jaeger.library.StatusBarUtil;

public class MainActivity extends AppCompatActivity {

    public static NavigationView navigationView;
    ActionBarDrawerToggle actionBarDrawerToggle;
    Toolbar toolbar;
    DrawerLayout drawerLayout;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);


        toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        StatusBarUtil.setColor(MainActivity.this,getColor(R.color.colorPrimary));

        drawerLayout = (DrawerLayout) findViewById(R.id.nav_drawer);
        actionBarDrawerToggle = new ActionBarDrawerToggle(this, drawerLayout, R.string.Open, R.string.Close);
        actionBarDrawerToggle.setDrawerIndicatorEnabled(true);
        drawerLayout.addDrawerListener(actionBarDrawerToggle);
        actionBarDrawerToggle.syncState();
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        toolbar.setNavigationOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                drawerLayout.openDrawer(GravityCompat.START);
            }
        });

        loadFragment(new Home_fragment());
        navigationView = (NavigationView) findViewById(R.id.navView);

        toolbar.setTitle("NavIC");
        navigationView.setCheckedItem(R.id.home_nav);
        navigationView.setNavigationItemSelectedListener(new NavigationView.OnNavigationItemSelectedListener() {
            @Override
            public boolean onNavigationItemSelected(@NonNull MenuItem menuItem) {
                int id = menuItem.getItemId();
                Fragment fragment = null;
                menuItem.setChecked(true);
                switch (id) {
                    case R.id.home_nav:
                        fragment = new Home_fragment();
                        toolbar.setTitle("NavIC");
                        break;
                    case R.id.light_nav:
                        toolbar.setTitle("Light Vehicle");
                        fragment = new LightVehicle_fragment();
                        break;
                    case R.id.heavy_nav:
                        Toast.makeText(MainActivity.this, "c", Toast.LENGTH_SHORT).show();
                        break;
                    case R.id.util_nav:
                        fragment = new Utility_fragment();
                        toolbar.setTitle("Utilities");
                        break;
                    case R.id.emer_nav:
                        fragment = new Emergency_fragment();
                        toolbar.setTitle("Emergency");
                        break;
                    default:
                        toolbar.setTitle("NavIC");
                        return false;
                }
                drawerLayout.closeDrawers();
                loadFragment(fragment);
                return true;
            }
        });
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        MenuInflater menuInflater = getMenuInflater();
        menuInflater.inflate(R.menu.nav_right, menu);
        return super.onCreateOptionsMenu(menu);
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        super.onOptionsItemSelected(item);
        switch (item.getItemId()){
            case R.id.about_nav:
                Toast.makeText(this, "About", Toast.LENGTH_SHORT).show();
                break;
            default:
                return false;
        }
        return actionBarDrawerToggle.onOptionsItemSelected(item) || super.onOptionsItemSelected(item);
    }
    private boolean loadFragment(Fragment fragment){
        if(fragment!=null){
            getSupportFragmentManager()
                    .beginTransaction()
                    .replace(R.id.contentContainer,fragment)
                    .commit();
            return true;
        }
        else
            return false;
    }
}