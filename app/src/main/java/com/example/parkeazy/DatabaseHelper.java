package com.example.parkeazy;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;

import com.example.parkeazy.DatabaseTables.User;
import com.example.parkeazy.DatabaseTables.ParkingLot;
import com.example.parkeazy.DatabaseTables.Reservation;

import java.io.ByteArrayOutputStream;
import java.util.ArrayList;
import java.util.List;


public class DatabaseHelper extends SQLiteOpenHelper {

    // Database version
    private static final int DATABASE_VERSION = 1;

    // Database name
    private static final String DATABASE_NAME = "parking.db";

    // User Table Name and Users Table Columns Names
    private static final String TABLE_USER = "Users";
    private static final String COLUMN_USER_ID = "user_id";
    private static final String COLUMN_USER_NAME = "user_name";
    private static final String COLUMN_USER_EMAIL = "user_email";
    private static final String COLUMN_USER_PASSWORD = "user_password";

    // ParkingLot Table Name and ParkingLots Table Columns Names
    private static final String TABLE_PARKINGLOT = "ParkingLots";
    private static final String COLUMN_PARKINGLOT_ID = "parkinglot_id";
    private static final String COLUMN_PARKINGLOT_NAME = "parkinglot_name";
    private static final String COLUMN_PARKINGLOT_CITY = "parkinglot_city";
    private static final String COLUMN_PARKINGLOT_SPOTS = "parkinglot_spots";
    private static final String COLUMN_PARKINGLOT_LATITUDE = "parkinglot_latitude";
    private static final String COLUMN_PARKINGLOT_LONGITUDE = "parkinglot_longitutde";

    // Reservation Table Name and Reservations Table Columns Names
    private static final String TABLE_RESERVATION = "Reservations";
    private static final String COLUMN_RESERVATION_ID = "reservation_id";
    private static final String COLUMN_RESERVATION_EMAIL = "reservation_email";
    private static final String COLUMN_RESERVATION_CITY = "reservation_city";
    private static final String COLUMN_RESERVATION_PARKINGLOT = "reservation_parkinglot";
    private static final String COLUMN_RESERVATION_DATE = "reservation_date";
    private static final String COLUMN_RESERVATION_TIME = "reservation_time";
    private static final String COLUMN_RESERVATION_QR = "reservation_qr";

    // Create table sql query
    private final String CREATE_USER_TABLE = "CREATE TABLE " + TABLE_USER + "("
            + COLUMN_USER_ID + " INTEGER PRIMARY KEY AUTOINCREMENT, " + COLUMN_USER_NAME + " TEXT NOT NULL, "
            + COLUMN_USER_EMAIL + " TEXT NOT NULL, " + COLUMN_USER_PASSWORD + " TEXT NOT NULL" + ")";

    private final String CREATE_PARKINGLOT_TABLE = "CREATE TABLE " + TABLE_PARKINGLOT + "("
            + COLUMN_PARKINGLOT_ID + " INTEGER PRIMARY KEY AUTOINCREMENT, "
            + COLUMN_PARKINGLOT_NAME + " TEXT NOT NULL, "
            + COLUMN_PARKINGLOT_CITY + " TEXT NOT NULL, "
            + COLUMN_PARKINGLOT_SPOTS + " INTEGER NOT NULL, "
            + COLUMN_PARKINGLOT_LATITUDE + " REAL NOT NULL, "
            + COLUMN_PARKINGLOT_LONGITUDE + " REAL NOT NULL" + ")";

    private final String CREATE_RESERVATION_TABLE = "CREATE TABLE " + TABLE_RESERVATION + "("
            + COLUMN_RESERVATION_ID + " INTEGER PRIMARY KEY AUTOINCREMENT, "
            + COLUMN_RESERVATION_EMAIL + " TEXT NOT NULL, "
            + COLUMN_RESERVATION_CITY + " TEXT NOT NULL, "
            + COLUMN_RESERVATION_PARKINGLOT + " TEXT NOT NULL, "
            + COLUMN_RESERVATION_DATE + " TEXT NOT NULL, "
            + COLUMN_RESERVATION_TIME + " TEXT NOT NULL,"
            + COLUMN_RESERVATION_QR + " BLOB" + ")";

    // drop table sql query
    private final String DROP_USER_TABLE = "DROP TABLE IF EXISTS " + TABLE_USER;
    private final String DROP_PARKINGLOT_TABLE = "DROP TABLE IF EXISTS " + TABLE_PARKINGLOT;
    private final String DROP_RESERVATION_TABLE = "DROP TABLE IF EXISTS " + TABLE_RESERVATION;

