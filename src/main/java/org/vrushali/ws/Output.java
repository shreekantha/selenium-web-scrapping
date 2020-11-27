
/**
 * Copyright 2020  Vrushali Foundation
 * <p>
 * Licensed under the Apache License, Version 2.0 (the "License"); you may not
 * use this file except in compliance with the License.  You may obtain a copy
 * of the License at
 * <p>
 * http://www.apache.org/licenses/LICENSE-2.0
 * <p>
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS, WITHOUT
 * WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.  See the
 * License for the specific language governing permissions and limitations under
 * the License.
 */

/**
 *
 */
package org.vrushali.ws;

/**
 * @author spaneos
 *
 */

public class Output {

    private String usn;
    private String term;
    private String courseCode;
    private String courseName;
    private String internal;
    private String external;
    private String total;
    private String credits;
    private String gradePoints;
    private String grade;
    private String result;
    private String identifier;

    public Output() {

    }

  

    /**
	 * @param usn
	 * @param term
	 * @param courseCode
	 * @param courseName
	 * @param internal
	 * @param external
	 * @param total
	 * @param credits
	 * @param gradePoints
	 * @param grade
	 * @param result
	 * @param identifier
	 */
	public Output(String usn, String term, String courseCode, String courseName, String internal, String external,
			String total, String credits, String gradePoints, String grade, String result, String identifier) {
		super();
		this.usn = usn;
		this.term = term;
		this.courseCode = courseCode;
		this.courseName = courseName;
		this.internal = internal;
		this.external = external;
		this.total = total;
		this.credits = credits;
		this.gradePoints = gradePoints;
		this.grade = grade;
		this.result = result;
		this.identifier = identifier;
	}

	
	public Output(String usn, String term, String courseCode, String courseName, String internal, String external,
			String total, String result, String identifier) {
		super();
		this.usn = usn;
		this.term = term;
		this.courseCode = courseCode;
		this.courseName = courseName;
		this.internal = internal;
		this.external = external;
		this.total = total;
		
		this.result = result;
		this.identifier = identifier;
	}


	



	public String getUsn() {
        return usn;
    }

    public void setUsn(String usn) {
        this.usn = usn;
    }

    public String getTerm() {
        return term;
    }

    public void setTerm(String term) {
        this.term = term;
    }

    public String getCourseCode() {
        return courseCode;
    }

    public void setCourseCode(String courseCode) {
        this.courseCode = courseCode;
    }

    public String getCourseName() {
        return courseName;
    }

    public void setCourseName(String courseName) {
        this.courseName = courseName;
    }

    public String getInternal() {
        return internal;
    }

    public void setInternal(String internal) {
        this.internal = internal;
    }

    public String getExternal() {
        return external;
    }

    public void setExternal(String external) {
        this.external = external;
    }

    public String getTotal() {
        return total;
    }

    public void setTotal(String total) {
        this.total = total;
    }
    
    

    public String getCredits() {
		return credits;
	}

	public void setCredits(String credits) {
		this.credits = credits;
	}

	public String getGradePoints() {
		return gradePoints;
	}

	public void setGradePoints(String gradePoints) {
		this.gradePoints = gradePoints;
	}

	public String getGrade() {
		return grade;
	}

	public void setGrade(String grade) {
		this.grade = grade;
	}

	public String getResult() {
        return result;
    }

    public void setResult(String result) {
        this.result = result;
    }

    
    public String getIdentifier() {
		return identifier;
	}

	public void setIdentifier(String identifier) {
		this.identifier = identifier;
	}

	@Override
	public String toString() {
		return String.format(
				"Output [usn=%s, term=%s, courseCode=%s, courseName=%s, internal=%s, external=%s, total=%s, result=%s, identifier=%s]",
				usn, term, courseCode, courseName, internal, external, total, result, identifier);
	}

	


}
