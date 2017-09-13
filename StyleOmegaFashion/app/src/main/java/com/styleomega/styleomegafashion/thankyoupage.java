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
import android.widget.LinearLayout;
import android.widget.TableLayout;
import android.widget.TableRow;
import android.widget.TextView;

public class thankyoupage extends AppCompatActivity {
    databaseConnection dbcon=new databaseConnection(this);
    SharedPreferences sharepref;
    public static final String preference="pref";
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_thankyoupage);
        Bundle bundle = getIntent().getExtras();
        String totprice = bundle.getString("totpri");
        int totalpri=Integer.parseInt(totprice);

        sharepref=getSharedPreferences(preference, Context.MODE_PRIVATE);

        final String usrname=sharepref.getString("username", "");

        int trnNo=dbcon.getTrnNoByUserSalesTable(usrname);
        final Cursor resultSet=dbcon.SearchSalesTable(trnNo,usrname);
            TableLayout tableview = (TableLayout) findViewById(R.id.checkOutTable);
            TableRow row = new TableRow(this);
            TableLayout.LayoutParams lp = new TableLayout.LayoutParams(
                    TableLayout.LayoutParams.FILL_PARENT,
                    TableLayout.LayoutParams.WRAP_CONTENT);
            row.setLayoutParams(lp);
            row.setPadding(2, 2, 2, 2);
            row.setBackgroundColor(Color.parseColor("#828080"));

            TextView Header = new TextView(this);

            Header.setGravity(Gravity.CENTER);
            Header.setText("Checkout Summary");
            Header.setTextSize(25.0f);
            Header.setPadding(15, 0, 5, 0);
            Header.setTextColor(Color.parseColor("#d3cdcd"));
            Header.setTypeface(null, Typeface.BOLD);

            row.addView(Header);
        tableview.addView(row);

        TableRow rowheadings = new TableRow(this);
        TextView itmnm = new TextView(this);
        TextView quanttyofItem = new TextView(this);
        TextView SalepriOfItem = new TextView(this);
        TextView totalforTheItem = new TextView(this);
        itmnm.setText("Product");
        quanttyofItem.setText("QTY");
        SalepriOfItem.setText("Price");
        totalforTheItem.setText("Total");
        itmnm.setTypeface(null, Typeface.BOLD);
        quanttyofItem.setTypeface(null, Typeface.BOLD);
        SalepriOfItem.setTypeface(null, Typeface.BOLD);
        totalforTheItem.setTypeface(null, Typeface.BOLD);
        rowheadings.addView(itmnm);
        rowheadings.addView(quanttyofItem);
        rowheadings.addView(SalepriOfItem);
        rowheadings.addView(totalforTheItem);
        tableview.addView(rowheadings);

        double grandTot=0.00;
        while(resultSet.moveToNext()) {

            TableRow row2 = new TableRow(this);

            TextView Values2 = new TextView(this);
            Values2.setPadding(2, 0, 2, 0);
            Values2.setTextSize(13);
            Values2.setTextColor(Color.parseColor("#111111"));
            Values2.setTypeface(null, Typeface.BOLD);
            String itemnameSale=resultSet.getString(1);
            Values2.setText(itemnameSale);
            row2.addView(Values2);

            TextView Values4 = new TextView(this);
            Values4.setPadding(2, 0, 50, 0);
            Values4.setTextSize(13);
            Values4.setTextColor(Color.parseColor("#111111"));
            Values4.setTypeface(null, Typeface.BOLD);
            int qtySale=resultSet.getInt(4);
            Values4.setText(""+qtySale);
            row2.addView(Values4);

            TextView Values5 = new TextView(this);
            Values5.setPadding(2, 0, 50, 0);
            Values5.setTextSize(13);
            Values5.setTextColor(Color.parseColor("#111111"));
            Values5.setTypeface(null, Typeface.BOLD);
            int salePriSale=resultSet.getInt(3);
            Values5.setText(""+salePriSale);
            row2.addView(Values5);

            TextView Values6 = new TextView(this);
            Values6.setPadding(2, 0, 50, 0);
            Values6.setTextSize(13);
            Values6.setTextColor(Color.parseColor("#111111"));
            Values6.setTypeface(null, Typeface.BOLD);
            int total=resultSet.getInt(4)*resultSet.getInt(3);
            Values6.setText(""+total);
            row2.addView(Values6);

            grandTot+=total;
            tableview.addView(row2);
        }

        View v = new View(this);
        v.setLayoutParams(new LinearLayout.LayoutParams(
                LinearLayout.LayoutParams.MATCH_PARENT,
                5
        ));

        v.setBackgroundColor(Color.parseColor("#f20707"));

        tableview.addView(v);

        TextView Values7 = new TextView(this);
        Values7.setPadding(0, 0, 2, 0);
        Values7.setGravity(Gravity.CENTER);
        Values7.setTextSize(20);
        Values7.setTextColor(Color.parseColor("#111111"));
        Values7.setTypeface(null, Typeface.BOLD);
        Values7.setText("Grand Total is : "+grandTot);
        TableRow row3 = new TableRow(this);
        row3.addView(Values7);
        tableview.addView(row3);

    }
    @Override
    public void onBackPressed()
    {
        super.onBackPressed();
        startActivity(new Intent(thankyoupage.this, Navigation.class));
        finish();

    }

}
