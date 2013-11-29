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
package com.github.pires.example.model;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Table;

import com.google.common.base.Objects;
import com.impetus.kundera.index.Index;
import com.impetus.kundera.index.IndexCollection;

@Entity
@Table(name = "users")
@IndexCollection(columns = { @Index(name = "firstName", type = "UTF8Type"),
    @Index(name = "surname", type = "UTF8Type") })
public class User {
  @Id
  @GeneratedValue(strategy = GenerationType.TABLE)
  private Integer id;

  @Column
  private String firstName;

  @Column
  private String surname;

  public User() {
  }

  public User(String firstName, String lastName) {
    this.firstName = firstName;
    this.surname = lastName;
  }

  public Integer getId() {
    return id;
  }

  public void setId(Integer id) {
    this.id = id;
  }

  public String getFirstName() {
    return firstName;
  }

  public void setFirstName(String firstName) {
    this.firstName = firstName;
  }

  public String getSurname() {
    return surname;
  }

  public void setSurname(String surname) {
    this.surname = surname;
  }

  @Override
  public String toString() {
    return Objects.toStringHelper(this).add("id", id)
        .add("firstname", firstName).add("surname", surname).toString();
  }

}