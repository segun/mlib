package trinisoftinc.json.me.util;

import java.util.Enumeration;
import trinisoftinc.json.me.MyJSONArray;
import trinisoftinc.json.me.MyJSONException;
import trinisoftinc.json.me.MyJSONObject;

/*
Copyright (c) 2002 JSON.org

Permission is hereby granted, free of charge, to any person obtaining a copy
of this software and associated documentation files (the "Software"), to deal
in the Software without restriction, including without limitation the rights
to use, copy, modify, merge, publish, distribute, sublicense, and/or sell
copies of the Software, and to permit persons to whom the Software is
furnished to do so, subject to the following conditions:

The above copyright notice and this permission notice shall be included in all
copies or substantial portions of the Software.

The Software shall be used for Good, not Evil.

THE SOFTWARE IS PROVIDED "AS IS", WITHOUT WARRANTY OF ANY KIND, EXPRESS OR
IMPLIED, INCLUDING BUT NOT LIMITED TO THE WARRANTIES OF MERCHANTABILITY,
FITNESS FOR A PARTICULAR PURPOSE AND NONINFRINGEMENT. IN NO EVENT SHALL THE
AUTHORS OR COPYRIGHT HOLDERS BE LIABLE FOR ANY CLAIM, DAMAGES OR OTHER
LIABILITY, WHETHER IN AN ACTION OF CONTRACT, TORT OR OTHERWISE, ARISING FROM,
OUT OF OR IN CONNECTION WITH THE SOFTWARE OR THE USE OR OTHER DEALINGS IN THE
SOFTWARE.
*/

/**
 * This provides static methods to convert an MyXML text into a MyJSONObject,
 * and to covert a MyJSONObject into an MyXML text.
 * @author JSON.org
 * @version 2
 */
public class MyXML {

    /** The Character '&'. */
    public static final Character AMP   = new Character('&');

    /** The Character '''. */
    public static final Character APOS  = new Character('\'');

    /** The Character '!'. */
    public static final Character BANG  = new Character('!');

    /** The Character '='. */
    public static final Character EQ    = new Character('=');

    /** The Character '>'. */
    public static final Character GT    = new Character('>');

    /** The Character '<'. */
    public static final Character LT    = new Character('<');

    /** The Character '?'. */
    public static final Character QUEST = new Character('?');

    /** The Character '"'. */
    public static final Character QUOT  = new Character('"');

    /** The Character '/'. */
    public static final Character SLASH = new Character('/');

    /**
     * Replace special characters with MyXML escapes:
     * <pre>
     * &amp; <small>(ampersand)</small> is replaced by &amp;amp;
     * &lt; <small>(less than)</small> is replaced by &amp;lt;
     * &gt; <small>(greater than)</small> is replaced by &amp;gt;
     * &quot; <small>(double quote)</small> is replaced by &amp;quot;
     * </pre>
     * @param string The string to be escaped.
     * @return The escaped string.
     */
    public static String escape(String string) {
        StringBuffer sb = new StringBuffer();
        for (int i = 0, len = string.length(); i < len; i++) {
            char c = string.charAt(i);
            switch (c) {
            case '&':
                sb.append("&amp;");
                break;
            case '<':
                sb.append("&lt;");
                break;
            case '>':
                sb.append("&gt;");
                break;
            case '"':
                sb.append("&quot;");
                break;
            default:
                sb.append(c);
            }
        }
        return sb.toString();
    }

    /**
     * Scan the content following the named tag, attaching it to the context.
     * @param x       The MyXMLTokener containing the source string.
     * @param context The MyJSONObject that will include the new material.
     * @param name    The tag name.
     * @return true if the close tag is processed.
     * @throws MyJSONException
     */
    private static boolean parse(MyXMLTokener x, MyJSONObject context,
                                 String name) throws MyJSONException {
        char       c;
        int        i;
        String     n;
        MyJSONObject o = null;
        String     s;
        Object     t;

// Test for and skip past these forms:
//      <!-- ... -->
//      <!   ...   >
//      <![  ... ]]>
//      <?   ...  ?>
// Report errors for these forms:
//      <>
//      <=
//      <<

        t = x.nextToken();

// <!

        if (t == BANG) {
            c = x.next();
            if (c == '-') {
                if (x.next() == '-') {
                    x.skipPast("-->");
                    return false;
                }
                x.back();
            } else if (c == '[') {
                t = x.nextToken();
                if (t.equals("CDATA")) {
                    if (x.next() == '[') {
                        s = x.nextCDATA();
                        if (s.length() > 0) {
                            context.accumulate("content", s);
                        }
                        return false;
                    }
                }
                throw x.syntaxError("Expected 'CDATA['");
            }
            i = 1;
            do {
                t = x.nextMeta();
                if (t == null) {
                    throw x.syntaxError("Missing '>' after '<!'.");
                } else if (t == LT) {
                    i += 1;
                } else if (t == GT) {
                    i -= 1;
                }
            } while (i > 0);
            return false;
        } else if (t == QUEST) {

// <?

            x.skipPast("?>");
            return false;
        } else if (t == SLASH) {

// Close tag </

            if (name == null || !x.nextToken().equals(name)) {
                throw x.syntaxError("Mismatched close tag");
            }
            if (x.nextToken() != GT) {
                throw x.syntaxError("Misshaped close tag");
            }
            return true;

        } else if (t instanceof Character) {
            throw x.syntaxError("Misshaped tag");

// Open tag <

        } else {
            n = (String)t;
            t = null;
            o = new MyJSONObject();
            for (;;) {
                if (t == null) {
                    t = x.nextToken();
                }

// attribute = value

                if (t instanceof String) {
                    s = (String)t;
                    t = x.nextToken();
                    if (t == EQ) {
                        t = x.nextToken();
                        if (!(t instanceof String)) {
                            throw x.syntaxError("Missing value");
                        }
                        o.accumulate(s, t);
                        t = null;
                    } else {
                        o.accumulate(s, "");
                    }

// Empty tag <.../>

                } else if (t == SLASH) {
                    if (x.nextToken() != GT) {
                        throw x.syntaxError("Misshaped tag");
                    }
                    context.accumulate(n, o);
                    return false;

// Content, between <...> and </...>

                } else if (t == GT) {
                    for (;;) {
                        t = x.nextContent();
                        if (t == null) {
                            if (name != null) {
                                throw x.syntaxError("Unclosed tag " + name);
                            }
                            return false;
                        } else if (t instanceof String) {
                            s = (String)t;
                            if (s.length() > 0) {
                                o.accumulate("content", s);
                            }

// Nested element

                        } else if (t == LT) {
                            if (parse(x, o, n)) {
                                if (o.length() == 0) {
                                    context.accumulate(n, "");
                                } else if (o.length() == 1 &&
                                       o.opt("content") != null) {
                                    context.accumulate(n, o.opt("content"));
                                } else {
                                    context.accumulate(n, o);
                                }
                                return false;
                            }
                        }
                    }
                } else {
                    throw x.syntaxError("Misshaped tag");
                }
            }
        }
    }


