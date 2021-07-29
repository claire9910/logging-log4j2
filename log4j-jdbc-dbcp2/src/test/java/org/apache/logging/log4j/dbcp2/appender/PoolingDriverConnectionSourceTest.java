/*
 * Licensed to the Apache Software Foundation (ASF) under one or more
 * contributor license agreements. See the NOTICE file distributed with
 * this work for additional information regarding copyright ownership.
 * The ASF licenses this file to You under the Apache license, Version 2.0
 * (the "License"); you may not use this file except in compliance with
 * the License. You may obtain a copy of the License at
 *
 *      http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the license for the specific language governing permissions and
 * limitations under the license.
 */

package org.apache.logging.log4j.dbcp2.appender;

import java.sql.Connection;
import java.sql.SQLException;

import org.apache.logging.log4j.core.config.Property;
import org.apache.logging.log4j.jdbc.appender.JdbcH2TestHelper;
import org.junit.Assert;
import org.junit.Test;

public class PoolingDriverConnectionSourceTest {

    private void openAndClose(final PoolingDriverConnectionSource source) throws SQLException {
        try (Connection conn = source.getConnection()) {
            Assert.assertFalse(conn.isClosed());
        } finally {
            source.stop();
        }
    }

    @Test
    public void testH2Properties() throws SQLException {
        final Property[] properties = new Property[] {
                // @formatter:off
                Property.createProperty("username", JdbcH2TestHelper.USER_NAME),
                Property.createProperty("password", JdbcH2TestHelper.PASSWORD),
                // @formatter:on
        };
        // @formatter:off
        final PoolingDriverConnectionSource source = PoolingDriverConnectionSource.newPoolingDriverConnectionSourceBuilder()
            .setConnectionString(JdbcH2TestHelper.CONNECTION_STRING_MEM)
            .setProperties(properties)
            .build();
        openAndClose(source);
    }

    @Test
    public void testH2PropertiesAndPoolName() throws SQLException {
        final Property[] properties = new Property[] {
                // @formatter:off
                Property.createProperty("username", JdbcH2TestHelper.USER_NAME),
                Property.createProperty("password", JdbcH2TestHelper.PASSWORD),
                // @formatter:on
        };
        // @formatter:off
        final PoolingDriverConnectionSource source = PoolingDriverConnectionSource.newPoolingDriverConnectionSourceBuilder()
            .setConnectionString(JdbcH2TestHelper.CONNECTION_STRING_MEM)
            .setProperties(properties)
            .setPoolName("MyPoolName")
            .build();
        // @formatter:on
        openAndClose(source);
    }

    @Test
    public void testH2UserAndPassword() throws SQLException {
        // @formatter:off
        final PoolingDriverConnectionSource source = PoolingDriverConnectionSource.newPoolingDriverConnectionSourceBuilder()
            .setConnectionString(JdbcH2TestHelper.CONNECTION_STRING_MEM)
            .setUserName(JdbcH2TestHelper.USER_NAME.toCharArray())
            .setPassword(JdbcH2TestHelper.PASSWORD.toCharArray())
            .build();
        openAndClose(source);
    }

    @Test
    public void testH2UserPasswordAndPoolName() throws SQLException {
        // @formatter:off
        final PoolingDriverConnectionSource source = PoolingDriverConnectionSource.newPoolingDriverConnectionSourceBuilder()
            .setConnectionString(JdbcH2TestHelper.CONNECTION_STRING_MEM)
            .setUserName(JdbcH2TestHelper.USER_NAME.toCharArray())
            .setPassword(JdbcH2TestHelper.PASSWORD.toCharArray())
            .setPoolName("MyPoolName")
            .build();
        openAndClose(source);
    }

    @Test
    public void testPoolableConnectionFactoryConfig() throws SQLException {
        PoolableConnectionFactoryConfig poolableConnectionFactoryConfig = PoolableConnectionFactoryConfig.newBuilder().setMaxConnLifetimeMillis(30000).build();
        // @formatter:off
        final PoolingDriverConnectionSource source = PoolingDriverConnectionSource.newPoolingDriverConnectionSourceBuilder()
            .setConnectionString(JdbcH2TestHelper.CONNECTION_STRING_MEM)
            .setUserName(JdbcH2TestHelper.USER_NAME.toCharArray())
            .setPassword(JdbcH2TestHelper.PASSWORD.toCharArray())
            .setPoolName("MyPoolName")
            .setPoolableConnectionFactoryConfig(poolableConnectionFactoryConfig)
            .build();
        // @formatter:on
        openAndClose(source);
    }
}
