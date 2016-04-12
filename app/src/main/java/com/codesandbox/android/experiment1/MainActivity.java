package com.codesandbox.android.experiment1;

import android.app.Fragment;
import android.app.FragmentManager;
import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
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

import yuku.ambilwarna.AmbilWarnaDialog;

public class MainActivity extends AppCompatActivity {

    FloatingActionsMenu mFloatingActionsMenu;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        final ViewGroup parent = (ViewGroup) this.findViewById(android.R.id.content);

        mFloatingActionsMenu = (FloatingActionsMenu) findViewById(R.id.multiple_actions);
        addExperimentButtons(mFloatingActionsMenu, parent,
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
        if (id == R.id.action_stop) {
            stopExperiment();
            return true;
        }
        if (id == R.id.action_bg_colour) {
            final View container = findViewById(R.id.fragment_container);
            int initialColour = Color.WHITE;
            if (container.getBackground() instanceof ColorDrawable) {
                initialColour = ((ColorDrawable)container.getBackground()).getColor();
            }

            AmbilWarnaDialog dialog = new AmbilWarnaDialog(
                    this,
                    initialColour,
                    new AmbilWarnaDialog.OnAmbilWarnaListener() {
                        @Override
                        public void onOk(AmbilWarnaDialog dialog, int color) {
                            // color is the color selected by the user.
                            container.setBackgroundColor(color);
                        }

                        @Override
                        public void onCancel(AmbilWarnaDialog dialog) {
                            // cancel was selected by the user
                        }
                    });

            dialog.show();
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

    private void handleExperimentSelection(final ExperimentBaseFragment experiment, final View view) {
        FragmentManager fragmentManager = getFragmentManager();
        Fragment f = fragmentManager.findFragmentByTag(ExperimentBaseFragment.TAG);
        if (f == null) {
            fragmentManager.beginTransaction().add(R.id.fragment_container, experiment, ExperimentBaseFragment.TAG).commit();
            Snackbar.make(mFloatingActionsMenu, "Starting experiment: " + experiment.getFriendlyName(), Snackbar.LENGTH_LONG)
                    .setAction("Action", null).show();
        } else {
            stopExperiment(fragmentManager, f, view);
            view.postDelayed(new Runnable() {
               @Override
               public void run() {
                   handleExperimentSelection(experiment, view);
               }
            }, 1500);
        }
    }

    private void stopExperiment() {
        FragmentManager fragmentManager = getFragmentManager();
        Fragment f = fragmentManager.findFragmentByTag(ExperimentBaseFragment.TAG);
        if (f != null) {
            stopExperiment(fragmentManager, f, findViewById(R.id.fragment_container));
        }
    }

    private void stopExperiment(final FragmentManager fragmentManager, final Fragment fragment, final View view) {
        Snackbar.make(mFloatingActionsMenu, "Killing running experiment", Snackbar.LENGTH_LONG)
                .setAction("Action", null).show();
        fragmentManager.
                beginTransaction().
                remove(fragment).
                commit();
    }
}
