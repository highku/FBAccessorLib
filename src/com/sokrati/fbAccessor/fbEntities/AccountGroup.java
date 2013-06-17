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

import com.sokrati.fbAccessor.Constants;
import com.sokrati.fbAccessor.exceptions.IllegalAccessError;
import com.sokrati.fbAccessor.fbRequests.*;
import com.sokrati.fbAccessor.fbResponses.*;

import com.sokrati.fbRestAccessor.FBRestAccessor;
import com.sokrati.fbRestAccessor.exceptions.*;

public class AccountGroup
{
    private FBAccessorService m_parent = null;

    // Unusable constructor
    private AccountGroup()
    {
    }

    protected AccountGroup(FBAccessorService parentRef)
    {
        m_parent = parentRef;
    }

    /*
     * This method gives the accounts for a given access_token
     * */
    public Account[] getAccountsByAccessToken(String acessToken)
        throws IllegalAccessError, MarshallingError, FBCommunicationError,
        InvalidConfigurationError, InitializationError,
        InvalidUrlReplaceParamError, AuthenticationFailureError
    {
        // Check lib-initialization mode
        if (!ModeType.isModeReadOnly(getMode()))
        {
            throw new IllegalAccessError(
                IllegalAccessError.METHOD_NOT_ALLOWED + getMode().toString()
            );
        }

        // Create a request
        GetAccountsForAccessTokenRequest req =
            new GetAccountsForAccessTokenRequest(acessToken);

        // Make a call to fb to get accounts
        GetAccountsForAccessTokenResponse response =
            (GetAccountsForAccessTokenResponse) getFBRestAccessor().call(
                Constants.GET_ACCOUNTS_FOR_ACCESS_TOKEN_KEY, req);

        // Account objects needs to have the ref of its parent
        for(Account acc : response.getData())
        {
            acc.setParent(this);
        }
        
        return response.getData();
    }

    /*
     * This method gives the fb account for the given id
     * */
    public Account getAccountById(String acessToken, String fbAccountId)
        throws IllegalAccessError, MarshallingError, FBCommunicationError,
        InvalidConfigurationError, InitializationError,
        InvalidUrlReplaceParamError, AuthenticationFailureError
    {
        // Check lib-initialization mode
        if (!(ModeType.isModeReadOnly(getMode()) ||
            ModeType.isModeWriteOnly(getMode())))
        {
            throw new IllegalAccessError(IllegalAccessError.METHOD_NOT_ALLOWED
                                         + getMode().toString());
        }

        // Create a request
        GetAccountByIdRequest req = new GetAccountByIdRequest(acessToken);

        // Make a call to fb to get account
        Account response = (Account) getFBRestAccessor().call(
            Constants.GET_ACCOUNT_BY_ID,
            Constants.URL_REPLACE_PARAM_ACCOUNT,
            fbAccountId,
            req);
        response.setParent(this);

        return response;
    }

    public ModeType getMode()
    {
        return m_parent.getMode();
    }

    public FBRestAccessor getFBRestAccessor()
    {
        return m_parent.getFBRestAccessor();
    }

    public FBAccessorService getParent()
    {
        return m_parent;
    }

    private void setParent(FBAccessorService parent)
    {
        m_parent = parent;
    }

    @Override
    public String toString()
    {
        StringBuilder builder = new StringBuilder();
        builder.append("AccountGroup [m_parent=");
        builder.append(m_parent);
        builder.append("]");
        return builder.toString();
    }

}
