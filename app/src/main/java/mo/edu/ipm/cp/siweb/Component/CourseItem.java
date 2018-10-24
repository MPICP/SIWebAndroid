package mo.edu.ipm.cp.siweb.Component;

/*
 * Copyright (C) 2017 Sysdata Spa.
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *     http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */

import android.content.Intent;
import android.view.View;
import android.widget.Button;

import com.sysdata.widget.accordion.Item;

import mo.edu.ipm.cp.siweb.AttendenceHistoryActivity;
import mo.edu.ipm.cp.siweb.Model.Course;
import mo.edu.ipm.cp.siweb.R;

/**
 * Created on 05/04/17.
 *
 * @author Umberto Marini
 */
public class CourseItem extends Item {

    private String mTitle;
    private String mDescription;
    private Course course;
    private String mCourseStatistics;

    public static CourseItem create(Course course) {
        return new CourseItem(course);
    }

    CourseItem(Course course) {
        mTitle = course.subjectCode + '-' + course.sectionCode + ' ' + course.subjectTitle;
        mDescription = "updated at: " + course.lastEntryDate;
        mCourseStatistics = course.absenseHour != null ?
                String.format("Absense Rate: %s, Reasonable Absense : %s", course.absenseRate, course.raRate) :
                String.format("Continuous Assessment: %s, Exam: %s, Final: %s, Grade: %s", course.assessmentMark, course.examMark, course.finalMark, course.finalGrade);
        this.course = course;
    }

    public String getTitle() {
        return mTitle;
    }

    public String getDescription() {
        return mDescription;
    }

    public String getCourseStatistics () {
        return mCourseStatistics;
    }

    public String getCod () {
        return course.cod;
    }

    public String getYear () {
        return course.year;
    }

    @Override
    public int getUniqueId() {
        return hashCode();
    }

    @Override
    public boolean equals(Object o) {
        if (this == o)
            return true;
        if (o == null || getClass() != o.getClass())
            return false;

        CourseItem that = (CourseItem) o;

        if (!mTitle.equals(that.mTitle))
            return false;
        return mDescription != null ? mDescription.equals(that.mDescription) : that.mDescription == null;
    }

    @Override
    public int hashCode() {
        int result = course.hashCode();
        return result;
    }
}
