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
import java.util.Arrays;


import org.codehaus.jackson.JsonParseException;
import org.codehaus.jackson.annotate.JsonIgnore;
import org.codehaus.jackson.annotate.JsonIgnoreProperties;
import org.codehaus.jackson.map.JsonMappingException;
import org.codehaus.jackson.map.ObjectMapper;

import com.sokrati.fbAccessor.Constants;
import com.sokrati.fbAccessor.exceptions.FBAccessorError;
import com.sokrati.fbAccessor.exceptions.IllegalAccessError;

import com.sokrati.fbAccessor.fbRequests.GetAdsByIdsBatchRequest;
import com.sokrati.fbAccessor.fbRequests.GetAdsByIdsRequest;

import com.sokrati.fbRestAccessor.FBRestAccessor;
import com.sokrati.fbRestAccessor.entities.BatchResponseEntity;
import com.sokrati.fbRestAccessor.exceptions.*;
import com.sokrati.fbRestAccessor.request.FBBatchRequest;
import com.sokrati.fbRestAccessor.response.FBBatchResponse;
import com.sokrati.fbRestAccessor.response.FBRestResponse;

@JsonIgnoreProperties(ignoreUnknown = true)
public class AdGroup extends FBRestResponse
{
    
    private Campaign m_parent;
    
    private String m_id = null;
    private String m_adGroupId;
    private String m_campaignId;
    private String m_name;
    private Integer m_status;
    private Integer m_bidType;
    private Double m_maxBid;
    private Double m_startTime;
    private Double m_endTime;
    private String[] m_creativeIds;
    private Ad m_creative;
    
    private StatusDetail m_statusDetail = null;
    private Integer m_reDownload = null;
    
    private Targeting targeting;
    
    
    public AdGroup()
    {
        m_reDownload = 1;
        //Unusable constructor
    }
    
    public AdGroup(String id,
                   String adGroupId,
                   String campaignId,
                   String name,
                   Integer status,
                   Integer bidType,
                   Double maxBid,
                   Double startTime,
                   Double endTime,
                   String[] creativeIds)
    {
        m_id = id;
        m_adGroupId = adGroupId;
        m_campaignId = campaignId;
        m_name = name;
        m_status = status;
        m_bidType = bidType;
        m_maxBid = maxBid;
        m_startTime = startTime;
        m_endTime = endTime;
        m_creativeIds = creativeIds;
        m_reDownload = 1;
    }            

    public String getId()
    {
        return m_id;
    }

    private void setId(String id)
    {
        this.m_id = id;
    }
    
    public String getAdgroup_id()
    {
        return m_adGroupId;
    }
    
    public void setAdgroup_id(String mAdGroupId)
    {
        m_adGroupId = mAdGroupId;
    }
    
    public String getCampaign_id()
    {
        return m_campaignId;
    }
    
    private void setCampaign_id(String mCampaignId)
    {
        m_campaignId = mCampaignId;
    }
    
    public String getName()
    {
        return m_name;
    }
    
    private void setName(String mName)
    {
        m_name = mName;
    }
    
    public Integer getAdgroup_status()
    {
        return m_status;
    }
    
    public void setAdgroup_status(Integer mStatus)
    {
        m_status = mStatus;
    }
    
    public Integer getBid_type()
    {
        return m_bidType;
    }
    
    private void setBid_type(Integer mBidType)
    {
        m_bidType = mBidType;
    }
    
    public Double getMax_bid()
    {
        return m_maxBid;
    }
    
    public void setMax_bid(Double mMaxBid)
    {
        m_maxBid = mMaxBid;
    }
    
    public Double getStart_time()
    {
        return m_startTime;
    }
    
    private void setStart_time(Double mStartTime)
    {
        m_startTime = mStartTime;
    }
    
    public Double getEnd_time()
    {
        return m_endTime;
    }
    
    private void setEnd_time(Double mEndTime)
    {
        m_endTime = mEndTime;
    }
    
    public String[] getCreative_ids()
    {
        return m_creativeIds;
    }
    
    public void setCreative_ids(String[] creativeIds)
    {
        m_creativeIds = creativeIds;
    }    

