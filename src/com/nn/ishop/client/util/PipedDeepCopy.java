/*****************************************************************************/
/* File Description  : PipedDeepCopy                                         */
/* File Version      : 1.0                                                   */
/* Legal Copyright   : Copyright (c) 2004-2007 by shiftTHINK Ltd. Liab. Co.  */
/* Company Name      : shiftTHINK Ltd. Liab. Co.                             */
/* Original Filename : PipedDeepCopy.java                                    */
/* Product Version   : 1.0                                                   */
/* Product Name      : CONNECT Project 							             */
/*****************************************************************************/

package com.nn.ishop.client.util;

import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.io.PipedInputStream;
import java.io.PipedOutputStream;

/**
 * <p>Title: Wrapper class for PipedDeepCopy</p>
 *  <p>Description: Utility for making deep copies (vs. clone()'s shallow copies) 
 *  of objects in a memory efficient way. Objects are
 * serialized in the calling thread and de-serialized in 
 * another thread. Error checking is fairly minimal in this
 * implementation. If an object is encountered that cannot 
 * be serialized (or that references an object that cannot be
 * serialized) an error is printed to System.err and null is 
 * returned. Depending on your specific application, it
 * might make more sense to have copy(...) re-throw the 
 * exception. [from
 * http://javatechniques.com/public/java/docs/basics/low-memory-deep-copy.html]</p>
 *  <p>Copyright: Copyright (c) 2006</p>
 *  <p>Company: shiftTHINK Ltd.</p>
 *
 * @author Uris Kalatchoff
 * @version 1.0
 */
public class PipedDeepCopy 
{
    /** Flag object used internally to 
     * indicate that deserialization failed. 
     * 
     */
    private static final Object ERROR = new Object();

    /**
     * Returns a copy of the object, or null if the 
     * object cannot be serialized.
     *
     * @param orig original object that is copied
     *
     * @return obj object copied
     */
    public static Object copy(Object orig) 
    {
        Object obj = null;
        
        PipedInputStream in;
        PipedOutputStream pos;
        ObjectOutputStream out;
        Deserializer des = null;

        try 
        {
            // Make a connected pair of piped streams
            in = new PipedInputStream();
            pos = new PipedOutputStream(in);

            // Make a deserializer thread (see inner class below)
            des = new Deserializer(in);

            // Write the object to the pipe
            out = new ObjectOutputStream(pos);
            out.writeObject(orig);

            // Wait for the object to be deserialized
            obj = des.getDeserializedObject();

            // See if something went wrong
            if (obj == ERROR) {
                obj = null;
            }
        } 
        catch (IOException ioe) 
        {
//            ConnectApplicationExceptionHandler.processException(
//            		ConnectApplicationExceptionUtil.createException(ioe,
//                    "", ConnectApplicationException.INFO));
        }
        finally
        {
        	in = null;
        	out = null;
        	pos = null;
        	des = null;
        }

        return obj;
    }

    /**
     * Thread subclass that handles deserializing from a PipedInputStream.
     */
    private static class Deserializer extends Thread {
        /** Object that we are deserializing */
        private Object obj = null;

        /** Lock that we block on while deserialization is happening */
        private Object lock = null;

        /** InputStream that the object is deserialized from. */
        private PipedInputStream in = null;

        public Deserializer(PipedInputStream pin) throws IOException {
            lock = new Object();
            this.in = pin;
            start();
        }

        public void run() {
            Object o = null;

            try {
                ObjectInputStream oin = new ObjectInputStream(in);
                o = oin.readObject();
            } catch (IOException e) {
                // This should never happen. If it does we make sure
                // that a the object is set to a flag that indicates
                // deserialization was not possible.
//                ConnectApplicationExceptionHandler.processException(
//                		ConnectApplicationExceptionUtil.createException(e,
//                        "", ConnectApplicationException.INFO));
            } catch (ClassNotFoundException cnfe) {
                // Same here...
//                ConnectApplicationExceptionHandler.processException(
//                		ConnectApplicationExceptionUtil.createException(
//                        cnfe, "", ConnectApplicationException.INFO));
            }

            synchronized (lock) {
                if (o == null) {
                    obj = ERROR;
                } else {
                    obj = o;
                }

                lock.notifyAll();
            }
        }

        /**
         * Returns the deserialized object. 
         * This method will block until the object is actually available.
         *
         * @return obj The deserialized object 
         */
        public Object getDeserializedObject() 
        {
            // Wait for the object to show up
            try 
            {
                synchronized (lock) {
                    while (obj == null) {
                        lock.wait();
                    }
                }
            } catch (InterruptedException ie) {
                // If we are interrupted we just return null
            }

            return obj;
        }
    }
}
