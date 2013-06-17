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
import java.text.SimpleDateFormat;
import java.util.Date;

public class StatsRequestQueryParam implements Serializable
{
    private String m_dateFormat = null;
    private String m_startTime = null;
    private String m_endTime = null;
    
    public StatsRequestQueryParam(Date startTime,
                                  Date endTime)
    {
        SimpleDateFormat frmtr = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
        m_startTime = frmtr.format(startTime);
        m_endTime = frmtr.format(endTime);
        m_dateFormat = "U";
    }
    
    public String getDate_format()
    {
        return m_dateFormat;
    }
    
    private void setDate_format(String dateFormat)
    {
        m_dateFormat = dateFormat;
    }
    
    public String getStart_time()
    {
        return m_startTime;
    }
    
    private void setStart_time(String startTime)
    {
        m_startTime = startTime;
    }
    
    public String getEnd_time()
    {
        return m_endTime;
    }
    
    private void setEnd_time(String endTime)
    {
        m_endTime = endTime;
    }
    
    @Override
    public String toString()
    {
        StringBuilder builder = new StringBuilder();
        builder.append("QueryParam [m_dateFormat=");
        builder.append(m_dateFormat);
        builder.append(", m_endTime=");
        builder.append(m_endTime);
        builder.append(", m_startTime=");
        builder.append(m_startTime);
        builder.append("]");
        return builder.toString();
    }
}
