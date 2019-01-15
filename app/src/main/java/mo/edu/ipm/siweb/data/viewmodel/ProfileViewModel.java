package mo.edu.ipm.siweb.data.viewmodel;

import android.util.Log;

import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.ViewModel;
import mo.edu.ipm.siweb.data.HttpService;
import mo.edu.ipm.siweb.data.model.Profile;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class ProfileViewModel extends ViewModel {

    private final String TAG = "ProfileViewModel";

    private MutableLiveData<Profile> mProfile;

    public MutableLiveData<Profile> getProfile() {
        if (mProfile == null) {
            mProfile = new MutableLiveData<>();
            loadProfile();
        }

        return mProfile;
    }

    private void loadProfile() {
        HttpService.SIWEB().getProfile().enqueue(new Callback<Profile>() {
            @Override
            public void onResponse(Call<Profile> call, Response<Profile> response) {
                Log.i(TAG, response.raw().toString());
                mProfile.postValue(response.body());
            }

            @Override
            public void onFailure(Call<Profile> call, Throwable t) {
                Log.e(TAG, t.getMessage(), t);
            }
        });
    }
}
