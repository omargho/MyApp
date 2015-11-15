package com.omar.og.myapplication;

import android.graphics.drawable.Drawable;
import android.os.AsyncTask;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentStatePagerAdapter;
import android.support.v4.view.ViewPager;
import android.support.v7.app.ActionBarActivity;
import android.support.v7.widget.Toolbar;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.ImageView;

import com.oguzdev.circularfloatingactionmenu.library.FloatingActionButton;
import com.oguzdev.circularfloatingactionmenu.library.FloatingActionMenu;
import com.oguzdev.circularfloatingactionmenu.library.SubActionButton;
import com.omar.og.myapplication.fragments.FragmentBoxOffice;
import com.omar.og.myapplication.fragments.FragmentSearch;
import com.omar.og.myapplication.fragments.FragmentUpcomming;

import it.neokree.materialtabs.MaterialTab;
import it.neokree.materialtabs.MaterialTabHost;
import it.neokree.materialtabs.MaterialTabListener;
import jp.co.recruit_lifestyle.android.widget.WaveSwipeRefreshLayout;


public class MainActivity extends ActionBarActivity implements MaterialTabListener { /* When using Appcombat support library
                                                         you need to extend Main Activity to
                                                         ActionBarActivity.
                                                      */


    private Toolbar toolbar;                              // Declaring the Toolbar Object
    private MaterialTabHost tabHost;

