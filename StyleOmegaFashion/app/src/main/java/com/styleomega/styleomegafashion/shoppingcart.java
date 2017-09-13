package com.styleomega.styleomegafashion;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.database.Cursor;
import android.graphics.Color;
import android.graphics.Typeface;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.Gravity;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

public class shoppingcart extends AppCompatActivity {
    databaseConnection dbcon = new databaseConnection(this);
    SharedPreferences sharepref;
    public static final String preference = "pref";
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_shoppingcart);
        sharepref = getSharedPreferences(preference, Context.MODE_PRIVATE);

        final String usrname = sharepref.getString("username", "");

        TextView Header = new TextView(this);
        Header.setGravity(Gravity.CENTER);
        Header.setText("SHOPPING CART");
        Header.setTextSize(25);
        Header.setTextColor(Color.parseColor("#070000"));
        Header.setTypeface(null, Typeface.BOLD);

        final Cursor results = dbcon.SearchShoppingCardItem(usrname);

        LinearLayout linearLayout = (LinearLayout) findViewById(R.id.lLshopCard);
        linearLayout.addView(Header);
        String path = "";
        String ClothType = "";
        String itmid = "";
        int totPri = 0;
        TextView textView7 = new TextView(this);
        Button purchaseAll = new Button(this);

        while (results.moveToNext()) {

            TextView textView = new TextView(this);
            TextView textView2 = new TextView(this);
            TextView textView3 = new TextView(this);
            TextView textView4 = new TextView(this);
            final EditText editText4 = new EditText(this);
            TextView textView5 = new TextView(this);
            ImageView imageView = new ImageView(this);
            Button shopCard = new Button(this);
            Button remove = new Button(this);

            textView.setText("Date Added : " + results.getString(0));
            textView2.setText(results.getString(1));
            textView3.setText("Size : " + results.getString(2));
            textView4.setText("Enter Quantity Below and Click it again to confirm");
            editText4.setText("" + results.getInt(3));
            textView5.setText("Price : " + results.getInt(4) + "       Total : " + results.getInt(4) * results.getInt(3));
            final int totalforone=results.getInt(4) * results.getInt(3);
            totPri += results.getInt(4) * results.getInt(3);
            itmid = results.getString(6);
            path = results.getString(5);
            int id = getResources().getIdentifier(path, "drawable", getPackageName());

            shopCard.setText("Purchase");
            shopCard.setTag(itmid);
            remove.setText("Remove");
            remove.setTag(itmid);
            editText4.setTag(itmid);
            final int totpric=totPri;
            final int quanti=results.getInt(3);
            imageView.setImageResource(id);


            shopCard.setOnClickListener(new View.OnClickListener() {

                @Override
                public void onClick(View v) {
                    String dd = v.getTag().toString();
                    final int qqty=dbcon.getQtyInItemTableAfterSingleTransac(dd);
                    if(qqty>=quanti) {
                        Toast.makeText(shoppingcart.this, "Purchase Successful ", Toast.LENGTH_SHORT).show();
                        dbcon.addItemstoSaleTable(usrname, dd);
                        dbcon.removeItemFromShoppingCard(usrname, dd);
                        dbcon.setQtyInItemTableAfterSingleTransac(dd, quanti);
                        Intent i = new Intent(shoppingcart.this, thankyoupage.class);
                        Bundle bundle2 = new Bundle();
                        String totalprice = ((Integer) totalforone).toString();
                        bundle2.putString("totpri", totalprice);
                        i.putExtras(bundle2);
                        startActivity(i);
                    }
                    else{
                        Toast.makeText(shoppingcart.this, "You have Exceeded the Available Stock", Toast.LENGTH_SHORT).show();
                    }
                }
            });

            remove.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    String dd = v.getTag().toString();
                    Toast.makeText(shoppingcart.this, "Product has been removed from Shopping Cart ", Toast.LENGTH_SHORT).show();
                    dbcon.removeItemFromShoppingCard(usrname, dd);
                    Intent i = new Intent(shoppingcart.this, shoppingcart.class);
                    startActivity(i);

                }
            });

            editText4.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    String qty = editText4.getText().toString();
                    if (!qty.contains("\n")){
                    int quantity = Integer.parseInt(qty);
                    String itemid = v.getTag().toString();
                    final int qqty=dbcon.getQtyInItemTableAfterSingleTransac(itemid);
                    if(qqty>=quantity) {
                        dbcon.setQtyToShopingCardTable(usrname, itemid, quantity);
                        Toast.makeText(shoppingcart.this, "Quantity Confirmed", Toast.LENGTH_SHORT).show();
                        Intent i = new Intent(shoppingcart.this, shoppingcart.class);
                        startActivity(i);
                    }
                    else{
                        Toast.makeText(shoppingcart.this, "You have Exceeded the Available Stock", Toast.LENGTH_SHORT).show();
                    }
                    }

                    else{
                        Toast.makeText(shoppingcart.this, "Incorrect Quantity ! Don't Leave Spaces", Toast.LENGTH_SHORT).show();
                    }

                }
            });
            textView.setTextColor(Color.parseColor("#0c0c0c"));
            textView2.setTextColor(Color.parseColor("#0c0c0c"));
            textView3.setTextColor(Color.parseColor("#0c0c0c"));
            textView4.setTextColor(Color.parseColor("#0c0c0c"));
            textView5.setTextColor(Color.parseColor("#0c0c0c"));
            linearLayout.addView(imageView);
            linearLayout.addView(textView);
            linearLayout.addView(textView2);
            linearLayout.addView(textView3);
            linearLayout.addView(textView4);
            linearLayout.addView(editText4);
            linearLayout.addView(textView5);
            linearLayout.addView(shopCard);
            linearLayout.addView(remove);
            View v2 = new View(this);
            v2.setLayoutParams(new LinearLayout.LayoutParams(
                    LinearLayout.LayoutParams.MATCH_PARENT,
                    15
            ));

            v2.setBackgroundColor(Color.parseColor("#f20707"));

            linearLayout.addView(v2);


        }
        if (totPri > 0) {
            textView7.setText("GRAND TOTAL IS : " + totPri);
            textView7.setTypeface(null, Typeface.BOLD);
            purchaseAll.setTag(totPri);
            purchaseAll.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {

                    Toast.makeText(shoppingcart.this, "Purchase Successfully Completed", Toast.LENGTH_SHORT).show();
                    dbcon.purchaseAllShopingCard(usrname);
                    Intent i = new Intent(shoppingcart.this, thankyoupage.class);
                    Bundle bundle = new Bundle();
                    String totalprice = v.getTag().toString();
                    bundle.putString("totpri", totalprice);
                    i.putExtras(bundle);
                    startActivity(i);

                }
            });

            linearLayout.addView(textView7);
            purchaseAll.setText("Check Out");
            linearLayout.addView(purchaseAll);
        }
        else{
            TextView textView10 = new TextView(this);
            textView10.setLayoutParams(new LinearLayout.LayoutParams(LinearLayout.LayoutParams.MATCH_PARENT, LinearLayout.LayoutParams.MATCH_PARENT));
            textView10.setText("No Item Added");
            textView10.setPadding(40,600,40,500);
            textView10.setTextSize(50);
            linearLayout.addView(textView10);
        }

    }

    @Override
    public void onBackPressed()
    {
        super.onBackPressed();
        startActivity(new Intent(shoppingcart.this, Navigation.class));
        finish();

    }
}
