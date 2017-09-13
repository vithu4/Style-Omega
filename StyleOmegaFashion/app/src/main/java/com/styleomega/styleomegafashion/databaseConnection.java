package com.styleomega.styleomegafashion;

import android.content.BroadcastReceiver;
import android.content.ComponentName;
import android.content.ContentResolver;
import android.content.ContentValues;
import android.content.Context;

import android.content.SharedPreferences;
import android.database.Cursor;
import android.database.DatabaseErrorHandler;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.view.View;

import java.text.SimpleDateFormat;
import java.util.Date;

/**
 * Created by HP on 8/23/2017.
 */

public class databaseConnection extends SQLiteOpenHelper {
    private static  final int database_version=1;
    private static  final String database_name="styleomeganew.db";
    private static  final String table="CUSTOMER";
    private static  final String table2="ITEM";
    private static  final String table3="SALE";
    private static  final String table4="WISHLIST";
    private static  final String table5="SHOPPINGCART";
    private static  final String table6="CMNT";
    private static  final String col1="CUSID";
    private static  final String col2="NAME";
    private static  final String col3="PWD";
    private static  final String col4="TELNO";
    private static  final String col5="EMAIL";
    private static  final String col1Item="ITEMID";
    private static  final String col2Item="ITEMNAME";
    private static  final String col3Item="QTY";
    private static  final String col4Item="SIZE";
    private static  final String col1WishListTab="DATE";
    private static  final String col2WishListTab="CUSID";
    private static  final String col3WishListTab="ITEMID";
    private static  final String col1ShoppingCartTab="DATE";
    private static  final String col2ShoppingCartTab="CUSID";
    private static  final String col3ShoppingCartTab="ITEMID";
    private static  final String col4ShoppingCartTab="QUANTITY";
    private static  final String col1SaleTab="DATE";
    private static  final String col3SaleTab="CUSID";
    private static  final String col2SaleTab="ITEMID";
    private static  final String col4SaleTab="QTY";
    private static  final String col5SaleTab="TRNNO";
    private static  final String col1CmntsTab="CUSID";
    private static  final String col2CmntsTab="ITEMID";
    private static  final String col3CmntsTab="CMNTS";
    SQLiteDatabase db;


    private static final String tablecreate="create table CUSTOMER (CUSID varchar primary key not null , NAME varchar not null," +
            "PWD varchar not null, TELNO INTEGER not null, EMAIL VARCHAR not null)";
    private static final String tablecreate2="create table ITEM (ITEMID varchar primary key not null , ITEMNAME varchar not null," +
            "QTY integer not null, SIZE varchar not null, SALEPRICE integer not null, CTYPE varchar not null, PATH varchar not null);";
    private static final String tablecreate3="CREATE TABLE SALE (DATE DATETIME NOT NULL , ITEMID VARCHAR NOT NULL , CUSID VARCHAR NOT NULL ," +
            " QTY INTEGER,TRNNO INTEGER not null,PRIMARY KEY (ITEMID,CUSID,TRNNO))";
    private static final String tablecreate4="CREATE TABLE WISHLIST (DATE DATETIME, CUSID VARCHAR NOT NULL ," +
            " ITEMID VARCHAR NOT NULL , PRIMARY KEY (CUSID, ITEMID))";
    private static final String tablecreate5="CREATE TABLE SHOPPINGCART (DATE DATETIME, CUSID VARCHAR NOT NULL , ITEMID VARCHAR NOT NULL ," +
            " QUANTITY INTEGER, PRIMARY KEY (CUSID, ITEMID))";
    private static final String tablecreate6="CREATE TABLE CMNT (CUSID VARCHAR, ITEMID VARCHAR, CMNTS VARCHAR)";

    public databaseConnection(Context context){
        super(context,database_name,null,database_version);

    }
    @Override
    public void onCreate(SQLiteDatabase db) {
        db.execSQL(tablecreate);
        db.execSQL(tablecreate2);
        db.execSQL(tablecreate3);
        db.execSQL(tablecreate4);
        db.execSQL(tablecreate5);
        db.execSQL(tablecreate6);

        this.db=db;

    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
        String query="DROP TABLE IF EXISTS "+table;
        String query2="DROP TABLE IF EXISTS "+table2;
        String query3="DROP TABLE IF EXISTS "+table3;
        String query4="DROP TABLE IF EXISTS "+table4;
        String query5="DROP TABLE IF EXISTS "+table5;
        String query6="DROP TABLE IF EXISTS "+table6;
        db.execSQL(query);
        db.execSQL(query2);
        db.execSQL(query3);
        db.execSQL(query4);
        db.execSQL(query5);
        db.execSQL(query6);
        this.onCreate(db);
    }

    public void insertCustomer(setAndGetAllVariables sg){
        db=this.getWritableDatabase();
        ContentValues CV=new ContentValues();
        CV.put(col1,sg.getCusid());
        CV.put(col2,sg.getCusName());
        CV.put(col3,sg.getPwd());
        CV.put(col4,sg.getTelNo());
        CV.put(col5,sg.getEmail());

        db.insert(table,null,CV);
        db.close();

    }

