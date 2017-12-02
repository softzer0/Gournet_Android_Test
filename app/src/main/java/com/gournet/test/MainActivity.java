package com.gournet.test;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import com.gournet.test.dummy.DummyContent;
import android.widget.TextView;

import com.gournet.test.dummy.DummyContent;

public class MainActivity extends AppCompatActivity implements ItemFragment.OnListFragmentInteractionListener {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        Bundle extras = getIntent().getExtras();

/*       final TextView textView = findViewById(R.id.textView);
        User user = (User) extras.getSerializable("user");
        textView.setText(user.first_name);*/
    }

    @Override
    public void onListFragmentInteraction(DummyContent.DummyItem item) {

    }
}

