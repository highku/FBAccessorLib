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

package com.sokrati.fbRestAccessor;

import java.io.*;
import java.util.*;

import org.json.JSONException;
import org.json.JSONObject;

import com.sokrati.fbRestAccessor.exceptions.*;
import com.sokrati.fbRestAccessor.request.FBRestRequest;
import com.sokrati.fbRestAccessor.response.FBRestResponse;
import com.sokrati.fbRestAccessor.utils.FBRestCaller;

public class FBRestAccessor
{

	/*
	 * A class to make the end calls to facebook rest services
	 * 
	 * The accessor accepts the end urls and the object to be instantiated for 
	 * communication
	 */
    private final static String RESPONSE = "response";
    private final static String POST = "post";
    private final static String TYPE = "type";
    private final static String URL = "url";
    
    private final static String BATCH_REQUEST = "batch_request";
    private final static String BATCH_ENTITY = "batch_entity";

    private final static String IS_RETRIABLE = "is_retriable";
    private final static String RETRY_INTERVAL_S = "retry_interval_s";
    private final static String MAX_RETRY_ATTEMPTS = "max_retry_attempts";
    
    private JSONObject m_mapping = null;

    // Unusuable constructor
    private FBRestAccessor()
    {
    }

    /*
     * This constructor takes a mapping file that is json and contains 
     * the url to access for given request and the object to use
     */

    public FBRestAccessor(File mappingFile)
        throws InitializationError
    {
        init(mappingFile);
    }

    /*
     * This method reads a mapping file and consructs a json mapping to be
     * used later 
     */
    private void init(File file)
        throws InitializationError
    {
        try
        {
            StringBuffer buf = new StringBuffer();
            BufferedReader input = new BufferedReader(new FileReader(file));
            String text;
            while ((text = input.readLine()) != null)
            {
                buf.append(text + "\n");
            }
            m_mapping = new JSONObject(buf.toString());
        }
        catch (IOException e)
        {
            throw new InitializationError("Failed to initialize json mapping",
                                          e);
        }
        catch (org.json.JSONException e)
        {
            throw new InitializationError("Failed to initialize json mapping",
                                          e);
        }

    }

    /*
     * The call constructs FBRestCaller that does a get call
     *
     * @param key : identifies the call to make. It should be a key defined  
     *            in the mapping file
     * @param req : the request
     * @return : the FBRestResponse object
     */

    public FBRestResponse call(String key,
                               FBRestRequest req)
        throws MarshallingError, FBCommunicationError,
        InvalidConfigurationError, InitializationError,
        InvalidUrlReplaceParamError, AuthenticationFailureError
    {
        return call(key, null, null, req);
    }

    /*
     * The call constructs FBRestCaller that does a get call
     *
     * @param key : identifies the call to make. It should be a key defined  
     *            in the mapping file
     * @param req : the request
     * @param urlParamKey : the parameter to be replaced in the url
     * @param urlParamValue : the value of the parameter to be replaced 
     * in the url
     * @return : the FBRestResponse object
     */

