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

import org.codehaus.jackson.map.annotate.JsonSerialize.Inclusion;
import org.codehaus.jackson.map.annotate.JsonSerialize;

@JsonSerialize(include = Inclusion.NON_NULL)
abstract public class FBRestRequest
{
    /*
     * Almost all of the facebook requests require an access_token
     */
    private String m_accessToken = null;
    
    /*
     * This constructor should never be used. It is used by jackson only. 
     */
    public FBRestRequest()
    {
    }

    /*
    * This constructor should be used by the derived classes
    */
    protected FBRestRequest(String accessToken)
    {        
        m_accessToken = accessToken;
    }
    
    public String getAccess_token()
    {
        return m_accessToken;
    }

    private void setAccess_token(String accessToken)
    {
        m_accessToken = accessToken;
    }

    /*
     * This method should be called by the derived classes 
     * */
    @Override
    public String toString()
    {
        return "FBRestRequest [m_accessToken=" + m_accessToken + "]";
    }
}
