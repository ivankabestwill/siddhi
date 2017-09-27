/*
 * Copyright (c) 2015, WSO2 Inc. (http://www.wso2.org) All Rights Reserved.
 *
 * WSO2 Inc. licenses this file to you under the Apache License,
 * Version 2.0 (the "License"); you may not use this file except
 * in compliance with the License.
 * You may obtain a copy of the License at
 *
 *     http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing,
 * software distributed under the License is distributed on an
 * "AS IS" BASIS, WITHOUT WARRANTIES OR CONDITIONS OF ANY
 * KIND, either express or implied. See the License for the
 * specific language governing permissions and limitations
 * under the License.
 */

package org.wso2.siddhi.core.query.input.stream.state.receiver;

import org.wso2.siddhi.core.query.input.StateMultiProcessStreamReceiver;
import org.wso2.siddhi.core.query.input.stream.state.PreStateProcessor;
import org.wso2.siddhi.core.util.statistics.LatencyTracker;

public class PatternMultiProcessStreamReceiver extends StateMultiProcessStreamReceiver {

    public PatternMultiProcessStreamReceiver(String streamId, int processCount, LatencyTracker latencyTracker, String queryName) {
        super(streamId, processCount, latencyTracker, queryName);
        eventSequence = new int[processCount];
        int count = 0;
        for (int i = eventSequence.length - 1; i >= 0; i--) {
            eventSequence[count] = i;
            count++;
        }
    }

    public PatternMultiProcessStreamReceiver clone(String key) {
        return new PatternMultiProcessStreamReceiver(streamId + key, processCount, latencyTracker, queryName);
    }

    protected void stabilizeStates() {
        if (stateProcessorsSize != 0) {
            for (PreStateProcessor preStateProcessor : stateProcessors) {
                preStateProcessor.updateState();
            }
        }
    }
}
