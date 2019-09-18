package com.codepath.mypizza;

import android.app.Activity;
import android.content.res.Configuration;
import android.os.Bundle;

import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentTransaction;
import androidx.appcompat.app.AppCompatActivity;
import android.util.Log;
import android.view.View;
import android.view.ViewGroup;
import android.view.ViewStub;
import android.widget.ListView;
import android.widget.Toast;

import com.codepath.mypizza.fragments.PizzaDetailFragment;
import com.codepath.mypizza.fragments.PizzaMenuFragment;

import java.util.LinkedList;
import java.util.Queue;
import java.util.UUID;

public class MainActivity extends AppCompatActivity  implements PizzaMenuFragment.OnItemSelectedListener {

  @Override
  protected void onCreate(Bundle savedInstanceState) {
    super.onCreate(savedInstanceState);
    setContentView(R.layout.activity_main);


    Log.d("DEBUG", getResources().getConfiguration().orientation + "");

    if (savedInstanceState == null) {
      // Instance of first fragment
      PizzaMenuFragment firstFragment = new PizzaMenuFragment();

      // Add Fragment to FrameLayout (flContainer), using FragmentManager
      FragmentTransaction ft = getSupportFragmentManager().beginTransaction();// begin  FragmentTransaction
      ft.add(R.id.flContainer, firstFragment);                                // add    Fragment
      ft.commit();                                                            // commit FragmentTransaction
    }

    if(getResources().getConfiguration().orientation == Configuration.ORIENTATION_LANDSCAPE){
      PizzaDetailFragment secondFragment = new PizzaDetailFragment();
      Bundle args = new Bundle();
      args.putInt("position", 0);
      secondFragment.setArguments(args);          // (1) Communicate with Fragment using Bundle
      FragmentTransaction ft2 = getSupportFragmentManager().beginTransaction();// begin  FragmentTransaction
      ft2.add(R.id.flContainer2, secondFragment);                               // add    Fragment
      ft2.commit();                                                            // commit FragmentTransaction
    }
  }

  @Override
  public void onPizzaItemSelected(int position) {
    Toast.makeText(this, "Called By Fragment A: position - "+ position, Toast.LENGTH_SHORT).show();

    // Load Pizza Detail Fragment
    PizzaDetailFragment secondFragment = new PizzaDetailFragment();

    Bundle args = new Bundle();
    args.putInt("position", position);
    secondFragment.setArguments(args);          // (1) Communicate with Fragment using Bundle


    if(getResources().getConfiguration().orientation == Configuration.ORIENTATION_LANDSCAPE){
      getSupportFragmentManager()
          .beginTransaction()
          .replace(R.id.flContainer2, secondFragment) // replace flContainer
          //.addToBackStack(null)
          .commit();
    }else{
      getSupportFragmentManager()
          .beginTransaction()
          .replace(R.id.flContainer, secondFragment) // replace flContainer
          .addToBackStack(null)
          .commit();
    }
  }

  //retrieve all View / ViewGroup with BFS
  public void retrieve(Object object) {
    Queue<View> queue = new LinkedList<>();
    View vg = this.getWindow().getDecorView();
    queue.add(vg);

    while ( !queue.isEmpty()) {
      vg = queue.remove();
      Log.d("UUID", object.getClass().getSimpleName() + "/" + vg.getClass().getSimpleName() + "-" + UUID.randomUUID());
      if (vg instanceof ViewGroup) {
        for (int i = 0; i < ((ViewGroup)vg).getChildCount(); i++) {
          queue.add(((ViewGroup)vg).getChildAt(i));
        }
      }
    }
  }
}
