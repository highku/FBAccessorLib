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

import java.io.Serializable;
import java.io.UnsupportedEncodingException;
import java.util.ArrayList;
import java.util.List;

import org.codehaus.jackson.annotate.JsonIgnore;
import org.codehaus.jackson.map.annotate.JsonSerialize;
import org.codehaus.jackson.map.annotate.JsonSerialize.Inclusion;

import com.sokrati.fbAccessor.Utils;
import com.sokrati.fbAccessor.exceptions.FBAccessorError;
import com.sokrati.fbAccessor.exceptions.InvalidInputError;
import com.sokrati.fbRestAccessor.entities.BatchRequestEntity;
import com.sokrati.fbRestAccessor.request.FBBatchRequest;

@JsonSerialize(include = Inclusion.NON_NULL)
public class GetCampaignsByIdsBatchRequest extends FBBatchRequest
{
    private GetCampaignsByIdsBatchRequest()
    {
    }

    public GetCampaignsByIdsBatchRequest(String accessToken,
                                         List<String> ids)
        throws FBAccessorError
    {
        super(accessToken);
        if (ids != null)
        {
            if (ids.size() > getBatchSize())
            {
                throw new InvalidInputError("Max batch size exceeded. "
                                            + "Expected : "
                                            + getBatchSize()
                                            + ". Received : "
                                            + ids.size());
            }

            ArrayList<BatchRequestEntity> batch =
                new ArrayList<BatchRequestEntity>();
            for (String id : ids)
            {
                BatchRequestEntity be =
                    new BatchRequestEntity(BatchRequestEntity.METHOD_GET,
                                           getRelativeUrl(id),
                                           null, null, null);
                batch.add(be);
            }
            setBatch(batch);
        }

    }

    @JsonIgnore
    public ArrayList<String> getIds()
    {
        ArrayList<String> ids = null;

        ArrayList<BatchRequestEntity> batch = getBatch();
        if (batch != null)
        {
            ids = new ArrayList<String>();
            for (BatchRequestEntity entity : batch)
            {
                ids.add(new String(entity.getBody()));
            }
        }

        return ids;
    }

    @JsonIgnore
    private String getRelativeUrl(String url) throws FBAccessorError
    {
        String relativeUrl = url;

        String queryParams = null;
        try
        {
            queryParams = Utils.getQueryString(new QueryParam());
        }
        catch (UnsupportedEncodingException e)
        {
            throw new InvalidInputError(e);
        }

        if (queryParams != null)
        {
            relativeUrl += "?" + queryParams;
        }
        return relativeUrl;
    }

}

class QueryParam implements Serializable
{
    private String m_dateFormat = null;

    protected QueryParam()
    {
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

    @Override
    public String toString()
    {
        StringBuilder builder = new StringBuilder();
        builder.append("QueryParam [m_dateFormat=");
        builder.append(m_dateFormat);
        builder.append("]");
        return builder.toString();
    }

}
