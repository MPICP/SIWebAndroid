package mo.edu.ipm.siweb.smb;

import android.content.Context;
import android.content.SharedPreferences;
import android.util.Log;

import com.hierynomus.msfscc.fileinformation.FileIdBothDirectoryInformation;
import com.hierynomus.smbj.SMBClient;
import com.hierynomus.smbj.auth.AuthenticationContext;
import com.hierynomus.smbj.auth.Authenticator;
import com.hierynomus.smbj.connection.Connection;
import com.hierynomus.smbj.session.Session;
import com.hierynomus.smbj.share.DiskShare;
import com.hierynomus.smbj.share.File;

import java.io.IOException;
import java.util.List;

import kotlin.NotImplementedError;
import mo.edu.ipm.siweb.R;
import mo.edu.ipm.siweb.SIWeb;

public class SIWebSMBClient {

    public static final String TAG = "SIWebSMBClient";

    private static final String IODrive = "202.175.9.5";
    private static final String IOPath = "\\Share\\"; // and school name

    private Connection mConnection;
    private Session mSession;

    private DiskShare mDriveShare;

    public SIWebSMBClient() {
        Context context = SIWeb.applicationContext.get();
        SharedPreferences sharedPreferences = context.getSharedPreferences(context.getString(R.string.pref_credential),
                Context.MODE_PRIVATE);

        String studentID = sharedPreferences.getString("studentID", "");
        String password = sharedPreferences.getString("password", "");
    }

    private void authenticateUser(String studentID, String password) {
        AuthenticationContext ac = new AuthenticationContext(studentID, password.toCharArray(), null);
        mSession = mConnection.authenticate(ac);
    }

    public List<File> listFileInDirectory(String directory) {
        throw new NotImplementedError();
    }

    public boolean uploadFileToDirectory(File file, String directory) {
        throw new NotImplementedError();
    }

    public boolean deleteFileFromDirectory(String path) {
        throw new NotImplementedError();
    }
}
