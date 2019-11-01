package com.example.studentmanagement;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.annotation.SuppressLint;
import android.content.Intent;
import android.os.Bundle;
import android.provider.ContactsContract;
import android.view.View;
import android.widget.Button;
import android.widget.ProgressBar;
import android.widget.RadioButton;
import android.widget.SeekBar;
import android.widget.TextView;
import android.widget.Toast;

import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;

public class FacultyAddedStudentAttendanceActivity extends AppCompatActivity {

    private RadioButton present;
    private RadioButton absent;
    private Button submit;
    private TextView pview;
    private SeekBar pseekbar;
    private ProgressBar pbar;
    private TextView aview;
    private SeekBar aseekbar;
    private ProgressBar abar;
    StudentAttendance sa;
    DatabaseReference ref;
    String course="";
    String id="";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        getSupportActionBar().hide();
        setContentView(R.layout.activity_faculty_added_student_attendance);

        ArrayList<String> al;
        al=getIntent().getStringArrayListExtra("temp");
        assert al != null;
        course=al.get(0);
        id=al.get(1);

        sa=new StudentAttendance();
        ref=FirebaseDatabase.getInstance().getReference().child("StudentAttendance");

        present=(RadioButton) findViewById(R.id.presentbutton);
        absent= (RadioButton) findViewById(R.id.absentbutton);
        submit= (Button) findViewById(R.id.submitbtn);
        pview= (TextView) findViewById(R.id.presentpercent);
        pbar= (ProgressBar) findViewById(R.id.presentprogressbar);
        pseekbar= (SeekBar) findViewById(R.id.presentseekbar);
        aview= (TextView) findViewById(R.id.absentpercent);
        abar= (ProgressBar) findViewById(R.id.absentprogressbar);
        aseekbar= (SeekBar) findViewById(R.id.absentseekbar);

        present.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                pseekbar.setOnSeekBarChangeListener(new SeekBar.OnSeekBarChangeListener() {
                    @Override
                    public void onProgressChanged(SeekBar seekBar, int i, boolean b) {
                        pbar.setProgress(i);
                        pview.setText(i+" %");
                        absent.setClickable(false);
                        aseekbar.setEnabled(false);
                        aseekbar.setProgress(100-i);
                        abar.setProgress(100-i);
                        aview.setText((100-i)+" %");
                    }

                    @Override
                    public void onStartTrackingTouch(SeekBar seekBar) {

                    }

                    @Override
                    public void onStopTrackingTouch(SeekBar seekBar) {

                    }
                });
            }
        });
        absent.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                aseekbar.setOnSeekBarChangeListener(new SeekBar.OnSeekBarChangeListener() {
                    @Override
                    public void onProgressChanged(SeekBar seekBar, int i, boolean b) {
                        abar.setProgress(i);
                        aview.setText(i+" %");
                        present.setClickable(false);
                        pseekbar.setEnabled(false);
                        pseekbar.setProgress(100-i);
                        pbar.setProgress(100-i);
                        pview.setText((100-i)+" %");
                    }

                    @Override
                    public void onStartTrackingTouch(SeekBar seekBar) {

                    }

                    @Override
                    public void onStopTrackingTouch(SeekBar seekBar) {

                    }
                });
            }
        });
        submit.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                String parr[] = pview.getText().toString().split(" ");
                String aarr[] = aview.getText().toString().split(" ");
                if (Integer.parseInt(parr[0]) + Integer.parseInt(aarr[0]) == 100) {
                    sa = new StudentAttendance();
                    ref = FirebaseDatabase.getInstance().getReference().child("StudentAttendance");
                    sa.setPresent(pview.getText().toString());
                    sa.setAbsent(aview.getText().toString());
                    ref.child(course).child(id).setValue(sa);
                    Toast.makeText(FacultyAddedStudentAttendanceActivity.this, "submitted..!", Toast.LENGTH_SHORT).show();
                }
                else
                    Toast.makeText(FacultyAddedStudentAttendanceActivity.this, "Total attendance should sum to 100!!!", Toast.LENGTH_SHORT).show();

            }
        });




    }
}
