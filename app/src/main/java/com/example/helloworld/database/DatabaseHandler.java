package com.example.helloworld.database;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.util.Log;

import org.apache.commons.lang3.SerializationUtils;

import java.io.ByteArrayInputStream;
import java.io.IOException;
import java.io.ObjectInputStream;
import java.util.ArrayList;
import java.util.List;


public class DatabaseHandler extends SQLiteOpenHelper {
    private static final int DATABASE_VERSION = 1;
    private static final String DATABASE_NAME = "MyThemesManager";
    private static final String TABLE_NAME = "themes";
    private static final String KEY_ID = "id";
    private static final String KEY_CLASS = "saveClass";


    public static List<String> db_entry_index = new ArrayList<String>();
    int db_entry_counter=0;



    public DatabaseHandler(Context context) {
        super(context, DATABASE_NAME, null, DATABASE_VERSION);
        //3rd argument to be passed is CursorFactory instance
    }

    // Creating Tables
    @Override
    public void onCreate(SQLiteDatabase db) {
        String CREATE_CONTACTS_TABLE = "CREATE TABLE " + TABLE_NAME + " ("
                + KEY_ID + " INTEGER PRIMARY KEY,"
                + KEY_CLASS + " BLOB "
                + ")";
        db.execSQL(CREATE_CONTACTS_TABLE);

    }

    // Upgrading database
    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
        // Drop older table if existed
        db.execSQL("DROP TABLE IF EXISTS " + TABLE_NAME);

