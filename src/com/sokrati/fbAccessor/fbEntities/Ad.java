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

package com.sokrati.fbAccessor.fbEntities;

import org.codehaus.jackson.annotate.JsonIgnore;
import org.codehaus.jackson.annotate.JsonIgnoreProperties;

import com.sokrati.fbRestAccessor.FBRestAccessor;

@JsonIgnoreProperties(ignoreUnknown = true)
public class Ad
{
    private String m_id = null;
    private String m_creativeId = null;
    private String m_title = null;
    private String m_body = null;
    private String m_imageHash = null;
    private String m_name = null;
    private String m_linkUrl = null;
    private String m_previewUrl = null;
    private String m_imageUrl = null;

    private Integer m_type = null;
    private Integer m_runStatus = null;
    private Integer m_countCurrentAdgroups = null;

    private AdGroup m_parent = null;   

    public Ad()
    {
    	
    }
    
    public String getId()
    {
        return m_id;
    }

    public String getTitle()
    {
        return m_title;
    }
    
    public String getCreative_id()
    {
        return m_creativeId;
    }

    public String getBody()
    {
        return m_body;
    }

    public String getImage_hash()
    {
        return m_imageHash;
    }

    public String getLink_url()
    {
        return m_linkUrl;
    }

    public String getPreview_url()
    {
        return m_previewUrl;
    }

    public String getImage_url()
    {
        return m_imageUrl;
    }

    public Integer getType()
    {
        return m_type;
    }

    public Integer getRun_status()
    {
        return m_runStatus;
    }

    public Integer getCount_current_adgroups()
    {
        return m_countCurrentAdgroups;
    }

    public String getName()
    {
        return m_name;
    }
    
    private void setName(String name)
    {
        this.m_name = name;
    }
    
    private void setId(String id)
    {
        this.m_id = id;
    }


    private void setTitle(String title)
    {
        this.m_title = title;
    }
    
    public void setCreative_id(String creativeId)
    {
        m_creativeId = creativeId;
    }

    private void setBody(String body)
    {
        this.m_body = body;
    }

    private void setImage_hash(String imageHash)
    {
        m_imageHash = imageHash;
    }

    public void setLink_url(String linkUrl)
    {
        m_linkUrl = linkUrl;
    }

    private void setPreview_url(String previewUrl)
    {
        m_previewUrl = previewUrl;
    }

    private void setImage_url(String imageUrl)
    {
        m_imageUrl = imageUrl;
    }

    private void setType(Integer type)
    {
        this.m_type = type;
    }

    private void setRun_status(Integer runStatus)
    {
        m_runStatus = runStatus;
    }

    private void setCount_current_adgroups(Integer countCurrentAdgroups)
    {
        m_countCurrentAdgroups = countCurrentAdgroups;
    }

    @JsonIgnore
    public ModeType getMode()
    {
        return m_parent.getMode();
    }

    @JsonIgnore
    public FBRestAccessor getFBRestAccessor()
    {
        return m_parent.getFBRestAccessor();
    }

    @JsonIgnore
    public AdGroup getParent()
    {
        return m_parent;
    }

    /*
     * This method is required to be public for the response to be complete
     * with parent information
     * */
    @JsonIgnore
    public void setParent(AdGroup parent)
    {
        m_parent = parent;
    }

    @Override
    public String toString()
    {
        StringBuilder builder = new StringBuilder();
        builder.append("Ad [m_body=");
        builder.append(m_body);
        builder.append(", m_countCurrentAdgroups=");
        builder.append(m_countCurrentAdgroups);
        builder.append(", m_creativeId=");
        builder.append(m_creativeId);
        builder.append(", m_id=");
        builder.append(m_id);
        builder.append(", m_name=");
        builder.append(m_name);
        builder.append(", m_imageHash=");
        builder.append(m_imageHash);
        builder.append(", m_imageUrl=");
        builder.append(m_imageUrl);
        builder.append(", m_linkUrl=");
        builder.append(m_linkUrl);        
        builder.append(", m_previewUrl=");
        builder.append(m_previewUrl);
        builder.append(", m_runStatus=");
        builder.append(m_runStatus);
        builder.append(", m_title=");
        builder.append(m_title);
        builder.append(", m_type=");
        builder.append(m_type);
        builder.append(", m_parent=");
        builder.append(m_parent);
        builder.append("]");
        return builder.toString();
    }
    
}
