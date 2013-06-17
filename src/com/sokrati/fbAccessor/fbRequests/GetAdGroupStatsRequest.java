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

package com.sokrati.fbAccessor.fbRequests;

import java.util.*;
import java.text.*;

import com.sokrati.fbRestAccessor.request.FBRestRequest;

import org.codehaus.jackson.map.annotate.JsonSerialize.Inclusion;
import org.codehaus.jackson.map.annotate.JsonSerialize;
import org.codehaus.jackson.annotate.*;

@JsonSerialize(include = Inclusion.NON_NULL)
public class GetAdGroupStatsRequest extends FBRestRequest
{
    private String m_startTime = null;
    private String m_endTime = null;
    private String[] m_adGroupIds = null;

    private GetAdGroupStatsRequest()
    {
    }

    public GetAdGroupStatsRequest(String[] adGroupIds,
                                  Date startTime,
                                  Date endTime,
                                  String accessToken)
    {
        super(accessToken);
        SimpleDateFormat frmtr = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
        m_startTime = frmtr.format(startTime);
        m_endTime = frmtr.format(endTime);
        m_adGroupIds = adGroupIds;
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

    public String[] getAdgroup_ids()
    {
        return m_adGroupIds;
    }

    private void setAdgroup_ids(String[] adGroupIds)
    {
        m_adGroupIds = adGroupIds;
    }

    @JsonIgnore
    private String getAdGroupIdsAsCsv()
    {
        StringBuilder builder = new StringBuilder();
        if (m_adGroupIds.length > 0)
        {
            builder.append(m_adGroupIds[0]);
        }
        for (int i = 1 ; i < m_adGroupIds.length ; i++)
        {
            builder.append(", " + m_adGroupIds[i]);
        }
        return builder.toString();
    }

    @Override
    public String toString()
    {
        StringBuilder builder = new StringBuilder();
        builder.append("GetAdGroupStatsRequest [toString()=");
        builder.append(super.toString());
        builder.append("Start Time: " + m_startTime);
        builder.append("End time: " + m_endTime);
        builder.append("AdGroupIds: " + getAdGroupIdsAsCsv());
        builder.append("]");
        return builder.toString();
    }
}
