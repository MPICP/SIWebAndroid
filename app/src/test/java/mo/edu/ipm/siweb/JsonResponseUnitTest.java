package mo.edu.ipm.siweb;

import org.junit.Test;

import mo.edu.ipm.siweb.data.remote.JsonDataAdapter;

public class JsonResponseUnitTest {

    JsonDataAdapter mJsonDataAdapter = JsonDataAdapter.getInstance();

    @Test
    public void testGetClassTable() {
        mJsonDataAdapter.getClassTable();
    }
}
