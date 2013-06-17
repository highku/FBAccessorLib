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
import com.sokrati.fbRestAccessor.response.FBRestResponse;

import java.util.*;

@JsonIgnoreProperties(ignoreUnknown = true)
public class AdGroupStats extends FBRestResponse
{
    private String m_id = null;
    private Long m_impressions = null;
    private Long m_clicks = null;
    private Double m_spent = null;
    private Long m_socialImpressions = null;
    private Long m_socialClicks = null;
    private Double m_socialSpent = null;
    private Long m_uniqueImpressions = null;
    private Long m_socialUniqueImpressions = null;
    private Long m_uniqueClicks = null;
    private Long m_socialUniqueClicks = null;
    private Long m_actions = null;
    private Long m_connections = null;
    private String m_adGroupId = null;
    private Date m_startTime = null;
    private Date m_endTime = null;

    public AdGroupStats()
    {
    }

    public String getId()
    {
        return m_id;
    }

    public Long getImpressions()
    {
        return m_impressions;
    }

    public Long getClicks()
    {
        return m_clicks;
    }

    public Double getSpent()
    {
        return m_spent;
    }

    public Long getSocial_impressions()
    {
        return m_socialImpressions;
    }

    public Long getSocial_clicks()
    {
        return m_socialClicks;
    }

    public Double getSocial_spent()
    {
        return m_socialSpent;
    }

    public Long getUnique_impressions()
    {
        return m_uniqueImpressions;
    }

    public Long getSocial_unique_impressions()
    {
        return m_socialUniqueImpressions;
    }

    public Long getUnique_clicks()
    {
        return m_uniqueClicks;
    }

    public Long getSocial_unique_clicks()
    {
        return m_socialUniqueClicks;
    }

    public Long getActions()
    {
        return m_actions;
    }

    public Long getConnections()
    {
        return m_connections;
    }

    public String getAdgroup_id()
    {
        return m_adGroupId;
    }

    public Date getStart_time()
    {
        return m_startTime;
    }

    public Date getEnd_time()
    {
        return m_endTime;
    }

    private void setId(String id)
    {
        m_id = id;
    }

    private void setImpressions(Long impressions)
    {
        m_impressions = impressions;
    }

    private void setClicks(Long clicks)
    {
        m_clicks = clicks;
    }

    private void setSpent(Double spent)
    {
        m_spent = spent;
    }

    private void setSocial_impressions(Long socialImpressions)
    {
        m_socialImpressions = socialImpressions;
    }

    private void setSocial_clicks(Long socialClicks)
    {
        m_socialClicks = socialClicks;
    }

    private void setSocial_spent(Double socialSpent)
    {
        m_socialSpent = socialSpent;
    }

    private void setUnique_impressions(Long uniqueImpressions)
    {
        m_uniqueImpressions = uniqueImpressions;
    }

    private void setSocial_unique_impressions(Long socialUniqueImpressions)
    {
        m_socialUniqueImpressions = socialUniqueImpressions;
    }

    private void setUnique_clicks(Long uniqueClicks)
    {
        m_uniqueClicks = uniqueClicks;
    }

    private void setSocial_unique_clicks(Long socialUniqueClicks)
    {
        m_socialUniqueClicks = socialUniqueClicks;
    }

    private void setActions(Long actions)
    {
        m_actions = actions;
    }

    private void setConnections(Long connections)
    {
        m_connections = connections;
    }

    private void setAdgroup_id(String adGroupId)
    {
        m_adGroupId = adGroupId;
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
        builder.append("id : " + m_id);
        builder.append("impressions : " + m_impressions);
        builder.append("clicks : " + m_clicks);
        builder.append("spent : " + m_spent);
        builder.append("socialImpressions : " + m_socialImpressions);
        builder.append("socialClicks : " + m_socialClicks);
        builder.append("socialSpent : " + m_socialSpent);
        builder.append("uniqueImpressions : " + m_uniqueImpressions);
        builder.append("socialUniqueImpressions : " + m_socialUniqueImpressions);
        builder.append("uniqueClicks : " + m_uniqueClicks);
        builder.append("socialUniqueClicks : " + m_socialUniqueClicks);
        builder.append("actions : " + m_actions);
        builder.append("connections : " + m_connections);
        builder.append("adGroupId : " + m_adGroupId);
        builder.append("startTime : " + m_startTime);
        builder.append("endTime : " + m_endTime);
        return builder.toString();
    }

}
