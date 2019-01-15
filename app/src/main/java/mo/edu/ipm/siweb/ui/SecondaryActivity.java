package mo.edu.ipm.siweb.ui;

import android.os.Bundle;
import android.os.PersistableBundle;

import androidx.annotation.Nullable;
import androidx.fragment.app.FragmentActivity;
import mo.edu.ipm.siweb.R;

public class SecondaryActivity extends FragmentActivity {

    public static final String ARG_FRAGMENT = "fragment";

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState, @Nullable PersistableBundle persistentState) {
        super.onCreate(savedInstanceState, persistentState);
        setContentView(R.layout.activity_secondary);
    }
}
