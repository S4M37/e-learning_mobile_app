package tn.ppp.gl3.e_learning.activities;

import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentTransaction;
import android.support.v7.app.AppCompatActivity;

import tn.ppp.gl3.e_learning.fragments.MainFragment;
import tn.ppp.gl3.e_learning.R;
import tn.ppp.gl3.e_learning.services.CompteManager;
import tn.ppp.gl3.e_learning.utils.Utils;

public class MainActivity extends AppCompatActivity {

    private FragmentManager fragmentManager;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        Utils.token = CompteManager.getToken(this);
        fragmentManager = getSupportFragmentManager();
        showFragment(new MainFragment());
    }

    public void showFragment(Fragment fragment) {
        FragmentTransaction fragmentTransaction = fragmentManager.beginTransaction();
        if (fragment != null) {
            fragmentTransaction.replace(R.id.container, fragment);
            /*if (fragmentManager.getBackStackEntryCount() > 1)
                fragmentManager.popBackStack(fragmentManager.getBackStackEntryAt(0).getId(), FragmentManager.POP_BACK_STACK_INCLUSIVE);
            fragmentTransaction.addToBackStack(null);*/
            fragmentTransaction.addToBackStack(null);
            fragmentTransaction.commit();
        }
    }


}
