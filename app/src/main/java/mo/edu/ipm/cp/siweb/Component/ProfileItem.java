package mo.edu.ipm.cp.siweb.Component;

import android.content.Context;
import android.util.AttributeSet;
import android.widget.RelativeLayout;

import mo.edu.ipm.cp.siweb.R;

public class ProfileItem extends RelativeLayout {
    public ProfileItem(Context context) {
        super(context);
        init();
    }

    public ProfileItem(Context context, AttributeSet attrs) {
        super(context, attrs);
        init();
    }

    public ProfileItem(Context context, AttributeSet attrs, int defStyle) {
        super(context, attrs, defStyle);
        init();
    }

    private void init () {
        inflate(getContext(), R.layout.profile_item, this);
    }
}
