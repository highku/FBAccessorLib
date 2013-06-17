/*
Copyright 2011-present, Sokrati

This file is part of GoogleAccessorLib.

GoogleAccessorLib is free software: you can redistribute it and/or modify
it under the terms of the GNU Lesser General Public License as published by
the Free Software Foundation, either version 3 of the License, or
any later version.

GoogleAccessorLib is distributed in the hope that it will be useful,
but WITHOUT ANY WARRANTY; without even the implied warranty of
MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE.  See the
GNU Lesser General Public License for more details.

You should have received a copy of the GNU Lesser General Public License
along with GoogleAccessorLib.  If not, see <http://www.gnu.org/licenses/>.
*/

package com.sokrati.httpRequest;

import java.net.*;
import java.util.*;
import java.io.*;
import java.util.zip.*;

import com.sokrati.logger.Log;

public class HttpRequest extends Object
{

	/**
	 * Object that wraps around call to ECS. The object is responsible for
	 * making the call and providing with the response and error code.
	 */
    private int m_code;
    private StringBuffer m_response;
    private byte[] m_byteReponse;
    private String m_url;
    private String m_userAgent;
    private boolean m_fatal;
    private boolean m_success;
    private boolean m_retry;
    private boolean m_preserveErrorResponse;
    private static int MAX_RETRY_COUNT = 4;
    private static int TIMEOUT = 360000; // 6 minutes
    
    private static String GZIP = "gzip";
    private static String ENCODING = "UTF-8";
    private static String PARAM_LANGUAGE = "Content-Language";
    private static String VALUE_LANGUAGE = "en-US";

    private static String PARAM_CONTENT_TYPE = "Content-Type";                                
    private static String VALUE_CONTENT_TYPE = "application/x-www-form-urlencoded;charset="+ ENCODING;

    private static String PARAM_CONTENT_LENGTH = "Content-Length";
    private static String SPACE = " ";
    private static String PLUS = "+";

    // MAX redirect request retry count
    private int maxRedirectRequestRetryCount = 5;

    // unusuable constructor
    protected HttpRequest() {}

    /*!
     * The call is made in the constructor. Once the object is instantiated the 
     * object is usable to fetch the results
     */

    public HttpRequest(String url) throws RuntimeException
    {
        initialize(url, "SokratiBot", false);
    }
    
    public HttpRequest(String url, boolean preserveErrorResponse)
        throws RuntimeException
    {
        initialize(url, "SokratiBot", preserveErrorResponse);
    }
    
    public HttpRequest(String url, String userAgent) throws RuntimeException
    {
        initialize(url, userAgent, false);
    }

    public HttpRequest(String url, String userAgent,
                       boolean preserveErrorResponse)
    {
        initialize(url, userAgent, preserveErrorResponse);
    }
    
    private void initialize(String url, String userAgent,
                            boolean preserveErrorResponse)
    {
        m_fatal = true;
        m_success = false;
        m_retry = false;

        //This is needed as URL and URI classed do not accept spaces in URL.
        m_url = url.replaceAll(SPACE, PLUS);
        m_userAgent = userAgent;
        m_preserveErrorResponse = preserveErrorResponse;
    }

    public synchronized void call() throws RuntimeException
    {
        call(false);
    }
    
