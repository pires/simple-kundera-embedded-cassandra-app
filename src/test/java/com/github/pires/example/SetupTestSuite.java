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

import java.io.File;
import java.io.IOException;
import java.net.URI;

import org.apache.cassandra.service.EmbeddedCassandraService;
import org.glassfish.embeddable.CommandResult;
import org.glassfish.embeddable.GlassFish;
import org.glassfish.embeddable.GlassFishException;
import org.glassfish.embeddable.GlassFishProperties;
import org.glassfish.embeddable.GlassFishRuntime;
import org.glassfish.embeddable.archive.ScatteredArchive;
import org.slf4j.LoggerFactory;
import org.testng.annotations.AfterSuite;
import org.testng.annotations.BeforeSuite;

/**
 * Testing environment bootstap.
 */
public class SetupTestSuite {

  private static final org.slf4j.Logger logger = LoggerFactory
      .getLogger(SetupTestSuite.class);

  public final static int SERVER_PORT = 8181;
  private GlassFish gfServer;

  /**
   * This is meant to run before all tests are performed.
   * 
   * @throws Throwable
   */
  @BeforeSuite
  public final void setUp() {
    try {
      // set-up embedded Cassandra instance
      logger.info("Starting embedded Cassandra instance..");
      new EmbeddedCassandraService().start();
      logger.info("Cassandra started.");

      // set-up embedded Glassfish instance
      GlassFishProperties gfProperties = new GlassFishProperties();
      gfProperties.setPort("http-listener", SERVER_PORT);
      gfServer = GlassFishRuntime.bootstrap().newGlassFish(gfProperties);
      gfServer.start();

      // create default JDBC connection pool
      CommandResult res;
      // create a JDBC connection pool
      String poolProperties = "user=sa:password=test:url=jdbc\\:derby\\:memory\\:db;create\\=true";
      res = gfServer.getCommandRunner().run("create-jdbc-connection-pool",
          "--ping", "--restype=java.sql.Driver", "--driverclassname",
          "org.apache.derby.jdbc.EmbeddedDriver", "--property", poolProperties,
          "MyPool");
      logger.info(res.getOutput());

      // create a JDBC resource
      res = gfServer.getCommandRunner().run("create-jdbc-resource",
          "--connectionpoolid", "MyPool", "jdbc/testDS");
      logger.info(res.getOutput());

      // create WAR
      ScatteredArchive archive = new ScatteredArchive("test",
          ScatteredArchive.Type.WAR);
      /*
       * by adding individual files and folders, we keep control of what is and
       * what isn't deployed for testing. for instance, we want to ignore EJB
       * timers, as they blow up :-/
       */
      archive.addClassPath(new File("target", "classes"));
      archive.addClassPath(new File("target", "test-classes"));

      // deploy the scattered web archive.
      deploy(archive.toURI());
    } catch (Exception e) {
      logger.error(
          "There was an error while setting up the testing environment.", e);
    }
  }

  /**
   * @throws GlassFishException
   *           This is meant to run after all tests are performed.
   * 
   * @throws
   */
  @AfterSuite
  public final void tearDown() {
    try {
      gfServer.stop();
      gfServer.dispose();
    } catch (Exception e) {
      logger.error("There was an error while shutting down embedded Glassfish");
    }
  }

  /**
   * Persists test scenario.
   * 
   * @throws GlassFishException
   * @throws IOException
   */
  private final void deploy(URI uri) throws GlassFishException, IOException {
    logger.info("Deploying webapp..");
    gfServer.getDeployer().deploy(uri, "--contextroot=test");

    logger.info("Persisting test scenario..");
    // persist users
    EJBLocator.getUserDao().create(EJBLocator.getEntities().getValidUser1());
    logger.info("Test scenario persisted successfully.");
  }

}