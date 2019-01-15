package mo.edu.ipm.siweb.data;

import mo.edu.ipm.siweb.data.model.AcademicYear;
import mo.edu.ipm.siweb.data.model.AttendanceHistory;
import mo.edu.ipm.siweb.data.model.ClassesTime;
import mo.edu.ipm.siweb.data.model.ExamsTime;
import mo.edu.ipm.siweb.data.model.ClassTaken;
import mo.edu.ipm.siweb.data.model.Login;
import mo.edu.ipm.siweb.data.model.Profile;
import retrofit2.Call;
import retrofit2.http.Field;
import retrofit2.http.FormUrlEncoded;
import retrofit2.http.GET;
import retrofit2.http.POST;
import retrofit2.http.Query;

public interface SIWebService {

    String BASE_URL = "https://wapps.ipm.edu.mo/siweb/";

    @FormUrlEncoded
    @POST("login.asp")
    Call<Login> authenticate(
            @Field("NETID") String id,
            @Field("NETPASSWORD") String password,
            @Field("action") String action);

    @GET("stud_info.asp")
    Call<Profile> getProfile();

    @GET("grade.asp")
    Call<AcademicYear> getAcademicYear();

    @FormUrlEncoded
    @POST("grade.asp")
    Call<ClassTaken> getGradeAndAbsences(
            @Field("sel_year") String year
    );

    // total is useless in here
    @GET("grade_atte.asp")
    Call<AttendanceHistory> getAttendanceHistory(
            @Query("anol_ano") String year,
            @Query("cod") int cod,
            @Query("total") int total
    );

    @GET("examtime_stud.asp")
    Call<ExamsTime> getExamsTime();

    @GET("time_stud.asp")
    Call<ClassesTime> getClassesTime();

}
