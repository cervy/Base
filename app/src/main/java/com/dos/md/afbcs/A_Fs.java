package com.dos.md.afbcs;

import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentPagerAdapter;
import android.support.v4.view.ViewPager;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.dos.md.R;

public class A_Fs extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_a__fs);

        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        // Create the adapter that will return a fragment for each of the three
        // primary sections of the activity.
        /*
      The {@link android.support.v4.view.PagerAdapter} that will provide
      fragments for each of the sections. We use a
      {@link FragmentPagerAdapter} derivative, which will keep every
      loaded fragment in memory. If this becomes too memory intensive, it
      may be best to switch to a
      {@link android.support.v4.app.FragmentStatePagerAdapter}.
     */
        SectionsPagerAdapter sectionsPagerAdapter = new SectionsPagerAdapter(getSupportFragmentManager());

        // Set up the ViewPager with the sections adapter.
        /*
      The {@link ViewPager} that will host the section contents.
     */
        ViewPager viewPager = (ViewPager) findViewById(R.id.container);
        assert viewPager != null;
        viewPager.setAdapter(sectionsPagerAdapter);

        /*TabLayout tabLayout = (TabLayout) findViewById(R.id.tabs);
        assert tabLayout != null;
        tabLayout.setupWithViewPager(viewPager);

        FloatingActionButton fab = (FloatingActionButton) findViewById(R.id.fab);
        fab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Snackbar.make(view, "Replace with your own action", Snackbar.LENGTH_LONG)
                        .setAction("Action", null).show();
            }
        });*/

    }


    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        //getMenuInflater().inflate(R.menu.menu_a__fs, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();

        //noinspection SimplifiableIfStatement
        // if (id == R.id.action_settings) {
        return true;
    }

    //return super.onOptionsItemSelected(item);


    /**
     * A placeholder fragment containing a simple view.
     */
    public static class PlaceholderFragment extends Fragment {
        /**
         * The fragment argument representing the section number for this
         * fragment.
         */
        private static final String ARG_SECTION_NUMBER = "section_number";

        public PlaceholderFragment() {
        }

        /**
         * Returns a new instance of this fragment for the given section
         * number.
         */
        public static PlaceholderFragment newInstance(int sectionNumber) {
            PlaceholderFragment fragment = new PlaceholderFragment();
            Bundle args = new Bundle();
            args.putInt(ARG_SECTION_NUMBER, sectionNumber);
            fragment.setArguments(args);
            return fragment;
        }

        @Override
        public View onCreateView(LayoutInflater inflater, ViewGroup container,
                                 Bundle savedInstanceState) {
            View rootView = inflater.inflate(R.layout.fragment_a__fs, container, false);
            TextView textView = (TextView) rootView.findViewById(R.id.section_label);
            textView.setText(getString(R.string.section_format, getArguments().getInt(ARG_SECTION_NUMBER)));
            return rootView;
        }
    }

    /**
     * A {@link FragmentPagerAdapter} that returns a fragment corresponding to
     * one of the sections/tabs/pages.
     */
    public class SectionsPagerAdapter extends FragmentPagerAdapter {

        public SectionsPagerAdapter(FragmentManager fm) {
            super(fm);
        }

        @Override
        public Fragment getItem(int position) {
            // getItem is called to instantiate the fragment for the given page.
            // Return a PlaceholderFragment (defined as a static inner class below).
            return PlaceholderFragment.newInstance(position + 1);
        }

        @Override
        public int getCount() {
            // Show 3 total pages.
            return 3;
        }

        @Override
        public CharSequence getPageTitle(int position) {
            switch (position) {
                case 0:
                    return "SECTION 1";
                case 1:
                    return "SECTION 2";
                case 2:
                    return "SECTION 3";
            }
            return null;
        }
    }

    /*showFragment(0);
    @Override
    public void onClick(View v) {

        final int id = v.getId();
        switch (id) {
            case R.id.leftMoney:

                showFragment(0);
                break;
            case R.id.profitDetail:
                showFragment(1);

                break;
            case R.id.bank:
                showFragment(2);

                break;
            case R.id.membershipPoint:
                showFragment(3);

                break;
            default:
        }
    }
    private void showFragment(int i) {

        FragmentManager fragmentManager = getSupportFragmentManager();
        FragmentTransaction transaction = fragmentManager.beginTransaction();
        hideFragment(transaction);

        final ActionBar actionBar = getSupportActionBar();
        assert actionBar != null;

        switch (i) {
            case 0:
                if (null == leftMone) {
                    leftMone = new LeftMone();
                    transaction.add(R.id.fragment, leftMone);
                }
                transaction.show(leftMone);
                actionBar.setTitle(R.string.leftMoney);

                break;
            case 1:
                if (null == profitDetai) {
                    profitDetai = new ProfitDetai();
                    transaction.add(R.id.fragment, profitDetai);

                }
                transaction.show(profitDetai);
                actionBar.setTitle(R.string.profitDetai);


                break;
            case 2:
                if (null == ban) {
                    ban = new Ban();
                    transaction.add(R.id.fragment, ban);

                }
                transaction.show(ban);
                actionBar.setTitle(R.string.zhanghuxinxi);


                break;
            case 3:
                if (null == membershipPoin) {
                    membershipPoin = new MembershipPoin();
                    transaction.add(R.id.fragment, membershipPoin);

                }
                transaction.show(membershipPoin);
                actionBar.setTitle(R.string.wodejifen);

                break;
            default:
        }
        transaction.commit();
    }

    private void hideFragment(FragmentTransaction transaction) {
        if (null != leftMone)
            transaction.hide(leftMone);
        if (null != profitDetai) transaction.hide(profitDetai);
        if (null != ban) transaction.hide(ban);
        if (null != membershipPoin) transaction.hide(membershipPoin);
    }*/
}