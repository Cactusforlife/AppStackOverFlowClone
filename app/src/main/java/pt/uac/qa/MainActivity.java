package pt.uac.qa;

import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.design.widget.NavigationView;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentTransaction;
import android.support.v4.view.GravityCompat;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.ActionBar;
import android.support.v7.app.ActionBarDrawerToggle;
import android.support.v7.app.AppCompatActivity;
import android.view.MenuItem;
import android.view.View;
import android.widget.TextView;

import com.squareup.picasso.Picasso;

import fr.tkeunebr.gravatar.Gravatar;
import pt.uac.qa.model.User;
import pt.uac.qa.views.RoundImageView;

public class MainActivity extends AppCompatActivity {
    private RoundImageView userImageView;
    private TextView userEmailView;
    private DrawerLayout drawerLayout;
    private CharSequence title;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        setupViews();
        displayFragment(new QuestionsFragment());
        displayUserInfo();
    }

    @Override
    public boolean onOptionsItemSelected(final MenuItem item) {
        switch (item.getItemId()) {
            case android.R.id.home:
                if (drawerLayout.isDrawerOpen(GravityCompat.START)) {
                    drawerLayout.closeDrawers();
                } else {
                    drawerLayout.openDrawer(GravityCompat.START);
                }

                return true;
        }

        return super.onOptionsItemSelected(item);
    }

    private void setupViews() {
        final NavigationView navView = findViewById(R.id.nav_view);

        userImageView = navView.getHeaderView(0).findViewById(R.id.user_imageview);
        userEmailView = navView.getHeaderView(0).findViewById(R.id.user_emailview);
        drawerLayout = findViewById(R.id.drawer);

        final ActionBarDrawerToggle drawerToggle = new ActionBarDrawerToggle(this, drawerLayout, R.string.drawer_open, R.string.drawer_close) {
            /** Called when a drawer has settled in a completely closed state. */
            public void onDrawerClosed(View view) {
                super.onDrawerClosed(view);
                setActivityTitle(title);
                invalidateOptionsMenu();
            }

            /** Called when a drawer has settled in a completely open state. */
            public void onDrawerOpened(View drawerView) {
                super.onDrawerOpened(drawerView);
                setActivityTitle(getString(R.string.app_name));
                invalidateOptionsMenu();
            }
        };

        navView.setNavigationItemSelectedListener(new NavigationView.OnNavigationItemSelectedListener() {
            @Override
            public boolean onNavigationItemSelected(@NonNull final MenuItem item) {
                final int itemId = item.getItemId();

                title = item.getTitle();
                item.setChecked(true);
                drawerLayout.closeDrawers();

                switch (itemId) {

                    case R.id.nav_all_questions:
                        displayFragment(new QuestionsFragment());

                        break;
                    case R.id.nav_all_my_questions:
                        displayFragment(new MyQuestionsFragment());

                        break;

                    case R.id.nav_all_my_anwsers:
                        break;

                    case R.id.nav_logout:
                        final QAApp app = (QAApp) getApplication();
                        app.logout();
                        startActivity(new Intent(MainActivity.this, LoginActivity.class));
                        finish();
                }

                return true;
            }
        });

        if (getSupportActionBar() != null) {
            getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        }

        title = getString(R.string.app_name);

        drawerLayout.addDrawerListener(drawerToggle);
        drawerToggle.syncState();
    }

    private void setActivityTitle(final CharSequence title) {
        final ActionBar actionBar = getSupportActionBar();

        if (actionBar != null) {
            actionBar.setTitle(title);
        } else {
            setTitle(title);
        }
    }

    private void displayFragment(final Fragment fragment) {
        final FragmentTransaction fragmentTransaction = getSupportFragmentManager().beginTransaction();
        fragmentTransaction.replace(R.id.content_frame, fragment);
        fragmentTransaction.commitAllowingStateLoss();
    }

    private void displayUserInfo() {
        final QAApp app = (QAApp) getApplicationContext();
        final User user = app.getUser();
        final String gravatarUrl = Gravatar.init()
                .with(user.getEmail())
                .defaultImage(Gravatar.DefaultImage.MONSTER)
                .build();

        userEmailView.setText(user.getName());

        Picasso.get()
                .load(gravatarUrl)
                .fit()
                .centerInside()
                .into(userImageView);
    }
}
