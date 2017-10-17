package layout;


import android.content.Context;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentStatePagerAdapter;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.ListView;

import edu.uw.fragmentdemo.R;
import edu.uw.viewpager.DetailFragment;
import edu.uw.viewpager.Movie;
import edu.uw.viewpager.MovieListFragment;

import static android.net.wifi.p2p.nsd.WifiP2pServiceRequest.newInstance;

/**
 * A simple {@link Fragment} subclass.
 */
public class SearchFragment extends Fragment {

    public static final String TAG = "SearchFragment";


    public SearchFragment() {
        // Required empty public constructor
    }

    //an interface for those who can respond to interactions with this Fragment
    interface onSearchSubmittedListener {
        void onSearchSubmitted(Movie movie);
    }

    private ArrayAdapter<Movie> adapter;
    private onSearchSubmittedListener callback;

    public static SearchFragment newInstance() {
        
        Bundle args = new Bundle();
        
        SearchFragment fragment = new SearchFragment();
        fragment.setArguments(args);
        return fragment;
    }

    public void onAttach(Context context) {
        super.onAttach(context);

        try {
            callback = (onSearchSubmittedListener)context;
        } catch (ClassCastException e) {
            throw new ClassCastException(context.toString() + " must implement onSearchSubmittedListener");
        }
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View rootView =  inflater.inflate(R.layout.fragment_search, container, false);

        ListView listView = (ListView)rootView.findViewById(R.id.list_view);
        listView.setAdapter(adapter);

        listView.onSearchSubmittedListener(new AdapterView.onSearchSubmittedListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                Movie movie = (Movie) parent.getItemAtPosition(position);
                Log.v(TAG, "You clicked on: " + movie);
                callback.onSearchSubmitted(movie);
            }
        });

        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_search, container, false);
    }
}

    public class MoviePagerAdapter extends FragmentStatePagerAdapter {
        public MoviePagerAdapter(FragmentManager fm) {
            super(fm);
        }

        @Override
        public int getCount() {
            return NUM_ITEMS;
        }

        @Override
        public Fragment getItem(int position) {
            if(position == 0) {
                return SearchFragment.newInstance();
            } else if (position == 1) {
                return MovieListFragment.newInstance();
            }
            return DetailFragment.newInstance();
        }
    }

