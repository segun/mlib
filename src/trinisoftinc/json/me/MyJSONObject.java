package trinisoftinc.json.me;

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

import java.io.IOException;
import java.io.Writer;
import java.util.Enumeration;
import java.util.Hashtable;
import java.util.Vector;

/**
 * A MyJSONObject is an unordered collection of name/value pairs. Its
 * external form is a string wrapped in curly braces with colons between the
 * names and values, and commas between the values and names. The internal form
 * is an object having <code>get</code> and <code>opt</code> methods for
 * accessing the values by name, and <code>put</code> methods for adding or
 * replacing values by name. The values can be any of these types:
 * <code>Boolean</code>, <code>MyJSONArray</code>, <code>MyJSONObject</code>,
 * <code>Number</code>, <code>String</code>, or the <code>MyJSONObject.NULL</code>
 * object. A MyJSONObject constructor can be used to convert an external form
 * JSON text into an internal form whose values can be retrieved with the
 * <code>get</code> and <code>opt</code> methods, or to convert values into a
 * JSON text using the <code>put</code> and <code>toString</code> methods.
 * A <code>get</code> method returns a value if one can be found, and throws an
 * exception if one cannot be found. An <code>opt</code> method returns a
 * default value instead of throwing an exception, and so is useful for
 * obtaining optional values.
 * <p>
 * The generic <code>get()</code> and <code>opt()</code> methods return an
 * object, which you can cast or query for type. There are also typed
 * <code>get</code> and <code>opt</code> methods that do type checking and type
 * coersion for you.
 * <p>
 * The <code>put</code> methods adds values to an object. For example, <pre>
 *     myString = new MyJSONObject().put("JSON", "Hello, World!").toString();</pre>
 * produces the string <code>{"JSON": "Hello, World"}</code>.
 * <p>
 * The texts produced by the <code>toString</code> methods strictly conform to
 * the JSON sysntax rules.
 * The constructors are more forgiving in the texts they will accept:
 * <ul>
 * <li>An extra <code>,</code>&nbsp;<small>(comma)</small> may appear just
 *     before the closing brace.</li>
 * <li>Strings may be quoted with <code>'</code>&nbsp;<small>(single
 *     quote)</small>.</li>
 * <li>Strings do not need to be quoted at all if they do not begin with a quote
 *     or single quote, and if they do not contain leading or trailing spaces,
 *     and if they do not contain any of these characters:
 *     <code>{ } [ ] / \ : , = ; #</code> and if they do not look like numbers
 *     and if they are not the reserved words <code>true</code>,
 *     <code>false</code>, or <code>null</code>.</li>
 * <li>Keys can be followed by <code>=</code> or <code>=></code> as well as
 *     by <code>:</code>.</li>
 * <li>Values can be followed by <code>;</code> <small>(semicolon)</small> as
 *     well as by <code>,</code> <small>(comma)</small>.</li>
 * <li>Numbers may have the <code>0-</code> <small>(octal)</small> or
 *     <code>0x-</code> <small>(hex)</small> prefix.</li>
 * <li>Comments written in the slashshlash, slashstar, and hash conventions
 *     will be ignored.</li>
 * </ul>
 * @author JSON.org
 * @version 2
 */
public class MyJSONObject {
    
     public static final Boolean TRUE = new Boolean(true);
     public static final Boolean FALSE = new Boolean(false);

    /**
     * MyJSONObject.NULL is equivalent to the value that JavaScript calls null,
     * whilst Java's null is equivalent to the value that JavaScript calls
     * undefined.
     */
     private static final class Null {

        /**
         * There is only intended to be a single instance of the NULL object,
         * so the clone method returns itself.
         * @return     NULL.
         */
        protected final Object clone() {
            return this;
        }


        /**
         * A Null object is equal to the null value and to itself.
         * @param object    An object to test for nullness.
         * @return true if the object parameter is the MyJSONObject.NULL object
         *  or null.
         */
        public boolean equals(Object object) {
            return object == null || object == this;
        }


        /**
         * Get the "null" string value.
         * @return The string "null".
         */
        public String toString() {
            return "null";
        }
    }


    /**
     * The hash map where the MyJSONObject's properties are kept.
     */
    private Hashtable myHashMap;


    /**
     * It is sometimes more convenient and less ambiguous to have a
     * <code>NULL</code> object than to use Java's <code>null</code> value.
     * <code>MyJSONObject.NULL.equals(null)</code> returns <code>true</code>.
     * <code>MyJSONObject.NULL.toString()</code> returns <code>"null"</code>.
     */
    public static final Object NULL = new Null();

