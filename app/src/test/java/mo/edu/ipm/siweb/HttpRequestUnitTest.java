package mo.edu.ipm.siweb;

import org.junit.Test;

import mo.edu.ipm.siweb.util.HttpRequest;

import static org.junit.Assert.*;

/**
 * Example local unit test, which will execute on the development machine (host).
 *
 * @see <a href="http://d.android.com/tools/testing">Testing documentation</a>
 */
public class HttpRequestUnitTest {

    HttpRequest request = HttpRequest.getInstance();

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