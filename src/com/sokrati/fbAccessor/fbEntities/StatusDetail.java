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

public class StatusDetail
{
    private String m_status;
    private String m_details;
    
    public StatusDetail(String status, String details)
    {
        m_status = status;
        m_details = details;
    }
    
    public String getStatus()
    {
        return m_status;
    }
    
    public String getDetails()
    {
        return m_details;
    }
    
    private void setStatus(String status)
    {
        m_status = status;
    }
    
    private void setDetails(String details)
    {
        m_details = details;
    }
    
    @Override
    public String toString()
    {
        StringBuilder builder = new StringBuilder();
        builder.append("StatusDetail [m_status=");
        builder.append(m_status);
        builder.append(", m_details=");
        builder.append(m_details);
        builder.append("]");
        return builder.toString();
    }
}
