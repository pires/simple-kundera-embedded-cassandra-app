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

import static org.testng.Assert.assertNotEquals;
import static org.testng.Assert.assertNotNull;
import static org.testng.Assert.assertNull;

import org.testng.annotations.Test;

import com.github.pires.example.AbstractTest;

/**
 * This class holds tests for {@link UserDao}.
 */
public class UserDaoTest extends AbstractTest {

  @Test
  public void verify_users_exist_in_cassandra() {
    assertNotEquals(getUserDao().count(), 0);
  }

  @Test
  public void verify_user_does_not_exist_in_cassandra() {
    assertNull(getUserDao().findByFirstName("Vivek"));
  }

  @Test
  public void verify_user_exist_in_cassandra() {
    assertNotNull(getUserDao().findByFirstName("Paulo"));
  }

}