package mo.edu.ipm.siweb.ui.fragment;


import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.fragment.app.Fragment;
import mo.edu.ipm.siweb.R;
import mo.edu.ipm.siweb.smb.SIWebSMBClient;

/**
 * A simple {@link Fragment} subclass.
 * Use the {@link FileFragment#newInstance} factory method to
 * create an instance of this fragment.
 */
public class FileFragment extends Fragment {
    // TODO: Rename parameter arguments, choose names that match
    // the fragment initialization parameters, e.g. ARG_ITEM_NUMBER
    public static final String ARG_SMB_SERVER = "smb_server";

    private SIWebSMBClient mSIWebSMBClient;

    private static final String ARG_SMB_IO = "io_drive";
    private static final String ARG_SMB_U = "u_drive";

    public FileFragment() {
        // Required empty public constructor
    }

    /**
     * Use this factory method to create a new instance of
     * this fragment using the provided parameters.
     *
     * @param server Choose which server to connect.
     * @return A new instance of fragment FileFragment.
     */
    // TODO: Rename and change types and number of parameters
    public static FileFragment newInstance(String server) {
        FileFragment fragment = new FileFragment();
        Bundle args = new Bundle();
        args.putString(ARG_SMB_SERVER, server);
        fragment.setArguments(args);
        return fragment;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        if (getArguments() != null) {
            String server = getArguments().getString(ARG_SMB_SERVER);
            if (server.equals(ARG_SMB_IO))
                mSIWebSMBClient = null;
            else if (server.equals(ARG_SMB_U))
                mSIWebSMBClient = null;
        }
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        return inflater.inflate(R.layout.fragment_file, container);
    }

}
