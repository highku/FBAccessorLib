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

import java.io.IOException;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Set;

import org.codehaus.jackson.JsonParseException;
import org.codehaus.jackson.annotate.JsonIgnore;
import org.codehaus.jackson.annotate.JsonIgnoreProperties;
import org.codehaus.jackson.map.JsonMappingException;
import org.codehaus.jackson.map.ObjectMapper;

import com.sokrati.fbAccessor.Constants;
import com.sokrati.fbAccessor.Utils;
import com.sokrati.fbAccessor.exceptions.FBAccessorError;
import com.sokrati.fbAccessor.exceptions.IllegalAccessError;
import com.sokrati.fbAccessor.exceptions.InvalidInputError;
import com.sokrati.fbAccessor.fbRequests.*;
import com.sokrati.fbAccessor.fbResponses.GetAllCampaignsForAccountResponse;
import com.sokrati.fbAccessor.fbResponses.AdGroupPostResponse;
import com.sokrati.fbRestAccessor.FBRestAccessor;
import com.sokrati.fbRestAccessor.exceptions.*;
import com.sokrati.fbRestAccessor.entities.*;
import com.sokrati.fbRestAccessor.request.*;
import com.sokrati.fbRestAccessor.response.*;

@JsonIgnoreProperties(ignoreUnknown = true)
public class Account extends FBRestResponse
{
    private AccountGroup m_parent = null;

    private String m_id = null;
    private String m_accountId = null;
    private String m_name = null;
    private String m_businessName = null;
    private Integer m_accountStatus = null;
    private Integer m_timezoneId = null;
    private String m_timezoneName = null;
    private String m_currency = null;
    private Long m_dailySpendLimit = null;


    // Unusable constructor
    private Account()
    {
    }

    protected Account(AccountGroup parentRef)
    {
        m_parent = parentRef;
    }

    /*
     * This method gives the fb campaign for the given id
     * */
    @JsonIgnore
    public Campaign getCampaignById(String acessToken, String campaignId)
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
        GetCampaignByIdRequest req = new GetCampaignByIdRequest(acessToken);
        
        // Make a call to fb to get account
        Campaign response = (Campaign) getFBRestAccessor().call(
            Constants.GET_CAMPAIGN_BY_ID,
            Constants.URL_REPLACE_PARAM_CAMPAIGN,
            campaignId,
            req);       
        
        response.setParent(this);

