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

import java.io.UnsupportedEncodingException;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import org.codehaus.jackson.annotate.JsonIgnore;
import org.codehaus.jackson.map.annotate.JsonSerialize;
import org.codehaus.jackson.map.annotate.JsonSerialize.Inclusion;

import com.sokrati.fbAccessor.Utils;
import com.sokrati.fbAccessor.exceptions.FBAccessorError;
import com.sokrati.fbAccessor.exceptions.InvalidInputError;
import com.sokrati.fbRestAccessor.entities.BatchRequestEntity;
import com.sokrati.fbRestAccessor.request.FBBatchRequest;

import com.sokrati.fbAccessor.fbEntities.StatsRequestQueryParam;

@JsonSerialize(include = Inclusion.NON_NULL)
public class GetCampaignStatsByIdsRequest extends FBBatchRequest
{
    private GetCampaignStatsByIdsRequest()
    {
    }

    public GetCampaignStatsByIdsRequest(String accessToken,
                                         List<String> ids,
                                         Date startTime,
                                         Date endTime)
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
                                           getRelativeUrl(id, startTime,
                                               endTime),
                                           null, null, null);
                batch.add(be);
            }
            setBatch(batch);
        }

    }

    @JsonIgnore
    private String getRelativeUrl(String url, Date startTime, Date endTime)
        throws FBAccessorError
    {
        String relativeUrl = url;

        String queryParams = null;
        try
        {
            queryParams =
                Utils.getQueryString(new StatsRequestQueryParam(startTime,
                                                              endTime));
        }
        catch (UnsupportedEncodingException e)
        {
            throw new InvalidInputError(e);
        }

        if (queryParams != null)
        {
            relativeUrl += "/stats?" + queryParams;
        }

        System.out.println("getRelativeUrl = [" + relativeUrl + "]");
        return relativeUrl;
    }

}
