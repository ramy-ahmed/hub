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

import com.aintshy.api.Base;
import com.aintshy.api.Human;
import com.aintshy.api.Talk;
import org.hamcrest.MatcherAssert;
import org.hamcrest.Matchers;
import org.junit.Test;

/**
 * Integration case for {@link PgHuman}.
 *
 * @author Yegor Bugayenko (yegor@tpc2.com)
 * @version $Id$
 * @since 0.1
 */
public final class PgHumanITCase {

    /**
     * PgHuman can ask a question.
     * @throws Exception If fails
     */
    @Test
    public void asksQuestion() throws Exception {
        final Base base = new PgBase();
        final Human human = base.register("hs@aintshy.com", "-9w8skkha");
        human.ask("how does it work for you?");
    }

    /**
     * PgHuman can fetch next talk.
     * @throws Exception If fails
     */
    @Test
    public void fetchesNextTalk() throws Exception {
        final Base base = new PgBase();
        final Human friend = base.register("f8@aintshy.com", "--Iokha");
        friend.ask("how are you doing this?");
        final Human human = base.register("oi@aintshy.com", "-9w8(8s");
        final Talk talk = human.next();
        MatcherAssert.assertThat(
            talk,
            Matchers.not(Matchers.equalTo(Talk.EMPTY))
        );
        MatcherAssert.assertThat(
            talk.asker(),
            Matchers.not(Matchers.equalTo(human))
        );
        MatcherAssert.assertThat(
            human.next(),
            Matchers.equalTo(talk)
        );
    }

}
