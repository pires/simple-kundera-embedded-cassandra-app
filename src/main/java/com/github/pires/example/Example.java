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

import java.util.Date;
import java.util.List;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.github.pires.example.dao.AuditRecordDao;
import com.github.pires.example.dao.UserDao;
import com.github.pires.example.model.AuditRecord;
import com.github.pires.example.model.AuditRecordId;
import com.github.pires.example.model.User;
import com.google.inject.Inject;

/**
 * Stuff to do.
 */
public class Example {

	private static final Logger logger = LoggerFactory.getLogger(Example.class
	        .getName());

	private final UserDao userDao;
	private final AuditRecordDao arDao;

	private final String APP1 = "app1";
	private final String APP2 = "app2";

	@Inject
	public Example(UserDao userDao, AuditRecordDao arDao) {
		this.userDao = userDao;
		this.arDao = arDao;
	}

	public void run() {
		testUserDao();
		testAuditRecordDao();
	}

	/**
	 * Test {@link UserDao}.
	 */
	private void testUserDao() {
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

	/**
	 * Test {@link AuditRecordDao}.
	 */
	private void testAuditRecordDao() {
		logger.info("PERSIST AND QUERY ALL");
		persist_and_query_all();
		logger.info("FIND ALL BY APP ID");
		find_all_by_appId();
		logger.info("FIND ALL BETWEEN TIME INTERVAL");
		find_all_by_appId_and_between_time_interval();
	}

	private void persist_and_query_all() {
		Long t = 0L;

		AuditRecordId arid1 = new AuditRecordId(APP1, "user1", "0", t);
		AuditRecord ar1 = new AuditRecord();
		ar1.setId(arid1);
		ar1.setAppIdIndex(APP1);
		ar1.setTimestampIndex(t);
		arDao.create(ar1);

		t = new Date().getTime();
		AuditRecordId arid2 = new AuditRecordId(APP1, "user2", "1", t);
		AuditRecord ar2 = new AuditRecord();
		ar2.setId(arid2);
		ar2.setAppIdIndex(APP1);
		ar2.setTimestampIndex(t);
		arDao.create(ar2);

		t -= 1000;
		AuditRecordId arid3 = new AuditRecordId(APP1, "user1", "1", t);
		AuditRecord ar3 = new AuditRecord();
		ar3.setId(arid3);
		ar3.setAppIdIndex(APP1);
		ar3.setTimestampIndex(t);
		arDao.create(ar3);

		t += 100;
		AuditRecordId arid4 = new AuditRecordId(APP1, "user1", "0", t);
		AuditRecord ar4 = new AuditRecord();
		ar4.setId(arid4);
		ar4.setAppIdIndex(APP1);
		ar4.setTimestampIndex(t);
		arDao.create(ar4);

		t -= 15000;
		AuditRecordId arid5 = new AuditRecordId(APP2, "user1", "0", t);
		AuditRecord ar5 = new AuditRecord();
		ar5.setId(arid5);
		ar5.setAppIdIndex(APP2);
		ar5.setTimestampIndex(t);
		arDao.create(ar5);

		for (AuditRecord record : arDao.findAll())
			logger.info("AuditRecord: {}", record);
	}

	private void find_all_by_appId() {
		for (AuditRecord record : arDao.find_all_by_appId(APP1))
			logger.info("AuditRecord: {}", record);
	}

	private void find_all_by_appId_and_between_time_interval() {
		final long begin = 0L;
		final long end = new Date().getTime();
		logger.info("Querying from app {}, between {} and {}..", APP1,
		        begin + 1, end);
		for (AuditRecord record : arDao.find_all_by_appId_and_between_time_interval(APP1,
		        begin + 1, end))
			logger.info("AuditRecord: {}", record);
	}

}