        // Create tables again
        onCreate(db);
    }

    public void storeClass(ClassTheme modeldata) {
        Log.d("DatabaseHandler", "Store Class for: "+modeldata.theme_title);
        Log.d("DatabaseHandler", "Size of  db_entry_index: "+db_entry_index.size());

        byte[] data = SerializationUtils.serialize(modeldata);
        SQLiteDatabase db = this.getWritableDatabase();
        ContentValues values = new ContentValues();
        values.put(KEY_CLASS, data);

        if(!db_entry_index.contains(modeldata.theme_title)) {
            Log.d("DatabaseHandler", "db_entry_index NOT CONTAINS theme_titel: ");
            db_entry_index.add(modeldata.theme_title);
            Log.d("DatabaseHandler","Inserted Row: "+db.insert(TABLE_NAME,null, values));

        }
        else {
            Log.d("DatabaseHandler", "db_entry_index CONTAIN theme_titel: ");
            int int_index = db_entry_index.indexOf(modeldata.theme_title)+1;
            String index = String.valueOf( int_index);
            Log.d("DatabaseHandler", "Index of Row to update: "+ index);
            Log.d("DatabaseHandler","Inserted Row: "+ db.update(TABLE_NAME,values, KEY_ID + "=" + index, null));
        }
        Log.d("DatabaseHandler","");

        //db.update(TABLE_NAME,values,KEY_ID + " =  '" +db_entry_index.indexOf(modeldata.theme_title) , null);
        //Log.d("DatabaseHandler","Insertet Row: "+db.update(TABLE_NAME,values,KEY_ID + " =  '" +db_entry_index.indexOf(modeldata.theme_title) + "'" , null));
        //Log.d("DatabaseHandler","Insertet Row: "+db.insert(TABLE_NAME, null, values));
        db.close(); // Closing database connection
    }

    public ClassTheme restoreClass(String theme_title) {
        //SQLiteDatabase db = this.getReadableDatabase();
        //Cursor res =  db.rawQuery( "select * from contacts where id="+id+"", null );
        //return res;
        Log.d("DatabaseHandler","Index of ArrayList for restore: "+db_entry_index.indexOf(theme_title));
        SQLiteDatabase db = this.getReadableDatabase();

        Cursor cursor = db.query(TABLE_NAME, new String[] {
                        KEY_ID, KEY_CLASS}, KEY_ID + "=?",
                new String[] { String.valueOf(db_entry_index.indexOf(theme_title)) }, null, null, null, null);

        ClassLamp elementData = new ClassLamp(Boolean.FALSE, 0, 0, 100,100);
        ClassTheme classTheme = new ClassTheme("Database Error");
        classTheme.addClassTheme(elementData);

        if (cursor != null && cursor.moveToFirst()) {
            if (cursor.getString(0) != null) {
                Log.d("DatabaseHandler", "Databasehandler Description: " + cursor.getInt(0));
            }

            if (cursor.getBlob(1) != null) {
                Log.d("DatabaseHandler", "returned from db");

                return restoreClassFromDB(cursor.getBlob(1));
            }
        }
        // return contact
        Log.d("DatabaseHandler", "returned from Code");
        return classTheme;
    }


    public ClassTheme restoreClassFromDB(byte[] data) {
        try {


            ByteArrayInputStream baip = new ByteArrayInputStream(data);
            ObjectInputStream ois = new ObjectInputStream(baip);
            ClassTheme dataobj = (ClassTheme) ois.readObject();
            return dataobj ;

        } catch (IOException e) {
            e.printStackTrace();
        } catch (ClassNotFoundException e) {
            e.printStackTrace();
        }
        return null;
    }


    public ClassLamp read(byte[] data) {
        try {


            ByteArrayInputStream baip = new ByteArrayInputStream(data);
            ObjectInputStream ois = new ObjectInputStream(baip);
            ClassLamp dataobj = (ClassLamp) ois.readObject();
            return dataobj ;

        } catch (IOException e) {
            e.printStackTrace();
        } catch (ClassNotFoundException e) {
            e.printStackTrace();
        }
        return null;
    }


    public void deleteAllEntries() {
        SQLiteDatabase db = this.getWritableDatabase();
        Log.d("sql", "Delete Database");
        //"delete from "+ TABLE_NAME
        db.delete(TABLE_NAME,null, null);
        db.close();
    }







    void deleteMealAndRecipe(String name) {
        SQLiteDatabase db = this.getWritableDatabase();
        Log.d("sql", "Delete Database ENtry with Name: " + name);
        db.delete(TABLE_NAME, KEY_CLASS + " =?", new String[]{name});
        db.close();
    }

    void deleteMealAndRecipe(int id) {
        SQLiteDatabase db = this.getWritableDatabase();
        Log.d("sql", "Delete Database ENtry with ID: " + id);
        db.delete(TABLE_NAME, KEY_ID + " =?", new String[]{String.valueOf(id)});
        db.close();
    }

    /*public List<ElementDataMeal> searchMeals(String name) {
        List<ElementDataMeal> receipeList = null;
        Log.d("elementData", "Search for " + name + " in DB");
        if(name.isEmpty()) {
            return getMeals();
        }
        try
            {
            SQLiteDatabase db = this.getReadableDatabase();
            //Cursor cursor = db.rawQuery("select * from " + TABLE_RECEIPE + " where " + KEY_TITLE + " like  ?", new String[] {name});
            Cursor cursor = db.rawQuery("select * from " + TABLE_RECEIPE + " where " + KEY_MEAL_TITLE + "=?", new String[] {name});
            if (cursor.moveToFirst()) {
                receipeList = new ArrayList<ElementDataMeal>();
                do {
                    ElementDataMeal elementData = new ElementDataMeal(null, null, null, null);
                    elementData.setName(cursor.getString(2));
                    elementData.setDescription(cursor.getString(3));
                    elementData.setImageEatPath(Uri.parse(cursor.getString(4)));
                    receipeList.add(elementData);

                } while (cursor.moveToNext());
            }
            else {
                    Log.d("elementData", "Return no entries");
                    ElementDataMeal elementData = new ElementDataMeal("Kein Suchergebniss", null, null, null);
                    receipeList = new ArrayList<ElementDataMeal>();
                    receipeList.add(elementData);
                    return receipeList;
            }
        } catch (Exception e) {
            receipeList = null;
        }
        return receipeList;
    }*/

    /*ElementData getReceipe(int position) {
        SQLiteDatabase db = this.getReadableDatabase();

        Cursor cursor = db.query(TABLE_RECEIPE, new String[] { KEY_ID,
                        KEY_TITLE, KEY_DESCRIPTION, KEY_PATH }, KEY_ID + "=?",
                new String[] { String.valueOf(position) }, null, null, null, null);
        if (cursor != null)
            cursor.moveToFirst();

        //ElementData elementData = new ElementData(cursor.getString(0),cursor.getString(1),5, Uri.parse(cursor.getString(2)));
        ElementData elementData = new ElementData(cursor.getString(1), cursor.getString(2), 0, Uri.parse(cursor.getString(3)));
        // return contact
        return elementData;
    }*/

    /*public List<ElementDataMeal> getMeals() {
        List<ElementDataMeal> receipeList = new ArrayList<ElementDataMeal>();
        // Select All Query
        String selectQuery = "SELECT  * FROM " + TABLE_RECEIPE;

        SQLiteDatabase db = this.getWritableDatabase();
        Cursor cursor = db.rawQuery(selectQuery, null);

        // looping through all rows and adding to list
        if (cursor.moveToFirst()) {
            do {
                ElementDataMeal elementData = new ElementDataMeal(null, null, null, null);
                elementData.setName(cursor.getString(1));
                elementData.setDescription(cursor.getString(2));
                elementData.setImageEatPath(Uri.parse(cursor.getString(3)));
                Log.d("DatabaseHandler", "Databasehandler Meal Index: "+cursor.getString(0));
                Log.d("DatabaseHandler", "Databasehandler Meal Title: "+cursor.getString(1));
                Log.d("DatabaseHandler", "Databasehandler Meal Description: "+cursor.getString(2));
                Log.d("DatabaseHandler", "Databasehandler Meal Path: "+cursor.getString(3));
                receipeList.add(elementData);
            } while (cursor.moveToNext());
        }
        return receipeList;
    }*/

    /*public List<ElementDataRecipe> getRecipe() {
        List<ElementDataRecipe> receipeList = new ArrayList<ElementDataRecipe>();
        // Select All Query
        String selectQuery = "SELECT  * FROM " + TABLE_RECEIPE;

        SQLiteDatabase db = this.getWritableDatabase();
        Cursor cursor = db.rawQuery(selectQuery, null);

        // looping through all rows and adding to list
        if (cursor.moveToFirst()) {
            do {
                ElementDataRecipe elementData = new ElementDataRecipe(null, null);
                Log.d("DatabaseHandler", "Databasehandler Recipe Description: "+cursor.getString(4));
                Log.d("DatabaseHandler", "Databasehandler Recipe Path: "+cursor.getString(5));
                elementData.setDescription(cursor.getString(4));
                elementData.addRecipeToList(Uri.parse(cursor.getString(5)));
                // Adding contact to list
                receipeList.add(elementData);
            } while (cursor.moveToNext());
        }
        return receipeList;
    }*/

    /*public ElementDataRecipe getRecipe(int position) {
        SQLiteDatabase db = this.getReadableDatabase();

        Cursor cursor = db.query(TABLE_RECEIPE, new String[] { KEY_ID,
                        KEY_MEAL_TITLE, KEY_MEAL_DESCRIPTION, KEY_MEAL_PATH, KEY_RECIPE_DESCRIPTION, KEY_RECIPE_PATH}, KEY_ID + "=?",
                new String[] { String.valueOf(position) }, null, null, null, null);
        if (cursor != null)
            cursor.moveToFirst();


        Log.d("DatabaseHandler", "Databasehandler Description: "+cursor.getString(4));
        Log.d("DatabaseHandler", "Databasehandler Path: "+cursor.getString(5));

        //ElementData elementData = new ElementData(cursor.getString(0),cursor.getString(1),5, Uri.parse(cursor.getString(2)));
        ElementDataRecipe elementData = new ElementDataRecipe(null,null);
        elementData.clear();
        if(cursor.getString(4) != null) {
            elementData.setDescription(cursor.getString(4));
        }
        if(cursor.getString(5) != null) {
            String recipePath_string_list = cursor.getString(5);
            for(int index = 0; index<recipePath_string_list.split("\n",100).length-1; index++) {
                elementData.addRecipeToList(Uri.parse(recipePath_string_list.split("\n",100)[index]));
                Log.d("DatabaseHandler", "Paths: " + elementData.getRecipeItem(index));
            }
            Log.d("DatabaseHandler", "elementdate size: "+ elementData.recipe_image_path.size());
        }
        // return contact
        return elementData;
    }*/


