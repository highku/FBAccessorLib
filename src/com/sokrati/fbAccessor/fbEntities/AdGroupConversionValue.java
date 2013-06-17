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
import java.util.Arrays;
import java.util.Date;

import org.codehaus.jackson.annotate.JsonIgnoreProperties;

@JsonIgnoreProperties(ignoreUnknown = true)
public class AdGroupConversionValue implements Serializable
{
    private Date m_startTime = null;
    private Date m_endTime = null;
    private ActionConversion[] m_conversions = null;
    
    public AdGroupConversionValue()
    {
    }
    
    public Date getStart_time()
    {
        return m_startTime;
    }
    
    public Date getEnd_time()
    {
        return m_endTime;
    }
    
    public ActionConversion[] getConversions()
    {
        return m_conversions;
    }
    
    private void setConversions(ActionConversion[] conversions)
    {
        this.m_conversions = conversions;
    }
    
    public void setStart_time(Date startTime)
    {
        m_startTime = startTime;
    }
    
    public void setEnd_time(Date endTime)
    {
        m_endTime = endTime;
    }

    @Override
    public String toString()
    {
        StringBuilder builder = new StringBuilder();
        builder.append("AdGroupConversionValue [m_startTime=")
            .append(m_startTime).append(", \n m_endTime=").append(m_endTime)
            .append(", \n m_conversions=").append(Arrays.toString(m_conversions))
            .append("]");
        return builder.toString();
    }
    
}
