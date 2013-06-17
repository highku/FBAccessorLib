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

package com.sokrati.fbAccessor.fbEntities;

import java.util.*;
import org.codehaus.jackson.annotate.*;
import com.sokrati.fbAccessor.fbEntities.AdGroup;
import com.sokrati.fbAccessor.fbEntities.Ad;
import com.sokrati.fbRestAccessor.response.FBRestResponse;

/*
 * Example JSon:
 *      "adgroups": {
            "6003499879702": {
                <AdGroup Object>
        },
        "creatives": {
            "6003499879302": 
                <Creative Object>
        }
 *
 */
@JsonIgnoreProperties(ignoreUnknown = true)
public class AdGroupRespData extends FBRestResponse
{
    private HashMap<String, AdGroup> m_adGroups;
    private HashMap<String, Ad> m_creatives;
    
    public AdGroupRespData()
    {
    }

    public HashMap<String, AdGroup> getAdgroups()
    {
        return m_adGroups;
    }

    private void setAdgroups(HashMap<String, AdGroup> adGroups)
    {
        m_adGroups = adGroups;
    }

    public HashMap<String, Ad> getCreatives()
    {
        return m_creatives;
    }

    private void setCreatives(HashMap<String, Ad> creatives)
    {
        m_creatives = creatives;
    }
    
    @Override
    public String toString()
    {
        StringBuilder builder = new StringBuilder();
        builder.append("AdGroupRespData [m_adGroups=");
        builder.append(m_adGroups);
        builder.append(", m_creatives=");
        builder.append(m_creatives);
        builder.append("]");
        return builder.toString();
    }
}



