package com.scriptedpapers.androidcortanaanimationdemo;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.GridView;

import com.scriptedpapers.androidcortanaanimation.CortanaView;
import com.scriptedpapers.androidcortanaanimation.utils.CortanaType;

import butterknife.Bind;
import butterknife.ButterKnife;

public class MainActivity extends AppCompatActivity {

    @Bind(R.id.cortanaView)
    CortanaView cortanaView;

    @Bind(R.id.buttonGridView)
    GridView buttonGridView;

    static final String[] ANIM_NAME = new String[] {
            "Listen", "Speak",
            "Remind", "Optimistic",
            "Remind 2", "Cool",
            "Think", "Bouncy"};

    static final int[] ANIM_TYPE = new int[] {
            CortanaType.LISTEN_TYPE, CortanaType.SPEAK_TYPE,
            CortanaType.REMIND_TYPE, CortanaType.OPTIMISTIC_TYPE,
            CortanaType.REMIND_2_TYPE, CortanaType.COOL_TYPE,
            CortanaType.THINK_TYPE, CortanaType.BOUNCY_TYPE};

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        ButterKnife.bind(this);

        ArrayAdapter<String> adapter = new ArrayAdapter<String>(this,
                R.layout.lyt_button, ANIM_NAME);

        buttonGridView.setAdapter(adapter);

        buttonGridView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            public void onItemClick(AdapterView<?> parent, View v,
                                    int position, long id) {
                cortanaView.setType(ANIM_TYPE[position]);
                cortanaView.startAnimation();
            }
        });
    }
}
