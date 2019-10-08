/*
 * Licensed to the Apache Software Foundation (ASF) under one or more
 * contributor license agreements.  See the NOTICE file distributed with
 * this work for additional information regarding copyright ownership.
 * The ASF licenses this file to You under the Apache License, Version 2.0
 * (the "License"); you may not use this file except in compliance with
 * the License.  You may obtain a copy of the License at
 *
 *     http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */
package org.apache.dubbo.remoting.exchange;

import org.apache.dubbo.remoting.Channel;
import org.apache.dubbo.remoting.RemotingException;

import java.util.concurrent.CompletableFuture;

/**
 * 信息交换接口
 */
public interface ExchangeChannel extends Channel {

    /**
     * 发送请求
     * @param request
     * @return
     * @throws RemotingException
     */
    CompletableFuture<Object> request(Object request) throws RemotingException;

    /**
     * 发送请求
     * @param request
     * @param timeout
     * @return
     * @throws RemotingException
     */
    CompletableFuture<Object> request(Object request, int timeout) throws RemotingException;

    /**
     * 获得信息交换处理器
     * @return
     */
    ExchangeHandler getExchangeHandler();

    /**
     * 优雅关闭
     * @param timeout
     */
    @Override
    void close(int timeout);
}
