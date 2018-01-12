/*
 * Copyright 2017 original authors
 * 
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 * 
 * http://www.apache.org/licenses/LICENSE-2.0
 * 
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License. 
 */
package org.particleframework.docs.server.body

import okhttp3.MediaType
import okhttp3.OkHttpClient
import okhttp3.Request
import okhttp3.RequestBody
import okhttp3.Response
import org.particleframework.context.ApplicationContext
import org.particleframework.runtime.server.EmbeddedServer
import spock.lang.AutoCleanup
import spock.lang.Shared
import spock.lang.Specification

/**
 * @author Graeme Rocher
 * @since 1.0
 */
class MessageControllerSpec extends Specification {

    @Shared @AutoCleanup EmbeddedServer embeddedServer =
            ApplicationContext.run(EmbeddedServer) // <1>

    void "test echo response"() {
        given:
        OkHttpClient client = new OkHttpClient()
        String body = "My Text"
        Request.Builder request = new Request.Builder()
                .url(new URL(embeddedServer.getURL(), "/receive/echo"))
                .post(RequestBody.create(MediaType.parse("text/plain"), body))// <2>
        Response response = client.newCall(request.build()).execute()

        expect:
        response.body().string() == body // <2>
    }


    void "test echo reactive response"() {
        given:
        OkHttpClient client = new OkHttpClient()
        String body = "My Text"
        Request.Builder request = new Request.Builder()
                .url(new URL(embeddedServer.getURL(), "/receive/echoFlow"))
                .post(RequestBody.create(MediaType.parse("text/plain"), body))// <2>
        Response response = client.newCall(request.build()).execute()

        expect:
        response.body().string() == body // <2>
    }
}