/**
 * Copyright (c) 2014, Aintshy.com
 * All rights reserved.
 *
 * Redistribution and use in source or binary forms, with or without
 * modification, are NOT permitted.
 *
 * THIS SOFTWARE IS PROVIDED BY THE COPYRIGHT HOLDERS AND CONTRIBUTORS
 * "AS IS" AND ANY EXPRESS OR IMPLIED WARRANTIES, INCLUDING, BUT
 * NOT LIMITED TO, THE IMPLIED WARRANTIES OF MERCHANTABILITY AND
 * FITNESS FOR A PARTICULAR PURPOSE ARE DISCLAIMED. IN NO EVENT SHALL
 * THE COPYRIGHT HOLDER OR CONTRIBUTORS BE LIABLE FOR ANY DIRECT,
 * INDIRECT, INCIDENTAL, SPECIAL, EXEMPLARY, OR CONSEQUENTIAL DAMAGES
 * (INCLUDING, BUT NOT LIMITED TO, PROCUREMENT OF SUBSTITUTE GOODS OR
 * SERVICES; LOSS OF USE, DATA, OR PROFITS; OR BUSINESS INTERRUPTION)
 * HOWEVER CAUSED AND ON ANY THEORY OF LIABILITY, WHETHER IN CONTRACT,
 * STRICT LIABILITY, OR TORT (INCLUDING NEGLIGENCE OR OTHERWISE)
 * ARISING IN ANY WAY OUT OF THE USE OF THIS SOFTWARE, EVEN IF ADVISED
 * OF THE POSSIBILITY OF SUCH DAMAGE.
 */
package com.aintshy.api;

import com.jcabi.aspects.Immutable;
import com.jcabi.urn.URN;
import java.io.IOException;

/**
 * Base of the entire system.
 *
 * @author Yegor Bugayenko (yegor@tpc2.com)
 * @version $Id$
 * @since 0.1
 */
@Immutable
public interface Base {

    /**
     * Register user by email (throws
     * {@link com.aintshy.api.Base.InvalidPasswordException} if password
     * is not correct).
     *
     * <p>Throws {@link com.aintshy.api.Base.InvalidEmailFormatException}
     * if email is in a wrong format.
     *
     * <p>Throws {@link com.aintshy.api.Base.InvalidPasswordFormatException}
     * if password is not good enough.
     *
     * @param email Email
     * @param password Password
     * @param pocket Pocket where to put the code
     * @return Human
     * @throws IOException If fails
     */
    Human register(String email, String password, Pocket pocket)
        throws IOException;

    /**
     * Remind.
     * @param email Email
     * @param pocket Pocket where to put the code
     * @throws IOException If fails
     */
    void remind(String email, Pocket pocket) throws IOException;

    /**
     * Get human by URN (throws
     * {@link com.aintshy.api.Base.HumanNotFoundException} if this user
     * is absent).
     *
     * @param urn His URN
     * @return Human
     * @throws IOException If fails
     */
    Human human(URN urn) throws IOException;

    /**
     * Password is not correct.
     */
    final class InvalidPasswordException extends RuntimeException {
        /**
         * Serialization marker.
         */
        private static final long serialVersionUID = 305929936831895556L;
        /**
         * Ctor.
         * @param cause Cause
         */
        public InvalidPasswordException(final String cause) {
            super(cause);
        }
    }

    /**
     * User not found.
     */
    final class HumanNotFoundException extends RuntimeException {
        /**
         * Serialization marker.
         */
        private static final long serialVersionUID = 305929936831895556L;
        /**
         * Ctor.
         * @param cause Cause
         */
        public HumanNotFoundException(final String cause) {
            super(cause);
        }
    }

    /**
     * Invalid email format.
     */
    final class InvalidEmailFormatException extends RuntimeException {
        /**
         * Serialization marker.
         */
        private static final long serialVersionUID = 305929936831895556L;
        /**
         * Ctor.
         * @param cause Cause
         */
        public InvalidEmailFormatException(final String cause) {
            super(cause);
        }
    }

    /**
     * Invalid email format.
     */
    final class InvalidPasswordFormatException extends RuntimeException {
        /**
         * Serialization marker.
         */
        private static final long serialVersionUID = 305929936831895556L;
        /**
         * Ctor.
         * @param cause Cause
         */
        public InvalidPasswordFormatException(final String cause) {
            super(cause);
        }
    }

}
