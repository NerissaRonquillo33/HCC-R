package com.example.hcc;

import android.content.DialogInterface;
import android.content.Intent;
import android.net.Uri;
import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;
import android.os.Bundle;
import androidx.cardview.widget.CardView;
import android.view.View;
import android.widget.Button;

import com.example.hcc.abstracts.Database;
import com.example.hcc.helper.MD5;
import com.example.hcc.models.Bills;
import com.example.hcc.models.Grades;
import com.example.hcc.models.Schedules;
import com.example.hcc.models.Students;

import java.io.BufferedReader;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.InputStreamReader;

public class Developer extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.developer);
        Button logout = findViewById(R.id.logout);
        CardView students = findViewById(R.id.students);
        CardView schedules = findViewById(R.id.schedules);
        CardView grades = findViewById(R.id.grades);
        CardView bills = findViewById(R.id.bills);
        /* bills */
        bills.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                importCSV(203);
            }
        });
        /* students */
        students.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                importCSV(200);
            }
        });
        /* schedules */
        schedules.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                importCSV(201);
            }
        });
        /* grades */
        grades.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                importCSV(202);
            }
        });
        /* Logout */
        logout.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                AlertDialog.Builder builder = new AlertDialog.Builder(Developer.this);

                builder.setTitle("Confirm");
                builder.setMessage("Are you sure you want to logout?");

                builder.setPositiveButton("YES", new DialogInterface.OnClickListener() {

                    public void onClick(DialogInterface dialog, int which) {
                        // Do nothing but close the dialog
                        Intent login = new Intent(Developer.this, Login.class);
                        startActivity(login);
                        dialog.dismiss();
                    }
                });

                builder.setNegativeButton("NO", new DialogInterface.OnClickListener() {

                    @Override
                    public void onClick(DialogInterface dialog, int which) {

                        // Do nothing
                        dialog.dismiss();
                    }
                });

                AlertDialog alert = builder.create();
                alert.show();
            }
        });
    }

    public void importCSV(int requestCode) {
        Intent i = new Intent();
        i.addCategory(Intent.CATEGORY_OPENABLE);
        i.setType("*/*");
        i.setAction(Intent.ACTION_GET_CONTENT);
        startActivityForResult(Intent.createChooser(i, "Select CSV"), requestCode);
    }

    public void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (resultCode == RESULT_OK) {
            if (requestCode == 200) {
                Database database = Database.getInstance(Developer.this);
                database.studentsDao().deleteAll();
                Uri uri = data.getData();
                if (null != uri) {
                    try (BufferedReader reader = new BufferedReader(new InputStreamReader(getContentResolver().openInputStream(uri)))) {
                        while (reader.ready()) {
                            String line = reader.readLine();
                            String[] studentsInfo = line.split(",");
                            if (studentsInfo.length == 8) {
                                database.studentsDao().insert(new Students(studentsInfo[0], new MD5().encrypt(studentsInfo[1]),studentsInfo[2],studentsInfo[3],studentsInfo[4],studentsInfo[5],studentsInfo[6],studentsInfo[7],studentsInfo[8],new byte[0],studentsInfo[9],studentsInfo[10]));
                            }
                        }
                    }catch (FileNotFoundException e) {
                        // TODO Auto-generated catch block
                        e.printStackTrace();
                    } catch (IOException e) {
                        // TODO Auto-generated catch block
                        e.printStackTrace();
                    }
                }
            }
            else if (requestCode == 201) {
                Database database = Database.getInstance(Developer.this);
                database.schedulesDao().deleteAll();
                Uri uri = data.getData();
                if (null != uri) {
                    try (BufferedReader reader = new BufferedReader(new InputStreamReader(getContentResolver().openInputStream(uri)))) {
                        while (reader.ready()) {
                            String line = reader.readLine();
                            String[] schedules = line.split(",");
                            if (schedules.length == 7) {
                                database.schedulesDao().insert(new Schedules(schedules[0],schedules[1],schedules[2],schedules[3],schedules[4],schedules[5],schedules[6]));
                            }
                        }
                    }catch (FileNotFoundException e) {
                        // TODO Auto-generated catch block
                        e.printStackTrace();
                    } catch (IOException e) {
                        // TODO Auto-generated catch block
                        e.printStackTrace();
                    }
                }
            }
            else if (requestCode == 202) {
                Database database = Database.getInstance(Developer.this);
                database.gradesDao().deleteAll();
                Uri uri = data.getData();
                if (null != uri) {
                    try (BufferedReader reader = new BufferedReader(new InputStreamReader(getContentResolver().openInputStream(uri)))) {
                        while (reader.ready()) {
                            String line = reader.readLine();
                            String[] grades = line.split(",");
                            if (grades.length == 9) {
                                database.gradesDao().insert(new Grades(grades[0],grades[1],grades[2],grades[3],grades[4],grades[5],grades[6],grades[7],grades[8],grades[9],grades[10]));
                            }
                        }
                    }catch (FileNotFoundException e) {
                        // TODO Auto-generated catch block
                        e.printStackTrace();
                    } catch (IOException e) {
                        // TODO Auto-generated catch block
                        e.printStackTrace();
                    }
                }
            }
            else if (requestCode == 203) {
                Database database = Database.getInstance(Developer.this);
                database.billsDao().deleteAll();
                Uri uri = data.getData();
                if (null != uri) {
                    try (BufferedReader reader = new BufferedReader(new InputStreamReader(getContentResolver().openInputStream(uri)))) {
                        while (reader.ready()) {
                            String line = reader.readLine();
                            String[] bill = line.split(",");
                            if (bill.length == 15) {
                                database.billsDao().insert(new Bills(bill[0],bill[1],bill[2],bill[3],bill[4],bill[5],bill[6],bill[7],bill[8],bill[9],bill[10],bill[11],bill[12],bill[13],bill[14],Integer.parseInt(bill[15]),Integer.parseInt(bill[16])));
                            }
                        }
                    }catch (FileNotFoundException e) {
                        // TODO Auto-generated catch block
                        e.printStackTrace();
                    } catch (IOException e) {
                        // TODO Auto-generated catch block
                        e.printStackTrace();
                    }
                }
            }
        }
    }
}