    /**
     * Construct an empty MyJSONObject.
     */
    public MyJSONObject() {
        this.myHashMap = new Hashtable();
    }


//#ifdef PRODUCER
//#     /**
//#      * Construct a MyJSONObject from a subset of another MyJSONObject.
//#      * An array of strings is used to identify the keys that should be copied.
//#      * Missing keys are ignored.
//#      * @param jo A MyJSONObject.
//#      * @param sa An array of strings.
//#      * @exception MyJSONException If a value is a non-finite number.
//#      */
//#     public MyJSONObject(MyJSONObject jo, String[] sa) throws MyJSONException {
//#         this();
//#         for (int i = 0; i < sa.length; i += 1) {
//#             putOpt(sa[i], jo.opt(sa[i]));
//#         }
//#     }
//#endif

    /**
     * Construct a MyJSONObject from a MyJSONTokener.
     * @param x A MyJSONTokener object containing the source string.
     * @throws MyJSONException If there is a syntax error in the source string.
     */
    public MyJSONObject(MyJSONTokener x) throws MyJSONException {
        this();
        char c;
        String key;

        if (x.nextClean() != '{') {
            throw x.syntaxError("A JSONObject text must begin with '{'");
        }
        for (;;) {
            c = x.nextClean();
            switch (c) {
            case 0:
                throw x.syntaxError("A JSONObject text must end with '}'");
            case '}':
                return;
            default:
                x.back();
                key = x.nextValue().toString();
            }

            /*
             * The key is followed by ':'. We will also tolerate '=' or '=>'.
             */

            c = x.nextClean();
            if (c == '=') {
                if (x.next() != '>') {
                    x.back();
                }
            } else if (c != ':') {
                throw x.syntaxError("Expected a ':' after a key");
            }
            
            Object o = x.nextValue();
            
            if(o.toString().indexOf(":") > 0 ||
                    o.toString().indexOf("/") > 0 || 
                    o.toString().indexOf("\\") > 0 || 
                    o.toString().indexOf("=") > 0 || 
                    o.toString().indexOf("=>") > 0) {
                put(key, "\"" + o + "\"");   
            } else {
                put(key, o);
            }                     


            /*
             * Pairs are separated by ','. We will also tolerate ';'.
             */

            char nc = x.nextClean();
            
            switch (nc) {
            case ';':
            case ',':
                if (x.nextClean() == '}') {
                    return;
                }
                x.back();
                break;
            case '}':
                return;
            default:
                throw x.syntaxError("Expected a ',' or '}'");
            }
        }
    }


//#ifdef PRODUCER
//#     /**
//#      * Construct a MyJSONObject from a Map.
//#      * @param map A map object that can be used to initialize the contents of
//#      *  the MyJSONObject.
//#      */
//#     public MyJSONObject(Hashtable map) {
//#         if (map == null) {
//#             this.myHashMap = new Hashtable();
//#         } else {
//#             this.myHashMap = new Hashtable(map.size());
//#             Enumeration keys = map.keys();
//#             while (keys.hasMoreElements()) {
//#                 Object key = keys.nextElement();
//#                 this.myHashMap.put(key, map.get(key));
//#             }
//#         }
//#     }
//#endif    
    
    /**
     * Construct a MyJSONObject from a string.
     * This is the most commonly used MyJSONObject constructor.
     * @param string    A string beginning
     *  with <code>{</code>&nbsp;<small>(left brace)</small> and ending
     *  with <code>}</code>&nbsp;<small>(right brace)</small>.
     * @exception MyJSONException If there is a syntax error in the source string.
     */
    public MyJSONObject(String string) throws MyJSONException {
        this(new MyJSONTokener(string));
    }


