package trinisoftinc.json.me.util;

import trinisoftinc.json.me.MyJSONException;
import trinisoftinc.json.me.MyJSONObject;
import trinisoftinc.json.me.MyJSONTokener;

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
 * The MyXMLTokener extends the MyJSONTokener to provide additional methods
 * for the parsing of MyXML texts.
 * @author JSON.org
 * @version 2
 */
public class MyXMLTokener extends MyJSONTokener {


   /** The table of entity values. It initially contains Character values for
    * amp, apos, gt, lt, quot.
    */
   public static final java.util.Hashtable entity;

   static {
       entity = new java.util.Hashtable(8);
       entity.put("amp",  MyXML.AMP);
       entity.put("apos", MyXML.APOS);
       entity.put("gt",   MyXML.GT);
       entity.put("lt",   MyXML.LT);
       entity.put("quot", MyXML.QUOT);
   }

    /**
     * Construct an MyXMLTokener from a string.
     * @param s A source string.
     */
    public MyXMLTokener(String s) {
        super(s);
    }

    /**
     * Get the text in the CDATA block.
     * @return The string up to the <code>]]&gt;</code>.
     * @throws MyJSONException If the <code>]]&gt;</code> is not found.
     */
    public String nextCDATA() throws MyJSONException {
        char         c;
        int          i;
        StringBuffer sb = new StringBuffer();
        for (;;) {
            c = next();
            if (c == 0) {
                throw syntaxError("Unclosed CDATA.");
            }
            sb.append(c);
            i = sb.length() - 3;
            if (i >= 0 && sb.charAt(i) == ']' &&
                          sb.charAt(i + 1) == ']' && sb.charAt(i + 2) == '>') {
                sb.setLength(i);
                return sb.toString();
            }
        }
    }


    /**
     * Get the next MyXML outer token, trimming whitespace. There are two kinds
     * of tokens: the '<' character which begins a markup tag, and the content
     * text between markup tags.
     *
     * @return  A string, or a '<' Character, or null if there is no more
     * source text.
     * @throws MyJSONException
     */
    public Object nextContent() throws MyJSONException {
        char         c;
        StringBuffer sb;
        do {
            c = next();
        } while (isWhitespace(c));
        if (c == 0) {
            return null;
        }
        if (c == '<') {
            return MyXML.LT;
        }
        sb = new StringBuffer();
        for (;;) {
            if (c == '<' || c == 0) {
                back();
                return sb.toString().trim();
            }
            if (c == '&') {
                sb.append(nextEntity(c));
            } else {
                sb.append(c);
            }
            c = next();
        }
    }


    /**
     * Return the next entity. These entities are translated to Characters:
     *     <code>&amp;  &apos;  &gt;  &lt;  &quot;</code>.
     * @param a An ampersand character.
     * @return  A Character or an entity String if the entity is not recognized.
     * @throws MyJSONException If missing ';' in MyXML entity.
     */
    public Object nextEntity(char a) throws MyJSONException {
        StringBuffer sb = new StringBuffer();
        for (;;) {
            char c = next();
            if (isLetterOrDigit(c) || c == '#') {
                sb.append(Character.toLowerCase(c));
            } else if (c == ';') {
                break;
            } else {
                throw syntaxError("Missing ';' in XML entity: &" + sb);
            }
        }
        String s = sb.toString();
        Object e = entity.get(s);
        return e != null ? e : a + s + ";";
    }


