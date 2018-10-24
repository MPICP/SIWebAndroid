package mo.edu.ipm.cp.siweb;

import org.junit.Test;

import mo.edu.ipm.cp.siweb.HTTP.HTTPRequest;

import static org.junit.Assert.*;

/**
 * Example local unit test, which will execute on the development machine (host).
 *
 * @see <a href="http://d.android.com/tools/testing">Testing documentation</a>
 */
public class HTTPRequestUnitTest {

    HTTPRequest request = HTTPRequest.getInstance();
    @Test
    public void addition_isCorrect() {
        assertEquals(4, 2 + 2);
    }

    @Test
    public void login_isCorrect() {
        assertTrue(request.login("P1709046", ""));
        assertFalse(request.login("P1234567", "FakePassword"));
    }

    @Test
    public void academicYear_isCorrect() throws Exception{
        assertNotNull(request.getAcademicYearForGrade());
    }
}