    /**
     * Accumulate values under a key. It is similar to the put method except
     * that if there is already an object stored under the key then a
     * MyJSONArray is stored under the key to hold all of the accumulated values.
     * If there is already a MyJSONArray, then the new value is appended to it.
     * In contrast, the put method replaces the previous value.
     * @param key   A key string.
     * @param value An object to be accumulated under the key.
     * @return this.
     * @throws MyJSONException If the value is an invalid number
     *  or if the key is null.
     */
    public MyJSONObject accumulate(String key, Object value)
            throws MyJSONException {
        testValidity(value);
        Object o = opt(key);
        if (o == null) {
            put(key, value);
        } else if (o instanceof MyJSONArray) {
            ((MyJSONArray)o).put(value);
        } else {
            put(key, new MyJSONArray().put(o).put(value));
        }
        return this;
    }

//#ifdef PRODUCER
//#     /**
//#      * Append values to the array under a key. If the key does not exist in the
//#      * MyJSONObject, then the key is put in the MyJSONObject with its value being a
//#      * MyJSONArray containing the value parameter. If the key was already
//#      * associated with a MyJSONArray, then the value parameter is appended to it.
//#      * @param key   A key string.
//#      * @param value An object to be accumulated under the key.
//#      * @return this.
//#      * @throws MyJSONException If the key is null or if the current value 
//#      * 	associated with the key is not a MyJSONArray.
//#      */
//#     public MyJSONObject append(String key, Object value)
//#             throws MyJSONException {
//#         testValidity(value);
//#         Object o = opt(key);
//#         if (o == null) {
//#             put(key, new MyJSONArray().put(value));
//#         } else if (o instanceof MyJSONArray) {
//#             throw new MyJSONException("MyJSONObject[" + key + 
//#             		"] is not a MyJSONArray.");
//#         } else {
//#             put(key, new MyJSONArray().put(o).put(value));
//#         }
//#         return this;
//#     }
//#endif

//#if CLDC!="1.0"
    /**
     * Produce a string from a double. The string "null" will be returned if
     * the number is not finite.
     * @param  d A double.
     * @return A String.
     */
    static public String doubleToString(double d) {
        if (Double.isInfinite(d) || Double.isNaN(d)) {
        	return "null";
        }

// Shave off trailing zeros and decimal point, if possible.

        String s = Double.toString(d);
        if (s.indexOf('.') > 0 && s.indexOf('e') < 0 && s.indexOf('E') < 0) {
            while (s.endsWith("0")) {
                s = s.substring(0, s.length() - 1);
            }
            if (s.endsWith(".")) {
                s = s.substring(0, s.length() - 1);
            }
        }
        return s;
    }
//#endif

    /**
     * Get the value object associated with a key.
     *
     * @param key   A key string.
     * @return      The object associated with the key.
     * @throws   MyJSONException if the key is not found.
     */
    public Object get(String key) throws MyJSONException {
        Object o = opt(key);
        if (o == null) {
            throw new MyJSONException("JSONObject[" + quote(key) +
                    "] not found.");
        }
        return o;
    }


    /**
     * Get the boolean value associated with a key.
     *
     * @param key   A key string.
     * @return      The truth.
     * @throws   MyJSONException
     *  if the value is not a Boolean or the String "true" or "false".
     */
    public boolean getBoolean(String key) throws MyJSONException {
        Object o = get(key);
//#if CLDC!="1.0"
        if (o.equals(Boolean.FALSE) ||
//#else
//#         if (o.equals(FALSE) ||
//#endif
                (o instanceof String &&
                ((String)o).toLowerCase().equals("false"))) {
            return false;
//#if CLDC!="1.0"
        } else if (o.equals(Boolean.TRUE) ||
//#else
//#         } else if (o.equals(TRUE) ||
//#endif
                (o instanceof String &&
                ((String)o).toLowerCase().equals("true"))) {
            return true;
        }
        throw new MyJSONException("JSONObject[" + quote(key) +
                "] is not a Boolean.");
    }

//#if CLDC!="1.0"
    /**
     * Get the double value associated with a key.
     * @param key   A key string.
     * @return      The numeric value.
     * @throws MyJSONException if the key is not found or
     *  if the value is not a Number object and cannot be converted to a number.
     */
    public double getDouble(String key) throws MyJSONException {
        Object o = get(key);
        if (o instanceof Byte) {
            return (double) ((Byte)o).byteValue();
        } else if (o instanceof Short) {
            return (double) ((Short)o).shortValue();
        } else if (o instanceof Integer) {
            return (double) ((Integer)o).intValue();
        } else if (o instanceof Long) {
            return (double) ((Long)o).longValue();
        } else if (o instanceof Float) {
            return (double) ((Float)o).floatValue();
        } else if (o instanceof Double) {
            return ((Double)o).doubleValue();
        } else if (o instanceof String) {
            try {
                return Double.valueOf((String)o).doubleValue();
            } catch (Exception e) {
                throw new MyJSONException("JSONObject[" + quote(key) +
                    "] is not a number.");
            }
        } 
        throw new MyJSONException("JSONObject[" + quote(key) +
            "] is not a number.");
    }
//#endif


    /**
     * Get the int value associated with a key. If the number value is too
     * large for an int, it will be clipped.
     *
     * @param key   A key string.
     * @return      The integer value.
     * @throws   MyJSONException if the key is not found or if the value cannot
     *  be converted to an integer.
     */
    public int getInt(String key) throws MyJSONException {
        Object o = get(key);
        if (o instanceof Byte) {
            return ((Byte)o).byteValue();
        } else if (o instanceof Short) {
            return ((Short)o).shortValue();
        } else if (o instanceof Integer) {
            return ((Integer)o).intValue();
        } else if (o instanceof Long) {
            return (int) ((Long)o).longValue();
//#if CLDC!="1.0"
        } else if (o instanceof Float) {
            return (int) ((Float)o).floatValue();
        } else if (o instanceof Double) {
            return (int) ((Double)o).doubleValue();
        } else if (o instanceof String) {
            return (int) getDouble(key);
//#endif
        } 
        throw new MyJSONException("JSONObject[" + quote(key) +
            "] is not a number.");
    }


