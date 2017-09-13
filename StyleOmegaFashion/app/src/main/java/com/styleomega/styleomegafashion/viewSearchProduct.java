package com.styleomega.styleomegafashion;

import android.content.Intent;
import android.database.Cursor;
import android.graphics.Color;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

public class viewSearchProduct extends AppCompatActivity {
    databaseConnection dbcon=new databaseConnection(this);
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_view_search_product);
        Bundle bundle = getIntent().getExtras();
        final String productitemid=bundle.getString("prodictsearched");
        final Cursor results=dbcon.AllProductUserSearchedInformation(productitemid);
        String path="";
        String itmid="";
        LinearLayout linearLayout=(LinearLayout)findViewById(R.id.lL1searchForAProductViewonePro);
        int count=0;
            while (results.moveToNext()) {
                count++;

                Button viewMore = new Button(this);
                itmid = results.getString(0);

                path = results.getString(6);
                int id = getResources().getIdentifier(path, "drawable", getPackageName());
                ImageView imageView = new ImageView(this);
                viewMore.setTag(itmid);
                viewMore.setText("View More Details");

                imageView.setImageResource(id);


                viewMore.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        Intent i = new Intent(viewSearchProduct.this, viewOneProduct.class);
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

            if(count==0){
            TextView textView10 = new TextView(this);
            textView10.setLayoutParams(new LinearLayout.LayoutParams(LinearLayout.LayoutParams.MATCH_PARENT, LinearLayout.LayoutParams.MATCH_PARENT));
            textView10.setText("No Item Found");
            textView10.setPadding(40,600,40,500);
            textView10.setTextSize(50);
            linearLayout.addView(textView10);
            }

        final Button btnToSearcaproduct=(Button)findViewById(R.id.btnToSearchaProductViewonePro);


        btnToSearcaproduct.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                EditText searchForProducts=(EditText)findViewById(R.id.ETsearchProductsViewonePro);
                String productname=searchForProducts.getText().toString();
                if (!(productname.contains("\n")&&productname.length()>0)){
                    btnToSearcaproduct.setTag(productname);
                    Intent i = new Intent(viewSearchProduct.this, viewSearchProduct.class);
                    Bundle bundle2 = new Bundle();
                    final String prodictsearched = v.getTag().toString();
                    bundle2.putString("prodictsearched", prodictsearched);
                    i.putExtras(bundle2);
                    startActivity(i);
                }
                else{
                    Toast.makeText(viewSearchProduct.this, "Type something to search ", Toast.LENGTH_SHORT).show();
                }

            }
        });

    }

}