    private synchronized void call(boolean followRedirects)
        throws RuntimeException
    {
        try
        {
            String redirectUrl = null;
            int retryCount = 0;
            HttpURLConnection conn = null;

            do
            {
                try
                {
                    URL urlObj = new URL(m_url);
                    conn = (HttpURLConnection)urlObj.openConnection();
                    conn.setConnectTimeout(TIMEOUT);

                    // set the request params
                    conn.setRequestMethod("GET");
                    if (m_userAgent != null)
                    {
                        conn.setRequestProperty("User-Agent", m_userAgent);
                    }
                    conn.setDoOutput(true);
                    conn.setDoInput(true);
                    conn.setInstanceFollowRedirects(followRedirects);

                    // make the call
                    conn.connect();
                    break;
                }
                catch (java.net.SocketTimeoutException e)
                {
                    retryCount++;
                    if (retryCount >= MAX_RETRY_COUNT)
                    {
                        throw e;
                    }
                }
                catch (java.net.SocketException e)
                {
                    Log.l.info("Socket exception occured, retrying the req.." +
                               ++retryCount);
                    if (retryCount >= MAX_RETRY_COUNT)
                    {
                        throw e;
                    }
                }
            }while(true);

            // Get the error codes
            m_code = conn.getResponseCode();
            if(m_code == 301 || m_code == 302) //redirect handling
            {
                for (int i = 1 ; ; i++)
                {
                    String key = conn.getHeaderFieldKey(i);
                    if(key == null)
                    {
                        break;
                    }
                    if (key.equals("Location"))
                    {
                        redirectUrl = conn.getHeaderField(i);
                    }
                }

                if (redirectUrl != null)
                {
                    /* spaces in the query string should be replaced by
                     * +. ' ' can occur only in query string.
                     * If not replaced query string is considered only till 
                     * first space. Rest of the url is considered to be
                     * HTTP version.
                     */
                    /*
                     * We do not make use of this URL while redirecting. 
                     * Instead we set setInstanceFollowRedirects to true
                     * which solves problem of relativeUrls in redirectUrls 
                     */
                    // If it is not relative URL, get connection directly to this URL.
                    maxRedirectRequestRetryCount--;
                    if (maxRedirectRequestRetryCount <= 0)
                    {
                        throw new RuntimeException(
                            "Could not connect: " + m_url + " : " +
                            "Maximum redirect count is reached."
                        );
                    }
                    else
                    {
                        if(!redirectUrl.startsWith("/"))
                        {
                            m_url = redirectUrl.replace(' ','+');
                                call(false //followRedirects
                                    );
                        }
                        else // Returned URL is relative URL
                        {
                                call(true //followRedirects
                                    );
                        }
                    }
                }
            }

            /*
             * Setting the m_fatal to be false here. Since it looks like things
             * have worked fine so far with no exceptions
             * If there is indeed a problem then it is dealt with in the error
             * codes.
             */

            if(redirectUrl == null)
            {
                InputStream in = null;
                m_fatal = false;

                if ((m_code >= 200) && (m_code < 300))
                {
                    // Read the response from connection input stream
                    m_success = true; 
                    in = conn.getInputStream();
                    if (GZIP.equalsIgnoreCase(conn.getContentEncoding()))
                    {
                        in = new GZIPInputStream(in);
                    }
                }
                else if ((m_code >= 400) && (m_code < 500))
                {
                    // Read the response from connection error stream
                    m_retry = true;
                    in = conn.getErrorStream();
                }
                else
                {
                    // TODO: Log an error here with a suitable message
                    // treat everything else as a fatal error
                    m_fatal = true;
                    in = conn.getErrorStream();
                }
                
                m_response = new StringBuffer();
                
                ArrayList<Byte> bytes = new ArrayList<Byte>();
                int c;
                InputStreamReader isr = new InputStreamReader(in, ENCODING);
                while ((c = isr.read()) != -1) 
                {
                    bytes.add((new Integer(c)).byteValue());
                    m_response.append((char) c);
                }
                

                Byte[] dataBytes = new Byte[bytes.size()];
                dataBytes = bytes.toArray(dataBytes);
                m_byteReponse = new byte[dataBytes.length];
                for (int i = 0 ; i < dataBytes.length ; i++)
                {
                    m_byteReponse[i] = dataBytes[i].byteValue();
                }
                isr.close();

            }
        }
        catch (IOException e)
        {   
            boolean throwException = true;
            if(e.getMessage().equalsIgnoreCase("Premature EOF"))
            {
                if(isValidPage())
                {
                    throwException = false;
                }
            }
            if(throwException)
            {
                m_fatal = true;
                Log.l.error("Got error in httpRequest call ", e);
                throw new RuntimeException("Could not connect: " + m_url + " : " +
                                           e.getMessage()
                                           );
            }            
        }
    }
    
    /*
     * This method sends a POST request to the initialized url
     *
     * It takes as input the string data to be posted.
     */

    public synchronized void doPost(String postData)
    {
        HttpURLConnection conn = null;
        int retryCount = 0;
        try
        {
            URL urlObj = new URL(m_url);
            // Qn: Should a POST request be retried?
            do
            {
                try
                {
                    conn = (HttpURLConnection) urlObj.openConnection();
                    conn.setRequestMethod("POST");

                    setRequestProperties(conn, postData);
                    writeDataToConnection(conn, postData);

                    break;
                }
                catch (java.net.SocketTimeoutException e)
                {
                    System.out.println("SocketTimeoutException has occurred for data:\n" + postData);
                    e.printStackTrace();
                    retryCount++;
                    if (retryCount >= MAX_RETRY_COUNT)
                    {
                        throw e;
                    }
                }
            } while (true);
            
            processResponse(conn);
        }
        catch (IOException e)
        {
            if (!isIgnorableException(e.getMessage()))
            {
                m_fatal = true;
                throw new RuntimeException("Error communicating with : " + m_url,
                                           e);
            }
        }
    }
    

