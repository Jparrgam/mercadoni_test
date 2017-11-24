package com.co.test;

import android.os.Bundle;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentTransaction;

import com.co.test.ui.BaseActivity;
import com.co.test.ui.fragment.DetailMovieFragment;
import com.co.test.ui.fragment.MovieListFragment;
import com.co.test.ui.onFragmentListener;

public class MainActivity extends BaseActivity implements onFragmentListener {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        getSupportFragmentManager().beginTransaction().add(R.id.container_fragment, new MovieListFragment()).commit();
    }

    @Override
    public void onDetailsFragment(String id) {
        FragmentManager childFragMan = getSupportFragmentManager();
        FragmentTransaction childFragTrans = childFragMan.beginTransaction();

        DetailMovieFragment fragB = new DetailMovieFragment();

        Bundle bundle = new Bundle();
        bundle.putString(DetailMovieFragment.EXTRA_ID, id);

        fragB.setArguments(bundle);

        childFragTrans.add(R.id.container_fragment, fragB);
        childFragTrans.addToBackStack("detailMovie");
        childFragTrans.commit();
    }
}
