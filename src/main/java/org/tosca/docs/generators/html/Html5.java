package org.tosca.docs.generators.html;

import com.googlecode.jatl.Html;

import java.io.Writer;

public class Html5 extends Html {

    public Html5(Writer writer) {
        super(writer);
    }

    /**
     * Starts the &lt;td&gt; tag.
     * []
     * <p>
     * Tag Closing Policy: {@link TagClosingPolicy#PAIR}
     *
     * @return this, never <code>null</code>
     */
    @Override
    public Html td() {
        // Override the default SELF closing policy as self closing tags are not supported by some browsers
        return start("td", TagClosingPolicy.PAIR);
    }
}