        return response;
    }
    
    /*
     * This method gives the fb campaign for the given id
     * */
    @JsonIgnore
    public ArrayList<CampaignStats> getCampaignStatsByIds(
        String acessToken, ArrayList<String> campaignIds, Date startTime,
        Date endTime
    )
        throws MarshallingError, FBCommunicationError,
        InvalidConfigurationError, InitializationError,
        InvalidUrlReplaceParamError, AuthenticationFailureError,
        FBAccessorError
    {
        // Check lib-initialization mode
        if (!ModeType.isModeReadOnly(getMode()))
        {
            throw new IllegalAccessError(IllegalAccessError.METHOD_NOT_ALLOWED
                                                     + getMode().toString());
        }

        if ((campaignIds == null) || (campaignIds.size() < 1))
        {
            throw new InvalidInputError("Campaign-ids can't be empty");
        }

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
            throw new InvalidInputError(
                "Invalid start-date " + startTime +
                "cannot be after end-date " + endTime
            );
        }

        ArrayList<CampaignStats> fbCampaignStats =
            new ArrayList<CampaignStats>();

        for (int fromIndex = 0, toIndex = 0;
             fromIndex < campaignIds.size();
             fromIndex = toIndex)
        {
            toIndex = fromIndex + FBBatchRequest.getBatchSize();
            if (toIndex > campaignIds.size())
            {
                toIndex = campaignIds.size();
            }

            List<String> campaignIdsBatch =
                campaignIds.subList(fromIndex, toIndex);

            ArrayList<CampaignStats> campaignsBatch =
                getFbCampaignsStatsInBatch(acessToken,
                                           campaignIdsBatch,
                                           startTime,
                                           endTime);

            fbCampaignStats.addAll(campaignsBatch);
        }

        return fbCampaignStats;
    }
    
    /*
     * This method gives the fb campaign for the given id
     * */
    @JsonIgnore
    public ArrayList<Campaign> getCampaignsByIds(String acessToken,
                                                 ArrayList<String> campaignIds)
        throws MarshallingError, FBCommunicationError,
        InvalidConfigurationError, InitializationError,
        InvalidUrlReplaceParamError, AuthenticationFailureError, FBAccessorError
    {
        // Check lib-initialization mode
        if (!ModeType.isModeReadOnly(getMode()))
        {
            throw new IllegalAccessError(IllegalAccessError.METHOD_NOT_ALLOWED
                                                     + getMode().toString());
        }
        
        if( (campaignIds == null) || (campaignIds.size() < 1))
        {
            throw new InvalidInputError("Campaign-ids can't be empty"); 
        }

        ArrayList<Campaign> fbCampaigns = new ArrayList<Campaign>();

        for (int fromIndex = 0, toIndex = 0; fromIndex < campaignIds.size(); 
             fromIndex = toIndex)
        {
            toIndex = fromIndex + FBBatchRequest.getBatchSize();
            if (toIndex > campaignIds.size())
            {
                toIndex = campaignIds.size();
            }

            List<String> campaignIdsBatch =
                campaignIds.subList(fromIndex, toIndex);

            ArrayList<Campaign> campaignsBatch =
                getFbCampaignsInBatch(acessToken, campaignIdsBatch);

            fbCampaigns.addAll(campaignsBatch);
        }

        return fbCampaigns;
    }

    /*
     * This method gets campaigns from fb in a batch for given campaign ids
     * */
    @JsonIgnore
    private ArrayList<CampaignStats> getFbCampaignsStatsInBatch(
        String acessToken, List<String> campaignIdsBatch, Date startTime,
        Date endTime
    )
        throws MarshallingError, FBCommunicationError,
        InvalidConfigurationError, InitializationError,
        InvalidUrlReplaceParamError, AuthenticationFailureError,
        FBAccessorError
    {
        GetCampaignStatsByIdsRequest req =
            new GetCampaignStatsByIdsRequest(acessToken,
                                             campaignIdsBatch,
                                             startTime, endTime);

        FBBatchResponse response = (FBBatchResponse) getFBRestAccessor().call(
            Constants.BATCH_CALL,
            req);

        return getCampaignsStats(response);
    }
    
    /*
     * This method gets campaign stats object from the response obtained from FB
     * */
    private ArrayList<CampaignStats> getCampaignsStats(FBBatchResponse response)
        throws MarshallingError, InvalidConfigurationError
    {
        ArrayList<CampaignStats> campaignStats = null;
        ArrayList<BatchResponseEntity> campaignsBatch = response.getBatches();
        if (campaignsBatch != null)
        {
            campaignStats = new ArrayList<CampaignStats>();
            ObjectMapper om = new ObjectMapper();

            try
            {
                for (BatchResponseEntity entity : campaignsBatch)
                {
                    CampaignStats campaign =
                        om.readValue(entity.getBody(), CampaignStats.class);
                    campaignStats.add(campaign);
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

        return campaignStats;
    }

    /*
     * This method gets campaigns from fb in a batch for given campaign ids
     * */
    @JsonIgnore
    private ArrayList<Campaign> getFbCampaignsInBatch(
        String acessToken, List<String> campaignIdsBatch
    )
        throws MarshallingError, FBCommunicationError,
        InvalidConfigurationError, InitializationError,
        InvalidUrlReplaceParamError, AuthenticationFailureError, FBAccessorError
    {
        GetCampaignsByIdsBatchRequest req =
            new GetCampaignsByIdsBatchRequest(acessToken, campaignIdsBatch);

        FBBatchResponse response = (FBBatchResponse) getFBRestAccessor().call(
            Constants.BATCH_CALL,
            req);

        return getCampaigns(response);
    }

    /*
     * This method gets Campaigns from FBRestResponse
     * */
    @JsonIgnore
    private ArrayList<Campaign> getCampaigns(FBBatchResponse response)
        throws MarshallingError, InvalidConfigurationError
    {
        ArrayList<Campaign> campaigns = null;
        ArrayList<BatchResponseEntity> campaignsBatch = response.getBatches();
        if (campaignsBatch != null)
        {
            campaigns = new ArrayList<Campaign>();
            ObjectMapper om = new ObjectMapper();

            try
            {
                for (BatchResponseEntity entity : campaignsBatch)
                {
                    Campaign campaign = om.readValue(entity.getBody(),
                                                     Campaign.class);
                    campaign.setParent(this);
                    campaigns.add(campaign);
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

        return campaigns;
    }  
    
    /*
     * This method gives the fb campaign for the given id
     * */
    @JsonIgnore
    public ArrayList<Campaign> getAllCampaigns(String acessToken)
        throws IllegalAccessError, MarshallingError, FBCommunicationError,
        InvalidConfigurationError, InitializationError,
        InvalidUrlReplaceParamError, InvalidInputError,
        AuthenticationFailureError
    {
        // Check lib-initialization mode
        if (!ModeType.isModeReadOnly(getMode()))
        {
            throw new IllegalAccessError(IllegalAccessError.METHOD_NOT_ALLOWED
                                                     + getMode().toString());
        }

        // Create a request
        GetAllCampaignsForAccountRequest req =
            new GetAllCampaignsForAccountRequest(acessToken, 
                                                 this.getId());

        // Make a call to fb to get Campaign
        GetAllCampaignsForAccountResponse response = null;

        ArrayList<Campaign> campaigns = new ArrayList<Campaign>(); 
                
        int i=0;
        do
        {
            System.out.println("Batch # " + i);
            response =
                (GetAllCampaignsForAccountResponse) getFBRestAccessor().call(
                    Constants.GET_CAMPAIGNS_FOR_ACCOUNT,
                    Constants.URL_REPLACE_PARAM_ACCOUNT,
                    req.getAccountId(),
                    req);
            
            // Campaign objects needs to have the ref of its parent
            for (Campaign cmp : response.getData())
            {
                cmp.setParent(this);
                campaigns.add(cmp);
            }
            
            // Go to the next batch
            int offset = response.getOffset() + response.getLimit();
            System.out.println("Offset : " + offset);
            req.setOffset(offset);                        
            
            i++;
        } while (response.getPaging().hasNext());

        return campaigns;
    }
    
    @JsonIgnore
    public List<AdGroup> updateAdGroups(
        List<AdGroup> adGroups, String accessToken
    )
        throws FBAccessorError, MarshallingError, InitializationError,
               FBCommunicationError, InvalidConfigurationError,
               InvalidUrlReplaceParamError, InvalidInputError,
               AuthenticationFailureError
    {
        // Check lib-initialization mode
        if (!ModeType.isModeWriteOnly(getMode()))
        {
            throw new IllegalAccessError(IllegalAccessError.METHOD_NOT_ALLOWED
                                                     + getMode().toString());
        }
        ArrayList<BatchRequestEntity> batches =
            new ArrayList<BatchRequestEntity>();
        try
        {
            for (AdGroup adGroup : adGroups)
            {
                batches.add(new BatchRequestEntity(
                    Constants.POST, adGroup.getAdgroup_id(), 
                     Utils.getQueryString(adGroup),null, null)
                );
            }
        }
        catch (java.io.UnsupportedEncodingException e)
        {
            throw new InvalidInputError("IO Exception encountered " +
                                        "while generating query string " +
                                        "for ad group", e);
        }
        FBBatchResponse resp = (FBBatchResponse) getFBRestAccessor().call(
            Constants.BATCH_CALL, new FBBatchRequest(accessToken, batches)
        );
        
        return processUpdateAdGroupsResponse(resp, adGroups);
    }                               
    
    @JsonIgnore
    private List<AdGroup> processUpdateAdGroupsResponse(
        FBBatchResponse resp, List<AdGroup> adGroups
    )
        throws FBAccessorError, MarshallingError, InitializationError,
               FBCommunicationError, InvalidConfigurationError,
               InvalidUrlReplaceParamError, InvalidInputError,
               AuthenticationFailureError
    {
        if (resp.getError() != null)
        {
            for(AdGroup adGroup : adGroups)
            {
                adGroup.setStatusDetail(new StatusDetail(Constants.FAILED, 
                                        resp.getError().getMessage()));
            }
        }
        else
        {                                     
            ArrayList<BatchResponseEntity> responses = resp.getBatches();
            for(int i = 0; i < responses.size(); i++)
            {
                String body = responses.get(i).getBody();
                AdGroupPostResponse bodyObj = 
                    (AdGroupPostResponse) Utils.getRespObject(
                        body, AdGroupPostResponse.class);
                if (bodyObj.getError() == null)
                {
                    adGroups.get(i).setStatusDetail(
                        new StatusDetail(Constants.SUCCESS, null)
                    );
                    
                    adGroups.get(i).
                    	setCreative_ids(
                    		bodyObj.getData().getAdgroups().
                    		get(
                    			adGroups.get(i).getAdgroup_id()).
                    				getCreative_ids());
                    adGroups.get(i).setCreative(getAdCreative(bodyObj));
                }
                else
                {
                    adGroups.get(i).setStatusDetail( 
                        new StatusDetail(Constants.FAILED, 
                                        bodyObj.getError().getMessage()));
                }
            }
        }
        return adGroups;
    }
    
    @JsonIgnore
    private Ad getAdCreative(AdGroupPostResponse bodyObj)
    {
    	//the set of creative ids which map to their respective
    	//creative objects. This set will only contain one key
    	//as only on creative is returned for one ad group always.
    	Set<String> creativeKeySet = 
        		bodyObj.getData().getCreatives().keySet();
    	//get the ad creative object from the AdGroupPostResponse
    	//object.
        return bodyObj.getData().getCreatives().
        			get(creativeKeySet.iterator().next());
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
    public AccountGroup getParent()
    {
        return m_parent;
    }

    /*
     * This method is required to be public for the response to be complete
     * with parent information
     * */
    @JsonIgnore
    public void setParent(AccountGroup parent)
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

    public Integer getAccount_status()
    {
        return m_accountStatus;
    }

    private void setAccount_status(Integer accountStatus)
    {
        m_accountStatus = accountStatus;
    }

    private void setBusiness_name(String business_name)
    {
        this.m_businessName = business_name;
    }

    public String getBusiness_name()
    {
        return m_businessName;
    }

    private void setTimezone_id(Integer timezone_id)
    {
        this.m_timezoneId = timezone_id;
    }

    public Integer getTimezone_id()
    {
        return m_timezoneId;
    }

    private void setTimezone_name(String timezone_name)
    {
        this.m_timezoneName = timezone_name;
    }

    public String getTimezone_name()
    {
        return m_timezoneName;
    }

    private void setCurrency(String currency)
    {
        this.m_currency = currency;
    }

    public String getCurrency()
    {
        return m_currency;
    }

    private void setDaily_spend_limit(Long daily_spend_limit)
    {
        this.m_dailySpendLimit = daily_spend_limit;
    }

    public Long getDaily_spend_limit()
    {
        return m_dailySpendLimit;
    }

    @Override
    public String toString()
    {
        StringBuilder builder = new StringBuilder();
        builder.append("Account [m_accountId=");
        builder.append(m_accountId);
        builder.append(", m_accountStatus=");
        builder.append(m_accountStatus);
        builder.append(", m_businessName=");
        builder.append(m_businessName);
        builder.append(", m_currency=");
        builder.append(m_currency);
        builder.append(", m_dailySpendLimit=");
        builder.append(m_dailySpendLimit);
        builder.append(", m_id=");
        builder.append(m_id);
        builder.append(", m_name=");
        builder.append(m_name);
        builder.append(", m_parent={");
        builder.append(m_parent);
        builder.append("}, m_timezoneId=");
        builder.append(m_timezoneId);
        builder.append(", m_timezoneName=");
        builder.append(m_timezoneName);
        builder.append("]");
        return builder.toString();
    }

}
