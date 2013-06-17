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

import java.util.Arrays;

import org.codehaus.jackson.annotate.JsonIgnoreProperties;

import com.sokrati.fbRestAccessor.response.FBRestResponse;

@JsonIgnoreProperties(ignoreUnknown = true)
public class AdGroupConversionStats extends FBRestResponse
{
    private String m_adgroupId = null;
    private AdGroupConversionValue[] m_values = null;
    
    public AdGroupConversionStats()
    {
    }
    
    public String getAdgroup_id()
    {
        return m_adgroupId;
    }
    
    private void setAdgroup_id(String adgroup_id)
    {
        this.m_adgroupId = adgroup_id;
    }
    
    public AdGroupConversionValue[] getValues()
    {
        return m_values;
    }
    
    private void setValues(AdGroupConversionValue[] values)
    {
        this.m_values = values;
    }
    
    @Override
    public String toString()
    {
        StringBuilder builder = new StringBuilder();
        builder.append("AdGroupConversionStats [m_adgroupId=")
            .append(m_adgroupId).append(",\n m_values=")
            .append(Arrays.toString(m_values)).append("]");
        return builder.toString();
    }
    
}