    public FBRestResponse call(String key,
                               String urlParamKey,
                               String urlParamValue,
                               FBRestRequest req)
        throws MarshallingError, FBCommunicationError,
        InvalidConfigurationError, InitializationError,
        InvalidUrlReplaceParamError, AuthenticationFailureError
    {
        FBRestResponse out = null;
        try
        {
            JSONObject entry = (JSONObject) m_mapping.get(key);
            String callType = (String) (entry.get(TYPE));
            
            // Get URL
            String url = null;            
            if(urlParamKey != null)
            {
                url = getUpdatedUrl(key, urlParamKey, urlParamValue);
            }
            else
            {
                url = (String) (entry.get(URL));
            }
            
            // Get BatchRequest
            boolean batchRequest = false;
            try
            {
                batchRequest =
                    Boolean.parseBoolean((String) (entry
                        .getString(BATCH_REQUEST)));
            }
            catch (org.json.JSONException e)
            {
                batchRequest = false;
            }  
            
            // Check if the request is retriable
            boolean isRetriable = false;
            try
            {
                isRetriable =
                    Boolean.parseBoolean((String) (entry
                        .getString(IS_RETRIABLE)));
            }
            catch (org.json.JSONException e)
            {
                isRetriable = false;
            }
            // Construct request-retrial parameters
            RetriableRequestData retriableRequestData = null;
            if (isRetriable)
            {
                /*
                 * Get retry-interval. Default = 30 seconds
                 * */
                int retryIntervalS = 0;
                try
                {
                    retryIntervalS =
                        Integer.parseInt((String) entry
                            .getString(RETRY_INTERVAL_S));
                }
                catch (org.json.JSONException e)
                {
                    retryIntervalS = 30;
                }

                /*
                 * Get max-retry-attempts. Default = 10
                 * */
                int maxRetryAttempts = 0;
                try
                {
                    maxRetryAttempts =
                        Integer.parseInt((String) entry
                            .getString(MAX_RETRY_ATTEMPTS));
                }
                catch (org.json.JSONException e)
                {
                    maxRetryAttempts = 10;
                }

                retriableRequestData =
                    new RetriableRequestData(retryIntervalS, maxRetryAttempts);
            }
            else
            {
                retriableRequestData = new RetriableRequestData();
            }
            
            // Make a http-request
            if (callType.equals(POST))
            {
                if (batchRequest)
                {
                    out = FBRestCaller.executeBatchPost(url, req,
                              Class.forName((String) (entry.get(RESPONSE))),
                              retriableRequestData
                          );
                }
                else
                {
                    out = FBRestCaller.executePost(url, req,
                              Class.forName((String) (entry.get(RESPONSE))),
                              retriableRequestData
                          );
                }
            }
            else
            {
                if (batchRequest)
                {
                    out = FBRestCaller.executeBatchGet(url, req,
                            Class.forName((String) (entry.get(BATCH_ENTITY))),
                            retriableRequestData
                            );
                }
                else
                {
                    out = FBRestCaller.executeGet(
                        url, req,
                        Class.forName((String) (entry.get(RESPONSE))),
                        retriableRequestData
                        );
                }
               
            }
        }
        catch (org.json.JSONException e)
        {
            throw new InvalidConfigurationError("Failed to construct the "
                                                + "mapped call : ", e);
        }
        catch (java.lang.ClassNotFoundException e)
        {
            throw new InvalidConfigurationError("Failed to find the class", e);
        }
        
        
        if(out.hasAuthenticationFailed())
        {
            throw new AuthenticationFailureError("Facebook Authentication " +
            		"Failed : " + out.getError().getMessage());
        }
        
        return out;
    }    
    
    /*
     * This method returns the url key in the rest-accessor-config
     */
    public String getUrl(String key)
        throws InitializationError, InvalidConfigurationError
    {
        /*
         * This method should be called only after fb-rest-accessor is
         * initialized
         */
        if (m_mapping == null)
        {
            throw new InitializationError("FBRestaccessor not initialized with"
                                          + " fb-rest-accessor config");
        }

        String url = null;
        try
        {
            JSONObject entry = (JSONObject) m_mapping.get(key);
            url = (String) (entry.get(URL));
        }
        catch (JSONException e)
        {
            throw new InvalidConfigurationError("Failed to construct the "
                                                + "mapped call : ", e);
        }

        return url;

    }
    
    /*
     * This method replaces a given parameter in the url 
     * with the passed-in value
     */
    private String getUpdatedUrl(String key,
                                 String urlParamKey,
                                 String urlParamValue)
        throws InitializationError, InvalidConfigurationError,
        InvalidUrlReplaceParamError
    {        
        String oldUrl = getUrl(key);
                
        String updatedUrl = oldUrl.replaceAll(urlParamKey, urlParamValue);
        if (oldUrl.equals(updatedUrl))
        {
            // Old and new urls should be different
            throw new InvalidUrlReplaceParamError("Url replace param : "
                                                  + urlParamKey
                                                  + " Not found in url : "
                                                  + oldUrl);
        }

        return updatedUrl;
    }
                                       
}