    /**
     * Convert a well-formed (but not necessarily valid) MyXML string into a
     * MyJSONObject. Some information may be lost in this transformation
     * because JSON is a data format and MyXML is a document format. MyXML uses
     * elements, attributes, and content text, while JSON uses unordered
     * collections of name/value pairs and arrays of values. JSON does not
     * does not like to distinguish between elements and attributes.
     * Sequences of similar elements are represented as JSONArrays. Content
     * text may be placed in a "content" member. Comments, prologs, DTDs, and
     * <code>&lt;[ [ ]]></code> are ignored.
     * @param string The source string.
     * @return A MyJSONObject containing the structured data from the MyXML string.
     * @throws MyJSONException
     */
    public static MyJSONObject toJSONObject(String string) throws MyJSONException {
        MyJSONObject o = new MyJSONObject();
        MyXMLTokener x = new MyXMLTokener(string);
        while (x.more()) {
            x.skipPast("<");
            parse(x, o, null);
        }
        return o;
    }


    /**
     * Convert a MyJSONObject into a well-formed, element-normal MyXML string.
     * @param o A MyJSONObject.
     * @return  A string.
     * @throws  MyJSONException
     */
    public static String toString(Object o) throws MyJSONException {
        return toString(o, null);
    }


    /**
     * Convert a MyJSONObject into a well-formed, element-normal MyXML string.
     * @param o A MyJSONObject.
     * @param tagName The optional name of the enclosing tag.
     * @return A string.
     * @throws MyJSONException
     */
    public static String toString(Object o, String tagName)
            throws MyJSONException {
        StringBuffer b = new StringBuffer();
        int          i;
        MyJSONArray    ja;
        MyJSONObject   jo;
        String       k;
        Enumeration  keys;
        int          len;
        String       s;
        Object       v;
        if (o instanceof MyJSONObject) {

// Emit <tagName>

            if (tagName != null) {
                b.append('<');
                b.append(tagName);
                b.append('>');
            }

// Loop thru the keys.

            jo = (MyJSONObject)o;
            keys = jo.keys();
            while (keys.hasMoreElements()) {
                k = keys.nextElement().toString();
                v = jo.get(k);
                if (v instanceof String) {
                    s = (String)v;
                } else {
                    s = null;
                }

// Emit content in body

                if (k.equals("content")) {
                    if (v instanceof MyJSONArray) {
                        ja = (MyJSONArray)v;
                        len = ja.length();
                        for (i = 0; i < len; i += 1) {
                            if (i > 0) {
                                b.append('\n');
                            }
                            b.append(escape(ja.get(i).toString()));
                        }
                    } else {
                        b.append(escape(v.toString()));
                    }

// Emit an array of similar keys

                } else if (v instanceof MyJSONArray) {
                    ja = (MyJSONArray)v;
                    len = ja.length();
                    for (i = 0; i < len; i += 1) {
                        b.append(toString(ja.get(i), k));
                    }
                } else if (v.equals("")) {
                    b.append('<');
                    b.append(k);
                    b.append("/>");

// Emit a new tag <k>

                } else {
                    b.append(toString(v, k));
                }
            }
            if (tagName != null) {

// Emit the </tagname> close tag

                b.append("</");
                b.append(tagName);
                b.append('>');
            }
            return b.toString();

// MyXML does not have good support for arrays. If an array appears in a place
// where MyXML is lacking, synthesize an <array> element.

        } else if (o instanceof MyJSONArray) {
            ja = (MyJSONArray)o;
            len = ja.length();
            for (i = 0; i < len; ++i) {
                b.append(toString(
                    ja.opt(i), (tagName == null) ? "array" : tagName));
            }
            return b.toString();
        } else {
            s = (o == null) ? "null" : escape(o.toString());
            return (tagName == null) ? "\"" + s + "\"" :
                (s.length() == 0) ? "<" + tagName + "/>" :
                "<" + tagName + ">" + s + "</" + tagName + ">";
        }
    }
}