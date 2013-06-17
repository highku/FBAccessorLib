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

package com.sokrati.fbRestAccessor.utils;

import java.io.UnsupportedEncodingException;
import java.net.URLEncoder;
import java.util.*;

import org.codehaus.jackson.map.ObjectMapper;
import org.json.JSONException;
import org.json.JSONObject;

import com.sokrati.httpRequest.*;
import com.sokrati.fbRestAccessor.RetriableRequestData;
import com.sokrati.fbRestAccessor.exceptions.*;
import com.sokrati.fbRestAccessor.request.FBRestRequest;
import com.sokrati.fbRestAccessor.response.FBRestResponse;

public class FBRestCaller
{

    /**
     * A utility class that permits making the http call and 
     * constructs the responses out of the same 
     */
    
    private static final String ENCODING = "UTF-8";
    private static final String GET = "GET";
    private static final String POST = "POST";

    /*
     * Performs the HTTP call using the GET method.
     */
    public static FBRestResponse executeGet(String url,
                                            FBRestRequest req,
                                            Class respClazz, 
                                            RetriableRequestData retriableRequestData)
        throws MarshallingError, FBCommunicationError
    {
        String httpResponse = getHttpResponse(url, req, retriableRequestData);

        return response(httpResponse, respClazz);
    }

    /*
     * Performs the HTTP call using the GET method.
     * Returns a response which is a batch response
     * 
     * Sample batch response:
     * {
        "6003226498877": {
        "account_id": 347589464,
        "campaign_id": 6003226498877,
        "name": "BSB Mens Accessories",
        "daily_budget": 2000,
        "campaign_status": 2,
        "daily_imps": 0,
        "id": "6003226498877",
        "start_time": "2011-03-17T12:11:36+0000",
        "end_time": null,
        "updated_time": "2011-10-09T08:04:47+0000"
        },
        "6003226845477": {
        "account_id": 347589464,
        "campaign_id": 6003226845477,
        "name": "BSB mens clothing",
        "daily_budget": 5000,
        "campaign_status": 2,
        "daily_imps": 0,
        "id": "6003226845477",
        "start_time": "2011-03-17T12:37:48+0000",
        "end_time": null,
        "updated_time": "2011-10-09T08:04:50+0000"
        }
        }
     */
    public static FBRestResponse executeBatchGet(String url,
                                                 FBRestRequest req,
                                                 Class batchEntityClazz,
                                                 RetriableRequestData retriableRequestData)
        throws MarshallingError, FBCommunicationError
    {
        String httpResponse = getHttpResponse(url, req, retriableRequestData);
        return batchResponse(httpResponse, batchEntityClazz);
    }
    
    /*
     * Performs the HTTP call using the POST method.
     */
    public static FBRestResponse executePost(String url,
                                             FBRestRequest req,
                                             Class respClazz,
                                             RetriableRequestData retriableRequestData)
        throws MarshallingError, FBCommunicationError
    {
        String httpResponse = getHttpPostResponse(url, req, retriableRequestData);

        return response(httpResponse, respClazz);
    }
    
    /*
     * Performs the HTTP call using the POST method.
     */
    public static FBRestResponse executeBatchPost(String url,
                                                  FBRestRequest req,
                                                  Class batchEntityClazz,
                                                  RetriableRequestData retriableRequestData)
        throws FBCommunicationError, MarshallingError
    {
        String httpResponse = getHttpPostResponse(url, req, retriableRequestData);
        return batchPostResponse(httpResponse, batchEntityClazz);
    }
    
    /*
     * Returns reponse of batch post call.
     */
    private static FBRestResponse batchPostResponse(String json, Class clz)
        throws MarshallingError
    {
        String newJson;
        if (json.startsWith("{\"error\""))
        {
            newJson = json;
        }
        else
        {
            newJson = wrapOrigJson(json);
        }    

        return response(newJson, clz);
    }

    /*
     * Wraps original batch response json so it can be deserialized.
     */
    private static String wrapOrigJson(String json)
    {
        String out = "{\"batches\":" + json + "}";
        return out;
    }
        
    /*
     * this method gets post response from a url using HTTP request
     * */    
    private static String getHttpPostResponse(String url,
                                              FBRestRequest req,
                                              RetriableRequestData retriableRequestData)
        throws MarshallingError
    {
        HttpRequest request = new HttpRequest(url, true);
        String queryString;
        try
        {
            queryString = getQueryString(req);
        }
        catch (UnsupportedEncodingException e)
        {
            throw new MarshallingError("Unable to construct query "
                                       + "string : ", e);
        }
        catch (JSONException e)
        {
            throw new MarshallingError("Error while parsing JSON : ", e);
        }

        return callAndRetry(POST, request, retriableRequestData, queryString);
    }
    
    /*
     * this method gets response from a url using HTTP request
     * */
    private static String getHttpResponse(String url, 
                                          FBRestRequest req,
                                          RetriableRequestData retriableRequestData)
        throws MarshallingError
    {
        String queryString;
        try
        {
            queryString = getQueryString(req);
        }
        catch (UnsupportedEncodingException e)
        {
            throw new MarshallingError("Unable to construct query "
                                       + "string : ", e);
        }
        catch (JSONException e)
        {
            throw new MarshallingError("Error while parsing JSON : ", e);
        }
        HttpRequest request = new HttpRequest(url + "?" + queryString, true);              
        
        return callAndRetry(GET, request, retriableRequestData, null);
    }
    
