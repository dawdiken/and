package com.example.david.final_exam_rev1;
//USING ANDROID-SQLITE DATABASES

import android.content.ContentValues;
import android.database.Cursor;
import android.database.SQLException;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteException;
import android.util.Log;

import java.io.File;

import android.app.Activity;
import android.content.ContentValues;
import android.database.Cursor;
import android.database.SQLException;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteException;
import android.os.Bundle;
import android.util.Log;
import android.widget.TextView;

import java.io.File;

public class SQLDemo2 extends Activity{
    SQLiteDatabase db;
//    TextView txtMsg;

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);




        try {
            openDatabase(); // open (create if needed) database
            dropTable(); // if needed drop table tblAmigos
            insertSomeDbData(); // create-populate tblAmigos
            useRawQueryShowAll(); // display all records
            useRawQuery1(); // fixed SQL with no arguments
            useRawQuery2(); // parameter substitution
            useRawQuery3(); //manual string concatenation
            useSimpleQuery1(); //simple (parametric) query
            useSimpleQuery2(); //nontrivial 'simple query'
            showTable("tblAmigo"); //retrieve all rows from a table
            updateDB(); //use execSQL to update
            String name = "john";
            String phone = "0980";
            useInsertMethod(name,phone); //use insert method
            useUpdateMethod(); //use update method
            useDeleteMethod(); //use delete method

            db.close(); // make sure to release the DB
//            txtMsg.append("\nAll Done!");
        } catch (Exception e) {
//            txtMsg.append("\nError onCreate: " + e.getMessage());
//            finish();
        }
    }// onCreate

    // /////////////////////////////////////////////////////////////////
    public void openDatabase() {
        try {
            // path to private memory:
            String SDcardPath = "com.example.david.final_exam_rev1";
//            String SDcardPath = "com.example.david.myapplication";
            // -----------------------------------------------------------
            // this provides the path name to the SD card
            // String SDcardPath = Environment.getExternalStorageDirectory().getPath();

            String myDbPath = SDcardPath + "/" + "myfriendsDB5.db";
//            txtMsg.append("\n-openDatabase - DB Path: " + myDbPath);
            File file = new File(myDbPath);
            if (file.exists() && !file.isDirectory())
                db = SQLiteDatabase.openDatabase(myDbPath, null,
                        SQLiteDatabase.CREATE_IF_NECESSARY);
//            System.out.println("im herererererererrrrrrrrrrrrrrrrrrrrrrrrrrrrrr");
//            System.out.println("im herererererererrrrrrrrrrrrrrrrrrrrrrrrrrrrrr");
//            System.out.println("im herererererererrrrrrrrrrrrrrrrrrrrrrrrrrrrrr");
//            System.out.println("im herererererererrrrrrrrrrrrrrrrrrrrrrrrrrrrrr");
//            db.close(); // make sure to release the DB

//            txtMsg.append("\n-openDatabase - DB was opened");
        } catch (SQLiteException e) {
            System.out.println("helo");
//            finish();
        }
    }// createDatabase

    // /////////////////////////////////////////////////////////////////
    public void insertSomeDbData() {
        // create table: tblAmigo
        db.beginTransaction();
        try {
            // create table
            db.execSQL("create table tblAMIGO ("
                    + " recID integer PRIMARY KEY autoincrement, "
                    + " name  text, " + " phone text );  ");
            // commit your changes
            db.setTransactionSuccessful();

//            txtMsg.append("\n-insertSomeDbData - Table was created");

        } catch (SQLException e1) {
//            txtMsg.append("\nError insertSomeDbData: " + e1.getMessage());
//            finish();
        } finally {
            db.endTransaction();
        }

        // populate table: tblAmigo
        db.beginTransaction();
        try {

            // insert rows
            db.execSQL("insert into tblAMIGO(name, phone) "
                    + " values ('AAA', '555-1111' );");
            db.execSQL("insert into tblAMIGO(name, phone) "
                    + " values ('BBB', '555-2222' );");
            db.execSQL("insert into tblAMIGO(name, phone) "
                    + " values ('CCC', '555-3333' );");

            // commit your changes
            db.setTransactionSuccessful();
//            txtMsg.append("\n-insertSomeDbData - 3 rec. were inserted");

        } catch (SQLiteException e2) {
//            txtMsg.append("\nError insertSomeDbData: " + e2.getMessage());

        } finally {
            db.endTransaction();
        }

    }// insertSomeData


    // ///////////////////////////////////////////////////////////////
    public void useRawQueryShowAll() {
        try {
            // hard-coded SQL select with no arguments
            String mySQL = "select * from tblAMIGO";
            Cursor c1 = db.rawQuery(mySQL, null);

//            txtMsg.append("\n-useRawQueryShowAll" + showCursor(c1) );

        } catch (Exception e) {
//            txtMsg.append("\nError useRawQuery1: " + e.getMessage());

        }
    }// useRawQuery1


    // ///////////////////////////////////////////////////////////

    public String showCursor( Cursor cursor) {
        // show SCHEMA (column names & types)
        cursor.moveToPosition(-1); //reset cursor's top
        String cursorData = "\nCursor: [";

        try {
            // get column names
            String[] colName = cursor.getColumnNames();
            for(int i=0; i<colName.length; i++){
                String dataType = getColumnType(cursor, i);
                cursorData += colName[i] + dataType;

                if (i<colName.length-1){
                    cursorData+= ", ";
                }
            }
        } catch (Exception e) {
            Log.e( "<<SCHEMA>>" , e.getMessage() );
        }
        cursorData += "]";

        // now get the rows
        cursor.moveToPosition(-1); //reset cursor's top
        while (cursor.moveToNext()) {
            String cursorRow = "\n[";
            for (int i = 0; i < cursor.getColumnCount(); i++) {
                cursorRow += cursor.getString(i);
                if (i<cursor.getColumnCount()-1)
                    cursorRow +=  ", ";
            }
            cursorData += cursorRow + "]";
        }
        return cursorData + "\n";
    }

    public String getColumnType(Cursor cursor, int i) {
        try {
            //peek at a row holding valid data
            cursor.moveToFirst();
            int result = cursor.getType(i);
            String[] types = {":NULL", ":INT", ":FLOAT", ":STR", ":BLOB", ":UNK" };
            //backtrack - reset cursor's top
            cursor.moveToPosition(-1);
            return types[result];
        } catch (Exception e) {
            return " ";
        }
    }
    // ///////////////////////////////////////////////////////////////
    public void useRawQuery1() {
        try {
            // hard-coded SQL select with no arguments
            String mySQL = "select * from tblAMIGO";
            Cursor c1 = db.rawQuery(mySQL, null);

            // get the first recID
            c1.moveToFirst();
            int index = c1.getColumnIndex("recID");
            int theRecID = c1.getInt(index);

//            txtMsg.append("\n-useRawQuery1 - first recID  " + theRecID);
//            txtMsg.append("\n-useRawQuery1" + showCursor(c1) );

        } catch (Exception e) {
//            txtMsg.append("\nError useRawQuery1: " + e.getMessage());

        }
    }// useRawQuery1
    // ///////////////////////////////////////////////////////////////
    public void useRawQuery2() {
        try {
            // use: ? as argument's placeholder

            String mySQL = " select recID, name, phone "
                    + " from tblAmigo "
                    + " where recID > ? " + "   and name  = ? ";
            String[] args = { "1", "BBB" };
            Cursor c1 = db.rawQuery(mySQL, args);

            // pick NAME from first returned row
            c1.moveToFirst();
            int index = c1.getColumnIndex("name");
            String theName = c1.getString(index);
//
//            txtMsg.append("\n-useRawQuery2 Retrieved name: " + theName);
//            txtMsg.append("\n-useRawQuery2 " + showCursor(c1) );

        } catch (Exception e) {
//            txtMsg.append("\nError useRawQuery2: " + e.getMessage());

        }
    }// useRawQuery2

    // ///////////////////////////////////////////////////////////////
    public void useRawQuery3() {
        try {
            // arguments injected by manual string concatenation
            String[] args = { "1", "BBB" };

            String mySQL = " select recID, name, phone"
                    + "   from tblAmigo "
                    + "  where recID > "  + args[0]
                    + "    and name  = '" + args[1] + "'";

            Cursor c1 = db.rawQuery(mySQL, null);

            // pick PHONE from first returned row
            int index = c1.getColumnIndex("phone");	//case sensitive
            c1.moveToNext();
            String thePhone = c1.getString(index);

//            txtMsg.append("\n-useRawQuery3 - Phone: " + thePhone);
//            txtMsg.append("\n-useRawQuery3  " + showCursor(c1) );

        } catch (Exception e) {
//            txtMsg.append("\nError useRawQuery3: " + e.getMessage());

        }
    }// useRawQuery3

    // ///////////////////////////////////////////////////////////////
    public void useSimpleQuery1() {
        try {
            // simple-parametric query on one table.
            // arguments: tableName, columns, condition, cond-args,
            // 			  groupByCol, havingCond, orderBy
            // the next parametric query is equivalent to SQL stmt:
            // 		select recID, name, phone from tblAmigo
            // 		where recID > 1 and length(name) >= 3
            // 		order by recID

            Cursor c1 = db.query(
                    "tblAMIGO",
                    new String[] { "recID", "name", "phone" },
                    "recID > 1 and length(name) >= 3 ",
                    null,
                    null,
                    null,
                    "recID");

            // get NAME from first data row
            int index = c1.getColumnIndex("phone");
            c1.moveToFirst();
            String theName = c1.getString(index);

//            txtMsg.append("\n-useSimpleQuery1 - Total rec " + theName);
//            txtMsg.append("\n-useSimpleQuery1 " + showCursor(c1) );

        } catch (Exception e) {
//            txtMsg.append("\nError useSimpleQuery1: " + e.getMessage());
        }
    }// useSimpleQuery1

    // ///////////////////////////////////////////////////////////////
    public void useSimpleQuery2() {
        try {
            // nontrivial 'simple query' on one table
            String[] selectColumns = { "name", "count(*) as TotalSubGroup" };
            String whereCondition = "recID >= ?";
            String[] whereConditionArgs = { "1" };
            String groupBy = "name";
            String having = "count(*) <= 4";
            String orderBy = "name";

            Cursor c1 = db.query("tblAMIGO", selectColumns, whereCondition,
                    whereConditionArgs, groupBy, having, orderBy);

            int theTotalRows = c1.getCount();
//            txtMsg.append("\n-useSimpleQuery2 - Total rec: " + theTotalRows);
//            txtMsg.append("\n-useSimpleQuery2 " + showCursor(c1) );

        } catch (Exception e) {
//            txtMsg.append("\nError useSimpleQuery2: " + e.getMessage());

        }
    }// useSimpleQuery2

    // ///////////////////////////////////////////////////////////////
    public void showTable(String tableName) {
        try {
            String sql = "select * from " + tableName ;
            Cursor c = db.rawQuery(sql, null);
//            txtMsg.append("\n-showTable: " + tableName + showCursor(c) );

        } catch (Exception e) {
//            txtMsg.append("\nError showTable: " + e.getMessage());

        }
    }// useCursor1


    // ///////////////////////////////////////////////////////////////
    public void useCursor1() {
        try {
            // this is similar to showCursor(...)
            // obtain a list of records[recId, name, phone] from DB
            String[] columns = { "recID", "name", "phone" };
            // using simple parametric cursor
            Cursor c = db.query("tblAMIGO", columns, null, null, null, null,
                    "recID");


            int theTotal = c.getCount();
//            txtMsg.append("\n-useCursor1 - Total rec " + theTotal);
//            txtMsg.append("\n");
            int idCol = c.getColumnIndex("recID");
            int nameCol = c.getColumnIndex("name");
            int phoneCol = c.getColumnIndex("phone");

            c.moveToPosition(-1);
            while (c.moveToNext()) {
                columns[0] = Integer.toString((c.getInt(idCol)));
                columns[1] = c.getString(nameCol);
                columns[2] = c.getString(phoneCol);

//                txtMsg.append(columns[0] + " " + columns[1] + " " + columns[2]
//                        + "\n");
            }

        } catch (Exception e) {
//            txtMsg.append("\nError useCursor1: " + e.getMessage());
//            finish();
        }
    }// useCursor1

    // ///////////////////////////////////////////////////////////////////
    public void updateDB() {
        // action query performed using execSQL
        // add 'XXX' to the name of person whose phone is 555-1111
//        txtMsg.append("\n-updateDB");

        try {
            String thePhoneNo = "555-1111";
            db.execSQL(" update tblAMIGO set name = (name || 'XXX') "
                    + " where phone = '" + thePhoneNo + "' ");
            showTable("tblAmigo");

        } catch (Exception e) {
//            txtMsg.append("\nError updateDB: " + e.getMessage());

        }
        useCursor1();
    }

    // ///////////////////////////////////////////////////////////////////
    public void dropTable() {
        // (clean start) action query to drop table

        try {
            db.execSQL("DROP TABLE IF EXISTS tblAmigo;");
//            txtMsg.append("\n-dropTable - dropped!!");
        } catch (Exception e) {
//            txtMsg.append("\nError dropTable: " + e.getMessage());
//            finish();
        }
    }

    // //////////////////////////////////////////////////////////////////
    public void useInsertMethod(String name, String phone) {
        // an alternative to SQL "insert into table values(...)"
        // ContentValues is an Android dynamic row-like container
        try {
            ContentValues initialValues = new ContentValues();
            initialValues.put("name", name);
            initialValues.put("phone", phone);

            int rowPosition = (int) db.insert("tblAMIGO", null, initialValues);

//            txtMsg.append("\n-useInsertMethod rec added at: " + rowPosition);
            showTable("tblAmigo");

        } catch (Exception e) {
//            txtMsg.append("\n-useInsertMethod - Error: " + e.getMessage());
        }

    }// useInsertMethod

    // ///////////////////////////////////////////////////////////////////
    public void useUpdateMethod() {
        try {
            // using the 'update' method to change name of selected friend
            String[] whereArgs = { "1" };

            ContentValues updValues = new ContentValues();
            updValues.put("name", "Maria");

            int recAffected = db.update("tblAMIGO", updValues,
                    "recID = ? ", whereArgs);

//            txtMsg.append("\n-useUpdateMethod - Rec Affected " + recAffected);
            showTable("tblAmigo");

        } catch (Exception e) {
//            txtMsg.append("\n-useUpdateMethod - Error: " + e.getMessage() );
        }
    }

    // ///////////////////////////////////////////////////////////////////
    public void useDeleteMethod() {
        // using the 'delete' method to remove a group of friends
        // whose id# is between 2 and 7

        try {
            String[] whereArgs = { "2" };

            int recAffected = db.delete("tblAMIGO", "recID = ?",
                    whereArgs);

//            txtMsg.append("\n-useDeleteMethod - Rec affected " + recAffected);
            showTable("tblAmigo");

        } catch (Exception e) {
//            txtMsg.append("\n-useDeleteMethod - Error: " + e.getMessage());
        }
    }

}// class