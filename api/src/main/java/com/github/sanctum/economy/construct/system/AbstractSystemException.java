/*
 *   Copyright 2021 Sanctum <https://github.com/the-h-team>
 *
 *   Licensed under the Apache License, Version 2.0 (the "License");
 *   you may not use this file except in compliance with the License.
 *   You may obtain a copy of the License at
 *
 *       http://www.apache.org/licenses/LICENSE-2.0
 *
 *   Unless required by applicable law or agreed to in writing, software
 *   distributed under the License is distributed on an "AS IS" BASIS,
 *   WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 *   See the License for the specific language governing permissions and
 *   limitations under the License.
 */
package com.github.sanctum.economy.construct.system;

/**
 * A checked exception base for all system errors.
 *
 * @since 2.0.0
 * @author ms5984
 */
public abstract class AbstractSystemException extends Exception {
    private static final long serialVersionUID = -359957975837575116L;

    /**
     * Constructs an exception with a message.
     *
     * @param message a message
     */
    AbstractSystemException(String message) {
        super(message);
    }

    /**
     * Constructs an exception with a message and cause.
     *
     * @param message a message
     * @param cause a cause throwable
     */
    AbstractSystemException(String message, Throwable cause) {
        super(message, cause);
    }

    /**
     * Constructs an exception with a cause.
     *
     * @param cause a cause throwable
     */
    AbstractSystemException(Throwable cause) {
        super(cause);
    }
}
