package com.example.toands.drink;

import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

public class ContainValue {

    public String _cmd = "+/n-----------------------------------------/n+";

    public String TAG = "main";
    public String ERROR = "error";
    public String TEXT_VIEW = "txtv";
    public String BUTTON = "btn";
    public String EDIT_TEXT = "edt";

    public String FIREBASE_TAG = "firebase-tag";
    public String TITLE_NAME = "This is title for";
    public String TITLE_MESS = "The Report";

    // #Firebase configuration value
    public String DATE = "date";
    public String TIME = "time";
    public String CONSTANT_BLISTER = "constant-blister";
    public String CURRENT_DRUG = "current-drugs";
    public String THE_NUMBER_BLISTER = "the-number-blister";

    public String SAY = "client-say";

    public String Ftrue = "true";
    public String Ffalse = "false";

    public String FULL_DE = "Dear sir, This is the time for today";
    public String SHORT_DE = "Timt";

    public long id = 0;
    public FirebaseDatabase database = FirebaseDatabase.getInstance();

    public DatabaseReference _drink3e7a9 = database.getReference();
    public DatabaseReference _Messages = database.getReference("Messages");
    public DatabaseReference _Rule = database.getReference("Rule");
    public DatabaseReference _Version = database.getReference("Version");
    public DatabaseReference _check = database.getReference("check");
    public DatabaseReference _uncheck = database.getReference("uncheck");
    public DatabaseReference _Persons = database.getReference("Persons");
    public DatabaseReference _Push = database.getReference("Push");
    public DatabaseReference _DrugAbuse = database.getReference("Drugs abuse");
}
