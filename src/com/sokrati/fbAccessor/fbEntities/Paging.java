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

import org.codehaus.jackson.annotate.JsonIgnoreProperties;

@JsonIgnoreProperties(ignoreUnknown = true)
public class Paging
{
    private String m_next = null;
    private String m_previous = null;

    public String getNext()
    {
        return m_next;
    }
    
    public boolean hasNext()
    {
        return (m_next!=null);
    }

    public String getPrevious()
    {
        return m_previous;
    }

    private void setNext(String next)
    {
        this.m_next = next;
    }

    private void setPrevious(String previous)
    {
        this.m_previous = previous;
    }

    @Override
    public String toString()
    {
        StringBuilder builder = new StringBuilder();
        builder.append("Paging [next=");
        builder.append(m_next);
        builder.append(", previous=");
        builder.append(m_previous);
        builder.append("]");
        return builder.toString();
    }

}
