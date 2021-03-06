package mo.edu.ipm.siweb.data.model;

import android.arch.lifecycle.MutableLiveData;
import android.arch.lifecycle.ViewModel;

import org.json.JSONException;
import org.json.JSONObject;

import java.io.IOException;

import mo.edu.ipm.siweb.data.remote.JsonDataAdapter;

public class ProfileViewModel extends ViewModel {

    private MutableLiveData<Profile> mProfile;

    public class Profile {
        private String id;
        private String name;

        public String getId() {
            return id;
        }

        public String getName() {
            return name;
        }

        public void setId(String id) {
            this.id = id;
        }

        public void setName(String name) {
            this.name = name;
        }
    }

    public MutableLiveData<Profile> getProfile() {
        mProfile = new MutableLiveData<>();
        loadProfile();

        return mProfile;
    }

    private void loadProfile() {
        new Thread(() -> {
            try {
                JSONObject object = JsonDataAdapter.getInstance().getProfile();
                Profile profile = new Profile();
                profile.setId(object.getString("id"));
                profile.setName(object.getString("name"));
                mProfile.postValue(profile);
            } catch (JSONException jsone) {
            } catch (IOException ioe) {
            }
        }).start();
    }
}