    /*
     * This method does get/post call on the vendor. The call is retried 
     * on failure if it is a retriable call. 
     * The method returns the response string to the caller.
     **/
    private static String callAndRetry(String method, HttpRequest request,
                                       RetriableRequestData retriableRequestData,
                                       String body)
    {
        int retryAttempts = 0;
        do
        {
            if (method.equals(GET))
            {
                request.call();
            }
            else if (method.equals(POST) && (null != body))
            {
                request.doPost(body);
            }
            retryAttempts++;

            long startTime = System.currentTimeMillis();
            long timeElapsed = 0;
            long retryIntervalMS = 
                retriableRequestData.getRetryInterval() * 1000;

            while(timeElapsed < retryIntervalMS)
            {
                timeElapsed = System.currentTimeMillis() - startTime;
            }            
            
        }while((isRequestEligibleForRetrial(request, retriableRequestData)) && 
               (retryAttempts < retriableRequestData.getMaxRetryAttempts()));

        return request.getBody();
    }
    
    /*
     * This method returns a true/false based on if the current request 
     * is retriable or not
     * */
    private static boolean isRequestEligibleForRetrial(HttpRequest httpReq,
                                 RetriableRequestData retriableRequestData)
    {
        boolean ret = retriableRequestData.isRetriable();

        if (ret)
        {
            if ((httpReq.isRetriable()) ||
                ((httpReq.getErrorCode() >= 500) &&
                  (httpReq.getErrorCode() < 600)
                ))
            {
                ret = true;
            }
            else
            {
                ret = false;
            }
        }

        return ret;
    }
            
    /*
     * This method converts a fb-request object and converts it ito a query-string.
     * The call also does the necessary URL encoding.
     * */
    private static String getQueryString(FBRestRequest req)
        throws MarshallingError, UnsupportedEncodingException,
        JSONException
    {
        // Construct a json from the given request object
        ObjectMapper om = new ObjectMapper();
        JSONObject json = null;
        try
        {
            json = new JSONObject(om.writeValueAsString(req));
        }
        catch (java.io.IOException e)
        {
            throw new MarshallingError("Unable to construct json " +
                                       "from object", e);
        }
        catch (JSONException e)
        {
            throw new MarshallingError("Unable to construct json " +
                                       "from object", e);
        }
        // Construct a query-string
        StringBuffer buf = new StringBuffer();
        java.util.Iterator keyIter = json.keys();
        boolean first = true;
        while (keyIter.hasNext())
        {
            String key = (String) (keyIter.next());
            if (!first)
            {
                buf.append("&");
            }
            buf.append(key)
                .append("=")
                .append(URLEncoder.encode(json.getString(key), ENCODING));
            first = false;
        }

        return buf.toString();
    }

    /*
     * This method takes in a json and retuns an FBResponse object
     */
    private static FBRestResponse response(String json, Class respClazz)
        throws MarshallingError
    {
        FBRestResponse out = null;
        ObjectMapper om = new ObjectMapper();

        try
        {
            out = (FBRestResponse) om.readValue(json, respClazz);
        }
        catch (java.io.IOException e)
        {
            throw new MarshallingError("Unable to fetch response object : ", e);
        }

        return out;
    }
    
    /*
     * This method takes in a json and returns an FBResponse object 
     * which is a batch response
     */
    private static FBRestResponse batchResponse(String json,
                                                Class batchEntityClazz)
        throws MarshallingError
    {
        JSONObject jsonObj = null;
        try
        {
            jsonObj = new JSONObject(json);
        }
        catch (JSONException e)
        {
            throw new MarshallingError("Received a non-json response" +
                                       " : [" + json + "] ; Error : ", e);
        }

        ArrayList<Object> batchEntities =
            getBatchEntities(jsonObj, batchEntityClazz);

        return new FBRestResponse(batchEntities);
    }

    /*
     * This method returns an array-list of batch-entity objects from the given
     * json-object
     * */
    private static ArrayList<Object> getBatchEntities(JSONObject jsonObj,
                                                      Class batchEntityClazz)
        throws MarshallingError
    {
        ArrayList<Object> batchEntities = new ArrayList<Object>();
        String[] keys = JSONObject.getNames(jsonObj);
        ObjectMapper om = new ObjectMapper();
        for (String key : keys)
        {
            JSONObject entry = null;
            Object batchEntity = null;

            try
            {
                entry = (JSONObject) jsonObj.get(key);
                batchEntity = om.readValue(entry.toString(), batchEntityClazz);
            }
            catch (JSONException e)
            {
                throw new MarshallingError("Unable to get key " + key
                                           + " from response-json : "
                                           + jsonObj.toString()
                                           + " : ", e);
            }
            catch (java.io.IOException e)
            {
                throw new MarshallingError("Unable to fetch response object " +
                                           "from : " + entry.toString(), e);
            }

            batchEntities.add(batchEntity);
        }

        return batchEntities;
    }
}
