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

package com.sokrati.fbAccessor;

import java.io.File;

import com.sokrati.fbAccessor.exceptions.IllegalInitializationError;
import com.sokrati.fbAccessor.fbEntities.*;

import com.sokrati.fbRestAccessor.FBRestAccessor;
import com.sokrati.fbRestAccessor.exceptions.*;

public class FBAccessor
{
    // Unusable constructor
    private FBAccessor()
    {
    }

    /*
     * This method gives a read-only service for accessing fb objects.
     * We should be passing a read_only file to this method.
     * */
    public static FBAccessorService getReadOnlyFBService(
        File restAccessorConfigFile
    )
        throws IllegalInitializationError, InitializationError
    {
        /*
         *  Construct a rest-accessor object to be used for all fb communication.
         *  This object must hae useJsonQuery as false.
         *  This object will be used by the parents and their children.
         */
        FBRestAccessor fbRestAccessor =
            new FBRestAccessor(restAccessorConfigFile);
        return new FBAccessorService(ModeType.READ_ONLY, fbRestAccessor);
    }

    /*
     * This method gives a write-only service for accessing fb objects.
     * We should be passing a write_only file to this method.
     * */
    public static FBAccessorService getWriteOnlyFBService(
        File restAccessorConfigFile
    )
        throws IllegalInitializationError, InitializationError
    {
        /*
         *  Construct a rest-accessor object to be used for all fb communication.
         *  This object will be used by the parents and their children.
         */
        FBRestAccessor fbRestAccessor =
            new FBRestAccessor(restAccessorConfigFile);
        return new FBAccessorService(ModeType.WRITE_ONLY, fbRestAccessor);
    }

}
