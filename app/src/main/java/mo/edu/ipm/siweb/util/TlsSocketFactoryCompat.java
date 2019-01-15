package mo.edu.ipm.siweb.util;

import java.io.IOException;
import java.net.InetAddress;
import java.net.Socket;
import java.security.KeyManagementException;
import java.security.NoSuchAlgorithmException;
import java.util.ArrayList;

import javax.net.ssl.SSLContext;
import javax.net.ssl.SSLSocket;
import javax.net.ssl.SSLSocketFactory;
import javax.net.ssl.TrustManager;

/**
 * Enable disabled TLS ciphers.
 * <p>
 * Resolve compatibility for the obsolete cipher used in SIWeb
 *
 * @author dodaydream
 * @version 2018.10.15
 */
public class TlsSocketFactoryCompat extends SSLSocketFactory {
    private SSLSocketFactory delegate;

    public TlsSocketFactoryCompat() throws NoSuchAlgorithmException, KeyManagementException {
        SSLContext context = SSLContext.getInstance("TLS");
        context.init(null, null, null);
        delegate = context.getSocketFactory();
    }

    public TlsSocketFactoryCompat(TrustManager[] tm) throws NoSuchAlgorithmException, KeyManagementException {
        SSLContext context = SSLContext.getInstance("TLS");
        context.init(null, tm, new java.security.SecureRandom());
        delegate = context.getSocketFactory();
    }

    @Override
    public String[] getDefaultCipherSuites() {
        return delegate.getDefaultCipherSuites();
    }

    @Override
    public String[] getSupportedCipherSuites() {
        return delegate.getSupportedCipherSuites();
    }

    @Override
    public Socket createSocket() throws IOException {
        return enableTLSOnSocket(delegate.createSocket());
    }

    @Override
    public Socket createSocket(Socket s, String host, int port, boolean autoClose) throws IOException {
        return enableTLSOnSocket(delegate.createSocket(s, host, port, autoClose));
    }

    @Override
    public Socket createSocket(String host, int port) throws IOException {
        return enableTLSOnSocket(delegate.createSocket(host, port));
    }

    @Override
    public Socket createSocket(String host, int port, InetAddress localHost, int localPort) throws IOException {
        return enableTLSOnSocket(delegate.createSocket(host, port, localHost, localPort));
    }

    @Override
    public Socket createSocket(InetAddress host, int port) throws IOException {
        return enableTLSOnSocket(delegate.createSocket(host, port));
    }

    @Override
    public Socket createSocket(InetAddress address, int port, InetAddress localAddress, int localPort) throws IOException {
        return enableTLSOnSocket(delegate.createSocket(address, port, localAddress, localPort));
    }

    private Socket enableTLSOnSocket(Socket socket) {
        if (socket != null && (socket instanceof SSLSocket)) {
            //Create list of supported protocols
            ArrayList<String> supportedProtocols = new ArrayList<>();
            for (String protocol : ((SSLSocket) socket).getEnabledProtocols()) {

                //Log.d("TLSSocketFactory", "Supported protocol:" + protocol);
                //Only add TLS protocols (don't want ot support older SSL versions)
                if (protocol.toUpperCase().contains("TLS")) {
                    supportedProtocols.add(protocol);
                }
            }
            //Force add TLSv1.1 and 1.2 if not already added
            if (!supportedProtocols.contains("TLSv1.1")) {
                supportedProtocols.add("TLSv1.1");
            }
            if (!supportedProtocols.contains("TLSv1.2")) {
                supportedProtocols.add("TLSv1.2");
            }

            String[] protocolArray = supportedProtocols.toArray(new String[supportedProtocols.size()]);
            ((SSLSocket) socket).setEnabledProtocols(protocolArray);
            ((SSLSocket) socket).setEnabledCipherSuites(((SSLSocket) socket).getSupportedCipherSuites());
        }
        return socket;
    }
}