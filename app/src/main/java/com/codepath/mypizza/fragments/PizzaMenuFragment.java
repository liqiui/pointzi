package com.codepath.mypizza.fragments;

import android.content.Context;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.ListView;

import com.codepath.mypizza.R;
import com.codepath.mypizza.data.Pizza;

/**
 * Created by Shyam Rokde on 8/5/16.
 */
public class PizzaMenuFragment extends Fragment {

  OnItemSelectedListener listener;
  ArrayAdapter<String> itemsAdapter;

  // This event fires 1st, before creation of fragment or any views
  // The onAttach method is called when the Fragment instance is associated with an Activity.
  // This does not mean the Activity is fully initialized.
  @Override
  public void onAttach(Context context) {
    super.onAttach(context);
    if(context instanceof OnItemSelectedListener){
      this.listener = (OnItemSelectedListener) context;
    } else {
      throw new ClassCastException(context.toString()
        + " must implement PizzaMenuFragment.OnItemSelectedListener");
    }
  }

  // This event fires 2nd, before views are created for the fragment
  // The onCreate method is called when the Fragment instance is being created, or re-created.
  // Use onCreate for any standard setup that does not require the activity to be fully created
  @Override
  public void onCreate(Bundle savedInstanceState) {
    super.onCreate(savedInstanceState);

    itemsAdapter = new ArrayAdapter<String>(getContext(), android.R.layout.simple_list_item_1, Pizza.pizzaMenu);
  }

  // The onCreateView method is called when Fragment should create its View object hierarchy
  @Override
  public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup parent, @Nullable Bundle savedInstanceState) {

    return inflater.inflate(R.layout.fragment_pizza_menu, parent, false);
  }


  // This event is triggered soon after onCreateView().
  // Any view setup should occur here.  E.g., view lookups and attaching view listeners.
  @Override
  public void onViewCreated(View view, Bundle savedInstanceState) {

    ListView lvItems = (ListView) view.findViewById(R.id.lvItems);
    lvItems.setAdapter(itemsAdapter);

    // load pizza detail on specific item click
    lvItems.setOnItemClickListener(new AdapterView.OnItemClickListener() {
      @Override
      public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
        // go to activity to load pizza details fragment
        listener.onPizzaItemSelected(position);

      }
    });
  }


  // Define the events that the fragment will use to communicate
  public interface OnItemSelectedListener {
    // This can be any number of events to be sent to the activity
    public void onPizzaItemSelected(int position);
  }


}
