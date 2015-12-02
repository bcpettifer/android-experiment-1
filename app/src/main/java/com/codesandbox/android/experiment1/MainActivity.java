package com.codesandbox.android.experiment1;

import android.app.Fragment;
import android.app.FragmentManager;
import android.app.FragmentTransaction;
import android.os.Bundle;
import android.support.design.widget.Snackbar;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Toast;

import com.codesandbox.android.experiment1.base.ExperimentBase;
import com.codesandbox.android.experiment1.experiments.BounceExperiment;
import com.codesandbox.android.experiment1.experiments.CirclingMadnessExperiment;
import com.codesandbox.android.experiment1.experiments.SpiralVectorExperiment;
import com.getbase.floatingactionbutton.FloatingActionButton;
import com.getbase.floatingactionbutton.FloatingActionsMenu;

public class MainActivity extends AppCompatActivity {

    private ExperimentBase mExperiment;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        final ViewGroup parent = (ViewGroup) this.findViewById(android.R.id.content);

        final FloatingActionsMenu menuMultipleActions = (FloatingActionsMenu) findViewById(R.id.multiple_actions);

        FloatingActionButton fab = new FloatingActionButton(getBaseContext());
        fab.setIcon(R.drawable.ic_dialog_circle_of_madness);
        fab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                handleExperimentSelection(new CirclingMadnessExperiment(), parent);
            }
        });
        fab.setSize(FloatingActionButton.SIZE_MINI);

        FloatingActionButton fab2 = new FloatingActionButton(getBaseContext());
        fab2.setIcon(R.drawable.ic_dialog_bounce);
        fab2.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                handleExperimentSelection(new BounceExperiment(), parent);
            }
        });
        fab2.setSize(FloatingActionButton.SIZE_MINI);

        FloatingActionButton fab3 = new FloatingActionButton(getBaseContext());
        fab3.setIcon(R.drawable.ic_dialog_spiral);
        fab3.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                handleExperimentSelection(new SpiralVectorExperiment(), parent);
            }
        });
        fab3.setSize(FloatingActionButton.SIZE_MINI);

        menuMultipleActions.addButton(fab);
        menuMultipleActions.addButton(fab2);
        menuMultipleActions.addButton(fab3);
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

    private void handleExperimentSelection(final ExperimentBase experiment, final ViewGroup parent) {
        if (mExperiment == null) {
            mExperiment = experiment;
            Snackbar.make(parent, "Starting experiment: " + mExperiment.getFriendlyName(), Snackbar.LENGTH_LONG)
                    .setAction("Action", null).show();
            mExperiment.startExperimentFor(MainActivity.this, parent);
        } else {
            Snackbar.make(parent, "Killing experiment: " + mExperiment.getFriendlyName(), Snackbar.LENGTH_LONG)
                    .setAction("Action", null).show();
            mExperiment.killExperimentFor(parent);
            mExperiment = null;
            parent.postDelayed(new Runnable() {
                @Override
                public void run() {
                    handleExperimentSelection(experiment, parent);
                }
            },
            1500);

        }
    }

    private void handleExperimentSelection(SpiralVectorExperiment experiment, ViewGroup parent) {
        FragmentManager fragmentManager = getFragmentManager();
        Fragment f = fragmentManager.findFragmentByTag(SpiralVectorExperiment.TAG);
        if (f == null) {
            getFragmentManager().beginTransaction().add(R.id.fragment_container, experiment, SpiralVectorExperiment.TAG).commit();
            Snackbar.make(parent, "Starting experiment: " + experiment.getFriendlyName(), Snackbar.LENGTH_LONG)
                    .setAction("Action", null).show();
        } else {
            Snackbar.make(parent, "Killing experiment: " + experiment.getFriendlyName(), Snackbar.LENGTH_LONG)
                    .setAction("Action", null).show();
            getFragmentManager().
                    beginTransaction().
                    remove(f).
                    commit();
        }

    }
}
