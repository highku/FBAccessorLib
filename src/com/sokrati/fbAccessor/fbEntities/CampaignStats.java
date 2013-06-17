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

import java.util.Date;

import org.codehaus.jackson.annotate.JsonIgnoreProperties;

import com.sokrati.fbRestAccessor.response.FBRestResponse;

@JsonIgnoreProperties(ignoreUnknown = true)
public class CampaignStats extends FBRestResponse
{
    private String m_id = null;
    private String m_campaignId = null;
    private String m_toplineId = null;

    private Long m_impressions = null;
    private Long m_clicks = null;
    private Double m_spent = null;

    private Long m_socialImpressions = null;
    private Long m_socialClicks = null;
    private Double m_socialSpent = null;

    private Long m_uniqueImpressions = null;
    private Long m_uniqueClicks = null;

    private Long m_socialUniqueImpressions = null;
    private Long m_socialUniqueClicks = null;

    private Long m_actions = null;
    private Long m_connections = null;

    private Date m_startTime = null;
    private Date m_endTime = null;

    public CampaignStats()
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

    public String getCampaign_id()
    {
        return m_campaignId;
    }

    public Date getStart_time()
    {
        return m_startTime;
    }

    public Date getEnd_time()
    {
        return m_endTime;
    }

    public String getTopline_id()
    {
        return m_toplineId;
    }

    private void setId(String id)
    {
        m_id = id;
    }

    private void setTopline_id(String toplineId)
    {
        m_toplineId = toplineId;
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

    private void setCampaign_id(String adGroupId)
    {
        m_campaignId = adGroupId;
    }

    private void setStart_time(Date startTime)
    {
        m_startTime = startTime;
    }

    private void setEnd_time(Date endTime)
    {
        m_endTime = endTime;
    }

    @Override
    public String toString()
    {
        StringBuilder builder = new StringBuilder();
        builder.append("CampaignStats [m_actions=");
        builder.append(m_actions);
        builder.append(", m_campaignId=");
        builder.append(m_campaignId);
        builder.append(", m_clicks=");
        builder.append(m_clicks);
        builder.append(", m_connections=");
        builder.append(m_connections);
        builder.append(", m_endTime=");
        builder.append(m_endTime);
        builder.append(", m_id=");
        builder.append(m_id);
        builder.append(", m_impressions=");
        builder.append(m_impressions);
        builder.append(", m_socialClicks=");
        builder.append(m_socialClicks);
        builder.append(", m_socialImpressions=");
        builder.append(m_socialImpressions);
        builder.append(", m_socialSpent=");
        builder.append(m_socialSpent);
        builder.append(", m_socialUniqueClicks=");
        builder.append(m_socialUniqueClicks);
        builder.append(", m_socialUniqueImpressions=");
        builder.append(m_socialUniqueImpressions);
        builder.append(", m_spent=");
        builder.append(m_spent);
        builder.append(", m_startTime=");
        builder.append(m_startTime);
        builder.append(", m_toplineId=");
        builder.append(m_toplineId);
        builder.append(", m_uniqueClicks=");
        builder.append(m_uniqueClicks);
        builder.append(", m_uniqueImpressions=");
        builder.append(m_uniqueImpressions);
        builder.append("]");
        return builder.toString();
    }

}
