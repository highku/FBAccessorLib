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

package com.sokrati.fbRestAccessor.request;

import java.util.*;
import com.sokrati.fbRestAccessor.request.FBRestRequest;
import com.sokrati.fbRestAccessor.entities.BatchRequestEntity;

import org.codehaus.jackson.annotate.JsonIgnore;
import org.codehaus.jackson.map.annotate.JsonSerialize.Inclusion;
import org.codehaus.jackson.map.annotate.JsonSerialize;

@JsonSerialize(include = Inclusion.NON_NULL)
public class FBBatchRequest extends FBRestRequest
{

    private ArrayList<BatchRequestEntity> m_batch = null;

    /*
     * Facebook allows a batch size of max 50.
     * */
    @JsonIgnore
    private static final int m_batchSize = 50;

    public FBBatchRequest()
    {
    }

    public FBBatchRequest(String accessToken)
    {
        super(accessToken);
    }

    public FBBatchRequest(String accessToken,
                          ArrayList<BatchRequestEntity> batch)
    {
        super(accessToken);
        m_batch = batch;
    }

    public ArrayList<BatchRequestEntity> getBatch()
    {
        return m_batch;
    }

    public void setBatch(ArrayList<BatchRequestEntity> batch)
    {
        m_batch = batch;
    }

    @JsonIgnore
    public static int getBatchSize()
    {
        return m_batchSize;
    }

    @Override
    public String toString()
    {
        StringBuilder builder = new StringBuilder();
        builder.append("FBBatchRequest [m_batch=");
        builder.append(m_batch);
        builder.append(", m_batchSize=");
        builder.append(m_batchSize);
        builder.append("]");
        return builder.toString();
    }
}
