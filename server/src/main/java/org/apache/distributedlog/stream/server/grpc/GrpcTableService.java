/*
 * Licensed to the Apache Software Foundation (ASF) under one
 * or more contributor license agreements.  See the NOTICE file
 * distributed with this work for additional information
 * regarding copyright ownership.  The ASF licenses this file
 * to you under the Apache License, Version 2.0 (the
 * "License"); you may not use this file except in compliance
 * with the License.  You may obtain a copy of the License at
 *
 *     http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */
package org.apache.distributedlog.stream.server.grpc;

import io.grpc.stub.StreamObserver;
import lombok.extern.slf4j.Slf4j;
import org.apache.distributedlog.stream.proto.storage.StorageContainerRequest;
import org.apache.distributedlog.stream.proto.storage.StorageContainerResponse;
import org.apache.distributedlog.stream.proto.storage.TableServiceGrpc.TableServiceImplBase;
import org.apache.distributedlog.stream.server.handler.StorageContainerResponseHandler;
import org.apache.distributedlog.stream.storage.api.RangeStore;

/**
 * The gRPC protocol based k/v service.
 */
@Slf4j
public class GrpcTableService extends TableServiceImplBase {

    private final RangeStore rangeStore;

    GrpcTableService(RangeStore store) {
        this.rangeStore = store;
        log.info("Created Table service");
    }

    @Override
    public void range(StorageContainerRequest request,
                      StreamObserver<StorageContainerResponse> responseObserver) {
        rangeStore.range(request).whenComplete(
            StorageContainerResponseHandler.of(responseObserver));
    }

    @Override
    public void put(StorageContainerRequest request,
                    StreamObserver<StorageContainerResponse> responseObserver) {
        rangeStore.put(request).whenComplete(
            StorageContainerResponseHandler.of(responseObserver));
    }

    @Override
    public void delete(StorageContainerRequest request,
                       StreamObserver<StorageContainerResponse> responseObserver) {
        rangeStore.delete(request).whenComplete(
            StorageContainerResponseHandler.of(responseObserver));
    }

}