    // Insert the parking lots into the ParkingLots table
    private final String INSERT_PARKINGLOTS_SKOPJE = "INSERT INTO " + TABLE_PARKINGLOT + "("
            + COLUMN_PARKINGLOT_NAME + ", " + COLUMN_PARKINGLOT_CITY
            + ", " + COLUMN_PARKINGLOT_SPOTS + ", " + COLUMN_PARKINGLOT_LATITUDE + ", "
            + COLUMN_PARKINGLOT_LONGITUDE + ") " +
            "VALUES('Boris Trajkovski', 'Skopje', 100, 42.009899587791814, 21.4057505572027), " +
            "('FEIT', 'Skopje', 50, 42.00534440297704, 21.408763851705082), " +
            "('Leptokarija', 'Skopje', 30, 42.004185243625244, 21.397907690954575), " +
            "('Skopje City Mall', 'Skopje', 200, 42.005384239619424, 21.39234603311928), " +
            "('Vlae', 'Skopje', 48, 42.00692754276735, 21.372312253765386), " +
            "('Karpos 4', 'Skopje', 35, 42.00642936732655, 21.381500031084354), " +
            "('GTC', 'Skopje', 150, 41.99382325799338, 21.436790634346163), " +
            "('Ramstore', 'Skopje', 70, 41.99230116294475, 21.425982470353645), " +
            "('Skopje Zoo', 'Skopje', 50, 42.00526605726059, 21.41797459060777)";

    private final String INSERT_PARKINGLOTS_BITOLA = "INSERT INTO " + TABLE_PARKINGLOT + "("
            + COLUMN_PARKINGLOT_NAME + ", " + COLUMN_PARKINGLOT_CITY
            + ", " + COLUMN_PARKINGLOT_SPOTS + ", " + COLUMN_PARKINGLOT_LATITUDE + ", "
            + COLUMN_PARKINGLOT_LONGITUDE + ") " +
            "VALUES('City Hall', 'Bitola', 40, 41.03271534595689, 21.331762393130955), " +
            "('Javor Trade Center', 'Bitola', 30, 41.03382611046311, 21.336609441190582), " +
            "('Magnolia Square', 'Bitola', 50, 41.03046555841543, 21.334525857743337), " +
            "('Museum Bitola', 'Bitola', 20, 41.02366371704382, 21.33526980634358), " +
            "('Kitchen Bar PLAZA', 'Bitola', 30, 41.02493333515727, 21.325970135423905), " +
            "('Clinic Hospital', 'Bitola', 50, 41.02413321409293, 21.31887048094168), " +
            "('Kocho Racin', 'Bitola', 28, 41.0207462371568, 21.324256981117447), " +
            "('Army Stadium', 'Bitola', 35, 41.02160568118958, 21.33213400065658)";

    private final String INSERT_PARKINGLOTS_KUMANOVO = "INSERT INTO " + TABLE_PARKINGLOT + "("
            + COLUMN_PARKINGLOT_NAME + ", " + COLUMN_PARKINGLOT_CITY
            + ", " + COLUMN_PARKINGLOT_SPOTS + ", " + COLUMN_PARKINGLOT_LATITUDE + ", "
            + COLUMN_PARKINGLOT_LONGITUDE + ") " +
            "VALUES('General Hospital', 'Kumanovo', 78, 42.13781020737933, 21.709046511939984), " +
            "('Museum Kumanovo', 'Kumanovo', 50, 42.13787028143484, 21.718038997216336), " +
            "('Setec Kumanovo', 'Kumanovo', 25, 42.13531636520832, 21.725207882104044), " +
            "('Baba Cana', 'Kumanovo', 38, 42.13653577153848, 21.721180707762915), " +
            "('11 Oktomvri', 'Kumanovo', 45, 42.13941275720915, 21.714853453892342), " +
            "('Pitu Guli', 'Kumanovo', 43, 42.13969948131441, 21.700088216241788), " +
            "('Bajram Shabani', 'Kumanovo', 50, 42.13375043787813, 21.713658793341256), " +
            "('Gostilnica Doma', 'Kumanovo', 25, 42.132171688584165, 21.71582172596504)";