    /*
     * This method sets in request parameters for the conennction
     */
    private static void setRequestProperties(HttpURLConnection conn,
                                             String data)
        throws ProtocolException, UnsupportedEncodingException
    {
        // Set headers       
        conn.setConnectTimeout(TIMEOUT);
        conn.setDoOutput(true);
        conn.setDoInput(true);

        // TODO: support redirects in future
        conn.setInstanceFollowRedirects(false);

        // Should this be in a hashmap?
        conn.setRequestProperty(PARAM_LANGUAGE, VALUE_LANGUAGE);
        conn.setRequestProperty(PARAM_CONTENT_TYPE, VALUE_CONTENT_TYPE);
        conn.setRequestProperty(PARAM_CONTENT_LENGTH,
                                Integer.toString(data.getBytes(ENCODING).length));

    }

    /*
     * This method writes data to the given connection
     * */
    private static void writeDataToConnection(HttpURLConnection conn,
                                              String requestData)
        throws IOException
    {
        DataOutputStream writer = new DataOutputStream(conn.getOutputStream());
        writer.writeBytes(requestData);
        writer.flush();
        writer.close();
    }

    /*
     * This method checks if the given exception contains a "Premature EOF" message. 
     * If it does and the page is valid we should ignore this exception
     * */
    private boolean isIgnorableException(String exception)
    {
        boolean ignoreException = false;
        if (exception.equalsIgnoreCase("Premature EOF"))
        {
            if (isValidPage())
            {
                ignoreException = true;
            }
        }
        return ignoreException;
    }

    /*
     * Process response obtained on the connection
     * */
    private void processResponse(HttpURLConnection conn) 
        throws SocketException, IOException
    {
        try
        {
            m_code = conn.getResponseCode();
        }
        catch (SocketException e)
        {
            throw e;
        }
        if ((m_code >= 200) && (m_code < 300))
        {
            m_success = true;
        }
        else if ((m_code >= 400) && (m_code < 500))
        {
            m_retry = true;
        }
        else
        {
            m_fatal = true;
        }

        if (m_success)
        {
            InputStream connStream = conn.getInputStream();
            BufferedReader connReader =
                new BufferedReader(new InputStreamReader(connStream));
            String line = null;
            m_response = new StringBuffer();
            while ((line = connReader.readLine()) != null)
            {
                m_response.append(line);
                m_response.append('\r');
            }
            connReader.close();
        }
        else if (m_preserveErrorResponse)
        {
            InputStream connStream = conn.getErrorStream();
            BufferedReader connReader =
                new BufferedReader(new InputStreamReader(connStream));
            String line = null;
            m_response = new StringBuffer();
            while ((line = connReader.readLine()) != null)
            {
                m_response.append(line);
                m_response.append('\r');
            }
            connReader.close();
        } 
    }

    /*
     * This method is used to check if the given page is valid(html) or not 
     */
    private boolean isValidPage()
    {
        boolean ret = true;
        String str = getBody().trim();
        String pattern = "</html>";
        if(str.endsWith(pattern))
        {
            ret = true;
        }
        else
        {
            ret = false;
        }
        return ret;
    }
    
    /*!
     * HttpRequest::isSuccessful
     *
     * Did the call to ECS succeed?
     */

    public boolean isSuccessful()
    {
        return m_success;
    }


    /*!
     * HttpRequest::isFatal
     *
     * Did the call fatally fail? In such a case making a call to ECS again may
     * be useless. Please bear in mind that this is on the entire request and
     * not individual items if the call was batched. This looks at the response
     * code in the HTTP header.
     *
     * There may be many reasons why this could happen:
     * - The items in the call may be inaccessible
     * - The keywords passed in could be invalid.
     */

    public boolean isFatal()
    {
        return m_fatal;
    }
    

    /*!
     * HttpRequest::isRetriable
     *
     * Should this request be retried?
     * The call looks at the code in the HTTP Response header.
     */
    
    public boolean isRetriable()
    {
        return m_retry;
    }


    /*!
     * HttpRequest::getErrorCode
     *
     * Get the error code 
     */

    public int getErrorCode()
    {
        return m_code;
    }

    public byte[] getBytes()
    {
        return m_byteReponse;
    }

    
    /*!
     * HttpRequest::getBody
     *
     * Gets the body
     */
    
    public String getBody()
    {
        String out = null;
        if(m_response != null)
        {
            out = m_response.toString();
        }

        return out;
    }
    
} // class HttpRequest