    /**
     * Get the MyJSONArray value associated with a key.
     *
     * @param key   A key string.
     * @return      A MyJSONArray which is the value.
     * @throws   MyJSONException if the key is not found or
     *  if the value is not a MyJSONArray.
     */
    public MyJSONArray getJSONArray(String key) throws MyJSONException {
        Object o = get(key);
        if (o instanceof MyJSONArray) {
            return (MyJSONArray)o;
        }
        throw new MyJSONException("JSONObject[" + quote(key) +
                "] is not a JSONArray.");
    }

    /**
     * Get the MyJSONObject value associated with a key.
     *
     * @param key   A key string.
     * @return      A MyJSONObject which is the value.
     * @throws   MyJSONException if the key is not found or
     *  if the value is not a MyJSONObject.
     */
    public MyJSONObject getJSONObject(String key) throws MyJSONException {
        Object o = get(key);
        if (o instanceof MyJSONObject) {
            return (MyJSONObject)o;
        }
        throw new MyJSONException("JSONObject[" + quote(key) +
                "] is not a JSONObject.");
    }

    /**
     * Get the long value associated with a key. If the number value is too
     * long for a long, it will be clipped.
     *
     * @param key   A key string.
     * @return      The long value.
     * @throws   MyJSONException if the key is not found or if the value cannot
     *  be converted to a long.
     */
    public long getLong(String key) throws MyJSONException {
        Object o = get(key);
        if (o instanceof Byte) {
            return ((Byte)o).byteValue();
        } else if (o instanceof Short) {
            return ((Short)o).shortValue();
        } else if (o instanceof Integer) {
            return ((Integer)o).intValue();
        } else if (o instanceof Long) {
            return ((Long)o).longValue();
//#if CLDC!="1.0"
        } else if (o instanceof Float) {
            return (long) ((Float)o).floatValue();
        } else if (o instanceof Double) {
            return (long) ((Double)o).doubleValue();
        } else if (o instanceof String) {
            return (long) getDouble(key);
//#endif
        } 
        throw new MyJSONException("JSONObject[" + quote(key) +
            "] is not a number.");
    }

    /**
     * Get the string associated with a key.
     *
     * @param key   A key string.
     * @return      A string which is the value.
     * @throws   MyJSONException if the key is not found.
     */
    public String getString(String key) throws MyJSONException {
        return get(key).toString();
    }

    /**
     * Determine if the MyJSONObject contains a specific key.
     * @param key   A key string.
     * @return      true if the key exists in the MyJSONObject.
     */
    public boolean has(String key) {
        return this.myHashMap.containsKey(key);
    }


    /**
     * Determine if the value associated with the key is null or if there is
     *  no value.
     * @param key   A key string.
     * @return      true if there is no value associated with the key or if
     *  the value is the MyJSONObject.NULL object.
     */
    public boolean isNull(String key) {
        return MyJSONObject.NULL.equals(opt(key));
    }


    /**
     * Get an enumeration of the keys of the MyJSONObject.
     *
     * @return An iterator of the keys.
     */
    public Enumeration keys() {
        return this.myHashMap.keys();
    }


    /**
     * Get the number of keys stored in the MyJSONObject.
     *
     * @return The number of keys in the MyJSONObject.
     */
    public int length() {
        return this.myHashMap.size();
    }


    /**
     * Produce a MyJSONArray containing the names of the elements of this
     * MyJSONObject.
     * @return A MyJSONArray containing the key strings, or null if the MyJSONObject
     * is empty.
     */
    public MyJSONArray names() {
        MyJSONArray ja = new MyJSONArray();
        Enumeration  keys = keys();
        while (keys.hasMoreElements()) {
            ja.put(keys.nextElement());
        }
        return ja.length() == 0 ? null : ja;
    }

    
    /**
     * Shave off trailing zeros and decimal point, if possible.
     */
    static public String trimNumber(String s) {
        if (s.indexOf('.') > 0 && s.indexOf('e') < 0 && s.indexOf('E') < 0) {
            while (s.endsWith("0")) {
                s = s.substring(0, s.length() - 1);
            }
            if (s.endsWith(".")) {
                s = s.substring(0, s.length() - 1);
            }
        }
        return s;
    }

    /**
     * Produce a string from a Number.
     * @param  n A Number
     * @return A String.
     * @throws MyJSONException If n is a non-finite number.
     */
    static public String numberToString(Object n)
            throws MyJSONException {
        if (n == null) {
            throw new MyJSONException("Null pointer");
        }
        testValidity(n);
        return trimNumber(n.toString());
    }

