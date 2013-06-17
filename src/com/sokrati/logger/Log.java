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

package com.sokrati.logger;

import org.apache.log4j.Logger;
import org.apache.log4j.PropertyConfigurator;

public final class Log {

    // We cannot write debug, info etc functions here
    // to call them on a private Logger, as that will
    // lose the originating class, lineno information.
    // TODO: Explore macros for java.
    public static Logger l = Logger.getLogger("SOKRATI");

    private Log () {}

    public static void configure(String logConfig) 
    {
        PropertyConfigurator.configure(logConfig);
    }

} // class Log
