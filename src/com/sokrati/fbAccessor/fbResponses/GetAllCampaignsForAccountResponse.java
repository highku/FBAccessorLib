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

import java.util.Arrays;

import org.codehaus.jackson.annotate.JsonIgnoreProperties;

import com.sokrati.fbAccessor.fbEntities.Campaign;
import com.sokrati.fbAccessor.fbEntities.Paging;

import com.sokrati.fbRestAccessor.response.FBRestResponse;

@JsonIgnoreProperties(ignoreUnknown = true)
public class GetAllCampaignsForAccountResponse extends FBRestResponse
{
    private Campaign[] m_data = null;

    private Integer m_count = 0;
    private Integer m_limit = 0;
    private Integer m_offset = 0;
    private Paging m_paging = null;

    public Campaign[] getData()
    {
        return m_data;
    }

    public Integer getCount()
    {
        return m_count;
    }

    public Integer getLimit()
    {
        return m_limit;
    }

    public Integer getOffset()
    {
        return m_offset;
    }

    public Paging getPaging()
    {
        return m_paging;
    }

    private void setCount(Integer count)
    {
        this.m_count = count;
    }

    private void setLimit(Integer limit)
    {
        this.m_limit = limit;
    }

    private void setOffset(Integer offset)
    {
        this.m_offset = offset;
    }

    private void setPaging(Paging paging)
    {
        this.m_paging = paging;
    }
    
    private void setData(Campaign[] data)
    {
        this.m_data = data;
    }

    @Override
    public String toString()
    {
        StringBuilder builder = new StringBuilder();
        builder.append("GetAllCampaignsForAccountResponse [count=");
        builder.append(m_count);
        builder.append(", limit=");
        builder.append(m_limit);
        builder.append(", m_data=");
        builder.append(Arrays.toString(m_data));
        builder.append(", offset=");
        builder.append(m_offset);
        builder.append(", paging=");
        builder.append(m_paging);
        builder.append("]");
        return builder.toString();
    }
}