    /**
     * Get an optional value associated with a key.
     * @param key   A key string.
     * @return      An object which is the value, or null if there is no value.
     */
    public Object opt(String key) {
        return key == null ? null : this.myHashMap.get(key);
    }


    /**
     * Get an optional boolean associated with a key.
     * It returns false if there is no such key, or if the value is not
     * Boolean.TRUE or the String "true".
     *
     * @param key   A key string.
     * @return      The truth.
     */
    public boolean optBoolean(String key) {
        return optBoolean(key, false);
    }


    /**
     * Get an optional boolean associated with a key.
     * It returns the defaultValue if there is no such key, or if it is not
     * a Boolean or the String "true" or "false" (case insensitive).
     *
     * @param key              A key string.
     * @param defaultValue     The default.
     * @return      The truth.
     */
    public boolean optBoolean(String key, boolean defaultValue) {
        try {
            return getBoolean(key);
        } catch (Exception e) {
            return defaultValue;
        }
    }

    
    /**
     * Put a key/value pair in the MyJSONObject, where the value will be a
     * MyJSONArray which is produced from a Collection.
     * @param key 	A key string.
     * @param value	A Collection value.
     * @return		this.
     * @throws MyJSONException
     */
    public MyJSONObject put(String key, Vector value) throws MyJSONException {
        put(key, new MyJSONArray(value));
        return this;
    }

    
//#if CLDC!="1.0"
    /**
     * Get an optional double associated with a key,
     * or NaN if there is no such key or if its value is not a number.
     * If the value is a string, an attempt will be made to evaluate it as
     * a number.
     *
     * @param key   A string which is the key.
     * @return      An object which is the value.
     */
    public double optDouble(String key) {
        return optDouble(key, Double.NaN);
    }
//#endif

//#if CLDC!="1.0"
    /**
     * Get an optional double associated with a key, or the
     * defaultValue if there is no such key or if its value is not a number.
     * If the value is a string, an attempt will be made to evaluate it as
     * a number.
     *
     * @param key   A key string.
     * @param defaultValue     The default.
     * @return      An object which is the value.
     */
    public double optDouble(String key, double defaultValue) {
        try {
            Object o = opt(key);
            return Double.parseDouble((String)o);
        } catch (Exception e) {
            return defaultValue;
        }
    }
//#endif

    /**
     * Get an optional int value associated with a key,
     * or zero if there is no such key or if the value is not a number.
     * If the value is a string, an attempt will be made to evaluate it as
     * a number.
     *
     * @param key   A key string.
     * @return      An object which is the value.
     */
    public int optInt(String key) {
        return optInt(key, 0);
    }


    /**
     * Get an optional int value associated with a key,
     * or the default if there is no such key or if the value is not a number.
     * If the value is a string, an attempt will be made to evaluate it as
     * a number.
     *
     * @param key   A key string.
     * @param defaultValue     The default.
     * @return      An object which is the value.
     */
    public int optInt(String key, int defaultValue) {
        try {
            return getInt(key);
        } catch (Exception e) {
            return defaultValue;
        }
    }


    /**
     * Get an optional MyJSONArray associated with a key.
     * It returns null if there is no such key, or if its value is not a
     * MyJSONArray.
     *
     * @param key   A key string.
     * @return      A MyJSONArray which is the value.
     */
    public MyJSONArray optJSONArray(String key) {
        Object o = opt(key);
        return o instanceof MyJSONArray ? (MyJSONArray)o : null;
    }


    /**
     * Get an optional MyJSONObject associated with a key.
     * It returns null if there is no such key, or if its value is not a
     * MyJSONObject.
     *
     * @param key   A key string.
     * @return      A MyJSONObject which is the value.
     */
    public MyJSONObject optJSONObject(String key) {
        Object o = opt(key);
        return o instanceof MyJSONObject ? (MyJSONObject)o : null;
    }


    /**
     * Get an optional long value associated with a key,
     * or zero if there is no such key or if the value is not a number.
     * If the value is a string, an attempt will be made to evaluate it as
     * a number.
     *
     * @param key   A key string.
     * @return      An object which is the value.
     */
    public long optLong(String key) {
        return optLong(key, 0);
    }


    /**
     * Get an optional long value associated with a key,
     * or the default if there is no such key or if the value is not a number.
     * If the value is a string, an attempt will be made to evaluate it as
     * a number.
     *
     * @param key   A key string.
     * @param defaultValue     The default.
     * @return      An object which is the value.
     */
    public long optLong(String key, long defaultValue) {
        try {
            return getLong(key);
        } catch (Exception e) {
            return defaultValue;
        }
    }


    /**
     * Get an optional string associated with a key.
     * It returns an empty string if there is no such key. If the value is not
     * a string and is not null, then it is coverted to a string.
     *
     * @param key   A key string.
     * @return      A string which is the value.
     */
    public String optString(String key) {
        return optString(key, "");
    }


