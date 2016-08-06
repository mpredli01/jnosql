/*
 * Licensed to the Apache Software Foundation (ASF) under one
 * or more contributor license agreements. See the NOTICE file
 * distributed with this work for additional information
 * regarding copyright ownership. The ASF licenses this file
 * to you under the Apache License, Version 2.0 (the
 * "License"); you may not use this file except in compliance
 * with the License. You may obtain a copy of the License at
 *
 * http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing,
 * software distributed under the License is distributed on an
 * "AS IS" BASIS, WITHOUT WARRANTIES OR CONDITIONS OF ANY
 * KIND, either express or implied. See the License for the
 * specific language governing permissions and limitations
 * under the License.
 */

package org.jnosql.diana.api.document;


import org.jnosql.diana.api.CloseResource;
import org.jnosql.diana.api.ExecuteAsyncQueryException;
import org.jnosql.diana.api.TTL;

import java.util.List;
import java.util.function.Consumer;
import java.util.stream.StreamSupport;

/**
 * Interface used to interact with the persistence context to {@link DocumentCollectionEntity}
 * The DocumentCollectionManager API is used to create and remove persistent {@link DocumentCollectionEntity} instances,
 * to find entities by their primary key, and to query over entities.
 */
public interface DocumentCollectionManager extends CloseResource {

    /**
     * Saves document collection entity
     *
     * @param entity entity to be saved
     * @return the entity saved
     * @throws NullPointerException when document is null
     */
    DocumentCollectionEntity save(DocumentCollectionEntity entity) throws NullPointerException;

    /**
     * Saves an entity asynchronously
     *
     * @param entity entity to be saved
     * @throws ExecuteAsyncQueryException    when there is a async error
     * @throws UnsupportedOperationException when the database does not have support to save asynchronous
     */
    void saveAsync(DocumentCollectionEntity entity) throws ExecuteAsyncQueryException, UnsupportedOperationException;

    /**
     * Saves document collection entity with time to live
     *
     * @param entity entity to be saved
     * @param ttl    the time to live
     * @return the entity saved
     */
    DocumentCollectionEntity save(DocumentCollectionEntity entity, TTL ttl);

    /**
     * Saves an entity asynchronously with time to live
     *
     * @param entity entity to be saved
     * @param ttl    the time to live
     * @throws ExecuteAsyncQueryException    when there is a async error
     * @throws UnsupportedOperationException when the database does not have support to save asynchronous
     */
    void saveAsync(DocumentCollectionEntity entity, TTL ttl) throws ExecuteAsyncQueryException, UnsupportedOperationException;

    /**
     * Saves documents collection entity, by default it's just run for each saving using
     * {@link DocumentCollectionManager#save(DocumentCollectionEntity)},
     * each NoSQL vendor might replace to a more appropriate one.
     *
     * @param entities entities to be saved
     * @return the entity saved
     * @throws NullPointerException when entities is null
     */
    default void save(Iterable<DocumentCollectionEntity> entities) throws NullPointerException {
        StreamSupport.stream(entities.spliterator(), false).forEach(this::save);
    }

    /**
     * Saves entities asynchronously, by default it's just run for each saving using
     * {@link DocumentCollectionManager#saveAsync(DocumentCollectionEntity)},
     * each NoSQL vendor might replace to a more appropriate one.
     *
     * @param entities entities to be saved
     * @throws ExecuteAsyncQueryException    when there is a async error
     * @throws UnsupportedOperationException when the database does not have support to save asynchronous
     */
    default void saveAsync(Iterable<DocumentCollectionEntity> entities) throws ExecuteAsyncQueryException, UnsupportedOperationException {
        StreamSupport.stream(entities.spliterator(), false).forEach(this::saveAsync);
    }

    /**
     * Saves documents collection entity with time to live, by default it's just run for each saving using
     * {@link DocumentCollectionManager#save(DocumentCollectionEntity, TTL)},
     * each NoSQL vendor might replace to a more appropriate one.
     *
     * @param entities entities to be saved
     * @return the entity saved
     * @throws NullPointerException when entities is null
     */
    default void save(Iterable<DocumentCollectionEntity> entities, TTL ttl) throws NullPointerException {
        StreamSupport.stream(entities.spliterator(), false).forEach(d -> save(d, ttl));
    }

    /**
     * Saves entities asynchronously with time to live, by default it's just run for each saving using
     * {@link DocumentCollectionManager#saveAsync(DocumentCollectionEntity, TTL)},
     * each NoSQL vendor might replace to a more appropriate one.
     *
     * @param entities entities to be saved
     * @throws ExecuteAsyncQueryException    when there is a async error
     * @throws UnsupportedOperationException when the database does not have support to save asynchronous
     */
    default void saveAsync(Iterable<DocumentCollectionEntity> entities, TTL ttl) {
        StreamSupport.stream(entities.spliterator(), false).forEach(d -> saveAsync(d, ttl));
    }

