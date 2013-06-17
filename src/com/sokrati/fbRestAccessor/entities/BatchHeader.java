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

@JsonIgnoreProperties(ignoreUnknown = true)
public class BatchHeader
{
    private String m_name;
    private String m_value;
    
    public BatchHeader()
    {
    }
    
    public BatchHeader(String name, String value)
    {
        m_name = name;
        m_value = value;
    }
    
    public String getName()
    {
        return m_name;
    }
    
    public String getValue()
    {
        return m_value;
    }
    
    private void setName(String name)
    {
        m_name = name;
    }
    
    private void setValue(String value)
    {
        m_value = value;
    }
    
    @Override
    public String toString()
    {
        StringBuilder builder = new StringBuilder();
        builder.append("BatchHeader [m_name=");
        builder.append(m_name);
        builder.append(", m_value=");
        builder.append(m_value);
        builder.append("]");
        return builder.toString();
    }
        
}