    /**
     * Get an optional string associated with a key.
     * It returns the defaultValue if there is no such key.
     *
     * @param key   A key string.
     * @param defaultValue     The default.
     * @return      A string which is the value.
     */
    public String optString(String key, String defaultValue) {
        Object o = opt(key);
        return o != null ? o.toString() : defaultValue;
    }


    /**
     * Put a key/boolean pair in the MyJSONObject.
     *
     * @param key   A key string.
     * @param value A boolean which is the value.
     * @return this.
     * @throws MyJSONException If the key is null.
     */
    public MyJSONObject put(String key, boolean value) throws MyJSONException {
//#if CLDC!="1.0"
        put(key, value ? Boolean.TRUE : Boolean.FALSE);
//#else
//#         put(key, value ? TRUE : FALSE);
//#endif
        return this;
    }


//#if CLDC!="1.0"
    /**
     * Put a key/double pair in the MyJSONObject.
     *
     * @param key   A key string.
     * @param value A double which is the value.
     * @return this.
     * @throws MyJSONException If the key is null or if the number is invalid.
     */
    public MyJSONObject put(String key, double value) throws MyJSONException {
        put(key, new Double(value));
        return this;
    }
//#endif

    /**
     * Put a key/int pair in the MyJSONObject.
     *
     * @param key   A key string.
     * @param value An int which is the value.
     * @return this.
     * @throws MyJSONException If the key is null.
     */
    public MyJSONObject put(String key, int value) throws MyJSONException {
        put(key, new Integer(value));
        return this;
    }


    /**
     * Put a key/long pair in the MyJSONObject.
     *
     * @param key   A key string.
     * @param value A long which is the value.
     * @return this.
     * @throws MyJSONException If the key is null.
     */
    public MyJSONObject put(String key, long value) throws MyJSONException {
        put(key, new Long(value));
        return this;
    }

     
//#ifdef PRODUCER
//#     /**
//#      * Put a key/value pair in the MyJSONObject, where the value will be a
//#      * MyJSONObject which is produced from a Map.
//#      * @param key 	A key string.
//#      * @param value	A Map value.
//#      * @return		this.
//#      * @throws MyJSONException
//#      */
//#     public MyJSONObject put(String key, Hashtable value) throws MyJSONException {
//#         put(key, new MyJSONObject(value));
//#         return this;
//#     }
//#endif    
    
    /**
     * Put a key/value pair in the MyJSONObject. If the value is null,
     * then the key will be removed from the MyJSONObject if it is present.
     * @param key   A key string.
     * @param value An object which is the value. It should be of one of these
     *  types: Boolean, Double, Integer, MyJSONArray, MyJSONObject, Long, String,
     *  or the MyJSONObject.NULL object.
     * @return this.
     * @throws MyJSONException If the value is non-finite number
     *  or if the key is null.
     */
    public MyJSONObject put(String key, Object value) throws MyJSONException {
        if (key == null) {
            throw new MyJSONException("Null key.");
        }
        if (value != null) {
            testValidity(value);
            this.myHashMap.put(key, value);
        } else {
            remove(key);
        }
        return this;
    }

    /**
     * Put a key/value pair in the MyJSONObject, but only if the
     * key and the value are both non-null.
     * @param key   A key string.
     * @param value An object which is the value. It should be of one of these
     *  types: Boolean, Double, Integer, MyJSONArray, MyJSONObject, Long, String,
     *  or the MyJSONObject.NULL object.
     * @return this.
     * @throws MyJSONException If the value is a non-finite number.
     */
    public MyJSONObject putOpt(String key, Object value) throws MyJSONException {
        if (key != null && value != null) {
            put(key, value);
        }
        return this;
    }

    /**
     * Produce a string in double quotes with backslash sequences in all the
     * right places. A backslash will be inserted within </, allowing JSON
     * text to be delivered in HTML. In JSON text, a string cannot contain a
     * control character or an unescaped quote or backslash.
     * @param string A String
     * @return  A String correctly formatted for insertion in a JSON text.
     */
    public static String quote(String string) {
        if (string == null || string.length() == 0) {
            return "\"\"";
        }

        char         b;
        char         c = 0;
        int          i;
        int          len = string.length();
        StringBuffer sb = new StringBuffer(len + 4);
        String       t;

        sb.append('"');
        for (i = 0; i < len; i += 1) {
            b = c;
            c = string.charAt(i);
            switch (c) {
            case '\\':
            case '"':
                sb.append('\\');
                sb.append(c);
                break;
            case '/':
                if (b == '<') {
                    sb.append('\\');
                }
                sb.append(c);
                break;
            case '\b':
                sb.append("\\b");
                break;
            case '\t':
                sb.append("\\t");
                break;
            case '\n':
                sb.append("\\n");
                break;
            case '\f':
                sb.append("\\f");
                break;
            case '\r':
                sb.append("\\r");
                break;
            default:
                if (c < ' ') {
                    t = "000" + Integer.toHexString(c);
                    sb.append("\\u" + t.substring(t.length() - 4));
                } else {
                    sb.append(c);
                }
            }
        }
        sb.append('"');
        return sb.toString();
    }

