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
package com.github.pires.example;

import java.util.List;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.github.pires.example.dao.UserDao;
import com.github.pires.example.model.User;
import com.google.inject.Inject;
import com.impetus.client.cassandra.common.CassandraConstants;

public class Example {

	private static final Logger logger = LoggerFactory.getLogger(Example.class
	        .getName());

	private final UserDao userDao;

	@Inject
	public Example(UserDao userDao) {
		this.userDao = userDao;
		userDao.getEntityManager().setProperty("cql.version", CassandraConstants.CQL_VERSION_3_0);
	}

	public void run() {
		User u1 = new User("Paulo", "Pires");
		User u2 = new User("Joana", "Quiterio");
		User u3 = new User("Joana", "Pires");
		userDao.create(u1);
		userDao.create(u2);
		userDao.create(u3);

		logger.info("Have {} users persisted.", userDao.count());
		for (User user : userDao.findAll())
			logger.info(user.toString());

		logger.info("Querying DB for persons with first name Joana..");
		List<User> joanas = userDao.findByFirstName("Joana");
		logger.info("Found {} Joanas", joanas.size());
		for (User user : joanas)
			logger.info(" --> {}", user);

		logger.info("Querying DB for persons with last name Pires..");
		List<User> pireses = userDao.findNativeByLastName("Pires");
		logger.info("Found {} Pires", pireses.size());
		for (User user : pireses)
			logger.info(" --> {}", user);
	}

}