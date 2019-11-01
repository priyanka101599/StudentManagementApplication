package com.example.studentmanagement;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.ProgressBar;
import android.widget.RadioButton;
import android.widget.TextView;

import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;

public class FacultyViewStudentAttendanceActivity extends AppCompatActivity {

    private RadioButton present;
    private RadioButton absent;
    private TextView pview;
    private ProgressBar pbar;
    private TextView aview;
    private ProgressBar abar;
    StudentAttendance sa;
    DatabaseReference ref;
    String course="";
    String id="";
    String pdata="";
    String adata="";


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        getSupportActionBar().hide();
        setContentView(R.layout.activity_faculty_view_student_attendance);

        ArrayList<String> al;
        al=getIntent().getStringArrayListExtra("temp");
        assert al != null;
        course=al.get(0);
        id=al.get(1);

        sa=new StudentAttendance();
        ref= FirebaseDatabase.getInstance().getReference().child("StudentAttendance").child(course);

        present=(RadioButton) findViewById(R.id.presentbutton);
        absent= (RadioButton) findViewById(R.id.absentbutton);
        pview= (TextView) findViewById(R.id.presentpercent);
        aview= (TextView) findViewById(R.id.absentpercent);
        pbar= (ProgressBar) findViewById(R.id.pvprogressbar);
        abar= (ProgressBar) findViewById(R.id.avprogressbar);

        if(ref.child(id)!=null){
            ref.child(id).addValueEventListener(new ValueEventListener() {
                @Override
                public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                    StudentAttendance sa=dataSnapshot.getValue(StudentAttendance.class);
                    if(dataSnapshot.exists()){
                        pdata=sa.getPresent();
                        adata=sa.getAbsent();
                        String parr[]=pdata.split(" ");
                        String aarr[]=adata.split(" ");
                        final int pvalue=Integer.parseInt(parr[0]);
                        final int avalue=Integer.parseInt(aarr[0]);
                        present.setOnClickListener(new View.OnClickListener() {
                            @Override
                            public void onClick(View view) {
                                pview.setText(pvalue+" %");
                                pbar.setProgress(pvalue);
                            }
                        });
                        absent.setOnClickListener(new View.OnClickListener() {
                            @Override
                            public void onClick(View view) {
                                aview.setText(avalue+" %");
                                abar.setProgress(avalue);
                            }
                        });
                    }
                }

                @Override
                public void onCancelled(@NonNull DatabaseError databaseError) {

                }
            });
        }

    }
}
