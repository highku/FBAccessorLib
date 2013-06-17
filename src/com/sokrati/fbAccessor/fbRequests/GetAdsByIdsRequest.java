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

import com.sokrati.fbRestAccessor.request.FBRestRequest;

public class GetAdsByIdsRequest extends FBRestRequest
{
    private String m_ids = null;

    public GetAdsByIdsRequest(String accessToken, String ids)
    {
        super(accessToken);
        m_ids = ids;
    }

    public void setIds(String ids)
    {
        this.m_ids = ids;
    }

    public String getIds()
    {
        return m_ids;
    }

    @Override
    public String toString()
    {
        StringBuilder builder = new StringBuilder();
        builder.append("GetAdsByIdsRequest [m_ids=");
        builder.append(m_ids);
        builder.append(", toString()=");
        builder.append(super.toString());
        builder.append("]");
        return builder.toString();
    }
}
