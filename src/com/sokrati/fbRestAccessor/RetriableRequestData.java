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

package com.sokrati.fbRestAccessor;

public class RetriableRequestData
{

    private boolean m_isRetriable;
    private int m_retryInterval;
    private int m_maxRetryAttempts;

    // This object should only be visible inside this package
    RetriableRequestData()
    {
        m_isRetriable = false;
        m_retryInterval = 0;
        m_maxRetryAttempts = 0;
    }

    RetriableRequestData(int retryInterval,
                         int maxRetryAttempts)
    {

        m_isRetriable = true;
        m_retryInterval = retryInterval;
        m_maxRetryAttempts = maxRetryAttempts;
    }

    public boolean isRetriable()
    {
        return m_isRetriable;
    }

    public int getRetryInterval()
    {
        return m_retryInterval;
    }

    public int getMaxRetryAttempts()
    {
        return m_maxRetryAttempts;
    }

    private void setRetriable(boolean isRetriable)
    {
        this.m_isRetriable = isRetriable;
    }

    private void setRetryInterval(int retryInterval)
    {
        this.m_retryInterval = retryInterval;
    }

    private void setMaxRetryAttempts(int maxRetryAttempts)
    {
        this.m_maxRetryAttempts = maxRetryAttempts;
    }

    @Override
    public String toString()
    {
        StringBuilder builder = new StringBuilder();
        builder.append("RetriableRequestData [isRetriable=");
        builder.append(m_isRetriable);
        builder.append(", maxRetryAttempts=");
        builder.append(m_maxRetryAttempts);
        builder.append(", retryInterval=");
        builder.append(m_retryInterval);
        builder.append("]");
        return builder.toString();
    }

}
