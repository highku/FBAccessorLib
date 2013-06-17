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

import org.codehaus.jackson.annotate.JsonIgnoreProperties;

@JsonIgnoreProperties(ignoreUnknown = true)
public class Error
{
    
    /**
     * A class to store error details sent from Facebook.
     * This is a part of the response sent by Facebook. Its presence in a response
     * indicates that there was an error. 
     */
    private String m_message = null;
    private String m_type = null;

    // This is used by jackson
    public Error()
    {
    }

    public String getMessage()
    {
        return m_message;
    }

    private void setMessage(String message)
    {
        m_message = message;
    }

    public String getType()
    {
        return m_type;
    }

    private void setType(String type)
    {
        m_type = type;
    }

    @Override
    public String toString()
    {
        StringBuilder builder = new StringBuilder();
        builder.append("Error [m_message=");
        builder.append(m_message);
        builder.append(", m_type=");
        builder.append(m_type);
        builder.append("]");
        return builder.toString();
    }
}
