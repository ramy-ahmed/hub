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
package com.aintshy.pgsql;

import com.aintshy.api.Human;
import com.aintshy.api.Messages;
import com.aintshy.api.Talk;
import com.google.common.base.Joiner;
import com.jcabi.aspects.Immutable;
import com.jcabi.jdbc.JdbcSession;
import com.jcabi.jdbc.SingleOutcome;
import java.io.IOException;
import java.sql.SQLException;
import java.util.Locale;
import lombok.EqualsAndHashCode;
import lombok.ToString;

/**
 * Talk in PostgreSQL.
 *
 * @author Yegor Bugayenko (yegor@tpc2.com)
 * @version $Id$
 * @since 0.1
 */
@Immutable
@ToString(of = "num")
@EqualsAndHashCode(of = { "src", "num" })
final class PgTalk implements Talk {

    /**
     * Data source.
     */
    private final transient PgSource src;

    /**
     * Number of it.
     */
    private final transient long num;

    /**
     * Ctor.
     * @param source Data source
     * @param number Number
     */
    PgTalk(final PgSource source, final long number) {
        this.src = source;
        this.num = number;
    }

    @Override
    public long number() {
        return this.num;
    }

    @Override
    public Human asker() throws IOException {
        try {
            return new PgHuman(
                this.src,
                new JdbcSession(this.src.get())
                    // @checkstyle LineLength (1 line)
                    .sql("SELECT asker FROM question JOIN talk ON question.id=talk.question AND talk.id=?")
                    .set(this.num)
                    .select(new SingleOutcome<Long>(Long.class))
            );
        } catch (final SQLException ex) {
            throw new IOException(ex);
        }
    }

    @Override
    public Human responder() throws IOException {
        try {
            return new PgHuman(
                this.src,
                new JdbcSession(this.src.get())
                    .sql("SELECT responder FROM talk WHERE id=?")
                    .set(this.num)
                    .select(new SingleOutcome<Long>(Long.class))
            );
        } catch (final SQLException ex) {
            throw new IOException(ex);
        }
    }

    @Override
    public String question() throws IOException {
        try {
            return new JdbcSession(this.src.get())
                // @checkstyle LineLength (1 line)
                .sql("SELECT text FROM question JOIN talk ON talk.question=question.id WHERE talk.id=?")
                .set(this.num)
                .select(new SingleOutcome<String>(String.class));
        } catch (final SQLException ex) {
            throw new IOException(ex);
        }
    }

    @Override
    public Locale locale() throws IOException {
        try {
            return new Locale(
                new JdbcSession(this.src.get())
                    .set(this.num)
                    .sql(
                        Joiner.on(' ').join(
                            "SELECT locale FROM question",
                            "JOIN talk ON talk.question=question.id",
                            "WHERE talk.id=?"
                        )
                    )
                    .select(new SingleOutcome<String>(String.class))
            );
        } catch (final SQLException ex) {
            throw new IOException(ex);
        }
    }

    @Override
    public Messages messages() {
        return new PgMessages(this.src, this.num);
    }
}