    public Targeting getTargeting()
    {
        return targeting;
    }
    
    private void setTargeting(Targeting targeting)
    {
        this.targeting = targeting;
    }   
    
    public Integer getRedownload()
    {
        return m_reDownload;
    }
    
    private void setRedownload(Integer reDownload)
    {
        m_reDownload = reDownload;
    }
    
    public Ad getCreative()
    {
    	return m_creative;
    }
    
    public void setCreative(Ad creative)
    {
    	m_creative = creative;
    }
    
    @JsonIgnore
    public ModeType getMode()
    {
        return m_parent.getMode();
    }
    
    @JsonIgnore
    public boolean isDeleted()
    {
        return (m_status.equals(StatusType.DELETED.getStatusTypeId()));
    }
    
    @JsonIgnore
    public String getStatus()
    {
        return StatusType.valueOf(m_status);        
    }
    
    /*
     * This method is required to be public for the response to be complete
     * with parent information
     * */
    @JsonIgnore
    public void setParent(Campaign parent)
    {
        m_parent = parent;
    }
    
    @JsonIgnore
    public FBRestAccessor getFBRestAccessor()
    {
        return m_parent.getFBRestAccessor();
    }

    @JsonIgnore
    public StatusDetail getStatusDetail()
    {
        return m_statusDetail;
    }
    
    @JsonIgnore
    public void setStatusDetail(StatusDetail statusDetail)
    {
        m_statusDetail = statusDetail;
    }
    
    /*
     * This method gets ads for an ad_group
     * */
    @JsonIgnore
    public ArrayList<Ad> getAllAds(String accessToken)
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

        ArrayList<Ad> ads = null;

        if ((m_creativeIds != null) && (m_creativeIds.length > 0))
        {
            ads = getAdsForAdIds(accessToken, m_creativeIds);
        }