    /**
     * Returns the next MyXML meta token. This is used for skipping over <!...>
     * and <?...?> structures.
     * @return Syntax characters (<code>< > / = ! ?</code>) are returned as
     *  Character, and strings and names are returned as Boolean. We don't care
     *  what the values actually are.
     * @throws MyJSONException If a string is not properly closed or if the MyXML
     *  is badly structured.
     */
    public Object nextMeta() throws MyJSONException {
        char c;
        char q;
        do {
            c = next();
        } while (isWhitespace(c));
        switch (c) {
        case 0:
            throw syntaxError("Misshaped meta tag.");
        case '<':
            return MyXML.LT;
        case '>':
            return MyXML.GT;
        case '/':
            return MyXML.SLASH;
        case '=':
            return MyXML.EQ;
        case '!':
            return MyXML.BANG;
        case '?':
            return MyXML.QUEST;
        case '"':
        case '\'':
            q = c;
            for (;;) {
                c = next();
                if (c == 0) {
                    throw syntaxError("Unterminated string.");
                }
                if (c == q) {
//#if CLDC!="1.0"
                    return Boolean.TRUE;
//#else
//#                     return MyJSONObject.TRUE;
//#endif
                }
            }
        default:
            for (;;) {
                c = next();
                if (isWhitespace(c)) {
//#if CLDC!="1.0"
                    return Boolean.TRUE;
//#else
//#                     return MyJSONObject.TRUE;
//#endif
                }
                switch (c) {
                case 0:
                case '<':
                case '>':
                case '/':
                case '=':
                case '!':
                case '?':
                case '"':
                case '\'':
                    back();
//#if CLDC!="1.0"
                    return Boolean.TRUE;
//#else
//#                     return MyJSONObject.TRUE;
//#endif
                }
            }
        }
    }


    /**
     * Get the next MyXML Token. These tokens are found inside of angle
     * brackets. It may be one of these characters: <code>/ > = ! ?</code> or it
     * may be a string wrapped in single quotes or double quotes, or it may be a
     * name.
     * @return a String or a Character.
     * @throws MyJSONException If the MyXML is not well formed.
     */
    public Object nextToken() throws MyJSONException {
        char c;
        char q;
        StringBuffer sb;
        do {
            c = next();
        } while (isWhitespace(c));
        switch (c) {
        case 0:
            throw syntaxError("Misshaped element.");
        case '<':
            throw syntaxError("Misplaced '<'.");
        case '>':
            return MyXML.GT;
        case '/':
            return MyXML.SLASH;
        case '=':
            return MyXML.EQ;
        case '!':
            return MyXML.BANG;
        case '?':
            return MyXML.QUEST;

// Quoted string

        case '"':
        case '\'':
            q = c;
            sb = new StringBuffer();
            for (;;) {
                c = next();
                if (c == 0) {
                    throw syntaxError("Unterminated string.");
                }
                if (c == q) {
                    return sb.toString();
                }
                if (c == '&') {
                    sb.append(nextEntity(c));
                } else {
                    sb.append(c);
                }
            }
        default:

// Name

            sb = new StringBuffer();
            for (;;) {
                sb.append(c);
                c = next();
                if (isWhitespace(c)) {
                    return sb.toString();
                }
                switch (c) {
                case 0:
                case '>':
                case '/':
                case '=':
                case '!':
                case '?':
                case '[':
                case ']':
                    back();
                    return sb.toString();
                case '<':
                case '"':
                case '\'':
                    throw syntaxError("Bad character in a name.");
                }
            }
        }
    }
    

    private static boolean isWhitespace(char c) {
        switch (c) {
            case ' ':
            case '\r':
            case '\n':
            case '\t':
                return true;
        }
        return false;
    }
    

    private static boolean isLetterOrDigit(char c) {
        switch (c) {
            case '0':
            case '1':
            case '2':
            case '3':
            case '4':
            case '5':
            case '6':
            case '7':
            case '8':
            case '9':

            case 'a':
            case 'b':
            case 'c':
            case 'd':
            case 'e':
            case 'f':
            case 'g':
            case 'h':
            case 'i':
            case 'j':
            case 'k':
            case 'l':
            case 'm':
            case 'n':
            case 'o':
            case 'p':
            case 'q':
            case 'r':
            case 's':
            case 't':
            case 'u':
            case 'v':
            case 'w':
            case 'x':
            case 'y':
            case 'z':

            case 'A':
            case 'B':
            case 'C':
            case 'D':
            case 'E':
            case 'F':
            case 'G':
            case 'H':
            case 'I':
            case 'J':
            case 'K':
            case 'L':
            case 'M':
            case 'N':
            case 'O':
            case 'P':
            case 'Q':
            case 'R':
            case 'S':
            case 'T':
            case 'U':
            case 'V':
            case 'W':
            case 'X':
            case 'Y':
            case 'Z':
                return true;
        }
        return false;
    }
    
}
