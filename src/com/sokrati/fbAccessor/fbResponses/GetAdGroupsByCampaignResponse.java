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
import com.sokrati.fbAccessor.fbEntities.AdGroup;
import com.sokrati.fbRestAccessor.response.FBRestResponse;

@JsonIgnoreProperties(ignoreUnknown = true)
public class GetAdGroupsByCampaignResponse extends FBRestResponse
{
    private AdGroup[] m_data = null;
    
    public AdGroup[] getData()
    {
        return m_data;
    }
    
    private void setData(AdGroup[] data)
    {
        m_data = data;
    }
    
    public String toString()
    {
        StringBuilder builder = new StringBuilder();
        builder.append("GetAdGroupsByCampaignResponse [m_data=");
        builder.append(Arrays.toString(m_data));
        builder.append("]");
        return builder.toString();
    }
}
