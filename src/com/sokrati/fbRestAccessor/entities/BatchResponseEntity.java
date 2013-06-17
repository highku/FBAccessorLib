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

package com.sokrati.fbRestAccessor.entities;

import org.codehaus.jackson.annotate.JsonIgnoreProperties;
import java.util.*;

/*
 * Example of array of batch response entities:
 * [
    { "code": 200, 
      "headers":[
          { "name": "Content-Type", 
            "value": "text/javascript; charset=UTF-8" }
      ],
      "body": "{\"id\":\"…\"}"},
    { "code": 200,
      "headers":[
          { "name":"Content-Type", 
            "value":"text/javascript; charset=UTF-8"}
      ],
      "body":"{\"data\": [{…}]}}
    ]
 *
 */

@JsonIgnoreProperties(ignoreUnknown = true)
public class BatchResponseEntity
{
    private Integer m_code;
    private ArrayList<BatchHeader> m_headers;
    
    /*
     * Facebook returns json string (escaped) as the body.
     * This will be deserialized in accessor lib depending on 
     * the object in the body.
     */
    private String m_body;      
    
    public BatchResponseEntity()
    {
    }
    
    public BatchResponseEntity(Integer code, ArrayList<BatchHeader> headers,
                               String body)
    {
        m_code = code;
        m_headers = headers;
        m_body = body;
    }
    
    public Integer getCode()
    {
        return m_code;
    }
    
    public ArrayList<BatchHeader> getHeaders()
    {
        return m_headers;
    }
    
    public String getBody()
    {
        return m_body;
    }
    
    private void setCode(Integer code)
    {
        m_code = code;
    }
    
    private void setHeaders(ArrayList<BatchHeader> headers)
    {
        m_headers = headers;
    }
    
    private void setBody(String body)
    {
        m_body = body;
    }
    
    @Override
    public String toString()
    {
        StringBuilder builder = new StringBuilder();
        builder.append("BatchResponseEntity [m_code=");
        builder.append(m_code);
        builder.append(", m_headers=");
        builder.append(m_headers);
        builder.append(", m_body=");
        builder.append(m_body);
        builder.append("]");
        return builder.toString();
    }
}    

