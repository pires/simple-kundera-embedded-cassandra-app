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
import java.util.Map;

import javax.persistence.Query;

import org.apache.cassandra.thrift.ConsistencyLevel;

import com.github.pires.example.Constants;
import com.github.pires.example.model.User;
import com.impetus.client.cassandra.common.CassandraConstants;
import com.impetus.client.cassandra.thrift.ThriftClient;
import com.impetus.kundera.client.Client;

public class UserDao extends AbstractDao<User> {

	public UserDao() {
		super(User.class);
	}

	/**
	 * Finds {@link User} instances with specified first name.
	 * <p>
	 * This is a JPQL query.
	 * 
	 * @param firstName
	 *            the first name of the user to query for
	 * @return a list of {@link User} instances with specified first name.
	 */
	public List<User> findByFirstName(String firstName) {
		String sql = "select u from User u where u.firstName = :firstname";
		Query q = getEntityManager().createQuery(sql);
		q.setParameter("firstname", firstName);
		List<User> results = q.getResultList();

		return results == null ? new ArrayList<User>() : results;
	}

	/**
	 * CQL query.
	 * 
	 * @param surname
	 *            the surname of the user to query for
	 * @return a list of {@link User} instances with specified surname.
	 */
	public List<User> findNativeByLastName(String surname) {
		Map<String, Client> clientMap = (Map<String, Client>) getEntityManager()
		        .getDelegate();
		ThriftClient tc = (ThriftClient) clientMap.get(Constants.PU);
		tc.setCqlVersion(CassandraConstants.CQL_VERSION_3_0);
		tc.setConsistencyLevel(ConsistencyLevel.QUORUM);

		String sql = "select * from users where surname='".concat(surname)
		        .concat("'");
		List<User> results = getEntityManager().createNativeQuery(sql,
		        User.class).getResultList();

		return results == null ? new ArrayList<User>() : results;
	}

}