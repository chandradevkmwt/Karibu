package com.doomshell.karibu;


import android.content.DialogInterface;
import android.content.Intent;

import android.content.SharedPreferences;
import android.graphics.Typeface;
import android.os.Bundle;
import android.support.v4.app.FragmentTransaction;
import android.support.v7.app.ActionBar;
import android.support.v7.app.AlertDialog;
import android.text.Spannable;
import android.text.SpannableString;
import android.view.SubMenu;
import android.view.View;
import android.support.design.widget.NavigationView;
import android.support.v4.view.GravityCompat;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.ActionBarDrawerToggle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.Button;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.doomshell.karibu.helper.CustomTypefaceSpan;
import com.doomshell.karibu.model.SharedPrefManager;

public class Home extends AppCompatActivity
        implements NavigationView.OnNavigationItemSelectedListener, View.OnClickListener {

    TextView footer_search,restro_footer;
    View navview;

    TextView name, mobileno, emailid;
    DrawerLayout drawer;
    Button nav_logout_btn;
    String userid,email,fname,mobl;
    boolean islogin;

    AlertDialog logoutDialogue=null;
    LinearLayout cart_layout;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_home);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        footer_search=(TextView)findViewById(R.id.footer_search);
        restro_footer=(TextView)findViewById(R.id.restro_footer);
        cart_layout=(LinearLayout) findViewById(R.id.cart_layout);

        final DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
        ActionBarDrawerToggle toggle = new ActionBarDrawerToggle(this, drawer, toolbar, R.string.navigation_drawer_open, R.string.navigation_drawer_close);
        drawer.setDrawerListener(toggle);
        toggle.syncState();

        NavigationView navigationView = (NavigationView) findViewById(R.id.nav_view);
        navigationView.setNavigationItemSelectedListener(this);

        navview = navigationView.getHeaderView(0);
        name = (TextView) navview.findViewById(R.id.Dusername);
      //  mobileno = (TextView) navview.findViewById(R.id.Dmobile);
        emailid = (TextView) navview.findViewById(R.id.Demail);

        nav_logout_btn=(Button) navigationView.findViewById(R.id.nav_logout_btn);


     // profile_imageView=(ImageView)Headerview.findViewById(R.id.profile_imageView);



        navview.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

              Profile profile = new Profile();

                FragmentTransaction devicetrans = getSupportFragmentManager().beginTransaction();
                devicetrans.replace(R.id.home_container, profile);

                if (drawer.isDrawerOpen(GravityCompat.START))
                {
                    drawer.closeDrawer(GravityCompat.START);
                }
                devicetrans.commit();
            }
        });

        Menu m = navigationView.getMenu();

        for (int i=0;i<m.size();i++) {
            MenuItem mi = m.getItem(i);

            //for aapplying a font to subMenu ...
            SubMenu subMenu = mi.getSubMenu();
            if (subMenu!=null && subMenu.size() >0 ) {
                for (int j=0; j <subMenu.size();j++) {
                    MenuItem subMenuItem = subMenu.getItem(j);
                    applyFontToMenuItem(subMenuItem);
                }
            }
            //the method we have create in activity
            applyFontToMenuItem(mi);
        }

        Home_Fraghment home_fraghment=new Home_Fraghment();
        FragmentTransaction transaction=getSupportFragmentManager().beginTransaction();
        transaction.replace(R.id.home_container,home_fraghment);
        transaction.commit();

        footer_search.setOnClickListener(this);
        restro_footer.setOnClickListener(this);
        //profile_imageView.setOnClickListener(this);
        nav_logout_btn.setOnClickListener(this);
        cart_layout.setOnClickListener(this);
    }

    public void onResume()
    {
        super.onResume();

        if(SharedPrefManager.getInstance(Home.this).getLogin_status("islogin"))
        {
            userid = SharedPrefManager.getInstance(Home.this).getUser_details("userid");
            email = SharedPrefManager.getInstance(Home.this).getUser_details("emailid");
            fname = SharedPrefManager.getInstance(Home.this).getUser_details("fname");
            //  mobl = SharedPrefManager.getInstance(Home.this).getUser_details("mobile");

            if (email.equalsIgnoreCase("null") || email == null) {
                email = "";
            }
            if (fname.equalsIgnoreCase("null") || fname.equals("")) {
                fname = "Please update";
            }
           /* if (mobl.equalsIgnoreCase("null") || mobl == null) {
                mobl = "";
            }*/

            name.setText(fname);
            // mobileno.setText(mobl);
            emailid.setText(email);
            nav_logout_btn.setText("Log out");
        }else
        {
            name.setText("");
            // mobileno.setText("");
            emailid.setText("Please login");
            nav_logout_btn.setText("Log in");
        }

    }

    @Override
    public void onBackPressed() {

        DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
        if (drawer.isDrawerOpen(GravityCompat.START)) {
            drawer.closeDrawer(GravityCompat.START);
        } else {
            super.onBackPressed();
        }
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.home, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();

        //noinspection SimplifiableIfStatement
        if (id == R.id.action_settings) {
            return true;
        }

        return super.onOptionsItemSelected(item);
    }

    @SuppressWarnings("StatementWithEmptyBody")
    @Override
    public boolean onNavigationItemSelected(MenuItem item) {
        // Handle navigation view item clicks here.
        int id = item.getItemId();

        if (id == R.id.nav_camera) {
            // Handle the camera action
        } else if (id == R.id.nav_order) {

            Orderhistory orderhistory=new Orderhistory();
            FragmentTransaction transaction=getSupportFragmentManager().beginTransaction();
            transaction.replace(R.id.home_container,orderhistory);
            transaction.addToBackStack(orderhistory.getClass().getName().toString());
            transaction.commit();

        } else if (id == R.id.nav_share) {

        }

        DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
        drawer.closeDrawer(GravityCompat.START);
        return true;
    }

    @Override
    public void onClick(View view) {
        DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout);

        if (drawer.isDrawerOpen(GravityCompat.START)) {
            drawer.closeDrawer(GravityCompat.START);
        }

        if(view==footer_search)
        {
            Intent intent=new Intent(Home.this,Search.class);
            startActivity(intent);
        }

        if(view==restro_footer)
        {
            Home_Fraghment home_fraghment=new Home_Fraghment();
            FragmentTransaction transaction=getSupportFragmentManager().beginTransaction();
            transaction.replace(R.id.home_container,home_fraghment);
            transaction.addToBackStack(home_fraghment.getClass().getName().toString());
            transaction.commit();
        }

      /*  if(view==profile_imageView)
        {
            Profile profile=new Profile();
            FragmentTransaction transaction=getSupportFragmentManager().beginTransaction();
            transaction.replace(R.id.home_container,profile);
            transaction.addToBackStack(profile.getClass().getName().toString());
            transaction.commit();
        }
*/
        if(view==nav_logout_btn)
        {
            if(SharedPrefManager.getInstance(Home.this).getLogin_status("islogin"))
            {

                AlertDialog.Builder  builder=new AlertDialog.Builder(Home.this);
                builder.setMessage("Are you sure to logout");
                builder.setNegativeButton("No", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                   logoutDialogue.dismiss();
                    }
                });

                builder.setPositiveButton("Yes", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        SharedPrefManager.getInstance(Home.this).save_Login_status("islogin",false);
                        SharedPreferences userdetails=getSharedPreferences("userdetails",0);
                        userdetails.edit().clear();
                        userdetails.edit().commit();

                        finish();
                        startActivity(getIntent());
                    }
                });
                logoutDialogue=builder.create();
                logoutDialogue.show();

            }else {

                Intent intent = new Intent(Home.this, Log_in.class);
                startActivity(intent);
            }
        }

        if(view==cart_layout)
        {
            Cart cart=new Cart();
            FragmentTransaction transaction=getSupportFragmentManager().beginTransaction();
            transaction.replace(R.id.home_container,cart);
         //   transaction.addToBackStack(cart.getClass().getName().toString());
            transaction.commit();
          //  Toast.makeText(this, "ok cart", Toast.LENGTH_SHORT).show();
        }
    }

    private void applyFontToMenuItem(MenuItem mi) {
        Typeface font = Typeface.create("sans-serif-light", Typeface.NORMAL);
        SpannableString mNewTitle = new SpannableString(mi.getTitle());
        mNewTitle.setSpan(new CustomTypefaceSpan("" , font), 0 , mNewTitle.length(),  Spannable.SPAN_INCLUSIVE_INCLUSIVE);
        mi.setTitle(mNewTitle);
    }
}