    /**
     * Saves an entity asynchronously
     *
     * @param entity   entity to be saved
     * @param callBack the callback, when the process is finished will call this instance returning
     *                 the saved entity within parameters
     * @throws ExecuteAsyncQueryException    when there is a async error
     * @throws UnsupportedOperationException when the database does not have support to save asynchronous
     */
    void saveAsync(DocumentCollectionEntity entity, Consumer<DocumentCollectionEntity> callBack) throws
            ExecuteAsyncQueryException, UnsupportedOperationException;

    /**
     * Saves an entity asynchronously with time to live
     *
     * @param entity   entity to be saved
     * @param ttl      time to live
     * @param callBack the callback, when the process is finished will call this instance returning
     *                 the saved entity within parameters
     * @throws ExecuteAsyncQueryException    when there is a async error
     * @throws UnsupportedOperationException when the database does not have support to save asynchronous
     */
    void saveAsync(DocumentCollectionEntity entity, TTL ttl, Consumer<DocumentCollectionEntity> callBack) throws
            ExecuteAsyncQueryException, UnsupportedOperationException;

    /**
     * Updates a entity
     *
     * @param entity entity to be updated
     * @return the entity updated
     */
    DocumentCollectionEntity update(DocumentCollectionEntity entity);

    /**
     * Updates an entity asynchronously
     *
     * @param entity entity to be updated
     * @throws ExecuteAsyncQueryException    when there is a async error
     * @throws UnsupportedOperationException when the database does not have support to save asynchronous
     */
    void updateAsync(DocumentCollectionEntity entity) throws ExecuteAsyncQueryException, UnsupportedOperationException;

    /**
     * Updates an entity asynchronously
     *
     * @param entity   entity to be updated
     * @param callBack the callback, when the process is finished will call this instance returning
     *                 the updated entity within parametersa
     * @throws ExecuteAsyncQueryException    when there is a async error
     * @throws UnsupportedOperationException when the database does not have support to save asynchronous
     */
    void updateAsync(DocumentCollectionEntity entity, Consumer<DocumentCollectionEntity> callBack) throws
            ExecuteAsyncQueryException, UnsupportedOperationException;

    /**
     * Deletes an entity
     *
     * @param query query to delete an entity
     */
    void delete(DocumentQuery query);

    /**
     * Deletes an entity asynchronously
     *
     * @param query query to delete an entity
     * @throws ExecuteAsyncQueryException    when there is a async error
     * @throws UnsupportedOperationException when the database does not have support to save asynchronous
     */
    void deleteAsync(DocumentQuery query) throws ExecuteAsyncQueryException, UnsupportedOperationException;

    /**
     * Deletes an entity asynchronously
     *
     * @param query    query to delete an entity
     * @param callBack the callback, when the process is finished will call this instance returning
     *                 the null within parameters
     * @throws ExecuteAsyncQueryException    when there is a async error
     * @throws UnsupportedOperationException when the database does not have support to delete asynchronous
     */
    void deleteAsync(DocumentQuery query, Consumer<Void> callBack) throws ExecuteAsyncQueryException,
            UnsupportedOperationException;

    /**
     * Finds {@link DocumentCollectionEntity} from query
     *
     * @param query - query to figure out entities
     * @return entities found by query
     */
    List<DocumentCollectionEntity> find(DocumentQuery query);

    /**
     * Finds {@link DocumentCollectionEntity} from query asynchronously
     *
     * @param query    query to find entities
     * @param callBack the callback, when the process is finished will call this instance returning
     *                 the result of query within parameters
     * @throws ExecuteAsyncQueryException    when there is a async error
     * @throws UnsupportedOperationException when the database does not have support to save asynchronous
     */
    void findAsync(DocumentQuery query, Consumer<List<DocumentCollectionEntity>> callBack) throws
            ExecuteAsyncQueryException, UnsupportedOperationException;

    /**
     * Executes a native query from database, this query may be difference between kind of database.
     *
     * @param query query to be executed
     * @return the result of query
     * @throws UnsupportedOperationException when the database does not have support to run native query
     */
    List<DocumentCollectionEntity> nativeQuery(String query) throws UnsupportedOperationException;

    /**
     * Executes a native query from database, this query may be difference between kind of database and run it
     * asynchronously.
     *
     * @param query    query to be executed
     * @param callBack the callback, when the process is finished will call this instance returning
     *                 the result of query within parameters
     * @throws ExecuteAsyncQueryException    when there is a async error
     * @throws UnsupportedOperationException when the database does not have support to run native query async.
     */
    void nativeQueryAsync(String query, Consumer<List<DocumentCollectionEntity>> callBack) throws
            ExecuteAsyncQueryException, UnsupportedOperationException;

    /**
     * Creates a {@link PreparedStatement} from a native query
     *
     * @param query a query to be executed
     * @return a {@link PreparedStatement}
     * @throws UnsupportedOperationException when there is not support to this resource
     */
    PreparedStatement nativeQueryPrepare(String query) throws UnsupportedOperationException;

}
