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

package com.sokrati.fbRestAccessor.response;

import java.util.ArrayList;

import org.codehaus.jackson.annotate.JsonIgnoreProperties;

import com.sokrati.fbRestAccessor.entities.*;

@JsonIgnoreProperties(ignoreUnknown = true)
public class FBBatchResponse extends FBRestResponse
{

    private ArrayList<BatchResponseEntity> m_batches = null;

    // This is used by jackson
    public FBBatchResponse()
    {
        super();
    }

    public FBBatchResponse(ArrayList<BatchResponseEntity> batches)
    {
        super();
        m_batches = batches;
    }

    public ArrayList<BatchResponseEntity> getBatches()
    {
        return m_batches;
    }

    private void setBatches(ArrayList<BatchResponseEntity> batches)
    {
        this.m_batches = batches;
    }

    @Override
    public String toString()
    {
        StringBuilder builder = new StringBuilder();
        builder.append("FBBatchResponse [m_batch=");
        builder.append(m_batches);
        builder.append(", FBRestResponse=");
        builder.append(super.toString());
        builder.append("]");
        return builder.toString();
    }
}