    public Cursor SearchUserInformation(String username){
        db=this.getWritableDatabase();
        String query="select CUSID,NAME,PWD,TELNO,EMAIL from "+table+" where CUSID='"+username+"'";
        Cursor cursor=db.rawQuery(query,null);

        cursor.moveToNext();
        return cursor;

    }

    public boolean SearchUserInfor(String username){
        db=this.getWritableDatabase();
        String query="select CUSID from "+table+" where CUSID='"+username+"'";
        Cursor cursor=db.rawQuery(query,null);

       boolean flag=false;
        if(cursor.moveToNext()){
            flag=true;
        }

        return flag;

    }

    public void updateCustomer(String username,String name,String pass,String telno,String email){
        db=this.getWritableDatabase();
        int telenumber=Integer.parseInt(telno);
        ContentValues CV=new ContentValues();
        CV.put(col1,username);
        CV.put(col2,name);
        CV.put(col3,pass);
        CV.put(col4,telenumber);
        CV.put(col5,email);

        db.update(table,CV,"CUSID=?",new String[]{username});


    }

    public Cursor SearchAllUserInformation(){
        db=this.getWritableDatabase();
        String query="select * from "+table;
        Cursor cursor=db.rawQuery(query,null);
        return cursor;

    }

    public Cursor SearchAllProductInformation(){
        db=this.getWritableDatabase();
        String query="select * from "+table2;
        Cursor cursorPro=db.rawQuery(query,null);
        return cursorPro;

    }

    public void addItemstoWishTable(String usrid,String itmid){
        db=this.getWritableDatabase();

        ContentValues CV=new ContentValues();
        String date = new SimpleDateFormat("dd-MM-yy").format(new Date());
        CV.put(col1WishListTab,date);
        CV.put(col2WishListTab,usrid);
        CV.put(col3WishListTab,itmid);

        db.insert(table4,null,CV);
        db.close();

    }

    public void addItemstoShoppingCardTable(String usrid,String itmid,int qty){
        db=this.getWritableDatabase();

        ContentValues CV=new ContentValues();
        String date = new SimpleDateFormat("dd-MM-yy").format(new Date());
        CV.put(col1ShoppingCartTab,date);
        CV.put(col2ShoppingCartTab,usrid);
        CV.put(col3ShoppingCartTab,itmid);
        CV.put(col4ShoppingCartTab,qty);
        db.insert(table5,null,CV);
        db.close();

    }

    public Cursor SearchwishListItem(String username){
        db=this.getWritableDatabase();
        String query="SELECT WISHLIST .DATE, ITEM.ITEMNAME, ITEM.SIZE, ITEM.QTY, ITEM.SALEPRICE, ITEM.PATH, ITEM.ITEMID " +
                "FROM WISHLIST JOIN ITEM ON WISHLIST.ITEMID" +
                "=ITEM .ITEMID WHERE WISHLIST.CUSID='"+username+"'";
        Cursor cursor=db.rawQuery(query,null);
        return cursor;

    }

    public void removeItemFromWishList(String username,String itemid) {

         db = this.getWritableDatabase();

        db.execSQL("DELETE FROM " + table4 + " WHERE CUSID='"+username+"' AND ITEMID='"+itemid+"'");

        //Close the database
        db.close();
    }

    public Cursor SearchShoppingCardItem(String username){
        db=this.getWritableDatabase();
        String query="SELECT SHOPPINGCART.DATE, ITEM.ITEMNAME, ITEM.SIZE, SHOPPINGCART.QUANTITY, ITEM.SALEPRICE, ITEM.PATH, ITEM.ITEMID " +
                "FROM SHOPPINGCART JOIN ITEM ON SHOPPINGCART.ITEMID" +
                "=ITEM .ITEMID WHERE SHOPPINGCART.CUSID='"+username+"'";
        Cursor cursor=db.rawQuery(query,null);
        return cursor;

    }

    public void removeItemFromShoppingCard(String username,String itemid) {

        db = this.getWritableDatabase();

        db.execSQL("DELETE FROM " + table5 + " WHERE CUSID='"+username+"' AND ITEMID='"+itemid+"'");

        //Close the database
        db.close();
    }

    public void addItemstoSaleTable(String usrid,String itmid){
        db=this.getWritableDatabase();
        int qty=getQtyFromShopingCardTable(usrid,itmid);
        ContentValues CV=new ContentValues();
        String date = new SimpleDateFormat("dd-MM-yy").format(new Date());
        int trnno=getTrnNoSalesTable();
        CV.put(col1SaleTab,date);
        CV.put(col2SaleTab,itmid);
        CV.put(col3SaleTab,usrid);
        CV.put(col4SaleTab,qty);
        CV.put(col5SaleTab,trnno+1);
        db.insert(table3,null,CV);
        db.close();

    }

    public void setQtyToShopingCardTable(String userid,String itmid, int qty){
        db=this.getWritableDatabase();

        ContentValues CV=new ContentValues();
        CV.put(col2ShoppingCartTab,userid);
        CV.put(col3ShoppingCartTab,itmid);
        CV.put(col4ShoppingCartTab,qty);

        db.update(table5,CV,"CUSID='"+userid+"' AND ITEMID='"+itmid+"'",null);


    }