        return ads;
    }

    /*
     * This method gets ads for given ad_ids' list.
     * 
     * Note: This method should ideally be a static method, 
     * But since the fb-rest-accessor object is obtained from the parent, 
     * it can't be. And that object cannot and shouldn't be static
     * */
    public ArrayList<Ad> getAdsForAdIds(String accessToken,
                                        ArrayList<String> adIds)
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

        ArrayList<Ad> ads = null;
        if (adIds != null)
        {
            String[] adIdsArray = new String[adIds.size()];
            adIds.toArray(adIdsArray);
            ads = getAdsForAdIds(accessToken, adIdsArray);
        }

        return ads;
    }

    /*
     * This method gets ads for given ad_ids.
     * It applies batching logic
     * 
     * Note: This method should ideally be a static method, 
     * But since the fb-rest-accessor object is obtained from the parent, 
     * it can't be. Adn that object cannot and shouldn't be static
     * */
    private ArrayList<Ad> getAdsForAdIds(String accessToken,
                                         String[] adIds)
        throws MarshallingError, FBCommunicationError,
            InvalidConfigurationError, InitializationError,
            InvalidUrlReplaceParamError, AuthenticationFailureError,
        FBAccessorError
    {
        ArrayList<Ad> fbAds = new ArrayList<Ad>();

        for (int fromIndex = 0, toIndex = 0; fromIndex < adIds.length;
            fromIndex = toIndex)
        {
            toIndex = fromIndex + FBBatchRequest.getBatchSize();
            if (toIndex > adIds.length)
            {
                toIndex = adIds.length;
            }

            String[] adIdsBatch = Arrays.copyOfRange(adIds,
                                                     fromIndex, toIndex);

            ArrayList<Ad> adsBatch = getAdsForIds(accessToken, adIdsBatch);
            if (adsBatch != null)
            {
                fbAds.addAll(adsBatch);
            }
        }
        return fbAds;
    }

    /*
     * This method gets ads from fb in a batch for given ad ids
     * */
    private ArrayList<Ad> getAdsForIds(String acessToken,
                                       String[] adIdsBatch)
         throws MarshallingError, FBCommunicationError,
             InvalidConfigurationError, InitializationError,
             InvalidUrlReplaceParamError, AuthenticationFailureError,
        FBAccessorError
    {
        GetAdsByIdsBatchRequest req = new GetAdsByIdsBatchRequest(acessToken,
            adIdsBatch);

        FBBatchResponse response = (FBBatchResponse) getFBRestAccessor().call(
            Constants.BATCH_CALL,
            req);

        return getAds(response);
    }

    /*
     * This method gives ads for given csv of ad-ids
     * */

    private ArrayList<Ad> getAdsForAdIdsCsv(String acessToken, String adIdsCsv)
        throws MarshallingError, FBCommunicationError,
            InvalidConfigurationError, InitializationError,
            InvalidUrlReplaceParamError, AuthenticationFailureError
    {
        GetAdsByIdsRequest req =
            new GetAdsByIdsRequest(acessToken, adIdsCsv);

        FBRestResponse response = getFBRestAccessor().call(
                Constants.GET_ADS_BY_IDS,
                req);

        return getAds(response);
    }

    /*
     * This method gets Ads from FBRestResponse
     * */
    private ArrayList<Ad> getAds(FBRestResponse response)
    {
        ArrayList<Ad> ads = null;
        ArrayList<Object> adsBatch = response.getBatch();
        if (adsBatch != null)
        {
            ads = new ArrayList<Ad>();
            for (int i = 0; i < adsBatch.size(); i++)
            {
                Ad ad = (Ad) adsBatch.get(i);
                ad.setParent(this);
                ads.add(i, ad);
            }
        }

        return ads;
    }

    /*
     * This method gets Ads from FBRestResponse
     * */
    private ArrayList<Ad> getAds(FBBatchResponse response)
        throws MarshallingError, InvalidConfigurationError
    {
        ArrayList<Ad> ads = null;
        ArrayList<BatchResponseEntity> adsBatch = response.getBatches();
        if (adsBatch != null)
        {
            ads = new ArrayList<Ad>();
            ObjectMapper om = new ObjectMapper();

            try
            {
                for (BatchResponseEntity entity : adsBatch)
                {
                    Ad ad = om.readValue(entity.getBody(), Ad.class);
                    /*
                     *  ads may belong to different ad_groups.
                     *  Can't do setParent in that case
                     */
                    ads.add(ad);                    
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

        return ads;
    }

    @Override
    public String toString()
    {
        StringBuilder builder = new StringBuilder();
        builder.append("AdGroup [m_adGroupId=");
        builder.append(m_adGroupId);
        builder.append(", m_bidType=");
        builder.append(m_bidType);
        builder.append(", m_campaignId=");
        builder.append(m_campaignId);
        builder.append(", m_creativeIds=");
        builder.append(Arrays.toString(m_creativeIds));
        builder.append(", m_endTime=");
        builder.append(m_endTime);
        builder.append(", m_id=");
        builder.append(m_id);
        builder.append(", m_maxBid=");
        builder.append(m_maxBid);
        builder.append(", m_name=");
        builder.append(m_name);        
        builder.append(", m_startTime=");
        builder.append(m_startTime);
        builder.append(", m_status=");
        builder.append(m_status);
        builder.append(", targeting=");
        builder.append(targeting);
        builder.append(", m_parent=");
        builder.append(m_parent);
        builder.append(", toString()=");
        builder.append(super.toString());
        builder.append("]");
        return builder.toString();
    }            

}

// Enum for StatusTypes of FB AdGroup
enum StatusType
{
    ACTIVE(1),
    DELETED(3),
    PENDING_REVIEW(4),
    DISAPPROVED(5),
    CAMPAIGN_PAUSED(8),
    ADGROUP_PAUSED(9);

    private int m_statusTypeId;

    private StatusType()
    {
        m_statusTypeId = 1;
    }

    private StatusType(int c)
    {
        m_statusTypeId = c;
    }

    int getStatusTypeId()
    {
        return m_statusTypeId;
    }

    static String valueOf(int i)
    {
        String status = null;

        StatusType[] all = values();
        for (StatusType s : all)
        {
            if (s.getStatusTypeId() == i)
            {
                status = s.name();
                break;
            }

        }

        return status;
    }
}