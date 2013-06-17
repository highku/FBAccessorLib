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

import com.sokrati.fbAccessor.FBAccessor;
import com.sokrati.fbAccessor.exceptions.IllegalInitializationError;

import com.sokrati.fbRestAccessor.FBRestAccessor;

public class FBAccessorService
{
    private ModeType m_mode = null;
    private FBRestAccessor m_fbRestAccessor;

    private FBAccessorService()
    {
    }

    public FBAccessorService(ModeType mode, FBRestAccessor restAccessor)
        throws IllegalInitializationError
    {
        if (!isCallerValid())
        {
            throw new IllegalInitializationError(
                "Only " + FBAccessor.class.getSimpleName() +
                " class is allowed to call this method"
            );
        }

        m_mode = mode;
        m_fbRestAccessor = restAccessor;
    }

    /*
     * This method checks if the caller class is FBAccessor or not
     * */
    private static boolean isCallerValid()
    {
        boolean ret = false;

        StackTraceElement[] stackTraceElements =
            Thread.currentThread().getStackTrace();

        /*
         The stack-elements look like this
            Element [0] : 
            ClassName: java.lang.Thread
            File     : Thread.java
            Method   : getStackTrace
            Element [1] : 
            ClassName: com.sokrati.fbAccessor.fbEntities.FBAccessorService
            File     : FBAccessorService.java
            Method   : isCallerValid
            Element [2] : 
            ClassName: com.sokrati.fbAccessor.fbEntities.FBAccessorService
            File     : FBAccessorService.java
            Method   : <init>
            Element [3] : 
            ClassName: com.sokrati.fbAccessor.FBAccessor
            File     : FBAccessor.java
            Method   : getReadOnlyFBService
         * */
        if (stackTraceElements[3].getClassName().
                equals(FBAccessor.class.getName()))
        {
            ret = true;
        }

        return ret;
    }

    public ModeType getMode()
    {
        return m_mode;
    }

    private void setMode(ModeType m_mode)
    {
        this.m_mode = m_mode;
    }

    public AccountGroup getAccountGroup()
    {
        return new AccountGroup(this);
    }

    public FBRestAccessor getFBRestAccessor()
    {
        return m_fbRestAccessor;
    }

    private void setFBRestAccessor(FBRestAccessor m_restAccessor)
    {
        this.m_fbRestAccessor = m_restAccessor;
    }

    @Override
    public String toString()
    {
        StringBuilder builder = new StringBuilder();
        builder.append("FBAccessorService [m_fbRestAccessor=");
        builder.append(m_fbRestAccessor);
        builder.append(", m_mode=");
        builder.append(m_mode);
        builder.append("]");
        return builder.toString();
    }
}
