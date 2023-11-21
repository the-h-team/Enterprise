/*
 *  Copyright 2023 Sanctum <https://github.com/the-h-team>
 *
 *  Licensed under the Apache License, Version 2.0 (the "License");
 *  you may not use this file except in compliance with the License.
 *  You may obtain a copy of the License at
 *
 *      http://www.apache.org/licenses/LICENSE-2.0
 *
 *  Unless required by applicable law or agreed to in writing, software
 *  distributed under the License is distributed on an "AS IS" BASIS,
 *  WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 *  See the License for the specific language governing permissions and
 *  limitations under the License.
 */
package io.github.sanctum.economy.construct.system;

import org.jetbrains.annotations.ApiStatus;
import org.jetbrains.annotations.NotNull;

import java.util.Queue;
import java.util.concurrent.CompletableFuture;
import java.util.concurrent.ConcurrentLinkedQueue;
import java.util.function.Consumer;

@ApiStatus.Internal
class PendingResultImpl<R extends Result<T>, T> extends PendingResult<R, T> {
    final CompletableFuture<R> result;
    final Queue<Consumer<R>> onceCompleteProcessors = new ConcurrentLinkedQueue<>();
    final Queue<Consumer<T>> ifSuccessfulProcessors = new ConcurrentLinkedQueue<>();
    final Queue<Consumer<AbstractSystemException>> ifFailedProcessors = new ConcurrentLinkedQueue<>();

    PendingResultImpl(CompletableFuture<R> result) {
        this.result = result;
    }

    @Override
    public void onceComplete(@NotNull Consumer<R> processor) {
        onceCompleteProcessors.add(processor);
    }

    @Override
    public void ifSuccessful(@NotNull Consumer<T> consumer) {
        ifSuccessfulProcessors.add(consumer);
    }

    @Override
    public void ifFailed(@NotNull Consumer<AbstractSystemException> consumer) {
        ifFailedProcessors.add(consumer);
    }

    // TODO process queues
}