    public int getQtyFromShopingCardTable(String username,String itemid){
        db=this.getWritableDatabase();
        int qty=0;
        String query="SELECT QUANTITY FROM SHOPPINGCART WHERE CUSID='"+username+"' AND ITEMID='"+itemid+"'";
        Cursor cursor=db.rawQuery(query,null);
        cursor.moveToNext();
        qty=cursor.getInt(0);
        return qty;

    }

    public void purchaseAllShopingCard(String usrid){
        db=this.getWritableDatabase();
        String itmid;
        int qty=0;
        ContentValues CV=new ContentValues();
        String date = new SimpleDateFormat("dd-MM-yy").format(new Date());
        int trnno=getTrnNoSalesTable();
        Cursor results=SearchShoppingCardItem(usrid);
        if(results.moveToFirst()) {
            do {
               itmid=results.getString(6);
                qty=results.getInt(3);
                setQtyInItemTableAfterSingleTransac(itmid,qty);
                CV.put(col1SaleTab,date);
                CV.put(col2SaleTab,itmid);
                CV.put(col3SaleTab,usrid);
                CV.put(col4SaleTab,qty);
                CV.put(col5SaleTab,trnno+1);
                db.insert(table3,null,CV);
            }
            while (results.moveToNext());
        }
        db.close();
        removeAllItemFromShoppingCard(usrid);
    }

    public void removeAllItemFromShoppingCard(String username) {

        db = this.getWritableDatabase();

        db.execSQL("DELETE FROM " + table5 + " WHERE CUSID='"+username+"'");

        //Close the database
        db.close();
    }

    public void setQtyInItemTableAfterSingleTransac(String itmid, int qty){
        db=this.getWritableDatabase();
        int quantity=getQtyInItemTableAfterSingleTransac(itmid);
        ContentValues CV=new ContentValues();
        CV.put(col3Item,quantity-qty);

        db.update(table2,CV,"ITEMID='"+itmid+"'",null);


    }

    public int getQtyInItemTableAfterSingleTransac(String itemid){
        db=this.getWritableDatabase();
        int qty=0;
        String query="SELECT QTY FROM ITEM WHERE ITEMID='"+itemid+"'";
        Cursor cursor=db.rawQuery(query,null);
        cursor.moveToNext();
        qty=cursor.getInt(0);
        return qty;

    }

    public Cursor SearchOneViewProductInformation(String itemid){
        db=this.getWritableDatabase();
        String query="select * from "+table2+" where ITEMID='"+itemid+"'";
        Cursor cursorPro=db.rawQuery(query,null);
        return cursorPro;

    }

    public void addItemstoCmntTable(String usrid,String itmid,String msg){
        db=this.getWritableDatabase();

        ContentValues CV=new ContentValues();

        CV.put(col1CmntsTab,usrid);
        CV.put(col2CmntsTab,itmid);
        CV.put(col3CmntsTab,msg);

        db.insert(table6,null,CV);
        db.close();

    }

    public Cursor SearchOneViewProductCmnts(String itemid){
        db=this.getWritableDatabase();
        String query="select * from "+table6+" where ITEMID='"+itemid+"'";
        Cursor cursorPro=db.rawQuery(query,null);
        return cursorPro;

    }

    public int getTrnNoSalesTable(){
        db=this.getWritableDatabase();
        int trnNo;
        String query="select * from "+table3+" ORDER BY TRNNO DESC";
        Cursor cursorPro=db.rawQuery(query,null);
        cursorPro.moveToNext();
        trnNo=cursorPro.getInt(4);
        return trnNo ;

    }

    public int getTrnNoByUserSalesTable(String usrnm){
        db=this.getWritableDatabase();
        int trnNo;
        String query="select * from "+table3+" where CUSID='"+usrnm+"' ORDER BY TRNNO DESC";
        Cursor cursorPro=db.rawQuery(query,null);
        cursorPro.moveToNext();
        trnNo=cursorPro.getInt(4);
        return trnNo ;

    }

    public Cursor SearchSalesTable(int trnNo,String usrid){
        db=this.getWritableDatabase();
        String query="select SALE.DATE,ITEM.ITEMNAME,ITEM.SIZE, ITEM.SALEPRICE,SALE.QTY" +
                " FROM ITEM JOIN SALE ON ITEM.ITEMID=SALE.ITEMID where TRNNO="+trnNo+" AND CUSID='"+usrid+"'";
        Cursor cursorPro=db.rawQuery(query,null);
        return cursorPro;

    }

    public Cursor AllProductUserSearchedInformation(String itemname){
        db=this.getWritableDatabase();
        String query="select * from "+table2+" where ITEMNAME LIKE '%"+itemname+"%'";
        Cursor cursorPro=db.rawQuery(query,null);
        return cursorPro;

    }

    public void updatePassword(String usrid,String password){
        db=this.getWritableDatabase();
        ContentValues CV=new ContentValues();
        CV.put(col3,password);

        db.update(table,CV,"CUSID='"+usrid+"'",null);


    }

}
