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

package com.sokrati.fbAccessor;

import java.util.*;

import java.io.UnsupportedEncodingException;

import org.codehaus.jackson.map.ObjectMapper;
import org.json.JSONException;
import org.json.JSONObject;

import com.sokrati.fbRestAccessor.response.*;

import com.sokrati.fbAccessor.exceptions.*;

public class Utils
{
    public static final int CAMPAIGNS_BATCH_SIZE = 50;
    public static final int ADS_BATCH_SIZE = 100;
    
    /*
     * TODO: use arrayToCommaDelimitedString from   extended 
     * by org.springframework.util.StringUtils
     */
    
    /*
     * This method returns a csv of the strings list passed in
     * */
    public static String getCsvString(List<String> ids)
    {
        StringBuilder csv = new StringBuilder();

        if ((ids != null) && (ids.size() > 0))
        {
            csv.append(ids.get(0));
            for (int i = 1; i < ids.size(); i++)
            {
                csv.append(",");
                csv.append(ids.get(i));
            }
        }

        return csv.toString();
    }

    /*
     * This method returns a csv of the strings array passed in
     * */
    public static String getCsvString(String[] ids)
    {
        return getCsvString(new ArrayList<String>(Arrays.asList(ids)));
    }

    /*
     * This method returns a csv of the objects array passed in
     * */
    public static String getCsvString(Object[] values)
    {
        ArrayList<String> ids = null;
        if (values != null)
        {
            ids = new ArrayList<String>();
            for (int i = 0; i < values.length; i++)
            {
                ids.add(values[i].toString());
            }
        }
        return getCsvString(ids);
    }
    
    /*
     * This method accepts an object, and returns a query string.
     * Note: Only non null fields of the objects are included in the query
     * string.
     */
    public static String getQueryString(Object obj)
        throws FBAccessorError, UnsupportedEncodingException
    {
        // Construct a json from the given request object
        ObjectMapper om = new ObjectMapper();
        JSONObject json = null;
        try
        {
            json = new JSONObject(om.writeValueAsString(obj));
        }
        catch (java.io.IOException e)
        {
            throw new InvalidInputError("Unable to construct json " +
                                      "from object", e);
        }
        catch (JSONException e)
        {
            throw new InvalidInputError("Unable to construct json " +
                                      "from object", e);
        }
        // Construct a query-string
        StringBuffer buf = new StringBuffer();
        try
        {
            java.util.Iterator keyIter = json.keys();
            boolean first = true;
            while (keyIter.hasNext())
            {
                String key = (String) (keyIter.next());
                if ((json.getString(key) != null) &&
                    !(json.getString(key).equalsIgnoreCase("null")) &&
                    !(json.getString(key).trim().equals("")))
                {
                    if (!first)
                    {
                        buf.append("&");
                    }
                    buf.append(key)
                        .append("=")
                        .append(json.getString(key));
                    first = false;
                }
            }
        }
        catch (JSONException e)
        {
            throw new InvalidInputError("JSON Exception while creating " +
                                        "query string", e);
        }

        return buf.toString();
    }
    
    public static FBRestResponse getRespObject(String json, Class clz)
        throws InvalidInputError
    {
        FBRestResponse out = null;
        ObjectMapper om = new ObjectMapper();
        try
        {
            out = (FBRestResponse) om.readValue(json, clz);
        }
        catch (java.io.IOException e)
        {
            throw new InvalidInputError("Unable to fetch response object : ", e);
        }
        return out;
    }
}
