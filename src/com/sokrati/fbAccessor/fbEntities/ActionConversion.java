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

import java.io.Serializable;

import org.codehaus.jackson.annotate.JsonIgnoreProperties;

@JsonIgnoreProperties(ignoreUnknown = true)
public class ActionConversion implements Serializable
{
    private String m_actionType = null;
    private String m_objectId = null;
    private Integer m_postClick1d = null;
    private Integer m_postClick7d = null;
    private Integer m_postClick28d = null;
    private Integer m_postImp1d = null;
    private Integer m_postImp7d = null;
    private Integer m_postImp28d = null;
    
    public ActionConversion()
    {
    }
    
    public String getAction_type()
    {
        return m_actionType;
    }
    
    public String getObject_id()
    {
        return m_objectId;
    }
    
    public Integer getPost_click_1d()
    {
        return m_postClick1d;
    }
    
    public Integer getPost_click_7d()
    {
        return m_postClick7d;
    }
    
    public Integer getPost_click_28d()
    {
        return m_postClick28d;
    }
    
    public Integer getPost_imp_1d()
    {
        return m_postImp1d;
    }
    
    public Integer getPost_imp_7d()
    {
        return m_postImp7d;
    }
    
    public Integer getPost_imp_28d()
    {
        return m_postImp28d;
    }
    
    private void setAction_type(String action_type)
    {
        this.m_actionType = action_type;
    }
    
    private void setObject_id(String object_id)
    {
        this.m_objectId = object_id;
    }
    
    private void setPost_click_1d(Integer post_click_1d)
    {
        this.m_postClick1d = post_click_1d;
    }
    
    private void setPost_click_7d(Integer post_click_7d)
    {
        this.m_postClick7d = post_click_7d;
    }
    
    private void setPost_click_28d(Integer post_click_28d)
    {
        this.m_postClick28d = post_click_28d;
    }
    
    private void setPost_imp_1d(Integer post_imp_1d)
    {
        this.m_postImp1d = post_imp_1d;
    }
    
    private void setPost_imp_7d(Integer post_imp_7d)
    {
        this.m_postImp7d = post_imp_7d;
    }
    
    private void setPost_imp_28d(Integer post_imp_28d)
    {
        this.m_postImp28d = post_imp_28d;
    }

    @Override
    public String toString()
    {
        StringBuilder builder = new StringBuilder();
        builder.append("ActionConversion [m_actionType=").append(m_actionType)
            .append(", \n m_objectId=").append(m_objectId)
            .append(", \n m_postClick1d=").append(m_postClick1d)
            .append(", \n m_postClick7d=").append(m_postClick7d)
            .append(", \n m_postClick28d=").append(m_postClick28d)
            .append(", \n m_postImp1d=").append(m_postImp1d)
            .append(", \n m_postImp7d=").append(m_postImp7d)
            .append(", \n m_postImp28d=").append(m_postImp28d).append("]");
        return builder.toString();
    }
    
}
