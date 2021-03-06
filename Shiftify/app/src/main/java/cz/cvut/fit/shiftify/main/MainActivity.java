package cz.cvut.fit.shiftify.main;

import android.content.ContentResolver;
import android.content.Intent;
import android.content.res.Configuration;
import android.net.Uri;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.design.widget.NavigationView;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.view.GravityCompat;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.ActionBarDrawerToggle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.MenuItem;

import org.joda.time.LocalDate;


import cz.cvut.fit.shiftify.PersonsListFragment;
import cz.cvut.fit.shiftify.R;
import cz.cvut.fit.shiftify.ShiftListFragment;
import cz.cvut.fit.shiftify.DatePickDialog;


/**
 * Main activity
 * In this class is implemented navigation drawer logic
 * You can switch fragment by navigation drawer
 */
public class MainActivity extends AppCompatActivity implements DatePickDialog.DatePickDialogCallback {


    private static final String SELECTED_ITEM_ID = "selected_item";
    private DrawerLayout mDrawer;
    private Toolbar mToolbar;
    private NavigationView mNavViewDrawer;
    private ActionBarDrawerToggle mDrawerToggle;
    private int selectedItemId;

    private static final int DEFAULT_ITEM = R.id.nav_item_shifts_list;
    public static final int CREATE_SCHEDULE_TYPE_REQUEST = 1;
    public static final int EDIT_SCHEDULE_TYPE_REQUEST = 2;
    private static final int PERSON_LIST_ITEM = R.id.nav_item_persons_list;
    private static final int IMPORT_REQUESTED_ITEM = R.id.nav_item_export_import;
    private static final String PERSON_LIST_FRAGMENT_TAG = "person_list_fragment_tag";
    private static final String SHIFT_LIST_FRAGMENT_TAG = "shift_list_fragment_tag";
    private static final String ABOUT_FRAGMENT_TAG = "about_fragment_tag";
    private static final String SCHEDULE_TYPE_LIST_FRAGMENT_TAG = "schedule_type_list_fragment_tag";
    private static final String EXPORT_IMPORT_FRAGMENT_TAG = "export_import_fragment_tag";

