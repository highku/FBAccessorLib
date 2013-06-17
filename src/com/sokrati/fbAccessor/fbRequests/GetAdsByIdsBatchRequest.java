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

import java.util.ArrayList;

import org.codehaus.jackson.annotate.JsonIgnore;
import org.codehaus.jackson.map.annotate.JsonSerialize;
import org.codehaus.jackson.map.annotate.JsonSerialize.Inclusion;

import com.sokrati.fbAccessor.exceptions.FBAccessorError;
import com.sokrati.fbAccessor.exceptions.InvalidInputError;
import com.sokrati.fbRestAccessor.entities.BatchRequestEntity;
import com.sokrati.fbRestAccessor.request.FBBatchRequest;

@JsonSerialize(include = Inclusion.NON_NULL)
public class GetAdsByIdsBatchRequest extends FBBatchRequest
{
    private GetAdsByIdsBatchRequest()
    {
    }

    public GetAdsByIdsBatchRequest(String accessToken,
                                   String[] ids)
        throws FBAccessorError
    {
        super(accessToken);
        if (ids != null)
        {
            if (ids.length > getBatchSize())
            {
                throw new InvalidInputError("Max batch size exceeded. "
                                            + "Expected : "
                                            + getBatchSize()
                                            + ". Received : "
                                            + ids.length);
            }

            ArrayList<BatchRequestEntity> batch =
                new ArrayList<BatchRequestEntity>();
            for (String id : ids)
            {
                BatchRequestEntity be =
                    new BatchRequestEntity(BatchRequestEntity.METHOD_GET,
                                           id, null, null, null);
                batch.add(be);
            }
            setBatch(batch);
        }

    }

    @JsonIgnore
    public String[] getIds()
    {
        String[] ids = null;

        ArrayList<BatchRequestEntity> batch = getBatch();
        if (batch != null)
        {
            ids = new String[batch.size()];
            for (int i = 0; i < batch.size(); i++)
            {
                ids[i] = batch.get(i).getBody();
            }
        }

        return ids;
    }

}