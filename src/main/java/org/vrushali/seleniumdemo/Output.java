
/*******************************************************************************
 * Copyright 2020  Vrushali Foundation
 * 
 * Licensed under the Apache License, Version 2.0 (the "License"); you may not
 * use this file except in compliance with the License.  You may obtain a copy
 * of the License at
 * 
 *   http://www.apache.org/licenses/LICENSE-2.0
 * 
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS, WITHOUT
 * WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.  See the
 * License for the specific language governing permissions and limitations under
 * the License.
 ******************************************************************************/

/**
 * 
 */
package org.vrushali.seleniumdemo;

/**
 * @author spaneos
 *
 */
public class Output {

	String usn;
	String term;
	String courseCode;
	String courseName;
	String internal;
	String external;
	String total;
	String result;
	
	/**
	 * @param usn
	 * @param term
	 * @param courseCode
	 * @param courseName
	 * @param internal
	 * @param external
	 * @param total
	 * @param result
	 */
	public Output(String usn, String term, String courseCode, String courseName, String internal, String external,
			String total, String result) {
		super();
		this.usn = usn;
		this.term = term;
		this.courseCode = courseCode;
		this.courseName = courseName;
		this.internal = internal;
		this.external = external;
		this.total = total;
		this.result = result;
	}
	@Override
	public String toString() {
		return String.format(
				"Output [usn=%s, term=%s, courseCode=%s, courseName=%s, internal=%s, external=%s, total=%s, result=%s]\n", usn,
				term, courseCode, courseName, internal, external, total, result);
	}
	
	
	
	
}