    @Override
    protected void onNewIntent(Intent intent) {
        super.onNewIntent(intent);
        String action = intent.getAction();
        String type = intent.getType();

        if (Intent.ACTION_VIEW.equals(action) && type != null){
            selectedItemId = IMPORT_REQUESTED_ITEM;
            MenuItem newSelectedMenuItem = mNavViewDrawer.getMenu().findItem(selectedItemId);
            setNewFragmentContent(newSelectedMenuItem);
            onImportIntent(intent);
        }
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        setContentView(R.layout.activity_main);

        mToolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(mToolbar);

        mDrawer = (DrawerLayout) findViewById(R.id.drawer_layout);
        mNavViewDrawer = (NavigationView) findViewById(R.id.nav_view_drawer);
        setupDrawerContent(mNavViewDrawer);


        mDrawerToggle = setupDrawToggle();
        mDrawer.addDrawerListener(mDrawerToggle);

/*        If savedInstanceState == null - set default selected item in navigation drawer
          Else savedInstanceState != null - get date Bundle id of selected item */
        if (savedInstanceState != null) {
            selectedItemId = savedInstanceState.getInt(SELECTED_ITEM_ID, DEFAULT_ITEM);
        } else {
            selectedItemId = DEFAULT_ITEM;
        }

        Intent intent = getIntent();
        String action = intent.getAction();
        String type = intent.getType();

        if (Intent.ACTION_VIEW.equals(action) && type != null){
            selectedItemId = IMPORT_REQUESTED_ITEM;
            MenuItem newSelectedMenuItem = mNavViewDrawer.getMenu().findItem(selectedItemId);
            setNewFragmentContent(newSelectedMenuItem);
            onImportIntent(intent);
            return;
        }

        MenuItem newSelectedMenuItem = mNavViewDrawer.getMenu().findItem(selectedItemId);
        setNewFragmentContent(newSelectedMenuItem);
    }


    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()) {
            case android.R.id.home:
                mDrawer.openDrawer(GravityCompat.START);
                return true;
        }

        return super.onOptionsItemSelected(item);
    }

    @Override
    protected void onPostCreate(Bundle savedInstanceState) {
        super.onPostCreate(savedInstanceState);
        mDrawerToggle.syncState();
    }

    @Override
    public void onConfigurationChanged(Configuration newConfig) {
        super.onConfigurationChanged(newConfig);
        mDrawerToggle.onConfigurationChanged(newConfig);
    }

    @Override
    protected void onSaveInstanceState(Bundle outState) {
        outState.putInt(SELECTED_ITEM_ID, selectedItemId);
        super.onSaveInstanceState(outState);
    }

    private void setupDrawerContent(NavigationView navigationView) {
        navigationView.setNavigationItemSelectedListener(new NavigationView.OnNavigationItemSelectedListener() {
            @Override
            public boolean onNavigationItemSelected(@NonNull MenuItem item) {
                setNewFragmentContent(item);
                return true;
            }
        });
    }


    /**
     * Sets new fragment to show
     * Sets selected item, title and close navigation drawer
     *
     * @param menuItem Id of selected item date navigation drawer
     */
    private void setNewFragmentContent(MenuItem menuItem) {
        Class fragmentClass = null;
        String fragmentTag = null;
        selectedItemId = menuItem.getItemId();

        if (selectedItemId == R.id.nav_item_feedback) {
            sendFeedBack();
        } else {
            switch (selectedItemId) {
                case R.id.nav_item_persons_list:
                    fragmentClass = PersonsListFragment.class;
                    fragmentTag = PERSON_LIST_FRAGMENT_TAG;
                    break;
                case R.id.nav_item_shifts_list:
                    fragmentClass = ShiftListFragment.class;
                    fragmentTag = SHIFT_LIST_FRAGMENT_TAG;
                    break;
                case R.id.nav_item_about_project:
                    fragmentClass = AboutFragment.class;
                    fragmentTag = ABOUT_FRAGMENT_TAG;
                    break;
                case R.id.nav_item_schedule_types_list:
                    fragmentClass = ScheduleTypeListFragment.class;
                    fragmentTag = SCHEDULE_TYPE_LIST_FRAGMENT_TAG;
                    break;
                case R.id.nav_item_export_import:
                    fragmentClass = ExportImportFragment.class;
                    fragmentTag = EXPORT_IMPORT_FRAGMENT_TAG;
                    break;
            }

            switchFragment(fragmentClass, fragmentTag);

            menuItem.setChecked(true);
            setTitle(menuItem.getTitle());
            mDrawer.closeDrawers();
        }
    }

    /**
     * Sets new fragment and content of fragmentContainer in layout
     *
     * @param fragmentClass Class of new fragment
     */
    private void switchFragment(Class fragmentClass, String fragmentTag) {
        Fragment fragment = null;
        if (fragmentClass != null) {
            try {
                fragment = (Fragment) fragmentClass.newInstance();
            } catch (Exception e) {
                e.printStackTrace();
            }

            FragmentManager fragmentManager = getSupportFragmentManager();
            fragmentManager.beginTransaction().replace(R.id.fragmentContainer, fragment, fragmentTag).commit();
            fragmentManager.executePendingTransactions();
        }
    }

    @Override
    public void onBackPressed() {
//        On pressed back button - close drawer
        if (mDrawer.isDrawerOpen(GravityCompat.START)) {
            mDrawer.closeDrawers();
        } else {
            super.onBackPressed();
        }
    }

    private ActionBarDrawerToggle setupDrawToggle() {
        return new ActionBarDrawerToggle(this, mDrawer, mToolbar, R.string.drawer_open, R.string.drawer_close);
    }

    //    Send feedback = set intent with ACTION_VIEW and send into system - browser, html viewer, ...
    private void sendFeedBack() {
        Intent intent = new Intent(Intent.ACTION_VIEW);
        intent.setData(Uri.parse(getString(R.string.github_url_feedback)));
        startActivity(intent);
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
    }

    @Override
    public void onDateSet(LocalDate pickedDate) {

        ShiftListFragment shiftListFragment = (ShiftListFragment) getSupportFragmentManager().findFragmentById(R.id.fragmentContainer);
        if (shiftListFragment != null) {
            shiftListFragment.setSelectedDate(pickedDate);
        }
    }

    private void onImportIntent(Intent intent){
        ExportImportFragment fragment = (ExportImportFragment) getSupportFragmentManager().findFragmentByTag(EXPORT_IMPORT_FRAGMENT_TAG);
        fragment.onImportRequested(intent.getData());
    }
}
