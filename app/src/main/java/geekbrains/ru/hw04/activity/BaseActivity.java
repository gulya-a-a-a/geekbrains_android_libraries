package geekbrains.ru.hw04.activity;


import android.annotation.SuppressLint;

import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentTransaction;

@SuppressLint("Registered")
public class BaseActivity extends AppCompatActivity {

    public void addFragment(int containerID, Fragment fragment) {
        Fragment f = getSupportFragmentManager().findFragmentById(containerID);
//        if (f != null)
//            return;
        FragmentTransaction fragmentTransaction = getSupportFragmentManager().beginTransaction();
        fragmentTransaction.setTransition(FragmentTransaction.TRANSIT_FRAGMENT_FADE);
        fragmentTransaction.replace(containerID, fragment);
        fragmentTransaction.addToBackStack("previous");
        fragmentTransaction.commit();
    }
}
