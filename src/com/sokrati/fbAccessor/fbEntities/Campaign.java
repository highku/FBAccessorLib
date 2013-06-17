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

import org.codehaus.jackson.JsonParseException;
import org.codehaus.jackson.annotate.JsonIgnore;
import org.codehaus.jackson.annotate.JsonIgnoreProperties;
import org.codehaus.jackson.map.JsonMappingException;
import org.codehaus.jackson.map.ObjectMapper;

import com.sokrati.fbAccessor.Constants;
import com.sokrati.fbAccessor.fbRequests.*;
import com.sokrati.fbAccessor.fbResponses.*;
import com.sokrati.fbAccessor.exceptions.IllegalAccessError;
import com.sokrati.fbAccessor.fbRequests.GetAdGroupByIdRequest;
import com.sokrati.fbAccessor.fbRequests.GetAdGroupsByCampaignRequest;
import com.sokrati.fbAccessor.fbResponses.GetAdGroupsByCampaignResponse;
import com.sokrati.fbRestAccessor.FBRestAccessor;
import com.sokrati.fbRestAccessor.entities.BatchResponseEntity;
import com.sokrati.fbRestAccessor.exceptions.*;
import com.sokrati.fbAccessor.exceptions.*;
import com.sokrati.fbRestAccessor.request.FBBatchRequest;
import com.sokrati.fbRestAccessor.response.FBBatchResponse;
import com.sokrati.fbRestAccessor.response.FBRestResponse;
import com.sokrati.fbAccessor.Utils;

import java.io.IOException;
import java.util.*;

@JsonIgnoreProperties(ignoreUnknown = true)
public class Campaign extends FBRestResponse
{
    private Account m_parent = null;
    
    private String m_id = null;
    private String m_accountId = null;
    private String m_name = null;
    private Long m_dailyBudget = null;
    private Long m_lifetimeBudget = null;
    private Integer m_campaign_status = null;
    private Long m_start_time = null;
    private Long m_end_time = null;
    private Long m_updated_time = null;
    
    // Unusable constructor
    private Campaign()
    {
    }
    
