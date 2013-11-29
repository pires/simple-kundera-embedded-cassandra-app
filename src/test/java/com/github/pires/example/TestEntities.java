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

import com.github.pires.example.model.User;

/**
 * Test scenario
 */
public class TestEntities {

  private User validUser1;

  private TestEntities() {
    validUser1 = new User("Paulo", "Pires");
  }

  public static TestEntities bootstrap() {
    return new TestEntities();
  }

  public User getValidUser1() {
    return validUser1;
  }

}