    /**
     * Remove a name and its value, if present.
     * @param key The name to be removed.
     * @return The value that was associated with the name,
     * or null if there was no value.
     */
    public Object remove(String key) {
        return this.myHashMap.remove(key);
    }

    /**
     * Throw an exception if the object is an NaN or infinite number.
     * @param o The object to test.
     * @throws MyJSONException If o is a non-finite number.
     */
    static void testValidity(Object o) throws MyJSONException {
        if (o != null) {
//#if CLDC!="1.0"
            if (o instanceof Double) {
                if (((Double)o).isInfinite() || ((Double)o).isNaN()) {
                    throw new MyJSONException(
                        "JSON does not allow non-finite numbers");
                }
            } else if (o instanceof Float) {
                if (((Float)o).isInfinite() || ((Float)o).isNaN()) {
                    throw new MyJSONException(
                        "JSON does not allow non-finite numbers.");
                }
            }
//#endif
        }
    }

    /**
     * Produce a MyJSONArray containing the values of the members of this
     * MyJSONObject.
     * @param names A MyJSONArray containing a list of key strings. This
     * determines the sequence of the values in the result.
     * @return A MyJSONArray of values.
     * @throws MyJSONException If any of the values are non-finite numbers.
     */
    public MyJSONArray toJSONArray(MyJSONArray names) throws MyJSONException {
        if (names == null || names.length() == 0) {
            return null;
        }
        MyJSONArray ja = new MyJSONArray();
        for (int i = 0; i < names.length(); i += 1) {
            ja.put(this.opt(names.getString(i)));
        }
        return ja;
    }

    /**
     * Make a JSON text of this MyJSONObject. For compactness, no whitespace
     * is added. If this would not result in a syntactically correct JSON text,
     * then null will be returned instead.
     * <p>
     * Warning: This method assumes that the data structure is acyclical.
     *
     * @return a printable, displayable, portable, transmittable
     *  representation of the object, beginning
     *  with <code>{</code>&nbsp;<small>(left brace)</small> and ending
     *  with <code>}</code>&nbsp;<small>(right brace)</small>.
     */
    public String toString() {
        try {
            Enumeration keys = keys();
            StringBuffer sb = new StringBuffer("{");

            while (keys.hasMoreElements()) {
                if (sb.length() > 1) {
                    sb.append(',');
                }
                Object o = keys.nextElement();
                sb.append(quote(o.toString()));
                sb.append(':');
                sb.append(valueToString(this.myHashMap.get(o)));
            }
            sb.append('}');
            return sb.toString();
        } catch (Exception e) {
            return null;
        }
    }


    /**
     * Make a prettyprinted JSON text of this MyJSONObject.
     * <p>
     * Warning: This method assumes that the data structure is acyclical.
     * @param indentFactor The number of spaces to add to each level of
     *  indentation.
     * @return a printable, displayable, portable, transmittable
     *  representation of the object, beginning
     *  with <code>{</code>&nbsp;<small>(left brace)</small> and ending
     *  with <code>}</code>&nbsp;<small>(right brace)</small>.
     * @throws MyJSONException If the object contains an invalid number.
     */
    public String toString(int indentFactor) throws MyJSONException {
        return toString(indentFactor, 0);
    }


    /**
     * Make a prettyprinted JSON text of this MyJSONObject.
     * <p>
     * Warning: This method assumes that the data structure is acyclical.
     * @param indentFactor The number of spaces to add to each level of
     *  indentation.
     * @param indent The indentation of the top level.
     * @return a printable, displayable, transmittable
     *  representation of the object, beginning
     *  with <code>{</code>&nbsp;<small>(left brace)</small> and ending
     *  with <code>}</code>&nbsp;<small>(right brace)</small>.
     * @throws MyJSONException If the object contains an invalid number.
     */
    String toString(int indentFactor, int indent) throws MyJSONException {
        int          i;
        int          n = length();
        if (n == 0) {
            return "{}";
        }
        Enumeration keys = keys();
        StringBuffer sb = new StringBuffer("{");
        int          newindent = indent + indentFactor;
        Object       o;
        if (n == 1) {
            o = keys.nextElement();
            sb.append(quote(o.toString()));
            sb.append(": ");
            sb.append(valueToString(this.myHashMap.get(o), indentFactor,
                    indent));
        } else {
            while (keys.hasMoreElements()) {
                o = keys.nextElement();
                if (sb.length() > 1) {
                    sb.append(",\n");
                } else {
                    sb.append('\n');
                }
                for (i = 0; i < newindent; i += 1) {
                    sb.append(' ');
                }
                sb.append(quote(o.toString()));
                sb.append(": ");
                sb.append(valueToString(this.myHashMap.get(o), indentFactor,
                        newindent));
            }
            if (sb.length() > 1) {
                sb.append('\n');
                for (i = 0; i < indent; i += 1) {
                    sb.append(' ');
                }
            }
        }
        sb.append('}');
        return sb.toString();
    }


