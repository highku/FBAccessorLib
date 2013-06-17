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

import java.beans.*;
import java.lang.reflect.Method;
import java.util.Arrays;
import java.util.HashMap;

import org.codehaus.jackson.annotate.JsonIgnoreProperties;

import com.sokrati.fbAccessor.Utils;
import com.sokrati.fbRestAccessor.exceptions.InvalidConfigurationError;

@JsonIgnoreProperties(ignoreUnknown = true)
public class Targeting
{
    public static final String TARGETING_KEY_KEYWORDS = "keywords";
    
    private Integer m_ageMax;
    private Integer m_ageMin;
    private Integer m_broadAge;

    private Integer[] m_genders;
    private Integer[] m_relationshipStatuses;
    private Integer[] m_interestedIn;

    private String[] m_countries;
    private String[] m_keywords;

    private Targeting()
    {
    }

    public Targeting(Integer ageMax,
                     Integer ageMin,
                     Integer broadAge,
                     Integer[] genders,
                     Integer[] relationshipStatuses,
                     Integer[] interestedIn,
                     String[] countries,
                     String[] keywords)
    {
        m_ageMax = ageMax;
        m_ageMin = ageMin;
        m_broadAge = broadAge;
        m_genders = genders;
        m_relationshipStatuses = relationshipStatuses;
        m_interestedIn = interestedIn;
        m_countries = countries;
        m_keywords = keywords;
    }

    public Integer getAge_max()
    {
        return m_ageMax;
    }

    public Integer getAge_min()
    {
        return m_ageMin;
    }

    public Integer getBroad_age()
    {
        return m_broadAge;
    }

    public Integer[] getGenders()
    {
        return m_genders;
    }

    public Integer[] getRelationship_statuses()
    {
        return m_relationshipStatuses;
    }

    public Integer[] getInterested_in()
    {
        return m_interestedIn;
    }

    public String[] getCountries()
    {
        return m_countries;
    }

    public String[] getKeywords()
    {
        return m_keywords;
    }

    private void setAge_max(Integer ageMax)
    {
        m_ageMax = ageMax;
    }

    private void setAge_min(Integer ageMin)
    {
        m_ageMin = ageMin;
    }

    private void setBroad_age(Integer broadAge)
    {
        m_broadAge = broadAge;
    }

    private void setGenders(Integer[] genders)
    {
        m_genders = genders;
    }

    private void setRelationship_statuses(Integer[] relationshipStatuses)
    {
        m_relationshipStatuses = relationshipStatuses;
    }

    private void setInterested_in(Integer[] interestedIn)
    {
        m_interestedIn = interestedIn;
    }

    private void setCountries(String[] countries)
    {
        m_countries = countries;
    }

    private void setKeywords(String[] keywords)
    {
        m_keywords = keywords;
    }

    /*
     * This method returns a hashmap of this class's elements' key-value pairs
     * */
    public HashMap<String, byte[]> retrieveAllTargetingInfo()
        throws InvalidConfigurationError
    {
        HashMap<String, byte[]> targetingInfo = new HashMap<String, byte[]>();

        try
        {
            BeanInfo info = Introspector.getBeanInfo(Targeting.class,
                                                 Object.class);
            PropertyDescriptor[] props = info.getPropertyDescriptors();
            for (PropertyDescriptor pd : props)
            {
                // Get name of the class member/property
                String name = pd.getName();

                // Get name of getter method
                Method getter = pd.getReadMethod();

                // Invoke the getter method to get the value 
                String csv = null;
                if (getter.getReturnType().isArray())
                {
                    Object[] values = (Object[]) getter.invoke(this);
                    csv = Utils.getCsvString(values);
                }
                else
                {
                    Object value = getter.invoke(this);
                    if (value != null)
                    {
                        csv = value.toString();
                    }
                }
                if (csv != null)
                {
                    targetingInfo.put(name, csv.getBytes());
                }
            }
        }
        catch (Exception e)
        {
            throw new InvalidConfigurationError("Exception occured while " +
                                                "retrieving targeting-nfo", e);
        }
        return targetingInfo;
    }

    @Override
    public String toString()
    {
        StringBuilder builder = new StringBuilder();
        builder.append("Targeting [age_max=");
        builder.append(m_ageMax);
        builder.append(", age_min=");
        builder.append(m_ageMin);
        builder.append(", broad_age=");
        builder.append(m_broadAge);
        builder.append(", countries=");
        builder.append(Arrays.toString(m_countries));
        builder.append(", genders=");
        builder.append(Arrays.toString(m_genders));
        builder.append(", interested_in=");
        builder.append(Arrays.toString(m_interestedIn));
        builder.append(", keywords=");
        builder.append(Arrays.toString(m_keywords));
        builder.append(", relationship_statuses=");
        builder.append(Arrays.toString(m_relationshipStatuses));
        builder.append("]");
        return builder.toString();
    }

}
