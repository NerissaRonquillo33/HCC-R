package com.example.hcc;

import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

import java.util.List;

public class DBHelper {

    DHelper dHelper;
    List<Course_Item> courseItemList;
    public DBHelper(Context context, List<Course_Item> courseItemList)
    {
        dHelper = new DHelper(context);
        this.courseItemList = courseItemList;
    }

    public List<Course_Item> getData()
    {
        SQLiteDatabase db = dHelper.getWritableDatabase();
        String[] columns = {DHelper.UID,DHelper.CODE,DHelper.DESCRIPTION,DHelper.UNIT,DHelper.SEMESTER,DHelper.YEAR};
        Cursor cursor =db.query(DHelper.TABLE_NAME,columns,null,null,null,null,null);
        while (cursor.moveToNext())
        {
            int cid =cursor.getInt(cursor.getColumnIndex(DHelper.UID));
            String code =cursor.getString(cursor.getColumnIndex(DHelper.CODE));
            String  description =cursor.getString(cursor.getColumnIndex(DHelper.DESCRIPTION));
            String  unit =cursor.getString(cursor.getColumnIndex(DHelper.UNIT));
            String  semester =cursor.getString(cursor.getColumnIndex(DHelper.SEMESTER));
            String  year =cursor.getString(cursor.getColumnIndex(DHelper.YEAR));
            courseItemList.add(new Course_Item(cid,code,description,Integer.parseInt(unit),Integer.parseInt(semester),Integer.parseInt(year),R.drawable.book_edit));
        }
        return courseItemList;
    }

    static class DHelper extends SQLiteOpenHelper {
        private static final String DATABASE_NAME = "HCC";
        private static final String TABLE_NAME = "courses";
        private static final int DATABASE_Version = 1;
        private static final String UID="_id";
        private static final String CODE = "code";
        private static final String DESCRIPTION= "description";
        private static final String UNIT= "unit";
        private static final String SEMESTER= "semester";
        private static final String YEAR= "year";
        private static final String CREATE_TABLE = "CREATE TABLE "+TABLE_NAME+
                " ("+UID+" INTEGER PRIMARY KEY AUTOINCREMENT, "+CODE+" VARCHAR(255) ,"+ DESCRIPTION+" VARCHAR(225),"+UNIT+" INTEGER,"+SEMESTER+" INTEGER,"+YEAR+" INTEGER);";
        private static final String INSERT_FIRST = "INSERT INTO courses(code,description,unit,semester,year) VALUES ('HIS-GEC','Reading in Philippine History',3,1,1),('CON-GEC','The Contemporary World',3,1,1),('COM-GEC','Purposive Communication',3,1,1),('PS-MM1','Professional Salesmanship',3,1,1),('PE1','Fundamentals of Physical Fitness',2,1,1),('NSTP1','National Service Training Program 1',3,1,1),('US-GEC','Undestanding The Self',3,2,1),('ITE-GEE','Living in IT Era',3,2,1),('REL-GEE','Religious Experiences and Spirituality',3,2,1),('MECO-BAC','Basic Microeconomics',3,2,1),('PE2','Rhythmic Activities',2,2,1),('NSTP2','National Service Training Program 2',3,2,1),('STS-GEC','Science Technology and Society',3,1,2),('ETH-GEE','Ethics',3,1,2),('RIZ-GEC','The life and Works of Rizal',3,1,2),('BRES-BAC','Business Research',3,1,2),('BLAW-BAC','Business Law',3,1,2),('COBE-MME','Consumer Behaviour',3,1,2),('PE3','Individual or Dual Sports',2,1,2),('MAT-GEC','Mathematics in the Modern World',3,2,2),('ART-GEC','Art Appreciation',3,2,2),('RVA-GEE','Reading Visual Art',3,2,2),('BTAX-BAC','Taxiation(Income Taxation)',3,2,2),('MM-MM3','Marketinf Management',3,2,2),('MIS-BAE','Management Information System',3,2,2),('PE4','Team Sports/Games',2,2,2),('OPS-BME','Operation Management',3,1,3),('GGOV-BAC','Good Governacnce and Social Responsibility',3,1,3),('HRM-BAC','Human Resource Management',3,1,3),('INTRA-BAC','International Business Trade',3,1,3),('DM-MM4','Distribution Management',3,1,3),('AD-MMS','Advertising',3,1,3),('GRAPH-BAE','Graphical Designing',3,1,3),('STRAT-BME','Strategic Management',3,2,3),('MR-MM2','Marketing Research',3,2,3),('PM-MM2','Product Management',3,2,3),('RM-MM7','Retail Management',3,2,3),('SALEM-MME','Sales Management',3,2,3),('PS-MM8','Pricing Strategy',3,2,3),('IMARK-MME','International Marketing',3,1,4),('FS-BAC','Feasibility Study',3,1,4),('PFIN-MME','Personal Finance',3,1,4),('FRAN-MME','Franchising',3,1,4),('ECOM-MME','E-Commerce and Interner Marketing',3,2,4),('PRAC','Work Integrated Learning (600hrs)',6,2,4)";
        private static final String DROP_TABLE ="DROP TABLE IF EXISTS "+TABLE_NAME;
        private Context context;
        public DHelper(Context context) {
            super(context, DATABASE_NAME, null, DATABASE_Version);
            this.context=context;
        }

        @Override
        public void onCreate(SQLiteDatabase sqLiteDatabase) {
            try {
                sqLiteDatabase.execSQL(CREATE_TABLE);
                sqLiteDatabase.execSQL(INSERT_FIRST);
            } catch (Exception e) {
                // nothing todo
            }
        }

        @Override
        public void onUpgrade(SQLiteDatabase sqLiteDatabase, int i, int i1) {
            try {
                sqLiteDatabase.execSQL(DROP_TABLE);
                onCreate(sqLiteDatabase);
            }catch (Exception e) {
                // nothing todo
            }
        }
    }
}
