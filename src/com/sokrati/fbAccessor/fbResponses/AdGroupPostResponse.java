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

import java.util.*;
import org.codehaus.jackson.annotate.*;
import com.sokrati.fbAccessor.fbEntities.AdGroupRespData;
import com.sokrati.fbRestAccessor.response.FBRestResponse;

/*
 *Example JSon:
 *  "body": "{
    "result": true,
    "data": {
        <AdGroupRespData>
      }
    }"
 */


@JsonIgnoreProperties(ignoreUnknown = true)
public class AdGroupPostResponse extends FBRestResponse
{
    private Boolean m_result;
    private AdGroupRespData m_data;

    public AdGroupPostResponse()
    {
    }

    public Boolean getResult()
    {
        return m_result;
    }

    private void setResult(Boolean result)
    {
        m_result = result;
    }

    public AdGroupRespData getData()
    {
        return m_data;
    }

    private void setData(AdGroupRespData data)
    {
        m_data = data;
    }
    
    @Override
    public String toString()
    {
        StringBuilder builder = new StringBuilder();
        builder.append("AdGroupPostResponse [m_result=");
        builder.append(m_result);
        builder.append(", m_data=");
        builder.append(m_data.toString());
        builder.append("]");
        return builder.toString();
    }
}