//    // code to get all contacts in a list view
//    public List<Contact> getAllContacts() {
//        List<Contact> contactList = new ArrayList<Contact>();
//        // Select All Query
//        String selectQuery = "SELECT  * FROM " + TABLE_RECEIPE;
//
//        SQLiteDatabase db = this.getWritableDatabase();
//        Cursor cursor = db.rawQuery(selectQuery, null);
//
//        // looping through all rows and adding to list
//        if (cursor.moveToFirst()) {
//            do {
//                Contact contact = new Contact();
//                contact.setID(Integer.parseInt(cursor.getString(0)));
//                contact.setName(cursor.getString(1));
//                contact.setPhoneNumber(cursor.getString(2));
//                // Adding contact to list
//                contactList.add(contact);
//            } while (cursor.moveToNext());
//        }
//
//        // return contact list
//        return contactList;
//    }
//
//    // code to update the single contact
//    public int updateContact(Contact contact) {
//        SQLiteDatabase db = this.getWritableDatabase();
//
//        ContentValues values = new ContentValues();
//        values.put(KEY_TITLE, contact.getName());
//        values.put(KEY_DESCRIPTION, contact.getPhoneNumber());
//
//        // updating row
//        return db.update(TABLE_RECEIPE, values, KEY_ID + " = ?",
//                new String[] { String.valueOf(contact.getID()) });
//    }
//
//    // Deleting single contact
//    public void deleteContact(Contact contact) {
//        SQLiteDatabase db = this.getWritableDatabase();
//        db.delete(TABLE_RECEIPE, KEY_ID + " = ?",
//                new String[] { String.valueOf(contact.getID()) });
//        db.close();
//    }
//
//    // Getting contacts Count
    public int getTableSize() {
        String countQuery = "SELECT  * FROM " + TABLE_NAME;
        SQLiteDatabase db = this.getReadableDatabase();
        Cursor cursor = db.rawQuery(countQuery, null);
        int itemsCount = cursor.getCount();
        cursor.close();

        Log.d("DatabaseHandler", "Databasehandler SIze:" + itemsCount);
        // return count
        return itemsCount;
    }

}