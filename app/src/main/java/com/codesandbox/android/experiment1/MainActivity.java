package com.codesandbox.android.experiment1;

import android.os.Bundle;
import android.support.design.widget.Snackbar;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.View;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.Toast;

import com.codesandbox.android.experiment1.base.Experiment;
import com.codesandbox.android.experiment1.experiments.BounceExperiment;
import com.codesandbox.android.experiment1.experiments.CirclingMadnessExperiment;
import com.getbase.floatingactionbutton.FloatingActionButton;
import com.getbase.floatingactionbutton.FloatingActionsMenu;

public class MainActivity extends AppCompatActivity {

    private Experiment experiment;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        final FloatingActionsMenu menuMultipleActions = (FloatingActionsMenu) findViewById(R.id.multiple_actions);

        FloatingActionButton fab = new FloatingActionButton(getBaseContext());
        fab.setIcon(R.drawable.ic_dialog_circle_of_madness);
        fab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (experiment == null) {
                    experiment = new CirclingMadnessExperiment();
                    Snackbar.make(view, "Starting experiment: " + experiment.getFriendlyName(), Snackbar.LENGTH_LONG)
                            .setAction("Action", null).show();
                    experiment.startExperimentFor(MainActivity.this);
                } else {
                    Toast.makeText(MainActivity.this, "I'm afraid I can't do that!", Toast.LENGTH_SHORT).show();
                }
            }
        });
        fab.setSize(FloatingActionButton.SIZE_MINI);

        FloatingActionButton fab2 = new FloatingActionButton(getBaseContext());
        fab2.setIcon(R.drawable.ic_dialog_bounce);
        fab2.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                experiment = new BounceExperiment();
                experiment.startExperimentFor(MainActivity.this);
            }
        });
        fab2.setSize(FloatingActionButton.SIZE_MINI);

        menuMultipleActions.addButton(fab);
        menuMultipleActions.addButton(fab2);

    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_main, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();

        //noinspection SimplifiableIfStatement
        if (id == R.id.action_settings) {
            Toast.makeText(this, "All your settings are belong to us!", Toast.LENGTH_SHORT).show();
            return true;
        }

        return super.onOptionsItemSelected(item);
    }
}