    private final String INSERT_PARKINGLOTS_PRILEP = "INSERT INTO " + TABLE_PARKINGLOT + "("
            + COLUMN_PARKINGLOT_NAME + ", " + COLUMN_PARKINGLOT_CITY
            + ", " + COLUMN_PARKINGLOT_SPOTS + ", " + COLUMN_PARKINGLOT_LATITUDE + ", "
            + COLUMN_PARKINGLOT_LONGITUDE + ") " +
            "VALUES('Vojdan Cernodrinski', 'Prilep', 40, 41.34123576277522, 21.55860420427835), " +
            "('Borka Taleski', 'Prilep', 60, 41.34209900817622, 21.562289435089347), " +
            "('Kristal Palas', 'Prilep', 71, 41.34460890162499, 21.540733551930938), " +
            "('Tutunski Kombinat', 'Prilep', 30, 41.34633046095719, 21.530852162297073), " +
            "('Club Calypso', 'Prilep', 38, 41.344779419233305, 21.55324662764162), " +
            "('Dobre Jovanovski', 'Prilep', 50, 41.35479510032684, 21.5514329548661), " +
            "('Aqua Park Salida', 'Prilep', 71, 41.33297357788562, 21.554033709026214), " +
            "('Sonce GA', 'Prilep', 51, 41.33712372384391, 21.547671043097576)";

    private final String INSERT_PARKINGLOTS_TETOVO = "INSERT INTO " + TABLE_PARKINGLOT + "("
            + COLUMN_PARKINGLOT_NAME + ", " + COLUMN_PARKINGLOT_CITY
            + ", " + COLUMN_PARKINGLOT_SPOTS + ", " + COLUMN_PARKINGLOT_LATITUDE + ", "
            + COLUMN_PARKINGLOT_LONGITUDE + ") " +
            "VALUES('ONE Hospital', 'Tetovo', 62, 42.00325618702329, 20.977728229530282), " +
            "('Clinical Center', 'Tetovo', 58, 42.00579672735434, 20.970619466101695), " +
            "('Fadil Sulejmani', 'Tetovo', 53, 41.9933040093265, 20.961464831470508), " +
            "('Vero & Jumbo', 'Tetovo', 110, 42.00640578975021, 20.981984196932473), " +
            "('Palma Mall', 'Tetovo', 79, 42.00445603011018, 20.987202483760846), " +
            "('City Stadium', 'Tetovo', 90, 42.01856305524606, 20.9771491740295), " +
            "('City Shopping Center', 'Tetovo', 120, 42.00870095784672, 20.969514560301253)";

    private final String INSERT_PARKINGLOTS_OHRID = "INSERT INTO " + TABLE_PARKINGLOT + "("
            + COLUMN_PARKINGLOT_NAME + ", " + COLUMN_PARKINGLOT_CITY
            + ", " + COLUMN_PARKINGLOT_SPOTS + ", " + COLUMN_PARKINGLOT_LATITUDE + ", "
            + COLUMN_PARKINGLOT_LONGITUDE + ") " +
            "VALUES('Ancient Theatre', 'Ohrid', 70, 41.114851923801, 20.794195510317337), " +
            "('Ali Pasha Mosque', 'Ohrid', 28, 41.11541444081432, 20.800626318111288), " +
            "('City Beach', 'Ohrid', 55, 41.120005125357245, 20.78405554734112), " +
            "('St. Climent', 'Ohrid', 38, 41.12158366598262, 20.780635417353327), " +
            "('Saint Sophia', 'Ohrid', 50, 41.11246997410243, 20.794262706055516), " +
            "('Evropa', 'Ohrid', 30, 41.112008039340445, 20.804796358367543), " +
            "('Metodi Patchev', 'Ohrid', 48, 41.11192463411402, 20.810058926749168)";

    private final String INSERT_PARKINGLOTS_VELES = "INSERT INTO " + TABLE_PARKINGLOT + "("
            + COLUMN_PARKINGLOT_NAME + ", " + COLUMN_PARKINGLOT_CITY
            + ", " + COLUMN_PARKINGLOT_SPOTS + ", " + COLUMN_PARKINGLOT_LATITUDE + ", "
            + COLUMN_PARKINGLOT_LONGITUDE + ") " +
            "VALUES('City Park', 'Veles', 50, 41.720318191080686, 21.77517971266664), " +
            "('Primary Court', 'Veles', 38, 41.71703768748762, 21.78322029091526), " +
            "('Kosturnica', 'Veles', 50, 41.72278949041399, 21.78820188459789), " +
            "('St. Spas', 'Veles', 28, 41.706742867386566, 21.78885447611153), " +
            "('Kole Nedelkovski', 'Veles', 15, 41.71905412429746, 21.76322121565553), " +
            "('ASNOM', 'Veles', 40, 41.719397352801025, 21.77010172573434), " +
            "('Saint Panteleimon', 'Veles', 30, 41.7064046066085, 21.781629905666037)";