    /**
     * Make a JSON text of an Object value. If the object has an
     * value.toString() method, then that method will be used to produce
     * the JSON text. The method is required to produce a strictly
     * conforming text. If the object does not contain a toString
     * method (which is the most common case), then a text will be
     * produced by the rules.
     * <p>
     * Warning: This method assumes that the data structure is acyclical.
     * @param value The value to be serialized.
     * @return a printable, displayable, transmittable
     *  representation of the object, beginning
     *  with <code>{</code>&nbsp;<small>(left brace)</small> and ending
     *  with <code>}</code>&nbsp;<small>(right brace)</small>.
     * @throws MyJSONException If the value is or contains an invalid number.
     */
    static String valueToString(Object value) throws MyJSONException {
        if (value == null || value.equals(null)) {
            return "null";
        }
        if (value instanceof String) {
        	Object o;
        	try {
            	o = ((String)value).toString();
            } catch (Exception e) {
            	throw new MyJSONException(e);
            }
            if (o instanceof String) {
	        	return (String)o;
	        }
            throw new MyJSONException("Bad value from toString: " + o);
        }
//#if CLDC!="1.0"
        if (value instanceof Float || value instanceof Double ||
//#else
//#         if (
//#endif
            value instanceof Byte || value instanceof Short || 
            value instanceof Integer || value instanceof Long) {
            return numberToString(value);
        }
        if (value instanceof Boolean || value instanceof MyJSONObject ||
                value instanceof MyJSONArray) {
            return value.toString();
        }
        return quote(value.toString());
    }


    /**
     * Make a prettyprinted JSON text of an object value.
     * <p>
     * Warning: This method assumes that the data structure is acyclical.
     * @param value The value to be serialized.
     * @param indentFactor The number of spaces to add to each level of
     *  indentation.
     * @param indent The indentation of the top level.
     * @return a printable, displayable, transmittable
     *  representation of the object, beginning
     *  with <code>{</code>&nbsp;<small>(left brace)</small> and ending
     *  with <code>}</code>&nbsp;<small>(right brace)</small>.
     * @throws MyJSONException If the object contains an invalid number.
     */
     static String valueToString(Object value, int indentFactor, int indent)
            throws MyJSONException {
        if (value == null || value.equals(null)) {
            return "null";
        }
        try {
	        if (value instanceof String) {
		        Object o = ((String)value).toString();
		        if (o instanceof String) {
		        	return (String)o;
		        }
	        }
        } catch (Exception e) {
        	/* forget about it */
        }
//#if CLDC!="1.0"
        if (value instanceof Float || value instanceof Double ||
//#else
//#         if (
//#endif
            value instanceof Byte || value instanceof Short || 
            value instanceof Integer || value instanceof Long) {
            return numberToString(value);
        }
        if (value instanceof Boolean) {
            return value.toString();
        }
        if (value instanceof MyJSONObject) {
            return ((MyJSONObject)value).toString(indentFactor, indent);
        }
        if (value instanceof MyJSONArray) {
            return ((MyJSONArray)value).toString(indentFactor, indent);
        }
        return quote(value.toString());
    }


     /**
      * Write the contents of the MyJSONObject as JSON text to a writer.
      * For compactness, no whitespace is added.
      * <p>
      * Warning: This method assumes that the data structure is acyclical.
      *
      * @return The writer.
      * @throws MyJSONException
      */
     public Writer write(Writer writer) throws MyJSONException {
        try {
            boolean  b = false;
            Enumeration keys = keys();
            writer.write('{');

            while (keys.hasMoreElements()) {
                if (b) {
                    writer.write(',');
                }
                Object k = keys.nextElement();
                writer.write(quote(k.toString()));
                writer.write(':');
                Object v = this.myHashMap.get(k);
                if (v instanceof MyJSONObject) {
                    ((MyJSONObject)v).write(writer);
                } else if (v instanceof MyJSONArray) {
                    ((MyJSONArray)v).write(writer);
                } else {
                    writer.write(valueToString(v));
                }
                b = true;
            }
            writer.write('}');
            return writer;
        } catch (IOException e) {
            throw new MyJSONException(e);
        }
     }
}