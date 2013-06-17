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

package com.sokrati.fbAccessor.fbResponses;

import java.util.*;
import org.codehaus.jackson.annotate.*;
import com.sokrati.fbAccessor.fbEntities.AdGroupStats;
import com.sokrati.fbRestAccessor.response.FBRestResponse;

@JsonIgnoreProperties(ignoreUnknown = true)
public class GetAdGroupStatsResponse extends FBRestResponse
{
    private Date m_startTime = null;
    private Date m_endTime = null;

    private AdGroupStats[] m_data = null;
    private Integer m_count = null;
    private Integer m_limit = null;
    private Integer m_offset = null;

    public GetAdGroupStatsResponse()
    {
    }

    public Date getStart_time()
    {
        return m_startTime;
    }

    private void setStart_time(Date startTime)
    {
        m_startTime = startTime;
    }

    public Date getEnd_time()
    {
        return m_endTime;
    }

    private void setEnd_time(Date endTime)
    {
        m_endTime = endTime;
    }

    public AdGroupStats[] getData()
    {
        return m_data;
    }

    private void setData(AdGroupStats[] data)
    {
        m_data = data;
    }

    @JsonIgnore
    public AdGroupStats[] getAdGroupStats()
    {
        return m_data;
    }

    public Integer getCount()
    {
        return m_count;
    }

    private void setCount(Integer count)
    {
        m_count = count;
    }

    public Integer getOffset()
    {
        return m_offset;
    }

    private void setOffset(Integer offset)
    {
        m_offset = offset;
    }

    public Integer getLimit()
    {
        return m_limit;
    }

    private void setLimit(Integer limit)
    {
        m_limit = limit;
    }

    @Override
    public String toString()
    {
        StringBuilder builder = new StringBuilder();
        builder.append("GetAdGroupStatsResponse [m_data=");
        builder.append(Arrays.toString(m_data));
        builder.append("StartTime: " + m_startTime);
        builder.append("EndTime: " + m_endTime);
        builder.append("Count: " + m_count);
        builder.append("limit: " + m_limit);
        builder.append("Offset: " + m_offset);
        builder.append("]");
        return builder.toString();
    }

}