    private final String INSERT_PARKINGLOTS_STRUMICA = "INSERT INTO " + TABLE_PARKINGLOT + "("
            + COLUMN_PARKINGLOT_NAME + ", " + COLUMN_PARKINGLOT_CITY
            + ", " + COLUMN_PARKINGLOT_SPOTS + ", " + COLUMN_PARKINGLOT_LATITUDE + ", "
            + COLUMN_PARKINGLOT_LONGITUDE + ") " +
            "VALUES('Global Parking', 'Strumica', 80, 41.43965068574011, 22.640844936186202), " +
            "('Museum Strumica', 'Strumica', 50, 41.43520191837705, 22.63740186746864), " +
            "('Moschea Orta', 'Strumica', 45, 41.435596701871354, 22.63428285227743), " +
            "('Mladost Stadium', 'Strumica', 60, 41.44538960070961, 22.636409453535265), " +
            "('Nikola Vapcarov', 'Strumica', 50, 41.42936727207575, 22.651278315358567), " +
            "('Nikola Karev', 'Strumica', 37, 41.427195724698954, 22.643278243897857), " +
            "('St. Kiril & Metodij', 'Strumica', 44, 41.43430233675808, 22.635703492507563)";

    /**
     * Constructor
     *
     * @param context
     */
    public DatabaseHelper(Context context) {
        super(context, DATABASE_NAME, null, DATABASE_VERSION);
    }

    // This is called the first time a database is accessed.
    // There should be code in here to create a new database.
    @Override
    public void onCreate(SQLiteDatabase db) {
        db.execSQL(CREATE_USER_TABLE);
        db.execSQL(CREATE_RESERVATION_TABLE);
        db.execSQL(CREATE_PARKINGLOT_TABLE);
        db.execSQL(INSERT_PARKINGLOTS_SKOPJE);
        db.execSQL(INSERT_PARKINGLOTS_BITOLA);
        db.execSQL(INSERT_PARKINGLOTS_KUMANOVO);
        db.execSQL(INSERT_PARKINGLOTS_PRILEP);
        db.execSQL(INSERT_PARKINGLOTS_TETOVO);
        db.execSQL(INSERT_PARKINGLOTS_OHRID);
        db.execSQL(INSERT_PARKINGLOTS_VELES);
        db.execSQL(INSERT_PARKINGLOTS_STRUMICA);
    }