    private ViewPager viewPager;
public static final int MOVIES_HITS=0;
    public static final int MOVIES_UPCOMMING=1;
    public static final int MOVIES_SEARCH_RESULT=2;
    public static WaveSwipeRefreshLayout mWaveSwipeRefreshLayout;
    MyPagerAdapter pager;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);


        toolbar = (Toolbar) findViewById(R.id.tool_bar); // Attaching the layout to the toolbar object
        setSupportActionBar(toolbar);
        tabHost = (MaterialTabHost) findViewById(R.id.materialTabHost);
        viewPager = (ViewPager) findViewById(R.id.viewPager);
        boolean aaa=false;

        pager = new MyPagerAdapter(getSupportFragmentManager());
        viewPager.setAdapter(pager);
        viewPager.setOnPageChangeListener(new ViewPager.SimpleOnPageChangeListener() {
            @Override
            public void onPageSelected(int position) {
                // when user do a swipe the selected tab change
                tabHost.setSelectedNavigationItem(position);
               //viewPager.setOffscreenPageLimit(3);//we use it when our pagerAdpater extends StateAdpter
            }
        });

        for (int i = 0; i < pager.getCount(); i++) {
            tabHost.addTab(
                    //   tabHost.newTab().setText(pager.getPageTitle(i)).setTabListener(this)

                    tabHost.newTab().setIcon(pager.getIcon(i)).setTabListener(this)
            );
        }


        //-------------------------------------- FAB
        ImageView icon = new ImageView(this); // Create an icon
        icon.setImageResource(R.drawable.ic_add_black_24dp);

        FloatingActionButton actionButton = new FloatingActionButton.Builder(this)
                .setContentView(icon).setBackgroundDrawable(R.drawable.ic_fab)
                .build();
        ImageView icon0 = new ImageView(this);
        icon0.setImageResource(R.drawable.right_arrow);


        ImageView icon1 = new ImageView(this);
        icon1.setImageResource(R.drawable.ic_account_circle_black_24dp);

        ImageView icon2 = new ImageView(this);

        icon2.setImageResource(R.drawable.ic_lock_black_24dp);

        SubActionButton.Builder itemBuilder = new SubActionButton.Builder(this);

        SubActionButton button0 = itemBuilder.setContentView(icon0).build();
        SubActionButton button1 = itemBuilder.setContentView(icon1).build();
        SubActionButton button2 = itemBuilder.setContentView(icon2).build();
        FloatingActionMenu actionMenu = new FloatingActionMenu.Builder(this)
                .addSubActionView(button1)
                .addSubActionView(button0).addSubActionView(button2)
                        // ...
                .attachTo(actionButton)
                .build();
        button2.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                ((FragmentBoxOffice) pager.instantiateItem(viewPager, viewPager.getCurrentItem())).sortMe();
            }
        });

        /*
        for action we can make the main activity implement View.OnClickListener and place into it methods executed from different fragment
        and in the method on click "onClick(View v)" we create a instance of the runned fragment
        "Fragment fragment = (Fragment) adapter.instantiateItem(viewPager, viewPager.getCurrentItem());"
        then we must compare the tag of the view and then execute th preffered task.
        if (v.getTag().equals("string")) {
                ((SortListener) fragment).onSortByRating();
        //as a solution here we create an interface contatain all the desired methods and all fragments implement this interface

        for every button we set his tag "button.setTag("name"):" and we set the listner"btn.setOnClickListener(this)"


        */
        //---------------------------------------refresh

        mWaveSwipeRefreshLayout = (WaveSwipeRefreshLayout) findViewById(R.id.main_swipe);
        //mWaveSwipeRefreshLayout.setColorSchemeColors(R.color.ColorPrimary);
        mWaveSwipeRefreshLayout.setWaveColor(getResources().getColor(R.color.ColorPrimary));

        mWaveSwipeRefreshLayout.setOnRefreshListener(new WaveSwipeRefreshLayout.OnRefreshListener() {
            @Override public void onRefresh() {
               // Toast.makeText(getApplicationContext(),"refresh",Toast.LENGTH_LONG).show();
                FragmentBoxOffice.newInstance("", "");
                // Do work to refresh the list here.
                new Task().execute();
            }
        });


        }


    private class Task extends AsyncTask<Void, Void, String[]> {

        @Override
        protected String[] doInBackground(Void... voids) {
            return new String[0];
        }

        @Override protected void onPostExecute(String[] result) {
            // Call setRefreshing(false) when the list has been refreshed.

            mWaveSwipeRefreshLayout.setRefreshing(false);
            super.onPostExecute(result);
        }}









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
            return true;
        }

        return super.onOptionsItemSelected(item);
    }

    @Override
    public void onTabSelected(MaterialTab materialTab) {
        viewPager.setCurrentItem(materialTab.getPosition());
    }

    @Override
    public void onTabReselected(MaterialTab materialTab) {

    }

    @Override
    public void onTabUnselected(MaterialTab materialTab) {

    }


    class MyPagerAdapter extends FragmentStatePagerAdapter {//we use statePagerAdapter when we want our fragment saved in memory when we swipe across view page

        String[] tabText = getResources().getStringArray(R.array.tabs);
        int[] icons = {R.drawable.ic_home_black_24dp, R.drawable.ic_account_circle_black_24dp, R.drawable.ic_lock_black_24dp};

        public MyPagerAdapter(FragmentManager fm) {
            super(fm);
            tabText = getResources().getStringArray(R.array.tabs);
        }

        @Override
        public Fragment getItem(int position) {
            Fragment fragment = null;
            switch (position){
                case MOVIES_HITS:
                    fragment= FragmentBoxOffice.newInstance("","");
                    break;
                case MOVIES_SEARCH_RESULT:
                    fragment=FragmentSearch.newInstance("", "");
                    break;
                case MOVIES_UPCOMMING:
                    fragment= FragmentUpcomming.newInstance("", "");
                    break;



            }

            return fragment;
        }

        @Override
        public CharSequence getPageTitle(int position) {

//            Drawable drawable = MrVector.inflate(getResources(), R.drawable.vector_android);
//            drawable.setBounds(0, 0, 36, 36);
//            ImageSpan imageSpan = new ImageSpan(drawable);
//            SpannableString spannableString = new SpannableString(" ");
//            spannableString.setSpan(imageSpan, 0, spannableString.length(), Spanned.SPAN_EXCLUSIVE_EXCLUSIVE);
            return tabText[position];
        }

        private Drawable getIcon(int posiition) {
            return getResources().getDrawable(icons[posiition]);
        }

        @Override
        public int getCount() {
            return 3;
        }
    }



}