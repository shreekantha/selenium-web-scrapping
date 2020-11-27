
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
package org.vrushali.ws;

import com.opencsv.bean.CsvBindByName;

/**
 * @author spaneos
 *
 */
public class Input {

	@CsvBindByName()
	private String usn;
	@CsvBindByName
	private String term;
	@CsvBindByName
	private String scheme;

	/**
	 * 
	 */
	public Input() {
		super();
	}

	/**
	 * @param usn
	 * @param term
	 * @param scheme
	 */
	public Input(String usn, String term, String scheme) {
		super();
		this.usn = usn;
		this.term = term;
		this.scheme = scheme;
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

	public String getScheme() {
		return scheme;
	}

	public void setScheme(String scheme) {
		this.scheme = scheme;
	}

	@Override
	public String toString() {
		return String.format("Input [usn=%s, term=%s, scheme=%s]", usn, term, scheme);
	}

}
