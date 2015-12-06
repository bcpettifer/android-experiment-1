package com.codesandbox.android.experiment1;

import android.app.Fragment;
import android.app.FragmentManager;
import android.os.Bundle;
import android.support.design.widget.Snackbar;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Toast;

import com.codesandbox.android.experiment1.base.ExperimentBaseFragment;
import com.codesandbox.android.experiment1.experiments.BounceExperiment;
import com.codesandbox.android.experiment1.experiments.CirclingMadnessExperiment;
import com.codesandbox.android.experiment1.experiments.MystifyExperiment;
import com.codesandbox.android.experiment1.experiments.SpiralVectorExperiment;
import com.getbase.floatingactionbutton.FloatingActionButton;
import com.getbase.floatingactionbutton.FloatingActionsMenu;

public class MainActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        final ViewGroup parent = (ViewGroup) this.findViewById(android.R.id.content);

        final FloatingActionsMenu menuMultipleActions = (FloatingActionsMenu) findViewById(R.id.multiple_actions);
        addExperimentButtons(menuMultipleActions, parent,
                new CirclingMadnessExperiment(),
                new BounceExperiment(),
                new SpiralVectorExperiment(),
                new MystifyExperiment());
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

    private void addExperimentButtons(final FloatingActionsMenu menu, final ViewGroup parent, final ExperimentBaseFragment... experiments) {
        for (ExperimentBaseFragment experiment : experiments) {
            addExperimentButton(menu, parent, experiment);
        }
    }

    private void addExperimentButton(final FloatingActionsMenu menu, final ViewGroup parent, final ExperimentBaseFragment experiment) {
        FloatingActionButton fab = new FloatingActionButton(getBaseContext());
        fab.setIcon(experiment.getIcon());
        fab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                handleExperimentSelection(experiment, parent);
                menu.collapse();
            }
        });
        fab.setSize(FloatingActionButton.SIZE_MINI);
        menu.addButton(fab);
    }

    private void handleExperimentSelection(final ExperimentBaseFragment experiment, final ViewGroup parent) {
        FragmentManager fragmentManager = getFragmentManager();
        Fragment f = fragmentManager.findFragmentByTag(ExperimentBaseFragment.TAG);
        if (f == null) {
            getFragmentManager().beginTransaction().add(R.id.fragment_container, experiment, ExperimentBaseFragment.TAG).commit();
            Snackbar.make(parent, "Starting experiment: " + experiment.getFriendlyName(), Snackbar.LENGTH_LONG)
                    .setAction("Action", null).show();
        } else {
            Snackbar.make(parent, "Killing running experiment", Snackbar.LENGTH_LONG)
                    .setAction("Action", null).show();
            getFragmentManager().
                    beginTransaction().
                    remove(f).
                    commit();
            parent.postDelayed(new Runnable() {
               @Override
               public void run() {
                   handleExperimentSelection(experiment, parent);
               }
            }, 1500);
        }
    }
}
