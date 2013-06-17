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

import org.codehaus.jackson.map.annotate.JsonSerialize;
import org.codehaus.jackson.map.annotate.JsonSerialize.Inclusion;

import com.sokrati.fbRestAccessor.request.FBRestRequest;

@JsonSerialize(include = Inclusion.NON_NULL)
public class GetAdGroupByIdRequest extends FBRestRequest
{
    private GetAdGroupByIdRequest()
    {
    }

    public GetAdGroupByIdRequest(String accessToken)
    {
        super(accessToken);
    }

    @Override
    public String toString()
    {
        StringBuilder builder = new StringBuilder();
        builder.append("GetAdGroupByIdRequest [toString()=");
        builder.append(super.toString());
        builder.append("]");
        return builder.toString();
    }

}
