package com.styleomega.styleomegafashion;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.database.Cursor;
import android.graphics.Color;
import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.Snackbar;
import android.support.v7.widget.SearchView;
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
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

public class Navigation extends AppCompatActivity
        implements NavigationView.OnNavigationItemSelectedListener {
    databaseConnection dbcon=new databaseConnection(this);
    SharedPreferences sharepref;
    public static final String preference="pref";


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_navigation);
        final Cursor results=dbcon.SearchAllProductInformation();
        String path="";
        String itmid="";
        while(results.moveToNext()) {
        LinearLayout linearLayout=(LinearLayout)findViewById(R.id.lLContentNaviga);

        Button viewMore = new Button(this);
            itmid=results.getString(0);

            path = results.getString(6);
            int id = getResources().getIdentifier(path, "drawable", getPackageName());
            ImageView imageView = new ImageView(this);
            viewMore.setTag(itmid);
            viewMore.setText("View More Details");

            imageView.setImageResource(id);


            viewMore.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    Intent i = new Intent(Navigation.this, viewOneProduct.class);
                    Bundle bundle2 = new Bundle();
                    final String itemid = v.getTag().toString();
                    bundle2.putString("itemid", itemid);
                    i.putExtras(bundle2);
                    startActivity(i);

                }
            });

        linearLayout.addView(imageView);
        linearLayout.addView(viewMore);

            View v = new View(this);
            v.setLayoutParams(new LinearLayout.LayoutParams(
                    LinearLayout.LayoutParams.MATCH_PARENT,
                    15
            ));

            v.setBackgroundColor(Color.parseColor("#f20707"));

            linearLayout.addView(v);
        }

        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        FloatingActionButton fab = (FloatingActionButton) findViewById(R.id.fab);
        fab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Snackbar.make(view, "Replace with your own action", Snackbar.LENGTH_LONG)
                        .setAction("Action", null).show();
            }
        });

       final Button btnToSearcaproduct=(Button)findViewById(R.id.btnToSearchaProduct);


        btnToSearcaproduct.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                EditText searchForProducts=(EditText)findViewById(R.id.ETsearchProducts);
                String productname=searchForProducts.getText().toString();
                int num=productname.length();
                if (!(productname.contains("\n")&&productname.length()>0)){
                    btnToSearcaproduct.setTag(productname);
                    Intent i = new Intent(Navigation.this, viewSearchProduct.class);
                    Bundle bundle2 = new Bundle();
                    final String prodictsearched = v.getTag().toString();
                    bundle2.putString("prodictsearched", prodictsearched);
                    i.putExtras(bundle2);
                    startActivity(i);
                }
                else{
                   Toast.makeText(Navigation.this, "Type something to search ", Toast.LENGTH_SHORT).show();
                }

            }
        });



        DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
        ActionBarDrawerToggle toggle = new ActionBarDrawerToggle(
                this, drawer, toolbar, R.string.navigation_drawer_open, R.string.navigation_drawer_close);
        drawer.setDrawerListener(toggle);
        toggle.syncState();

        NavigationView navigationView = (NavigationView) findViewById(R.id.nav_view);
        navigationView.setNavigationItemSelectedListener(this);
    }

    @Override
    public void onBackPressed() {
        DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
        if (drawer.isDrawerOpen(GravityCompat.START)) {
            drawer.closeDrawer(GravityCompat.START);
        } else {
            super.onBackPressed();
            sharepref = getSharedPreferences(preference, Context.MODE_PRIVATE);

            final String usrname = sharepref.getString("username", "");
            if(usrname.equals("Guest")) {
                startActivity(new Intent(Navigation.this, MainActivity.class));
                finish();
            }
            else{
                startActivity(new Intent(Navigation.this, Navigation.class));
                finish();
            }
        }
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.navigation, menu);
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
        sharepref = getSharedPreferences(preference, Context.MODE_PRIVATE);

        final String usrname = sharepref.getString("username", "");
        int id = item.getItemId();
        if(!usrname.equals("Guest")) {
            if (id == R.id.my_acc) {
                Intent i = new Intent(Navigation.this, manageAccount.class);
                startActivity(i);
            } else if (id == R.id.my_shirts) {
                Intent i = new Intent(Navigation.this, shirtsDisplay.class);
                startActivity(i);
            } else if (id == R.id.my_jeans) {
                Intent i = new Intent(Navigation.this, jeansDisplay.class);
                startActivity(i);
            } else if (id == R.id.my_accessories) {
                Intent i = new Intent(Navigation.this, accessories.class);
                startActivity(i);
            } else if (id == R.id.my_wishList) {
                Intent i = new Intent(Navigation.this, wishListActivity.class);
                startActivity(i);
            } else if (id == R.id.my_shoppingCard) {
                Intent i = new Intent(Navigation.this, shoppingcart.class);
                startActivity(i);
            }
            else if (id == R.id.my_share) {
                Intent i = new Intent(Navigation.this, shareInfo.class);
                startActivity(i);
            }
            else if (id == R.id.my_logout) {
                Intent i = new Intent(Navigation.this, logout.class);
                startActivity(i);
            }
        }
        else{
            Toast.makeText(Navigation.this, "Please Login to Continue", Toast.LENGTH_SHORT).show();
            Intent i = new Intent(Navigation.this, Login.class);
            startActivity(i);
        }


        DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
        drawer.closeDrawer(GravityCompat.START);
        return true;
    }

}
