package mo.edu.ipm.siweb.ui;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.AsyncTask;
import android.os.Bundle;
import android.support.design.widget.Snackbar;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ProgressBar;
import android.widget.TextView;

import org.json.JSONException;
import org.jsoup.HttpStatusException;

import java.io.IOException;

import mo.edu.ipm.siweb.R;
import mo.edu.ipm.siweb.data.remote.JsonDataAdapter;

public class LoginFragment extends Fragment {

    private Button mLoginButton;
    private TextView mStudentIDTextView;
    private TextView mStudentPasswordTextView;
    private ProgressBar mProgressBar;

    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        return inflater.inflate(R.layout.fragment_login, null);
    }

    @Override
    public void onViewCreated(View view, Bundle savedInstanceState) {
        mStudentIDTextView = view.findViewById(R.id.studentID);
        mStudentPasswordTextView = view.findViewById(R.id.password);
        mProgressBar = view.findViewById(R.id.login_progress);
        mLoginButton = view.findViewById(R.id.buttonLogin);
        mLoginButton.setOnClickListener(view1 -> {
            // TODO try login

            String id = mStudentIDTextView.getText().toString();
            String password = mStudentPasswordTextView.getText().toString();

            if (checkInputs(id, password)) {
                mLoginButton.setVisibility(View.INVISIBLE);
                mProgressBar.setVisibility(View.VISIBLE);
                new LoginTask().execute(id, password);
            }
        });
    }

    private boolean checkInputs(String id, String password) {
        if (id.isEmpty()) {
            mStudentIDTextView.requestFocus();
            mStudentIDTextView.setError("Student ID is required");
            return false;
        }

        if (id.length() != 8 || Character.toUpperCase(id.charAt(0)) != 'P') {
            mStudentIDTextView.requestFocus();
            mStudentIDTextView.setError("Illegal Student ID format");
            return false;
        }

        if (password.isEmpty()) {
            mStudentPasswordTextView.requestFocus();
            mStudentPasswordTextView.setError("Password is required");
            return false;
        }

        return true;
    }

    public class LoginTask extends AsyncTask<String, Void, Integer> {

        public final Integer LOGIN_SUCCESS = 1;
        public final Integer LOGIN_FAILURE = 2;
        public final Integer LOGIN_IO_FAILURE = 3;

        @Override
        protected Integer doInBackground(String... strings) {
            try {
                JsonDataAdapter jsonDataAdapter = JsonDataAdapter.getInstance();
                return jsonDataAdapter.login(strings[0], strings[1]).getBoolean("login") ? LOGIN_SUCCESS : LOGIN_FAILURE;
            } catch (HttpStatusException hse) {
                if (hse.getStatusCode() == 401)
                    return LOGIN_FAILURE;
            } catch (IOException ioe) {
                return LOGIN_IO_FAILURE;
            } catch (JSONException jsone) {
                return LOGIN_IO_FAILURE;
            }
            return LOGIN_SUCCESS;
        }

        @Override
        protected void onPostExecute(Integer result) {
            mProgressBar.setVisibility(View.INVISIBLE);
            if (result == LOGIN_SUCCESS) {
                SharedPreferences sharedPref = getActivity().getPreferences(Context.MODE_PRIVATE);
                SharedPreferences.Editor editor = sharedPref.edit();
                editor.putString("studentID", mStudentIDTextView.getText().toString());
                editor.putString("password", mStudentPasswordTextView.getText().toString());
                editor.commit();

                Intent intent = new Intent(getActivity(), MainActivity.class);
                startActivity(intent);
                // JUMP TO ACTIVITY
            } else {
                mLoginButton.setVisibility(View.VISIBLE);
                String message = "";
                if (result == LOGIN_FAILURE) {
                    message = "Invalid id or password";
                } else if (result == LOGIN_IO_FAILURE) {
                    message = "An error occured while login";
                }
                Snackbar.make(getView(), message, Snackbar.LENGTH_SHORT).show();
            }

        }
    }
}
