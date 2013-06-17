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

import org.codehaus.jackson.annotate.JsonIgnoreProperties;
import org.codehaus.jackson.map.annotate.JsonSerialize.Inclusion;
import org.codehaus.jackson.map.annotate.JsonSerialize;

import com.sokrati.fbRestAccessor.request.FBRestRequest;

@JsonSerialize(include = Inclusion.NON_NULL)
@JsonIgnoreProperties(ignoreUnknown = true)
public class GetAllCampaignsForAccountRequest extends FBRestRequest
{
    private String m_dateFormat = null;
    private String m_accountID = null;
    private Integer m_offset = 0;

    private GetAllCampaignsForAccountRequest()
    {
    }

    public GetAllCampaignsForAccountRequest(String accessToken,
                                            String accountID)
    {
        super(accessToken);
        // This means we will get back time in unix-timestamp style
        m_dateFormat = "U";
        m_accountID = accountID;
    }

    public GetAllCampaignsForAccountRequest(String accessToken,
                                            String accountID,
                                            Integer offset)
    {
        super(accessToken);
        // This means we will get back time in unix-timestamp style
        m_dateFormat = "U";
        m_accountID = accountID;
        m_offset = offset;
    }

    public String getDate_format()
    {
        return m_dateFormat;
    }

    private void setDate_format(String dateFormat)
    {
        m_dateFormat = dateFormat;
    }

    public String getAccountId()
    {
        return m_accountID;
    }

    private void setAccountId(String accountId)
    {
        m_accountID = accountId;
    }

    public Integer getOffset()
    {
        return m_offset;
    }

    // this needs to be public as the offsets are modified at run time.
    public void setOffset(Integer offset)
    {
        this.m_offset = offset;
    }

    @Override
    public String toString()
    {
        StringBuilder builder = new StringBuilder();
        builder.append("GetAllCampaignsForAccountRequest [m_accountID=");
        builder.append(m_accountID);
        builder.append(", m_dateFormat=");
        builder.append(m_dateFormat);
        builder.append(", m_offset=");
        builder.append(m_offset);
        builder.append("]");
        return builder.toString();
    }

}
