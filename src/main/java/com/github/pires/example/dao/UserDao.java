/**
 * Licensed under the Apache License, Version 2.0 (the "License"); you may not
 * use this file except in compliance with the License. You may obtain a copy of
 * the License at
 *     http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS, WITHOUT
 * WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied. See the
 * License for the specific language governing permissions and limitations under
 * the License.
 */
package com.github.pires.example.dao;

import java.util.ArrayList;
import java.util.List;

import com.github.pires.example.model.User;

public class UserDao extends AbstractDao<User> {

	public UserDao() {
		super(User.class);
	}

	/**
	 * JPQL query.
	 * 
	 * @param firstName
	 *            the first name of the user to query for
	 * @return a list of {@link User} instances with specified first name.
	 */
	public List<User> findByFirstName(String firstName) {
		String sql = "select u from User u where u.firstName = '" + firstName
		        + "'";
		List<User> results = getEntityManager().createQuery(sql, User.class)
		        .getResultList();
		results = results == null ? new ArrayList<User>() : results;

		return results;
	}

	/**
	 * CQL query.
	 * 
	 * @param surname
	 *            the surname of the user to query for
	 * @return a list of {@link User} instances with specified surname.
	 */
	public List<User> findNativeByLastName(String surname) {
		String sql = "select * from users where surname = '" + surname + "'";
		List<User> results = getEntityManager().createNativeQuery(sql,
		        User.class).getResultList();
		results = results == null ? new ArrayList<User>() : results;

		return results;
	}

}