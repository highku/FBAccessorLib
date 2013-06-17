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

import com.sokrati.logger.Log;

import org.codehaus.jackson.annotate.JsonIgnoreProperties;

import com.sokrati.fbRestAccessor.exceptions.AuthenticationFailureError;

@JsonIgnoreProperties(ignoreUnknown = true)
public class FBRestResponse
{
    private Error m_error = null;
    private ArrayList<Object> m_batch = null;

    // This is used by jackson
    public FBRestResponse()
    {
    }

    public FBRestResponse(ArrayList<Object> batch)
    {
        m_batch = batch;
    }

    public Error getError()
    {
        return m_error;
    }

    private void setError(Error error)
    {
        m_error = error;
    }

    public boolean hasError()
    {
        return (m_error != null);
    }
    
    public boolean hasAuthenticationFailed()
    {
        boolean authFailure = false;
        if (hasError())
        {

            authFailure =
                AuthenticationFailureError.FB_AUTH_EXCEPTION_TYPE
                    .equalsIgnoreCase(m_error.getType());

        }
        return authFailure;
    }

    public ArrayList<Object> getBatch()
    {
        return m_batch;
    }

    private void setBatch(ArrayList<Object> batch)
    {
        this.m_batch = batch;
    }

    @Override
    public String toString()
    {
        StringBuilder builder = new StringBuilder();
        builder.append("FBRestResponse [m_batch=");
        builder.append(m_batch);
        builder.append(", m_error=");
        builder.append((m_error != null) ? (m_error.toString()) : ("Error="));
        builder.append("]");
        return builder.toString();
    }
}