    protected Campaign(Account parentRef)
    {
        m_parent = parentRef;
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
    public Account getParent()
    {
        return m_parent;
    }
    
    /*
     * This method is required to be public for the response to be complete
     * with parent information
     * */
    @JsonIgnore
    public void setParent(Account parent)
    {
        m_parent = parent;
    }
    
    public String getId()
    {
        return m_id;
    }
    
    private void setId(String id)
    {
        this.m_id = id;
    }
    
    public String getAccount_id()
    {
        return m_accountId;
    }
    
    private void setAccount_id(String accountId)
    {
        m_accountId = accountId;
    }
    
    public String getName()
    {
        return m_name;
    }
    
    private void setName(String name)
    {
        this.m_name = name;
    }
    
    public Long getDaily_budget()
    {
        return m_dailyBudget;
    }
    
    private void setDaily_budget(Long dailyBudget)
    {
        m_dailyBudget = dailyBudget;
    }
    
    private void setCampaign_status(Integer campaign_status)
    {
        this.m_campaign_status = campaign_status;
    }
    
    public Integer getCampaign_status()
    {
        return m_campaign_status;
    }
    
    private void setStart_time(Long start_time)
    {
        this.m_start_time = start_time;
    }
    
    public Long getStart_time()
    {
        return m_start_time;
    }
    
    private void setEnd_time(Long end_time)
    {
        this.m_end_time = end_time;
    }
    
    public Long getEnd_time()
    {
        return m_end_time;
    }
    
    private void setUpdated_time(Long updated_time)
    {
        this.m_updated_time = updated_time;
    }
    
    public Long getUpdated_time()
    {
        return m_updated_time;
    }
    
    private void setLifetime_budget(Long lifetime_budget)
    {
        this.m_lifetimeBudget = lifetime_budget;
    }
    
    public Long getLifetime_budget()
    {
        return m_lifetimeBudget;
    }
    
    @JsonIgnore
    public AdGroup[] getAllAdGroups(String accessToken)
        throws IllegalAccessError, MarshallingError, FBCommunicationError,
        InvalidConfigurationError, InitializationError,
        InvalidUrlReplaceParamError, AuthenticationFailureError
    {
        if (!ModeType.isModeReadOnly(getMode()))
        {
            throw new IllegalAccessError(IllegalAccessError.METHOD_NOT_ALLOWED
                                         + getMode().toString());
        }
        
        GetAdGroupsByCampaignRequest req =
            new GetAdGroupsByCampaignRequest(accessToken);
        
        GetAdGroupsByCampaignResponse res =
            (GetAdGroupsByCampaignResponse) getFBRestAccessor().call(
                Constants.GET_ADGROUPS_BY_CAMPAIGN,
                Constants.URL_REPLACE_PARAM_CAMPAIGN, m_id, req);
        
        AdGroup[] out = res.getData();
        
        if (null != out)
        {
            for (int i = 0; i < out.length; i++)
            {
                out[i].setParent(this);
            }
        }
        
        return out;
    }
    
    @JsonIgnore
    public AdGroupStats[] getAllAdGroupStats(String[] adGroupIds,
                                             String accessToken,
                                             Date startTime,
                                             Date endTime)
        throws IllegalAccessError, MarshallingError, FBCommunicationError,
        InvalidConfigurationError, InitializationError,
        InvalidUrlReplaceParamError, InvalidInputError,
        AuthenticationFailureError
    {
        ArrayList<AdGroupStats> fbAdGroupStats = new ArrayList<AdGroupStats>();
        
        if (!ModeType.isModeReadOnly(getMode()))
        {
            throw new IllegalAccessError(IllegalAccessError.METHOD_NOT_ALLOWED
                                         + getMode().toString());
        }
        
        if (endTime.before(startTime))
        {
            throw new InvalidInputError("End time: " + endTime
                                        +
                                        " can not be less than start time: "
                                        + startTime);
        }
        
        AdGroupStats[] stats = null;
        if (adGroupIds != null)
        {
            for (int fromIndex = 0, toIndex = 0;
                fromIndex < adGroupIds.length;
                fromIndex = toIndex)
            {
                toIndex = fromIndex + Utils.ADS_BATCH_SIZE;
                if (toIndex > adGroupIds.length)
                {
                    toIndex = adGroupIds.length;
                }
                String[] adGroupIdsBatch = Arrays.copyOfRange(adGroupIds,
                    fromIndex, toIndex);
                
                GetAdGroupStatsRequest request =
                    new GetAdGroupStatsRequest(adGroupIdsBatch, startTime,
                        endTime, accessToken);
                
                GetAdGroupStatsResponse response =
                    (GetAdGroupStatsResponse) getFBRestAccessor().call(
                        Constants.GET_ADGROUP_STATS,
                        Constants.URL_REPLACE_PARAM_ACCOUNT,
                        m_parent.getId(),
                        request);
                
                AdGroupStats[] out = response.getAdGroupStats();
                
                if (out != null)
                {
                    fbAdGroupStats.addAll(Arrays.asList(out));
                }
            }
            
            if (fbAdGroupStats != null)
            {
                stats = new AdGroupStats[fbAdGroupStats.size()];
                stats = fbAdGroupStats.toArray(stats);
                updateStartAndEndtime(stats, startTime, endTime);
            }
        }
        
        return stats;
    }
    
    /*
     * Helper method
     */
    @JsonIgnore
    public ArrayList<AdGroupConversionStats> getAdGroupConversionStats(
        String acessToken,
        String[] adGroupIds,
        Date startTime,
        Date endTime)
        throws MarshallingError, FBCommunicationError,
        InvalidConfigurationError, InitializationError,
        InvalidUrlReplaceParamError, AuthenticationFailureError,
        FBAccessorError
    {
        ArrayList<String> adGroupIdsList = new ArrayList<String>();
        if (adGroupIds != null)
        {
            for (int i = 0; i < adGroupIds.length; i++)
            {
                adGroupIdsList.add(adGroupIds[i]);
            }
        }
        return getAdGroupConversionStats(acessToken, adGroupIdsList, startTime,
            endTime);
    }
    
    /*
     * This method gets action conversion stats for a given batch of ad_grops 
     * from fb
     */
    @JsonIgnore
    public ArrayList<AdGroupConversionStats> getAdGroupConversionStats(
        String acessToken, ArrayList<String> adGroupIds, Date startTime,
        Date endTime
    )
        throws MarshallingError, FBCommunicationError,
        InvalidConfigurationError, InitializationError,
        InvalidUrlReplaceParamError, AuthenticationFailureError,
        FBAccessorError
    {
        // Get conversion stats in batches
        ArrayList<AdGroupConversionStats> fbAdGroupConversionStats =
            new ArrayList<AdGroupConversionStats>();
        
        // Check lib-initialization mode
        if (!ModeType.isModeReadOnly(getMode()))
        {
            throw new IllegalAccessError(IllegalAccessError.METHOD_NOT_ALLOWED
                                         + getMode().toString());
        }
        // Validate input
        if (adGroupIds != null)
        {
            // It is possible that a campaign may not have any ad_groups
            if (startTime == null)
            {
                throw new InvalidInputError("Invalid start-date " + startTime);
            }
            
            if (endTime == null)
            {
                throw new InvalidInputError("Invalid end-date " + startTime);
            }
            
            if (startTime.after(endTime))
            {
                throw new InvalidInputError("Invalid start-date " + startTime
                                            + "cannot be after end-date "
                                            + endTime);
            }
            
            for (int fromIndex = 0, toIndex = 0;
                fromIndex < adGroupIds.size();
                fromIndex = toIndex)
            {
                toIndex = fromIndex + FBBatchRequest.getBatchSize();
                if (toIndex > adGroupIds.size())
                {
                    toIndex = adGroupIds.size();
                }
                
                List<String> adGroupIdsBatch =
                    adGroupIds.subList(fromIndex, toIndex);
                
                ArrayList<AdGroupConversionStats> adGroupsBatch =
                    getFbAdGroupConversionStatsInBatch(acessToken,
                        adGroupIdsBatch,
                        startTime,
                        endTime);
                
                fbAdGroupConversionStats.addAll(adGroupsBatch);
            }
        }
        
        return fbAdGroupConversionStats;
    }
    
    /*
     * This method gets the action conversion stats for a given batch of ad_groups
     */
    @JsonIgnore
    private ArrayList<AdGroupConversionStats>
    getFbAdGroupConversionStatsInBatch(
        String acessToken, List<String> adGroupIdsBatch, Date startTime,
        Date endTime
    )
        throws FBAccessorError, MarshallingError, FBCommunicationError,
        InvalidConfigurationError, InitializationError,
        InvalidUrlReplaceParamError, AuthenticationFailureError
    {
        GetAdGroupConversionStatsByIdsRequest req =
            new GetAdGroupConversionStatsByIdsRequest(acessToken,
                adGroupIdsBatch,
                startTime, endTime);
        
        FBBatchResponse response = (FBBatchResponse) getFBRestAccessor().call(
            Constants.BATCH_CALL,
            req);
        
        return getAdGroupConversionStats(response, startTime, endTime);
    }
    
    /*
     * This method gets ad_group_conversion stats from the response obtained
     */
    @JsonIgnore
    private static ArrayList<AdGroupConversionStats>
    getAdGroupConversionStats(
        FBBatchResponse response, Date startTime, Date endTime
    )
        throws MarshallingError, InvalidConfigurationError
    {
        ArrayList<AdGroupConversionStats> adGroupsStats =
            new ArrayList<AdGroupConversionStats>();
        ArrayList<BatchResponseEntity> adGroupsBatch = response.getBatches();
        if (adGroupsBatch != null)
        {
            ObjectMapper om = new ObjectMapper();
            
            try
            {
                for (BatchResponseEntity entity : adGroupsBatch)
                {
                    AdGroupConversionStats adGroupConversionStat =
                        om.readValue(entity.getBody(),
                            AdGroupConversionStats.class);
                    updateStartAndEndtime(adGroupConversionStat, startTime,
                        endTime);
                    adGroupsStats.add(adGroupConversionStat);
                }
            }
            catch (JsonParseException e)
            {
                throw new MarshallingError(e);
            }
            catch (JsonMappingException e)
            {
                throw new MarshallingError(e);
            }
            catch (IOException e)
            {
                throw new InvalidConfigurationError(e);
            }
            
        }
        
        return adGroupsStats;
    }
    
    /*
     * This method sets the start and end date for the given action-conversion-stat     
     */
    @JsonIgnore
    private static void updateStartAndEndtime(
        AdGroupConversionStats adGroupConversionStat,
        Date startTime, Date endTime)
    {
        if (adGroupConversionStat != null)
        {
            AdGroupConversionValue[] values = adGroupConversionStat.getValues();
            if (values != null)
            {
                for (AdGroupConversionValue value : values)
                {
                    /*
                     *  This is required as the conversion from epoch to a 
                     *  human readable date is not correct  
                     */
                    value.setStart_time(startTime);
                    value.setEnd_time(endTime);
                }
            }
        }
        
    }
    
    /*
     * This method sets the start and end date for the given adgroup stats     
     */
    @JsonIgnore
    private static void updateStartAndEndtime(AdGroupStats[] adGroupStats,
                                              Date startTime,
                                              Date endTime)
    {
        if (adGroupStats != null)
        {
            for (AdGroupStats stat : adGroupStats)
            {
                /*
                 *  This is required as the conversion from epoch to a 
                 *  human readable date is not correct  
                 */
                stat.setStart_time(startTime);
                stat.setEnd_time(endTime);
            }
        }
        
    }
    
    /*
     * This method gives the fb adGroup for the given id.
     * This is required as we don't get creative-ids in the get-all-adgroups
     * request's response
     * */
    @JsonIgnore
    public AdGroup getAdGroupById(String acessToken, String adGroupId)
        throws IllegalAccessError, MarshallingError, FBCommunicationError,
        InvalidConfigurationError, InitializationError,
        InvalidUrlReplaceParamError, AuthenticationFailureError
    {
        // Check lib-initialization mode
        if (!ModeType.isModeReadOnly(getMode()))
        {
            throw new IllegalAccessError(IllegalAccessError.METHOD_NOT_ALLOWED
                                         + getMode().toString());
        }
        
        // Create a request
        GetAdGroupByIdRequest req = new GetAdGroupByIdRequest(acessToken);
        
        // Make a call to fb to get account
        AdGroup response = (AdGroup) getFBRestAccessor().call(
            Constants.GET_AD_GROUP_BY_ID,
            Constants.URL_REPLACE_PARAM_AD_GROUP,
            adGroupId,
            req);
        
        response.setParent(this);
        
        return response;
    }
    
    @Override
    public String toString()
    {
        StringBuilder builder = new StringBuilder();
        builder.append("Campaign [campaign_status=");
        builder.append(m_campaign_status);
        builder.append(", end_time=");
        builder.append(m_end_time);
        builder.append(", m_accountId=");
        builder.append(m_accountId);
        builder.append(", m_dailyBudget=");
        builder.append(m_dailyBudget);
        builder.append(", m_lifetimeBudget=");
        builder.append(m_lifetimeBudget);
        builder.append(", m_id=");
        builder.append(m_id);
        builder.append(", m_name=");
        builder.append(m_name);
        builder.append(", m_parent=");
        builder.append(m_parent);
        builder.append(", start_time=");
        builder.append(m_start_time);
        builder.append(", updated_time=");
        builder.append(m_updated_time);
        builder.append("]");
        return builder.toString();
    }
}
