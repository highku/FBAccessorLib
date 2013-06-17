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
import org.codehaus.jackson.map.annotate.JsonSerialize.Inclusion;
import org.codehaus.jackson.map.annotate.JsonSerialize;

/*
 * Example array of request entity jsons:
 * [
             {
              "method": "POST",
              "name": "create_creative",
              "relative_url": "act_187687683/adcreatives",
              "attached_files": "test1",
              "body": "title=Test title&body=Test body&link_url=http://www.test12345.com&image_file=test1.jpg"
             },
             {
              "method": "POST",
              "relative_url": "act_187687683/adgroups",
              "body": "campaign_id=6004163746239&redownload=1&bid_type=1&max_bid=30&creative={\"creative_id\":\"{result=create_creative:$.id}\"}&targeting={\"countries\":[\"US\"]}&name=test1"
             },
             {
              "method": "POST",
              "relative_url": "act_187687683/adgroups",
              "body": "campaign_id=6004163746239&redownload=1&bid_type=1&max_bid=30&creative={\"creative_id\":\"{result=create_creative:$.id}\"}&targeting={\"countries\":[\"GB\"]}&name=test2"
             },
             {
              "method": "POST",
              "relative_url": "act_187687683/adgroups",
              "body": "campaign_id=6004163746239&redownload=1&bid_type=1&max_bid=30&creative={\"creative_id\":\"{result=create_creative:$.id}\"}&targeting={\"countries\":[\"IE\"]}&name=test3"
             }
            ]
 *
 */


@JsonSerialize(include = Inclusion.NON_NULL)
public class BatchRequestEntity
{
    private String m_method;    //mandatory field
    private String m_relativeURL;   //mandatory field
    private String m_body;  //mandatory field for post operations      
    private String m_name;
    private String m_attachedFiles;
    
    public static final String METHOD_POST = "POST";
    public static final String METHOD_GET = "GET";
    
    public BatchRequestEntity()
    {
    }        
    
    public BatchRequestEntity(String method, String relativeURL, String body, 
                 String name, String attachedFiles)
    {
        m_method = method;
        m_relativeURL = relativeURL;
        m_body = body;
        m_name = name;
        m_attachedFiles = attachedFiles;
    }
    
    public String getMethod()
    {
        return m_method;
    }
    
    public String getRelative_url()
    {
        return m_relativeURL;
    }
    
    public String getBody()
    {
        return m_body;
    }
    
    public String getName()
    {
        return m_name;
    }
    
    public String getAttached_files()
    {
        return m_attachedFiles;
    }
    
    private void setMethod(String method)
    {
        m_method = method;
    }
    
    private void setRelative_url(String relativeURL)
    {
        m_relativeURL = relativeURL;
    }
    
    private void setBody(String body)
    {
        m_body = body;
    }
    
    private void setName(String name)
    {
        m_name = name;
    }
    
    private void setAttached_files(String attachedFiles)
    {
        m_attachedFiles = attachedFiles;
    }
    
    @Override
    public String toString()
    {
        StringBuilder builder = new StringBuilder();
        builder.append("BatchRequestEntity [m_method=");
        builder.append(m_method);
        builder.append(", m_relativeURL=");
        builder.append(m_relativeURL);
        builder.append(", m_body=");
        builder.append(m_body);
        builder.append(", m_name=");
        builder.append(m_name);
        builder.append(", m_attachedFiles=");
        builder.append(m_attachedFiles);
        builder.append("]");
        return builder.toString();
    }
}
   