    // This is called if the database version number changes.
    // It prevents previous users app from breaking when you change the database design.
    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
        //Drop User Table if exist
        db.execSQL(DROP_USER_TABLE);
        db.execSQL(DROP_PARKINGLOT_TABLE);
        db.execSQL(DROP_RESERVATION_TABLE);
        // Create tables again
        onCreate(db);
    }

    /**
     * This method is to create user record
     * @param user
     */
    public void addUser(User user) {
        SQLiteDatabase db = this.getWritableDatabase();
        ContentValues values = new ContentValues();
        values.put(COLUMN_USER_NAME, user.getName());
        values.put(COLUMN_USER_EMAIL, user.getEmail());
        values.put(COLUMN_USER_PASSWORD, user.getPassword());
        // Inserting Row
        db.insert(TABLE_USER, null, values);
        db.close();
    }

    /**
     * This method is to fetch all user and return the list of user records
     * @return list
     */
    public ArrayList<User> getAllUser() {
        // array of columns to fetch
        String[] columns = {
                COLUMN_USER_ID,
                COLUMN_USER_EMAIL,
                COLUMN_USER_NAME,
                COLUMN_USER_PASSWORD
        };
        // sorting orders
        String sortOrder =
                COLUMN_USER_NAME + " ASC";
        ArrayList<User> userList = new ArrayList<User>();
        SQLiteDatabase db = this.getReadableDatabase();
        // query the user table
        /**
         * Here query function is used to fetch records from user table this function works like we use sql query.
         * SQL query equivalent to this query function is
         * SELECT user_id,user_name,user_email,user_password FROM user ORDER BY user_name;
         */
        Cursor cursor = db.query(TABLE_USER, //Table to query
                columns,    //columns to return
                null,        //columns for the WHERE clause
                null,        //The values for the WHERE clause
                null,       //group the rows
                null,       //filter by row groups
                sortOrder); //The sort order
        // Traversing through all rows and adding to list
        if (cursor.moveToFirst()) {
            do {
                User user = new User();
                user.setId(Integer.parseInt(cursor.getString(cursor.getColumnIndex(COLUMN_USER_ID))));
                user.setName(cursor.getString(cursor.getColumnIndex(COLUMN_USER_NAME)));
                user.setEmail(cursor.getString(cursor.getColumnIndex(COLUMN_USER_EMAIL)));
                user.setPassword(cursor.getString(cursor.getColumnIndex(COLUMN_USER_PASSWORD)));
                // Adding user record to list
                userList.add(user);
            } while (cursor.moveToNext());
        }
        cursor.close();
        db.close();
        // return user list
        return userList;
    }

    /**
     * This method to update user record
     * @param user
     */
    public void updateUser(User user) {
        SQLiteDatabase db = this.getWritableDatabase();
        ContentValues values = new ContentValues();
        values.put(COLUMN_USER_NAME, user.getName());
        values.put(COLUMN_USER_EMAIL, user.getEmail());
        values.put(COLUMN_USER_PASSWORD, user.getPassword());
        // updating row
        db.update(TABLE_USER, values, COLUMN_USER_ID + " = ?",
                new String[]{String.valueOf(user.getId())});
        db.close();
    }

    /**
     * This method is to delete user record
     * @param user
     */
    public void deleteUser(User user) {
        SQLiteDatabase db = this.getWritableDatabase();
        // delete user record by id
        db.delete(TABLE_USER, COLUMN_USER_ID + " = ?",
                new String[]{String.valueOf(user.getId())});
        db.close();
    }

    /**
     * This method to check user exist or not
     * @param email
     * @return true/false
     */
    public boolean checkUser(String email) {
        // array of columns to fetch
        String[] columns = {
                COLUMN_USER_ID
        };

        SQLiteDatabase db = this.getReadableDatabase();
        // selection criteria
        String selection = COLUMN_USER_EMAIL + " = ?";
        // selection argument
        String[] selectionArgs = {email};
        // query user table with condition
        /**
         * Here query function is used to fetch records from users table; this function works like we use sql query.
         * SQL query equivalent to this query function is
         * SELECT user_id FROM user WHERE user_email = 'jack@androidtutorialshub.com';
         */
        Cursor cursor = db.query(TABLE_USER, //Table to query
                columns,                    //columns to return
                selection,                  //columns for the WHERE clause
                selectionArgs,              //The values for the WHERE clause
                null,                       //group the rows
                null,                      //filter by row groups
                null);                      //The sort order
        int cursorCount = cursor.getCount();
        cursor.close();
        db.close();
        if (cursorCount > 0) {
            return true;
        }
        return false;
    }

    /**
     * This method to check user exist or not
     * @param email
     * @param password
     * @return true/false
     */
    public boolean checkUser(String email, String password) {
        // array of columns to fetch
        String[] columns = {
                COLUMN_USER_ID
        };
        SQLiteDatabase db = this.getReadableDatabase();
        // selection criteria
        String selection = COLUMN_USER_EMAIL + " = ?" + " AND " + COLUMN_USER_PASSWORD + " = ?";
        // selection arguments
        String[] selectionArgs = {email, password};
        // query user table with conditions
        /**
         * Here query function is used to fetch records from user table this function works like we use sql query.
         * SQL query equivalent to this query function is
         * SELECT user_id FROM user WHERE user_email = 'jack@androidtutorialshub.com' AND user_password = 'qwerty';
         */
        Cursor cursor = db.query(TABLE_USER, //Table to query
                columns,                    //columns to return
                selection,                  //columns for the WHERE clause
                selectionArgs,              //The values for the WHERE clause
                null,                       //group the rows
                null,                       //filter by row groups
                null);                      //The sort order
        int cursorCount = cursor.getCount();
        cursor.close();
        db.close();
        if (cursorCount > 0) {
            return true;
        }
        return false;
    }

    /**
     * This method is to create parkingLot record
     * @param parkingLot
     */
    public void addParkingLot(ParkingLot parkingLot) {
        SQLiteDatabase db = this.getWritableDatabase();
        ContentValues values = new ContentValues();
        values.put(COLUMN_PARKINGLOT_NAME, parkingLot.getName());
        values.put(COLUMN_PARKINGLOT_CITY, parkingLot.getCity());
        values.put(COLUMN_PARKINGLOT_SPOTS, parkingLot.getSpots());
        values.put(COLUMN_PARKINGLOT_LATITUDE, parkingLot.getLatitude());
        values.put(COLUMN_PARKINGLOT_LONGITUDE, parkingLot.getLongitude());
        // Inserting Row
        db.insert(TABLE_PARKINGLOT, null, values);
        db.close();
    }

    public ParkingLot getParkingLot(String parkingLotName){
        // array of columns to fetch
        String[] columns = {
                COLUMN_PARKINGLOT_ID,
                COLUMN_PARKINGLOT_NAME,
                COLUMN_PARKINGLOT_CITY,
                COLUMN_PARKINGLOT_SPOTS,
                COLUMN_PARKINGLOT_LATITUDE,
                COLUMN_PARKINGLOT_LONGITUDE
        };
        // selection criteria
        String selection = COLUMN_PARKINGLOT_NAME + " = ?";
        // selection argument
        String[] selectionArgs = {parkingLotName};
        String sortOrder =
                COLUMN_PARKINGLOT_NAME + " ASC";
        SQLiteDatabase db = this.getReadableDatabase();
        // query the user table
        /**
         * Here query function is used to fetch records from parkingLot table this function works like we use sql query.
         */
        Cursor cursor = db.query(TABLE_PARKINGLOT, //Table to query
                columns,    //columns to return
                selection,        //columns for the WHERE clause
                selectionArgs,        //The values for the WHERE clause
                null,       //group the rows
                null,       //filter by row groups
                sortOrder); //The sort order
        // Traversing through all rows and adding to list
        ParkingLot parkingLot = new ParkingLot();
        if (cursor.moveToFirst()) {
            do {
                parkingLot.setId(Integer.parseInt(cursor.getString(cursor.getColumnIndex(COLUMN_PARKINGLOT_ID))));
                parkingLot.setName(cursor.getString(cursor.getColumnIndex(COLUMN_PARKINGLOT_NAME)));
                parkingLot.setCity(cursor.getString(cursor.getColumnIndex(COLUMN_PARKINGLOT_CITY)));
                parkingLot.setSpots(Integer.parseInt(cursor.getString(cursor.getColumnIndex(COLUMN_PARKINGLOT_SPOTS))));
                parkingLot.setLatitude(Double.parseDouble(cursor.getString(cursor.getColumnIndex(COLUMN_PARKINGLOT_LATITUDE))));
                parkingLot.setLongitude(Double.parseDouble(cursor.getString(cursor.getColumnIndex(COLUMN_PARKINGLOT_LONGITUDE))));
            } while (cursor.moveToNext());
        }
        cursor.close();
        db.close();
        return parkingLot;
    }

    /**
     * This method is to fetch all parkingLot in a given city and return the list of parkingLot records
     * @return list
     */
    public ArrayList<ParkingLot> getAllParkingLot(String city) {
        // array of columns to fetch
        String[] columns = {
                COLUMN_PARKINGLOT_ID,
                COLUMN_PARKINGLOT_NAME,
                COLUMN_PARKINGLOT_CITY,
                COLUMN_PARKINGLOT_SPOTS,
                COLUMN_PARKINGLOT_LATITUDE,
                COLUMN_PARKINGLOT_LONGITUDE
        };
        // selection criteria
        String selection = COLUMN_PARKINGLOT_CITY + " = ?";
        // selection argument
        String[] selectionArgs = {city};
        // sorting orders
        String sortOrder =
                COLUMN_PARKINGLOT_NAME + " ASC";
        ArrayList<ParkingLot> parkingLotList = new ArrayList<ParkingLot>();
        SQLiteDatabase db = this.getReadableDatabase();
        // query the user table
        /**
         * Here query function is used to fetch records from parkingLot table this function works like we use sql query.
         */
        Cursor cursor = db.query(TABLE_PARKINGLOT, //Table to query
                columns,    //columns to return
                selection,        //columns for the WHERE clause
                selectionArgs,        //The values for the WHERE clause
                null,       //group the rows
                null,       //filter by row groups
                sortOrder); //The sort order
        // Traversing through all rows and adding to list
        if (cursor.moveToFirst()) {
            do {
                ParkingLot parkingLot = new ParkingLot();
                parkingLot.setId(Integer.parseInt(cursor.getString(cursor.getColumnIndex(COLUMN_PARKINGLOT_ID))));
                parkingLot.setName(cursor.getString(cursor.getColumnIndex(COLUMN_PARKINGLOT_NAME)));
                parkingLot.setCity(cursor.getString(cursor.getColumnIndex(COLUMN_PARKINGLOT_CITY)));
                parkingLot.setSpots(Integer.parseInt(cursor.getString(cursor.getColumnIndex(COLUMN_PARKINGLOT_SPOTS))));
                parkingLot.setLatitude(Double.parseDouble(cursor.getString(cursor.getColumnIndex(COLUMN_PARKINGLOT_LATITUDE))));
                parkingLot.setLongitude(Double.parseDouble(cursor.getString(cursor.getColumnIndex(COLUMN_PARKINGLOT_LONGITUDE))));
                // Adding user record to list
                parkingLotList.add(parkingLot);
            } while (cursor.moveToNext());
        }
        cursor.close();
        db.close();
        // return user list
        return parkingLotList;
    }

    /**
     * This method to update parkingLot record
     * @param parkingLot
     */
    public void updateParkingLot(ParkingLot parkingLot) {
        SQLiteDatabase db = this.getWritableDatabase();
        ContentValues values = new ContentValues();
        values.put(COLUMN_PARKINGLOT_NAME, parkingLot.getName());
        values.put(COLUMN_PARKINGLOT_CITY, parkingLot.getCity());
        values.put(COLUMN_PARKINGLOT_SPOTS, parkingLot.getSpots());
        values.put(COLUMN_PARKINGLOT_LATITUDE, parkingLot.getLatitude());
        values.put(COLUMN_PARKINGLOT_LONGITUDE, parkingLot.getLongitude());
        // updating row
        db.update(TABLE_PARKINGLOT, values, COLUMN_PARKINGLOT_ID + " = ?",
                new String[]{String.valueOf(parkingLot.getId())});
        db.close();
    }

    /**
     * This method is to delete parkingLot record
     * @param parkingLot
     */
    public void deleteParkingLot(ParkingLot parkingLot) {
        SQLiteDatabase db = this.getWritableDatabase();
        // delete user record by id
        db.delete(TABLE_PARKINGLOT, COLUMN_PARKINGLOT_ID + " = ?",
                new String[]{String.valueOf(parkingLot.getId())});
        db.close();
    }

    /**
     * This method is to create reservation record
     * @param reservation
     */
    public void addReservation(Reservation reservation) {
        SQLiteDatabase db = this.getWritableDatabase();
        ContentValues values = new ContentValues();
        values.put(COLUMN_RESERVATION_EMAIL, reservation.getEmail());
        values.put(COLUMN_RESERVATION_CITY, reservation.getCity());
        values.put(COLUMN_RESERVATION_PARKINGLOT, reservation.getParkingLot());
        values.put(COLUMN_RESERVATION_DATE, reservation.getDate());
        values.put(COLUMN_RESERVATION_TIME, reservation.getTime());
        Bitmap bitmap = reservation.getQr();
        ByteArrayOutputStream stream = new ByteArrayOutputStream();
        bitmap.compress(Bitmap.CompressFormat.PNG, 0, stream);
        byte[] image = stream.toByteArray();
        values.put(COLUMN_RESERVATION_QR, image);
        // Inserting Row
        db.insert(TABLE_RESERVATION, null, values);
        db.close();
    }

    /**
     * This method is to fetch all reservation and return the list of reservation records
     * @return list
     */
    public ArrayList<Reservation> getAllReservation(String email) {
        // array of columns to fetch
        String[] columns = {
                COLUMN_RESERVATION_ID,
                COLUMN_RESERVATION_EMAIL,
                COLUMN_RESERVATION_CITY,
                COLUMN_RESERVATION_PARKINGLOT,
                COLUMN_RESERVATION_DATE,
                COLUMN_RESERVATION_TIME,
                COLUMN_RESERVATION_QR
        };
        // selection criteria
        String selection = COLUMN_RESERVATION_EMAIL + " = ?";
        // selection argument
        String[] selectionArgs = {email};
        // sorting orders
        String sortOrder =
                COLUMN_RESERVATION_DATE + " ASC";
        ArrayList<Reservation> reservationList = new ArrayList<Reservation>();
        SQLiteDatabase db = this.getReadableDatabase();
        // query the user table
        /**
         * Here query function is used to fetch records from reservation table this function works like we use sql query.
         */
        Cursor cursor = db.query(TABLE_RESERVATION, //Table to query
                columns,    //columns to return
                selection,        //columns for the WHERE clause
                selectionArgs,        //The values for the WHERE clause
                null,       //group the rows
                null,       //filter by row groups
                sortOrder); //The sort order
        // Traversing through all rows and adding to list
        if (cursor.moveToFirst()) {
            do {
                Reservation reservation = new Reservation();
                reservation.setId(Integer.parseInt(cursor.getString(cursor.getColumnIndex(COLUMN_RESERVATION_ID))));
                reservation.setEmail(cursor.getString(cursor.getColumnIndex(COLUMN_RESERVATION_EMAIL)));
                reservation.setCity(cursor.getString(cursor.getColumnIndex(COLUMN_RESERVATION_CITY)));
                reservation.setParkingLot(cursor.getString(cursor.getColumnIndex(COLUMN_RESERVATION_PARKINGLOT)));
                reservation.setDate(cursor.getString(cursor.getColumnIndex(COLUMN_RESERVATION_DATE)));
                reservation.setTime(cursor.getString(cursor.getColumnIndex(COLUMN_RESERVATION_TIME)));
                Bitmap image = BitmapFactory.decodeByteArray(cursor.getBlob(cursor.getColumnIndex(COLUMN_RESERVATION_QR)),
                        0, cursor.getBlob(cursor.getColumnIndex(COLUMN_RESERVATION_QR)).length);
                reservation.setQr(image);
                // Adding user record to list
                reservationList.add(reservation);
            } while (cursor.moveToNext());
        }
        cursor.close();
        db.close();
        // return reservation list
        return reservationList;
    }

    /**
     * This method to update reservation record
     * @param reservation
     */
    public void updateReservation(Reservation reservation) {
        SQLiteDatabase db = this.getWritableDatabase();
        ContentValues values = new ContentValues();
        values.put(COLUMN_RESERVATION_EMAIL, reservation.getEmail());
        values.put(COLUMN_RESERVATION_CITY, reservation.getCity());
        values.put(COLUMN_RESERVATION_PARKINGLOT, reservation.getParkingLot());
        values.put(COLUMN_RESERVATION_DATE, reservation.getDate());
        values.put(COLUMN_RESERVATION_TIME, reservation.getTime());
        // updating row
        db.update(TABLE_RESERVATION, values, COLUMN_RESERVATION_ID + " = ?",
                new String[]{String.valueOf(reservation.getId())});
        db.close();
    }

    /**
     * This method is to delete reservation record
     * @param reservation
     */
    public void deleteReservation(Reservation reservation) {
        SQLiteDatabase db = this.getWritableDatabase();
        // delete user record by id
        db.delete(TABLE_RESERVATION, COLUMN_RESERVATION_ID + " = ?",
                new String[]{String.valueOf(reservation.getId())});
        db.close();
    }

    /**
     * This method returns the number of reservation records
     * @param city
     * @param parkingLot
     * @param date
     * @param time
     * @return
     */
    public int getNumberOfReservation(String city, String parkingLot, String date, String time) {
        // array of columns to fetch
        String[] columns = {
                COLUMN_RESERVATION_ID
        };
        SQLiteDatabase db = this.getReadableDatabase();
        // selection criteria
        String selection = COLUMN_RESERVATION_CITY + " = ?" + " AND " + COLUMN_RESERVATION_PARKINGLOT
                + " = ?" + " AND " + COLUMN_RESERVATION_DATE + " = ?" + " AND " + COLUMN_RESERVATION_TIME + " = ?";
        // selection argument
        String[] selectionArgs = {city, parkingLot, date, time};
        // query user table with condition
        /**
         * Here query function is used to fetch records from reservations table; this function works like we use sql query.
         */
        Cursor cursor = db.query(TABLE_RESERVATION, //Table to query
                columns,                    //columns to return
                selection,                  //columns for the WHERE clause
                selectionArgs,              //The values for the WHERE clause
                null,                       //group the rows
                null,                      //filter by row groups
                null);                      //The sort order
        int cursorCount = cursor.getCount();
        cursor.close();
        db.close();
        return cursorCount;
    }

    public int getNumberOfReservation(String email) {
        // array of columns to fetch
        String[] columns = {
                COLUMN_RESERVATION_ID
        };
        SQLiteDatabase db = this.getReadableDatabase();
        // selection criteria
        String selection = COLUMN_RESERVATION_EMAIL + " = ?";
        // selection argument
        String[] selectionArgs = {email};
        // query user table with condition
        /**
         * Here query function is used to fetch records from reservations table; this function works like we use sql query.
         */
        Cursor cursor = db.query(TABLE_RESERVATION, //Table to query
                columns,                    //columns to return
                selection,                  //columns for the WHERE clause
                selectionArgs,              //The values for the WHERE clause
                null,                       //group the rows
                null,                      //filter by row groups
                null);                      //The sort order
        int cursorCount = cursor.getCount();
        cursor.close();
        db.close();
        return cursorCount;
    }

}

